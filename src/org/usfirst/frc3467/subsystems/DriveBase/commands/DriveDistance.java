package org.usfirst.frc3467.subsystems.DriveBase.commands;

import org.usfirst.frc3467.commands.CommandBase;

public class DriveDistance extends CommandBase {
	
	double m_distance;
	
	public DriveDistance(double distance) {
		requires(drivebase);
		this.setInterruptible(true);
		
		m_distance = distance;

// Set Timeout as a function of distance to be driven - this will make sure we have time to 
//		go the distance before timing out
//		setTimeout(2 * distance);
	
	}

	protected void initialize() {
		
		drivebase.initDistance(m_distance);
		
	}
	
	protected void execute() {

		drivebase.driveDistance();
	}
	
	protected boolean isFinished() {
		return (drivebase.onPosition() || isTimedOut());
	}
	
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		end();
	}
	
}
