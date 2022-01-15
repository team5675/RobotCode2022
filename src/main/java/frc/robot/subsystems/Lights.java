package frc.robot.subsystems;

public class Lights{

    static Lights instance;

    public Lights getInstance(){

        if (instance == null) {

            instanace = new Lights();
        }

        return instance;
    }

}