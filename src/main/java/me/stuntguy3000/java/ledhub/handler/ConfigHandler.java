package me.stuntguy3000.java.ledhub.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.*;
import me.stuntguy3000.java.ledhub.object.config.MainConfiguration;

import java.io.*;
import java.util.HashMap;
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

            HashMap<String, LEDServiceAction> actions = new HashMap<>();
            actions.put("enable",
                    new LEDServiceAction(
                            LEDServiceActionType.STATIC,
                            new LEDColour(255, 255, 255), null,
                            LEDServiceQueueCondition.ALWAYS_QUEUE, 2000));
            actions.put("changegreen",
                    new LEDServiceAction(
                            LEDServiceActionType.TRANSITION,
                            new LEDColour(255, 255, 255), null,
                            LEDServiceQueueCondition.ALWAYS_QUEUE, 2000));

            LEDService ledService = new LEDService("sampleService", actions);

            LEDServiceAction fadeBlueToRed = new LEDServiceAction(
                    LEDServiceActionType.TRANSITION,
                    new LEDColour(255, 0, 0),
                    new LEDColour(0, 0, 255),
                    LEDServiceQueueCondition.ALWAYS_QUEUE, 1000);

            LEDServiceAction fadeRedToGreen = new LEDServiceAction(
                    LEDServiceActionType.TRANSITION,
                    new LEDColour(0, 255, 0),
                    new LEDColour(255, 0, 0),
                    LEDServiceQueueCondition.ALWAYS_QUEUE, 1000);

            LEDServiceAction fadeGreenToBlue = new LEDServiceAction(
                    LEDServiceActionType.TRANSITION,
                    new LEDColour(0, 0, 255),
                    new LEDColour(0, 255, 0),
                    LEDServiceQueueCondition.ALWAYS_QUEUE, 1000);

            LinkedList<LEDServiceAction> ledServiceActions = new LinkedList<>();
            ledServiceActions.add(fadeBlueToRed);
            ledServiceActions.add(fadeRedToGreen);
            ledServiceActions.add(fadeGreenToBlue);

            LEDBackground ledBackground = new LEDBackground("rgbFade", ledServiceActions);

            mainConfiguration.getBackgrounds().add(ledBackground);
            mainConfiguration.getServices().add(ledService);
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
    