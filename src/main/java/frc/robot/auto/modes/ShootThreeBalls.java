package frc.robot.auto.modes;

import frc.robot.auto.ActionRunner;
import frc.robot.auto.Pathfinder;
import frc.robot.auto.actions.ShootBalls;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Sucker;

public class ShootThreeBalls extends Mode {

    ActionRunner actionrunner = ActionRunner.getInstance();
    Pathfinder pathfinder = Pathfinder.getInstance();
    Sucker sucker = Sucker.getInstance();
    NavX navX = NavX.getInstance();

    public void run() {

        System.out.println("Running three ball");
        sucker.deploy();
        sucker.suckOrBlow(1);
        pathfinder.translate(0, 4.2, 180, 0.23);
        sucker.retract();
        pathfinder.translate(0, -2, 190, 0.2);
        sucker.suckOrBlow(0);
        actionrunner.run(new ShootBalls(2, 2000, -2000));
        sucker.suckOrBlow(1);
        sucker.deploy();
        pathfinder.translate(7.5, -1.5, 270, 0.5);
        sucker.retract();
        pathfinder.translate(-6, 1, 200, 0.5);
        sucker.deploy();
        sucker.retract();
        actionrunner.run(new ShootBalls(1, 1850, -1850));
    }
}