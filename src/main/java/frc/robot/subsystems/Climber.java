package frc.robot.subsystems;

public class Climber{

    static Climber instance;

    public Climber getInstance(){

        if (instance == null) {

            instance = new Climber();
        }
        
        return instance;
        
    }

}