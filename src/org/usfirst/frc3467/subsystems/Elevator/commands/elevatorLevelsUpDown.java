package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

/**
 *	Drive up or down to next level and stay there until interrupted
 */
public class elevatorLevelsUpDown extends CommandBase {

	boolean m_elevUp;
	double m_position;
	
    public elevatorLevelsUpDown(boolean moveUp) {
       	requires(elevator);
        
       	//TRUE = move Up one tote level. If false go down 1 level
    	m_elevUp = moveUp;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.initPositionalMode();
    	
    	if(m_elevUp == true){
    		m_position = elevator.getPositionForOneLevelUp();
    	}
    	else
    	{
    		m_position = elevator.getPositionForOneLevelDown();
    	}

    	if (m_position == -1)	// Already at top/bottom or Bad level
    		return;

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (m_position == -1)	// Already at top/bottom or Bad level
    		return;

    	elevator.gotoPosition(m_position);
    }

    // This command will never finish - it always must be interrupted.
    // Exceptions are: already at top or bottom, or out-of-range level passed in
    protected boolean isFinished() {
    	if (m_position == -1)	// Already at top/bottom or Bad level
    		return true;
    	
    	if (elevator.onTarget()) {
    		// Be sure to set the current level
        	if(m_elevUp == true)
        		elevator.levelUp();
        	else
        		elevator.levelDown();
    	}
    	
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
