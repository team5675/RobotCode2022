package frc.robot.subsystems;


import java.lang.ModuleLayer.Controller;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.subsystems.Pneumatics;




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
        .add("LimitSwitch3", 1)
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

    
    DigitalInput LimitSwitch1 = new DigitalInput(1);
    DigitalInput LimitSwitch2 = new DigitalInput(2);
    DigitalInput LimitSwitch3 = new DigitalInput(3);

static Climber firstinstance;

Solenoid climberSolenoid1;
Solenoid climberSolenoid2;
Solenoid thirdvalve;
Spark winch;

boolean locked = true;

public Climber(){

winch = new Spark(Constants.WINCH_MOTOR_ID);


}


   
public Spark setWinchSpeed(int speed) {

    winch.set(Math.abs(speed));
    return winch;
}

DoubleSolenoid solenoidErect = new DoubleSolenoid(null, Constants.DEPLOY_ID_1, Constants.DEPLOY_ID_2);
DoubleSolenoid solenoid1 = new DoubleSolenoid(null, Constants.DEPLOY_ID_1, Constants.DEPLOY_ID_2);
DoubleSolenoid solenoid2 = new DoubleSolenoid(null, Constants.DEPLOY_ID_2, Constants.DEPLOY_ID_2);
DigitalInput forwardLimitSwitch = new DigitalInput(1);
DigitalInput reverseLimitSwitch = new DigitalInput(2);
boolean forwardOne = forwardLimitSwitch.get();
boolean forwardTwo = forwardLimitSwitch.get();
boolean forwardThree = forwardLimitSwitch.get();
boolean reverseOne = reverseLimitSwitch.get();



public void deploy(){
 
    solenoidErect.set(Value.kForward);
    

    if(forwardOne = true) {
        
        solenoid1.set(Value.kForward);
        winch = setWinchSpeed(800);
        
        }
    if(forwardTwo = true) {

        solenoid2.set(Value.kForward);
        winch = setWinchSpeed(800);

    }
    if(forwardThree = true) {

        solenoid1.set(Value.kReverse);
        winch = setWinchSpeed(0);

    }

}

    



    public void retract(){
    
     
    }

    
    



    static Climber instance;

    public Climber getInstance(){

        if (instance == null) {

            instance = new Climber();
        }
        
        return instance;
        
    }

}