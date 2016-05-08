package me.stuntguy3000.java.ledhub.impl.factories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.stuntguy3000.java.ledhub.interfaces.factories.GsonCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.gsonoptions.GsonDisableHtmlEscapingOption;
import me.stuntguy3000.java.ledhub.interfaces.gsonoptions.GsonOption;
import me.stuntguy3000.java.ledhub.interfaces.gsonoptions.GsonSetPrettyPrintingOption;

public class GsonCreationFactoryImpl implements GsonCreationFactory{
    @Override
    public Gson createGson(GsonOption[] options) {
        GsonBuilder builder = new GsonBuilder();
        for (GsonOption option : options) {
            if (option instanceof GsonDisableHtmlEscapingOption) {
                builder.disableHtmlEscaping();
            } else if (option instanceof GsonSetPrettyPrintingOption) {
                builder.setPrettyPrinting();
            }
        }
        return builder.create();
    }
}
