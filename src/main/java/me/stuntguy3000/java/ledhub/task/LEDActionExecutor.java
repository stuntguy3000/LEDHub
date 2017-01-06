package me.stuntguy3000.java.ledhub.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.handler.TimerHandler;
import me.stuntguy3000.java.ledhub.object.LEDAction;

import java.util.LinkedList;

/**
 * @author stuntguy3000
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class LEDActionExecutor extends Thread {

    private LinkedList<LEDAction> actions;

    @Override
    public void run() {
        try {
            for (LEDAction action : actions) {
                if (action != null) {
                    int playCount = action.getPlayCount();

                    if (playCount < 1) {
                        playCount = 1;
                    }

                    switch (action.getType()) {
                        case STATIC: {
                            for (int i = 0; i < playCount; i++) {
                                LEDHub.getInstance().getSerialHandler().sendData(action.getEndColour().getString(LEDHub.MULTIPLIER));
                                long expireTime = action.getActionLife();

                                if (expireTime > 0) {
                                    Thread.sleep(expireTime);
                                }
                            }
                            break;
                        }
                        case TRANSITION: {
                            for (int i = 0; i < playCount; i++) {
                                TimerHandler.fadeColours(action.getStartColour(), action.getEndColour(), action.getActionLife());
                            }
                            break;
                        }
                        case CUT: {
                            for (int i = 0; i < playCount; i++) {
                                TimerHandler.cutColours(action.getStartColour(), action.getEndColour(), action.getActionLife());
                            }
                            break;
                        }
                    }

                    if (action.getEndDelay() > 0) {
                        Thread.sleep(action.getEndDelay());
                    }
                }
            }

            LEDHub.getInstance().getServiceHandler().processQueue();
        } catch (InterruptedException ignore) {

        }
    }
}
