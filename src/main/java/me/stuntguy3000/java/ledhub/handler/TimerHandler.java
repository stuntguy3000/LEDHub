package me.stuntguy3000.java.ledhub.handler;

import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.LEDColour;

/**
 * @author stuntguy3000
 */
public class TimerHandler {
    /**
     * Fade between two LEDColour's over totalTime
     * <p>Warning: It is important to consider that totalTime will not reflect an accurate total amount of time taken to fade.</p>
     * <p>There is a very slight delay when sending the serial data.</p>
     *
     * @param current
     * @param end
     * @param totalTime
     * @throws InterruptedException
     */
    public static void fadeColours(LEDColour current, LEDColour end, long totalTime) throws InterruptedException {
        float differenceR = end.getR() - current.getR();
        float differenceG = end.getG() - current.getG();
        float differenceB = end.getB() - current.getB();

        float updateAmountR = 0, updateAmountG = 0, updateAmountB = 0;

        if (differenceR != 0) {
            updateAmountR = differenceR / totalTime;
        }

        if (differenceG != 0) {
            updateAmountG = differenceG / totalTime;
        }

        if (differenceB != 0) {
            updateAmountB = differenceB / totalTime;
        }

        float r = current.getR(), g = current.getG(), b = current.getB();

        for (int i = 0; i < totalTime; i++) {
            r += updateAmountR;
            g += updateAmountG;
            b += updateAmountB;

            long systemTimeOneSecond = System.nanoTime() + 1000000;

            LEDColour ledColour = new LEDColour(Math.round(r), Math.round(g), Math.round(b));
            LEDHub.getInstance().getSerialHandler().sendData(ledColour.getString(LEDHub.MULTIPLIER));
            LEDHub.getInstance().getAppHandler().updateImage(ledColour);

            boolean oneSecond = true;
            while (oneSecond) {
                if (System.nanoTime() >= systemTimeOneSecond) {
                    oneSecond = false;
                }
            }
        }
    }

    public static void cutColours(LEDColour startColour, LEDColour endColour, long actionLife) throws InterruptedException {
        LEDHub.getInstance().getSerialHandler().sendData(startColour.getString(LEDHub.MULTIPLIER));
        LEDHub.getInstance().getAppHandler().updateImage(startColour);
        Thread.sleep(actionLife / 2);

        LEDHub.getInstance().getSerialHandler().sendData(endColour.getString(LEDHub.MULTIPLIER));
        LEDHub.getInstance().getAppHandler().updateImage(endColour);
        Thread.sleep(actionLife / 2);
    }
}
