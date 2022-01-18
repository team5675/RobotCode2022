package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Spark;
import frc.libs.motors.SparkMaxMotor;
import frc.robot.Constants;
import frc.robot.DriverController;
import edu.wpi.first.wpilibj.DigitalInput;

public class Shooter{

    static Shooter instance;

    enum ShooterState{

        StartUp,
        Shooting,
        Idle
    }

    public Shooter getInstance(){

        if (instance == null) {

            instanace = new Shooter();
        }

        return instance;
    }

}