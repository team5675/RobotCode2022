package frc.robot.auto;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class trajectoryController {

    HolonomicDriveController controller;

    SwerveDriveKinematics kinematics;

    Translation2d frontL = new Translation2d(0.483, 0.483);
    Translation2d frontR = new Translation2d(0.483, -0.483);
    Translation2d rearL  = new Translation2d(-0.483, 0.483);
    Translation2d rearR  = new Translation2d(-0.483, -0.483);


    public trajectoryController() {

        //First PID for forward, second for strafe, third for rotation
        controller = new HolonomicDriveController(new PIDController(1, 0, 0), 
        new PIDController(1, 0, 0), new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(5, 2.5)));
        
        kinematics = new SwerveDriveKinematics(frontL, frontR, rearL, rearR);
    }

    public SwerveModuleState[] updateVelocities(PathPlannerTrajectory traj, int i) {

        ChassisSpeeds adjSpeeds = controller.calculate(traj.getState(i-1).poseMeters, traj.getState(i).poseMeters, 
        traj.getState(i).velocityMetersPerSecond, Rotation2d.fromDegrees(traj.getState(i).holonomicRotation.getDegrees()));
    
        return kinematics.toSwerveModuleStates(adjSpeeds);
    }
}
