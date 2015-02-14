package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.subsystems.Elevator.Conveyor;

public class conveyorDrive extends CommandBase {
	
	private double m_speed;
	
	public conveyorDrive(double speed) {
		requires(conveyor);
		m_speed = speed;
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {

		conveyor.conveyorMotor.set(m_speed);
		
	}
	
	protected boolean isFinished() {
		// TODO: put current sensing code here to know when to stop
		return false;
	}
	
	protected void end() {
	}
	
	protected void interrupted() {
		end();
	}
	
}
