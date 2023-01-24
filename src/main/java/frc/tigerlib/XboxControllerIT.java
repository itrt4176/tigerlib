package frc.tigerlib;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * Handle input from Xbox 360 or Xbox One controllers connected to the Driver
 * Station.
 * 
 * <p>
 * Allows you to use the d-pad as buttons and supports a built in deadzone. The
 * default deadzone is ±0.05.
 *
 * <p>
 * This class handles Xbox input that comes from the Driver Station. Each time a
 * value is
 * requested the most recent value is returned. There is a single class instance
 * for each controller
 * and the mapping of ports to hardware buttons depends on the code in the
 * Driver Station.
 */
public class XboxControllerIT extends CommandXboxController {
    private int lastPOV = -1;
    private double deadzone;

    /** 
     * Represents a d-pad direction on an XboxController. 
     * 
     * @deprecated Will be removed in 2024. 
     */
    @Deprecated(since = "2023", forRemoval = true)
    public enum DPadDirection {
        kUp(0),
        kRight(90),
        kDown(180),
        kLeft(270);

        public final int value;

        DPadDirection(int value) {
            this.value = value;
        }

        /**
         * Get the human-friendly name of the direction, matching the relevant methods.
         * This is done by
         * stripping the leading `k` and appending the angle.
         *
         * <p>
         * Primarily used for automated unit tests.
         *
         * @return the human-friendly name of the direction.
         */
        @Override
        public String toString() {
            var name = this.name().substring(1); // Remove leading `k`
            return name + " (" + this.value + "°)";
        }
    }

    /**
     * Construct an instance of a controller.
     *
     * @param port The port index on the Driver Station that the controller is
     *             plugged into.
     */
    public XboxControllerIT(final int port) {
        super(port);
        deadzone = 0.05;
    }

    /**
     * Returns 0.0 if the given value is within the deadzone around zero. The
     * remaining range between the deadband and 1.0 is scaled from 0.0 to 1.0.
     * 
     * @param deadzone The range ±0.0 which will be returned as 0.0.
     *                 Valid range: 0.0 - 1.0
     */
    public void setDeadzone(double deadzone) {
        this.deadzone = deadzone;
    }

    /**
     * Get the deadzone currently set for the controller.
     * 
     * @return Current deadzone
     */
    public double getDeadzone() {
        return deadzone;
    }

    private double applyDeadzone(double value) {
        if (Math.abs(value) > deadzone) {
            if (value > 0.0) {
                return (value - deadzone) / (1.0 - deadzone);
            } else {
                return (value + deadzone) / (1.0 - deadzone);
            }
        } else {
            return 0.0;
        }
    }

    @Override
    public double getLeftX() {
        return applyDeadzone(super.getLeftX());
    }

    @Override
    public double getLeftY() {
        return applyDeadzone(super.getLeftY());
    }

    @Override
    public double getRightX() {
        return applyDeadzone(super.getRightX());
    }

    @Override
    public double getRightY() {
        return applyDeadzone(super.getRightY());
    }

    /**
     * Get the angle in degrees of the default POV (index 0) on the HID.
     *
     * <p>
     * The POV angles start at 0 in the up direction, and increase clockwise (eg
     * right is 90,
     * upper-left is 315).
     *
     * @return the angle of the POV in degrees, or -1 if the POV is not pressed.
     * 
     * @deprecated Replace with {@link GenericHID#getPOV() getHID().getPOV()}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public int getPOV() {
        lastPOV = getHID().getPOV();
        return lastPOV;
    }

    /**
     * Read the value of the left D-Pad button on the controller.
     *
     * @return The state of the button.
     * 
     * @deprecated Replace with {@link #povLeft()} and {@link Trigger}
     */
    @Deprecated(since = "2023", forRemoval = true)
    public boolean getLeftDPad() {
        getPOV();
        return lastPOV == DPadDirection.kLeft.value;
    }

