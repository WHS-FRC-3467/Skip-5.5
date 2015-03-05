package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Conveyor;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class elevatorCGAddToteTopOfIndexer extends CommandGroup {
    

	
    public  elevatorCGAddToteTopOfIndexer() {
        

    	// Make sure Indexer is  engaged
    	addSequential(new indexerOperate(true));
    	

    	
    	// Lift tote above Indexer (and drive conveyor in at same time)
    	addParallel(new conveyorDrive(Conveyor.kIntakeHold));
    	addSequential(new elevatorToPosition(Elevator.kLevelIndexToteTopOfIndexers, 2.0));

    	// Lower conveyor back to "resting position" in two steps: manual@fixed speed, then PID to hold
    	addSequential(new elevatorDriveToPosition(Elevator.kDown_Fixed, Elevator.kLevelTwo));    	
    	addSequential(new elevatorToPosition(Elevator.kLevelZero, 1.0));

    	// Turn off conveyor
    	addSequential(new conveyorDrive(Conveyor.kStop));

    	// Lower all the way to floor to be ready to get next tote
    	addSequential(new elevatorDriveToFloor());
    	
    	// TODO: Add command to add 1 to tote count in Elevator
    }
}
