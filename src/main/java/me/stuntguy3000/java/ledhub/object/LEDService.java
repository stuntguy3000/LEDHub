package me.stuntguy3000.java.ledhub.object;

import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author stuntguy3000
 */
@Data
public class LEDService {

    private String serviceName;
    private HashMap<String, LinkedList<LEDAction>> serviceActions;

    public LEDService(String serviceName, HashMap<String, LinkedList<LEDAction>> serviceActions) {
        this.serviceName = serviceName;
        this.serviceActions = serviceActions;
    }
}
