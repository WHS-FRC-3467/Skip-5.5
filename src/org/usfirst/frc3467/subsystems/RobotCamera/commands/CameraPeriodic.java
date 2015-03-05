package org.usfirst.frc3467.subsystems.RobotCamera.commands;

import org.usfirst.frc3467.commands.CommandBase;

/**
 *
 */
public class CameraPeriodic extends CommandBase {

    int 			m_loopCount = 0;
    final	int		m_updateCount = 4;	// Number of calls to skip before updating frame
    
    public CameraPeriodic() {
    	requires(usbcamera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

		if (m_loopCount > m_updateCount) {
			usbcamera.setImage();
			m_loopCount = 0;

		}
		m_loopCount++;
    }
        
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	usbcamera.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
