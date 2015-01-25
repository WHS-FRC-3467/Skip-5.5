package org.usfirst.frc3467.subsystems.Elevator;

import org.usfirst.frc3467.RobotMap;
import org.usfirst.frc3467.subsystems.Elevator.commands.conveyorSetState;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Conveyor extends Subsystem {

	public enum ConveyorState {kOff(0), kIntake(1), kDischarge(2);
		private int value;

		private ConveyorState(int value){
			this.value = value;
		}

		public int getValue(){
			return this.value;
		}
	};

	private static Conveyor instance;
	private Talon conveyorMotor;

	public static Conveyor getInstance() {
		return instance;
	}
	
	public Conveyor() {
		instance = this;
		conveyorMotor = new Talon(RobotMap.conveyorTalon);
	}
	
	protected void initDefaultCommand() {
		this.setDefaultCommand(new conveyorSetState(ConveyorState.kOff, 0.0));
	}
	
	public void setState(Conveyor.ConveyorState state, double power) {
		switch (state) {
		case kIntake:
			conveyorMotor.set(power);
			break;
		case kDischarge:
			conveyorMotor.set(-power);
			break;
		case kOff:
		default:
			conveyorMotor.set(0.0);
			break;
		}
		
	}

	
}
