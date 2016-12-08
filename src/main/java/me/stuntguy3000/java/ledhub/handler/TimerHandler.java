package me.stuntguy3000.java.ledhub.handler;

import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.LEDColour;

/**
 * @author stuntguy3000
 */
public class TimerHandler {
    public static void fadeColours(LEDColour endColour, long totalTime) throws InterruptedException {
        fadeColours(new LEDColour(0, 0, 0), endColour, totalTime);
    }

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

            LEDColour ledColour = new LEDColour(Math.round(r), Math.round(g), Math.round(b));
            LEDHub.getInstance().getSerialHandler().sendData(ledColour.toString());

            Thread.sleep(1);
        }
    }

    public static void flashColour(LEDColour ledColour, LEDColour defaultColour, int i) {
        LEDHub.getInstance().getSerialHandler().sendData(ledColour.toString());
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LEDHub.getInstance().getSerialHandler().sendData(defaultColour.toString());
    }

    public static void cutColours(LEDColour startColour, LEDColour endColour, long actionLife) {
        LEDHub.getInstance().getSerialHandler().sendData(startColour.toString());
        try {
            Thread.sleep(actionLife / 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LEDHub.getInstance().getSerialHandler().sendData(endColour.toString());
        try {
            Thread.sleep(actionLife / 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
