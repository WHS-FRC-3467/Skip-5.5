package org.usfirst.frc3467;

import org.usfirst.frc3467.triggers.DoubleButton;
import org.usfirst.frc3467.control.Gamepad;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCalibrate;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDrive;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorPosition;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
	
	public static Joystick driveJoystick;
	public static Gamepad operatorGamepad;
	
	public OI() {
		driveJoystick = new Joystick(1);
		operatorGamepad = new Gamepad(2);
		
	}

	public Joystick getJoystick() {
		return driveJoystick;
	}
		
	public Gamepad getGamepad() {
		return operatorGamepad;
	}
	
	public void BindCommands() {

		// Elevator Commands
		new DoubleButton(operatorGamepad, 11, 12).whenActive(new elevatorCalibrate());

		new JoystickButton(operatorGamepad, Gamepad.dPadY)
					.whileHeld(new elevatorDrive(true));
		
		new JoystickButton(operatorGamepad, Gamepad.xButton)
			.whenPressed(new elevatorPosition(10));
		
		new JoystickButton(operatorGamepad, Gamepad.aButton)
			.whenPressed(new elevatorPosition(20));
	
		new JoystickButton(operatorGamepad, Gamepad.bButton)
			.whenPressed(new elevatorPosition(30));
	
		new JoystickButton(operatorGamepad, Gamepad.yButton)
			.whenPressed(new elevatorPosition(40));
	
		// Conveyor
		
		// Indexer
		/*
		
		// SmartDashboard Buttons
		SmartDashboard.putData("Drive Forward", new DriveForward(2.25));
		SmartDashboard.putData("Drive Backward", new DriveForward(-2.25));
		SmartDashboard.putData("Start Rollers", new SetCollectionSpeed(Collector.FORWARD));
		SmartDashboard.putData("Stop Rollers", new SetCollectionSpeed(Collector.STOP));
		SmartDashboard.putData("Reverse Rollers", new SetCollectionSpeed(Collector.REVERSE));
	 
		 */
	}
}
