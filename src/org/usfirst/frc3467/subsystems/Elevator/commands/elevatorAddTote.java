package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class elevatorAddTote extends CommandGroup {
    

	
    public  elevatorAddTote() {
        
    	// Make sure Indexer is  engaged
    	addSequential(new indexerOperate(true));
    	
    	// Lift tote above Indexer
    	addSequential(new elevatorToLevel(Elevator.kLevelIndexTote));

    	// Lower conveyor back to bottom
    	addSequential(new elevatorToLevel(Elevator.kLevelZero));
    	
    	// TODO: Add to tote count in Elevator
        
    }
}
