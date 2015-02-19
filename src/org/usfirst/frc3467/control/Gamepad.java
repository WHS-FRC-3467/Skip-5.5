package org.usfirst.frc3467.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gamepad extends edu.wpi.first.wpilibj.Joystick {
	public static int xButton = 1;
	public static int aButton = 2;
	public static int bButton = 3;
	public static int yButton = 4;
	public static int leftBumper = 5;
	public static int rightBumper = 6;
	public static int leftTrigger = 7;
	public static int rightTrigger = 8;
	public static int backButton = 9;
	public static int startButton = 10;
	public static int leftStickPress = 11;
	public static int rightStickPress = 12;
	
	public static int leftStick_xAxis = 0;
	public static int leftStick_yAxis = 1;
	public static int rightStick_xAxis = 2;
	public static int rightStick_yAxis = 3;
	
	public Gamepad(int port) {
		super(port);
	}
	
	public double getLeftStickX() {
		return getRawAxis(leftStick_xAxis);
	}
	
	public double getLeftStickY() {
		return getRawAxis(leftStick_yAxis);
	}
	
	public boolean getLeftAxisButton() {
		return getRawButton(leftStickPress);
	}
	
	public double getRightStickX() {
		return getRawAxis(rightStick_xAxis);
	}
	
	public double getRightStickY() {
		return getRawAxis(rightStick_yAxis);
	}
	
	public boolean getRightAxisButton() {
		return getRawButton(rightStickPress);
	}
	
	public boolean getLeftBumper() {
		return getRawButton(leftBumper);
	}
	
	public boolean getRightBumper() {
		return getRawButton(rightBumper);
	}
	
	public boolean getLeftTrigger() {
		return getRawButton(leftTrigger);
	}
	
	public boolean getRightTrigger() {
		return getRawButton(rightTrigger);
	}
	
	public boolean getXButton() {
		return getRawButton(xButton);
	}
	
	public boolean getAButton() {
		return getRawButton(aButton);
	}
	
	public boolean getBButton() {
		return getRawButton(bButton);
	}
	
	public boolean getYButton() {
		return getRawButton(yButton);
	}
	
	public boolean getStartButton() {
		return getRawButton(startButton);
	}
	
	public boolean getBackButton() {
		return getRawButton(backButton);
	}
	
	public boolean getDpadUp() {
		return(getPOV(0) == 0 ? true : false);
	}
	
	public boolean getDpadRight() {
		return(getPOV(0) == 90 ? true : false);
	}
	
	public boolean getDpadDown() {
		return(getPOV(0) == 180 ? true : false);
	}

	public boolean getDpadLeft() {
		return(getPOV(0) == 270 ? true : false);
	}
	
	
}
