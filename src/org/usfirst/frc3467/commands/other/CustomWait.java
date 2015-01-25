package org.usfirst.frc3467.commands.other;

import org.usfirst.frc3467.commands.CommandBase;

public class CustomWait extends CommandBase {
	long startTime = 0;
	long waitTime = 0;
	boolean finished = false;
	
	public CustomWait(long waitTimeMilli) {
		waitTime = waitTimeMilli;
	}
	
	protected void initialize() {
		startTime = System.currentTimeMillis();
		finished = false;
	}
	
	protected void execute() {
		if (System.currentTimeMillis() - startTime > waitTime)
			finished = true;
		
	}
	
	protected boolean isFinished() {
		return finished;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		
	}
	
}
