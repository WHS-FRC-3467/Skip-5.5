package org.usfirst.frc3467.subsystems.DriveBase.commands;

import org.usfirst.frc3467.OI;
import org.usfirst.frc3467.commands.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveMecanum extends CommandBase {
	
	boolean m_useVoltage = false;
	
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

		// Deadband
		if (x > -0.08 && x < 0.08) x = 0;
		if (y > -0.08 && y < 0.08) y = 0;
		if (rotation > -0.08 && rotation < 0.08) rotation = 0;

        // Square the inputs (while preserving the sign) to increase
		// fine control while permitting full power
        if (x >= 0.0)
            x = (x * x);
        else
            x = -(x * x);
        
        if (y >= 0.0)
            y = (y * y);
        else
            y = -(y * y);
        
        if (rotation >= 0.0)
        	rotation = (rotation * rotation);
        else
        	rotation = -(rotation * rotation);
        
		SmartDashboard.putNumber(   "IMU_Yaw", imu.getYaw());
		drivebase.driveMecanum(x, y, rotation / 2.0, imu.getYaw());

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
