package me.stuntguy3000.java.ledhub;

import java.util.Arrays;

import lombok.Getter;
import me.stuntguy3000.java.ledhub.action.LEDFadeToAction;
import me.stuntguy3000.java.ledhub.action.LEDStaticAction;
import me.stuntguy3000.java.ledhub.handler.ConfigHandler;
import me.stuntguy3000.java.ledhub.handler.SerialHandler;
import me.stuntguy3000.java.ledhub.handler.ServiceHandler;
import me.stuntguy3000.java.ledhub.object.LEDService;
import me.stuntguy3000.java.ledhub.object.LEDServiceAction;

/**
 * @author stuntguy3000
 */
public class LEDHub {
    /**
     * Handler Instances
     */
    @Getter
    private ConfigHandler configHandler;
    @Getter
    private SerialHandler serialHandler;
    @Getter
    private ServiceHandler serviceHandler;

    /**
     * Instance of LEDHub
     */
    @Getter
    private static LEDHub instance;

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

        // Register Handlers
        registerHandlers();

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
                                for (LEDService ledService : getServiceHandler().getAllServices().values()) {
                                    System.out.println(String.format("Service %s has %d action(s).", ledService.getServiceName(), ledService.getServiceActions().size()));
                                }

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

                                for (LEDServiceAction trigger : ledService.getServiceActions().values()) {
                                    if (trigger instanceof LEDFadeToAction) {
                                        LEDFadeToAction ledFadeToAction = (LEDFadeToAction) trigger;
                                        System.out.println(" Type: LEDFadeToAction");
                                        System.out.println(String.format("  Start Colour: %s", ledFadeToAction.getStartColour().toString()));
                                        System.out.println(String.format("  End Colour: %s", ledFadeToAction.getEndColour().toString()));
                                        System.out.println(String.format("  Fade Speed: %d", ledFadeToAction.getTriggerLife()));
                                    } else if (trigger instanceof LEDStaticAction) {
                                        LEDStaticAction ledStaticAction = (LEDStaticAction) trigger;
                                        System.out.println(" Type: LEDStaticAction");
                                        System.out.println(String.format("  End Colour: %s", ledStaticAction.getEndColour().toString()));
                                    } else {
                                        System.out.println("Unknown class type!");
                                    }

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

    /**
     * Register any handlers, such as configuration
     */
    private void registerHandlers() {
        configHandler = new ConfigHandler();
        serialHandler = new SerialHandler();
        serviceHandler = new ServiceHandler();

        /**
         * Connect to the Serial port
         */
        serialHandler.connectPort();
    }
}
