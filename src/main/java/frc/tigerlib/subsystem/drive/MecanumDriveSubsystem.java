package frc.tigerlib.subsystem.drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public abstract class MecanumDriveSubsystem extends DriveSubsystemBase{
    protected MecanumDrive drive;
    protected MecanumDriveOdometry odometer;
    private Runnable resetEncoders;
    private boolean isFieldOriented;

    public MecanumDriveSubsystem(MotorController frontLeft, MotorController rearLeft, MotorController frontRight,
            MotorController rearRight, Gyro gyro, Runnable resetEncoders, MecanumDriveKinematics kinematics) {

        super(gyro);

        frontRight.setInverted(true);
        rearRight.setInverted(true);

        drive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);

        this.resetEncoders = resetEncoders;
        this.resetEncoders.run();
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

    @Override
    public void setRobotPosition(Pose2d pose) {
        resetEncoders.run();
        odometer.resetPosition(pose, gyro.getRotation2d());
    }
}
