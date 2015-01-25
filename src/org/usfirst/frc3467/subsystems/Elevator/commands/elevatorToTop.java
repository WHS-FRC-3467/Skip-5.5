package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

public class elevatorToTop extends CommandBase {
	
	public elevatorToTop() {
		requires(elevator);
	}
	
	protected void initialize() {
		elevator.gotoTop();
	}
	
	protected void execute() {
		
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {
	}
	
	protected void interrupted() {
		end();
	}
	
}
