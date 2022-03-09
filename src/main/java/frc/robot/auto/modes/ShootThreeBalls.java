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
        pathfinder.translate(0, 4, 180, 0.2);
        sucker.retract();
        actionrunner.run(new ShootBalls(1, true));
        sucker.deploy();
        sucker.suckOrBlow(0.75);
        pathfinder.translate(7, -3.5, 270, 0.5);
        sucker.retract();
        pathfinder.translate(-7, 2, 210, 0.5);
        actionrunner.run(new ShootBalls(2, true));
    }
}