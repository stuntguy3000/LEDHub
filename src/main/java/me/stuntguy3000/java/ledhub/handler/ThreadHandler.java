package me.stuntguy3000.java.ledhub.handler;

import java.util.LinkedList;

import lombok.Getter;
import me.stuntguy3000.java.ledhub.object.LEDServiceAction;
import me.stuntguy3000.java.ledhub.task.LEDServiceActionTask;

/**
 * @author stuntguy3000
 */
public class ThreadHandler {
    @Getter
    private LEDServiceActionTask ledServiceActionTask;

    public Thread getNewTask(LEDServiceAction ledServiceAction) {
        LinkedList<LEDServiceAction> ledServiceActions = new LinkedList<>();
        ledServiceActions.add(ledServiceAction);

        return getNewTask(ledServiceActions);
    }

    public LEDServiceActionTask getNewTask(LinkedList<LEDServiceAction> actions) {
        if (ledServiceActionTask != null) {
            ledServiceActionTask.interrupt();
        }

        ledServiceActionTask = new LEDServiceActionTask(actions);
        return ledServiceActionTask;
    }
}
