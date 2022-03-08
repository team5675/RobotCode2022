package frc.robot.auto.actions;

import edu.wpi.first.networktables.NetworkTable;
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
    double lastError = 0;

    NetworkTableEntry ballsShotEntry;

    public ShootBalls(int newAmount) {

        ballsShotEntry = NetworkTableInstance.getDefault().getTable("dashboard").getEntry("balls shot");

        ballsShotEntry.setDouble(0);

        amount = newAmount;
    }



    @Override
    public void start() {
        vision.lightOn();
        
    }

    @Override
    public boolean loop() {
        double error = vision.getHorizontalOffset();
        double d = (error - lastError) / 0.04;

        drive.move(0, 0, vision.getHorizontalOffset() * Constants.AUTO_ROTATE_P + d * Constants.AUTO_ROTATE_D, 0, true);

        shooter.pewpew();

        if(shooter.getBlackRPMSetpoint() > shooter.getBlackRPM() && debounce) {

            ballsShot++;

            ballsShotEntry.setDouble(ballsShot);

            debounce = false;
        }

        if(shooter.getBlackRPM() >= shooter.getBlackRPMSetpoint() && !debounce) {
            debounce =true;
        }

        lastError = error;

        if(amount == ballsShot) {
            return false;
        } else {
            return true;
        }

        
    }

    @Override
    public void stop() {
        vision.lightOff();
        shooter.idle();
    }


}