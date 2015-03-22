package org.usfirst.frc3467.commands.autonomous;

import org.usfirst.frc3467.subsystems.Elevator.Elevator;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDriveToFloor;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorToPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoNon extends CommandGroup {
	
	public AutoNon() {
		addSequential(new elevatorDriveToFloor());
		addSequential(new elevatorToPosition(Elevator.kLevelOne + 700)); // for can knockover hight
	}

}
