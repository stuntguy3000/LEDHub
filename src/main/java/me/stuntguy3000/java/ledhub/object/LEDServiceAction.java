package me.stuntguy3000.java.ledhub.object;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author stuntguy3000
 */
@Data
@AllArgsConstructor
public class LEDServiceAction {
    private LEDServiceActionType type;
    private LEDColour endColour;
    private LEDColour startColour;
    private int actionLife;
}
