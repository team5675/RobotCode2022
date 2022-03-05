// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.subsystems.*;
import frc.robot.auto.Pathfinder;
import frc.robot.auto.actions.LineUpTowardsTargetWithDriver;

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
  Pneumatics pneumatics;
  Sucker suck;

  Pathfinder pathfinder;
  LineUpTowardsTargetWithDriver lineUpTowardsTargetWithDriver;

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
    pneumatics = Pneumatics.getInstance();
    suck = Sucker.getInstance();

    lineUpTowardsTargetWithDriver = new LineUpTowardsTargetWithDriver();

    shoot.setcolor(DriverStation.getAlliance().toString());

    pathfinder.setPath("FirstTry");

    vision.lightOff();

  }

 
  @Override
  public void robotPeriodic() {

    //if(pneumatics.getPressureSwitch()) {

      //pneumatics.runCompressor();
    pneumatics.stopCompressor();

  }

  
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

    vision.loop();

    //Reset Yaw on NavX
    if(driverController.getResetYaw()) {

      navX.resetYaw();
    }

    //Tele-op auto functions or manual drive
    double forward = driverController.getForward();
    double strafe = driverController.getStrafe();
    double rotation = driverController.getRotation();
    double angle = navX.getAngle();
    boolean isFieldOriented = driverController.isFieldOriented();

    
    if (driverController.getShootPressed()) {

      lineUpTowardsTargetWithDriver.start();
    } else if (driverController.getShootReleased()) {

      lineUpTowardsTargetWithDriver.stop();
    }

    if(driverController.getShoot()) {

      lineUpTowardsTargetWithDriver.loop();
      shoot.pewpew();
    } else {
      drive.move(forward, strafe, rotation, angle, isFieldOriented);
      shoot.idle();
    }


    suck.suckOrBlow((driverController.getIntakeSuck() - driverController.getOuttake()) * .75);

    if (driverController.getIntakeDeploy())
      suck.deploy();
    else
      suck.retract();

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
