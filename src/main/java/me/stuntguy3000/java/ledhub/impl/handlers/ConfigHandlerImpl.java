package me.stuntguy3000.java.ledhub.impl.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;

import lombok.Getter;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.ExceptionHandler;
import me.stuntguy3000.java.ledhub.interfaces.factories.ArrayCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.ExceptionHandlingFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.FactoryFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.FileCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.GsonCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.GsonOptionCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.ReaderCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.gsonoptions.GsonOption;
import me.stuntguy3000.java.ledhub.interfaces.handlers.ConfigHandler;
import me.stuntguy3000.java.ledhub.object.LEDColor;
import me.stuntguy3000.java.ledhub.object.LEDService;
import me.stuntguy3000.java.ledhub.object.LEDServiceAction;
import me.stuntguy3000.java.ledhub.object.LEDServiceActionType;
import me.stuntguy3000.java.ledhub.object.config.MainConfiguration;

// @author Luke Anderson | stuntguy3000
public class ConfigHandlerImpl implements ConfigHandler {
    private final LEDHub application;

    @Getter
    private Gson gson;
    @Getter
    private MainConfiguration mainConfiguration;

    public ConfigHandlerImpl() {
        this.application = LEDHub.getInstance();

        FactoryFactory factory = FactoryFactory.createFactory();
        GsonCreationFactory gsonCreationFactory = factory.createGsonCreationFactory();
        GsonOptionCreationFactory gsonOptionCreationFactory = factory.createGsonOptionCreationFactory();
        ArrayCreationFactory arrayCreationFactory = factory.createArrayCreationFactory();

        GsonOption disableHtmlEscapingOption = gsonOptionCreationFactory.createDisableHtmlEscapingOption();
        GsonOption setPrettyPrintingOption = gsonOptionCreationFactory.createSetPrettyPrintingOption();

        GsonOption[] gsonOptionArray = arrayCreationFactory.createArray(disableHtmlEscapingOption, setPrettyPrintingOption);

        this.gson = gsonCreationFactory.createGson(gsonOptionArray);

        loadConfig();
    }

    public void loadConfig() {
        FactoryFactory factoryFactory = FactoryFactory.createFactory();
        FileCreationFactory fileCreationFactory = factoryFactory.createFileCreationFactory();
        File configFile = fileCreationFactory.createFile("config.json");

        ExceptionHandlingFactory exceptionHandlingFactory = factoryFactory.createExceptionHandlingFactory();

        ExceptionHandler exceptionHandler = exceptionHandlingFactory.createExceptionHandler(() -> {
            if (!configFile.exists()) {
                saveConfig();
                loadConfig();
            } else {
                ReaderCreationFactory readerCreationFactory = factoryFactory.createReaderCreationFactory();
                Reader fileReader = readerCreationFactory.createFileReader(configFile);
                Reader bufferedReader = readerCreationFactory.createBufferedReader(fileReader);
                mainConfiguration = gson.fromJson(bufferedReader, MainConfiguration.class);
            }
        }, t -> {
            t.printStackTrace();
        });

        exceptionHandler.execute();
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
                            new LEDColor(255, 255, 255), null, 2000));
            actions.put("changegreen",
                    new LEDServiceAction(
                            LEDServiceActionType.TRANSITION,
                            new LEDColor(255, 255, 255), new LEDColor(0, 255, 0), 2000));

            LEDService ledService = new LEDService("testService", actions);
            mainConfiguration.getLedServices().put(ledService.getServiceName().toLowerCase(), ledService);
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
    