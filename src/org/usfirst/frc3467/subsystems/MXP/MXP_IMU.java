package org.usfirst.frc3467.subsystems.MXP;

import org.usfirst.frc3467.subsystems.MXP.commands.imuUpdateDisplay;

import edu.wpi.first.wpilibj.command.Subsystem;





//import com.kauailabs.nav6.frc.IMU; 
//import com.kauailabs.nav6.frc.IMUAdvanced;
import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
*
*/
public class MXP_IMU extends Subsystem {
   
    SerialPort serial_port;
    //IMU imu;			// This class can be used w/nav6 and navX MXP.
    //IMUAdvanced imu;	// This class can be used w/nav6 and navX MXP.
    AHRS imu;			// This class can only be used w/the navX MXP.
    boolean first_iteration;
    
    private static final boolean debugging = true;
	
    // Gyro Offset (for starting matches with the robot NOT at zero)
	private double 	m_gyroOffset = 0;
	private static MXP_IMU instance;
	
	public static MXP_IMU getInstance() {
		return instance;
	}
	
	protected void initDefaultCommand() {
		this.setDefaultCommand(new imuUpdateDisplay());
	}
	
	public MXP_IMU() {
		
		instance = this;
		
		try {
	    	// Use SerialPort.Port.kOnboard if connecting nav6 to Roborio Rs-232 port
	    	// Use SerialPort.Port.kMXP if connecting navX MXP to the RoboRio MXP port
	    	// Use SerialPort.Port.kUSB if connecting nav6 or navX MXP to the RoboRio USB port
	    		
	        serial_port = new SerialPort(9600,SerialPort.Port.kUSB);
//	    	serial_port = new SerialPort(57600,SerialPort.Port.kMXP);
			if (serial_port == null)
				System.out.println("**** serial port is null :-\\");
			else
				System.out.println("**** serial port is good!");

			// You can add a second parameter to modify the 
			// update rate (in hz) from.  The minimum is 4.  
	    	// The maximum (and the default) is 100 on a nav6, 60 on a navX MXP.
			// If you need to minimize CPU load, you can set it to a
			// lower value, as shown here, depending upon your needs.
	    	// The recommended maximum update rate is 50Hz
			
			// You can also use the IMUAdvanced class for advanced
			// features on a nav6 or a navX MXP.
	    	
	    	// You can also use the AHRS class for advanced features on 
	    	// a navX MXP.  This offers superior performance to the
	    	// IMU Advanced class, and also access to 9-axis headings
	    	// and magnetic disturbance detection.  This class also offers
	    	// access to altitude/barometric pressure data from a
	    	// navX MXP Aero.
			
			byte update_rate_hz = 50;
			//imu = new IMU(serial_port,update_rate_hz);
			//imu = new IMUAdvanced(serial_port,update_rate_hz);
			imu = new AHRS(serial_port,update_rate_hz);
			
			if (imu == null)
				System.out.println("**** imu is null :-\\");
			else
				System.out.println("**** imu is good!");
			
		} catch( Exception ex ) {
			System.out.println("**** Error instantiating IMU");
			ex.printStackTrace();
		}
		
        if ( imu != null ) {
            LiveWindow.addSensor("IMU", "Gyro", imu);
        }
        first_iteration = true;
        SmartDashboard.putNumber("Match START Gyro Offset", m_gyroOffset);
    }
		   
    public double getYaw() {
    SmartDashboard.putNumber("Match START Gyro Offset", m_gyroOffset);
    	if (imu != null)
    		return (double)(imu.getYaw() + m_gyroOffset);
    	else
    		return 0.0;
    }
    
	public void zeroYaw() {
        m_gyroOffset = 0;
		imu.zeroYaw();
	}
	
	public void getGyroOffset(){
		m_gyroOffset = (SmartDashboard.getNumber("Match START Gyro Offset"));
	}
		
	public void setGyroOffset(double offset){
		m_gyroOffset = offset;
	}
		public void update() {

        // When calibration has completed, zero the yaw
        // Calibration is complete approaximately 20 seconds
        // after the robot is powered on.  During calibration,
        // the robot should be still
        
        boolean is_calibrating = imu.isCalibrating();
        if ( first_iteration && !is_calibrating ) {
//            Timer.delay( 0.3 );
            imu.zeroYaw();
            first_iteration = false;
        }
        
        if (debugging) {
        	
	        // Update the dashboard with status and orientation
	        // data from the navX IMU
	        
	        SmartDashboard.putBoolean(  "IMU_Connected",        imu.isConnected());
	        SmartDashboard.putBoolean(  "IMU_IsCalibrating",    imu.isCalibrating());
	        SmartDashboard.putNumber(   "IMU_Yaw",              imu.getYaw());
	        SmartDashboard.putNumber(   "IMU_Pitch",            imu.getPitch());
	        SmartDashboard.putNumber(   "IMU_Roll",             imu.getRoll());
	        SmartDashboard.putNumber(   "IMU_CompassHeading",   imu.getCompassHeading());
	        SmartDashboard.putNumber(   "IMU_Update_Count",     imu.getUpdateCount());
	        SmartDashboard.putNumber(   "IMU_Byte_Count",       imu.getByteCount());
	
	        // If you are using the IMUAdvanced class, you can also access the following
	        // additional functions, at the expense of some extra processing
	        // that occurs on the CRio processor
/*	        
	        SmartDashboard.putNumber(   "IMU_Accel_X",          imu.getWorldLinearAccelX());
	        SmartDashboard.putNumber(   "IMU_Accel_Y",          imu.getWorldLinearAccelY());
	        SmartDashboard.putBoolean(  "IMU_IsMoving",         imu.isMoving());
	        SmartDashboard.putNumber(   "IMU_Temp_C",           imu.getTempC());
	        
	        SmartDashboard.putNumber(   "Velocity_X",       	imu.getVelocityX() );
	        SmartDashboard.putNumber(   "Velocity_Y",       	imu.getVelocityY() );
	        SmartDashboard.putNumber(   "Displacement_X",       imu.getDisplacementX() );
	        SmartDashboard.putNumber(   "Displacement_Y",       imu.getDisplacementY() );
*/        
        }
    }
}
    
    
    
    

