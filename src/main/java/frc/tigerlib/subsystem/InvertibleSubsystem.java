package frc.tigerlib.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface InvertibleSubsystem extends Subsystem {
    public void setInverted();

    public void setStandard();
    public boolean isInverted();
}