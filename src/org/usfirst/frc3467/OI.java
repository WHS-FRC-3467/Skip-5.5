package org.usfirst.frc3467;

import org.usfirst.frc3467.triggers.DoubleButton;


import org.usfirst.frc3467.triggers.DPadUp;
import org.usfirst.frc3467.triggers.DPadRight;
import org.usfirst.frc3467.triggers.DPadDown;
import org.usfirst.frc3467.triggers.DPadLeft;
import org.usfirst.frc3467.triggers.GamepadLeftTrigger;
import org.usfirst.frc3467.triggers.GamepadRightTrigger;
import org.usfirst.frc3467.triggers.SingleButtonandIgnoreOtherButton;
import org.usfirst.frc3467.control.Gamepad;
import org.usfirst.frc3467.subsystems.DriveBase.commands.DriveDistance;
import org.usfirst.frc3467.subsystems.DriveBase.commands.DriveSetFieldCentricState;
import org.usfirst.frc3467.subsystems.Elevator.Conveyor;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;
import org.usfirst.frc3467.subsystems.Elevator.commands.cangrabbersSetState;
import org.usfirst.frc3467.subsystems.Elevator.commands.conveyorDrive;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGAddTote;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGAddToteSlowHigh;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGAutoAddToteLimitSwitchSensing;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorCGDropStackWith2ToteOnConveyor;
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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
	
	//inputs
	public static Joystick driveJoystick;

	public static Gamepad operatorGamepad;
	public static Joystick mspLaunchpad;
	
	public OI() {
		driveJoystick = new Joystick(0);	;
		operatorGamepad = new Gamepad(1);
		mspLaunchpad = new Joystick(2);
	}

	public Joystick getDrJoystick() {
		return driveJoystick;
	}
	

		
	public Gamepad getGamepad() {
		return operatorGamepad;
	}
	
	public Joystick getLaunchpad(){
		return mspLaunchpad;
	}
	
	public void BindCommands() {

		/*
		 * Elevator/Conveyor/Indexer Commands
		 */
		

		//MSP LAUNCHPAD 
		new JoystickButton(mspLaunchpad, 1)
			.whenPressed(new elevatorToPosition(Elevator.kLevelOne));

		new JoystickButton(mspLaunchpad, 2)
		.whenPressed(new elevatorToPosition(Elevator.kLevelTwo));
		
		new JoystickButton(mspLaunchpad, 3)
		.whenPressed(new elevatorToPosition(Elevator.kLevelThree));
		
		new JoystickButton(mspLaunchpad, 4)
		.whenPressed(new elevatorToPosition(Elevator.kLevelFour));
	
		new JoystickButton(mspLaunchpad, 5)
		.whenPressed(new elevatorToPosition(Elevator.kLevelFive));
		
		new JoystickButton(mspLaunchpad, 6)
		.whenPressed(new elevatorToPosition(Elevator.kLevelTop));
		
		new JoystickButton(mspLaunchpad, 7)
		.whenPressed(new elevatorCGAddToteSlowHigh());
		
		new JoystickButton(mspLaunchpad, 12)
		.whenPressed(new elevatorCGDropStackWith2ToteOnConveyor());
		
		new JoystickButton(mspLaunchpad, 13)
		.whenPressed(new cangrabbersSetState(false)); 
		
		new JoystickButton(mspLaunchpad, 8)
			.whenPressed(new cangrabbersSetState(true));
		
		new JoystickButton(mspLaunchpad, 9)
		.whenPressed(new elevatorToPosition(3300));
		
		new SingleButtonandIgnoreOtherButton(mspLaunchpad, 10, 14)
		.whileActive(new elevatorDriveManual(Elevator.kUp_FixedPlus + 0.1));
		
		new SingleButtonandIgnoreOtherButton(mspLaunchpad, 11, 14)
		.whileActive(new elevatorDriveManual(Elevator.kDown_FixedPlus));		

		new DoubleButton(mspLaunchpad, 10, 14)
		.whileActive(new elevatorDriveManual(Elevator.kUp_FixedPlus));
		
		new DoubleButton(mspLaunchpad, 11, 14)
		.whileActive(new elevatorDriveManual(Elevator.kDown_Fixed));
		
		
		// Halt Elevator PID and switch to manual mode
		new JoystickButton(operatorGamepad, Gamepad.leftBumper)
			.whileHeld(new elevatorDriveManual(0));
		
		
		// DeIndex Stack with NO tote on conveyor
		new JoystickButton(operatorGamepad, Gamepad.rightBumper)
			.whenPressed(new elevatorCGDropStack());
		
		// Index Routine:
		new GamepadLeftTrigger(operatorGamepad)
			.whenActive(new elevatorCGAddTote());
		// Slow and High Index Routine (For RCs and last totes from the human fdr statn

		// DeIndex Stack with a tote on the conveyor
		
		new GamepadRightTrigger(operatorGamepad)
		    .whenActive(new elevatorCGDropStackWithToteOnConveyor());
		
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
			.whenActive(new elevatorToPosition(1750));

		// Go to Level 0 (platform height)
		new DPadRight(operatorGamepad)
			.whenActive(new elevatorToPosition(SmartDashboard.getNumber("Scoring Platform Height: Default 150", 150)));
 		// Go to human feeding height


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
 * 		// Disable field-centric driving
 
		new JoystickButton(driveJoystick, 1)
			.whileHeld(new DriveSetFieldCentricState(false));

		new JoystickButton(driveJoystick, 1)
			.whenReleased(new DriveSetFieldCentricState(true));		
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
		SmartDashboard.putData("CanGrabbers Out", new cangrabbersSetState(true));	
		SmartDashboard.putData("CanGrabbers In", new cangrabbersSetState(false));	


	}
}
