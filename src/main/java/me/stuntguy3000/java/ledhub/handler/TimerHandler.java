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
        float differenceR = Math.max(current.getR(), end.getR()) - Math.min(current.getR(), end.getR());
        float differenceG = Math.max(current.getG(), end.getG()) - Math.min(current.getG(), end.getG());
        float differenceB = Math.max(current.getB(), end.getB()) - Math.min(current.getB(), end.getB());

        float updateAmountR = 0, updateAmountG = 0, updateAmountB = 0;

        if (differenceR > 0) {
            updateAmountR = differenceR / totalTime;
        }

        if (differenceG > 0) {
            updateAmountG = differenceG / totalTime;
        }

        if (differenceB > 0) {
            updateAmountB = differenceB / totalTime;
        }

        float r = 0, g = 0, b = 0;

        for (int i = 0; i <= totalTime; i++) {
            r += updateAmountR;
            g += updateAmountG;
            b += updateAmountB;

            LEDColour ledColour = new LEDColour(Math.round(r), Math.round(g), Math.round(b));
            LEDHub.getInstance().getSerialHandler().sendData(ledColour.toString());

            Thread.sleep(1);
        }
    }
}
