package frc.tigerlib.subsystem;

public interface InvertibleSubsystem {
    public void setInverted();

    public void setStandard();
    public boolean isInverted();
}