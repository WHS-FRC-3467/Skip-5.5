package org.usfirst.frc3467.commands.autonomous;

import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDriveToFloor;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoNon extends CommandGroup {
	
	public AutoNon() {
		addSequential(new elevatorDriveToFloor());
	}

}
