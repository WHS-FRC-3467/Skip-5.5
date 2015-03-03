package org.usfirst.frc3467.subsystems.DriveBase.commands;

import org.usfirst.frc3467.commands.CommandBase;

public class DriveSetFieldCentricState extends CommandBase {
	

	
	public DriveSetFieldCentricState(boolean state) {

		drivebase.setFieldCentricState(state);
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {

	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {

	}
	
	protected void interrupted() {
		end();
	}
	
}
