package me.stuntguy3000.java.ledhub;

import lombok.Getter;
import me.stuntguy3000.java.ledhub.handler.ConfigHandler;
import me.stuntguy3000.java.ledhub.handler.SerialHandler;

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
            // Keep Alive
        }
    }

    /**
     * Register any handlers, such as configuration
     */
    private void registerHandlers() {
        configHandler = new ConfigHandler();
        serialHandler = new SerialHandler();

        /**
         * Connect to the Serial port
         */
        serialHandler.connectPort();
    }
}
