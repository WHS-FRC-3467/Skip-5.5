package org.usfirst.frc3467.subsystems.Elevator;

import java.util.Vector;

import org.usfirst.frc3467.RobotMap;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDrive;
import org.usfirst.frc3467.pid.PIDF_CANTalon;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {

	private static Elevator instance;

	private CANTalon 		m_winchMotor1;
	private CANTalon 		m_winchMotor2;
	private PIDF_CANTalon	m_pidfCAN;
	private DigitalInput 	m_limitSwitch;
	
	// Controls display to SmartDashboard
	private static final boolean debugging = true;
	
	// State Info
	private int				m_numTotes;
	private int				m_currLevel;
	private boolean			m_haveBin;
	private boolean 		m_stackingOnPlatform;
	private boolean 		m_stackingOnStep;
	
	// Default PID Constants
	// TODO: Tune these!
	private final double 	KP = 0.300;
	private final double 	KI = 0.001;
	private final double 	KD = 0.012;
	
	/* Default iZone
	 * The iZone is the zone around the target setpoint in which the I term
	 * is actually used. This number defines the size of the iZone ON EACH SIDE
	 * of the desired setpoint.
	 */
	private final int 		IZONE = 1500;
	
	// Default Tolerance for position  error
	private final double 	TOLERANCE = 50;
	
	// Default Ramp Rate for PercentVBus operation
	private final double	RAMPRATE_PVB = 2.0;
	
	// Default Ramp Rate for Closed-Loop operation
	private final double 	RAMPRATE_CL = 2.0;
	
	// TODO: Review and tune all these constants
	
	// Public constants for elevator levels
	// These sometimes act like enumerated values, but they also contain data
	public static final int kLevelZero = 0;
	public static final int kLevelOne = 2500;
	public static final int kLevelTwo = 4500;
	public static final int kLevelThree = 6600;
	public static final int kLevelFour = 8900;
	public static final int kLevelFive = 11000;
	
	public static Vector<Double> LevelList;
	
	
	// Additions to level for platform and step
	public static final int kAdd4Platform = 300;
	public static final int kAdd4Step = 700;
	
	// Other useful levels
	public static final int kLevelIndexTote = 3000;
	public static final int kLevelIndexBin = 2000;
	
	// Constants for some useful speeds
	public static final double kUp_Fixed = 0.2;
	public static final double kStop = 0;
	public static final double kDown_Fixed = -0.2;
	

	public static Elevator getInstance() {
		return instance;
	}
	
	public Elevator() {
		instance = this;
		
		// Init state info
		m_numTotes = 0;
		m_haveBin = false;
		m_currLevel = 0;
		m_stackingOnPlatform = false;
		m_stackingOnStep = false;

		LevelList = new Vector<Double>();
		LevelList.addElement(new Double(kLevelZero));
		LevelList.addElement(new Double(kLevelOne));
		LevelList.addElement(new Double(kLevelTwo));
		LevelList.addElement(new Double(kLevelThree));
		LevelList.addElement(new Double(kLevelFour));
		LevelList.addElement(new Double(kLevelFive));

		m_limitSwitch = new DigitalInput(RobotMap.elevBottomLimitSwitch);
		m_winchMotor1 = new CANTalon(RobotMap.winchDriveCANTalon);
		m_winchMotor2 = new CANTalon(RobotMap.winchSlaveCANTalon);
		
	    // winch motor 1 is the master device
		m_winchMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);

		// winch motor 2 is the slave device - it will follow winch motor 1
	    m_winchMotor2.changeControlMode(ControlMode.Follower);
		m_winchMotor2.set(RobotMap.winchDriveCANTalon);
		m_winchMotor2.reverseOutput(true);

		// Set parameters for master talon; slave will follow suit
		m_winchMotor1.setIZone(IZONE);
//		m_winchMotor1.setVoltageRampRate(RAMPRATE_PVB);
		m_winchMotor1.setCloseLoopRampRate(RAMPRATE_CL);
		
		//m_winchMotor1.setForwardSoftLimit(12000);
		// PID Testing mode - limit top
		m_winchMotor1.setForwardSoftLimit(8000);
		m_winchMotor1.enableForwardSoftLimit(true);

		// For Testing Only - set bottom limit to position of conveyor on boot
		m_winchMotor1.setReverseSoftLimit(0);
		m_winchMotor1.enableReverseSoftLimit(true);

	    // Turn off motor safety until we get the system tuned
		m_winchMotor1.setSafetyEnabled(false);
		//m_winchMotor1.setExpiration(1.0);

		// Put a wrapper around winch motor 1 for managing the PID
		m_pidfCAN = new PIDF_CANTalon("Elevator", m_winchMotor1, TOLERANCE, false);
		m_pidfCAN.setPID(KP, KI, KD);
		
	}
	
	protected void initDefaultCommand() {
		this.setDefaultCommand(new elevatorDrive());
	}
	
	public void initManualMode() {

		m_winchMotor1.changeControlMode(ControlMode.PercentVbus);
		m_winchMotor1.enableControl();
		
		// Stop motor until we are ready to set speed
		m_winchMotor1.set(0);
		
		if (debugging)
			SmartDashboard.putString("Elevator Mode", "Manual");
	}
	
	public void driveManual(double speed) {
		
		if((m_limitSwitch.get() == true) && speed < 0)
		{
			speed = 0;
		}
		
		m_winchMotor1.set(speed);
		
		if (debugging) {
	    	SmartDashboard.putBoolean("Elevator Enabled", m_winchMotor1.isControlEnabled());
			SmartDashboard.putNumber("Elevator Speed Requested", speed);		
			SmartDashboard.putNumber("Elevator Position", m_winchMotor1.getPosition());
		}
	
	}
	
	public void initPositionalMode() {
		m_winchMotor1.changeControlMode(ControlMode.Position);
		
		// Disable for safety until we are ready to set the setpoint
		m_pidfCAN.disable();
		
		if (debugging)
			SmartDashboard.putString("Elevator Mode", "Positional");
		
	}
	
	public void zeroEncoder() {
		m_pidfCAN.setPosition(0);
		this.setLevel(0);

	}
	
	public boolean isZero() {
		return m_limitSwitch.get();
	}
	
	
	public void updatePIDFfromSDB() {
		m_pidfCAN.updatePIDF();
		
	}
	
	public double getPositionForLevel(int level) {
		
		double target = 0;
		
		// If requested level is out of bounds, return -1
		if (level < 0 || level > 5)
			return (-1);
		
		// Get encoder value for requested level
		target = ((Double) LevelList.elementAt(level)).doubleValue();
		
		// Add increments if we are stacking on platform or step, rather than floor
		if (m_stackingOnPlatform == true)
			target += kAdd4Platform;
		else if (m_stackingOnStep == true)
			target += kAdd4Step;
		
		return target;
	}
	
	public double getPositionForOneLevelUp() {

		verifyCurrentLevel();
		return getPositionForLevel(m_currLevel + 1);
	}
	
	public double getPositionForOneLevelDown(){
		
		verifyCurrentLevel();
		return getPositionForLevel(m_currLevel - 1);
	}
	
	private void verifyCurrentLevel() {
		double currpos = m_pidfCAN.getPosition();
		int actual_level = 0;
		int add2lvl = 0;
		
		// Add increments if we are stacking on platform or step, rather than floor
		if (m_stackingOnPlatform == true)
			add2lvl = kAdd4Platform;
		else if (m_stackingOnStep == true)
			add2lvl = kAdd4Step;
		
		/*
		 *  Find out if current encoder position matches the level we THINK we are on
		 *  
		 *  1. Loop through each level
		 *  2. Compare current position to position reading for the level
		 *  3. If current position is <= to expected position reading, then break out of loop
		 *  4. Else if current position > expected position reading (+ tolerance), then
		 *     bump actual_level counter and go to next level
		 */
		for (int i = 0; i <= 5; i++) {
			double lvlpos = ((Double)LevelList.elementAt(i)).doubleValue() + add2lvl;
			if (currpos <= (lvlpos + TOLERANCE))
				break;
			else
				actual_level++;
		}
		
		if (m_currLevel != actual_level)
			SmartDashboard.putString("Elevator Verify", "OUT OF SYNC");
		else
			SmartDashboard.putString("Elevator Verify", "Level OK");
			
		m_currLevel = actual_level;
	}
	
	public void gotoPosition(double position) {

		// Re-enable PID
		if (!m_pidfCAN.isEnabled())
			m_pidfCAN.enable();

		m_pidfCAN.setSetpoint(position);		
	}
	
	public double getPosition() {
		return m_pidfCAN.getPosition();				
	}
	
	public boolean onTarget() {
		return m_pidfCAN.onTarget();
	}
	
	public void disablePID() {
		m_pidfCAN.disable();
	}
	
	/*
	 * Methods to handle elevator state data
	 */
	public void addTote() {
		m_numTotes++;
	}
	
	public void subtractTote() {
		m_numTotes--;
	}
	
	public void setNumTotes(int numT) {
		m_numTotes = numT;
	}
	
	public int getNumTotes() {
		return m_numTotes;
	}
	
	public void addBin() {
		m_haveBin = true;
	}
	
	public void removeBin() {
		m_haveBin = false;
	}
	
	public boolean hasBin() {
		return m_haveBin;
	}

	public void setLevel(int level) {
		m_currLevel = level;
	}
	
	public int getLevel() {
		return m_currLevel;
	}
	
	public void levelUp() {
		m_currLevel++;
	}
	
	public void levelDown() {
		m_currLevel--;
	}
	
	public void setStackingOnPlatform(boolean onPlatform) {
		m_stackingOnPlatform = onPlatform;
		if (onPlatform == true)
			m_stackingOnStep = false;
	}
	
	public boolean getStackingOnPlatform() {
		return m_stackingOnPlatform;
	}
	
	public void setStackingOnStep(boolean onStep) {
		m_stackingOnStep = onStep;
		if (onStep == true)
			m_stackingOnPlatform = false;
	}
	
	public boolean getStackingOnStep() {
		return m_stackingOnStep;
	}
	
	
	




}
