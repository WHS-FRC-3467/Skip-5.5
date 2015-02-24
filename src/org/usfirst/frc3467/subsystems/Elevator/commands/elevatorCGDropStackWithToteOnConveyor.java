package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class elevatorCGDropStackWithToteOnConveyor extends CommandGroup {
    
    public  elevatorCGDropStackWithToteOnConveyor() {

    	// Lift stack above Indexer
    	SmartDashboard.putString("elevatorCGDropStack", "elevatorToPosition(LevelOne, 3.0)");
    	addSequential(new elevatorToPosition(Elevator.kLevelDropStackWithToteOnConveyor , 3.0));

    	// Unengage Indexer
    	SmartDashboard.putString("elevatorCGDropStack", "indexerOperate(false)");
    	addSequential(new indexerOperate(false));
    	
    	// Wait a sec
    	SmartDashboard.putString("elevatorCGDropStack", "WaitCommand(2.0)");
    	addSequential(new WaitCommand(2.0));
    	
    	// Lower conveyor back to "resting position" in two steps: manual@fixed speed, then PID to hold
    	SmartDashboard.putString("elevatorCGDropStack", "elevatorDriveToPosition(LevelZero, kDown_Fixed - 0.1)");
    	addSequential(new elevatorDriveToPosition(Elevator.kDown_Fixed - 0.1, Elevator.kLevelZero));
    	
    	SmartDashboard.putString("elevatorCGDropStack", "elevatorToPosition(LevelZero, 3.0)");
    	addSequential(new elevatorToPosition(Elevator.kLevelZero));
    	
    }
}
