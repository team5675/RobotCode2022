// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.subsystems.*;
import frc.robot.auto.Pathfinder;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  Drive drive;
  DriverController driverController;
  NavX navX;
  Dashboard dash;
  Shooter shoot;
  Vision vision;

  Pathfinder pathfinder;

  enum Paths {

    StraightLineFor,
    StraightLineBack,
    Diag,
    Curve

  }

  SendableChooser<Paths> modeSelector;
    NetworkTable autoTable;
    NetworkTableEntry waitTime;
    NetworkTableEntry startOffset;


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    drive = Drive.getInstance();
    driverController = DriverController.getInstance();
    navX = NavX.getInstance();
    pathfinder = Pathfinder.getInstance();
    dash = Dashboard.getInstance();
    shoot = Shooter.getInstance();
    vision = Vision.getInstance();

    shoot.setcolor(DriverStation.getAlliance().toString());

    navX.resetYaw();

    pathfinder.setPath("FirstTry");

    /*modeSelector = new SendableChooser<Paths>();
        autoTable = NetworkTableInstance.getDefault().getTable("auto");
        waitTime = autoTable.getEntry("waitTime");
        startOffset = autoTable.getEntry("startOffset");
        modeSelector.addOption("Straight Line Forward", Paths.StraightLineFor);
        modeSelector.addOption("Straight Line Backwards", Paths.StraightLineBack);
        modeSelector.addOption("Back Left Diagonal", Paths.Diag);
        modeSelector.addOption("Big Turn Time", Paths.Curve);
        SmartDashboard.putData(modeSelector);

    Paths toReturn = modeSelector.getSelected();

    switch (toReturn) {
      case StraightLineFor:

        pathfinder.setPath("PLEASE WORK");
        break;
      
      case StraightLineBack:
      
        pathfinder.setPath("Straight Back");
        break;

      case Diag:

        pathfinder.setPath("Diag");
        break;
      
      case Curve:
      
        pathfinder.setPath("Curve");
        break;
    
      default:
        break;
    }*/

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    /* Might be the cause of a run-time error
    dash.getDriveTab().add("FL Azimuth", drive.getFrontLeft().getAzimuth()).withWidget(BuiltInWidgets.kVoltageView);
    dash.getDriveTab().add("FR Azimuth", drive.getFrontRight().getAzimuth()).withWidget(BuiltInWidgets.kVoltageView);
    dash.getDriveTab().add("BL Azimuth", drive.getBackLeft().getAzimuth()).withWidget(BuiltInWidgets.kVoltageView);
    dash.getDriveTab().add("BR Azimuth", drive.getBackRight().getAzimuth()).withWidget(BuiltInWidgets.kVoltageView);
    */
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

    pathfinder.drivePath();
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {

    
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    //Auto Set Offset Encoders
    // **MUST ALIGN STRAIGHT BEFOREHAND**
    if(driverController.getResetSwerveOffset()) {

      drive.getBackRight().setOffset(drive.getBackRight().getAzimuth());
      drive.getBackLeft().setOffset(drive.getBackLeft().getAzimuth());
      drive.getFrontRight().setOffset(drive.getFrontRight().getAzimuth());
      drive.getFrontRight().setOffset(drive.getFrontLeft().getAzimuth());
    }

    //Reset Yaw on NavX
    if(driverController.getResetYaw()) {

      navX.resetYaw();
    }

    //Tele-op auto functions or manual drive
    double forward = driverController.getForward(); //CHANGE THIS BACK
    double strafe = driverController.getStrafe();
    double rotation = driverController.getRotation();
    double angle = navX.getAngle();
    boolean isFieldOriented = driverController.isFieldOriented();

    drive.move(forward, strafe, rotation * -1, angle, isFieldOriented);

    //dash.getPathfinderTab().add("Gyro Angle", angle).withWidget(BuiltInWidgets.kGyro).getEntry();

  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  static Robot instance;

  public static Robot getInstance() {

    if(instance == null) {

      instance = new Robot();
    }

    return instance;
  }
}
