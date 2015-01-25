package org.usfirst.frc3467.subsystems.Elevator;

import org.usfirst.frc3467.RobotMap;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDrive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {

	private CANTalon winchMotor1;
	private CANTalon winchMotor2;
	
	private static double topPosition = 0;
	
	private static final boolean debugging = true;
	
	private static double Kp = 0.00;
	private static double Ki = 0.00;
	private static double Kd = 0.00;
	
	private static Elevator instance;

	// Constants for some useful speeds
	public static final double UP_FIXED = 0.2;
	public static final double STOP = 0;
	public static final double DOWN_FIXED = -0.2;
	
	public static Elevator getInstance() {
		return instance;
	}
	
	public Elevator() {
		instance = this;
		
		winchMotor1 = new CANTalon(RobotMap.winchDriveCANTalon);
		winchMotor2 = new CANTalon(RobotMap.winchSlaveCANTalon);
		
	    winchMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	    winchMotor1.setSafetyEnabled(true);
	    winchMotor1.setExpiration(0.1);
	    winchMotor1.setPID(Kp, Ki, Kd);
		
		winchMotor2.changeControlMode(ControlMode.Follower);
		winchMotor2.set(RobotMap.winchDriveCANTalon);
		
	}
	
	protected void initDefaultCommand() {
		this.setDefaultCommand(new elevatorDrive());
	}
	
	public void zeroEncoder() {
		winchMotor1.setPosition(0);
	}
	
	public void initManualMode() {
		winchMotor1.changeControlMode(ControlMode.PercentVbus);
		
	}
	
	public void driveManual(double speed) {
		winchMotor1.set(speed);
	
	}
	
	public void initPositionalMode() {
		winchMotor1.changeControlMode(ControlMode.Position);
		
	}
	
	public void gotoPosition(double position) {
		winchMotor1.set(position);		

	}
	
	public void gotoTop() {
		winchMotor1.set(topPosition);		

	}
	
	public double getPosition() {
		return winchMotor1.getPosition();				
	}
	
	public void calibrate() {
		
		// goto bottom until switch is hit
		
		// zero encoder count
		winchMotor1.setPosition(0.0);
		
		// goto top until switch is hit
		
		// record encoder count
		//topPosition = winchMotor1.getPosition();
	}
	
}
