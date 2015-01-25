package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

public class elevatorCalibrate extends CommandBase {
	
	public elevatorCalibrate() {

	}
	
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
