package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.RobotMap;
//import org.usfirst.frc3467.pid.PIDFManager;
import org.usfirst.frc3467.subsystems.DriveBase.commands.DriveMecanum;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBase extends Subsystem {
	
	public static final boolean debugging = true;
	
	private CANTalon CANTalonFL, CANTalonRL, CANTalonFR, CANTalonRR; 
	private RobotDrive drive;
	
	private static DriveBase instance;
	
	public static DriveBase getInstance() {
		return instance;
	}
	
	protected void initDefaultCommand() {
		this.setDefaultCommand(new DriveMecanum());
	}
	
	public DriveBase() {
		instance = this;
		
		CANTalon CANTalonFL = new CANTalon(RobotMap.driveTrainCANTalonFL);		
		CANTalon CANTalonRL = new CANTalon(RobotMap.driveTrainCANTalonRL);
	    CANTalon CANTalonFR = new CANTalon(RobotMap.driveTrainCANTalonFR);
	    CANTalon CANTalonRR = new CANTalon(RobotMap.driveTrainCANTalonRR);
	    
	    drive = new RobotDrive(CANTalonFL, CANTalonRL, CANTalonFR, CANTalonRR);
	    
		drive.setSafetyEnabled(true);
        drive.setExpiration(0.1);
        drive.setSensitivity(0.5);
        drive.setMaxOutput(1.0);
		drive.setInvertedMotor(MotorType.kFrontLeft, false);
		drive.setInvertedMotor(MotorType.kRearLeft, false);
		drive.setInvertedMotor(MotorType.kFrontRight, true);
		drive.setInvertedMotor(MotorType.kRearRight, true);
		
	}
	
	// Set up for arcade driving by PercentVBus
	public void initArcade() {
	    CANTalonFL.changeControlMode(ControlMode.PercentVbus);
	    CANTalonRL.changeControlMode(ControlMode.PercentVbus);
	    CANTalonFR.changeControlMode(ControlMode.PercentVbus);
	    CANTalonRR.changeControlMode(ControlMode.PercentVbus);
		
        drive.setMaxOutput(1.0);
		
	}
		
	// Use arcade mode to drive
	public void driveArcade(double speed, double angle) {
		drive.arcadeDrive(speed, angle);
		updateSD();
	}
	
	// Set up for tank driving by PercentVBus
	public void initTank() {
	    CANTalonFL.changeControlMode(ControlMode.PercentVbus);
	    CANTalonRL.changeControlMode(ControlMode.PercentVbus);
	    CANTalonFR.changeControlMode(ControlMode.PercentVbus);
	    CANTalonRR.changeControlMode(ControlMode.PercentVbus);
		
        drive.setMaxOutput(1.0);
		
	}

	// Use standard tank drive
	public void driveTank(double left, double right) {
		drive.tankDrive(left, right);
		updateSD();
	}
	
	// Set up for mecanum driving by Velocity
	public void initMecanum() {
	    CANTalonFL.changeControlMode(ControlMode.Speed);
	    CANTalonRL.changeControlMode(ControlMode.Speed);
	    CANTalonFR.changeControlMode(ControlMode.Speed);
	    CANTalonRR.changeControlMode(ControlMode.Speed);
		
	    CANTalonFL.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	    CANTalonRL.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	    CANTalonFR.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	    CANTalonRR.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	    
        // Determine maximum velocity of our wheels
	    drive.setMaxOutput(20.0);

        // TODO: Set PIDs using PIDFManager...
	    // CANTalonFL.setPID(0.0, 0.0, 0.0, 0.0, 0, 0, 0);
		
	}

	// Use mecanum
	public void driveMecanum(double x, double y, double rotation, double gyroAngle) {
		drive.mecanumDrive_Cartesian(x, y, rotation, gyroAngle);
		updateSD();
	}
	
	// Refresh Smart Dashboard values
	public void updateSD() {
		if (debugging) {
			// Print data to smart dashboard
			SmartDashboard.putNumber("FL Encoder", CANTalonFL.getEncPosition());
			SmartDashboard.putNumber("RL Encoder", CANTalonRL.getEncPosition());
			SmartDashboard.putNumber("FR Encoder", CANTalonFR.getEncPosition());
			SmartDashboard.putNumber("RR Encoder", CANTalonRR.getEncPosition());
			
			// TODO: Add more CANTalon outputs...
		}
	}
	
}
