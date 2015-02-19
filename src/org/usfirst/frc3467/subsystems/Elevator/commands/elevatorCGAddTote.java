package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class elevatorCGAddTote extends CommandGroup {
    

	
    public  elevatorCGAddTote() {
        
    	// Make sure Indexer is  engaged
    	addSequential(new indexerOperate(true));
    	
    	// Lift tote above Indexer
    	addSequential(new elevatorToPosition(Elevator.kLevelIndexTote));

    	// Lower conveyor back to bottom
    	addSequential(new elevatorToPosition(Elevator.kLevelZero));
    	
    	// TODO: Add to tote count in Elevator
        
    }
}
