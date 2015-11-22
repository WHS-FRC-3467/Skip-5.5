package org.usfirst.frc3467.subsystems.Elevator;

import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.subsystems.Elevator.commands.AlwaysOnWatcherCommand;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AlwaysOnLoop extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new AlwaysOnWatcherCommand());
    }
    
    public void thisVoidIsAlwaysRunning(){
    	if((CommandBase.elevator.getPosition() > 6650) && (CommandBase.elevator.getPosition() < 8000)){
    		CommandBase.indexer.disengageIndexer();
    		CommandBase.indexer.disable(true);
    	}
    	else{
    		CommandBase.indexer.disable(false);
    	}
    }
}


