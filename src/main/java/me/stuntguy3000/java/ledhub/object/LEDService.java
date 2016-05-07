package me.stuntguy3000.java.ledhub.object;

import java.util.HashMap;
import java.util.LinkedHashSet;

import lombok.Data;

/**
 * @author stuntguy3000
 */
@Data
public class LEDService {
    private String serviceName;
    private HashMap<String, LEDServiceAction> serviceActions;
    private transient LinkedHashSet<String> actionsToProcess = new LinkedHashSet<>();

    public LEDService(String serviceName, HashMap<String, LEDServiceAction> serviceActions) {
        this.serviceName = serviceName;
        this.serviceActions = serviceActions;
    }

    public void activate() {
        actionsToProcess.clear();

        for (String ledServiceAction : serviceActions.keySet()) {
            actionsToProcess.add(ledServiceAction);
        }

        for (String ledServiceName : actionsToProcess) {
            LEDServiceAction action = serviceActions.get(ledServiceName);

            if (action != null) {
                switch (action.getType()) {
                    case STATIC: {

                    }
                }
            }
        }
    }
}
