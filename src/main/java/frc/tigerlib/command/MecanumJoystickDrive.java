// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.tigerlib.command;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.tigerlib.subsystem.drive.MecanumDriveSubsystem;

public class MecanumJoystickDrive extends CommandBase {
  private MecanumDriveSubsystem driveSys;
  private XboxController controller;

  /** Creates a new MecanumJoystickDrive. */
  public MecanumJoystickDrive(MecanumDriveSubsystem driveSubsystem, XboxController controller) {
    // Use addRequirements() here to declare subsystem dependencies.
    driveSys = driveSubsystem;
    this.controller = controller;

    addRequirements(driveSys);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSys.drive(controller.getLeftX(), controller.getLeftY(), controller.getRightX());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
