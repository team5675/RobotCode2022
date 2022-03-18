package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;

import java.util.Queue;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType; 

public class Shooter {

    static Shooter instance;

    CANSparkMax flywheelBlack; 
    CANSparkMax flywheelBlue;
    Spark hoodMotor;
    Spark greenWheel;

    SparkMaxPIDController blackPID;
    SparkMaxPIDController bluePID;

    RelativeEncoder blackEnc;
    RelativeEncoder blueEnc;

    NetworkTable dashboardTable;
    NetworkTableEntry blackRPM;
    NetworkTableEntry blueRPM;

    NetworkTableEntry blackSetpoint;
    NetworkTableEntry blueSetpoint;

    double blackRPMSetpoint;
    double blueRPMSetpoint;


    Vision vision;
    Drive drive = Drive.getInstance();
    ColorSensor colorSensor = ColorSensor.getInstance();


    String allianceColor;
    Queue<Integer> ballQueue;

    
    Color bColor;
    DigitalInput ballInTopPos;
    int ballsin = 0;

    boolean first = true;
    double startTime;

    public Shooter() {
        vision = Vision.getInstance();

        flywheelBlack = new CANSparkMax(Constants.SHOOTER_ID_1, MotorType.kBrushless);
        flywheelBlue = new CANSparkMax(Constants.SHOOTER_ID_2, MotorType.kBrushless);
        hoodMotor = new Spark(Constants.HOOD_ID);
        greenWheel = new Spark(Constants.SHOOTER_GATE_ID);

        ballInTopPos = new DigitalInput(Constants.INDEX_PROX);
        

        blackPID = flywheelBlack.getPIDController();
        bluePID = flywheelBlue.getPIDController();

        bluePID.setFF(0.00021);
        bluePID.setP(0.00006);
        bluePID.setD(0.00071);

        blackPID.setFF(0.0002);
        blackPID.setP(0.00025);
        blackPID.setD(0.0006);

        blackEnc = flywheelBlack.getEncoder();
        blueEnc = flywheelBlue.getEncoder();

        dashboardTable = NetworkTableInstance.getDefault().getTable("dashboard");

        blackRPM = dashboardTable.getEntry("blackRPM");
        blueRPM = dashboardTable.getEntry("blueRPM");

        blackSetpoint = dashboardTable.getEntry("blackSetpoint");
        blueSetpoint = dashboardTable.getEntry("blueSetpoint");

        blackSetpoint.setDouble(0);
        blueSetpoint.setDouble(0);

    }

    public void setcolor(String aColor) {

        allianceColor = aColor;
        System.out.println(allianceColor);
    }

    public void loop() {

        //if we have a ball in the gate
        /*if(colorSensor.getProximity() < Constants.MIN_PROX_VALUE) {
            //add it to the queue
            ballQueue.add(colorSensor.getIR());

            ballsin++;
        }

        //while the gate is empty
        if(!ballInTopPos.get() && ballsin < 1) {

            greenWheel.set(0.5);
        }*/

        if(getProx()) {

            greenWheel.set(0);
        } else greenWheel.set(.5);
    }

    public void pewpew() {

        blueRPMSetpoint = -1 * (26.186 * Math.pow(vision.getDistanceFromTarget(), 2) - 449.39 * vision.getDistanceFromTarget() + 3766.4);
        //blackRPMSetpoint = 80.016 * vision.getDistanceFromTarget() + 1171.4;

        blackRPMSetpoint = (0.3214 * Math.pow(vision.getDistanceFromTarget(), 5) - 18.482 * Math.pow(vision.getDistanceFromTarget(), 4) 
        + 415.11 * Math.pow(vision.getDistanceFromTarget(), 3) - 4537.3 * Math.pow(vision.getDistanceFromTarget(), 2) + 24138 * vision.getDistanceFromTarget() -48192);

        //Low goal, 1000 on black, -1500 on blue

        //flywheelBlack.setRPMVelocity();
        //flywheelBlue.setRPMVelocity(2500 * (int)vision.getDistanceFromTarget());

        //blackPID.setReference(blackSetpoint.getDouble(0), ControlType.kVelocity);
        //bluePID.setReference(blueSetpoint.getDouble(0), ControlType.kVelocity);

        blackPID.setReference(blackRPMSetpoint, ControlType.kVelocity);
        bluePID.setReference(blueRPMSetpoint, ControlType.kVelocity);

        

        if(blackEnc.getVelocity() >= blackRPMSetpoint && blueEnc.getVelocity() <= blueRPMSetpoint) {

            greenWheel.set(1);

            try {
                wait(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        

        blackRPM.setDouble(blackEnc.getVelocity());
        blueRPM.setDouble(blueEnc.getVelocity());

        blackSetpoint.setDouble(blackRPMSetpoint);
        blueSetpoint.setDouble(blueRPMSetpoint);
    }

    public void pewpewAuto(double blackAutoRPM, double blueAutoRPM, boolean isLinedUp) {

        if(isLinedUp) {

            blackPID.setReference(blackAutoRPM, ControlType.kVelocity);
            bluePID.setReference(blueAutoRPM, ControlType.kVelocity);
        
        

        if(blackEnc.getVelocity() >= blackRPMSetpoint + 50 && blueEnc.getVelocity() <= blueRPMSetpoint - 50) {

            greenWheel.set(1);
        } else {
            greenWheel.set(0);
        }

        } else {

            greenWheel.set(0);
        }

        

        blackRPM.setDouble(blackEnc.getVelocity());
        blueRPM.setDouble(blueEnc.getVelocity());

        blackSetpoint.setDouble(blackRPMSetpoint);
        blueSetpoint.setDouble(blueRPMSetpoint);
    }

    public void idle() {

        blackPID.setReference(500, ControlType.kVelocity);
        bluePID.setReference(-500, ControlType.kVelocity);
    }

    public void stop() {
        greenWheel.set(0);
    }

    public double getBlackRPM() {

        return blackEnc.getVelocity();
    }

    public double getBlueRPM() {

        return blueEnc.getVelocity();
    }

    public double getBlackRPMSetpoint() {

        return blackRPMSetpoint;
    }

    public Boolean getProx() {

        return !ballInTopPos.get();
    }

    public static Shooter getInstance() {

        if (instance == null) {

            instance = new Shooter();
        }

        return instance;
    }

}