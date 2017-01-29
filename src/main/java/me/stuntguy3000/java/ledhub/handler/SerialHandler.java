package me.stuntguy3000.java.ledhub.handler;

import jssc.SerialPort;
import jssc.SerialPortException;
import lombok.Data;
import me.stuntguy3000.java.ledhub.LEDHub;

/**
 * @author stuntguy3000
 */
@Data
public class SerialHandler {
    private String lastSent = "";
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
}
