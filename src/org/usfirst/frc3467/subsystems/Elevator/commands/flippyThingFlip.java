package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.RobotMap;
import org.usfirst.frc3467.commands.CommandBase;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class flippyThingFlip extends CommandBase {
	
	private double m_speedtoflipIn;
	private double m_speedtoflipOut;
	public int timesStalled;
	private static double flippyThingCurrent;
	private PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	public flippyThingFlip(double speedtoflipIn, double speedtoflipOut) {
		requires(conveyor);
		m_speedtoflipIn = speedtoflipIn;
		m_speedtoflipOut = speedtoflipOut;
	}
	
	protected void initialize() {
		
		timesStalled = 0;
	}
	
	protected void execute() {
	
		flippyThingCurrent = pdp.getCurrent(RobotMap.flippyThingMotorPDPCHANNEL);
		
		//When I stall, add 1 to the timesStalled integer.
		if(flippyThingCurrent > 50)
		{
			timesStalled = + 1;
		}

		//If I haven't stalled yet flip up.
		
		if((timesStalled) == 0)
		{
			conveyor.driveFlippyThing(m_speedtoflipIn);
		}
		
		//When I stall the first time reverse my direction.
		if(timesStalled == 1)
		{
			conveyor.driveFlippyThing(m_speedtoflipOut);
		}
		if(timesStalled > 1)
		{
			conveyor.driveFlippyThing(0);
		}
	}
	
	protected boolean isFinished() {

		// TODO: put current sensing code here to know when to stop
		return false;
	}
	
	protected void end() {
		conveyor.driveFlippyThing(0);
	}
	
	protected void interrupted() {
		end();
	}
	
}
