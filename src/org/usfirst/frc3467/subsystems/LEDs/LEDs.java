package org.usfirst.frc3467.subsystems.LEDs;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LEDs extends Subsystem {
	
	private final int I2C_ADDRESS = 0x62;
	public static final int REG1 = 0x0;
	public static final int REG2 = 0x1;
	public static final int REG3 = 0x2;
	public static final int REG4 = 0x3;
	public static final int REG5 = 0x4;
	
	public static final int IDLE = 0;
	public static final int HIGH_CURRENT = 1;
	public static final int ONE_THIRTY = 2;
	public static final int TRUSS = 3;
	public static final int CLOSE = 4;
	public static final int TRUSS2 = 5;
	
	private final boolean DEBUGGING = true;
	
	public I2C leds;
	
	public LEDs() {
		leds = new I2C(Port.kOnboard, I2C_ADDRESS);
	}
	
	protected void initDefaultCommand() {
		this.setDefaultCommand(new UpdateLEDs());
		SmartDashboard.putData("Forward", new SetLEDs(0));
		SmartDashboard.putData("Reverse", new SetLEDs(1));
		SmartDashboard.putData("Idle", new SetLEDs(2));
		SmartDashboard.putData("HC", new SetLEDs(3));
		SmartDashboard.putData("130", new SetLEDs(4));
		SmartDashboard.putData("Truss", new SetLEDs(5));
		SmartDashboard.putData("Close", new SetLEDs(6));
	}
	
	public void setState(String location, int state) {
		// if (DEBUGGING) {
		// System.out.println("Location: " + location + "	State: " + state);
		// }
		setState(location, REG2, state);
	}
	
	public void setState(String location, int reg, int state) {
		if (DEBUGGING) {
			System.out.println("Location: " + location + "	State: " + state);
		}
		if (leds.write(reg, state)) {
			System.out.println("Transaction failed");
		}
	}
}
