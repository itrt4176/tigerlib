package frc.tigerlib.subsystem.drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public abstract class DifferentialDriveSubsystem extends DriveSubsystemBase {
    protected DifferentialDrive drive;
    protected MotorControllerGroup leftMotors, rightMotors;
    protected DifferentialDriveOdometry odometer;
    private Runnable resetEncoders;
    
    public DifferentialDriveSubsystem(MotorControllerGroup leftMotors, MotorControllerGroup rightMotors, Gyro gyro, Runnable resetEncoders) {
        super(new DifferentialDrive(leftMotors, rightMotors), gyro);
        drive = (DifferentialDrive) super.drive;
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;
        this.rightMotors.setInverted(true);

        this.resetEncoders = resetEncoders;
        this.resetEncoders.run();
        this.gyro = gyro;
        odometer = new DifferentialDriveOdometry(gyro.getRotation2d());
    }

    public void drive(double ySpeed, double rotation) {
        drive.arcadeDrive(ySpeed, rotation, true);
    }

    public void setRobotPosition(Pose2d pose) {
        resetEncoders.run();
        odometer.resetPosition(pose, gyro.getRotation2d());
    }

    
}
