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


    DigitalInput limitSwitch1 = new DigitalInput(0);
    DigitalInput limitSwitch2 = new DigitalInput(1);
    .getChannel();
    
    
    
    
    //Code until robot is assembled
   /* public void teleopPeriodic() {
        setMotorSpeed(joystick.getRawAxis(2));
    }
    
    public void setMotorSpeed(double speed) {
        if (speed > 0) {
            if (toplimitSwitch.get()) {
                // We are going up and top limit is tripped so stop
                motor.set(0);
            } else {
                // We are going up but top limit is not tripped so go at commanded speed
                motor.set(speed);
            }
        } else {
            if (bottomlimitSwitch.get()) {
                // We are going down and bottom limit is tripped so stop
                motor.set(0);
            } else {
                // We are going down but bottom limit is not tripped so go at commanded speed
                motor.set(speed);
            }
        }
    }
*/




    static Climber instance;

    public Climber getInstance(){

        if (instance == null) {

            instance = new Climber();
        }
        
        return instance;
        
    }

}