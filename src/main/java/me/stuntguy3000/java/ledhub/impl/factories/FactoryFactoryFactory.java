package me.stuntguy3000.java.ledhub.impl.factories;

import me.stuntguy3000.java.ledhub.interfaces.factories.ArrayCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.ConditionalCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.ExceptionHandlingFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.FactoryFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.FileCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.GsonCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.GsonOptionCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.ReaderCreationFactory;

public class FactoryFactoryFactory implements FactoryFactory {

    @Override
    public GsonCreationFactory createGsonCreationFactory() {
        return new GsonCreationFactoryImpl();
    }

    @Override
    public GsonOptionCreationFactory createGsonOptionCreationFactory() {
        return new GsonOptionCreationFactoryImpl();
    }

    @Override
    public ArrayCreationFactory createArrayCreationFactory() {
        return new ArrayCreationFactoryImpl();
    }

    @Override
    public FileCreationFactory createFileCreationFactory() {
        return new FileCreationFactoryImpl();
    }

    @Override
    public ExceptionHandlingFactory createExceptionHandlingFactory() {
        return new ExceptionHandlingFactoryImpl();
    }

    @Override
    public ReaderCreationFactory createReaderCreationFactory() {
        return new ReaderCreationFactoryImpl();
    }

    @Override
    public ConditionalCreationFactory createConditionalCreationFactory() {
        return new ConditonalCreationFactoryImpl();
    }
}
