package org.usfirst.frc3467.pid;

import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/*
 * Class PIDF_CANTalon
 * 
 * Essentially a wrapper around a CANTalon class instance
 * 
 * Manages PIDF constants and updates SmartDashboard/LiveWindow
 * with information useful for running a CANTalon in closed-loop mode.
 * 
 * Displayed on SmartDashboard/LiveWindow:
 * P:
 * I:
 * D:
 * F:
 * Setpoint:
 * Current Position:
 * Tolerance:
 * Current Error:
 * Output:
 * Enabled:
 * 
 */

public class PIDF_CANTalon implements LiveWindowSendable {
	
	private String m_name;
	private CANTalon m_talon;
    private double m_tolerance;  // position range used to check if on target
	private boolean m_hasFeedForward;
    
	private ITable table = null;
    
    private double m_P;     // factor for "proportional" control
    private double m_I;     // factor for "integral" control
    private double m_D;     // factor for "derivative" control
    private double m_F;     // factor for feedforward term
    private double m_setpoint = 0.0;
    
	// Controls display to SmartDashboard
	private static final boolean debugging = true;
	

	public PIDF_CANTalon(String name, CANTalon talon, double tolerance,
							boolean hasFeedForward) {
		this.m_name = name;
		this.m_talon = talon;
	    this.m_tolerance = tolerance;
		this.m_hasFeedForward = hasFeedForward;
	
		if (debugging)
			initSmartDashboard();
	}

	public CANTalon getTalon() {
		return m_talon;
	}
	
	private void initSmartDashboard () {
		
		SmartDashboard.putNumber(m_name + " P", m_talon.getP());
		SmartDashboard.putNumber(m_name + " I", m_talon.getI());
		SmartDashboard.putNumber(m_name + " D", m_talon.getD());
		SmartDashboard.putNumber(m_name + " F", m_talon.getF());
		
    	SmartDashboard.putNumber(m_name + " Setpoint", m_setpoint);
    	SmartDashboard.putNumber(m_name + " Position", m_talon.getPosition());
    	SmartDashboard.putNumber(m_name + " Tolerance", m_tolerance);
    	SmartDashboard.putNumber(m_name + " Error", m_talon.getClosedLoopError());
    	SmartDashboard.putNumber(m_name + " Output", m_talon.get());
    	SmartDashboard.putBoolean(m_name + " Enabled", m_talon.isControlEnabled());

	}
	
    /**
     * Set the PID Controller gain parameters.
     * Set the proportional, integral, and differential coefficients.
     * @param p Proportional coefficient
     * @param i Integral coefficient
     * @param d Differential coefficient
     */
    public synchronized void setPID(double p, double i, double d) {
        m_P = p;
        m_I = i;
        m_D = d;

        m_talon.setProfile(0);
        m_talon.setP(p);
        m_talon.setI(i);
        m_talon.setD(d);
        
        if (table != null) {
            table.putNumber("p", p);
            table.putNumber("i", i);
            table.putNumber("d", d);
        }

        if (debugging) {
    		SmartDashboard.putNumber(m_name + " P", m_talon.getP());
    		SmartDashboard.putNumber(m_name + " I", m_talon.getI());
    		SmartDashboard.putNumber(m_name + " D", m_talon.getD());
        }
    }

    /**
    * Set the PID Controller gain parameters.
    * Set the proportional, integral, and differential coefficients.
    * @param p Proportional coefficient
    * @param i Integral coefficient
    * @param d Differential coefficient
    * @param f Feed forward coefficient
    */
    public synchronized void setPID(double p, double i, double d, double f) {
        m_P = p;
        m_I = i;
        m_D = d;
        m_F = f;

        m_talon.setProfile(0);
        m_talon.setP(p);
        m_talon.setI(i);
        m_talon.setD(d);
        m_talon.setF(f);
        
        if (table != null) {
            table.putNumber("p", p);
            table.putNumber("i", i);
            table.putNumber("d", d);
            table.putNumber("f", f);
        }
        if (debugging) {
    		SmartDashboard.putNumber(m_name + " P", m_talon.getP());
    		SmartDashboard.putNumber(m_name + " I", m_talon.getI());
    		SmartDashboard.putNumber(m_name + " D", m_talon.getD());
    		SmartDashboard.putNumber(m_name + " F", m_talon.getF());
        }
    }

    /**
     * Get the Proportional coefficient
     * @return proportional coefficient
     */
    public synchronized double getP() {
        return m_P;
    }

    /**
     * Get the Integral coefficient
     * @return integral coefficient
     */
    public synchronized double getI() {
        return m_I;
    }

    /**
     * Get the Differential coefficient
     * @return differential coefficient
     */
    public synchronized double getD() {
        return m_D;
    }

    /**
     * Get the Feed forward coefficient
     * @return feed forward coefficient
     */
    public synchronized double getF() {
        return m_F;
    }

