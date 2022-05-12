package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.Spark;





public class Climber{
    
    DigitalInput leftLimitSwitch;
    DigitalInput rightLimitSwitch;
    DigitalInput climberLockLimitSwitch;

    DoubleSolenoid solenoidErect = new DoubleSolenoid(Constants.COMPRESSOR_ID, PneumaticsModuleType.REVPH, 14, 15);
    DoubleSolenoid solenoid1 = new DoubleSolenoid(Constants.COMPRESSOR_ID, PneumaticsModuleType.REVPH, 12, 13);
    DoubleSolenoid solenoid2 = new DoubleSolenoid(Constants.COMPRESSOR_ID, PneumaticsModuleType.REVPH, 11, 10);

    static Climber instance;

    Sucker suck = Sucker.getInstance();

    Spark winch;

    public Climber() {

        winch = new Spark(Constants.WINCH_MOTOR_ID);

        leftLimitSwitch = new DigitalInput(1);
        rightLimitSwitch = new DigitalInput(0);
        climberLockLimitSwitch = new DigitalInput(2);
    }

    public void startPos() {

        solenoidErect.set(Value.kForward);
        solenoid1.set(Value.kForward);
        solenoid2.set(Value.kReverse);
    }

    public void stop() {

        winch.set(0);
    }


public void deploy(){
 
    solenoidErect.set(Value.kReverse);
    suck.deploy();
    

    if(leftLimitSwitch.get()) {

        winch.set(-1);
        solenoid1.set(Value.kForward);
    }

    if(!rightLimitSwitch.get() && leftLimitSwitch.get()) {
        winch.set(-1);
    }

    if(rightLimitSwitch.get()) {

        winch.set(0);
        solenoid2.set(Value.kForward);

        if(!climberLockLimitSwitch.get()) {
    
            solenoid1.set(Value.kReverse);
        }

        //solenoid1.set(Value.kReverse);
    }

}

    public static Climber getInstance(){

        if (instance == null) {

            instance = new Climber();
        }
        
        return instance;
        
    }

}