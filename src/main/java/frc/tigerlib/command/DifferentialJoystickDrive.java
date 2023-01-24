// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.tigerlib.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.tigerlib.subsystem.drive.DifferentialDriveSubsystem;

/**
 * Prewritten command to drive a {@link DifferentialDriveSubsystem} with a
 * {@link CommandXboxController}.
 */
public class DifferentialJoystickDrive extends CommandBase {
  private DifferentialDriveSubsystem driveSys;
  private CommandXboxController controller;

  /**
   * Construct a DifferentialJoystickDrive command.
   * 
   * <p>
   * This command should be set as the default for the subsystem after
   * creation.
   * 
   * 
   * @param driveSubsystem {@link DifferentialDriveSubsystem} to drive.
   * @param controller     {@link CommandXboxController} to use for driving.
   */
  public DifferentialJoystickDrive(DifferentialDriveSubsystem driveSubsystem, CommandXboxController controller) {
    driveSys = driveSubsystem;
    this.controller = controller;
    addRequirements(driveSys);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSys.drive(controller.getLeftY(), -controller.getLeftX());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
