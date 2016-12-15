package me.stuntguy3000.java.ledhub.object.config;

import lombok.Data;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.LEDBackground;
import me.stuntguy3000.java.ledhub.object.LEDService;
import me.stuntguy3000.java.ledhub.object.LEDServiceActionWrapper;

import java.util.ArrayList;

/**
 * @author stuntguy3000
 */
@Data
public class MainConfiguration {
    private String serialPort = "COM3";
    private double ledMultiplier = 1;
    private ArrayList<LEDService> services = new ArrayList<>();
    private ArrayList<LEDBackground> backgrounds = new ArrayList<>();

    public void addService(LEDService ledService) {
        services.add(ledService);
        LEDHub.getInstance().getConfigHandler().saveConfig();
    }

    public void addAction(LEDService service, LEDServiceActionWrapper ledServiceActionWrapper) {
        service.getServiceActions().put(ledServiceActionWrapper.getActionName().toLowerCase(), ledServiceActionWrapper.getLedServiceActionType());
        LEDHub.getInstance().getConfigHandler().saveConfig();
    }
}
