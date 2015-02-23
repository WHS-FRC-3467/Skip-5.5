package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class elevatorCGAddTote extends CommandGroup {
    

	
    public  elevatorCGAddTote() {
        

    	// Make sure Indexer is  engaged
    	SmartDashboard.putString("elevatorCGAddTote", "indexerOperate(true)");
    	addSequential(new indexerOperate(true));
    	
    	// Wait a sec
    	SmartDashboard.putString("elevatorCGAddTote", "WaitCommand(1.0)");
    	addSequential(new WaitCommand(1.0));
    	
    	// Lift tote above Indexer
    	SmartDashboard.putString("elevatorCGAddTote", "elevatorToPosition(indexTote, 3.0)");
    	addSequential(new elevatorToPosition(Elevator.kLevelIndexTote, 3.0));

    	// Lower conveyor back to "resting position"
    	SmartDashboard.putString("elevatorCGAddTote", "elevatorToPosition(LevelZero, 3.0)");
    	addSequential(new elevatorToPosition(Elevator.kLevelZero, 3.0));
    	
    	// TODO: Add command to add 1 to tote count in Elevator
    }
}
