package org.usfirst.frc3467.subsystems.DriveBase.commands;

import org.usfirst.frc3467.commands.CommandBase;

public class DriveTimedTank extends CommandBase {
	
	double m_speed;
	
	public DriveTimedTank(double time, double speed) {
		requires(drivebase);
		this.setInterruptible(true);
		
		m_speed = speed;
		setTimeout(time);
	}

	protected void initialize() {
		
		drivebase.initTank();
	}
	
	protected void execute() {

		drivebase.driveTank(m_speed, m_speed);
	}
	
	protected boolean isFinished() {
		return (isTimedOut());
	}
	
	
	protected void end() {
		
		drivebase.driveTank(0.0,  0.0);
	}
	
	protected void interrupted() {
		end();
	}
	
}
