package frc.tigerlib.subsystem.drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/**
 * A class for easy bootstrapping of a differential drive/skid-steer drive
 * subsystem
 * such as the Kit of Parts drive base, "tank drive", or West Coast Drive.
 *
 * <p>
 * These drive bases typically have drop-center / skid-steer with two or more
 * wheels per side
 * (e.g., 6WD or 8WD). This class takes a MotorController per side in
 * {@link #setMotors(MotorController, MotorController)}. For four and six motor
 * drivetrains, construct and pass in {@link
 * edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup} instances as
 * follows.
 *
 * <p>
 * Four motor drivetrain:
 *
 * <pre>
 * <code>
 * public class DriveSystem extends DifferentialDriveSubsystem {
 *   MotorController m_frontLeft = new PWMVictorSPX(1);
 *   MotorController m_rearLeft = new PWMVictorSPX(2);
 *   MotorControllerGroup m_left = new MotorControllerGroup(m_frontLeft, m_rearLeft);
 *
 *   MotorController m_frontRight = new PWMVictorSPX(3);
 *   MotorController m_rearRight = new PWMVictorSPX(4);
 *   MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_rearRight);
 *
 *   public DriveSystem() {
 *     setMotors(m_left, m_right);
 *   }
 * }
 * </code>
 * </pre>
 *
 * <p>
 * Six motor drivetrain:
 *
 * <pre>
 * <code>
 * public class DriveSystem extends DifferentialDriveSubsystem {
 *   MotorController m_frontLeft = new PWMVictorSPX(1);
 *   MotorController m_midLeft = new PWMVictorSPX(2);
 *   MotorController m_rearLeft = new PWMVictorSPX(3);
 *   MotorControllerGroup m_left = new MotorControllerGroup(m_frontLeft, m_midLeft, m_rearLeft);
 *
 *   MotorController m_frontRight = new PWMVictorSPX(4);
 *   MotorController m_midRight = new PWMVictorSPX(5);
 *   MotorController m_rearRight = new PWMVictorSPX(6);
 *   MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_midRight, m_rearRight);
 *
 *   public DriveSystem() {
 *     setMotors(m_left, m_right);
 *   }
 * }
 * </code>
 * </pre>
 *
 * <p>
 * A differential drive robot has left and right wheels separated by an
 * arbitrary width.
 *
 * <p>
 * Drive base diagram:
 *
 * <pre>
 * |_______|
 * | |   | |
 *   |   |
 * |_|___|_|
 * |       |
 * </pre>
 */
public abstract class DifferentialDriveSubsystem extends DriveSubsystemBase {
    @FunctionalInterface
    protected interface DriveMethod {
        void drive(double xSpeed, double rotation);
    }

    protected DifferentialDrive drive;
    protected DifferentialDriveOdometry odometer;
    protected DriveMethod driveMethod;
    protected boolean inverted;

    /** Constructor. */
    protected DifferentialDriveSubsystem() {}

    /**
     * Set the left and right motors to create the {@link DifferentialDrive} and 
     * {@link DifferentialDriveOdometry} objects.
     * 
     * @param leftMotor left motor
     * @param rightMotor right motor
     */
    protected void setMotors(MotorController leftMotor, MotorController rightMotor) {
        rightMotor.setInverted(true);
        drive = new DifferentialDrive(leftMotor, rightMotor);
        drive.setDeadband(0.0);
        setStandard();

        resetEncoders();
        odometer = new DifferentialDriveOdometry(
            gyro.getRotation2d(),
            0.0,
            0.0
        );
    }

    /**
     * Arcade drive method for differential drive platform.
     * 
     * @param xSpeed   The robot's speed along the X axis [-1.0..1.0]. Forward is
     *                 positive.
     * @param rotation The robot's rotation rate around the Z axis [-1.0..1.0].
     *                 Counterlockwise is
     *                 positive.
     */
    public void drive(double xSpeed, double rotation) {
        driveMethod.drive(xSpeed, rotation);
    }

    @Override
    public void setStandard() {
        driveMethod = (xSpeed, rotation) -> drive.arcadeDrive(xSpeed, rotation);

        inverted = false;
    }

    @Override
    public void setInverted() {
        driveMethod = (xSpeed, rotation) -> drive.arcadeDrive(-xSpeed, -rotation);

        inverted = true;
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }

    public void setRobotPosition(Pose2d pose) {
        resetEncoders();
        odometer.resetPosition(gyro.getRotation2d(), 0.0, 0.0, pose);
        robotPosition = odometer.getPoseMeters();
    }

    @Override
    public void periodic() {
        robotPosition = odometer.update(gyro.getRotation2d(), getLeftDistance(), getRightDistance());
    }
}
