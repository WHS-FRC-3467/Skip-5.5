package org.usfirst.frc3467.subsystems.DriveBase.commands;

import org.usfirst.frc3467.commands.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSetFieldCentricState extends CommandBase {
	
	boolean m_state;

	
	public DriveSetFieldCentricState(boolean state) {

		m_state = state;

	}
	
	protected void initialize() {
		drivebase.setFieldCentricState(m_state);
	}
	
	protected void execute() {


	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {

		drivebase.setFieldCentricState(!m_state);
	}
	
	protected void interrupted() {
		end();
	}
	
}
