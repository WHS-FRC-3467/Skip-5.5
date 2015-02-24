package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class elevatorCGIndexUprightRC extends CommandGroup {
    

	
    public  elevatorCGIndexUprightRC() {
        

    	// Make sure Indexer is  engaged
    	SmartDashboard.putString("elevatorCGIndexUprightRC", "indexerOperate(true)");
    	addSequential(new indexerOperate(true));
    	
    	// Wait half a sec
    	SmartDashboard.putString("elevatorCGIndexUprightRC", "WaitCommand(0.5)");
    	addSequential(new WaitCommand(0.5));
    	
    	// Lift RC above Indexer (and drive conveyor in at same time)
    	SmartDashboard.putString("elevatorCGIndexUprightRC", "elevatorToPosition(kLevelIndexUprightRC, 2.0)");
    	addParallel(new conveyorDrive(-0.25));
    	addSequential(new elevatorToPosition(Elevator.kLevelIndexUprightRC, 2.0));

    	// Lower conveyor back to "resting position" in two steps: manual@fixed speed, then PID to hold
    	SmartDashboard.putString("elevatorCGIndexUprightRC", "elevatorDriveToPosition(LevelZero, kDown_Fixed - 0.1)");
    	addSequential(new elevatorDriveToPosition(Elevator.kDown_Fixed - 0.1, Elevator.kLevelZero));
    	
    	SmartDashboard.putString("elevatorCGIndexUprightRC", "elevatorToPosition(LevelZero, 2.0)");
    	addSequential(new elevatorToPosition(Elevator.kLevelZero, 2.0));

    	// Turn off conveyor
    	addSequential(new conveyorDrive(0.0));

    	// TODO: Add command to add 1 to tote count in Elevator
    }
}
