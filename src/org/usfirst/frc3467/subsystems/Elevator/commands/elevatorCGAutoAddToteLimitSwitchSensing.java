package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.subsystems.Elevator.Conveyor;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class elevatorCGAutoAddToteLimitSwitchSensing extends CommandGroup {
    
    public elevatorCGAutoAddToteLimitSwitchSensing() {
    	
    	addSequential(new conveyorSettleLimit());
       	// Make sure Indexer is  engaged
    	addSequential(new indexerOperate(true));
    	

    	// Lift tote above Indexer (and drive conveyor in at same time)
    	addParallel(new conveyorDrive(Conveyor.kIntakeHold));
 
    	addSequential(new elevatorDriveToPosition(Elevator.kUp_FixedPlus + 0.6, Elevator.kLevelIndexTote - 90));
    	addSequential(new elevatorToPosition(Elevator.kLevelIndexTote));
    	
    	// Lower conveyor back to "resting position" in two steps: manual@fixed speed, then PID to hold
    	addSequential(new elevatorDriveToPosition(Elevator.kDown_Fixed - 0.25, Elevator.kLevelIndexTote - 900));
    	addSequential(new elevatorDriveToPosition(Elevator.kDown_Fixed - 0.55, Elevator.kLevelZero + 100));   	
    	addSequential(new elevatorToPosition(Elevator.kLevelZero));

    	// Turn off conveyor
    	addSequential(new conveyorDrive(Conveyor.kStop), 0.01);

    	// Lower all the way to floor to be ready to get next tote
    	addSequential(new elevatorDriveToFloor());

    }


}
