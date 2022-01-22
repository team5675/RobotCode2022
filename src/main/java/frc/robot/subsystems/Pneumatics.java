package frc.robot.subsystems;

public class Pneumatics{

    static Pneumatics instance;

    public Pneumatics getInstance(){

        if (instance == null) {

            instance = new Pneumatics();
        }

        return instance;
    }

}