package me.stuntguy3000.java.ledhub.handler;

import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.LEDColor;

/**
 * @author stuntguy3000
 */
public class TimerHandler {
    public static void fadeColors(LEDColor endColor, int totalTime) throws InterruptedException {
        fadeColors(new LEDColor(0, 0, 0), endColor, totalTime);
    }

    public static void fadeColors(LEDColor current, LEDColor end, int totalTime) throws InterruptedException {
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

        for (int i = 0; i <= totalTime; i++) {
            r += updateAmountR;
            g += updateAmountG;
            b += updateAmountB;

            LEDColor ledColor = new LEDColor(Math.round(r), Math.round(g), Math.round(b));
            LEDHub.getInstance().getSerialHandler().sendData(ledColor.toString());

            Thread.sleep(1);
        }
    }
}
