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
    	if((CommandBase.elevator.getPosition() > 5500) && (CommandBase.elevator.getPosition() < 6700)){
    		CommandBase.indexer.disengageIndexer();
    		CommandBase.indexer.disabled = true;
    	}
    	else{
    		CommandBase.indexer.disabled = false;
    	}
    }
}


