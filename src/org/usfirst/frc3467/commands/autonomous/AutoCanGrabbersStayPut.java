package org.usfirst.frc3467.commands.autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.commands.DriveTimedTank;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;
import org.usfirst.frc3467.subsystems.Elevator.commands.cangrabbersSetState;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDriveToFloor;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorToPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCanGrabbersStayPut extends CommandGroup {
	
	
	public AutoCanGrabbersStayPut() {
		

		// DriveTimedTank (time (sec), speed (-1 -> +1))
		addSequential(new cangrabbersSetState(true), 0.7);
	}
}
