package me.stuntguy3000.java.ledhub.hook.hivemc;

import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.handler.ServiceHandler;
import me.stuntguy3000.java.ledhub.object.*;

import java.util.LinkedList;

import static spark.Spark.get;

/**
 * @author stuntguy3000
 */
public class HiveMCHook implements Runnable {
    private LEDColour[] colourArray = {
            // White                              Orange
            new LEDColour(255, 255, 255), new LEDColour(255, 69, 0),
            // Magenta                            Aqua
            new LEDColour(255, 0, 255), new LEDColour(0, 162, 255),
            // Yellow                             Lime
            new LEDColour(255, 255, 0), new LEDColour(50, 205, 50),
            // Pink                               Gray
            new LEDColour(255, 122, 122), new LEDColour(57, 41, 53),
            // Light Gray                         Cyan
            new LEDColour(136, 107, 97), new LEDColour(70, 130, 180),
            // Purple                             Blue
            new LEDColour(160, 32, 240), new LEDColour(0, 0, 180),
            // Brown                              Green
            new LEDColour(139, 69, 19), new LEDColour(0, 255, 0),
            // Red                                Black
            new LEDColour(255, 0, 0), new LEDColour(0, 0, 0),
    };

    public void run() {
        ServiceHandler serviceHandler = LEDHub.getInstance().getServiceHandler();

        get("/hivemc/load", (request, response) -> {
            System.out.println("HiveMC Load");
            serviceHandler.setServiceBackground(null);
            return "";
        });

        get("/hivemc/joinserver", (request, response) -> {
            System.out.println("HiveMC JoinServer");
            LEDAction ledAction = new LEDAction(LEDServiceActionType.STATIC, null,
                    new LEDColour(255, 255, 0),
                    LEDServiceQueueCondition.JUMP_QUEUE, -1);

            LinkedList<LEDAction> actions = new LinkedList<>();
            actions.add(ledAction);

            serviceHandler.setServiceBackground(new LEDBackground(
                    null, actions, false
            ));
            serviceHandler.processQueue();
            return "";
        });

        get("/hivemc/stop", (request, response) -> {
            System.out.println("HiveMC Stop");
            serviceHandler.setServiceBackground(null);
            serviceHandler.processQueue();

            return "";
        });

        get("/hivemc/colour/:colour", (request, response) -> {
            String colour = request.params(":colour");
            int colourID = Integer.parseInt(colour);

            LEDAction ledAction = new LEDAction(LEDServiceActionType.STATIC, null,
                    colourArray[colourID], LEDServiceQueueCondition.JUMP_QUEUE, -1);

            LinkedList<LEDAction> actions = new LinkedList<>();
            actions.add(ledAction);

            serviceHandler.setServiceBackground(new LEDBackground(null, actions, false));
            serviceHandler.processQueue();

            return "";
        });
    }
}
