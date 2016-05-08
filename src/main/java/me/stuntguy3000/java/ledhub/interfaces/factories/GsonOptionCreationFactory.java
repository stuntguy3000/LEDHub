package me.stuntguy3000.java.ledhub.interfaces.factories;

import me.stuntguy3000.java.ledhub.interfaces.gsonoptions.GsonOption;

public interface GsonOptionCreationFactory {
    GsonOption createDisableHtmlEscapingOption();
    GsonOption createSetPrettyPrintingOption();
}
