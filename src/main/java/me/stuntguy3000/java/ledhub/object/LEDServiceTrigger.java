package me.stuntguy3000.java.ledhub.object;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author stuntguy3000
 */
@Data
@AllArgsConstructor
public abstract class LEDServiceTrigger {
    private LEDColour endColour;
    private int triggerLife;
}
