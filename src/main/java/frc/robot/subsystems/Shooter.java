package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.util.Color;
import frc.libs.motors.SparkMaxMotor;
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
import com.revrobotics.SparkMaxRelativeEncoder;
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


    String allianceColor;
    Queue<Integer> ballQueue;

    ColorSensorV3 colorSensor;
    Color bColor;
    DigitalInput ballInTopPos;
    int ballsin = 0;
    
    DigitalInput limitSwitchOne = new DigitalInput(1);
    DigitalInput limitSwitchTwo = new DigitalInput(2);
    boolean limitOne = limitSwitchOne.get();
    boolean limitTwo = limitSwitchTwo.get();

    public Shooter() {
        vision = Vision.getInstance();

        flywheelBlack = new CANSparkMax(Constants.SHOOTER_ID_1, MotorType.kBrushless);
        flywheelBlue = new CANSparkMax(Constants.SHOOTER_ID_2, MotorType.kBrushless);
        hoodMotor = new Spark(Constants.HOOD_ID);
        greenWheel = new Spark(Constants.SHOOTER_GATE_ID);

        ballInTopPos = new DigitalInput(Constants.INDEX_PROX);
        colorSensor = new ColorSensorV3(I2C.Port.kMXP);

        blackPID = flywheelBlack.getPIDController();
        bluePID = flywheelBlue.getPIDController();

        bluePID.setFF(0.00019);
        bluePID.setP(0.0001);
        blackPID.setFF(0.00019);
        blackPID.setP(0.0001);

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
        if(colorSensor.getProximity() < Constants.MIN_PROX_VALUE) {
            //add it to the queue
            ballQueue.add(colorSensor.getIR());

            ballsin++;
        }

        //while the gate is empty
        if(!ballInTopPos.get() && ballsin < 1) {

            greenWheel.set(0.5);
        }
    }

    public void pewpew() {

        blueRPMSetpoint = 26.186 * Math.pow(vision.getDistanceFromTarget(), 2) - 449.39 * vision.getDistanceFromTarget() + 3756.4;
        blackRPMSetpoint = 80.016 * vision.getDistanceFromTarget() + 1171.4;

        //TODO: Update Regression Model for Shooter
        //Low goal, 1000 on black, -1500 on blue

        //flywheelBlack.setRPMVelocity();
        //flywheelBlue.setRPMVelocity(2500 * (int)vision.getDistanceFromTarget());

        //blackPID.setReference(blackSetpoint.getDouble(0), ControlType.kVelocity);
        //bluePID.setReference(blueSetpoint.getDouble(0), ControlType.kVelocity);

        blackPID.setReference(blueRPMSetpoint, ControlType.kVelocity);
        bluePID.setReference(blackRPMSetpoint, ControlType.kVelocity);

        greenWheel.set(1);

        blackRPM.setDouble(blackEnc.getVelocity());
        blueRPM.setDouble(blueEnc.getVelocity());
    }

    public void idle() {

        blackPID.setReference(500, ControlType.kVelocity);
        bluePID.setReference(-500, ControlType.kVelocity);
    }

    public static Shooter getInstance() {

        if (instance == null) {

            instance = new Shooter();
        }

        return instance;
    }

}