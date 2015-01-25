package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;


/**
 *
 */
public class elevatorPosition extends CommandBase {

	double	position;
	private final double TOLERANCE = 5;
	
    public elevatorPosition(double pos) {
    	requires(elevator);
    	position = pos;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.initPositionalMode();
//		setTimeout(2);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	elevator.gotoPosition(position);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Math.abs(position - elevator.getPosition()) <= TOLERANCE) || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
