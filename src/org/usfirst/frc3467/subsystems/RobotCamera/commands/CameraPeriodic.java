package org.usfirst.frc3467.subsystems.RobotCamera.commands;

import com.ni.vision.NIVision;

import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;


import org.usfirst.frc3467.commands.CommandBase;
/**
 *
 */
public class CameraPeriodic extends Command {
    int session;
    Image frame;
    
    public CameraPeriodic() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(CommandBase.usbcamera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name (ex "cam0") can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {


        /**
         * grab an image and provide it for the camera server
         * which will in turn send it to the dashboard.
		*/


            NIVision.IMAQdxGrab(session, frame, 1);
            
            CameraServer.getInstance().setImage(frame);

            /** robot code here! **/
            Timer.delay(0.005);		// wait for a motor update time
        }



    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        NIVision.IMAQdxStopAcquisition(session);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
