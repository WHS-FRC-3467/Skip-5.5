package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

/**
 *
 */
public class indexerOperate extends CommandBase {

	private Boolean cmd;
	
	public indexerOperate(Boolean engageCmd) {
        requires(indexer);
        cmd = engageCmd;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (cmd == true)
    		indexer.engageIndexer();
    	else
    		indexer.disengageIndexer();    	
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
