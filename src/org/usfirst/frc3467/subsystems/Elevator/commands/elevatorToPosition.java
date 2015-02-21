package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

/**
 *	Move elevator to a specified position (units = encoder counts)
 */
public class elevatorToPosition extends CommandBase {

	double	position;
	
    public elevatorToPosition(double pos) {
    	requires(elevator);
    	position = pos;
    	if (position < 0) position = 0;	// Never go below zero
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.initPositionalMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	elevator.gotoPosition(position);
    }

    // This command will never finish - it always must be interrupted.
    // The one exception is when it is at the very bottom (zero) position,
    // in which case it does not need to continue running.
    protected boolean isFinished() {
    	if (elevator.isZero())
    		return true;
    	else
        	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	elevator.disablePID();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
