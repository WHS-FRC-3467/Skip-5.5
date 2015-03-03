package org.usfirst.frc3467;

import org.usfirst.frc3467.triggers.DoubleButton;
import org.usfirst.frc3467.triggers.DPadUp;
import org.usfirst.frc3467.triggers.DPadRight;
import org.usfirst.frc3467.triggers.DPadDown;
import org.usfirst.frc3467.triggers.DPadLeft;
import org.usfirst.frc3467.control.Gamepad;
import org.usfirst.frc3467.subsystems.DriveBase.DriveBase;
import org.usfirst.frc3467.subsystems.DriveBase.commands.DriveDistance;
import org.usfirst.frc3467.subsystems.DriveBase.commands.DriveSetFieldCentricState;
import org.usfirst.frc3467.subsystems.Elevator.Conveyor;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;
import org.usfirst.frc3467.subsystems.Elevator.commands.conveyorDrive;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGAddTote;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGDropStackWithToteOnConveyor;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGGoToTop;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGNextLevel;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGIndexSidewaysRC;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGIndexUprightRC;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCalibrate;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDrive;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGDropStack;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDriveToFloor;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorHoldLevel;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorLevelsUpDown;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorToPosition;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorUpdatePIDF;
import org.usfirst.frc3467.subsystems.Elevator.commands.indexerOperate;
import org.usfirst.frc3467.subsystems.MXP.commands.imuUpdateDisplay;
import org.usfirst.frc3467.subsystems.MXP.commands.imuZeroYaw;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
	
	//inputs
	public static Joystick driveJoystick;
	public static Gamepad operatorGamepad;
	
	public OI() {
		driveJoystick = new Joystick(0);
		operatorGamepad = new Gamepad(1);
		
	}

	public Joystick getJoystick() {
		return driveJoystick;
	}
		
	public Gamepad getGamepad() {
		return operatorGamepad;
	}
	
	public void BindCommands() {

		/*
		 * Elevator/Conveyor/Indexer Commands
		 */
		
		// Calibrate
		new DoubleButton(operatorGamepad, 11, 12)
			.whenActive(new elevatorCalibrate());

		// Drive Elevator Up - Fixed Speed
		new JoystickButton(operatorGamepad, Gamepad.leftBumper)
			.whileHeld(new elevatorDrive(0));
		
		// Drive Elevator Down - Fixed Speed
		new JoystickButton(operatorGamepad, Gamepad.rightBumper)
			.whenPressed(new elevatorCGDropStack());
		
		// Index Tote
		new JoystickButton(operatorGamepad, Gamepad.leftTrigger)
			.whenPressed(new elevatorCGAddTote());
		
		// Drop Stack
		new JoystickButton(operatorGamepad, Gamepad.rightTrigger)
			.whenPressed(new elevatorCGDropStackWithToteOnConveyor());
		
		// Conveyor - Eject Slow
		new JoystickButton(operatorGamepad, Gamepad.xButton)
			.whileHeld(new conveyorDrive(Conveyor.kEjectSlow));
		
		// Conveyor - Intake to Pickup
		new JoystickButton(operatorGamepad, Gamepad.aButton)
			.whileHeld (new conveyorDrive(Conveyor.kIntakePickup));
	
		// Conveyor Intake Fast
		new JoystickButton(operatorGamepad, Gamepad.bButton)
			.whileHeld(new conveyorDrive(Conveyor.kIntakeFast));
	
		// Conveyor - Eject Fast
		new JoystickButton(operatorGamepad, Gamepad.yButton)
			.whileHeld(new conveyorDrive(Conveyor.kEjectFast - 0.1));
		
		new JoystickButton(operatorGamepad, Gamepad.startButton)
			.whenPressed(new indexerOperate(true));
	
		new JoystickButton(operatorGamepad, Gamepad.backButton)
			.whenPressed(new indexerOperate(false));
	 
		
// 		new DPadUp(operatorGamepad).whenActive(new elevatorCGNextLevel(true));
// 		new DPadDown(operatorGamepad).whenActive(new elevatorCGNextLevel(false));

		
		// Go to Top
		new DPadUp(operatorGamepad)
			.whenActive(new elevatorCGGoToTop());

		// Go to Level 0 (platform height)
		new DPadRight(operatorGamepad)
			.whenActive(new elevatorToPosition(Elevator.kLevelZero));
 		
		// Go to Step Height
		new DPadDown(operatorGamepad)
			.whenActive(new elevatorToPosition(Elevator.kLevelStep));
 		
		// Drive slowly to floor
		new DPadLeft(operatorGamepad)
			.whenActive(new elevatorDriveToFloor());
		
		new JoystickButton(driveJoystick, 2)
			.whenPressed(new imuZeroYaw());
 		
		/*
		new JoystickButton(driveJoystick, 3)
			.whenPressed(new DriveSetFieldCentricState(true));
		
		new JoystickButton(driveJoystick, 4)
			.whenPressed(new DriveSetFieldCentricState(false));
		*/

		
		// SmartDashboard Buttons
		
		SmartDashboard.putBoolean("Field Centric Enabled", DriveBase.m_fieldCentricEnabled);
		SmartDashboard.putData("Update Elevator PID", new elevatorUpdatePIDF());
		SmartDashboard.putData("Update IMU Display", new imuUpdateDisplay());
		SmartDashboard.putData("Indexer Engage", new indexerOperate(true));
		SmartDashboard.putData("Indexer Retract", new indexerOperate(false));
		SmartDashboard.putData("Drop Stack (no tote on conveyor)", new elevatorCGDropStack());
		SmartDashboard.putData("IndexUprightRC", new elevatorCGIndexSidewaysRC());
		SmartDashboard.putData("IndexSidewaysRC", new elevatorCGIndexUprightRC());
//		SmartDashboard.putData("DriveDistance Test", new DriveDistance(1000));

	}
}
