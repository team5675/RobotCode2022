package frc.robot.auto.modes;

import java.nio.file.Path;

import frc.robot.auto.ActionRunner;
import frc.robot.auto.Pathfinder;
import frc.robot.auto.actions.ShootBalls;
import frc.robot.subsystems.Sucker;

public class ShootThreeBalls extends Mode {

    ActionRunner actionrunner = ActionRunner.getInstance();
    Pathfinder pathfinder = new Pathfinder("ThreeBallPath");
    Sucker sucker = Sucker.getInstance();

    public void run() {

        actionrunner.run(new ShootBalls(1));
        sucker.deploy();
        sucker.suckOrBlow(0.75);
        pathfinder.drivePath();
        actionrunner.run(new ShootBalls(2));
    }
}