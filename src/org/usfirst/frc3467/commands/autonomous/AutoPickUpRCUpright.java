package org.usfirst.frc3467.commands.autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.commands.DriveTimedTank;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;
import org.usfirst.frc3467.subsystems.Elevator.commands.conveyorDrive;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDriveToFloor;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDriveToPosition;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorToPosition;
import org.usfirst.frc3467.subsystems.Elevator.commands.indexerOperate;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutoPickUpRCUpright extends CommandGroup {
	
	
	public AutoPickUpRCUpright() {
	
		// DriveTimedTank (time (sec), speed (-1 -> +1))
		addSequential(new elevatorDriveToFloor());
		addSequential(new DriveTimedTank(0.8, 0.6), 0.8);
		addParallel(new conveyorDrive(-0.45), 4.0);
		addSequential(new DriveTimedTank(4, 0.55), 4);
		addSequential(new conveyorDrive(0), 0.01);
		addSequential(new WaitCommand(1));
		addSequential(new DriveTimedTank (0.6, -0.6));
		addSequential(new elevatorToPosition(5400), 3);
		addSequential(new indexerOperate(2), 0.01);
		addSequential(new indexerOperate(2));
	}
}
