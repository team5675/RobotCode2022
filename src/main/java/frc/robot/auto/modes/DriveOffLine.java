package frc.robot.auto.modes;

import frc.robot.auto.Pathfinder;
import frc.robot.subsystems.NavX;

public class DriveOffLine extends Mode {

    Pathfinder pathFinder = new Pathfinder();
    NavX navX = NavX.getInstance();
    
    public void run() {

        System.out.println("Running Drive Line");

        navX.setOffset(90);
        pathFinder.translate(0, -4, 90, 1);
    }
}
