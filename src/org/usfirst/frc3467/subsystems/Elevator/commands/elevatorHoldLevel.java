package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    	SmartDashboard.putNumber("elevatorHold", m_position);
    	if (m_position == -1)	// Bad level specified
    		return;

    	elevator.initPositionalMode();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (m_position == -1)	// Bad level specified
    		return;

    	elevator.gotoPosition(m_position);
    }

    // This command will never finish - it always must be interrupted.
    protected boolean isFinished() {
    	if (m_position == -1)	// Bad level specified
    		return true;
  
    	if (elevator.onTarget())
    		// Be sure to set the current level
    		elevator.setLevel(m_level);

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
