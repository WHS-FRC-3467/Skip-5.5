package org.usfirst.frc3467.subsystems.Elevator;

import org.usfirst.frc3467.RobotMap;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDrive;
import org.usfirst.frc3467.pid.PIDF_CANTalon;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {

	private static Elevator instance;

	private CANTalon 		m_winchMotor1;
	private CANTalon 		m_winchMotor2;
	private PIDF_CANTalon	m_pidfCAN;
	
	private double m_topPosition = 0;
	
	// Controls display to SmartDashboard
	private static final boolean debugging = true;
	
	// State Info
	private int		m_numTotes;
	private double	m_currLevel;
	private boolean	m_haveBin;
	private boolean m_stackingOnPlatform;
	private boolean m_stackingOnStep;
	
	// Default PID Constants
	private final double KP = 0.6;
	private final double KI = 0.003;
	private final double KD = 0.06;
	
	/* Default iZone
	 * The iZone is the zone around the target setpoint in which the I term
	 * is actually used. This number defines the size of the iZone ON EACH SIDE
	 * of the desired setpoint.
	 */
	private final int IZONE = 100;
	
	// Default Tolerance for position  error
	private final double TOLERANCE = 5;
	
	// Default Ramp Rate for Closed-Loop operation
	private final double RAMPRATE = 2;
	
	// TODO: Review and tune all these constants
	
	// Public constants for elevator levels
	// These sometimes act like enumerated values, but they also contain data
	public static final int kLevelZero = 0;
	public static final int kLevelOne = 1000;
	public static final int kLevelTwo = 2000;
	public static final int kLevelThree = 3000;
	public static final int kLevelFour = 4000;
	public static final int kLevelFive = 5000;
	
	// Additions to level for platform and step
	public static final int kAdd4Platform = 300;
	public static final int kAdd4Step = 700;
	
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
		m_currLevel = kLevelZero;
		m_stackingOnPlatform = false;
		m_stackingOnStep = false;
		
		m_winchMotor1 = new CANTalon(RobotMap.winchDriveCANTalon);
		m_winchMotor2 = new CANTalon(RobotMap.winchSlaveCANTalon);
		
	    // winch motor 1 is the master device
		m_winchMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		m_winchMotor1.setIZone(IZONE);
		m_winchMotor1.setCloseLoopRampRate(RAMPRATE);

	    // Turn off motor safety until we get the system tuned
		m_winchMotor1.setSafetyEnabled(false);
		//m_winchMotor1.setExpiration(1.0);

		// winch motor 2 is the slave device - it will follow winch motor 1
	    m_winchMotor2.changeControlMode(ControlMode.Follower);
		m_winchMotor2.set(RobotMap.winchDriveCANTalon);
		
		// Put a wrapper around winch motor 1 for managing the PID
		m_pidfCAN = new PIDF_CANTalon("Elevator", m_winchMotor1, TOLERANCE, false);
		m_pidfCAN.setPID(KP, KI, KD);
		
	}
	
	protected void initDefaultCommand() {
		this.setDefaultCommand(new elevatorDrive());
	}
	
	public void initManualMode() {
		m_winchMotor1.changeControlMode(ControlMode.PercentVbus);
		if (debugging)
			SmartDashboard.putString("Elevator Mode", "Manual");
	}
	
	public void driveManual(double speed) {
		m_winchMotor1.set(speed);
		if (debugging)
			SmartDashboard.putNumber("Elevator Speed Requested", speed);		
	
	}
	
	public void initPositionalMode() {
		m_winchMotor1.changeControlMode(ControlMode.Position);
		if (debugging)
			SmartDashboard.putString("Elevator Mode", "Positional");
		
	}
	
	public void zeroEncoder() {
		m_pidfCAN.setPosition(0);

	}
	
	public void updatePIDFfromSDB() {
		m_pidfCAN.updatePIDF();
		
	}
	
	public void gotoLevel(int level) {
		
		double target = 0;
		
		// Add increments if we are stacking on platform or step, rather than floor
		if (m_stackingOnPlatform == true)
			target += kAdd4Platform;
		else if (m_stackingOnStep == true)
			target += kAdd4Step;
		
		// Add in the distance to the desired level
		switch (level)
		{
		case kLevelOne:
			target += kLevelOne; 
			break;
		case kLevelTwo:
			target += kLevelTwo; 
			break;
		case kLevelThree:
			target += kLevelThree; 
			break;
		case kLevelFour:
			target += kLevelFour; 
			break;
		case kLevelFive:
			target += kLevelFive; 
			break;
		case kLevelZero:
		default:
			target = 0;
		}
		
		// Command elevator to go to determined position
		m_pidfCAN.setSetpoint(target);
	}
	
	
	public void gotoPosition(double position) {
		m_pidfCAN.setSetpoint(position);		
	}
	
	public double getPosition() {
		return m_pidfCAN.getPosition();				
	}
	
	public boolean onTarget() {
		return m_pidfCAN.onTarget();
	}
	
	public void gotoTop() {
		m_pidfCAN.setSetpoint(m_topPosition);		
	}
	
	public void setTop(double topPos) {
		m_topPosition = topPos;
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

	public void setLevel(double level) {
		m_currLevel = level;
	}
	
	public double getLevel() {
		return m_currLevel;
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
