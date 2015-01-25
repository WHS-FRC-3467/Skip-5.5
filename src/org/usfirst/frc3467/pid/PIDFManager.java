package org.usfirst.frc3467.pid;


import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;


/*
 * class PIDFManager
constructor takes: Subsystemname, (PIDController  or CANTalon)
Manages PIDF constants:
    Display on SDB
    
    updateDisplay: Refresh SDB display only
    updatePIDF: Take values from SDB and write to prefs
    initPIDF: Read values from Prefs and update controller
            If Prefs is empty, default to 0's to start
            
    Put button on SDB to do updatePIDF
    
    Constructor & updateDisplay calls should be contingent on debugging flag
    
 */


public class PIDFManager {
	
	PIDController m_controller = null;
	CANTalon m_talon = null;
	String m_name;
	boolean m_hasFeedForward;
	
	Preferences prefs;
	
	public PIDFManager(String name, PIDController controller, boolean hasFeedForward) {
		this.m_name = name;
		this.m_controller = controller;
		this.m_hasFeedForward = hasFeedForward;
		updateDisplay();
	}
	
	public PIDFManager(String name, CANTalon talon, boolean hasFeedForward) {
		this.m_name = name;
		this.m_talon = talon;
		this.m_hasFeedForward = hasFeedForward;
		updateDisplay();
	}

	public void updateDisplay() {
		
		if (m_controller != null) {
			SmartDashboard.putNumber(m_name + " P", m_controller.getP());
			SmartDashboard.putNumber(m_name + " I", m_controller.getI());
			SmartDashboard.putNumber(m_name + " D", m_controller.getD());
			if (m_hasFeedForward)
				SmartDashboard.putNumber(m_name + " F", m_controller.getF());
			SmartDashboard.putNumber(m_name + " Error", m_controller.getError());
			SmartDashboard.putNumber(m_name + " Output", m_controller.get());
		}
		else {
			SmartDashboard.putNumber(m_name + " P", m_talon.getP());
			SmartDashboard.putNumber(m_name + " I", m_talon.getI());
			SmartDashboard.putNumber(m_name + " D", m_talon.getD());
			if (m_hasFeedForward)
				SmartDashboard.putNumber(m_name + " F", m_talon.getF());
			SmartDashboard.putNumber(m_name + " Error", m_talon.getClosedLoopError());
			SmartDashboard.putNumber(m_name + " Output", m_talon.get());
		}
	}
	
	public void initPIDF() {
	
		double P, I, D, F;
		
		
	}
	
	
	public void updatePIDF() {
		// Assign
		double p = SmartDashboard.getNumber(m_name + " P");
		double i = SmartDashboard.getNumber(m_name + " I");
		double d = SmartDashboard.getNumber(m_name + " D");
		if (m_hasFeedForward) {
			double f = SmartDashboard.getNumber(m_name + " F");
			m_controller.setPID(p, i, d, f);
		} else
			m_controller.setPID(p, i, d);
		
		// Read
		SmartDashboard.putNumber(m_name + " Error", m_controller.getError());
		SmartDashboard.putNumber(m_name + " Output", m_controller.get());
		SmartDashboard.putNumber(m_name + " Setpoint", m_controller.getSetpoint());
		// SmartDashboard.putNumber(name + " Input", controller.getSetpoint() + controller.getError());
	}

}
