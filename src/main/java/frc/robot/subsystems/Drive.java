//Robot code given for 2022 season

/*package frc.robot.subsystems;

public class Drive{

        static Drive instance;

    public Drive getInstance(){

        if (instance == null) {

            instance = new Drive();
        }

        return instance;
    }

}*/

//Robot code from 2020

/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.libs.swerve.SwerveDrive;
import frc.libs.swerve.WheelDrive;

import frc.robot.Constants;

/**
 * Drive class to manage chassis with swerve drive module.
 */

public class Drive {

    static Drive instance;

    static SwerveDrive chassis;

    static WheelDrive backRight;
    static WheelDrive backLeft;
    static WheelDrive frontRight;
    static WheelDrive frontLeft;


    public Drive() {

        backRight = new WheelDrive(Constants.DRIVE_BACK_RIGHT_AZIMUTH_ID, Constants.DRIVE_BACK_RIGHT_SPEED_ID, Constants.BR_AZIMUTH_ENCODER_ID, 
        Constants.BR_P, Constants.BR_I, Constants.BR_D, Constants.BR_ANGLE_OFFSET);

        backLeft = new WheelDrive(Constants.DRIVE_BACK_LEFT_AZIMUTH_ID, Constants.DRIVE_BACK_LEFT_SPEED_ID, Constants.BL_AZIMUTH_ENCODER_ID, 
        Constants.BL_P, Constants.BL_I, Constants.BL_D, Constants.BL_ANGLE_OFFSET);

        frontRight = new WheelDrive(Constants.DRIVE_FRONT_RIGHT_AZIMUTH_ID, Constants.DRIVE_FRONT_RIGHT_SPEED_ID, Constants.FR_AZIMUTH_ENCODER_ID, 
        Constants.FR_P, Constants.FR_I, Constants.FR_D, Constants.FR_ANGLE_OFFSET);

        frontLeft = new WheelDrive(Constants.DRIVE_FRONT_LEFT_AZIMUTH_ID, Constants.DRIVE_FRONT_LEFT_SPEED_ID, Constants.FL_AZIMUTH_ENCODER_ID, 
        Constants.FL_P, Constants.FL_I, Constants.FL_D, Constants.FL_ANGLE_OFFSET);

        chassis = new SwerveDrive(backRight, backLeft, frontRight, frontLeft);
    }


    public void move(double forward, double strafe, double rotation, double angle, boolean isFieldOriented) {
        
        chassis.drive(forward, strafe, rotation, angle - 90, isFieldOriented);
    }

    public SwerveDrive getSwerve() {

        return chassis;
    }


    public WheelDrive getBackRight() {

        return backRight;
    }

    
    public WheelDrive getBackLeft() {

        return backLeft;
    }


    public WheelDrive getFrontRight() {

        return frontRight;
    }
    

    public WheelDrive getFrontLeft() {

        return frontLeft;
    }


    public static Drive getInstance() {

        if (instance == null) {
            
            instance = new Drive();
        }

        return instance;
    }
}