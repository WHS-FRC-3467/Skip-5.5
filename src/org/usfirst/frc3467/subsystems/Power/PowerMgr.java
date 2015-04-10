package org.usfirst.frc3467.subsystems.Power;

import org.usfirst.frc3467.OI;
import org.usfirst.frc3467.RobotMap;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PowerMgr extends Subsystem {

	private static PowerMgr instance;
	public static PowerDistributionPanel powerpanel;
	private static boolean brownoutstate;

	public static PowerMgr getInstance() {
		return instance;
	}

	
	public PowerMgr() {

		instance = this;		
		powerpanel = new PowerDistributionPanel();
	}


	public static boolean getBrownoutState(){
		if(powerpanel.getVoltage() < 7.5){
			brownoutstate = true;
		}
		if(powerpanel.getVoltage() > 7.5){
			brownoutstate = false;
		}
		return brownoutstate;
	}
	
	protected void initDefaultCommand() {
	}

}
