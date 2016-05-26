package me.stuntguy3000.java.ledhub.task;

import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.handler.TimerHandler;
import me.stuntguy3000.java.ledhub.object.LEDServiceAction;

/**
 * @author stuntguy3000
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class LEDServiceActionTask extends Thread {

    private LinkedList<LEDServiceAction> actions;

    @Override
    public void run() {
        try {
            for (LEDServiceAction action : actions) {
                if (action != null) {
                    switch (action.getType()) {
                        case STATIC: {
                            LEDHub.getInstance().getSerialHandler().sendData(action.getEndColour().toString());
                            int expireTime = action.getActionLife();

                            if (expireTime > 0) {
                                Thread.sleep(expireTime);
                            }
                            return;
                        }
                        case TRANSITION: {
                            TimerHandler.fadeColours(action.getStartColour(), action.getEndColour(), action.getActionLife());
                        }
                    }
                }
            }

            LEDHub.getInstance().getServiceHandler().processQueue();
        } catch (InterruptedException ignore) {

        }
    }
}
