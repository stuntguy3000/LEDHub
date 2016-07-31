package me.stuntguy3000.java.ledhub.handler;

import jssc.SerialPort;
import jssc.SerialPortException;
import lombok.Getter;
import me.stuntguy3000.java.ledhub.LEDHub;

/**
 * @author stuntguy3000
 */
public class SerialHandler {
    private String lastSent = "";
    @Getter
    private SerialPort serialPort;

    public void sendData(String data) {
        if (lastSent.isEmpty() || !lastSent.equalsIgnoreCase(data)) {
            try {
                lastSent = data;
                serialPort.writeString(data);
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
