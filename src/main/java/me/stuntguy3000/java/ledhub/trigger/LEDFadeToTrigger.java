package me.stuntguy3000.java.ledhub.trigger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.stuntguy3000.java.ledhub.object.LEDColour;
import me.stuntguy3000.java.ledhub.object.LEDServiceTrigger;

/**
 * @author stuntguy3000
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LEDFadeToTrigger extends LEDServiceTrigger {
    private LEDColour startColour;
    private int fadeSpeed;

    public LEDFadeToTrigger(LEDColour endColour, int triggerLife, LEDColour startColour, int fadeSpeed) {
        super(endColour, triggerLife);

        this.startColour = startColour;
        this.fadeSpeed = fadeSpeed;
    }
}
