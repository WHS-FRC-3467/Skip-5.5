package org.usfirst.frc3467.subsystems.MXP.commands;

import org.usfirst.frc3467.commands.CommandBase;


/**
 *
 */
public class imuUpdateDisplay extends CommandBase {

    private int counter;
    
	public imuUpdateDisplay() {
        requires(imu);
		this.setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	counter = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	// Only run the update on every 50th pass (about once per second)
    	if (counter < 50)
    		counter++;
    	else {
        	imu.update();
        	counter = 0;
    	}
    	
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
