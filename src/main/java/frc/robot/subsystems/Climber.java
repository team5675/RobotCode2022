package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class Climber{

    public static void updateShuffleboard(){

        Shuffleboard.getTab("Climber")
        .add("Limit Switch1", 1)
        .withWidget(BuiltInWidgets.kBooleanBox)
        .getEntry();

        Shuffleboard.getTab("Climber")
        .add("Limit Switch2", 1)
        .withWidget(BuiltInWidgets.kBooleanBox)
        .getEntry();

        Shuffleboard.getTab("Climber")
        .add("Cylinder1", 1)
        .withWidget(BuiltInWidgets.kRelay)
        .getEntry();

        Shuffleboard.getTab("Climber")
        .add("Cylinder2", 1)
        .withWidget(BuiltInWidgets.kRelay)
        .getEntry();

        Shuffleboard.getTab("Climber")
        .add("Cylinder3", 1)
        .withWidget(BuiltInWidgets.kRelay)
        .getEntry();
    }

    
    DigitalInput limitSwitch1 = new DigitalInput(1);
    DigitalInput limitSwitch2 = new DigitalInput(2);

static Climber instance;

DoubleSolenoid lock;
Solenoid firstvalve;
Solenoid secondvalve;
Solenoid thirdvalve;
Spark winch;

boolean locked = true;

public Climber(){

lock = new DoubleSolenoid(Constants.LOCK_SOLENOID_ID_1, Constants.LOCK_SOLENOID_ID_2, 0);
winch = new Spark(Constants.WINCH_MOTOR_ID);


}

   
public void setWinchSpeed(double speed) {

    winch.set(Math.abs(speed));
}


    static Climber somthinginstance;

    public Climber getInstance(){

        if (somthinginstance == null) {

            somthinginstance = new Climber();
        }
        
        return somthinginstance;
        
    }

}