    /**
     * Set the setpoint for the PIDController
     * @param setpoint the desired setpoint
     */
    public synchronized void setSetpoint(double setpoint) {

    	m_setpoint = setpoint;
    	m_talon.set(setpoint);;

    	reportPosition(m_talon.getPosition());
    	
        if (table != null)
            table.putNumber("setpoint", m_setpoint);
        
        if (debugging)
        	SmartDashboard.putNumber(m_name + " Setpoint", m_setpoint);

    }

    /**
     * Returns the current setpoint of the PIDController
     * @return the current setpoint
     */
    public synchronized double getSetpoint() {

    	m_setpoint = m_talon.getSetpoint();
    	return m_setpoint;
    }

    /**
     * Set the position of the associated encoder
     * @param position - the desired position
     */
    public synchronized void setPosition(double position) {
    	m_talon.setPosition(position);
        reportPosition(position);

    }

    /**
     * Returns the current position of the associated encoder
     * @return the current position
     */
    public synchronized double getPosition() {
    	double pos = m_talon.getPosition();
    	reportPosition(pos);	
    	return pos;

    }

    private void reportPosition(double position) {
        if (table != null)
            table.putNumber("position", position);
        
        if (debugging)
        	SmartDashboard.putNumber(m_name + " Position", position);

    }
    
    /**
     * Tolerance is the total range of acceptable values on either side
     * of the desired setpoint.
     */
    public void setTolerance (double tolerance) {
        m_tolerance = tolerance;
        
        if (debugging)
        	SmartDashboard.putNumber(m_name + " Tolerance", m_tolerance);

    }

    public synchronized boolean onTarget() {

    	return (Math.abs(getSetpoint() - m_talon.getPosition()) <= m_tolerance);

    }

    /**
     * Begin running the PIDController
     */
    public synchronized void enable() {
        
    	m_talon.enableControl();

        if (table != null) {
            table.putBoolean("enabled", true);
        }
        if (debugging)
        	SmartDashboard.putBoolean(m_name + " Enabled", true);
    }

    /**
     * Stop running the PIDController, this sets the output to zero before stopping.
     */
    public synchronized void disable() {
        m_talon.disableControl();

        if (table != null) {
            table.putBoolean("enabled", false);
        }
        if (debugging)
        	SmartDashboard.putBoolean(m_name + " Enabled", false);
    }

    /**
     * Return true if PIDController is enabled.
     */
    public synchronized boolean isEnabled() {

    	return m_talon.isControlEnabled();
    }

	/*
	 * Code to support update of PIDF constants from a button on the SmartDashboard
	 */
    public void updatePIDF() {

    	// Assign
		double p = SmartDashboard.getNumber(m_name + " P");
		double i = SmartDashboard.getNumber(m_name + " I");
		double d = SmartDashboard.getNumber(m_name + " D");
		if (m_hasFeedForward) {
			double f = SmartDashboard.getNumber(m_name + " F");
			setPID(p, i, d, f);
		} else
			setPID(p, i, d);
		
	}

    
/*
 * Overrides for LiveWindowSendable
 */
    
    @Override
    public String getSmartDashboardType() {
        return "PIDF_CANTalon";
    }

    private final ITableListener listener = new ITableListener()
    {
        @Override
        public void valueChanged(ITable table, String key, Object value, boolean isNew)
        {    	
            if (key.equals("p") ||
            	key.equals("i") ||
            	key.equals("d") ||
            	key.equals("f"))
            {
                if (getP() != table.getNumber("p", 0.0) ||
                	getI() != table.getNumber("i", 0.0) ||
                    getD() != table.getNumber("d", 0.0) ||
                    getF() != table.getNumber("f", 0.0))
                    	setPID(table.getNumber("p", 0.0),
                    		   table.getNumber("i", 0.0),
                    		   table.getNumber("d", 0.0),
                    		   table.getNumber("f", 0.0));
            }
            else if (key.equals("setpoint"))
            {
                if (getSetpoint() != ((Double) value).doubleValue())
                    setSetpoint(((Double) value).doubleValue());
            }
            else if (key.equals("tolerance"))
            {
                if (m_tolerance != ((Integer) value).intValue())
                    m_tolerance = (((Integer) value).intValue());
            }
            else if (key.equals("enabled"))
            {
                if (isEnabled() != ((Boolean) value).booleanValue())
                {
                    if (((Boolean) value).booleanValue())
                        enable();
                    else
                        disable();
                }
            }
        }
    };

    @Override
    public void initTable(ITable table)
    {
        if(this.table!=null)
            this.table.removeTableListener(listener);

        this.table = table;
        if(table!=null)
        {
            table.putNumber("p", getP());
            table.putNumber("i", getI());
            table.putNumber("d", getD());
            table.putNumber("f", getF());
            table.putNumber("setpoint", getSetpoint());
            table.putNumber("position", getPosition());
            table.putNumber("tolerance", m_tolerance);
            table.putNumber("error", m_talon.getClosedLoopError());
            table.putNumber("output", m_talon.getOutputVoltage());
            table.putBoolean("enabled", isEnabled());
            table.addTableListener(listener, false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITable getTable() {
        return table;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTable() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startLiveWindowMode() {
        disable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopLiveWindowMode() {
    }
}

