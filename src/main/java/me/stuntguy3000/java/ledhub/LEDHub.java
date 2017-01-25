package me.stuntguy3000.java.ledhub;

import lombok.Getter;
import me.stuntguy3000.java.ledhub.handler.*;
import me.stuntguy3000.java.ledhub.hook.csgo.CSGOHook;
import me.stuntguy3000.java.ledhub.hook.hivemc.HiveMCHook;

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {

        }
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

        // Connect to the Serial port
        appHandler.showUI();
        serialHandler.connectPort();
        serviceHandler.processQueue();

        new CSGOHook().init();
        //new Thread(new RocketLeagueHook()).start();
        new Thread(new HiveMCHook()).start();
    }

    public void restart() {
        serviceHandler.reset();
        serviceHandler.processQueue();
    }
}
