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

    public elevatorToPosition(double pos, double timeout) {
		this(pos);
    	setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.initPositionalMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	elevator.gotoPosition(position);
    }

    // Usually this command will never finish - it always must be interrupted.
    // Exception - if a timeout is set
    protected boolean isFinished() {
    	if (isTimedOut())
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
