package org.usfirst.frc3467.subsystems.LEDs;

import org.usfirst.frc3467.OI;

import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.subsystems.LIDAR.LIDAR;
import org.usfirst.frc3467.subsystems.Power.PowerMgr;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LEDs extends Subsystem {

	
	private boolean brownout;
	private boolean hmfdrdist;
	private boolean stackdist;

		public LEDs() {

	}

	
	public void start() {

	

			
	
			if(LIDAR.getDistance() < 93 && LIDAR.getDistance() > 84){
		
				OI.mspLaunchpad.setOutput(4, true);
				hmfdrdist = true;
				SmartDashboard.putString("LED State", "HumanFeed");
				
			}
			if(LIDAR.getDistance() > 93 || LIDAR.getDistance() < 84){
				OI.mspLaunchpad.setOutput(4, false);
				hmfdrdist = false;
			}
		
			if(LIDAR.getDistance() < 83 && LIDAR.getDistance() > 70){

				OI.mspLaunchpad.setOutput(1, true);
				stackdist = true;
				SmartDashboard.putString("LED State", "StackDist");
				
			}
			if(LIDAR.getDistance() > 83 || LIDAR.getDistance() < 70){
				OI.mspLaunchpad.setOutput(1, false);
				stackdist = false;
			}

		
		
		}

	protected void setOff(){
		OI.mspLaunchpad.setOutput(2, true);
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		this.setDefaultCommand(new RunLeds());
	}
	
}
