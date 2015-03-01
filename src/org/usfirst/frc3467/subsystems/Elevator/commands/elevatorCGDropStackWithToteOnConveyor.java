package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class elevatorCGDropStackWithToteOnConveyor extends CommandGroup {
    
    public  elevatorCGDropStackWithToteOnConveyor() {

    	// Lift stack above Indexer
    	addSequential(new elevatorToPosition(Elevator.kLevelDropStackWithToteOnConveyor, 3.0));

    	// Unengage Indexer
    	addSequential(new indexerOperate(false));
    	
    	// Wait a couple secs
    	addSequential(new WaitCommand(2.0));
    	
    	// Lower conveyor back to "resting position" in two steps: manual@fixed speed, then PID to hold
    	addSequential(new elevatorDriveToPosition(Elevator.kDown_FixedPlus, Elevator.kLevelZero));
    	addSequential(new elevatorToPosition(Elevator.kLevelZero));
    	
    }
}
