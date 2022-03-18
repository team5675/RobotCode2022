package frc.robot.auto.modes;

import frc.robot.auto.ActionRunner;
import frc.robot.auto.Pathfinder;
import frc.robot.auto.actions.ShootBalls;
import frc.robot.subsystems.Sucker;

public class ShootTwoBalls extends Mode{

    //Pathfinder path = new Pathfinder("TwoBallPath");

    ActionRunner actionRunner = ActionRunner.getInstance();
    Sucker sucker = Sucker.getInstance();
    Pathfinder pathfinder = Pathfinder.getInstance();

    public void run() {

        sucker.deploy();
        sucker.suckOrBlow(0.75);
        pathfinder.translate(0, 4, 180, 0.2);
        sucker.retract();
        //actionRunner.run(new ShootBalls(2, true));

    }
}