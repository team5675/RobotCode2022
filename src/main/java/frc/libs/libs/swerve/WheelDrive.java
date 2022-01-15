package frc.libs.swerve;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;

import frc.libs.swerve.PIDFFController;

//import frc.robot.SwerveDrive.Encoder;
 
public class WheelDrive {
	
	
private CANSparkMax angleMotor;
private CANSparkMax speedMotor;

public CANEncoder driveEncoder;

private PIDFFController anglePID;

private AnalogInput azimuthEncoder;

double P;
double I;
double D;

double ANGLE_OFFSET;

double setpoint;

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
	}


	public void drive(double speed, double angle, boolean deadband) {
		
		//normalizes the encoder angle in case offsets caused it to go above 5
		if (angle > 5) {angle -= 5;}
		if (angle < 0) {angle += 5;}

		//System.out.println("Module ID: " + speedMotor.getDeviceId() + " Error" + anglePID.getPositionError());

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