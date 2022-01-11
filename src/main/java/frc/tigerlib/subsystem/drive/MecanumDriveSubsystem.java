package frc.tigerlib.subsystem.drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public abstract class MecanumDriveSubsystem extends DriveSubsystemBase {
    protected MotorController frontLeft, rearLeft, frontRight, rearRight;
    protected MecanumDrive drive;
    protected MecanumDriveOdometry odometer;
    private boolean isFieldOriented;

    public MecanumDriveSubsystem(MotorController frontLeft, MotorController rearLeft, MotorController frontRight,
            MotorController rearRight, Gyro gyro, MecanumDriveKinematics kinematics) {

        super(gyro);

        frontRight.setInverted(true);
        rearRight.setInverted(true);

        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.rearLeft = rearLeft;
        this.rearRight = rearRight;

        drive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);

        resetEncoders();
        odometer = new MecanumDriveOdometry(kinematics, gyro.getRotation2d());
    }

    public void drive(double xSpeed, double ySpeed, double rotation) {
        if (isFieldOriented) {
            drive.driveCartesian(ySpeed, xSpeed, rotation, gyro.getAngle());
        } else {
            drive.driveCartesian(ySpeed, xSpeed, rotation);
        }
    }

    protected void setFieldOriented(boolean isFieldOriented) {
        this.isFieldOriented = isFieldOriented;
    }

    protected abstract MecanumDriveWheelSpeeds getWheelSpeeds();

    @Override
    public void setRobotPosition(Pose2d pose) {
        resetEncoders();
        odometer.resetPosition(pose, gyro.getRotation2d());
        robotPosition = odometer.getPoseMeters();
    }

    @Override
    public void periodic() {
        robotPosition = odometer.update(gyro.getRotation2d(), getWheelSpeeds());
    }
}
