package me.stuntguy3000.java.ledhub.impl.factories;

import lombok.SneakyThrows;
import me.stuntguy3000.java.ledhub.interfaces.factories.StreamCreationFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class StreamCreationFactoryImpl implements StreamCreationFactory {
    @Override
    @SneakyThrows
    public OutputStream createFileOutputStream(File file) {
        return new FileOutputStream(file);
    }
}
