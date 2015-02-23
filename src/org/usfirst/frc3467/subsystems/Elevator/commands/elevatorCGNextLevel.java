package org.usfirst.frc3467.subsystems.Elevator.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class elevatorCGNextLevel extends CommandGroup {
    
    public  elevatorCGNextLevel(boolean moveUp) {

    	// Need to run an intervening command so we can repeat the LevelsUpDown command
    	addSequential(new elevatorNull());
    	
    	addSequential(new elevatorLevelsUpDown(moveUp)); 

    }
}
