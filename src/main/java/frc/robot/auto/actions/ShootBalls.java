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

    public ShootBalls(int newAmount, boolean useVision) {

        lineup = useVision;

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

        drive.move(0, 0, vision.getHorizontalOffset() * Constants.AUTO_ROTATE_P, 0, true);

        shooter.pewpew();

        if(shooter.getBlackRPMSetpoint() > shooter.getBlackRPM() + 100 && debounce) {

            ballsShot++;

            ballsShotEntry.setDouble(ballsShot);

            debounce = false;
        }

        if(shooter.getBlackRPM() >= shooter.getBlackRPMSetpoint() && !debounce) {
            debounce =true;
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