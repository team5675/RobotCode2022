package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;

/*
Get Limelight data and send Limelight data/interact with it
*/

public class Vision {

    static Vision instance;


    static NetworkTable limelightTable;
    static NetworkTableEntry ledMode;
    static NetworkTableEntry horizontalOffset;
    static NetworkTableEntry verticalOffset;
    static NetworkTableEntry distanceFromTarget;
    static NetworkTableEntry verticalAngleOffset;
    static NetworkTableEntry isTarget;
    static NetworkTable dashboardTable;

    double distance;
    boolean lightOn = false;
    NavX navX;


    public Vision() {

        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        dashboardTable = NetworkTableInstance.getDefault().getTable("dashboard");
        ledMode = limelightTable.getEntry("ledMode");
        horizontalOffset = limelightTable.getEntry("tx");
        verticalOffset = limelightTable.getEntry("ty");
        isTarget = limelightTable.getEntry("tv");
        distanceFromTarget = dashboardTable.getEntry("distanceFromTarget");
        navX = NavX.getInstance();
    }

    public void lightOn() {

        lightOn = true;

        ledMode.setDouble(3);
    }


    public void lightOff() {

        lightOn = false;
        
        ledMode.setDouble(1);
    }

    public boolean isTargeted() {

        return isTarget.getBoolean(false);
    }


    public boolean getLightOn() {

        if (ledMode.getDouble(0) == 3) {

            return true;
        } else {

            return false;
        }
    }


    public double getHorizontalOffset() {
        double raw = horizontalOffset.getDouble(0);

        return raw - 1;
    }
    

    public double getVerticalOffset() {

        return verticalOffset.getDouble(0);
    }


    public void loop() {
        
            distance = ((Constants.VISION_TARGET_HEIGHT - Constants.VISION_CAMERA_HEIGHT) / Math.tan(Math.toRadians(Constants.VISION_CAMERA_ANGLE + getVerticalOffset()))) / 12;
            distanceFromTarget.setDouble(distance);
    }

    public double getDistanceFromTarget() {

        return distance;
    }


    public static Vision getInstance() {

        if (instance == null) {
            
            instance = new Vision();
        }

        return instance;
    }
}