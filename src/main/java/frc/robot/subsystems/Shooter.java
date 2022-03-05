package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.util.Color;
import frc.libs.motors.SparkMaxMotor;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;

import java.util.Queue;

import com.revrobotics.ColorSensorV3; 

public class Shooter {

    static Shooter instance;

    SparkMaxMotor flywheelBlack; 
    SparkMaxMotor flywheelBlue;
    Spark hoodMotor;
    Spark greenWheel;


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

        flywheelBlack = new SparkMaxMotor(Constants.SHOOTER_ID_1);
        flywheelBlue = new SparkMaxMotor(Constants.SHOOTER_ID_2);
        hoodMotor = new Spark(Constants.HOOD_ID);
        greenWheel = new Spark(Constants.SHOOTER_GATE_ID);

        ballInTopPos = new DigitalInput(Constants.INDEX_PROX);
        colorSensor = new ColorSensorV3(I2C.Port.kMXP);

    }

    public void setcolor(String aColor) {

        allianceColor = aColor;
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
        
        if(vision.isTargeted()) {

            //TODO: Update Regression Model for Shooter

            flywheelBlack.setRPMVelocity(2500 * (int)vision.getDistanceFromTarget());
            flywheelBlue.setRPMVelocity(2500 * (int)vision.getDistanceFromTarget());
        }
    }

    public void idle() {

        flywheelBlack.setRPMVelocity(250);
        flywheelBlue.setRPMVelocity(250);
    }

    public static Shooter getInstance() {

        if (instance == null) {

            instance = new Shooter();
        }

        return instance;
    }

}