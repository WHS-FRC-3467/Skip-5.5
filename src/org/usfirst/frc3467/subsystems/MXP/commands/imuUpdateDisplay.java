package org.usfirst.frc3467.subsystems.MXP.commands;

import org.usfirst.frc3467.commands.CommandBase;


/**
 *
 */
public class imuUpdateDisplay extends CommandBase {

    public imuUpdateDisplay() {
        requires(imu);
//		this.setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	imu.update();
    	
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
    	end();
    }
}
