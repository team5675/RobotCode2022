package frc.robot.subsystems;

public class Sucker{

    static Sucker instance;

    public Sucker getInstance(){

        if (instance == null) {

            instance = new Sucker();
        }

        return instance;
    }

}