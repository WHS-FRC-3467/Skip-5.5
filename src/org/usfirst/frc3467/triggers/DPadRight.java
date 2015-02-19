package org.usfirst.frc3467.triggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * Gamepad DPadUp support
 */
public class DPadRight extends Trigger {
	private Joystick joy;
	
	public DPadRight(Joystick joy) {
		this.joy = joy;
	}	

	public boolean get() {
        return (joy.getPOV(0) == 90);
    }
}
