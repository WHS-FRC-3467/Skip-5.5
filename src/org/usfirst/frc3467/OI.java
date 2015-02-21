package org.usfirst.frc3467;

import org.usfirst.frc3467.triggers.DoubleButton;
import org.usfirst.frc3467.triggers.DPadUp;
import org.usfirst.frc3467.triggers.DPadRight;
import org.usfirst.frc3467.triggers.DPadDown;
import org.usfirst.frc3467.triggers.DPadLeft;
import org.usfirst.frc3467.control.Gamepad;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;
import org.usfirst.frc3467.subsystems.Elevator.commands.conveyorDrive;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGAddTote;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCalibrate;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDrive;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGDropStack;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorHoldLevel;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorLevelsUpDown;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorUpdatePIDF;
import org.usfirst.frc3467.subsystems.Elevator.commands.indexerOperate;
import org.usfirst.frc3467.subsystems.Elevator.commands.conveyorCGIntakeandLick;
import org.usfirst.frc3467.subsystems.MXP.commands.imuUpdateDisplay;

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

		// Elevator/Conveyor/Indexer Commands
		new DoubleButton(operatorGamepad, 11, 12).whenActive(new elevatorCalibrate());

		// Drive Elevator Up - Fixed Speed
		new JoystickButton(operatorGamepad, Gamepad.leftBumper)
			.whileHeld(new elevatorDrive(Elevator.kUp_Fixed));
		
		// Drive Elevator Down - Fixed Speed
		new JoystickButton(operatorGamepad, Gamepad.rightBumper)
			.whileHeld(new elevatorDrive(Elevator.kDown_Fixed));
		
		// Engage Indexer
		new JoystickButton(operatorGamepad, Gamepad.leftTrigger)
			.whenPressed(new indexerOperate(true));
		
		// Retract Indexer
		new JoystickButton(operatorGamepad, Gamepad.rightTrigger)
			.whenPressed(new indexerOperate(false));
		
		// Conveyor - Spit Out Slow
		new JoystickButton(operatorGamepad, Gamepad.xButton)
			.whileHeld(new conveyorDrive(0.25));
		
		// Conveyor - Intake Slow
		new JoystickButton(operatorGamepad, Gamepad.aButton)
			.whileHeld (new conveyorDrive(-0.25));
	
		// Conveyor Intake - Fast
		new JoystickButton(operatorGamepad, Gamepad.bButton)
			.whileHeld(new conveyorDrive(-0.5));
	
		// Conveyor - Spit Out Fast
		new JoystickButton(operatorGamepad, Gamepad.yButton)
			.whileHeld(new conveyorDrive(0.5));
		
 		new DPadUp(operatorGamepad).whenActive(new elevatorHoldLevel(0));
 		new DPadRight(operatorGamepad).whenActive(new elevatorHoldLevel(1));
 		new DPadDown(operatorGamepad).whenActive(new elevatorHoldLevel(2));
 		new DPadLeft(operatorGamepad).whenActive(new elevatorHoldLevel(3));
 		
 		{
//			new elevatorCGAddTote();
		}

		{
//			new elevatorCGDropStack();
		}
		
		{
//			new elevatorLevelsUpDown(false);
		}
		
		{
//			new elevatorLevelsUpDown(true);
		}

		
		// SmartDashboard Buttons
		SmartDashboard.putData("Update Elevator PID", new elevatorUpdatePIDF());
		SmartDashboard.putData("Update IMU Display", new imuUpdateDisplay());
		
		/*
 		SmartDashboard.putData("Drive Backward", new DriveForward(-2.25));
		SmartDashboard.putData("Start Rollers", new SetCollectionSpeed(Collector.FORWARD));
		SmartDashboard.putData("Stop Rollers", new SetCollectionSpeed(Collector.STOP));
		SmartDashboard.putData("Reverse Rollers", new SetCollectionSpeed(Collector.REVERSE));
		 */
	}
}
