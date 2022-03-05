/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.actions;

import frc.robot.Constants;
import frc.robot.DriverController;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Vision;

/**
 * Rotate shooter side of robot towards target with driver forward and strafe input
 */
public class LineUpTowardsTargetWithDriver implements Action {
    
    DriverController driverController = DriverController.getInstance();
    Drive drive = Drive.getInstance();
    Vision vision = Vision.getInstance();
    NavX navX = NavX.getInstance();

    //double lastError = 0;

    double targetAngle;
    double offset;
    int dir;


    public void start() {

        vision.lightOn();
        targetAngle = navX.getAngle() - vision.getHorizontalOffset();
    }


    public boolean loop() {
        offset = vision.getHorizontalOffset();

        drive.move(0, 0, -offset * Constants.AUTO_ROTATE_P, navX.getAngle(), false);

        return true; 
    }

    
    public void stop() {
        vision.lightOff();
        //lastError = 0;
    }
}