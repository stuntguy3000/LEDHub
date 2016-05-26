package me.stuntguy3000.java.ledhub.object;

import java.util.HashMap;

import lombok.Data;

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
