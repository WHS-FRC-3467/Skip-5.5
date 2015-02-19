package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

/**
 *	Drive to an elevator level and stay there until interrupted
 */
public class elevatorHoldLevel extends CommandBase {

	int	m_level;
	double m_position;
	
    public elevatorHoldLevel(int level) {
    	requires(elevator);
    	this.setInterruptible(true);
    	m_level = level;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_position = elevator.getPositionForLevel(m_level);
    	if (m_position == -1)	// Bad level specified
    		return;

    	elevator.initPositionalMode();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (m_position == -1)	// Bad level specified
    		return;

    	elevator.gotoPosition(m_level);
    }

    // This command will never finish - it always must be interrupted.
    // The one exception is when it is at the very bottom (zero) position,
    // in which case it does not need to continue running.
    protected boolean isFinished() {
    	if (m_position == -1)	// Bad level specified
    		return true;
  
    	if (elevator.onTarget())
    		// Be sure to set the current level
    		elevator.setLevel(m_level);

    	if (elevator.isZero())
    		return true;
    	else
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
