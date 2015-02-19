package org.usfirst.frc3467.commands;

import java.util.Vector;

import org.usfirst.frc3467.OI;
import org.usfirst.frc3467.Robot;
import org.usfirst.frc3467.subsystems.DriveBase.DriveBase;
import org.usfirst.frc3467.subsystems.Elevator.Conveyor;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;
import org.usfirst.frc3467.subsystems.Elevator.Indexer;
import org.usfirst.frc3467.subsystems.LEDs.LEDs;
import org.usfirst.frc3467.subsystems.MXP.MXP_IMU;




import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class CommandBase extends Command {
	public static OI oi;
	public static CommandBase cb;
	public static DriveBase drivebase;
	public static Elevator elevator;
	public static Indexer indexer;
	public static Conveyor conveyor;
	public static LEDs leds;
	public static MXP_IMU imu;

	public static Vector<Subsystem> subsystemList;
	
	public static void init() {
		subsystemList = new Vector<Subsystem>();

		// Add new subsystems to the list		
		drivebase = new DriveBase();
		subsystemList.addElement(drivebase);
		Robot.PIDList.addElement((PIDController)drivebase.getPIDController());
		
		elevator = new Elevator();
		subsystemList.addElement(elevator);
		
		indexer = new Indexer();
		subsystemList.addElement(indexer);
		
		conveyor = new Conveyor();
		subsystemList.addElement(conveyor);
		
//		leds = new LEDs();
//		subsystemList.addElement(leds);
		
		imu = new MXP_IMU();
		subsystemList.addElement(imu);
		
		oi = new OI();
		oi.BindCommands();
		
	}
	
	public CommandBase() {
		super();
		cb = this;
	}
	
	public CommandBase(String name) {
		super(name);
	}
	

}
