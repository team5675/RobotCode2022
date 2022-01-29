/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class Constants {
   
    //Drive subsystem constants
    public static final double ETPF = 10; //Encoder Ticks Per Foot
    
	public static final int WHEEL_BASE_WIDTH = 25;
    public static final int WHEEL_BASE_DEPTH = 25;
    
    public static final int DRIVE_FRONT_LEFT_SPEED_ID = 5;//7;
    public static final int DRIVE_FRONT_LEFT_AZIMUTH_ID = 6;//8;
    public static final double FL_P = 0.6;
    public static final double FL_I = 0;
    public static final double FL_D = 0.003;
    public static final int FL_AZIMUTH_ENCODER_ID = 3;
	public static double FL_ANGLE_OFFSET = .107;//1.54;

    public static final int DRIVE_FRONT_RIGHT_SPEED_ID = 1;//6;
    public static final int DRIVE_FRONT_RIGHT_AZIMUTH_ID = 2;//5;
    public static final double FR_P = 0.6;
    public static final double FR_I = 0;
    public static final double FR_D = 0.003;
    public static final int FR_AZIMUTH_ENCODER_ID = 2;
	public static double FR_ANGLE_OFFSET =  2.89; //41;

    public static final int DRIVE_BACK_LEFT_SPEED_ID = 11;//1;
    public static final int DRIVE_BACK_LEFT_AZIMUTH_ID = 10;//2;
    public static final double BL_P = 0.6;
    public static final double BL_I = 0;
    public static final double BL_D = 0.003;
    public static final int BL_AZIMUTH_ENCODER_ID = 1;  
	public static double BL_ANGLE_OFFSET = 2.395;//0.37;

    public static final int DRIVE_BACK_RIGHT_SPEED_ID = 8;//4;
    public static final int DRIVE_BACK_RIGHT_AZIMUTH_ID = 7;//3;
    public static final double BR_P = 0.6;
    public static final double BR_I = 0;
    public static final double BR_D = 0.003;
    public static final int BR_AZIMUTH_ENCODER_ID = 0;
    public static double BR_ANGLE_OFFSET = 4.116;//4.25;3.276

    public static final double PATHFINDER_KP = 0;
	public static final double PATHFINDER_KV = 0.0909090909;
	public static final double PATHFINDER_KA = 0.011;

    //Shooter subsystem constants
    public static final double SHOOTER_HOOD_P = 0.01;
    public static final int SHOOTER_GATE_ID = 0;
    public static final int SHOOTER_ID_1 = 3;
    public static final int SHOOTER_ID_2 = 4;
    public static final int HOOD_ID = 3;
    public static final double SHOOTER_KP = 0.00007;
    public static final double SHOOTER_KD = 0.00002;
    public static final double SHOOTER_KF = 0.00021; //0.00019
    //Shooter threshold was 100 RPM

    //Pizza spinner constants
    public static final int SPINNER_MOTOR_ID = 9;
    public static final int SPINNER_TICKS_PER_REV = 0;
    public static final double SPINNER_REVS_SETPOINT = 100;
    public static final double ONE_COLOR_REVS = 30;
    public static final int SPINNER_ARM_IN_CHANNEL = 6;
    public static final int SPINNER_ARM_OUT_CHANNEL = 1;

    //Intake subsystem constants
    public static final int INTAKE_ID = 1;
    public static final int DEPLOY_ID_1 = 7;
    public static final int DEPLOY_ID_2 = 0;

    //Vision subsystem constants
    public static final double VISION_TARGET_HEIGHT = 8.1875;
    public static final double VISION_CAMERA_HEIGHT = 1.8958333;//1.7493;
    public static final double VISION_CAMERA_ANGLE = 15;//25.3778;

    //Pneumatics constants
    public static final int COMPRESSOR_ID = 0;

    //Auto Constants
    public static final double AUTO_FORWARD_P = 0.2;
    public static final double AUTO_ROTATE_P = 0.022;
    public static final double AUTO_ROTATE_D = 0.009; //change this back to 0.009 once you put the WheelDrive slow
    public static final double AUTO_STRAFE_P = 0.05;
    public static final double AUTO_GYRO_P = -0.03;

    //Pathfinder Constants
    public static final double PATHFINDER_SLOWDOWN = 0;
    public static final double PATHFINDER_SLOWDOWN_END = 0.1;
    public static final double PATHFINDER_SLOWDOWN_P = 0.5;

    //Climb Constants
    public static final int LOCK_SOLENOID_ID_1 = 2;
    public static final int LOCK_SOLENOID_ID_2 = 5;
    public static final int MASTER_ARM_RAISE_SOLENOID_ID = 4;
    public static final int MASTER_ARM_COLLAPSE_SOLENOID_ID = 3;
    public static final int WINCH_MOTOR_ID = 2;
    public static final int TROLLER_MOTOR_ID = 15;

    //62 Constants
}