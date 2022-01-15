package frc.robot.subsystems;

public class Shooter{

    static Shooter instance;

    public Shooter getInstance(){

        if (instance == null) {

            instanace = new Shooter();
        }

        return instance;
    }

}