package org.usfirst.frc3467.subsystems.DriveBase.commands;

import org.usfirst.frc3467.OI;
import org.usfirst.frc3467.commands.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveMecanum extends CommandBase {
	
	boolean m_useVoltage = false;
	double m_lastX = 0.0, m_lastY = 0.0, m_lastRot = 0.0;
	
	public DriveMecanum(boolean useVoltage) {
		requires(drivebase);
		this.setInterruptible(true);
	
		m_useVoltage = useVoltage;
	}

	protected void initialize() {
		
		drivebase.initMecanum(m_useVoltage);
		
	}
	
	protected void execute() {

		double x, y, rotation;
		
		x = OI.driveJoystick.getX();
		y = OI.driveJoystick.getY();
		rotation = OI.driveJoystick.getZ();

		// Adjust stick values for better control
		x = m_lastX = adjustStick(x, m_lastX);
		y = m_lastY = adjustStick(y, m_lastY);
		rotation = m_lastRot = adjustStick(rotation, m_lastRot);
		
		SmartDashboard.putNumber(   "IMU_Yaw", imu.getYaw());
		if (OI.driveJoystick.getTrigger()  )
			drivebase.driveMecanum(x, y, rotation, 0);
		else
			drivebase.driveMecanum(x, y, rotation, imu.getYaw());
	
		
	}
	
	private double adjustStick(double input, double lastVal) {
		
		double val = input;
		double change;
		final double changeLimit = 0.20;
		
		/*
		 *  Deadband limit
		 */
		if (val > -0.08 && val < 0.08) {
			return 0.0;
		}

        /*
         *  Square the inputs (while preserving the sign) to increase
		 *  fine control while permitting full power
         */
        if (val > 0.0)
            val = (val * val);
        else
            val = -(val * val);
        
		/*
         *  Slew rate limiter - limit rate of change
         */
        
		change = val - lastVal;
		
		if (change > changeLimit)
			change = changeLimit;
		else if (change < -changeLimit)
			change = -changeLimit;
		
		return (lastVal += change);
		
        //return val;
		
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		end();
	}
	
}
