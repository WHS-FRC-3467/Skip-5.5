package org.usfirst.frc3467.subsystems.LEDs;

import org.usfirst.frc3467.commands.CommandBase;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunLeds extends CommandBase {

    public RunLeds() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(leds);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leds.start();
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