    /**
     * Whether the left D-Pad button was pressed since the last check.
     *
     * @return Whether the button was pressed since the last check.
     * 
     * @deprecated Replace with {@link #povLeft()} and {@link Trigger}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public boolean getLeftDPadPressed() {
        int pov = getHID().getPOV();
        if (lastPOV != DPadDirection.kLeft.value && pov == DPadDirection.kLeft.value) {
            lastPOV = pov;
            return true;
        } else {
            lastPOV = pov;
            return false;
        }
    }

    /**
     * Whether the left D-Pad button was released since the last check.
     *
     * @return Whether the button was released since the last check.
     * 
     * @deprecated Replace with {@link #povLeft()} and {@link Trigger}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public boolean getLeftDPadReleased() {
        int pov = getHID().getPOV();
        if (lastPOV == DPadDirection.kLeft.value && pov != DPadDirection.kLeft.value) {
            lastPOV = pov;
            return true;
        } else {
            lastPOV = pov;
            return false;
        }
    }


    /**
     * Read the value of the right D-Pad button on the controller.
     *
     * @return The state of the button.
     * 
     * @deprecated Replace with {@link #povRight()} and {@link Trigger}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public boolean getRightDPad() {
        getPOV();
        return lastPOV == DPadDirection.kRight.value;
    }


    /**
     * Whether the right D-Pad button was pressed since the last check.
     *
     * @return Whether the button was pressed since the last check.
     * 
     * @deprecated Replace with {@link #povRight()} and {@link Trigger}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public boolean getRightDPadPressed() {
        int pov = getHID().getPOV();
        if (lastPOV != DPadDirection.kRight.value && pov == DPadDirection.kRight.value) {
            lastPOV = pov;
            return true;
        } else {
            lastPOV = pov;
            return false;
        }
    }

    /**
     * Whether the right D-Pad button was released since the last check.
     *
     * @return Whether the button was released since the last check.
     * 
     * @deprecated Replace with {@link #povRight()} and {@link Trigger}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public boolean getRightDPadReleased() {
        int pov = getHID().getPOV();
        if (lastPOV == DPadDirection.kRight.value && pov != DPadDirection.kRight.value) {
            lastPOV = pov;
            return true;
        } else {
            lastPOV = pov;
            return false;
        }
    }

    /**
     * Read the value of the up D-Pad button on the controller.
     *
     * @return The state of the button.
     * 
     * @deprecated Replace with {@link #povUp()} and {@link Trigger}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public boolean getUpDPad() {
        getPOV();
        return lastPOV == DPadDirection.kUp.value;
    }

    /**
     * Whether the up D-Pad button was pressed since the last check.
     *
     * @return Whether the button was pressed since the last check.
     * 
     * @deprecated Replace with {@link #povUp()} and {@link Trigger}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public boolean getUpDPadPressed() {
        int pov = getHID().getPOV();
        if (lastPOV != DPadDirection.kUp.value && pov == DPadDirection.kUp.value) {
            lastPOV = pov;
            return true;
        } else {
            lastPOV = pov;
            return false;
        }
    }

    /**
     * Whether the up D-Pad button was released since the last check.
     *
     * @return Whether the button was released since the last check.
     * 
     * @deprecated Replace with {@link #povUp()} and {@link Trigger}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public boolean getUpDPadReleased() {
        int pov = getHID().getPOV();
        if (lastPOV == DPadDirection.kUp.value && pov != DPadDirection.kUp.value) {
            lastPOV = pov;
            return true;
        } else {
            lastPOV = pov;
            return false;
        }
    }

    /**
     * Read the value of the right d-pad button on the controller.
     *
     * @return The state of the button.
     * 
     * @deprecated Replace with {@link #povDown()} and {@link Trigger}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public boolean getDownDPad() {
        getPOV();
        return lastPOV == DPadDirection.kDown.value;
    }

    /**
     * Whether the right d-pad button was pressed since the last check.
     *
     * @return Whether the button was pressed since the last check.
     * 
     * @deprecated Replace with {@link #povDown()} and {@link Trigger}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public boolean getDownDPadPressed() {
        int pov = getHID().getPOV();
        if (lastPOV != DPadDirection.kDown.value && pov == DPadDirection.kDown.value) {
            lastPOV = pov;
            return true;
        } else {
            lastPOV = pov;
            return false;
        }
    }

    /**
     * Whether the right d-pad button was released since the last check.
     *
     * @return Whether the button was released since the last check.
     * 
     * @deprecated Replace with {@link #povDown()} and {@link Trigger}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public boolean getDownDPadReleased() {
        int pov = getHID().getPOV();
        if (lastPOV == DPadDirection.kDown.value && pov != DPadDirection.kDown.value) {
            lastPOV = pov;
            return true;
        } else {
            lastPOV = pov;
            return false;
        }
    }
}
