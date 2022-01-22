package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

public class NavX{

    static NavX instance;

    AHRS gyro;

    public NavX getInstance(){

        if (instance == null) {

            instance = new NavX();
        }

        return instance;
    }

}