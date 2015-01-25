package org.usfirst.frc3467.subsystems.LEDs;

import org.usfirst.frc3467.commands.CommandBase;

public class SetLEDs extends CommandBase {
	int state = 0;
	boolean stop = false;
	
	public SetLEDs(int test) {
		//requires(leds);
		state = test;
	}
	
	protected void initialize() {
		stop = false;
	}
	
	protected void execute() {
		switch (state) {
			case 0:
				leds.setState("Forward", 0, 0);
				stop = true;
				break;
			case 1:
				leds.setState("Reverse", 0, 1);
				stop = true;
				break;
			case 2:
				leds.setState("Idle", 1, 0);
				break;
			case 3:
				leds.setState("HC", 1, 1);
				break;
			case 4:
				leds.setState("130", 1, 2);
				break;
			case 5:
				leds.setState("Truss", 1, 3);
				break;
			case 6:
				leds.setState("Close", 1, 4);
				break;
			default:
				break;
		}
	}
	
	protected boolean isFinished() {
		return stop;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		
	}
	
}
