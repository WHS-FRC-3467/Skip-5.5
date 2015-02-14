package org.usfirst.frc3467.subsystems.DriveBase.commands;

import org.usfirst.frc3467.commands.CommandBase;

public class DriveTurnToAngle extends CommandBase {
	
	double m_target;
	
	public DriveTurnToAngle(double targetangle) {
		requires(drivebase);
		this.setInterruptible(true);
//		setTimeout(2);
		
		m_target = targetangle;
	}

	protected void initialize() {
		
		// Use standard mecanum drive
		drivebase.initMecanum();
		
		/*
		 *  Enable drivebase rotational PID
		 */
		// Set target angle
		drivebase.setSetpoint(m_target);
		
		// Throttle down the max allowed speed
		drivebase.setOutputRange(-0.5, 0.5);
		
		// Set tolerance around the target angle
		drivebase.setAbsoluteTolerance(5);
		
		// Enable PID controller
		drivebase.enable();
	
	}
	
	protected void execute() {

	}
	
	protected boolean isFinished() {
    	
		if (drivebase.onTarget() || isTimedOut())
		{
			// Disable PID controller
			drivebase.disable();
			return true;
		}
		else
			return false;
	}
	
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		end();
	}
	
}
