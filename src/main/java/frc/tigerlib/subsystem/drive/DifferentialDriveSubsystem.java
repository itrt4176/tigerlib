package frc.tigerlib.subsystem.drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public abstract class DifferentialDriveSubsystem extends DriveSubsystemBase {
    protected MotorController leftMotors, rightMotors;
    protected DifferentialDrive drive;
    protected DifferentialDriveOdometry odometer;
    private Runnable resetEncoders;
    
    public DifferentialDriveSubsystem(MotorController leftMotors, MotorController rightMotors, Gyro gyro, Runnable resetEncoders) {
        super(gyro);
        rightMotors.setInverted(true);
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;
        drive = new DifferentialDrive(leftMotors, rightMotors);

        this.resetEncoders = resetEncoders;
        this.resetEncoders.run();
        odometer = new DifferentialDriveOdometry(gyro.getRotation2d());
    }

    public void drive(double ySpeed, double rotation) {
        drive.arcadeDrive(ySpeed, rotation, true);
    }

    public void setRobotPosition(Pose2d pose) {
        resetEncoders.run();
        odometer.resetPosition(pose, gyro.getRotation2d());
        robotPosition = odometer.getPoseMeters();
    }

    @Override
    public void periodic() {
        robotPosition = odometer.update(gyro.getRotation2d(), getLeftDistance(), getRightDistance());
    }
}
