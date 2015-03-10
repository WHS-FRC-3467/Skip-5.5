package org.usfirst.frc3467;

import org.usfirst.frc3467.triggers.DoubleButton;
import org.usfirst.frc3467.triggers.DPadUp;
import org.usfirst.frc3467.triggers.DPadRight;
import org.usfirst.frc3467.triggers.DPadDown;
import org.usfirst.frc3467.triggers.DPadLeft;
import org.usfirst.frc3467.control.Gamepad;
import org.usfirst.frc3467.subsystems.DriveBase.commands.DriveDistance;
import org.usfirst.frc3467.subsystems.DriveBase.commands.DriveSetFieldCentricState;
import org.usfirst.frc3467.subsystems.Elevator.Conveyor;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;
import org.usfirst.frc3467.subsystems.Elevator.commands.conveyorDrive;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGAddTote;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGAddToteTopOfIndexer;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGDropStackTopOfIndexer;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGDropStackTopOfIndexerWithToteOnC;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGDropStackWithToteOnConveyor;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGGoToTop;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGIndexSidewaysRC;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGIndexUprightRC;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCalibrate;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDriveManual;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGDropStack;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDriveToFloor;
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
	public static Joystick operatorJoystick;
	public static Gamepad operatorGamepad;
	
	public OI() {
		driveJoystick = new Joystick(0);
		operatorJoystick = new Joystick(2);
		operatorGamepad = new Gamepad(1);
		
	}

	public Joystick getDrJoystick() {
		return driveJoystick;
	}
	
	public Joystick getOpJoystick() {
		return operatorJoystick;
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

		// Halt Elevator PID and switch to manual mode
		new JoystickButton(operatorGamepad, Gamepad.leftBumper)
			.whileHeld(new elevatorDriveManual(0));
		
		// DeIndex Stack with NO tote on conveyor
		new JoystickButton(operatorGamepad, Gamepad.rightBumper)
			.whenPressed(new elevatorCGDropStack());
		
		// Index A Tote
		new JoystickButton(operatorGamepad, Gamepad.leftTrigger)
			.whenPressed(new elevatorCGAddTote());
		
		// DeIndex Stack with a tote on the conveyor
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
	 
		// Go to Top
		new DPadUp(operatorGamepad)
			.whenActive(new elevatorCGGoToTop());

		// Go to Level 0 (platform height)
		new DPadRight(operatorGamepad)
			.whenActive(new elevatorToPosition(Elevator.kLevelZero));
 		// Go to top of indexers
		new JoystickButton(operatorJoystick, 7)
			.whenPressed(new elevatorCGAddToteTopOfIndexer());
		// Deindex at top of indexers
		new JoystickButton(operatorJoystick, 8)
			.whenPressed(new elevatorCGDropStackTopOfIndexer());
		// Deindex at top of indexers with tote on conveyor
		new JoystickButton(operatorJoystick, 2)
			.whenPressed(new elevatorCGDropStackTopOfIndexerWithToteOnC());
		// Go to Step Level + 1
		new JoystickButton(operatorJoystick, 9)
			.whenPressed(new elevatorToPosition(Elevator.kLevelStepOne));
		// Go to Step Level + 2
		new JoystickButton(operatorJoystick, 10)
			.whenPressed(new elevatorToPosition(Elevator.kLevelStepTwo));
		// Go to Step Level + 3
		new JoystickButton(operatorJoystick, 11)
			.whenPressed(new elevatorToPosition(Elevator.kLevelStepThree));
		// Index Upright RC
		new JoystickButton(operatorJoystick, 12)
			.whenPressed(new elevatorCGIndexUprightRC());
		// Go to Step Height
		new DPadDown(operatorGamepad)
			.whenActive(new elevatorToPosition(Elevator.kLevelStep));
 		
		// Drive slowly to floor
		new DPadLeft(operatorGamepad)
			.whenActive(new elevatorDriveToFloor());
		
		// Zero the Gyro Yaw
		new JoystickButton(driveJoystick, 7)
			.whenPressed(new imuZeroYaw());
 		
		/*
		new JoystickButton(driveJoystick, 3)
			.whenPressed(new DriveSetFieldCentricState(true));
		
		new JoystickButton(driveJoystick, 4)
			.whenPressed(new DriveSetFieldCentricState(false));
		*/

		
		// SmartDashboard Buttons
		
		SmartDashboard.putData("Elevator Calibrate", new elevatorCalibrate());
		SmartDashboard.putData("Update Elevator PID", new elevatorUpdatePIDF());
		SmartDashboard.putData("Update IMU Display", new imuUpdateDisplay());
		SmartDashboard.putData("Indexer Engage", new indexerOperate(true));
		SmartDashboard.putData("Indexer Retract", new indexerOperate(false));
		SmartDashboard.putData("Drop Stack (no tote on conveyor)", new elevatorCGDropStack());
		SmartDashboard.putData("IndexUprightRC", new elevatorCGIndexSidewaysRC());
		SmartDashboard.putData("IndexSidewaysRC", new elevatorCGIndexUprightRC());
		SmartDashboard.putData("DriveDistance Test", new DriveDistance(1000));

	}
}
