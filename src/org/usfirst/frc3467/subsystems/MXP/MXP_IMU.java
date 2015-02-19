package org.usfirst.frc3467.subsystems.MXP;

import org.usfirst.frc3467.subsystems.MXP.commands.imuUpdateDisplay;

import edu.wpi.first.wpilibj.command.Subsystem;



import com.kauailabs.nav6.frc.IMU; 
//import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.SerialPort;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
*
*/
public class MXP_IMU extends Subsystem {
   
    SerialPort serial_port;
    IMU imu;  // Alternatively, use IMUAdvanced for advanced features
    //IMUAdvanced imu;
    boolean first_iteration;
	
    private static final boolean debugging = true;
	
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
	    	serial_port = new SerialPort(57600,SerialPort.Port.kMXP);
			
			if (serial_port == null)
				System.out.println("**** serial port is null :-\\");
			else
				System.out.println("**** serial port is good!");

			// You can add a second parameter to modify the 
			// update rate (in hz) from 4 to 100.  The default is 100.
			// If you need to minimize CPU load, you can set it to a
			// lower value, as shown here, depending upon your needs.
			
			// You can also use the IMUAdvanced class for advanced
			// features.
			
			byte update_rate_hz = 50;
			imu = new IMU(serial_port,update_rate_hz);
			//imu = new IMUAdvanced(serial_port, update_rate_hz);

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
    }
		   
    public double getYaw() {
    
    	if (imu != null)
    		return (double)(imu.getYaw());
    	else
    		return 0.0;
    }
    
	public void update() {

//		System.out.println("MXP_IMU.update() called..");
		
		// When calibration has completed, zero the yaw
        // Calibration is complete approximately 20 seconds
        // after the robot is powered on.
    	// During calibration, the robot should be still
        
        boolean is_calibrating = imu.isCalibrating();
        if ( first_iteration && !is_calibrating ) {
//            Timer.delay( 0.3 );
            imu.zeroYaw();
            first_iteration = false;
        }
        
        if (debugging) {
        	
        	// Update the dashboard with status and orientation
	        // data from the nav6 IMU
	        
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
	        
//	        SmartDashboard.putNumber(   "IMU_Accel_X",          imu.getWorldLinearAccelX());
//	        SmartDashboard.putNumber(   "IMU_Accel_Y",          imu.getWorldLinearAccelY());
//	        SmartDashboard.putBoolean(  "IMU_IsMoving",         imu.isMoving());
//	        SmartDashboard.putNumber(   "IMU_Temp_C",           imu.getTempC());
	        
//	        Timer.delay(0.2);
        }
    }
}
    
    
    
    

