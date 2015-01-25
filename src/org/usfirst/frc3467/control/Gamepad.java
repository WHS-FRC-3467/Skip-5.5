package org.usfirst.frc3467.control;

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
	public static int leftStick_xAxis = 1;
	public static int leftStick_yAxis = 2;
	public static int rightStick_xAxis = 3;
	public static int rightStick_yAxis = 4;
	public static int dPadX = 5;
	public static int dPadY = 6;
	
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
	
	public boolean getDpadLeft() {
		return isDown(dPadX, true);
	}
	
	public boolean getDpadRight() {
		return isDown(dPadX, false);
	}
	
	public boolean getDpadUp() {
		return isDown(dPadY, true);
	}
	
	public boolean getDpadDown() {
		return isDown(dPadY, false);
	}
	
	private boolean isDown(int pad, boolean isNeg) {
		double axis = getRawAxis(pad);
		if (isNeg && axis < -0.5)
			return true;
		if (!isNeg && axis > 0.5)
			return true;
		else
			return false;
	}
	
}
