package org.usfirst.frc3467.subsystems.DriveBase.commands;

import org.usfirst.frc3467.OI;
import org.usfirst.frc3467.commands.CommandBase;

public class DriveMecanum extends CommandBase {
	
	boolean m_useVoltage = false;
	
	public DriveMecanum(boolean useVoltage) {
		requires(drivebase);
		this.setInterruptible(true);
	
		m_useVoltage = useVoltage;
	}

	protected void initialize() {
		
		drivebase.initMecanum(m_useVoltage);
		
	}
	
	protected void execute() {

		double x, y, rotation;
		
		// Ken's Drive code
		/*
		 public static void driveFieldOrientedMecanum() {
		   	RobotMap.driveBaseRobotDrive41.mecanumDrive_Cartesian(-(OI.stick1.getX()), (OI.stick1.getY()),
		   			(OI.stick1.getZ())*Math.abs((OI.stick1.getZ())) / (2), -(gyro1.getAngle()) );
		 }
		 */
		 
		x = OI.driveJoystick.getX();
		y = OI.driveJoystick.getY();
		rotation = OI.driveJoystick.getZ();

		// Deadband
		if (x > -0.08 && x < 0.08) x = 0;
		if (y > -0.08 && y < 0.08) y = 0;
		if (rotation > -0.08 && rotation < 0.08) rotation = 0;

		drivebase.driveMecanum(x, y, rotation / 2.0, imu.getYaw());

	}
	
	protected boolean isFinished() {
		return false;
	}
	
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		end();
	}
	
}
