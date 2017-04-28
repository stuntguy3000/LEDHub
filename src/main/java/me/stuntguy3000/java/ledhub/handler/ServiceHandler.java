package me.stuntguy3000.java.ledhub.handler;

import lombok.Data;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.LEDAction;
import me.stuntguy3000.java.ledhub.object.LEDBackground;
import me.stuntguy3000.java.ledhub.object.LEDService;
import me.stuntguy3000.java.ledhub.object.config.MainConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author stuntguy3000
 */
@Data
public class ServiceHandler {
    private Queue<LEDAction> serviceQueue = new LinkedList<>();
    private boolean isBackgroundRunning = false;
    private LEDBackground serviceBackground = null;
    private LEDBackground defaultBackground = null;

    public ServiceHandler() {
        defaultBackground = LEDHub.getInstance().getConfigHandler().getMainConfiguration().getBackgrounds().get(0);
    }

    public void addToServiceQueue(LEDService ledService) {
        ledService.getServiceActions().values().forEach(this::addToServiceQueue);
    }

    public void addToServiceQueue(LinkedList<LEDAction> ledActions) {
        for (LEDAction ledAction : ledActions) {
            addToServiceQueue(ledAction);
        }
    }

    public void addToServiceQueue(LEDAction ledAction) {
        if (ledAction != null) {
            switch (ledAction.getLedServiceQueueCondition()) {
                case ALWAYS_QUEUE: {
                    serviceQueue.add(ledAction);

                    if (serviceQueue.size() == 1 && isBackgroundRunning) {
                        processQueue();
                    }
                    break;
                }
                case JUMP_QUEUE: {
                    isBackgroundRunning = false;
                    LEDHub.getInstance().getThreadHandler().getNewTask(ledAction).start();
                    break;
                }
                case RUN_IF_FREE: {
                    if (isBackgroundRunning) {
                        LEDHub.getInstance().getThreadHandler().getNewTask(ledAction).start();
                    }
                    break;
                }
            }
        }
    }

    public void processQueue() {
        if (serviceQueue.size() > 0) {
            if (isBackgroundRunning) {
                isBackgroundRunning = false;
            }

            LEDHub.getInstance().getThreadHandler().getNewTask(serviceQueue.remove()).start();
        } else {
            isBackgroundRunning = true;

            if (serviceBackground == null) {
                if (defaultBackground == null) {
                    MainConfiguration mainConfiguration = LEDHub.getInstance().getConfigHandler().getMainConfiguration();

                    if (!mainConfiguration.getBackgrounds().isEmpty()) {
                        defaultBackground = mainConfiguration.getBackgrounds().get(0);
                    } else {
                        return;
                    }
                }

                if (defaultBackground.isShuffle()) {
                    Collections.shuffle(defaultBackground.getActions());
                }

                LEDHub.getInstance().getThreadHandler().getNewTask(defaultBackground.getActions(), false).start();
            } else {
                LEDHub.getInstance().getThreadHandler().getNewTask(serviceBackground.getActions(), false).start();
            }
        }
    }

    /**
     * Returns an LEDService by name
     * <p>Returns null if non existent</p>
     *
     * @param name String name of the LEDService
     * @return LEDService the requested LEDService, null if invalid
     */
    public LEDService getService(String name) {
        for (LEDService service : LEDHub.getInstance().getConfigHandler().getMainConfiguration().getServices()) {
            if (service.getServiceName().equalsIgnoreCase(name)) {
                return service;
            }
        }

        return null;
    }

    /**
     * Returns all known LEDService's
     *
     * @return HashMap all known LEDService's
     */
    public ArrayList<LEDService> getAllServices() {
        return LEDHub.getInstance().getConfigHandler().getMainConfiguration().getServices();
    }

    public void reset() {
        serviceBackground = null;

        if (LEDHub.getInstance().getThreadHandler().getLedActionExecutor() != null) {
            LEDHub.getInstance().getThreadHandler().getLedActionExecutor().interrupt();
        }

        getServiceQueue().clear();
        isBackgroundRunning = false;
    }
}
