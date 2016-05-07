package me.stuntguy3000.java.ledhub.action;

import me.stuntguy3000.java.ledhub.object.LEDColour;
import me.stuntguy3000.java.ledhub.object.LEDServiceAction;

/**
 * @author stuntguy3000
 */
public class LEDStaticAction extends LEDServiceAction {
    public LEDStaticAction(LEDColour endColour, int triggerLife) {
        setEndColour(endColour);
        setTriggerLife(triggerLife);
    }
}
