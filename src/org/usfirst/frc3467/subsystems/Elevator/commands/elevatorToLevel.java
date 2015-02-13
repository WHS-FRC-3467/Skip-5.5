package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

/**
 *
 */
public class elevatorToLevel extends CommandBase {

	int	m_level;
	
    public elevatorToLevel(int level) {
    	requires(elevator);
    	m_level = level;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.initPositionalMode();
//		setTimeout(2);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	elevator.gotoLevel(m_level);
    }

    // Make this return true when this Command no longer needs to run execute()
    // When this command is finished, the default command (elevatorDrive) will take
    // over and switch the CANTalon to PercentVBus mode.
    protected boolean isFinished() {
    	return (elevator.onTarget() || isTimedOut());
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
