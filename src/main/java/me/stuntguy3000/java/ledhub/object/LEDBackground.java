package me.stuntguy3000.java.ledhub.object;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

/**
 * @author stuntguy3000
 */
@Data
@AllArgsConstructor
public class LEDBackground {
    private String backgroundName;
    private LinkedList<LEDServiceAction> actions;
}
