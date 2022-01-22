package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Sucker{

    static Sucker instance;

    DoubleSolenoid intakeSolenoid;
    Spark intake;

    PowerDistributionPanel pdp;
    boolean first;
    double currAmps;
    double prevAmps;
    int intakeMotorID;
    boolean ballIn;
    int ballCount;

    double THRESHOLD = 0.02;


    public Sucker() {

        pdp = new PowerDistributionPanel();

        first = true;

        intakeSolenoid = new DoubleSolenoid(Constants.DEPLOY_ID_1, Constants.DEPLOY_ID_2);

        intake         = new Spark(Constants.INTAKE_ID);
    }

    public int ballIn() {

        if (first) {

            ballCount = 0;
            //ID  of intake here
            intakeMotorID = Constants.INTAKE_ID;

            currAmps = pdp.getCurrent(intakeMotorID);

            ballIn = false;
            first = false;
        }

        prevAmps = currAmps;
        currAmps = pdp.getCurrent(intakeMotorID);


        if ((currAmps - prevAmps) > THRESHOLD && !ballIn) {

            ballIn = true;

            ballCount++;
        }

        if ((currAmps - prevAmps) < THRESHOLD && ballIn) {

            ballIn = false;
            
        } 

        return ballCount;
    }

    public boolean getBallIn() {
        return ballIn;
    }

    public void deploy() {

        intakeSolenoid.set(Value.kReverse);
    }


    public void retract() {

        intakeSolenoid.set(Value.kForward);
    }


    public void suckOrBlow(double speed) {

        intake.set(speed);
    }

    public Sucker getInstance(){

        if (instance == null) {

            instance = new Sucker();
        }

        return instance;
    }

}