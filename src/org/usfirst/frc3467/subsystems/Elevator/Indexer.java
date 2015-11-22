package org.usfirst.frc3467.subsystems.Elevator;

import org.usfirst.frc3467.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Indexer extends Subsystem {

	private DoubleSolenoid sIndexer;
	private DoubleSolenoid sClampPressure;
	public boolean engaged = false;
	public boolean disabled = false; //Boolean for disabling the usage of the indexer while the elevator is in the 
							  //specified range to protect the centering mechanism.
	private static Indexer instance;
	private Compressor mainCompressor;
	private boolean clamped;
	
	public static Indexer getInstance() {
		return instance;
	}
	
	public Indexer() {

		instance = this;		
		sIndexer = new DoubleSolenoid(RobotMap.indexerEngage, RobotMap.indexerDisengage);
		sClampPressure = new DoubleSolenoid(RobotMap.indexerClamp1, RobotMap.indexerClamp2);
		sIndexer.set(DoubleSolenoid.Value.kReverse);
		sClampPressure.set(DoubleSolenoid.Value.kReverse);
		engaged = false;
		mainCompressor = new Compressor(0);
		disabled = false;
		clamped = true;
	}
	
	public void disengageIndexer() {
		
		if ((disabled == false)) {
			sIndexer.set(DoubleSolenoid.Value.kReverse);
			sClampPressure.set(DoubleSolenoid.Value.kReverse);
			engaged = false;
			clamped = false;
		}
	}
	
	public void engageIndexer() {

		if ((disabled == false)) {
			sIndexer.set(DoubleSolenoid.Value.kForward);
			sClampPressure.set(DoubleSolenoid.Value.kReverse);
			engaged = true;
			clamped = false;
		}
	}

	public void engageCrushMode(){
		if((disabled == false)){
			sIndexer.set(DoubleSolenoid.Value.kForward);
			sClampPressure.set(DoubleSolenoid.Value.kForward);
			engaged = true;
			clamped = true;
		}
	}
	protected void initDefaultCommand() {
		
	}
	public boolean getEngagedStatus(){
		return engaged;
		
	}
	public void disable(boolean disabledlocal){
		disabled = disabledlocal;
		
	}
	
	
}
