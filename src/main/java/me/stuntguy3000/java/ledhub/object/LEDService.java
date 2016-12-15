package me.stuntguy3000.java.ledhub.object;

import lombok.Data;

import java.util.HashMap;

/**
 * @author stuntguy3000
 */
@Data
public class LEDService {

    private String serviceName;
    private HashMap<String, LEDServiceAction> serviceActions;

    public LEDService(String serviceName, HashMap<String, LEDServiceAction> serviceActions) {
        this.serviceName = serviceName;
        this.serviceActions = serviceActions;
    }
}
