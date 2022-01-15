/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.libs.motors;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * Add your docs here.
 */
public class SparkMaxMotor {

    private CANSparkMax controller;
    private CANPIDController pidController;
    private CANEncoder encoder;
    
    public SparkMaxMotor(int motorId) {

        controller = new CANSparkMax(motorId, MotorType.kBrushless);

        controller.restoreFactoryDefaults();
        controller.setSmartCurrentLimit(40);
        
        pidController = controller.getPIDController();
        encoder = controller.getEncoder();
    }


    public void setSpeed(double speed) {
    
        controller.set(speed);
    }


    public void setRPMVelocity(int RPM) {

        pidController.setReference(RPM, ControlType.kVelocity);
    }


    public double getVelocity() {

        return Math.abs(encoder.getVelocity());
    }


    public void configurePID(double p, double i, double d, double ff) {

        pidController.setP(p);
        pidController.setI(i);
        pidController.setD(d);
        pidController.setFF(ff);
    }

    
    public void burnFlash() {

        controller.burnFlash();
    }
}