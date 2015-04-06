package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.subsystems.Elevator.CanGrabbers;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class cangrabbersSetState extends CommandBase {
 public static int m_state;
    public cangrabbersSetState(int state) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(cangrabbers);
    	state = m_state;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	cangrabbers.setState(m_state);
    	this.end();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
