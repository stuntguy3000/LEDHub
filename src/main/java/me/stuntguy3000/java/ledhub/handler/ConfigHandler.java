package me.stuntguy3000.java.ledhub.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

import lombok.Getter;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.config.MainConfiguration;

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
            System.out.println("Error saving config.json!");
        }
    }
}
    