package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;
import org.usfirst.frc3467.subsystems.Elevator.Elevator;

/**
 *  Drive elevator manually, either by joystick or by fixed rate.
 */
public class elevatorDriveToFloor extends CommandBase {

	boolean isFinished = false;
	
	public elevatorDriveToFloor() {
    	requires(elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.initManualMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	isFinished = elevator.driveToPosition(Elevator.kDown_FixedPlus, 0.0);
    }

    // This method will never return true; this command must always be interrupted.
     protected boolean isFinished() 
     {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	elevator.driveManual(Elevator.kStop);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
