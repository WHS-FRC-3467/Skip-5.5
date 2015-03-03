package org.usfirst.frc3467.subsystems.RobotCamera;

import org.usfirst.frc3467.subsystems.RobotCamera.commands.CameraPeriodic;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RobotCamera extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new CameraPeriodic());
    }
}

