package frc.libs.swerve;

import frc.robot.Robot;

public class PIDFFController {
    

    double kP;
    double kI;
    double kD;

    double PSet;
    double ISet;
    double DSet;

    double kS;
    double kV;
    double kA;

    double error;
    double prevError;
    double returnValue;

    double previousSetpoint; 
    double optoAngle;

    boolean continuous;

    int min;
    int max;

    double clUnitsAway;
    double ccUnitsAway;

    double returnVal;

    double[] setPos = {0,0};
    double[] setpoints = new double[2];


    /**
     * **NEO ONLY** PID and feedforward controller
     * @param kP constant
     * @param kI constant
     * @param kD constant
     * @param kS constant
     * @param kV constant
     * @param kA constant
     */
    public PIDFFController(double kP, double kI, double kD) {

        this.kP = kP;
        this.kI = kI;
        this.kD = kD;

        prevError = 0;

        min = 0;
        max = 0;

        previousSetpoint = 0;
        optoAngle = 0;

        setpoints[0] = 0;
        setpoints[1] = 1;

        continuous = false;

        returnVal = 0;
    }

    public void isContinuous(int min, int max) {

        continuous = true;

        this.min = min;
        this.max = max;
    }

    public double calculateFF(double error) {

        //Feedforward explanation code
		double kS;
		double kV;
		double kA;

		//kS****************************************************************************************************
        kS = 0.78; //static friction of motor. measured in volts

		//kV****************************************************************************************************
		double freeSpeedVelocity    = 5676; //from rev website, in RPM
		double wheelDiameter        = .25; //in feet
		double azimuthGearReduction = 32; //from Colin

		double maxVelocity = (freeSpeedVelocity * Math.PI * wheelDiameter) / azimuthGearReduction;
        double maxVoltage= 12;
        double scalingFactor = 4.8;

		kV = scalingFactor * error; 


        //kA****************************************************************************************************
        
        double stallTorque = 2.6; //from rev website, in Nm
        double robotMass   = 124; //in lbs, prolly less now

		double maxAcceleration = (2 * stallTorque * azimuthGearReduction) / (wheelDiameter * robotMass);
		
        kA = (maxVoltage) / (maxAcceleration);
        
        return kS;
    }


    public double calculate(double feedback, double setpoint) {

        if (continuous) { 
                
            if (feedback > setpoint) {

                clUnitsAway = (max - feedback) + setpoint; //Going right
                ccUnitsAway = (feedback - min) - setpoint; //Going left
            }

            else if (feedback < setpoint) {

                clUnitsAway = (setpoint - feedback);
                ccUnitsAway = (feedback - min) + (max - setpoint);
            }

            if (Math.abs(clUnitsAway - ccUnitsAway) < 0.3) error = clUnitsAway;
            else error = Math.min(clUnitsAway, ccUnitsAway);

            /**
             * TODO: Either keep as cl or change to cc
             */
            if (error == ccUnitsAway) {

                error *= -1;
            }
        }else {

            error = setpoint - feedback;
        }
        

        PSet = error * kP;
        ISet += error;
        DSet = (error - prevError) / Robot.kDefaultPeriod;

        prevError = error;

        returnVal = PSet + (ISet * kI) + (DSet * kD);

        if (returnVal > 1) {

            returnVal = 1;
        }

        if (returnVal < -1) {

            returnVal = -1;
        }

        setpoint = returnVal; //+ 0.78 / 12);
        

        if (error > 0.0017 || error < -0.0017) return setpoint;
        else return 0;
    }
}
