package frc.robot.auto.modes;

import frc.robot.auto.ActionRunner;
import frc.robot.auto.Pathfinder;
import frc.robot.auto.actions.ShootBalls;
import frc.robot.subsystems.Sucker;

public class ShootFiveBalls extends Mode {

    //Pathfinder pathOne = new Pathfinder("ThreeBallPath");
    //Pathfinder pathTwo = new Pathfinder("SecondFiveBall");

    ActionRunner actionRunner = ActionRunner.getInstance();
    Sucker sucker = Sucker.getInstance();
    Pathfinder pathfinder = Pathfinder.getInstance();

    public void run() {

        System.out.println("Running One Ball");
        
        pathfinder.translate(4, 0, 270, 0.5);
        //actionRunner.run(new ShootBalls(1, false));
    }
}