package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.libs.motors.SparkMaxMotor;
import frc.robot.Constants;
import frc.robot.DriverController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3; 

public class Shooter {

    static Shooter instance;

    enum ShooterState {

        StartUp,
        Shooting,
        Idle,
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

    
    
    public Shooter() {
        vision = vision.getInstance();

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

    public void Adjust() {
        double K = -0.1;  //multiply by tx to adjust the shooty thing

        std.shared_ptr<NetworkTable> table = NetworkTable.getTable("limelight");
        float tx = table.GetNumber("tx");
        float tv = table.gerNumeber("tv");
        double adjust = (K * tx);

        if(tv == 0.0) {
            double spinAdjust = 0.3;
        }
        if(tv == 1.0) {

        }
    }

    public void balls() {
        int ballColor = hardwareMap.get(ColorSensorV3.class, "ballColor");
        int flop = (int) (5676 * .1);
        boolean teamBlue = true;
        
        if(teamBlue == true){
            if(ballColor == color.red) {
                flywheelOne.setRPMVelocity(flop);
                flywheelTwo.setRPMVelocity(flop);
            }
    
            if(ballColor == color.blue) {
                pewpew();
            }
        }
        if(teamBlue = false){
            if(ballColor == color.blue) {
                flywheelOne.setRPMVelocity(flop);
                flywheelTwo.setRPMVelocity(flop);
            }
    
            if(ballColor == color.red) {
                pewpew();
            }
        }
    }

    public void pewpew() {
        if(shooterState == ShooterState.StartUp) {
            ((CANSparkMax) hoodMotor).set(0.400001);

            int sexballs = (Integer) null;

            flywheelOne.setRPMVelocity(sexballs);
            flywheelTwo.setRPMVelocity(sexballs);
        }
        
        else if(shooterState == ShooterState.Shooting) {
            dist = vision.getDistanceFromTarget();

            int thirtyPerc = (int) (5676 * .3);
            int fourtyPerc = (int) (5676 * .4);
    
            if(dist < 20) {
                flywheelOne.setRPMVelocity(fourtyPerc);
                flywheelTwo.setRPMVelocity(fourtyPerc);
            }
    
            if(dist >= 20) {
                flywheelOne.setRPMVelocity(thirtyPerc);
                flywheelTwo.setRPMVelocity(fourtyPerc);
            }
    
            else {
                flywheelOne.setRPMVelocity((int) ((fourtyPerc) + .1));
                flywheelTwo.setRPMVelocity((int) ((fourtyPerc) + .1));
            }
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
        else if (shooterState == ShooterState.Shooting) {
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

            instance = new Shooter();
        }

        return instance;
    }

}