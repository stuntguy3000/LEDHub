package me.stuntguy3000.java.ledhub.interfaces.factories;

public interface ArrayCreationFactory {
    <T> T[] createArray(T... t);
}
