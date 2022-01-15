package frc.robot.subsystems;

public class NavX{

    static NavX instance;

    AHRS gyro;

    public NavX getInstance(){

        if (instance == null) {

            instanace = new NavX();
        }

        return instance;
    }

}