package frc.robot.subsystems;

import java.util.function.DoubleConsumer;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.SPI;

public class NavX  implements Sendable{

    static NavX instance;
    
    AHRS gyro;

    SendableBuilder builder;

    DoubleConsumer offsetConsumer;

    double offset;

    public NavX() {

        try {

            gyro = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException ex) {

			System.out.println("Error instantiating navX-MXP:  " + ex.getMessage());
        }
        
        gyro.reset();

        offset = 0;
    }

    /**
     * 
     * @return gyro angle w/ offset
     */
    public double getAngle() {

        return gyro.getAngle() % 360 + offset;
    }

    public void resetYaw() {

        gyro.zeroYaw();
    }


    public void setOffset(double offset) {

        this.offset = offset;
    }

    @Override
    public void initSendable(SendableBuilder builder) {

        builder.setSmartDashboardType("RobotBase");

        builder.addDoubleProperty("gyroAngle", () -> getAngle(), null);
        builder.addDoubleProperty("gyroOffset", () -> offset , offsetConsumer = offset -> setOffset(offset));
    }


    public static NavX getInstance() {
        
        if (instance == null) {
            
            instance = new NavX();
        }

        return instance;
    }
}