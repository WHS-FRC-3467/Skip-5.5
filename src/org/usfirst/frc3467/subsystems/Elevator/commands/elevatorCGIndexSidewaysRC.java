package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class elevatorCGIndexSidewaysRC extends CommandGroup {
    

	
    public  elevatorCGIndexSidewaysRC() {
        

    	// Make sure Indexer is  engaged
    	SmartDashboard.putString("elevatorCGIndexSidewaysRC", "indexerOperate(true)");
    	addSequential(new indexerOperate(true));
    	
    	// Wait half a sec
    	SmartDashboard.putString("elevatorCGIndexSidewaysRC", "WaitCommand(0.5)");
    	addSequential(new WaitCommand(0.5));
    	
    	// Lift RC above Indexer (and drive conveyor in at same time)
    	SmartDashboard.putString("elevatorCGIndexSidewaysRC", "elevatorToPosition(kLevelIndexUprightRC, 2.0)");
    	addParallel(new conveyorDrive(-0.25));
    	addSequential(new elevatorToPosition(Elevator.kLevelIndexSidewaysRC, 2.0));

    	// Lower conveyor back to "resting position" in two steps: manual@fixed speed, then PID to hold
    	SmartDashboard.putString("elevatorCGIndexSidewaysRC", "elevatorDriveToPosition(LevelZero, kDown_Fixed - 0.1)");
    	addSequential(new elevatorDriveToPosition(Elevator.kDown_Fixed - 0.1, Elevator.kLevelZero));
    	
    	SmartDashboard.putString("elevatorCGIndexSidewaysRC", "elevatorToPosition(LevelZero, 2.0)");
    	addSequential(new elevatorToPosition(Elevator.kLevelZero, 2.0));

    	// Turn off conveyor
    	addSequential(new conveyorDrive(0.0));

    	// TODO: Add command to add 1 to tote count in Elevator
    }
}
