package frc.robot.subsystems;

public class Vision{

    static Vision instance;

    public Vision getInstance(){

        if (instance == null) {

            instance = new Vision();
        }

        return instance;
    }

}