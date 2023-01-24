// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.tigerlib.subsystem.drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.tigerlib.subsystem.InvertibleSubsystem;

/**
 * A base for drive subsystems that can be subclassed for easier
 * bootstrapping.
 */
public abstract class DriveSubsystemBase extends SubsystemBase implements InvertibleSubsystem {
  protected Gyro gyro;
  protected Pose2d robotPosition;
  protected Field2d gameField;

  /** Constructor. */
  protected DriveSubsystemBase() {
    robotPosition = new Pose2d();
    gameField = new Field2d();
  }

  /**
   * Retrieve current robot position as a {@link Pose2d}.
   * @return Current robot position
   */
  public Pose2d getRobotPosition() {
    return robotPosition;
  }
  
  /**
   * Set the current robot position.
   * 
   * @param pose {@link Pose2d} of current robot position
   */
  public abstract void setRobotPosition(Pose2d pose);

  /**
   * Get distance measured by left side encoder.
   * 
   * @return distance
   */
  protected abstract double getLeftDistance();

  /**
   * Get distance measured by right side encoder.
   * 
   * @return distance
   */
  protected abstract double getRightDistance();

  /**
   * Resets the current count to zero on the left and right
   * encoders.
   */
  protected abstract void resetEncoders();

  /**
   * Set the gyroscope used by drive subsystem.
   * 
   * @param gyro gyroscope
   */
  protected void setGyro(Gyro gyro) {
    this.gyro = gyro;
    gyro.calibrate();
  }

  



  
}
