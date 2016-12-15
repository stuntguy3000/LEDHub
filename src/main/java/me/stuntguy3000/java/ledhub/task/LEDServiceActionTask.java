package me.stuntguy3000.java.ledhub.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.handler.TimerHandler;
import me.stuntguy3000.java.ledhub.object.LEDServiceAction;

import java.util.LinkedList;

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
                            long expireTime = action.getActionLife();

                            if (expireTime > 0) {
                                Thread.sleep(expireTime);
                            }
                            break;
                        }
                        case TRANSITION: {
                            TimerHandler.fadeColours(action.getStartColour(), action.getEndColour(), action.getActionLife());
                            break;
                        }
                        case CUT: {
                            TimerHandler.cutColours(action.getStartColour(), action.getEndColour(), action.getActionLife());
                            break;
                        }
                        case BACKGROUND: {
                            LEDHub.getInstance().getSerialHandler().sendData(action.getEndColour().toString());
                            break;
                        }
                    }
                }
            }

            LEDHub.getInstance().getServiceHandler().processQueue();
        } catch (InterruptedException ignore) {

        }
    }
}
