package me.stuntguy3000.java.ledhub.handler;

import java.util.HashMap;

import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.LEDService;
import me.stuntguy3000.java.ledhub.object.LEDServiceAction;
import me.stuntguy3000.java.ledhub.object.LEDServiceActionWrapper;

/**
 * @author stuntguy3000
 */
public class ServiceHandler {
    /**
     * Returns an LEDService by name
     * <p>Returns null if non existent</p>
     *
     * @param name String name of the LEDService
     * @return LEDService the requested LEDService, null if invalid
     */
    public LEDService getService(String name) {
        return LEDHub.getInstance().getConfigHandler().getMainConfiguration().getLedServices().get(name.toLowerCase());
    }

    /**
     * Create a new LEDService
     *
     * @param name String name of the LEDService
     * @return LEDService the new LEDService
     */
    public LEDService createService(String name) {
        LEDService ledService = new LEDService(name, null);

        LEDHub.getInstance().getConfigHandler().getMainConfiguration().addService(ledService);

        return ledService;
    }

    /**
     * Add a new LEDAction to a LEDService
     *
     * @param serviceName String the name of the service
     * @param ledServiceActionWrapper LEDServiceActionWrapper the information about the Action
     */
    public void addAction(String serviceName, LEDServiceActionWrapper ledServiceActionWrapper) {
        LEDHub.getInstance().getConfigHandler().getMainConfiguration()
                .addAction(getService(serviceName), ledServiceActionWrapper);
    }

    /**
     * Creates a ActionWrapper
     *
     * @param actionName String the name of the action
     * @param ledServiceAction LEDServiceAction the intended action
     * @return LEDServiceActionWrapper the created action wrapper
     */
    public LEDServiceActionWrapper createActionWrapper(String actionName, LEDServiceAction ledServiceAction) {
        LEDServiceActionWrapper wrapper = new LEDServiceActionWrapper();
        wrapper.setActionName(actionName);
        wrapper.setLedServiceActionType(ledServiceAction);

        return wrapper;
    }

    /**
     * Returns all known LEDService's
     * @return HashMap all known LEDService's
     */
    public HashMap<String, LEDService> getAllServices() {
        return LEDHub.getInstance().getConfigHandler().getMainConfiguration().getLedServices();
    }
}
