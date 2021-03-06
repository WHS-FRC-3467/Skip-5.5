package org.usfirst.frc3467.commands.autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.commands.DriveTimedTank;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;
import org.usfirst.frc3467.subsystems.Elevator.commands.conveyorDrive;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGAddTote;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDriveToFloor;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDriveToPosition;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorToPosition;
import org.usfirst.frc3467.subsystems.Elevator.commands.indexerOperate;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoKanKicker extends CommandGroup {
	
	public AutoKanKicker() {
		addSequential(new elevatorDriveToFloor());
		addSequential(new elevatorToPosition(1700), 1.3); // for can knockover hight
		addParallel(new conveyorDrive(-0.4));
		addSequential(new DriveTimedTank(0.4, 0.7));
		addSequential(new DriveTimedTank(0.3, 0.1));
		addSequential(new DriveTimedTank(0.7, -0.2));
		addSequential(new elevatorDriveToFloor(), 1.2);
		addParallel(new conveyorDrive(-0.7), 3.5);
		addSequential(new DriveTimedTank(3.2, 0.6));
		addParallel(new conveyorDrive(-0.3), 3.5);
		addSequential(new conveyorDrive(0.0), 0.01);

	}

}
