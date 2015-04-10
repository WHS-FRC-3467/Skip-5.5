/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3467;

import java.util.Vector;






import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.commands.autonomous.AutoCanGrabbersDrive;
import org.usfirst.frc3467.commands.autonomous.AutoCanGrabbersStayPut;
import org.usfirst.frc3467.commands.autonomous.AutoNon;
import org.usfirst.frc3467.commands.autonomous.AutoTimedTank;
import org.usfirst.frc3467.subsystems.Elevator.commands.elevatorDriveToFloor;
import org.usfirst.frc3467.subsystems.LEDs.LEDs;
import org.usfirst.frc3467.subsystems.LIDAR.LIDAR;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding
 * to each mode, as described in the IterativeRobot documentation. If you change the name of this
 * class or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot {

	Command autonomousCommand;
	SendableChooser autoChooser;
    CameraServer cServer;
    LIDAR lidar;

	public static Vector<PIDController> PIDList;
	
	public Robot() {
		PIDList = new Vector<PIDController>();
	}
	
	public void robotInit() {

		// Init camera and start camera server instance
     /*   cServer = CameraServer.getInstance();
        cServer.setQuality(50);
        cServer.setSize(1);
        //the camera name (ex "cam0") can be found through the roborio web interface
        cServer.startAutomaticCapture("cam0");
     */
        lidar = new LIDAR(Port.kMXP);
        lidar.start();
        // Initialize all subsystems
		CommandBase.init();
		
		// Add autonomous selector
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Default Auto", new AutoNon());
		autoChooser.addObject("Timed Tank", new AutoTimedTank());
		autoChooser.addObject("Can Grabber Auto - Move Right Side Only", new AutoCanGrabbersDrive());
		autoChooser.addObject("Can Grabber Auto - No Moving", new AutoCanGrabbersStayPut());
		
		SmartDashboard.putData("Auto", autoChooser);
		SmartDashboard.putNumber("Scoring Platform Height: Default 150", 150);
	}
	
	public void autonomousInit() {
		
		CommandBase.imu.getGyroOffset();
		// Reset all PID controllers
		for (int i = 0; i < PIDList.size(); i++) {
			PIDController controller = (PIDController) PIDList.elementAt(i);
			controller.reset();
			controller.enable();
		}

		// schedule the autonomous command
		autonomousCommand = (Command) autoChooser.getSelected();
		autonomousCommand.start();
	}
	
	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)  autonomousCommand.cancel();
		
		// Resets all integral terms in pid controllers
		for (int i = 0; i < PIDList.size(); i++) {
			PIDController controller = (PIDController) PIDList.elementAt(i);
			controller.reset();
			controller.enable();
		}
		
		
		if (CommandBase.elevator != null) {
			// If elevator has not been zeroed, queue up a command to do it now
			if (CommandBase.elevator.hasBeenZeroed() == false) {
				Scheduler.getInstance().add(new elevatorDriveToFloor());
			}
		}

		
	}
	
	public void disabledInit() {

		}
		

	
	public void disabledPeriodic(){
		CommandBase.imu.setGyroOffset(SmartDashboard.getNumber("Match START Gyro Offset"));
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		
		Scheduler.getInstance().run();
	}
	
	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
	
}
