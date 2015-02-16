package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.RobotMap;
import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.pid.PIDF_CANTalon;
import org.usfirst.frc3467.subsystems.DriveBase.commands.DriveMecanum;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class DriveBase extends PIDSubsystem {
	
	public static final boolean debugging = true;
	
	/* Default iZone
	 * The iZone is the zone around the target setpoint in which the I term
	 * is actually used. This number defines the size of the iZone ON EACH SIDE
	 * of the desired setpoint.
	 */
	private final int IZONE = 50;
	
	// Default Tolerance for closed-loop  error
	private final double TOLERANCE = 20;
	
	// Default Ramp Rate for Closed-Loop operation
	private final double RAMPRATE = 2;
	
	// Default PID Constants
	private final double KP = 0.2;
	private final double KI = 0.0;
	private final double KD = 0.0;
	private final double KF = 1.0;

	private CANTalon 				CANTalonFL, CANTalonRL, CANTalonFR, CANTalonRR; 
	private RobotDrive 				m_drive;
	private CANTalon.ControlMode	m_ctrlMode;
	
	private PIDF_CANTalon			m_pidfDriveFL;
	private PIDF_CANTalon			m_pidfDriveRL;
	private PIDF_CANTalon			m_pidfDriveFR;
	private PIDF_CANTalon			m_pidfDriveRR;

	private static DriveBase 		instance;
	
	public static DriveBase getInstance() {
		return instance;
	}
	
	protected void initDefaultCommand() {
		this.setDefaultCommand(new DriveMecanum());
	}
	
	public DriveBase() {
		
		// Call PIDSubsystem constructor
		// Use PID for turning drivebase by gyro
		super("DriveBase", 1.0, 0.0, 0.0);
		
		instance = this;

		// Instantiate motor controllers
		CANTalon CANTalonFL = new CANTalon(RobotMap.driveTrainCANTalonFL);		
		CANTalon CANTalonRL = new CANTalon(RobotMap.driveTrainCANTalonRL);
	    CANTalon CANTalonFR = new CANTalon(RobotMap.driveTrainCANTalonFR);
	    CANTalon CANTalonRR = new CANTalon(RobotMap.driveTrainCANTalonRR);
	    		
	    // Using Grayhill 256 encoders
	    CANTalonFL.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	    CANTalonRL.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	    CANTalonFR.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	    CANTalonRR.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	    
	    // Encoder counting backwards? Uncomment the corresponding line here.
//	    CANTalonFL.reverseSensor(true);
//	    CANTalonRL.reverseSensor(true);
//	    CANTalonFR.reverseSensor(true);
//	    CANTalonRR.reverseSensor(true);
		
	    // Set the iZones
	    CANTalonFL.setIZone(IZONE);
	    CANTalonRL.setIZone(IZONE);
	    CANTalonFR.setIZone(IZONE);
	    CANTalonRR.setIZone(IZONE);

		// Set the voltage ramp rates
	    CANTalonFL.setCloseLoopRampRate(RAMPRATE);
		CANTalonRL.setCloseLoopRampRate(RAMPRATE);
		CANTalonFR.setCloseLoopRampRate(RAMPRATE);
		CANTalonRR.setCloseLoopRampRate(RAMPRATE);

	    // Instantiate a RobotDrive object
		m_drive = new RobotDrive(CANTalonFL, CANTalonRL, CANTalonFR, CANTalonRR);
	    
		// Set RobotDrive parameters
		m_drive.setSafetyEnabled(true);
        m_drive.setExpiration(1.0);
        m_drive.setSensitivity(0.5);
        m_drive.setMaxOutput(1.0);
		m_drive.setInvertedMotor(MotorType.kFrontLeft, false);
		m_drive.setInvertedMotor(MotorType.kRearLeft, false);
		m_drive.setInvertedMotor(MotorType.kFrontRight, true);
		m_drive.setInvertedMotor(MotorType.kRearRight, true);
		
		// Disable all controllers for now
		CANTalonFL.changeControlMode(ControlMode.Disabled);
	    CANTalonRL.changeControlMode(ControlMode.Disabled);
	    CANTalonFR.changeControlMode(ControlMode.Disabled);
	    CANTalonRR.changeControlMode(ControlMode.Disabled);
	    
	    // Create PID management wrappers around controllers
	    m_pidfDriveFL = new PIDF_CANTalon("Drive FL", CANTalonFL, TOLERANCE, true);
		m_pidfDriveRL = new PIDF_CANTalon("Drive RL", CANTalonRL, TOLERANCE, true);
		m_pidfDriveFR = new PIDF_CANTalon("Drive FR", CANTalonFR, TOLERANCE, true);
		m_pidfDriveRR = new PIDF_CANTalon("Drive RR", CANTalonRR, TOLERANCE, true);

		// Set PIDF constants for each controller
		m_pidfDriveFL.setPID(KP, KI, KD, KF);
		m_pidfDriveRL.setPID(KP, KI, KD, KF);
		m_pidfDriveFR.setPID(KP, KI, KD, KF);
		m_pidfDriveRR.setPID(KP, KI, KD, KF);
		
	}
	
	// Set up for arcade driving by PercentVBus
	public void initArcade() {

		if (m_ctrlMode != ControlMode.PercentVbus) {

			CANTalonFL.changeControlMode(ControlMode.PercentVbus);
		    CANTalonRL.changeControlMode(ControlMode.PercentVbus);
		    CANTalonFR.changeControlMode(ControlMode.PercentVbus);
		    CANTalonRR.changeControlMode(ControlMode.PercentVbus);
		    m_ctrlMode = ControlMode.PercentVbus;
		}
		
        m_drive.setMaxOutput(1.0);
		
	}
		
	// Use arcade mode to m_drive
	public void driveArcade(double speed, double angle) {
		m_drive.arcadeDrive(speed, angle);

	}
	
	// Set up for tank driving by PercentVBus
	public void initTank() {
		if (m_ctrlMode != ControlMode.PercentVbus) {

		    CANTalonFL.changeControlMode(ControlMode.PercentVbus);
		    CANTalonRL.changeControlMode(ControlMode.PercentVbus);
		    CANTalonFR.changeControlMode(ControlMode.PercentVbus);
		    CANTalonRR.changeControlMode(ControlMode.PercentVbus);
		    m_ctrlMode = ControlMode.PercentVbus;
		}
        m_drive.setMaxOutput(1.0);
		
	}

	// Use standard tank m_drive
	public void driveTank(double left, double right) {
		m_drive.tankDrive(left, right);

	}
	
	// Set up for mecanum driving by Velocity
	public void initMecanum() {
		
		if (m_ctrlMode != ControlMode.Speed) {

			CANTalonFL.changeControlMode(ControlMode.Speed);
		    CANTalonRL.changeControlMode(ControlMode.Speed);
		    CANTalonFR.changeControlMode(ControlMode.Speed);
		    CANTalonRR.changeControlMode(ControlMode.Speed);
		    m_ctrlMode = ControlMode.Speed;
		}
	    
        /*
         *  Set maximum velocity of our wheels (in counts per 0.1 second)
	     *	12fps -> ~780 counts/0.1 seconds
		 *
		 *	Values computed by the RobotDrive code from the OI input (usually -1 -> +1)
		 *	will be multiplied by this value before being sent to the controllers' set() methods.
		 *
		 *	If drive stick(s) max out too early, lower this value.
	     */
		m_drive.setMaxOutput(780.0);
		
	}

	// Use mecanum
	public void driveMecanum(double x, double y, double rotation, double gyroAngle) {
		m_drive.mecanumDrive_Cartesian(x, y, rotation, gyroAngle);

	}

	private double pidTurnToAngleInput(double angle, boolean clockWise) {
		double correctedGyroAngle = CommandBase.imu.getYaw() + 180;
		boolean wrapAround = ((clockWise == true && correctedGyroAngle < angle && angle
				- correctedGyroAngle > 180) || (clockWise = true
				&& correctedGyroAngle > angle
				&& angle - correctedGyroAngle <= 180));
		if (clockWise == true && wrapAround) {
			angle = angle + 360;
		}
		if (clockWise == false && wrapAround) {
			angle = angle - 360;
		}
		double pidInputValue = (angle - correctedGyroAngle) / 180;
		return pidInputValue;
	}
	
	private boolean shortestTurnDirection(double angle){
		boolean turnClockWise = true;
		double correctedGyroAngle = CommandBase.imu.getYaw() + 180;
		if ((correctedGyroAngle >= angle && correctedGyroAngle - angle <= 180)
				|| (correctedGyroAngle < angle && angle - correctedGyroAngle > 180)) {
		turnClockWise = false;
		}
		return turnClockWise;
	}
	

	@Override
	protected double returnPIDInput() {
		
		boolean bClockwise = shortestTurnDirection(this.getSetpoint());
		return pidTurnToAngleInput(this.getSetpoint(), bClockwise);
	}

	@Override
	protected void usePIDOutput(double output) {
		m_drive.mecanumDrive_Cartesian(0, 0, output, CommandBase.imu.getYaw());
		
	}
	
}
