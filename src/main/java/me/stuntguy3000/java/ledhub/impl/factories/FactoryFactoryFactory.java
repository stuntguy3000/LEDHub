package me.stuntguy3000.java.ledhub.impl.factories;

import me.stuntguy3000.java.ledhub.interfaces.factories.ArrayCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.FactoryFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.GsonCreationFactory;
import me.stuntguy3000.java.ledhub.interfaces.factories.GsonOptionCreationFactory;

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
}
