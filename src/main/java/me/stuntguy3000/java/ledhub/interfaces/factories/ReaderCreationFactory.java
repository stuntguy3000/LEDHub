package me.stuntguy3000.java.ledhub.interfaces.factories;

import java.io.File;
import java.io.Reader;

public interface ReaderCreationFactory {
    Reader createBufferedReader(Reader reader);

    Reader createFileReader(File file);
}
