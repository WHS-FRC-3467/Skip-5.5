package org.usfirst.frc3467.subsystems.Elevator;

import org.usfirst.frc3467.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CanGrabbers extends Subsystem {

	private DoubleSolenoid sCanGrabbers;
	private boolean engaged;
	private static CanGrabbers instance;
	
	public static CanGrabbers getInstance() {
		return instance;
	}
	
	public CanGrabbers() {

		instance = this;		
		sCanGrabbers = new DoubleSolenoid(RobotMap.cangrabberOut, RobotMap.cangrabberIn);
		sCanGrabbers.set(DoubleSolenoid.Value.kReverse);
		engaged = false;
	}
	
	public void disengageCanGrabber() {
		
		if (engaged == true) {
			sCanGrabbers.set(DoubleSolenoid.Value.kReverse);
			engaged = false;
		}
	}
	
	public void engageCanGrabber() {

		if (engaged == false) {
			sCanGrabbers.set(DoubleSolenoid.Value.kForward);
			engaged = true;
		}
	}

	protected void initDefaultCommand() {
		
	}
	
}
