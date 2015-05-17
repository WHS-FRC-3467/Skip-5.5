package org.usfirst.frc3467.subsystems.Elevator;

import org.usfirst.frc3467.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Indexer extends Subsystem {

	private DoubleSolenoid sIndexer;
	private Solenoid sClampPressure;
	public boolean engaged;
	public boolean disabled; //Boolean for disabling the usage of the indexer while the elevator is in the 
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
		sClampPressure = new Solenoid(RobotMap.indexerClamp);
		sIndexer.set(DoubleSolenoid.Value.kReverse);
		sClampPressure.set(false);
		engaged = false;
		mainCompressor = new Compressor(0);
		disabled = false;
		clamped = true;
	}
	
	public void disengageIndexer() {
		
		if ((engaged == true) && (disabled == false)) {
			sIndexer.set(DoubleSolenoid.Value.kReverse);
			sClampPressure.set(false);
			engaged = false;
			clamped = false;
		}
	}
	
	public void engageIndexer() {

		if ((engaged == false) && (disabled == false)) {
			sIndexer.set(DoubleSolenoid.Value.kForward);
			sClampPressure.set(false);
			engaged = true;
			clamped = false;
		}
	}

	public void engageCrushMode(){
		if((clamped = false) && (engaged == true) && (disabled == false)){
			sIndexer.set(DoubleSolenoid.Value.kForward);
			sClampPressure.set(true);
			engaged = true;
			clamped = true;
		}
	}
	protected void initDefaultCommand() {
		
	}
	
}
