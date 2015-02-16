package org.usfirst.frc3467.subsystems.Elevator.commands;

import org.usfirst.frc3467.commands.CommandBase;

/**
 *
 */
public class elevatorLevelsUpDown extends CommandBase {

	private boolean elevToteDirection;
	
    public elevatorLevelsUpDown(boolean updown) {
    	//TRUE = add Up one tote level. If false go down 1 level
    	requires(elevator);
    	elevToteDirection = updown;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.initPositionalMode();
//		setTimeout(2);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(elevToteDirection == true){
    		elevator.addTote();
    	}
    	if(elevToteDirection == false)
    	{
    		elevator.subtractTote();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    // When this command is finished, the default command (elevatorDrive) will take
    // over and switch the CANTalon to PercentVBus mode.
    protected boolean isFinished() {
		return false;

    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
