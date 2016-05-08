package me.stuntguy3000.java.ledhub.impl.factories;

import me.stuntguy3000.java.ledhub.impl.gsonoptions.GsonDisableHtmlEscapingOptionImpl;
import me.stuntguy3000.java.ledhub.impl.gsonoptions.GsonSetPrettyPrintingOptionImpl;
import me.stuntguy3000.java.ledhub.interfaces.factories.GsonOptionCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.gsonoptions.GsonOption;

public class GsonOptionCreationFactoryImpl implements GsonOptionCreationFactory {
    @Override
    public GsonOption createDisableHtmlEscapingOption() {
        return new GsonDisableHtmlEscapingOptionImpl();
    }

    @Override
    public GsonOption createSetPrettyPrintingOption() {
        return new GsonSetPrettyPrintingOptionImpl();
    }
}
