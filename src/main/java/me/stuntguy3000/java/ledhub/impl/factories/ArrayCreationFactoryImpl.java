package me.stuntguy3000.java.ledhub.impl.factories;

import me.stuntguy3000.java.ledhub.interfaces.factories.ArrayCreationFactory;

import java.lang.reflect.Array;

public class ArrayCreationFactoryImpl implements ArrayCreationFactory {
    @Override
    public <T> T[] createArray(T... t) {
        return t;
    }
}
