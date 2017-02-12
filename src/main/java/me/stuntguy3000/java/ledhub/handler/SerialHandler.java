package me.stuntguy3000.java.ledhub.handler;

import com.logitech.gaming.LogiLED;
import jssc.SerialPort;
import jssc.SerialPortException;
import lombok.Data;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.LEDColour;

/**
 * @author stuntguy3000
 */
@Data
public class SerialHandler {
    private String lastSent = "";
    private long lastSentTime = 0;
    private SerialPort serialPort;

    public void sendData(LEDColour ledColour) {
        String serialMessage = ledColour.getString(LEDHub.MULTIPLIER);
        if (lastSent.isEmpty() || !lastSent.equalsIgnoreCase(serialMessage)) {
            try {
                lastSent = serialMessage;
                serialPort.writeString(serialMessage);

                if (System.nanoTime() - lastSentTime > 1e+6) {
                    float r = (float) ledColour.getR() / 255;
                    float g = (float) ledColour.getG() / 255;
                    float b = (float) ledColour.getB() / 255;
                    lastSentTime = System.nanoTime();

                    LogiLED.LogiLedSetLighting((int) (r * 100), (int) (g * 100), (int) (b * 100));
                }
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    public void connectPort() {
        serialPort = new SerialPort(LEDHub.getInstance().getConfigHandler().getMainConfiguration().getSerialPort());
        try {
            serialPort.openPort();
            serialPort.setParams(
                    SerialPort.BAUDRATE_256000,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE,
                    true, true);

            Thread.sleep(2000);
        } catch (SerialPortException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean closePort() {
        try {
            return serialPort.closePort();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return false;
    }
}
