/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Organize inputs from the Xbox Controllers
 */
public class DriverController {
    
    static DriverController instance;

    XboxController mainController;
    XboxController auxController;

    public DriverController() {
        mainController = new XboxController(0);
        auxController = new XboxController(1);
    }

    public double getForward() {

        return mainController.getRawAxis(1);
    }

    public double getStrafe() {

        return mainController.getRawAxis(0);
    }

    public double getRotation() {

        return mainController.getRawAxis(4);
    }

    public boolean isFieldOriented() {

        return mainController.getRawButton(6);
    }

    public double getHood() {
        return mainController.getRawAxis(5);
    }

    public boolean getResetYaw() {

        return mainController.getRawButton(4);
    }

    public double getIntakeSuck() {
        return (mainController.getRawAxis(3));
    }

    public boolean getIntakeDeploy() {

        return (mainController.getRawAxis(3) > 0.6) || (auxController.getRawAxis(3) > 0.6);
    }

    public boolean getStayStraight() {

        return mainController.getRawButton(1);
    }

    public boolean getResetSwerveOffset() {

        return mainController.getBButton();
    }

    public boolean getShoot() {

        return auxController.getXButton();
    }

    public boolean getShootPressed() {

        return mainController.getRawButtonPressed(3);
    }

    public boolean getShootReleased() {

        return mainController.getRawButtonReleased(3);
    }

    public boolean getLineUp() {

        return mainController.getRawButton(2);
    }

    public boolean getOnLineUpPressed() {

        return mainController.getRawButtonPressed(2);
    }

    public boolean getOnLineUpReleased() {

        return mainController.getRawButtonReleased(2);
    }

    public double getOuttake() {
        
        return mainController.getRawAxis(2);
    }

    public boolean getRunCompressor() {

        return mainController.getRawButtonPressed(8);
    }

    public boolean getUnlockClimb() {

        return auxController.getBButton();
    }

    public boolean getRaiseMasterArm() {
        
        return auxController.getRawButtonPressed(8);
    }

    public boolean getCollapseMasterArm() {

        return auxController.getRawButtonReleased(8);
    }


    public double getTroller() {

        return auxController.getRawAxis(4);
    }


    public boolean getWinchSafety() {

        return auxController.getRawButton(9);
    }


    public double getWinchSpeed() {

        return auxController.getRawAxis(1);
    }


    public static DriverController getInstance() {

        if (instance == null) {
            
            instance = new DriverController();
        }

        return instance;
    }
}