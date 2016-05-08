package me.stuntguy3000.java.ledhub.impl.factories;

import lombok.SneakyThrows;
import me.stuntguy3000.java.ledhub.interfaces.factories.ReaderCreationFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class ReaderCreationFactoryImpl implements ReaderCreationFactory {
    @Override
    public Reader createBufferedReader(Reader reader) {
        return new BufferedReader(reader);
    }

    @Override
    @SneakyThrows
    public Reader createFileReader(File file) {
        return new FileReader(file);
    }
}
