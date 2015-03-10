package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Conveyor;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class elevatorCGDropStackTopOfIndexerWithToteOnC extends CommandGroup {
    
    public  elevatorCGDropStackTopOfIndexerWithToteOnC() {

    	// Unengage Indexer
    	addSequential(new indexerOperate(false));
    	
    	// addParallel(new conveyorDrive(Conveyor.kIntakeHold + 0.15));
    	// Lift stack above Indexer
    	addSequential(new elevatorToPosition(Elevator.kLevelIndexToteTopOfIndexers - 2150, 2.0));

    	// Lower conveyor back to "resting position" in two steps: manual@fixed speed, then PID to hold
    	addSequential(new elevatorDriveToPosition((Elevator.kDown_Fixed - 0.1), Elevator.kLevelZero));
    	addSequential(new elevatorToPosition(Elevator.kLevelZero, 2.0));
    	//addSequential(new conveyorDrive(0));
    	
    }
}
