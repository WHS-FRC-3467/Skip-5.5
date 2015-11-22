package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Conveyor;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class elevatorCGDropStack extends CommandGroup {
    
    public  elevatorCGDropStack() {

    	
    	// Lift stack above Indexer
    	addParallel(new conveyorDrive(Conveyor.kIntakeHold));
    	addSequential(new elevatorToPosition(Elevator.kLevelIndexTote));
    	addSequential(new indexerOperate(0));
    	// Lower conveyor back to "resting position" in two steps: manual@fixed speed, then PID to hold
    	addSequential(new elevatorToPosition(Elevator.kLevelIndexTote - 160), 0.4);
    	addSequential(new elevatorToPosition(Elevator.kLevelZero + 160));
    	addSequential(new elevatorToPosition(Elevator.kLevelZero));
    	addSequential(new conveyorDrive(0), 0.05);
    	
    }
}
