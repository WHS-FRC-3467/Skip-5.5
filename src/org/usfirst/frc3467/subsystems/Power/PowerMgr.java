package org.usfirst.frc3467.subsystems.Power;

import org.usfirst.frc3467.RobotMap;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PowerMgr extends Subsystem {

	private static PowerMgr instance;
	private PowerDistributionPanel pdp;

	public static PowerMgr getInstance() {
		return instance;
	}
	
	public PowerMgr() {

		instance = this;		
		pdp = new PowerDistributionPanel();
	}

	public PowerMgr(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
