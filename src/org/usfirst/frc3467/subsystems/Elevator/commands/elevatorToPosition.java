package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

/**
 *	Move elevator to a specified position under PID control
 *	(units = encoder counts)
 */
public class elevatorToPosition extends CommandBase {

	double	position;
	boolean isZeroed;
	
    public elevatorToPosition(double pos) {
    	requires(elevator);
    	setInterruptible(true);
    	position = pos;
    	if (position < 0) position = 0;	// Never go below zero
    }

    public elevatorToPosition(double pos, double timeout) {
		this(pos);
    	setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isZeroed = elevator.initPositionalMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (isZeroed)
    		elevator.gotoPosition(position);
    }

    /* 
     * This command will finish if:
     * 	- the elevator has not been zero'ed
     *  - it is at the setpoint (or within tolerance)
     *  - the command times out
     *  - and of course, if it is interrupted
     */
     protected boolean isFinished() {
    	if (!isZeroed || elevator.onTarget() || isTimedOut())
    		return true;
    	else
        	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
//    	elevator.disablePID();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
