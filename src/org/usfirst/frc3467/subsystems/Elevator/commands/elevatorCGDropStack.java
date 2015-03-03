package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class elevatorCGDropStack extends CommandGroup {
    
    public  elevatorCGDropStack() {

    	// Unengage Indexer
    	addSequential(new indexerOperate(false));
    	
    	// Lift stack above Indexer
    	addSequential(new elevatorToPosition(Elevator.kLevelIndexTote, 2.0));

    	// Lower conveyor back to "resting position" in two steps: manual@fixed speed, then PID to hold
    	addSequential(new elevatorDriveToPosition((Elevator.kDown_Fixed - 0.1), Elevator.kLevelZero));
    	addSequential(new elevatorToPosition(Elevator.kLevelZero));
    	
    }
}
