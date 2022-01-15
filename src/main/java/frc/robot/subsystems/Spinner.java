package frc.robot.subsystems;

public class Spinner{

    static Spinner instance;

    public Spinner getInstance(){

        if (instance == null) {

            instanace = new Spinner();
        }

        return instance;
    }

}