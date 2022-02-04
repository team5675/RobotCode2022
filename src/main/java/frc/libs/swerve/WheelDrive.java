package frc.libs.swerve;


import com.revrobotics.RelativeEncoder;//import com.revrobotics.CANEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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
		velocityPID.setOutputRange(0, 5700);
	}

	public void drivePathfinder(double velocity, double angle, double maxVelocity) {

		angle /= 72;
		angle += getOffset();

		if (angle > 5) {angle -= 5;}
		if (angle < 0) {angle += 5;}

		//magic number shush, m to ft
		//velocitySetpoint = 458.59873 * (velocity / 3.281);

		//velocitySetpoint = (5676 / maxVelocity) * velocity;//1621.7143 * velocity;

		//maybe correct magic number? at 3.74 m/s RPM is 5676 adjusted for gear ratio
		velocitySetpoint = 1517.6538819 * velocity;

		velocityPID.setReference(velocitySetpoint, CANSparkMax.ControlType.kVelocity);

		setpoint = anglePID.calculate(azimuthEncoder.getVoltage(), angle);

		angleMotor.set(setpoint);

	}


	public void drive(double speed, double angle, boolean deadband) {
		
		//normalizes the encoder angle in case offsets caused it to go above 5
		if (angle > 5) {angle -= 5;}
		if (angle < 0) {angle += 5;}

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

	public void setOffset() {

		//Once we get reed switches installed, auto calibration

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

	public void stop() {

		speedMotor.set(0);
		angleMotor.set(0);
	}

	public double getOffset() {

		return ANGLE_OFFSET;
	}
}