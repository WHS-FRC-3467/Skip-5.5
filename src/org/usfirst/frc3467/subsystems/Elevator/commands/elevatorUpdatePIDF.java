package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

/**
 *
 */
public class elevatorUpdatePIDF extends CommandBase {

    public elevatorUpdatePIDF() {
    	requires(elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.updatePIDFfromSDB();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
