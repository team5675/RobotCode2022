package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.libs.motors.SparkMaxMotor;
import frc.robot.Constants;
import frc.robot.DriverController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

import com.revrobotics.SparkMaxLimitSwitch;

import java.sql.Time;

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

    //Motors for the shooters
    static ShooterState shooterState;
    SparkMaxMotor flywheelOne; 
    SparkMaxMotor flywheelTwo;
    Spark hoodMotor;
    SparkMaxMotor greenWheel;

    //Color Stuffs
    boolean teamBlue;
    boolean ballOne;
    boolean ballTwo;
    boolean blue;
    boolean red;


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
    double flop;
    double dist;
    double totalHeight;
    double cartHeight;
    boolean highTarget;

    DigitalInput hoodLowLimit;
    DigitalInput hoodHighLimit;

    DigitalInput limitSwitchOne = new DigitalInput(1);
    DigitalInput limitSwitchTwo = new DigitalInput(2);
    boolean limitOne = limitSwitchOne.get();
    boolean limitTwo = limitSwitchTwo.get();

    public Shooter() {
        vision = vision.getInstance();

        flywheelOne = new SparkMaxMotor(Constants.SHOOTER_ID_1);
        flywheelTwo = new SparkMaxMotor(Constants.SHOOTER_ID_2);
        hoodMotor = new Spark(Constants.HOOD_ID);
        greenWheel = new SparkMaxMotor(Constants.SHOOTER_GATE_ID);

        flywheelOne.configurePID(Constants.SHOOTER_KP, 0, Constants.SHOOTER_KD, Constants.SHOOTER_KF);
        flywheelTwo.configurePID(Constants.SHOOTER_KP, 0, Constants.SHOOTER_KD, Constants.SHOOTER_KF);

        logTable = NetworkTableInstance.getDefault().getTable("log");
        currentVelocity = logTable.getEntry("currentVelocity");
        velocityGoal = logTable.getEntry("velocityGoal");


    }
    
    public void limeLight() {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry tx = table.getEntry("tx");

    double targetOffsetAngle_Vertical = ty.getDouble(0.0);
    double limelightMountAngleDegrees = 20.0;
    double limelightLensHeightInches = 37.5;
    double goalHeightInches = 104.5;
    double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
    float KpAim = -0.1f;
    float KpDistance = -0.1f;
    float minAimCommand = 0.05f;

    double distanceFromLimelightToGoalInches = (goalHeightInches - limelightHeightInches)/Math.tan(angleToGoalRadians);*/

    if (/**joysrtick input*/)
/*{
        float heading_error = -tx;
        float distance_error = -ty;
        float steering_adjust = 0.0f;

        if (tx > 1.0) {
                steering_adjust = KpAim*heading_error - minAimCommand;
        }
        else if (tx < -1.0) {
                steering_adjust = KpAim*heading_error + minAimCommand;
        }

        float distance_adjust = KpDistance * distance_error;

        left_command += steering_adjust + distance_adjust;
        right_command -= steering_adjust + distance_adjust;
}*/


    }

    public void teamColor() {
        if(/**ButtonPress*/) {
            teamBlue = true;
        }
        else if(/**ButtonPress*/) {
            teamBlue = false;
        }

    }

    public void ballColor() {
        int ballColor = ColorSensorV3.get(ColorSensorV3.class, "ballColor");

        if(color.blue) {
            blue = true;
        }
        else if(color.red) {
            red = true;
        }

    }
    
    public void flop() {
        double flop = (5676 * .1);

        flywheelOne.setRPMVelocity((int) flop);
        flywheelTwo.setRPMVelocity((int) flop);

    }
    
    public void limitSwitchOne() {
        if(limitOne = true) {
            do {
                greenWheel.setRPMVelocity(1000);
            } while (limitOne = true); 
            
            ballColor();

        }

    }

    public void limitSwitchTwo() {
        if(limitTwo = true) {
            greenWheel.setRPMVelocity(0);*/
            //if(/**ButtonPress*/) {
                if(teamBlue = true) {
                    if(blue = true) {
                        pewpew();
                    }
                }
                if(teamBlue = true) {
                    if(red = true) {
                        flop();
                    }
                }
                if(teamBlue = false) {
                    if(blue = true) {
                        flop();
                    }
                }
                if(teamBlue = false) {
                    if(red = true) {
                        pewpew();
                    }

                }

            }

        }

    }

    public void pewpew() {
        if(shooterState == ShooterState.StartUp) {
            flywheelOne.setRPMVelocity(0);
            flywheelTwo.setRPMVelocity(0);

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
                greenWheel.set(1);
            }
            else greenWheel.set(0);

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

            greenWheel.set(0);
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


    public Shooter getInstance() {

        if (instance == null) {

            instance = new Shooter();
        }

        return instance;
    }

}