package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Conveyor;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class elevatorCGAddTote extends CommandGroup {
    

	
    public  elevatorCGAddTote() {
        

    	// Make sure Indexer is  engaged
    	addSequential(new indexerOperate(1));
    	

    	// Lift tote above Indexer (and drive conveyor in at same time)
    	addParallel(new conveyorDrive(Conveyor.kIntakeHold));
 
    	addSequential(new elevatorDriveToPosition(Elevator.kUp_FixedPlus + 0.7, Elevator.kLevelIndexTote - 3150));
    	addSequential(new elevatorDriveToPosition(Elevator.kUp_FixedPlus + 0.5, Elevator.kLevelIndexTote - 150));
    	addSequential(new elevatorToPosition(Elevator.kLevelIndexTote - 150));
    	
    	// Lower conveyor back to "resting position" in two steps: manual@fixed speed, then PID to hold
    	addSequential(new elevatorDriveToPosition(Elevator.kDown_Fixed - 0.2, Elevator.kLevelIndexTote - 1500));
    	addSequential(new elevatorDriveToPosition(Elevator.kDown_Fixed - 0.8, Elevator.kLevelZero + 500));
    	addSequential(new elevatorToPosition(Elevator.kLevelZero));

    	// Turn off conveyor
    	addSequential(new conveyorDrive(Conveyor.kStop), 0.01);

    	// Lower all the way to floor to be ready to get next tote
    	addSequential(new elevatorDriveToFloor());
    	
    	// TODO: Add command to add 1 to tote count in Elevator
    }
}
