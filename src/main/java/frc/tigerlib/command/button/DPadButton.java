package frc.tigerlib.command.button;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.tigerlib.XboxControllerIT.DPadDirection;

/** 
 * A {@link Button} that gets its state from the d-pad on an {@link XboxController}. 
 * 
 * @deprecated Replace with {@link Trigger} and POV methods in {@link CommandXboxController}.
*/
@Deprecated(since = "2023", forRemoval = true)
public class DPadButton extends POVButton {
    
    /**
     * Creates a d-pad button for triggering commands
     * 
     * @param controller The {@link XboxController} object that has the d-pad
     * @param direction The desired direction on the d-pad (see {@link DPadDirection})
     * 
     * @deprecated Replace with {@link Trigger} and POV methods in {@link CommandXboxController}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public DPadButton(XboxController controller, DPadDirection direction) {
        super(controller, direction.value);
    }

    /**
     * Checks whether the current direction of the d-pad is the target direction.
     *
     * @return Whether the direction of the d-pad matches the target direction
     * 
     * @deprecated Use {@link #getAsBoolean()}.
     */
    @Deprecated(since = "2023", forRemoval = true)
    public boolean get() {
        return getAsBoolean();
    }


}
