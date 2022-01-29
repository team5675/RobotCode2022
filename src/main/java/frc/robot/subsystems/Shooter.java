/**package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Spark;
import frc.libs.motors.SparkMaxMotor;
import frc.robot.Constants;
import frc.robot.DriverController;
import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class Shooter {

    static Shooter instance;

    enum ShooterState {

        StartUp,
        Shooting,
        Idle
    }

    static ShooterState shooterState;
    SparkMaxMotor flywheelOne;
    SparkMaxMotor flywheelTwo;
    Spark hoodMotor;
    Spark gate;

    Vision vision;
    DriverController driverController = DriverController.getInstance();
    Drive drive = Drive.getInstance();

    NetworkTable logTable;
    NetworkTableEntry currentVelocity;
    NetworkTableEntry velocityGoal;

    double hoodVelocity;

    double hoodP;
    double hoodD;
    int RPM;
    double dist;
    double totalHeight;
    double cartHeight;
    boolean highTarget;

    DigitalInput hoodLowLimit;
    DigitalInput hoodHighLimit;

    SparkMaxMotor Motor1;
    CANSparkMaxlowLevel MotorType;
    
    public Shooter() {
        vision = Vision.getInstance();

        hoodAngle = new AnalogInput(0 , 1);

        flywheelOne = new SparkMaxMotor(Constants.SHOOTER_ID_1);
        flywheelTwo = new SparkMaxMotor(Constants.SHOOTER_ID_2);
        hoodMotor = new Spark(Constants.HOOD_ID);
        gate = new Spark(Constants.SHOOTER_GATE_ID);

        flywheelOne.configurePID(Constants.SHOOTER_KP, 0, Constants.SHOOTER_KD, Constants.SHOOTER_KF);
        flywheelTwo.configurePID(Constants.SHOOTER_KP, 0, Constants.SHOOTER_KD, Constants.SHOOTER_KF);

        logTable = NetworkTableInstance.getDefault().getTable("log");
        currentVelocity = logTable.getEntry("currentVelocity");
        velocityGoal = logTable.getEntry("velocityGoal");

        MotorType = new.CANSparkMaxLowLevel; 
        Motor1 = new.CANSparkMax(1 , Motortype);

    }

    public void Adjust() {
        float Cst = -0.1;  //multiply by tx to adjust the shooty thing

        std.shared_ptr<NetworkTable> table = NetworkTable.getTable("limelight");
        float tx = table.GetNumber("tx");
        float tv = table.gerNumeber("tv");
        adjust = (Cst * tx);

        if(joystick.GetRawButton(9))

        if(tv == 0.0) {
            adjust = 0.3

        }
    }

    public void pewpew() {
        dist = vision.getDistanceFromTarget();

        int thirtyPerc = (5676 * .3);
        int fourtyPerc = (5676 * .4);

        if(dist < 20) {
            flywheelOne = setRPM fourtyPerc();
            flywheelTwo = setRPM fourtyPerc();

        }

        if(dist >= 20) {
            flywheelOne = setRPM thirtyPerc();
            flywheelTwo = setRPM fourtyPerc();

        }
        else {
            flywheelOne = setRPM fourtyPerc();
            flywheelTwo = setRPM fourtyPerc();
        }
    }
     
    public void run() {

        if (shooterState == ShooterState.StartUp)
        {

            hoodMotor.set(0.40001);

            if (hoodLowLimit.get() == false)
            {

                shooterState = ShooterState.Idle;
                //hoodEncoder.reset();
                hoodMotor.set(0);
            }
        }
        else if (shooterState == ShooterState.Shooting)
        {
            alignHood(); //hightarget is CLOSER

            if(driverController.getGate()) {
                gate.set(1);
            }
            else gate.set(0);

            if(driverController.getStopFlywheel()) {
                flywheelOne.setRPMVelocity(0);
                flywheelTwo.setRPMVelocity(0);
            } else {
                flywheelOne.setRPMVelocity((RPM + 250) * -1);
                flywheelTwo.setRPMVelocity((RPM + 250) * -1);
            }
            //if(Math.abs(hoodAngleTarget - hoodAngle) < 5) gate.set(1);
            //else gate.set(0);

            shooterState = ShooterState.Idle;
        }
        else
        {

            gate.set(0);
            hoodMotor.set(0);
            if(driverController.getStopFlywheel()) {
                flywheelOne.setRPMVelocity(0);
                flywheelTwo.setRPMVelocity(0);
            } else {
                flywheelOne.setRPMVelocity((int)(0.75 * RPM));
                flywheelTwo.setRPMVelocity((int)(0.75 * RPM));
            }
        }
    }

    public void alignHood() {
        if(vision.getDistanceFromTarget() < 9.5) highTarget = true;
        else highTarget = false;
            
        if(!highTarget) {
            if(!hoodHighLimit.get()) hoodMotor.set(0);
            else hoodMotor.set(-0.6);
        }
        else {
            if(!hoodLowLimit.get()) hoodMotor.set(0);
            else hoodMotor.set(0.6);
        }
    }

    public void shoot() {

        if (shooterState != ShooterState.StartUp)
        {

            shooterState = ShooterState.Shooting;
        }
    }

    public void stop() {
        shooterState = ShooterState.Idle;
    }


    public double getVelocity() {
        return (flywheelOne.getVelocity() + flywheelTwo.getVelocity()) / 2;
    }

    public String getState() {
        if(shooterState == ShooterState.StartUp) return "StartUp";
        else if(shooterState == ShooterState.Shooting) return "Shooting";
        else return "Idle";
    }


    public Shooter getInstance() {

        if (instance == null) {

            instanace = new Shooter();
        }

        return instance;
    }

}*/