package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;

/**
 *
 */
public class elevatorDrive extends CommandBase {

	boolean isFixedSpeed = false;
	
    public elevatorDrive() {
    	requires(elevator);
    }

    public elevatorDrive(boolean isFixed) {
    	requires(elevator);
    	isFixedSpeed = isFixed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.initManualMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (isFixedSpeed)
    	{
    		if (oi.getGamepad().getDpadUp())
    			elevator.driveManual(Elevator.UP_FIXED);
    		else if (oi.getGamepad().getDpadDown())
    			elevator.driveManual(Elevator.DOWN_FIXED);
    		else
    			elevator.driveManual(Elevator.STOP);
    	}
    	else
    		elevator.driveManual(-(oi.getGamepad().getLeftStickY()));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	elevator.driveManual(Elevator.STOP);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
