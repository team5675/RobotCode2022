package frc.robot.auto.actions;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class ShootBalls implements Action {

    Shooter shooter = Shooter.getInstance();
    NavX navX = NavX.getInstance();
    Drive drive = Drive.getInstance();
    Vision vision = Vision.getInstance();

    int amount;
    int ballsShot = 0;
    boolean debounce = false;
    boolean lineup;
    double lastError = 0;

    NetworkTableEntry ballsShotEntry;

    int ballShotProx;

    int blueRPM;
    int blackRPM;

    public ShootBalls(int newAmount, int blueRPM, int blackRPM) {

        ballsShotEntry = NetworkTableInstance.getDefault().getTable("dashboard").getEntry("balls shot");

        ballsShotEntry.setDouble(0);

        amount = newAmount;

        ballShotProx = 0;

        this.blueRPM = blueRPM;
        this.blackRPM = blackRPM;
    }



    @Override
    public void start() {
        vision.lightOn();
        
    }

    @Override
    public boolean loop() {

        drive.getFrontLeft().drive(vision.getHorizontalOffset() * Constants.AUTO_ROTATE_P, 2.348, false);
        drive.getFrontRight().drive(vision.getHorizontalOffset() * Constants.AUTO_ROTATE_P, 2.953, false);
        drive.getBackLeft().drive(vision.getHorizontalOffset() * Constants.AUTO_ROTATE_P, 3.008, false);
        drive.getBackRight().drive(vision.getHorizontalOffset() * Constants.AUTO_ROTATE_P, 0.628, false);

        shooter.pewpewAuto(blueRPM, blackRPM, vision.getHorizontalOffset() < 2 && vision.getHorizontalOffset() > -2);

        /*if(shooter.getBlackRPMSetpoint() > shooter.getBlackRPM() + 120 && debounce) {

            ballsShot++;

            ballsShotEntry.setDouble(ballsShot);

            debounce = false;
        }

        if(shooter.getBlackRPM() >= shooter.getBlackRPMSetpoint() && !debounce) {
            debounce =true;
        }*/

        if(shooter.getProx()) {

            ballShotProx++;
        }

        if(!shooter.getProx() && ballShotProx > 0) {
            ballsShot++;

            ballShotProx = 0;
        }

        if(amount == ballsShot && lineup == false) {
            return false;
        } else {
            return true;
        }

        
    }

    @Override
    public void stop() {
        vision.lightOn();
        shooter.idle();
    }


}