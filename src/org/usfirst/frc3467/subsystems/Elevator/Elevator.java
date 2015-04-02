package org.usfirst.frc3467.subsystems.Elevator;

import org.usfirst.frc3467.RobotMap;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDrivePID;
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
	public DigitalInput 	m_elevatorLimitSwitch;
	
	// Controls display to SmartDashboard
	private static final boolean debugging = true;
	
	// State Info
	private boolean			m_hasBeenZeroed;
	private double			m_elevatorSetpoint;
	private boolean			m_isPositional;
	private int				m_numTotes;
	private boolean			m_haveBin;
	
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
	private final double 	TOLERANCE = 30;
	
	// Default Ramp Rate for PercentVBus operation
//	private final double	RAMPRATE_PVB = 2.0;
	
	// Default Ramp Rate for Closed-Loop operation
	private final double 	RAMPRATE_CL = 2.0;
	
	// TODO: Review and tune all these constants
	
	// Public constants for elevator levels
	// These sometimes act like enumerated values, but they also contain data
	public static final int kLevelZero = 150;  // Platform eject height
	public static final int kLevelStepZero = 700;
	public static final int kLevelHumanFeed = 1350;
	public static final int kLevelOne = 2500;
	public static final int kLevelStepOne = 3450;
	public static final int kLevelTwo = 4500;
	public static final int kLevelStepTwo = 5600;
	public static final int kLevelThree = 6600;
	public static final int kLevelStepThree = 7700;
	public static final int kLevelFour = 8900;
	public static final int kLevelFive = 11000;
	public static final int kLevelTop = 13300;
	
	
	// Other useful levels
	public static final int kLevelStep = 1150;
	public static final int kLevelIndexTote = 6250;
	public static final int kLevelDropStackWithToteOnConveyor = 4800;
	public static final int kLevelIndexUprightRC = 4500;
	public static final int kLevelIndexSidewaysRC = 6000;

	
	// Constants for some useful speeds
	public static final double kUp_FixedPlus = 0.3;
	public static final double kUp_Fixed = 0.2;
	public static final double kUp_FixedSlow = 0.1;
	public static final double kStop = 0;
	public static final double kDown_FixedSlow = -0.1;
	public static final double kDown_Fixed = -0.2;
	public static final double kDown_FixedPlus = -0.3;
	

	public static Elevator getInstance() {
		return instance;
	}
	
	public Elevator() {
		instance = this;
		
		// Init state info
		m_hasBeenZeroed = false;
		m_elevatorSetpoint = 0.0;
		m_isPositional = true;
		m_numTotes = 0;
		m_haveBin = false;

		m_elevatorLimitSwitch = new DigitalInput(RobotMap.elevBottomLimitSwitch);

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
		m_winchMotor1.setCloseLoopRampRate(RAMPRATE_CL);
		
	    // Turn off motor safety until we get the system tuned
		m_winchMotor1.setSafetyEnabled(false);
		//m_winchMotor1.setExpiration(1.0);

		// Put a wrapper around winch motor 1 for managing the PID
		m_pidfCAN = new PIDF_CANTalon("Elevator", m_winchMotor1, TOLERANCE, false, debugging);
		m_pidfCAN.setPID(KP, KI, KD);
		
	}
	
	protected void initDefaultCommand() {
		this.setDefaultCommand(new elevatorDrivePID());
	}
	 
	public void initManualMode() {

		if (m_isPositional) {
			m_isPositional = false;
			m_winchMotor1.changeControlMode(ControlMode.PercentVbus);
			m_winchMotor1.enableControl();
			
			// Stop motor until we are ready to set speed
			m_winchMotor1.set(0);
			
			if (debugging)
				SmartDashboard.putString("Elevator Mode", "Manual");
		}
	}
	
	public void driveManual(double speed) {
		
		// If trying to drive down and limit switch is hit, then stop...
		if(isZero() && speed < 0.0) {
			speed = 0.0;
			zeroEncoder();
			m_elevatorSetpoint = 0;
			m_hasBeenZeroed = true;
		}
		else
		{
			m_winchMotor1.set(speed);
			
			// Update the elevator setpoint even while in manual mode
			// to avoid surprises when returning to PID control
			m_elevatorSetpoint = m_pidfCAN.getPosition();			
		}

		manualDriveSDUpdates(speed);
			
	}
	
	public boolean driveToPosition(double speed, double position) {

		double currPos;
		boolean retval = false;
		
		if(isZero()) {
			speed = 0.0;
			zeroEncoder();
			m_elevatorSetpoint = 0;
			m_hasBeenZeroed = true;
			retval = true;
		}
		else
		{
			currPos = m_pidfCAN.getPosition();
			
			// Update the elevator setpoint even while in manual mode
			// to avoid surprises when returning to PID control
			m_elevatorSetpoint = currPos;

			// Look at direction and current position to determine if we need to stop
			if ((speed >= 0.0 && currPos >= position) ||
				(speed < 0.0 && currPos <= position)) {

				m_winchMotor1.set(0.0);
				retval = true;
			}
			else
				m_winchMotor1.set(speed);
		}
		
		manualDriveSDUpdates(speed);
		
		return retval;
	}

	private void manualDriveSDUpdates(double speed) {
		if (debugging) {
	    	SmartDashboard.putBoolean("Elevator Enabled", m_winchMotor1.isControlEnabled());
			SmartDashboard.putNumber("Elevator Speed Requested", speed);		
			SmartDashboard.putNumber("Elevator Position", m_winchMotor1.getPosition());
			SmartDashboard.putBoolean("Elevator At Bottom", isZero());
			SmartDashboard.putBoolean("Elevator ZEROed", m_hasBeenZeroed);
		}
		
	}
	
	public void reportPosition(){
		m_pidfCAN.getPosition();
		if (debugging) {
	    	SmartDashboard.putBoolean("Elevator Enabled", m_winchMotor1.isControlEnabled());
			SmartDashboard.putNumber("Elevator Position", m_winchMotor1.getPosition());
			SmartDashboard.putBoolean("Elevator At Bottom", isZero());
		}

	}
	
	public boolean initPositionalMode() {

		if (!m_hasBeenZeroed)
		{
			// Don't allow anything to happen until we have been zeroed
			SmartDashboard.putBoolean("Elevator ZEROed", false);
			return false;
			
		}
		
		if (!m_isPositional) {
			m_isPositional = true;
			m_winchMotor1.changeControlMode(ControlMode.Position);
		
			// Disable for safety until we are ready to set the setpoint
			m_pidfCAN.disable();
			
			if (debugging)
				SmartDashboard.putString("Elevator Mode", "Positional");
		}
		return true;
		
	}
	
	public void zeroEncoder() {
		m_pidfCAN.setPosition(0);

	}
	
	public boolean isZero() {
		return m_elevatorLimitSwitch.get();
	}
	
	public boolean hasBeenZeroed() {
		return m_hasBeenZeroed;
	}
	
	
	public void updatePIDFfromSDB() {
		m_pidfCAN.updatePIDF();
		
	}
		
	public void gotoPosition(double position) {

		// Re-enable PID
		if (!m_pidfCAN.isEnabled())
			m_pidfCAN.enable();

		m_pidfCAN.setSetpoint(position);
		
		// Save the position
		m_elevatorSetpoint = position;
		
		if (debugging)
			SmartDashboard.putNumber("Elevator Setpoint", m_elevatorSetpoint);
	}
	
	/*
	 *  Return our own internal copy of the setpoint
	 *  
	 *  We need to do this because the Talon's setpoint gets set to zero
	 *  whenever we change modes
	 */
	public double getElevatorSetpoint() {

		if (debugging)
			SmartDashboard.putNumber("Elevator Setpoint", m_elevatorSetpoint);

		return m_elevatorSetpoint;				

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

}
