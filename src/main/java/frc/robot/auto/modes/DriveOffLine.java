package frc.robot.auto.modes;

import frc.robot.auto.Pathfinder;

public class DriveOffLine extends Mode {

    Pathfinder pathFinder = new Pathfinder("DriveLinePath");
    
    public void run() {

        pathFinder.drivePath();
    }
}
