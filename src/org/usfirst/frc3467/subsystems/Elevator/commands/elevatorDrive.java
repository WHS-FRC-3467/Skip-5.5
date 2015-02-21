package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;

/**
 *  Drive elevator manually, either by joystick or by fixed rate.
 */
public class elevatorDrive extends CommandBase {

	double fixedSpeed = 0;
	
    public elevatorDrive() {
    	requires(elevator);
    	fixedSpeed = 0;
    }

    public elevatorDrive(double fSpeed) {
    	requires(elevator);
    	fixedSpeed = fSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.initManualMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = 0;
    	
    	if (fixedSpeed == 0) {
			speed = -((oi.getGamepad().getRightStickY())/2.5);
			if (speed > -0.08 && speed < 0.08)
				speed = 0.0;
    	}
    	else
			speed = fixedSpeed;

    	elevator.driveManual(speed);
 
    }

    // This method will never return true; this command must always be interrupted.
     protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	elevator.driveManual(Elevator.kStop);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
