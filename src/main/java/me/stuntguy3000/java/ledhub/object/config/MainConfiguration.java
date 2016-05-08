package me.stuntguy3000.java.ledhub.object.config;

import java.util.HashMap;

import lombok.Data;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.LEDColor;
import me.stuntguy3000.java.ledhub.object.LEDService;
import me.stuntguy3000.java.ledhub.object.LEDServiceActionWrapper;

/**
 * @author stuntguy3000
 */
@Data
public class MainConfiguration {
    private String serialPort = "COM3";
    private HashMap<String, LEDService> ledServices = new HashMap<>();
    private LEDColor defaultColor = new LEDColor(0, 100, 255);

    public void addService(LEDService ledService) {
        ledServices.put(ledService.getServiceName().toLowerCase(), ledService);
        LEDHub.getInstance().getConfigHandler().saveConfig();
    }

    public void addAction(LEDService service, LEDServiceActionWrapper ledServiceActionWrapper) {
        service.getServiceActions().put(ledServiceActionWrapper.getActionName().toLowerCase(), ledServiceActionWrapper.getLedServiceActionType());
        LEDHub.getInstance().getConfigHandler().saveConfig();
    }
}
