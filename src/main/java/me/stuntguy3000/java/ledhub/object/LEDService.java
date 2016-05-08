package me.stuntguy3000.java.ledhub.object;

import java.util.HashMap;
import java.util.LinkedHashSet;

import lombok.Data;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.handler.TimerHandler;

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
        if (actionsToProcess == null) {
            actionsToProcess = new LinkedHashSet<>();
        }

        actionsToProcess.clear();
        actionsToProcess.addAll(serviceActions.keySet());

        for (String ledServiceName : actionsToProcess) {
            LEDServiceAction action = serviceActions.get(ledServiceName);

            if (action != null) {
                switch (action.getType()) {
                    case STATIC: {
                        LEDHub.getInstance().getSerialHandler().sendData(action.getEndColor().toString());
                        int expireTime = action.getActionLife();

                        if (expireTime > 0) {
                            try {
                                Thread.sleep(expireTime);
                            } catch (InterruptedException ignored) {

                            }

                            LEDHub.getInstance().getSerialHandler().sendData
                                    (LEDHub.getInstance().getConfigHandler().getMainConfiguration().getDefaultColor().toString());
                        }
                        continue;
                    }
                    case TRANSITION: {
                        try {
                            TimerHandler.fadeColors(action.getStartColor(), action.getEndColor(), action.getActionLife());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
