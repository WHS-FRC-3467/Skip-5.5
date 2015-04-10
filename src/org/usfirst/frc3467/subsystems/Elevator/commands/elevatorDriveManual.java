package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;

/**
 *  Drive elevator manually, either by joystick or by fixed rate.
 *  When stopped, PID control will be used to hold position.
 */
public class elevatorDriveManual extends CommandBase {

	double m_fixedSpeed;
	
    public elevatorDriveManual() {
    	requires(elevator);
    	setInterruptible(true);
    	m_fixedSpeed = 0;
    }

    public elevatorDriveManual(double fSpeed) {
    	requires(elevator);
    	m_fixedSpeed = fSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.initManualMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = 0;
    	
    	if (m_fixedSpeed == 0) {
			speed = -((oi.getGamepad().getLeftStickY())/1.5);
	        if (speed > 0.0)
	            speed = (speed * speed);
	        else
	            speed = -(speed * speed);
	        //deadband
			if (speed > -0.08 && speed < 0.08)
				speed = 0.0;
    	}
    	else
			speed = m_fixedSpeed;
    	
    	elevator.driveManual(speed);
 
    }

    // This method will never return true; this command must always be interrupted.
     protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true or when interrupted
    protected void end() {

    	elevator.driveManual(Elevator.kStop);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
