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
        sucker.suckOrBlow(0.75);
        pathfinder.translate(0, 4.2, 180, 0.23);
        sucker.retract();
        pathfinder.translate(0, -2, 180, 0.2);
        sucker.suckOrBlow(0);
        actionRunner.run(new ShootBalls(2, 2000, -2000));
        sucker.suckOrBlow(1);
        sucker.deploy();
        pathfinder.translate(7.5, -1, 270, 0.5);
        //actionRunner.run(new Wait(1000));
        sucker.retract();
        actionRunner.run(new Wait(1000));
        sucker.deploy();
        pathfinder.translate(6.15, 0.2, 225, 0.5);
        sucker.retract();
        sucker.suckOrBlow(0);
        pathfinder.translate(-1, 0.5, 225, 0.5);
        pathfinder.translate(-16, -1, 250, 0.65);
        actionRunner.run(new ShootBalls(3, 2000, -2000));

    }
}