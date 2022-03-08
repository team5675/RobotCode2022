package frc.robot.auto.modes;

import frc.robot.auto.ActionRunner;
import frc.robot.auto.Pathfinder;
import frc.robot.auto.actions.ShootBalls;
import frc.robot.subsystems.Sucker;

public class ShootTwoBalls extends Mode{

    Pathfinder path = new Pathfinder("TwoBallPath");

    ActionRunner actionRunner = ActionRunner.getInstance();
    Sucker sucker = Sucker.getInstance();

    public void run() {

        actionRunner.run(new ShootBalls(1));
        sucker.deploy();
        sucker.suckOrBlow(0.75);
        path.drivePath();
        actionRunner.run(new ShootBalls(1));

    }
}