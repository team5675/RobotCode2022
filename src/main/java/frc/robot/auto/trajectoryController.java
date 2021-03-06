package frc.robot.auto;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.Trajectory.State;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;

public class trajectoryController {

    NavX navx;

    Drive drive;

    HolonomicDriveController controller;

    SwerveDriveKinematics kinematics;

    Translation2d frontL = new Translation2d(0.483, 0.483);
    Translation2d frontR = new Translation2d(0.483, -0.483);
    Translation2d rearL  = new Translation2d(-0.483, 0.483);
    Translation2d rearR  = new Translation2d(-0.483, -0.483);

    Pose2d currPose;
    Pose2d robotPose;

    State currState;

    SwerveDriveOdometry odom;


    public trajectoryController() {

        navx = NavX.getInstance();
        drive = Drive.getInstance();

        //First PID for forward, second for strafe, third for rotation
        controller = new HolonomicDriveController(new PIDController(0.06, 0, 0), 
        new PIDController(0.06, 0, 0), new ProfiledPIDController(0.25, 0, 0, new TrapezoidProfile.Constraints(3, 1.5)));
        
        kinematics = new SwerveDriveKinematics(frontL, frontR, rearL, rearR);

        //may need to negate angle if gyro increases when robot turns *left*
        odom = new SwerveDriveOdometry(kinematics, Rotation2d.fromDegrees(navx.getAngle()));//navx.getAngle())));
    }

    public SwerveModuleState[] updateVelocities(Trajectory traj, double time) {

        currState = traj.sample(time);

        currPose = currState.poseMeters;

        robotPose = odom.update(Rotation2d.fromDegrees(navx.getAngle()), drive.getFrontLeft().getState(), drive.getFrontRight().getState(), 
        drive.getBackLeft().getState(), drive.getBackRight().getState());

        ChassisSpeeds adjSpeeds = controller.calculate(robotPose, currState, Rotation2d.fromDegrees(0));

        return kinematics.toSwerveModuleStates(adjSpeeds);
    }
}
