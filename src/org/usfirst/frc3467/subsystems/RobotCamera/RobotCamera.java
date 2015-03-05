package org.usfirst.frc3467.subsystems.RobotCamera;

import org.usfirst.frc3467.subsystems.RobotCamera.commands.CameraPeriodic;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RobotCamera extends Subsystem {
    
    CameraServer 	m_server;
	int				m_session;
    Image			m_frame;

    public void initDefaultCommand() {
    	setDefaultCommand(new CameraPeriodic());
    }

    public RobotCamera() {

    	m_server = CameraServer.getInstance();
        m_server.setQuality(50);
 //       m_server.setSize(CameraServer.k);
        
        m_frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name (ex "cam0") can be found through the roborio web interface
        m_session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(m_session);

        
    }

    public void setImage() {
        /**
         * grab an image and provide it for the camera server
         * which will in turn send it to the dashboard.
		*/
        NIVision.IMAQdxGrab(m_session, m_frame, 1);
        
        m_server.setImage(m_frame);
        
    }

    public void stop() {
    	NIVision.IMAQdxStopAcquisition(m_session);

    }
}

