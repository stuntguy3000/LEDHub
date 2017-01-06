package me.stuntguy3000.java.ledhub.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.*;
import me.stuntguy3000.java.ledhub.object.config.MainConfiguration;

import java.io.*;
import java.util.LinkedList;

// @author Luke Anderson | stuntguy3000
public class ConfigHandler {
    private final LEDHub application;

    @Getter
    private Gson gson;
    @Getter
    private MainConfiguration mainConfiguration;

    public ConfigHandler() {
        this.application = LEDHub.getInstance();

        GsonBuilder builder = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting();
        gson = builder.create();

        loadConfig();
    }

    private void loadConfig() {
        File configFile = new File("config.json");

        BufferedReader br;
        try {
            if (!configFile.exists()) {
                saveConfig();
                loadConfig();
                return;
            } else {
                br = new BufferedReader(new FileReader(configFile));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        mainConfiguration = gson.fromJson(br, MainConfiguration.class);
    }

    public void saveConfig() {
        File configFile = new File("config.json");

        if (mainConfiguration == null) {
            mainConfiguration = new MainConfiguration();
            mainConfiguration.setSerialPort("com3");

            LEDAction fadeBlueToRed = new LEDAction(
                    LEDServiceActionType.TRANSITION,
                    new LEDColour(0, 0, 255), new LEDColour(255, 0, 0),
                    LEDServiceQueueCondition.ALWAYS_QUEUE, 1000);

            LEDAction fadeRedToGreen = new LEDAction(
                    LEDServiceActionType.TRANSITION,
                    new LEDColour(255, 0, 0), new LEDColour(0, 255, 0),
                    LEDServiceQueueCondition.ALWAYS_QUEUE, 1000);

            LEDAction fadeGreenToBlue = new LEDAction(
                    LEDServiceActionType.TRANSITION,
                    new LEDColour(0, 255, 0), new LEDColour(0, 0, 255),
                    LEDServiceQueueCondition.ALWAYS_QUEUE, 1000);

            LinkedList<LEDAction> ledActions = new LinkedList<>();
            ledActions.add(fadeBlueToRed);
            ledActions.add(fadeRedToGreen);
            ledActions.add(fadeGreenToBlue);

            LEDBackground ledBackground = new LEDBackground("rgbFade", ledActions, false);

            mainConfiguration.getBackgrounds().add(ledBackground);
        }

        String json = gson.toJson(mainConfiguration);
        FileOutputStream outputStream;

        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            outputStream = new FileOutputStream(configFile);
            assert json != null;
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error saving config file!");
        }
    }
}
    