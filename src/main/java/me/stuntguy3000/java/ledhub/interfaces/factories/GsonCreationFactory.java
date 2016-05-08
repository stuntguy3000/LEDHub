package me.stuntguy3000.java.ledhub.interfaces.factories;

import com.google.gson.Gson;
import me.stuntguy3000.java.ledhub.interfaces.gsonoptions.GsonOption;

public interface GsonCreationFactory {
    Gson createGson(GsonOption[] options);
}
