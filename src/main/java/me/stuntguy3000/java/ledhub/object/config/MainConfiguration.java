package me.stuntguy3000.java.ledhub.object.config;

import java.util.HashMap;

import lombok.Data;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.LEDService;
import me.stuntguy3000.java.ledhub.object.LEDServiceActionWrapper;

/**
 * @author stuntguy3000
 */
@Data
public class MainConfiguration {
    private String serialPort = "COM3";
    private HashMap<String, LEDService> ledServices = new HashMap<>();

    public void addService(LEDService ledService) {
        ledServices.put(ledService.getServiceName().toLowerCase(), ledService);
        LEDHub.getInstance().getConfigHandler().saveConfig();
    }

    public void addTrigger(LEDService service, LEDServiceActionWrapper ledServiceActionWrapper) {
        service.getServiceActions().put(ledServiceActionWrapper.getTriggerName().toLowerCase(), ledServiceActionWrapper.getLedServiceActionType());
        LEDHub.getInstance().getConfigHandler().saveConfig();
    }
}
