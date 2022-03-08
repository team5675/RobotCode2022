package frc.robot.auto;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;


import java.io.IOException;
import java.nio.file.Path;

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

    Trajectory pathToRun;
    Drive drive;
    NavX navX;
    trajectoryController controller;
    
    int maxVelocity = 3; //m/s
    int maxAccel = 2;    //m/s^2
    int i = 1;

    SwerveModuleState[] states;

    double endTime;
    double elapsedTime = 0.001;
    double startTime;

    Trajectory traj = new Trajectory();

    public Pathfinder(String path) {

        drive = Drive.getInstance();

        navX = NavX.getInstance();

        controller = new trajectoryController();

        dash = Dashboard.getInstance();

        String trajJson = "paths/"+ path +".wpilib.json";

        Path trajPath = Filesystem.getDeployDirectory().toPath().resolve(trajJson);

        try {
            traj = TrajectoryUtil.fromPathweaverJson(trajPath);
        } catch (IOException e) {
            e.printStackTrace();
            DriverStation.reportError("Unable to open trajectory: " + trajJson, e.getStackTrace());
        }
    }

    public void drivePath() {

        /**
         * Steps for tuning 
         * 1. Have Robot drive 1M forward, compare to odometry and make adjustments to conversion factor as needed
         * 2. Figure out if NavX being a poopcock, or relative to azimuth degrees
         * 3. Have Robot turn 360 degrees, compare to odometry
         * 4. Run Diag path and tweak Holonomic P gain for X, Y, and Theta (assuming odom accurate enough)
         * 
         */

        endTime = traj.getTotalTimeSeconds();

        startTime = System.currentTimeMillis();

        //List<State> stateList = traj.getStates();

        //for(pathToRun : stateList){
        while(elapsedTime <= endTime) {

            try {

                states = controller.updateVelocities(traj, elapsedTime);

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
                DriverStation.reportError("Can't Drive", e.getStackTrace());
            }

            elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        }

        drive.getFrontLeft().stop();
        drive.getFrontRight().stop();
        drive.getBackLeft().stop();
        drive.getBackRight().stop();

    }


}