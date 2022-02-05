package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

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

    static Climber instance;

    public Climber getInstance(){

        if (instance == null) {

            instance = new Climber();
        }
        
        return instance;
        
    }

}