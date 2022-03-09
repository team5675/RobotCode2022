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

    public void run() {

        actionRunner.run(new ShootBalls(1, false));
        sucker.deploy();
        sucker.suckOrBlow(0.75);
        //pathOne.drivePath();
        actionRunner.run(new ShootBalls(2, false));
        //pathTwo.drivePath();
        actionRunner.run(new ShootBalls(2, true));

    }
}