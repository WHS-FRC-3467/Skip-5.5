package org.usfirst.frc3467.subsystems.Elevator;

import org.usfirst.frc3467.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Indexer extends Subsystem {

	private DoubleSolenoid sIndexer;
	private boolean engaged;
	private static Indexer instance;
	private Compressor mainCompressor;
	
	public static Indexer getInstance() {
		return instance;
	}
	
	public Indexer() {

		instance = this;		
		sIndexer = new DoubleSolenoid(RobotMap.indexerEngage, RobotMap.indexerDisengage);
		sIndexer.set(DoubleSolenoid.Value.kReverse);
		engaged = false;
		mainCompressor = new Compressor(0);
	}
	
	public void disengageIndexer() {
		
		if (engaged == true) {
			sIndexer.set(DoubleSolenoid.Value.kReverse);
			engaged = false;
		}
	}
	
	public void engageIndexer() {

		if (engaged == false) {
			sIndexer.set(DoubleSolenoid.Value.kForward);
			engaged = true;
		}
	}

	protected void initDefaultCommand() {
		
	}
	
}
