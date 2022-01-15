package frc.robot.subsystems;

public class Sucker{

    static Sucker instance;

    public Sucker getInstance(){

        if (instance == null) {

            instanace = new Sucker();
        }

        return instance;
    }

}