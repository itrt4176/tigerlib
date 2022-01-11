// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.tigerlib.subsystem.drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class DriveSubsystemBase extends SubsystemBase {
  protected Gyro gyro;
  protected Pose2d robotPosition;
  protected Field2d gameField;

  
  public DriveSubsystemBase(Gyro gyro) {
    this.gyro = gyro;

    gyro.calibrate();
    robotPosition = new Pose2d();
    gameField = new Field2d();
  }

  public Pose2d getRobotPosition() {
    return robotPosition;
  }
  
  public abstract void setRobotPosition(Pose2d pose);

  protected abstract double getLeftDistance();

  protected abstract double getRightDistance();

  protected abstract void resetEncoders();

  



  
}
