package me.stuntguy3000.java.ledhub.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author stuntguy3000
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LEDServiceAction implements Cloneable {
    private LEDServiceActionType type;
    private LEDColour endColour;
    private LEDColour startColour;
    private LEDServiceQueueCondition ledServiceQueueCondition;
    private long actionLife;

    @Override
    public LEDServiceAction clone() {
        return new LEDServiceAction(type, endColour, startColour, ledServiceQueueCondition, actionLife);
    }
}
