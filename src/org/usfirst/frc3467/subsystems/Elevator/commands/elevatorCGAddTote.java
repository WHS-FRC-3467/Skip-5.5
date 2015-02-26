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
    	addSequential(new indexerOperate(true));
    	
    	// Wait half a sec
    	addSequential(new WaitCommand(0.5));
    	
    	// Lift tote above Indexer (and drive conveyor in at same time)
    	addParallel(new conveyorDrive(Conveyor.kIntakeHold));
    	addSequential(new elevatorToPosition(Elevator.kLevelIndexTote, 2.0));

    	// Lower conveyor back to "resting position" in two steps: manual@fixed speed, then PID to hold
    	addSequential(new elevatorDriveToPosition(Elevator.kDown_Fixed, Elevator.kLevelOne));
    	
    	addSequential(new elevatorToPosition(Elevator.kLevelZero, 3.0));

    	// Turn off conveyor
    	addSequential(new conveyorDrive(Conveyor.kStop));

    	// TODO: Add command to add 1 to tote count in Elevator
    }
}
