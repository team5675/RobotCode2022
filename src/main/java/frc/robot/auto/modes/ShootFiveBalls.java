package frc.robot.auto.modes;

import frc.robot.auto.ActionRunner;
import frc.robot.auto.Pathfinder;
import frc.robot.auto.actions.ShootBalls;
import frc.robot.auto.actions.Wait;
import frc.robot.subsystems.Sucker;

public class ShootFiveBalls extends Mode {

    ActionRunner actionRunner = ActionRunner.getInstance();
    Sucker sucker = Sucker.getInstance();
    Pathfinder pathfinder = Pathfinder.getInstance();

    public void run() {

        System.out.println("Running four ball");
        sucker.deploy();
        sucker.suckOrBlow(1);
        pathfinder.translate(0, 4.3, 180, 0.22);
        sucker.retract();
        sucker.suckOrBlow(0.1);
        pathfinder.translate(0, -2, 190, 0.2);
        sucker.deploy();
        sucker.suckOrBlow(1);
        actionRunner.run(new ShootBalls(2, 2000, -2000));
        pathfinder.translate(7.5, -.1, 270, 0.5);
        pathfinder.translate(12, -0.4, 225, 0.5);
        pathfinder.translate(0.5, 1, 225, 0.2);
        sucker.retract();
        sucker.suckOrBlow(0);
        pathfinder.translate(-17, -2, 225, 0.65);
        sucker.deploy();
        sucker.suckOrBlow(1);
        actionRunner.run(new ShootBalls(2, 2000, -2000));

    }
}