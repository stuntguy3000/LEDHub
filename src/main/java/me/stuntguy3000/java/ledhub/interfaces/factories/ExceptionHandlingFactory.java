package me.stuntguy3000.java.ledhub.interfaces.factories;

import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.CatchHandler;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.ExceptionHandler;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.TryHandler;

public interface ExceptionHandlingFactory {
    ExceptionHandler createExceptionHandler(TryHandler tryHandler, CatchHandler catchHandler);
}
