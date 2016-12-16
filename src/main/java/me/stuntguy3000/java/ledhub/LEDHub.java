package me.stuntguy3000.java.ledhub;

import lombok.Getter;
import me.stuntguy3000.java.ledhub.handler.*;
import me.stuntguy3000.java.ledhub.hook.CSGOHook;
import me.stuntguy3000.java.ledhub.object.LEDService;
import me.stuntguy3000.java.ledhub.object.LEDServiceAction;
import org.apache.catalina.startup.Tomcat;

import java.util.Arrays;

/**
 * @author stuntguy3000
 */
public class LEDHub {
    public static double MULTIPLIER = 1;
    /**
     * Instance of LEDHub
     */
    @Getter
    private static LEDHub instance;
    @Getter
    private AppHandler appHandler;
    /**
     * Handler Instances
     */
    @Getter
    private ConfigHandler configHandler;
    @Getter
    private SerialHandler serialHandler;
    @Getter
    private ServiceHandler serviceHandler;
    @Getter
    private ThreadHandler threadHandler;
    @Getter
    private TimerHandler timerHandler;

    /**
     * Create a new instance of LEDHub
     * <p>Used on application startup</p>
     *
     * @param args String[] arguments (ignored)
     */
    public static void main(String[] args) {
        new LEDHub().main();
    }

    /**
     * Begin startup procedures
     */
    private void main() {
        instance = this;

        // Register Handlers/Start Webserver
        try {
            registerHandlers();
            startWebserver();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Command System
        while (true) {
            String fullCommand = System.console().readLine();
            String commandLabel = fullCommand.toLowerCase();
            String[] args = fullCommand.split(" ");

            if (args.length > 1) {
                args = Arrays.copyOfRange(args, 1, args.length);
            } else {
                args = null;
            }

            if (commandLabel.contains(" ")) {
                commandLabel = commandLabel.split(" ")[0];
            }

            switch (commandLabel) {
                default: {
                    System.out.println("Unknown command!");
                    continue;
                }
                case "exit":
                case "end":
                case "stop": {
                    System.exit(0);
                    continue;
                }
                case "service": {
                    if (!(args == null)) {
                        if (args.length == 1) {
                            if (args[0].equalsIgnoreCase("list")) {
                                for (LEDService ledService : getServiceHandler().getAllServices()) {
                                    System.out.println(String.format("Service %s has %d action(s).", ledService.getServiceName(), ledService.getServiceActions().size()));
                                }

                                continue;
                            }

                            LEDService service = getServiceHandler().getService(args[0]);

                            if (service != null) {
                                getServiceHandler().addToServiceQueue(service);
                                continue;
                            }
                        } else if (args.length == 2 && args[0].equalsIgnoreCase("info")) {
                            LEDService ledService = getServiceHandler().getService(args[1]);
                            if (ledService == null) {
                                System.out.println("Unknown service!");
                            } else {
                                System.out.println("====== Service " + ledService.getServiceName() + " info ======");
                                System.out.println(String.format(" LED Action count: %d", ledService.getServiceActions().size()));
                                System.out.println();

                                for (LEDServiceAction action : ledService.getServiceActions().values()) {
                                    System.out.println(" Type: " + action.getType().name());
                                    if (action.getStartColour() != null) {
                                        System.out.println(String.format("  Start Colour: %s", action.getStartColour().toString()));
                                    }

                                    if (action.getEndColour() != null) {
                                        System.out.println(String.format("  End Colour: %s", action.getEndColour().toString()));
                                    }

                                    System.out.println(String.format("  Timer: %d", action.getActionLife()));

                                    System.out.println();
                                }

                                System.out.println("End Information");
                            }
                            continue;
                        }
                    }

                    System.out.println("service command help:");
                    System.out.println("service list - View list of services");
                    System.out.println("service info <service> - View information on service");
                    System.out.println("service <service> - Activate service");
                }
            }
        }
    }

    private void startWebserver() throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(configHandler.getMainConfiguration().getWebserverPort());
        tomcat.getHost().setAppBase(".");
        tomcat.addWebapp("/", ".");
        tomcat.start();
    }

    /**
     * Register any handlers, such as configuration
     */
    private void registerHandlers() throws InterruptedException {
        configHandler = new ConfigHandler();
        serialHandler = new SerialHandler();
        serviceHandler = new ServiceHandler();
        timerHandler = new TimerHandler();
        appHandler = new AppHandler();
        threadHandler = new ThreadHandler();

        MULTIPLIER = getConfigHandler().getMainConfiguration().getLedMultiplier();

        if (MULTIPLIER <= 0) {
            MULTIPLIER = 1;
        }

        /**
         * Connect to the Serial port
         */
        appHandler.showUI();
        serialHandler.connectPort();
        serviceHandler.processQueue();

        new CSGOHook().init();
    }

    public void restart() {
        serviceHandler.reset();
        serviceHandler.processQueue();
    }
}
