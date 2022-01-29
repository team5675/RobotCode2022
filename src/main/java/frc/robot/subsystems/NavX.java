package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;


public class NavX {

    static NavX instance;
    
    AHRS gyro;


    public NavX() {

        try {

            gyro = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException ex) {

			System.out.println("Error instantiating navX-MXP:  " + ex.getMessage());
        }
        
        gyro.reset();
    }

    public double getAngle() {

        return gyro.getAngle() % 360;
    }

    public void resetYaw() {

        gyro.zeroYaw();
    }


    public void loop() {

    }


    public static NavX getInstance() {
        
        if (instance == null) {
            
            instance = new NavX();
        }

        return instance;
    }
}