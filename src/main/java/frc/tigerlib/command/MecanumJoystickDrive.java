// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.tigerlib.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.tigerlib.subsystem.drive.MecanumDriveSubsystem;

/**
 * Prewritten command to drive a {@link MecanumDriveSubsystem} with an
 * {@link XboxController}.
 */
public class MecanumJoystickDrive extends CommandBase {
  private MecanumDriveSubsystem driveSys;
  private CommandXboxController controller;

  /**
   * Construct a MecanumJoystickDrive command. Left joystick is used for
   * forward movement and strafing. X-axis of the right joystick is used
   * for rotation.
   * 
   * <p>
   * This command should be set as the default for the subsystem after
   * creation.
   * 
   * 
   * @param driveSubsystem {@link MecanumDriveSubsystem} to drive.
   * @param controller     {@link CommandXboxController} to use for driving.
   */
  public MecanumJoystickDrive(MecanumDriveSubsystem driveSubsystem, CommandXboxController controller) {
    // Use addRequirements() here to declare subsystem dependencies.
    driveSys = driveSubsystem;
    this.controller = controller;

    addRequirements(driveSys);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSys.drive(controller.getLeftY(), -controller.getLeftX(), -controller.getRightX());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
