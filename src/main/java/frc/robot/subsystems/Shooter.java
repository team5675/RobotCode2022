package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Spark;
import frc.libs.motors.SparkMaxMotor;
import frc.robot.Constants;
import frc.robot.DriverController;
import edu.wpi.first.wpilibj.DigitalInput;

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
    boolean highTarget;

    DigitalInput hoodLowLimit;
    DigitalInput hoodHighLimit;
    
    
    public Shooter() {
        vision = Vision.getInstance();

        hoodLowLimit = new DigitalInput(0);
        hoodHighLimit = new DigitalInput(1);

        flywheelOne = new SparkMaxMotor(Constants.SHOOTER_ID_1);
        flywheelTwo = new SparkMaxMotor(Constants.SHOOTER_ID_2);
        hoodMotor = new Spark(Constants.HOOD_ID);
        gate = new Spark(Constants.SHOOTER_GATE_ID);

        flywheelOne.configurePID(Constants.SHOOTER_KP, 0, Constants.SHOOTER_KD, Constants.SHOOTER_KF);
        flywheelTwo.configurePID(Constants.SHOOTER_KP, 0, Constants.SHOOTER_KD, Constants.SHOOTER_KF);

        logTable = NetworkTableInstance.getDefault().getTable("log");
        currentVelocity = logTable.getEntry("currentVelocity");
        velocityGoal = logTable.getEntry("velocityGoal");
    }
     
    public void run()
    {
        dist = vision.getDistanceFromTarget();
        if(dist < 8) RPM = 3050;
        else if(dist < 11.3) RPM = 4000;
        else if(dist > 17.5) RPM = 2700;
        else RPM = 2625;
        //System.out.println("Encoder: " + hoodEncoder.getDistance());
        //System.out.println("Distance" + vision.getDistanceFromTarget());

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

}