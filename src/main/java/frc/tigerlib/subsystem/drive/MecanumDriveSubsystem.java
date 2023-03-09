package frc.tigerlib.subsystem.drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelPositions;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/**
 * A class for easy bootstrapping of Mecanum drive subsystems.
 *
 * <p>
 * Mecanum drives are rectangular with one wheel on each corner. Each wheel has
 * rollers toed in
 * 45 degrees toward the front or back. When looking at the wheels from the top,
 * the roller axles
 * should form an X across the robot.
 *
 * <p>
 * Drive base diagram:
 *
 * <pre>
 * \\_______/
 * \\ |   | /
 *   |   |
 * /_|___|_\\
 * /       \\
 * </pre>
 */
public abstract class MecanumDriveSubsystem extends DriveSubsystemBase {
    @FunctionalInterface
    protected interface DriveMethod {
        void drive(double xSpeed, double ySpeed, double rotation);
    }

    protected MecanumDrive drive;
    protected MecanumDriveOdometry odometer;
    private boolean isFieldOriented;
    protected DriveMethod driveMethod;
    protected boolean inverted;

    /** Constructor. */
    protected MecanumDriveSubsystem() {}

    /**
     * Set drive motors and drive kinematics to create {@link #drive}
     * and {@link #odometer} objects.
     * 
     * @param frontLeft  The motor on the front-left corner.
     * @param rearLeft   The motor on the rear-left corner.
     * @param frontRight The motor on the front-right corner.
     * @param rearRight  The motor on the rear-right corner.
     * @param kinematics The mecanum drive kinematics for your drivetrain.
     * 
     * @see MecanumDriveKinematics
     */
    protected void setMotors(MotorController frontLeft, MotorController rearLeft, MotorController frontRight,
            MotorController rearRight, MecanumDriveKinematics kinematics) {
        
        frontRight.setInverted(true);
        rearRight.setInverted(true);

        drive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
        drive.setDeadband(0.0);
        setStandard();

        resetEncoders();
        odometer = new MecanumDriveOdometry(kinematics, gyro.getRotation2d(), getWheelPositions());
        isFieldOriented = false;
    }

    /**
     * Drive method for Mecanum platform.
     *
     * <p>
     * Angles are measured clockwise from the positive X axis. The robot's speed is
     * independent from its angle or rotation rate.
     * 
     * @param xSpeed   The robot's speed along the X axis [-1.0..1.0]. Forward is
     *                 positive.
     * @param ySpeed   The robot's speed along the Y axis [-1.0..1.0]. Left is
     *                 positive.
     * @param rotation The robot's rotation rate around the Z axis [-1.0..1.0].
     *                 Counterclockwise is positive.
     * 
     * @see #setFieldOriented(boolean)
     */
    public void drive(double xSpeed, double ySpeed, double rotation) {
        driveMethod.drive(xSpeed, ySpeed, rotation);
    }

    /**
     * When set to true, the robot is "directionless" and movement is
     * relative to the field rather than the robot using input from the
     * gyroscope.
     * 
     * <p>
     * Defaults to false.
     * 
     * @param isFieldOriented Set field-oriented control.
     */
    protected void setFieldOriented(boolean isFieldOriented) {
        this.isFieldOriented = isFieldOriented;
    }

    @Override
    public void setStandard() {
        driveMethod = (xSpeed, ySpeed, rotation) -> {
            if (isFieldOriented) {
                drive.driveCartesian(xSpeed, ySpeed, rotation, gyro.getRotation2d());
            } else {
                drive.driveCartesian(xSpeed, ySpeed, rotation);
            }
        };

        inverted = false;
    }

    @Override
    public void setInverted() {
        driveMethod = (xSpeed, ySpeed, rotation) -> {
            if (isFieldOriented) {
                drive.driveCartesian(-xSpeed, -ySpeed, rotation, gyro.getRotation2d());
            } else {
                drive.driveCartesian(-xSpeed, -ySpeed, rotation);
            }
        };

        inverted = true;
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }

    /**
     * Get the wheel speeds of each wheel.
     * 
     * <p> Used for odometry calculation.
     * 
     * @return {@link MecanumDriveWheelSpeeds}
     */
    protected abstract MecanumDriveWheelSpeeds getWheelSpeeds();

    protected abstract MecanumDriveWheelPositions getWheelPositions();

    @Override
    public void setRobotPosition(Pose2d pose) {
        resetEncoders();
        odometer.resetPosition(gyro.getRotation2d(), getWheelPositions(), pose);
        robotPosition = odometer.getPoseMeters();
    }

    @Override
    public void periodic() {
        robotPosition = odometer.update(gyro.getRotation2d(), getWheelPositions());
    }

    @Override
    protected double getLeftDistance() {
        return 0;
    }

    @Override
    protected double getRightDistance() {
        return 0;
    }
}
