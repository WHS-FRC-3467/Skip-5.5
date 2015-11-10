package org.usfirst.frc3467.commands.autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.commands.DriveTimedTank;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDriveToFloor;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorToPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoTimedTank extends CommandGroup {
	
	
	public AutoTimedTank() {
		

		//DriveTimedTank (time (sec), speed (-1 -> +1))
		addSequential(new DriveTimedTank(.5, 0.2));
		addSequential(new DriveTimedTank(.5, 0.5));
		addSequential(new DriveTimedTank(3.6, 0.6));
		addSequential(new DriveTimedTank(1.0, 0.3));
	}
}
