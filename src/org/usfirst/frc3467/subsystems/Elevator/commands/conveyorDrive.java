package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

public class conveyorDrive extends CommandBase {
	
	private double m_speed;
	
	public conveyorDrive(){
		requires(conveyor);
		m_speed = 0;
	}
	
	public conveyorDrive(double speed) {
		requires(conveyor);
		m_speed = speed;
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		double speed = 0;
    	
		if (m_speed == 0)
		{
			speed = (oi.getGamepad().getLeftStickX());

			// Deadband
			if (speed > -0.08 && speed < 0.08) speed = 0;

	        // Square the inputs (while preserving the sign) to increase
			// fine control while permitting full power
	        if (speed >= 0.0)
	            speed = (speed * speed);
	        else
	            speed = -(speed * speed);
	        

		}
		else
		{
			speed = m_speed;		
		}
		
		conveyor.driveManual(speed);
		
		
	}
	
	protected boolean isFinished() {
		// TODO: put current sensing code here to know when to stop
		return false;
	}
	
	protected void end() {
		conveyor.driveManual(0);
	}
	
	protected void interrupted() {
		end();
	}
	
}
