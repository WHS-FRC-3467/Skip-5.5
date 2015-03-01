package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class elevatorCGGoToTop extends CommandGroup {
    

	
    public  elevatorCGGoToTop() {
        
    	// Lift using PID to a position just short of the top
    	addSequential(new elevatorToPosition(Elevator.kLevelTop - 500, 3.0));

    	// Lift to top position in two steps: manual@fixed speed, then PID to hold
    	addSequential(new elevatorDriveToPosition(Elevator.kUp_FixedPlus, Elevator.kLevelTop));
    	addSequential(new elevatorToPosition(Elevator.kLevelTop));

    }
}
