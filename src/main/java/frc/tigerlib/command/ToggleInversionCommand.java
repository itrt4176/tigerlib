// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.tigerlib.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.tigerlib.subsystem.InvertibleSubsystem;

public class ToggleInversionCommand extends CommandBase {
  private InvertibleSubsystem[] subsystems;
  private boolean inverted;

  /** Creates a new InvertCommand. */
  public ToggleInversionCommand(InvertibleSubsystem... subsystems) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.subsystems = subsystems;
    addRequirements(this.subsystems);

    for (InvertibleSubsystem subsystem : this.subsystems) {
      subsystem.setStandard();
    }

    inverted = false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (inverted) {
      for (InvertibleSubsystem subsystem : subsystems) {
        subsystem.setStandard();
      }
    } else {
      for (InvertibleSubsystem subsystem : subsystems) {
        subsystem.setInverted();
      }
    }

    inverted = !inverted;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
