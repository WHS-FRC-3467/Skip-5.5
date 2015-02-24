package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class elevatorCGDropStack extends CommandGroup {
    
    public  elevatorCGDropStack() {

    	// Lift stack above Indexer
    	SmartDashboard.putString("elevatorCGDropStack", "elevatorToPosition(indexTote, 3.0)");
    	addSequential(new elevatorToPosition(Elevator.kLevelIndexTote, 3.0));

    	// Unengage Indexer
    	SmartDashboard.putString("elevatorCGDropStack", "indexerOperate(false)");
    	addSequential(new indexerOperate(false));
    	
    	// Wait a couple secs
    	SmartDashboard.putString("elevatorCGDropStack", "WaitCommand(2.0)");
    	addSequential(new WaitCommand(2.0));
    	
    	// Lower conveyor back to "resting position" in two steps: manual@fixed speed, then PID to hold
    	SmartDashboard.putString("elevatorCGDropStack", "elevatorDriveToPosition(LevelZero, kDown_Fixed)");
    	addSequential(new elevatorDriveToPosition(Elevator.kDown_Fixed, Elevator.kLevelZero));
    	
    	SmartDashboard.putString("elevatorCGDropStack", "elevatorToPosition(LevelZero, 3.0)");
    	addSequential(new elevatorToPosition(Elevator.kLevelZero));
    	
    }
}
