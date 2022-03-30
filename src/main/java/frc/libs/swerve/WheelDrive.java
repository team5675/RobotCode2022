package frc.libs.swerve;


import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;

//import frc.robot.SwerveDrive.Encoder;
 
public class WheelDrive {
	
	
private CANSparkMax angleMotor;
private CANSparkMax speedMotor;

public RelativeEncoder driveEncoder; //public CANEncoder driveEncoder;

private PIDFFController anglePID;
private SparkMaxPIDController velocityPID;

private AnalogInput azimuthEncoder;

double P;
double I;
double D;

double ANGLE_OFFSET;

double setpoint;

double velocitySetpoint;

//convert m/s to rpm AT MOTOR and vice versa
double velocityRPMConversion = 1517.6538819;

	/**
	 * @param angleMotor The CAN ID of the azimuth controller
	 * @param speedMotor The CAN ID of the speed controller
	 * @param analogIn   The Analog ID of the azimuth encoder
	 * @param P          Proportional error for the PID loop
	 * @param I          Integrated error for the PID loop
	 * @param D          Derivative error for the PID loop
	 * @param ANGLE_OFFSET The module's encoder offset
	 */
	public WheelDrive(int angleMotor, int speedMotor, int analogIn, double P, double I, double D, double ANGLE_OFFSET) {

		// create our "wheels"
		this.angleMotor = new CANSparkMax(angleMotor, MotorType.kBrushless);
		this.speedMotor = new CANSparkMax(speedMotor, MotorType.kBrushless);


		anglePID = new PIDFFController(P, I, D);

		anglePID.isContinuous(0, 5);

		this.azimuthEncoder = new AnalogInput(analogIn);

		this.driveEncoder = this.speedMotor.getEncoder();

		this.P = P;
		this.I = I;
		this.D = D;

		this.ANGLE_OFFSET = ANGLE_OFFSET;

		velocityPID = this.speedMotor.getPIDController();
		velocityPID.setP(5e-4);
		velocityPID.setI(0);
		velocityPID.setD(0);
		velocityPID.setFF(0.000156);
		velocityPID.setOutputRange(-1, 1);

		this.angleMotor.setSmartCurrentLimit(20);
	}

	public void drivePathfinder(double velocity, double angle) {

		angle /= 72;
		angle += getOffset();

		if (angle > 5) angle -= 5;
		if (angle < 0) angle += 5;

		//maybe correct magic number? at 3.74 m/s RPM is 5676 adjusted for gear ratio
		velocitySetpoint = Math.abs(velocityRPMConversion * velocity);

		velocityPID.setReference(velocitySetpoint, CANSparkMax.ControlType.kVelocity);

		setpoint = anglePID.calculate(azimuthEncoder.getVoltage(), angle);

		angleMotor.set(setpoint);

		System.out.println("ANGLE SETPOINT: " + setpoint);

		//System.out.println("Motor " + angleMotor.getDeviceId() + " Angle: " + getAzimuth());
	}


	public void drive(double speed, double angle, boolean deadband) {
		
		//normalizes the encoder angle in case offsets caused it to go above 5
		if (angle > 5) angle -= 5;
		if (angle < 0) angle += 5;

		//System.out.println("ID: " + speedMotor.getDeviceId() + " Value: " + azimuthEncoder.getVoltage());

		if (deadband) {

			speedMotor.set(0);
			angleMotor.set(0);
		}

		else { 

			setpoint = anglePID.calculate(azimuthEncoder.getVoltage(), angle);

			speedMotor.set(speed);
			angleMotor.set(setpoint);
		}

	}
	
	public double getAzimuth() {

		return azimuthEncoder.getVoltage();
	}

	public double getSpeedPosition() {
		return driveEncoder.getPosition();
	}

	public void resetSpeedDistance() {
		driveEncoder.setPosition(0);
	}

	public void setAzimuth(double setpoint) {

		angleMotor.set(setpoint);
	}

	public void setDrive(double setpoint) {

		speedMotor.set(setpoint);
	}

	public double getDrivePosition() {

		return driveEncoder.getPosition();
	}

	public SwerveModuleState getState() {

		return new SwerveModuleState(driveEncoder.getVelocity() / velocityRPMConversion, new Rotation2d(getAzimuth() * 72));
	}

	public void stop() {

		speedMotor.set(0);
		angleMotor.set(0);
	}

	public double getOffset() {

		return ANGLE_OFFSET;
	}
}