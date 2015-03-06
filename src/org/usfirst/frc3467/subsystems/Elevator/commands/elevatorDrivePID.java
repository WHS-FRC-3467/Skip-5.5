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
	 * is multiplied before being added to the setpoint.
	 * Modify it to change the maximum speed at which the elevator is driven.
	 * Know that the bigger the number, the faster the elevator.
	 */
	static final double kDeltaFactor = 80;
	
	double m_pidSetpoint;
	double m_positionDelta = 0;
	
    public elevatorDrivePID() {
    	requires(elevator);
    	setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    
    	elevator.initPositionalMode();

    	// Get current elevator setpoint
		m_pidSetpoint = elevator.getElevatorSetpoint();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

		m_positionDelta = -(oi.getGamepad().getLeftStickY());

		if (m_positionDelta < -0.08 || m_positionDelta > 0.08) {

			// If outside deadband, add delta to old setpoint
			m_pidSetpoint += m_positionDelta * kDeltaFactor;
			if (m_pidSetpoint < 0.0) m_pidSetpoint = 0.0;
		}

		//  Give new setpoint to elevator
		elevator.gotoPosition(m_pidSetpoint);							
		
    }

    // This method will never return true; this command must always be interrupted.
     protected boolean isFinished() {
    	 elevator.reportPosition();
        return false;
    }

    // Called once after isFinished returns true or when interrupted
    protected void end() {
		// disable PID before ending
		// elevator.disablePID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
