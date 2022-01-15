package frc.robot.subsystems;

public class Drive{

        static Drive instance;

    public Drive getInstance(){

        if (instance == null) {

            instanace = new Drive();
        }

        return instance;
    }

}