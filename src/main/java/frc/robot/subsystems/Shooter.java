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


    String allianceColor;
    Queue<Integer> ballQueue;

    ColorSensorV3 colorSensor;
    Color bColor;
    DigitalInput ballInTopPos;
    int ballsin = 0;

    boolean first = true;
    double startTime;

    int boostIncr = 0;

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

        bluePID.setFF(0.0002);
        bluePID.setP(0.0002);
        blackPID.setFF(0.0002);
        blackPID.setP(0.0002);

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

        if(!ballInTopPos.get()) {

            greenWheel.set(1);
        } else greenWheel.set(0);
    }

    public void pewpew() {

        blueRPMSetpoint = -1 * (26.186 * Math.pow(vision.getDistanceFromTarget(), 2) - 449.39 * vision.getDistanceFromTarget() + 3816.4 + boostIncr);
        //blackRPMSetpoint = 80.016 * vision.getDistanceFromTarget() + 1171.4;

        blackRPMSetpoint = 0.3214 * Math.pow(vision.getDistanceFromTarget(), 5) - 18.482 * Math.pow(vision.getDistanceFromTarget(), 4)   
        + 415.11 * Math.pow(vision.getDistanceFromTarget(), 3) - 4537.3 * Math.pow(vision.getDistanceFromTarget(), 2) + 24138 * vision.getDistanceFromTarget() -48162 - boostIncr;

        //Low goal, 1000 on black, -1500 on blue

        //flywheelBlack.setRPMVelocity();
        //flywheelBlue.setRPMVelocity(2500 * (int)vision.getDistanceFromTarget());

        //blackPID.setReference(blackSetpoint.getDouble(0), ControlType.kVelocity);
        //bluePID.setReference(blueSetpoint.getDouble(0), ControlType.kVelocity);

        blackPID.setReference(blackRPMSetpoint, ControlType.kVelocity);
        bluePID.setReference(blueRPMSetpoint, ControlType.kVelocity);

        

        if(blackEnc.getVelocity() >= blackRPMSetpoint + 10 && blueEnc.getVelocity() <= blueRPMSetpoint + 10) {

            greenWheel.set(1);
        }

        

        blackRPM.setDouble(blackEnc.getVelocity());
        blueRPM.setDouble(blueEnc.getVelocity());

        blackSetpoint.setDouble(blackRPMSetpoint);
        blueSetpoint.setDouble(blueRPMSetpoint);
    }

    public void pewpewAuto(int blueRPM, int blackRPM, boolean isLinedUp) {

        //Low goal, 1000 on black, -1500 on blue

        //flywheelBlack.setRPMVelocity();
        //flywheelBlue.setRPMVelocity(2500 * (int)vision.getDistanceFromTarget());

        //blackPID.setReference(blackSetpoint.getDouble(0), ControlType.kVelocity);
        //bluePID.setReference(blueSetpoint.getDouble(0), ControlType.kVelocity);
        if(isLinedUp) {

        blackPID.setReference(blackRPM, ControlType.kVelocity);
        bluePID.setReference(blueRPM, ControlType.kVelocity);

        

        if(blackEnc.getVelocity() >= blackRPM + 10 && blueEnc.getVelocity() <= blueRPM + 10) {

            greenWheel.set(1);
        } else greenWheel.set(0);

        } else greenWheel.set(0);

        blackSetpoint.setDouble(blackRPMSetpoint);
        blueSetpoint.setDouble(blueRPMSetpoint);
    }


    public void idle() {

        blackPID.setReference(500, ControlType.kVelocity);
        bluePID.setReference(-500, ControlType.kVelocity);

        greenWheel.set(0);
    }

    public Boolean getProx() {

        return !ballInTopPos.get();
    }

    public void badBall() {

        blackPID.setReference(1000, ControlType.kVelocity);
        bluePID.setReference(-500, ControlType.kVelocity);

        greenWheel.set(1);
    }

    public void raiseBoost() {

        boostIncr += 20;
    }

    public void lowerBoost() {

        boostIncr -= 20;
    }

    public double getBlackRPM() {

        return blackEnc.getVelocity();
    }

    public double getBlackRPMSetpoint() {

        return blackRPMSetpoint;
    }

    public static Shooter getInstance() {

        if (instance == null) {

            instance = new Shooter();
        }

        return instance;
    }

}