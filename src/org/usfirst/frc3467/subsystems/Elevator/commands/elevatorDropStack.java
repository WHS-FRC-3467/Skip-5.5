package org.usfirst.frc3467.subsystems.Elevator.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class elevatorDropStack extends CommandGroup {
    
    public  elevatorDropStack() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(new elevatorToLevel(1800));
    	addSequential(new indexerOperate(false));
    	addSequential(new elevatorToLevel(0));
        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
