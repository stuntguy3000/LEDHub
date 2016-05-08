package me.stuntguy3000.java.ledhub.impl.factories;

import me.stuntguy3000.java.ledhub.impl.exceptionhandling.ExceptionHandlerImpl;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.CatchHandler;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.ExceptionHandler;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.TryHandler;
import me.stuntguy3000.java.ledhub.interfaces.factories.ExceptionHandlingFactory;

public class ExceptionHandlingFactoryImpl implements ExceptionHandlingFactory {
    @Override
    public ExceptionHandler createExceptionHandler(TryHandler tryHandler, CatchHandler catchHandler) {
        return new ExceptionHandlerImpl(tryHandler, catchHandler);
    }
}
