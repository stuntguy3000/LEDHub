package me.stuntguy3000.java.ledhub.object;

import lombok.Data;

/**
 * @author stuntguy3000
 */
@Data
public class LEDServiceActionWrapper {
    private String triggerName;
    private LEDServiceAction ledServiceActionType;
}
