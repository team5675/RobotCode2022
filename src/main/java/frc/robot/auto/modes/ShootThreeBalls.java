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
        sucker.suckOrBlow(0.75);
        pathfinder.translate(0, 4.2, 180, 0.21);
        sucker.retract();
        pathfinder.translate(0, -1, 170, 0.2);
        actionrunner.run(new ShootBalls(1, false));
        sucker.deploy();
        sucker.suckOrBlow(0.75);
        pathfinder.translate(7, -2, 270, 0.5);
        sucker.retract();
        sucker.suckOrBlow(0);
        pathfinder.translate(-6, 3.8, 200, 0.5);
        actionrunner.run(new ShootBalls(2, true));
    }
}