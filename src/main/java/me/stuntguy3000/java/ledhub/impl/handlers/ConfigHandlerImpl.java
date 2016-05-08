package me.stuntguy3000.java.ledhub.impl.handlers;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.HashMap;

import lombok.Getter;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.interfaces.conditionals.Condition;
import me.stuntguy3000.java.ledhub.interfaces.conditionals.ConditionalExecutor;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.CatchExecutor;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.ExceptionExecutor;
import me.stuntguy3000.java.ledhub.interfaces.factories.ArrayCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.ConditionalCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.ExceptionHandlingFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.FactoryFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.FileCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.GsonCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.GsonOptionCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.ReaderCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.StreamCreationFactory;
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

        CatchExecutor catchExecutor = exceptionHandlingFactory.createCatchExecutor(FileNotFoundException.class, Throwable::printStackTrace);

        ArrayCreationFactory arrayCreationFactory = factoryFactory.createArrayCreationFactory();
        CatchExecutor[] catchExecutors = arrayCreationFactory.createArray(catchExecutor);

        ExceptionExecutor exceptionExecutor = exceptionHandlingFactory.createExceptionExecutor(() -> {
            ConditionalCreationFactory conditionalCreationFactory = factoryFactory.createConditionalCreationFactory();
            Condition condition = conditionalCreationFactory.createCondition(configFile::exists);
            ConditionalExecutor executor = conditionalCreationFactory.createConditionalExecutor(condition, () -> {
                ReaderCreationFactory readerCreationFactory = factoryFactory.createReaderCreationFactory();
                Reader fileReader = readerCreationFactory.createFileReader(configFile);
                Reader bufferedReader = readerCreationFactory.createBufferedReader(fileReader);
                mainConfiguration = gson.fromJson(bufferedReader, MainConfiguration.class);
            }, () -> {
                saveConfig();
                loadConfig();
            });
            executor.execute();
        }, catchExecutors);
        exceptionExecutor.execute();
    }

    public void saveConfig() {
        FactoryFactory factoryFactory = FactoryFactory.createFactory();
        FileCreationFactory fileCreationFactory = factoryFactory.createFileCreationFactory();
        File configFile = fileCreationFactory.createFile("config.json");

        ConditionalCreationFactory conditionalCreationFactory = factoryFactory.createConditionalCreationFactory();
        Condition condition = conditionalCreationFactory.createCondition(() -> mainConfiguration == null);
        ConditionalExecutor conditionalExecutor = conditionalCreationFactory.createConditionalExecutor(condition, () ->{
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
        }, conditionalCreationFactory.createEmptyFalseConditionalExecutor());
        conditionalExecutor.execute();

        String json = gson.toJson(mainConfiguration);

        ExceptionHandlingFactory exceptionHandlingFactory = factoryFactory.createExceptionHandlingFactory();
        CatchExecutor catchExecutor = exceptionHandlingFactory.createCatchExecutor(Exception.class, t -> {
            t.printStackTrace();
            System.out.println("Error saving config.json!");
        });
        ArrayCreationFactory arrayCreationFactory = factoryFactory.createArrayCreationFactory();
        CatchExecutor[] catchExecutors = arrayCreationFactory.createArray(catchExecutor);

        ExceptionExecutor exceptionExecutor = exceptionHandlingFactory.createExceptionExecutor(() -> {
            Condition condition1 = conditionalCreationFactory.createCondition(configFile::exists);
            ConditionalExecutor conditionalExecutor1 = conditionalCreationFactory.createConditionalExecutor(condition1, conditionalCreationFactory.createEmptyTrueConditionalExecutor(), () -> {
                configFile.createNewFile();
            });
            conditionalExecutor1.execute();
            StreamCreationFactory streamCreationFactory = factoryFactory.createStreamCreationFactory();
            OutputStream outputStream = streamCreationFactory.createFileOutputStream(configFile);
            assert json != null;
            outputStream.write(json.getBytes());
            outputStream.close();
        }, catchExecutors);
        exceptionExecutor.execute();
    }
}
    