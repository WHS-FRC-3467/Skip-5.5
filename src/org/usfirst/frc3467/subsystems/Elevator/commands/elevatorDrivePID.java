package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;

/**
 *  Drive elevator under PID control by joystick
 *  When stopped, PID control will be used to hold position.
 */
public class elevatorDrivePID extends CommandBase {

	/* 
	 * This is the factor by which the joystick value (-1.0 -> + 1.0)
	 * is divided before being added to the setpoint.
	 * Modify it to change the maximum speed at which the elevator is driven.
	 */
	static final double kDeltaFactor = 20;
	
	double m_pidSetpoint;
	double m_positionDelta = 0;
	
    public elevatorDrivePID() {
    	requires(elevator);
    	setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    
    	elevator.initPositionalMode();

    	// Set setpoint to current encoder value
		m_pidSetpoint = elevator.getPosition();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

		m_positionDelta = -(oi.getGamepad().getRightStickY());

		if (m_positionDelta > -0.08 && m_positionDelta < 0.08) {
			// No change in position
			m_positionDelta = 0.0;
		}
		else
		{
			// add delta to old setpoint and give new setpoint to elevator
			m_pidSetpoint += m_positionDelta / kDeltaFactor;
			if (m_pidSetpoint < 0.0) m_pidSetpoint = 0.0;
			elevator.gotoPosition(m_pidSetpoint);					
		}
    }

    // This method will never return true; this command must always be interrupted.
     protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true or when interrupted
    protected void end() {
		// disable PID before ending
		elevator.disablePID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
