package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.subsystems.Elevator.Conveyor;

import edu.wpi.first.wpilibj.Timer;

public class conveyorSettleLimit extends CommandBase {
	
	private boolean m_notHit;
	private boolean m_isSettled;
	private double m_timeLimit; // seconds
	private double m_startTime;
	
	public conveyorSettleLimit(){
		requires(conveyor);
		m_timeLimit = 0.25;
	}
	
	/**
	 * @param double timeLimit - in seconds
	 */
	public conveyorSettleLimit(double timeLimit) {
		requires(conveyor);
		m_timeLimit = timeLimit;
	}
	
	protected void initialize() {
		m_notHit = true;
		m_isSettled = false;
	}
	
	protected void execute() {
    	
		conveyor.driveManual(Conveyor.kIntakePickup);
		if (conveyor.getLimitSwitch()) {
			
			if (m_notHit) {
				// Start Timer
		        m_startTime = Timer.getFPGATimestamp();
				m_notHit = false;
			}
			else {
				// Check Timer
		        if ((Timer.getFPGATimestamp() - m_startTime) > m_timeLimit) {
					m_isSettled = true;

		        }
			}
			
		} else {
			
			// Start over
			m_notHit = true;
			
		}
		
	}
	
	protected boolean isFinished() {
		return m_isSettled;
	}
	
	protected void end() {
	}
	
	protected void interrupted() {
		end();
	}
	
}
