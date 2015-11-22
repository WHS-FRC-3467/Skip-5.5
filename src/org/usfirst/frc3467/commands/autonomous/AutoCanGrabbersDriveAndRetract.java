package org.usfirst.frc3467.commands.autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.commands.DriveTimedTank;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;
import org.usfirst.frc3467.subsystems.Elevator.commands.cangrabbersSetState;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDriveToFloor;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorToPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCanGrabbersDriveAndRetract extends CommandGroup {
	
	
	public AutoCanGrabbersDriveAndRetract() {
		

		addSequential(new cangrabbersSetState(true), 0.65);
		addParallel(new elevatorToPosition(1100));
		addSequential(new DriveTimedTank(0.3, 0.3));
		addSequential(new DriveTimedTank(1.2, 1.15));
		addParallel(new cangrabbersSetState(false));
		addSequential(new DriveTimedTank(1.6, 0.3));
	}
}
