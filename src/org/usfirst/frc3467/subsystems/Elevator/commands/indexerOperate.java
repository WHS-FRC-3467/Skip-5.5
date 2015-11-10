package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

/**
 *
 */
public class indexerOperate extends CommandBase {

	private int cmd;
	
	public indexerOperate(int engageMode) {
        requires(indexer);
        cmd = engageMode;
        		
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (cmd == 2){
    		if(CommandBase.indexer.getEngagedStatus()){
    			indexer.engageCrushMode();
    			System.out.println("Crush Mode");
    		}
    		if(!CommandBase.indexer.getEngagedStatus()){
    			indexer.engageIndexer();
    			System.out.println("Engaged");
    		}
    		}
    		if(cmd == 1){
    			indexer.engageIndexer();
    			System.out.println("Engaged");
    		}
    		if(cmd == 0){
    		indexer.disengageIndexer();    
    		System.out.println("Disengaged");
    	}
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
