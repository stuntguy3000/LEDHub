package me.stuntguy3000.java.ledhub.impl.factories;

import me.stuntguy3000.java.ledhub.interfaces.factories.FileCreationFactory;

import java.io.File;

public class FileCreationFactoryImpl implements FileCreationFactory {
    @Override
    public File createFile(String fileName) {
        return new File(fileName);
    }
}
