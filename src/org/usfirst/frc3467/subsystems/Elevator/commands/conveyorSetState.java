package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.subsystems.Elevator.Conveyor;

public class conveyorSetState extends CommandBase {
	
	private double m_power;
	private Conveyor.ConveyorState m_state;
	
	public conveyorSetState(Conveyor.ConveyorState request, double power) {
		requires(conveyor);
		
		m_state = request;
		m_power = power;
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {

		conveyor.setState(m_state, m_power);

	}
	
	protected boolean isFinished() {
		// TODO: put current sensing code here to know when to stop
		return false;
	}
	
	protected void end() {
		m_state = Conveyor.ConveyorState.kOff;
		conveyor.setState(m_state, 0.0);
	}
	
	protected void interrupted() {
		end();
	}
	
}
