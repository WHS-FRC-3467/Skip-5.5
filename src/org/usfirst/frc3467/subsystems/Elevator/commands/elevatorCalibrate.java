package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

public class elevatorCalibrate extends CommandBase {
	
	public elevatorCalibrate() {

	}
	
/*
 * 	This really needs to be a CommandGroup:

		1.	Either position elevator manually at bottom or 
			goto bottom until switch is hit
		
		2.	Zero encoder count
		
*/	
	
	protected void initialize() {
		elevator.zeroEncoder();
	}
	
	protected void execute() {
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		
	}
	
}
