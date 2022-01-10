package frc.tigerlib.command.button;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.tigerlib.XboxControllerIT.DPadDirection;

/** A {@link Button} that gets its state from the d-pad on an {@link XboxController}. */
public class DPadButton extends POVButton {
    
    /**
     * Creates a d-pad button for triggering commands
     * 
     * @param controller The {@link XboxController} object that has the d-pad
     * @param direction The desired direction on the d-pad (see {@link DPadDirection})
     */
    public DPadButton(XboxController controller, DPadDirection direction) {
        super(controller, direction.value);
    }

    /**
     * Checks whether the current direction of the d-pad is the target direction.
     *
     * @return Whether the direction of the d-pad matches the target direction
     */
    @Override
    public boolean get() {
        return super.get();
    }
}
