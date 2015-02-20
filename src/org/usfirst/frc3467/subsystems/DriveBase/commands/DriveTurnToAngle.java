package org.usfirst.frc3467.subsystems.DriveBase.commands;

import org.usfirst.frc3467.commands.CommandBase;

import edu.wpi.first.wpilibj.PIDController;

public class DriveTurnToAngle extends CommandBase {
	
	double m_target;
	PIDController m_pc;
	
	public DriveTurnToAngle(double targetangle) {
		requires(drivebase);
		this.setInterruptible(true);
//		setTimeout(2);
		
		m_target = targetangle;
		m_pc = drivebase.getPIDController();

	}

	protected void initialize() {
		
		// Use standard mecanum drive
		drivebase.initMecanum(false);
		
		/*
		 *  Enable drivebase rotational PID
		 */
		// Set target angle
		m_pc.setSetpoint(m_target);
		
		// Throttle down the max allowed speed
		m_pc.setOutputRange(-0.5, 0.5);
		
		// Set tolerance around the target angle
		m_pc.setAbsoluteTolerance(5);
		
		// Reset and enable PID controller
		m_pc.reset();
		m_pc.enable();
	
	}
	
	protected void execute() {

	}
	

	protected boolean isFinished() {
 
		if (drivebase.onTarget() || isTimedOut())	
		{
			// Disable PID controller
			m_pc.disable();
			return true;
		}
		else
			return false;
	}

	protected void end() {
		
	}
	
	protected void interrupted() {
		end();
	}
	
}
