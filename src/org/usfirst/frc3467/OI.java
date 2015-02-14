package org.usfirst.frc3467;

import org.usfirst.frc3467.triggers.DoubleButton;
import org.usfirst.frc3467.control.Gamepad;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;
import org.usfirst.frc3467.subsystems.Elevator.commands.conveyorDrive;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorAddTote;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCalibrate;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDrive;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDropStack;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorToLevel;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorUpdatePIDF;
import org.usfirst.frc3467.subsystems.Elevator.commands.indexerOperate;
import org.usfirst.frc3467.subsystems.Elevator.commands.intakeandLick;

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

		new JoystickButton(operatorGamepad, Gamepad.leftBumper)
			.whileHeld(new elevatorDrive(Elevator.kUp_Fixed));
		
		new JoystickButton(operatorGamepad, Gamepad.rightBumper)
			.whileHeld(new elevatorDrive(Elevator.kDown_Fixed));
		
		new JoystickButton(operatorGamepad, Gamepad.leftTrigger)
			.whenPressed(new indexerOperate(true));
		
		new JoystickButton(operatorGamepad, Gamepad.rightTrigger)
			.whenPressed(new indexerOperate(false));
		
		new JoystickButton(operatorGamepad, Gamepad.xButton)
			.whileHeld(new conveyorDrive(0.25));
		
		new JoystickButton(operatorGamepad, Gamepad.aButton)
			.whileHeld (new intakeandLick());
	
		new JoystickButton(operatorGamepad, Gamepad.bButton)
			.whenPressed(new conveyorDrive(-0.25));
	
		new JoystickButton(operatorGamepad, Gamepad.yButton)
			.whenPressed(new conveyorDrive(0.5));
	
		if(operatorGamepad.getDpadUp() == true)
		{
			new elevatorAddTote();
		}
		
		if(operatorGamepad.getDpadDown() == false)
		{
			new elevatorDropStack();
		}
		
		// SmartDashboard Buttons
		SmartDashboard.putData("Update Elevator PID", new elevatorUpdatePIDF());
		
		/*
 		SmartDashboard.putData("Drive Backward", new DriveForward(-2.25));
		SmartDashboard.putData("Start Rollers", new SetCollectionSpeed(Collector.FORWARD));
		SmartDashboard.putData("Stop Rollers", new SetCollectionSpeed(Collector.STOP));
		SmartDashboard.putData("Reverse Rollers", new SetCollectionSpeed(Collector.REVERSE));
		 */
	}
}
