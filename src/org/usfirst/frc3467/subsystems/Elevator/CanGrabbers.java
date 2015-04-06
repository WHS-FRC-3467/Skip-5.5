package org.usfirst.frc3467.subsystems.Elevator;

import org.usfirst.frc3467.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CanGrabbers extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	DoubleSolenoid cangrabbersol;
	
	public CanGrabbers(){
		cangrabbersol = new DoubleSolenoid(RobotMap.cangrabberIn, RobotMap.cangrabberOut);
	}
	/**
	 * state is 1, can grabbers out, state is 2, can grabbers in.
	 * state is 0, can grabber solenoid off.
	 * @param state
	 */
	public void setState(int state){
		state = 0;
		if(state == 1){
			cangrabbersol.set(DoubleSolenoid.Value.kForward);
		}
		if(state == 2){
			cangrabbersol.set(DoubleSolenoid.Value.kReverse);
		}
		else{
			cangrabbersol.set(DoubleSolenoid.Value.kOff);
		}
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

