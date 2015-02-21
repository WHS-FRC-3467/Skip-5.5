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
	
	// Default CANTalon PID constants
	private final double KP_V = 0.2;
	private final double KI_V = 0.0;
	private final double KD_V = 0.0;
	private final double KF_V = 1.0;

	// Positional CANTalon PID constants
	private final double KP_P = 0.5;
	private final double KI_P = 0.0;
	private final double KD_P = 0.0;
	private final double KF_P = 0.0;

	private CANTalon 				CANTalonFL, CANTalonRL, CANTalonFR, CANTalonRR; 
	private RobotDrive 				m_drive;
	private CANTalon.ControlMode	m_ctrlMode;
	
	private double					m_positionalDistance;
	
	private PIDF_CANTalon			m_pidfDriveFL;
	private PIDF_CANTalon			m_pidfDriveRL;
	private PIDF_CANTalon			m_pidfDriveFR;
	private PIDF_CANTalon			m_pidfDriveRR;
	
	private static DriveBase 		instance;
	
	public static DriveBase getInstance() {
		return instance;
	}
	
	protected void initDefaultCommand() {
		// Argument to DriveMecanum() says whether to use Voltage (PercentVBus) or not
		this.setDefaultCommand(new DriveMecanum(true));
	}
	
	public DriveBase() {
		
		// Call PIDSubsystem constructor
		// Use PID for turning drivebase by gyro
		// TODO: Tune these PID constants for turning in place
		super("DriveBase", 1.0, 0.0, 0.0);
		
		instance = this;

		// Instantiate motor controllers
		CANTalonFL = new CANTalon(RobotMap.driveTrainCANTalonFL);		
		CANTalonRL = new CANTalon(RobotMap.driveTrainCANTalonRL);
	    CANTalonFR = new CANTalon(RobotMap.driveTrainCANTalonFR);
	    CANTalonRR = new CANTalon(RobotMap.driveTrainCANTalonRR);
		
	    // Default mode (set in CANTalon constructor) is PercentVBus
	    m_ctrlMode = ControlMode.PercentVbus;
	    
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
		m_drive.setInvertedMotor(MotorType.kFrontLeft, true);
		m_drive.setInvertedMotor(MotorType.kRearLeft, true);
		m_drive.setInvertedMotor(MotorType.kFrontRight, false);
		m_drive.setInvertedMotor(MotorType.kRearRight, false);
 
	    // Create PID management wrappers around controllers
	    m_pidfDriveFL = new PIDF_CANTalon("Drive FL", CANTalonFL, TOLERANCE, true);
		m_pidfDriveRL = new PIDF_CANTalon("Drive RL", CANTalonRL, TOLERANCE, true);
		m_pidfDriveFR = new PIDF_CANTalon("Drive FR", CANTalonFR, TOLERANCE, true);
		m_pidfDriveRR = new PIDF_CANTalon("Drive RR", CANTalonRR, TOLERANCE, true);

		// Set PIDF constants for each controller
		m_pidfDriveFL.setPID(KP_V, KI_V, KD_V, KF_V);
		m_pidfDriveRL.setPID(KP_V, KI_V, KD_V, KF_V);
		m_pidfDriveFR.setPID(KP_V, KI_V, KD_V, KF_V);
		m_pidfDriveRR.setPID(KP_V, KI_V, KD_V, KF_V);
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
		
	// Use arcade mode to drive
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

	// Use standard tank drive
	public void driveTank(double left, double right) {
		m_drive.tankDrive(left, right);

	}

	// Set up for distance driving by PercentVBus
	public void initDistance(double distance) {

		m_positionalDistance = distance;
		
		if (m_ctrlMode != ControlMode.Position) {

		    // CANTalonFL is the master device
			CANTalonFL.changeControlMode(ControlMode.Position);
			
			// All others are slave devices - they will follow CANTalonFL
		    CANTalonRL.changeControlMode(ControlMode.Follower);
		    CANTalonFR.changeControlMode(ControlMode.Follower);
		    CANTalonRR.changeControlMode(ControlMode.Follower);
		    
		    CANTalonRL.set(RobotMap.driveTrainCANTalonFL);
		    CANTalonFR.set(RobotMap.driveTrainCANTalonFL);
		    CANTalonRR.set(RobotMap.driveTrainCANTalonFL);
		    CANTalonFR.reverseOutput(true);
		    CANTalonRR.reverseOutput(true);

			// Initialize encoder and setpoint
		    CANTalonFL.setPosition(0);
		    CANTalonFL.set(0);
		    
		    // Set parameters for master talon; slaves will follow suit
		    CANTalonFL.setIZone(IZONE);
		    CANTalonFL.setCloseLoopRampRate(RAMPRATE);

		    // Turn off motor safety until we get the system tuned
			CANTalonFL.setSafetyEnabled(false);
			//CANTalonFL.setExpiration(1.0);

			// Set PID constants
			m_pidfDriveFL.setPID(KP_P, KI_P, KD_P, KF_P);
			
		    m_ctrlMode = ControlMode.Position;
		}
		
	}

	// Drive specified distance
	public void driveDistance() {
		
		m_pidfDriveFL.setSetpoint(m_positionalDistance);

	}

	// Have we driven specified distance?
	public boolean onPosition() {
		
		return m_pidfDriveFL.onTarget();

	}

	// Set up for mecanum driving by either Velocity or Voltage
	public void initMecanum(boolean useVoltage) {
		
		if (useVoltage == true) {

			// Arcade driving uses PercentVBus
			initArcade();
		}
		else
		{
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
		
	}

	// Use mecanum drive
	public void driveMecanum(double x, double y, double rotation, double gyroAngle) {
		m_drive.mecanumDrive_Cartesian(x, y, rotation, gyroAngle);

	}

	/*
	 * Use PID loop on roboRio to turn robot to specified target angle
	 */
	private double pidTurnToAngleInput(double angle, boolean clockWise) {
		double correctedGyroAngle = CommandBase.imu.getYaw() + 180;
		boolean wrapAround = ((clockWise == true && correctedGyroAngle < angle && angle
				- correctedGyroAngle > 180) || (clockWise = true
				&& correctedGyroAngle > angle
				&& angle - correctedGyroAngle <= 180));
		if (wrapAround) {
			if (clockWise == true)
				angle = angle + 360;
			else
				angle = angle - 360;
		}
		double pidInputValue = (angle - correctedGyroAngle) / 180;
		return pidInputValue;
	}
	
	/*
	 * Calculate quickest direction from current orientation to a given angle
	 * 
	 * @returns: 	true = turn clockwise
	 * 				false = turn counterclockwise
	 */
	private boolean shortestTurnDirection(double angle){
		boolean turnClockWise = true;
		double correctedGyroAngle = CommandBase.imu.getYaw() + 180;
		if ((correctedGyroAngle >= angle && correctedGyroAngle - angle <= 180)
				|| (correctedGyroAngle < angle && angle - correctedGyroAngle > 180)) {
			turnClockWise = false;
		}
		return turnClockWise;
	}
	
	/*
	 * Methods used by PID subsystem to control DriveTurnToAngle
	 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#returnPIDInput()
	 */
	@Override
	protected double returnPIDInput() {
		
		boolean bClockwise = shortestTurnDirection(this.getSetpoint());
		return pidTurnToAngleInput(this.getSetpoint(), bClockwise);
	}

	@Override
	protected void usePIDOutput(double output) {
//		m_drive.mecanumDrive_Cartesian(0, 0, output, 0);
		
	}
	
}
