package me.stuntguy3000.java.ledhub.hook.rocketleague;

import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.handler.ServiceHandler;
import me.stuntguy3000.java.ledhub.object.LEDBackground;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author stuntguy3000
 */
public class RocketLeagueHook {
    public void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(1133);
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            int dataBuffer;
            int[] lineBuffer = new int[32];
            int index = 0;

            while ((dataBuffer = in.read()) != -1) {
                char character = (char) dataBuffer;

                if (character == '$') {
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int charCode : lineBuffer) {
                        stringBuilder.append((char) charCode);
                    }
                    processInput(stringBuilder.toString());
                    index = 0;
                } else {
                    lineBuffer[index] = dataBuffer;
                    index += 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processInput(String line) {
        ServiceHandler serviceHandler = LEDHub.getInstance().getServiceHandler();
        if (line.startsWith("RL_OPEN")) {
            serviceHandler.setServiceBackground(new LEDBackground(
                    null,
                    LEDHub.getInstance().getServiceHandler().getService("Rocket League")
                            .getServiceActions().get("Game Open"), false
            ));
            serviceHandler.processQueue();
        } else if (line.startsWith("RL_EXIT")) {
            serviceHandler.addToServiceQueue(
                    LEDHub.getInstance().getServiceHandler().getService("Rocket League")
                            .getServiceActions().get("Game Close"));

            serviceHandler.setServiceBackground(null);
            serviceHandler.processQueue();
        } else if (line.startsWith("RL_BOOST:")) {
            String[] split = line.split(":");
            String bootNumber = split[1];

            StringBuilder cleaned = new StringBuilder();
            for (byte numberByte : bootNumber.getBytes()) {
                char numberChar = (char) numberByte;

                if (Character.isDigit(numberChar)) {
                    cleaned.append(numberChar);
                }
            }

            int boostAmount = Integer.parseInt(cleaned.toString().trim());
            if (boostAmount > 0) {
                if (boostAmount > 0 && boostAmount <= 20) {
                    serviceHandler.setServiceBackground(new LEDBackground(
                            null,
                            LEDHub.getInstance().getServiceHandler().getService("Rocket League")
                                    .getServiceActions().get("Boost 0-20"), false
                    ));
                } else if (boostAmount > 20 && boostAmount <= 40) {
                    serviceHandler.setServiceBackground(new LEDBackground(
                            null,
                            LEDHub.getInstance().getServiceHandler().getService("Rocket League")
                                    .getServiceActions().get("Boost 21-40"), false
                    ));
                } else if (boostAmount > 40 && boostAmount <= 60) {
                    serviceHandler.setServiceBackground(new LEDBackground(
                            null,
                            LEDHub.getInstance().getServiceHandler().getService("Rocket League")
                                    .getServiceActions().get("Boost 41-60"), false
                    ));
                } else if (boostAmount > 60 && boostAmount <= 80) {
                    serviceHandler.setServiceBackground(new LEDBackground(
                            null,
                            LEDHub.getInstance().getServiceHandler().getService("Rocket League")
                                    .getServiceActions().get("Boost 61-80"), false
                    ));
                } else if (boostAmount > 80 && boostAmount <= 100) {
                    serviceHandler.setServiceBackground(new LEDBackground(
                            null,
                            LEDHub.getInstance().getServiceHandler().getService("Rocket League")
                                    .getServiceActions().get("Boost 81-100"), false
                    ));
                }
            } else {
                serviceHandler.setServiceBackground(new LEDBackground(
                        null,
                        LEDHub.getInstance().getServiceHandler().getService("Rocket League")
                                .getServiceActions().get("Game Open"), false
                ));
            }
            serviceHandler.processQueue();
        }
    }
}
