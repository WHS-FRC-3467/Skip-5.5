package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Conveyor;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class elevatorCGDropStackWithToteOnConveyor extends CommandGroup {
    
    public  elevatorCGDropStackWithToteOnConveyor() {


    	
    	// Lift stack above Indexer
    	addParallel(new conveyorDrive(Conveyor.kIntakeHold));
    	addSequential(new elevatorDriveToPosition(Elevator.kUp_FixedPlus + 0.5, Elevator.kLevelDropStackWithToteOnConveyor - 50));
    	addSequential(new elevatorToPosition(Elevator.kLevelDropStackWithToteOnConveyor));
    	// Unengage Indexer
    	addSequential(new indexerOperate(false));
    	
    	// Lower conveyor back to "resting position" in two steps: manual@fixed speed, then PID to hold
    	addSequential(new elevatorToPosition(Elevator.kLevelDropStackWithToteOnConveyor - 160));
    	addSequential(new elevatorDriveToPosition(Elevator.kDown_FixedPlus, Elevator.kLevelZero + 250));
    	addSequential(new elevatorToPosition(Elevator.kLevelZero));
    	addSequential(new conveyorDrive(0), 0.05);
    }
}
