package me.stuntguy3000.java.ledhub.interfaces.factories;

import me.stuntguy3000.java.ledhub.impl.factories.FactoryFactoryFactory;

public interface FactoryFactory {

    GsonCreationFactory createGsonCreationFactory();

    GsonOptionCreationFactory createGsonOptionCreationFactory();

    ArrayCreationFactory createArrayCreationFactory();

    FileCreationFactory createFileCreationFactory();

    ExceptionHandlingFactory createExceptionHandlingFactory();

    ReaderCreationFactory createReaderCreationFactory();

    static FactoryFactory createFactory() {
        return new FactoryFactoryFactory();
    }
}
