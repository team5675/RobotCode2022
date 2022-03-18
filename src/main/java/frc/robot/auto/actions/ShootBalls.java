package frc.robot.auto.actions;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.util.datalog.StringLogEntry;
import edu.wpi.first.wpilibj.DataLogManager;
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

    double blackSetpoint;
    double blueSetpoint;

    NetworkTableEntry ballsShotEntry;

    int ballsShotProx;

    DataLog log;
    DoubleLogEntry blackRPM;
    DoubleLogEntry blueRPM;
    StringLogEntry proxValue;

    public ShootBalls(int newAmount, double blackSetpoint, double blueSetpoint) {

        ballsShotEntry = NetworkTableInstance.getDefault().getTable("dashboard").getEntry("balls shot");

        ballsShotEntry.setDouble(0);

        amount = newAmount;

        ballsShotProx = 0;

        log = DataLogManager.getLog();

        blackRPM = new DoubleLogEntry(log, "Black RPM");
        blueRPM = new DoubleLogEntry(log, "Blue RPM");
        proxValue = new StringLogEntry(log, "Prox Tripped");

        this.blackSetpoint  = blackSetpoint;
        this.blueSetpoint = blueSetpoint;
    }



    @Override
    public void start() {
        vision.lightOn();
        
    }

    @Override
    public boolean loop() {

        drive.move(0, 0, vision.getHorizontalOffset() * Constants.AUTO_ROTATE_P, 0, true);
        
        shooter.pewpewAuto(blackSetpoint, blueSetpoint, (vision.getHorizontalOffset() < 1 && vision.getHorizontalOffset() > -1) );

        blackRPM.append(shooter.getBlackRPM(), System.currentTimeMillis());
        blueRPM.append(shooter.getBlueRPM(), System.currentTimeMillis());
    

        /*if(shooter.getBlackRPMSetpoint() > shooter.getBlackRPM() + 100 && debounce) {

            ballsShot++;

            ballsShotEntry.setDouble(ballsShot);

            debounce = false;
        }

        if(shooter.getBlackRPM() >= shooter.getBlackRPMSetpoint() && !debounce) {
            debounce =true;
        }*/

        if(shooter.getProx()) {

            ballsShotProx++;
        }

        if(!shooter.getProx() && ballsShotProx > 0) {

            ballsShot++;

            proxValue.append("Balls shot = " + ballsShot);

            ballsShotProx = 0;

            System.out.println("Balls shot = " + ballsShot);
        }

        if(amount == ballsShot) {

            shooter.stop();
            ballsShot = 0;
            return false;
        } else return true;
    
    }

    @Override
    public void stop() {
        vision.lightOn();
        shooter.idle();
    }


}