package me.stuntguy3000.java.ledhub.handler;

import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.LEDColour;

/**
 * @author stuntguy3000
 */
public class TimerHandler {
    public static void fadeColours(LEDColour endColour, int totalTime) throws InterruptedException {
        fadeColours(new LEDColour(0, 0, 0), endColour, totalTime);
    }

    public static void fadeColours(LEDColour current, LEDColour end, int totalTime) throws InterruptedException {
        int differenceR = Math.max(current.getR(), end.getR()) - Math.min(current.getR(), end.getR());
        int differenceG = Math.max(current.getG(), end.getG()) - Math.min(current.getG(), end.getG());
        int differenceB = Math.max(current.getB(), end.getB()) - Math.min(current.getB(), end.getB());

        double changePerMillisecondR = 0, changePerMillisecondG = 0, changePerMillisecondB = 0;

        if (differenceR != 0) {
            changePerMillisecondR = totalTime / differenceR;
        }

        if (differenceG != 0) {
            changePerMillisecondG = totalTime / differenceG;
        }

        if (differenceB != 0) {
            changePerMillisecondB = totalTime / differenceB;
        }

        double r = current.getR();
        double g = current.getG();
        double b = current.getB();

        for (int i = 0; i <= totalTime; i++) {
            r += changePerMillisecondR;
            g += changePerMillisecondG;
            b += changePerMillisecondB;

            LEDColour ledColour = new LEDColour((int) r, (int) g, (int) b);
            LEDHub.getInstance().getSerialHandler().sendData(ledColour.toString());

            Thread.sleep(1);
        }
    }
}
