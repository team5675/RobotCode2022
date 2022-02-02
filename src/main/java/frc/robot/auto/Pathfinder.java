package frc.robot.auto;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.kinematics.SwerveModuleState;

import com.pathplanner.lib.PathPlanner;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;

public class Pathfinder {

    static Pathfinder instance;

    enum pathState {

        running,
        end
    }

    pathState pState;

    PathPlannerTrajectory pathToRun;
    Drive drive;
    NavX navX;
    trajectoryController controller;
    
    int maxVelocity = 8;
    int maxAccel = 5;
    int i = 1;

    SwerveModuleState[] states;

    public Pathfinder() {

        drive = Drive.getInstance();

        navX = NavX.getInstance();

        controller = new trajectoryController();
    }

    public void setPath(String path) {

        pathToRun = PathPlanner.loadPath(path, maxVelocity, maxAccel);

        pState = pathState.running;
    }

    public void drivePath() {

        while(pState == pathState.running) {

            try {

                states = controller.updateVelocities(pathToRun, i);  
                i++;

                SwerveModuleState FL = states[0];
                SwerveModuleState FR = states[1];
                SwerveModuleState BL = states[2];
                SwerveModuleState BR = states[3];

                //System.out.println("Speed: " + FL.speedMetersPerSecond + " Angle: " + FL.angle.getDegrees());

                
                drive.getFrontLeft().drivePathfinder(FL.speedMetersPerSecond, FL.angle.getDegrees());
                drive.getFrontRight().drivePathfinder(FR.speedMetersPerSecond, FR.angle.getDegrees());
                drive.getBackLeft().drivePathfinder(BL.speedMetersPerSecond, BL.angle.getDegrees());
                drive.getBackRight().drivePathfinder(BR.speedMetersPerSecond, BR.angle.getDegrees());
                
            } catch (Exception e) {
                
                pState = pathState.end;
            }

            
        }

        drive.getFrontLeft().drive(0, 0, false);
        drive.getFrontRight().drive(0, 0, false);
        drive.getBackLeft().drive(0, 0, false);
        drive.getBackRight().drive(0, 0, false);

    }

    public static Pathfinder getInstance() {

        if(instance == null) {

            instance = new Pathfinder();
        }

        return instance;
    }


}