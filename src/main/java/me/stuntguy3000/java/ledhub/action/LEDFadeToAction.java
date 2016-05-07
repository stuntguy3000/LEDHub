package me.stuntguy3000.java.ledhub.action;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.stuntguy3000.java.ledhub.object.LEDColour;
import me.stuntguy3000.java.ledhub.object.LEDServiceAction;

/**
 * @author stuntguy3000
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LEDFadeToAction extends LEDServiceAction {
    private LEDColour startColour;

    public LEDFadeToAction(LEDColour endColour, int triggerLife, LEDColour startColour) {
        setEndColour(endColour);
        setTriggerLife(triggerLife);

        this.startColour = startColour;
    }
}
