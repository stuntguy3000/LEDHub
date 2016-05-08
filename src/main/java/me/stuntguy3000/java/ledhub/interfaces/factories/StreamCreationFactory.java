package me.stuntguy3000.java.ledhub.interfaces.factories;

import java.io.File;
import java.io.OutputStream;

public interface StreamCreationFactory {
    OutputStream createFileOutputStream(File file);
}
