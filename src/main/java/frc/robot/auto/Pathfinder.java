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
    
    int maxVelocity = 1;
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
                SwerveModuleState FR = states[0];
                SwerveModuleState BL = states[0];
                SwerveModuleState BR = states[0];

                //magic 72 number to scale from 0-360 to 0-5v
                drive.getFrontLeft().drive(FL.speedMetersPerSecond, FL.angle.getDegrees() / 72, false);
                drive.getFrontRight().drive(FR.speedMetersPerSecond, FR.angle.getDegrees() / 72, false);
                drive.getBackLeft().drive(BL.speedMetersPerSecond, BL.angle.getDegrees() / 72, false);
                drive.getBackRight().drive(BR.speedMetersPerSecond, BR.angle.getDegrees() / 72, false);
                
            } catch (Exception e) {
                
                pState = pathState.end;
            }

            
        }

    }

    public static Pathfinder getInstance() {

        if(instance == null) {

            instance = new Pathfinder();
        }

        return instance;
    }


}