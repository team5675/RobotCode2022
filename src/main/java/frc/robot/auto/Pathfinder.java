package frc.robot.auto;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import com.pathplanner.lib.PathPlanner;

import frc.robot.Dashboard;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;

public class Pathfinder {

    static Pathfinder instance;

    enum pathState {

        running,
        end
    }

    pathState pState;

    Dashboard dash;

    PathPlannerTrajectory pathToRun;
    Drive drive;
    NavX navX;
    trajectoryController controller;
    
    int maxVelocity = 3; //m/s
    int maxAccel = 2;    //m/s^2
    int i = 1;

    SwerveModuleState[] states;

    double endTime;
    double elapsedTime = 0;
    double startTime;

    public Pathfinder() {

        drive = Drive.getInstance();

        navX = NavX.getInstance();

        controller = new trajectoryController();

        dash = Dashboard.getInstance();
    }

    public void setPath(String path) {

        pathToRun = PathPlanner.loadPath(path, maxVelocity, maxAccel);

        pState = pathState.running;
    }

    public void drivePath() {

        endTime = pathToRun.getEndState().timeSeconds;

        startTime = System.currentTimeMillis();

        while(elapsedTime <= endTime) {

            try {

                states = controller.updateVelocities(pathToRun, elapsedTime);

                SwerveModuleState FL = states[0];
                SwerveModuleState FR = states[1];
                SwerveModuleState BL = states[2];
                SwerveModuleState BR = states[3];

                //System.out.println("Speed: " + FL.speedMetersPerSecond + " Angle: " + FL.angle.getDegrees());

                
                drive.getFrontLeft().drivePathfinder(FL.speedMetersPerSecond, FL.angle.getDegrees(), maxVelocity);
                drive.getFrontRight().drivePathfinder(FR.speedMetersPerSecond, FR.angle.getDegrees(), maxVelocity);
                drive.getBackLeft().drivePathfinder(BL.speedMetersPerSecond, BL.angle.getDegrees(), maxVelocity);
                drive.getBackRight().drivePathfinder(BR.speedMetersPerSecond, BR.angle.getDegrees(), maxVelocity);

                dash.getPathfinderTab().add("Front Left Speed", FL.speedMetersPerSecond).withWidget(BuiltInWidgets.kTextView).getEntry();
                dash.getPathfinderTab().add("Front Right Speed", FR.speedMetersPerSecond).withWidget(BuiltInWidgets.kTextView).getEntry();
                dash.getPathfinderTab().add("Back Left Speed", BL.speedMetersPerSecond).withWidget(BuiltInWidgets.kTextView).getEntry();
                dash.getPathfinderTab().add("Back Right Speed", BR.speedMetersPerSecond).withWidget(BuiltInWidgets.kTextView).getEntry();

                dash.getPathfinderTab().add("Front Left Angle", FL.angle.getDegrees()).withWidget(BuiltInWidgets.kTextView).getEntry();
                dash.getPathfinderTab().add("Front Right Angle", FR.angle.getDegrees()).withWidget(BuiltInWidgets.kTextView).getEntry();
                dash.getPathfinderTab().add("Back Left Angle", BL.angle.getDegrees()).withWidget(BuiltInWidgets.kTextView).getEntry();
                dash.getPathfinderTab().add("Back Right Angle", BR.angle.getDegrees()).withWidget(BuiltInWidgets.kTextView).getEntry();
                
            } catch (Exception e) {
                
                pState = pathState.end;
            }

            elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        }

        drive.getFrontLeft().stop();
        drive.getFrontRight().stop();
        drive.getBackLeft().stop();
        drive.getBackRight().stop();

    }

    public static Pathfinder getInstance() {

        if(instance == null) {

            instance = new Pathfinder();
        }

        return instance;
    }


}