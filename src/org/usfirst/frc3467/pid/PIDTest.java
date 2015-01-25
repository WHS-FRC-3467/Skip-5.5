package org.usfirst.frc3467.pid;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDTest {
	PIDController controller;
	String name;
	boolean hasFeedForward;
	
	public PIDTest(String name, PIDController controller, boolean hasFeedForward) {
		this.name = name;
		this.controller = controller;
		this.hasFeedForward = hasFeedForward;
		SmartDashboard.putNumber(name + " P", controller.getP());
		SmartDashboard.putNumber(name + " I", controller.getI());
		SmartDashboard.putNumber(name + " D", controller.getD());
		if (hasFeedForward)
			SmartDashboard.putNumber(name + " F", controller.getF());
		SmartDashboard.putNumber(name + " Error", controller.getError());
		SmartDashboard.putNumber(name + " Output", controller.get());
	}
	
	public void update() {
		// Assign
		double p = SmartDashboard.getNumber(name + " P");
		double i = SmartDashboard.getNumber(name + " I");
		double d = SmartDashboard.getNumber(name + " D");
		if (hasFeedForward) {
			double f = SmartDashboard.getNumber(name + " F");
			controller.setPID(p, i, d, f);
		} else
			controller.setPID(p, i, d);
		
		// Read
		SmartDashboard.putNumber(name + " Error", controller.getError());
		SmartDashboard.putNumber(name + " Output", controller.get());
		SmartDashboard.putNumber(name + " Setpoint", controller.getSetpoint());
		// SmartDashboard.putNumber(name + " Input", controller.getSetpoint() + controller.getError());
	}
}
