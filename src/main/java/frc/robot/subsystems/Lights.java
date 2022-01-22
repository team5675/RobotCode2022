package frc.robot.subsystems;

public class Lights{

    static Lights instance;

    public Lights getInstance(){

        if (instance == null) {

            instance = new Lights();
        }

        return instance;
    }

}