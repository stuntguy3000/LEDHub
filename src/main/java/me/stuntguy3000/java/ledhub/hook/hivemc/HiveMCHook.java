package me.stuntguy3000.java.ledhub.hook.hivemc;

import static spark.Spark.*;

/**
 * @author stuntguy3000
 */
public class HiveMCHook implements Runnable {
    public void run() {
        get("/hivemc/start", (request, response) -> {
            System.out.println("HiveMC Start");
            return "";
        });

        get("/hivemc/stop", (request, response) -> {
            // Show something
            System.out.println("HiveMC Stop");
            return "";
        });

        get("/hivemc/colour/:colour", (request, response) -> {
            // Show something
            System.out.println("HiveMC Colour: " + request.params(":colour"));
            return "";
        });
    }
}
