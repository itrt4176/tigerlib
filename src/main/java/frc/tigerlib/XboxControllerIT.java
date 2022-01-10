package frc.tigerlib;

import edu.wpi.first.wpilibj.XboxController;

/**
 * Handle input from Xbox 360 or Xbox One controllers connected to the Driver
 * Station. Allows you to use the d-pad as buttons in addition to an axis.
 *
 * <p>
 * This class handles Xbox input that comes from the Driver Station. Each time a
 * value is
 * requested the most recent value is returned. There is a single class instance
 * for each controller
 * and the mapping of ports to hardware buttons depends on the code in the
 * Driver Station.
 */
public class XboxControllerIT extends XboxController {
    private int lastPOV = -1;
    private double deadzone;

    /** Represents a d-pad direction on an XboxController. */
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

    public XboxControllerIT(final int port) {
        super(port);
        deadzone = 0.05;
    }

    public void setDeadzone(double deadzone) {
        this.deadzone = deadzone;
    }

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
     */
    public int getPOV() {
        lastPOV = super.getPOV();
        return lastPOV;
    }

    /**
     * Read the value of the left D-Pad button on the controller.
     *
     * @return The state of the button.
     */
    public boolean getLeftDPad() {
        lastPOV = super.getPOV();
        return lastPOV == DPadDirection.kLeft.value;
    }

    /**
     * Whether the left D-Pad button was pressed since the last check.
     *
     * @return Whether the button was pressed since the last check.
     */
    public boolean getLeftDPadPressed() {
        int pov = super.getPOV();
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
     */
    public boolean getLeftDPadReleased() {
        int pov = super.getPOV();
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
     */
    public boolean getRightDPad() {
        lastPOV = super.getPOV();
        return lastPOV == DPadDirection.kRight.value;
    }


    /**
     * Whether the right D-Pad button was pressed since the last check.
     *
     * @return Whether the button was pressed since the last check.
     */
    public boolean getRightDPadPressed() {
        int pov = super.getPOV();
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
     */
    public boolean getRightDPadReleased() {
        int pov = super.getPOV();
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
     */
    public boolean getUpDPad() {
        lastPOV = super.getPOV();
        return lastPOV == DPadDirection.kUp.value;
    }

    /**
     * Whether the up D-Pad button was pressed since the last check.
     *
     * @return Whether the button was pressed since the last check.
     */
    public boolean getUpDPadPressed() {
        int pov = super.getPOV();
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
     */
    public boolean getUpDPadReleased() {
        int pov = super.getPOV();
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
     */
    public boolean getDownDPad() {
        lastPOV = super.getPOV();
        return lastPOV == DPadDirection.kDown.value;
    }

    /**
     * Whether the right d-pad button was pressed since the last check.
     *
     * @return Whether the button was pressed since the last check.
     */
    public boolean getDownDPadPressed() {
        int pov = super.getPOV();
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
     */
    public boolean getDownDPadReleased() {
        int pov = super.getPOV();
        if (lastPOV == DPadDirection.kDown.value && pov != DPadDirection.kDown.value) {
            lastPOV = pov;
            return true;
        } else {
            lastPOV = pov;
            return false;
        }
    }
}
