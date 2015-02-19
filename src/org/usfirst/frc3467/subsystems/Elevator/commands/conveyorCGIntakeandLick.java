package org.usfirst.frc3467.subsystems.Elevator.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc3467.commands.CommandBase;

/**
 *
 */
public class conveyorCGIntakeandLick extends CommandGroup {
    
    public  conveyorCGIntakeandLick() {
    	requires(CommandBase.conveyor);
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	addParallel(new conveyorDrive(0.25), 3);
    	addSequential(new flippyThingFlip(0.3, -0.3));
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
