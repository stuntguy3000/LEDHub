package me.stuntguy3000.java.ledhub.object;

import lombok.Data;

/**
 * @author stuntguy3000
 */
@Data
public abstract class LEDServiceAction {
    private LEDColour endColour;
    private int triggerLife;
}
