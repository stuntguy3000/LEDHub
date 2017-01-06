package me.stuntguy3000.java.ledhub.handler;

import lombok.Data;
import me.stuntguy3000.java.ledhub.object.LEDAction;
import me.stuntguy3000.java.ledhub.task.LEDActionExecutor;

import java.util.LinkedList;

/**
 * @author stuntguy3000
 */
@Data
public class ThreadHandler {
    private LEDActionExecutor ledActionExecutor;

    public Thread getNewTask(LEDAction ledAction, boolean processQueue) {
        LinkedList<LEDAction> ledActions = new LinkedList<>();
        ledActions.add(ledAction);

        return getNewTask(ledActions, processQueue);
    }

    public LEDActionExecutor getNewTask(LinkedList<LEDAction> actions, boolean processQueue) {
        if (ledActionExecutor != null) {
            ledActionExecutor.interrupt();
            System.out.println("Interrupting");
        }

        ledActionExecutor = new LEDActionExecutor(actions, processQueue);
        return ledActionExecutor;
    }
}
