package me.stuntguy3000.java.ledhub.trigger;

import me.stuntguy3000.java.ledhub.object.LEDColour;
import me.stuntguy3000.java.ledhub.object.LEDServiceTrigger;

/**
 * @author stuntguy3000
 */
public class LEDStaticTrigger extends LEDServiceTrigger {
    public LEDStaticTrigger(LEDColour endColour, int triggerLife) {
        super(endColour, triggerLife);
    }
}
