package me.stuntguy3000.java.ledhub.impl.exceptionhandling;

import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.CatchHandler;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.ExceptionHandler;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.TryHandler;

public class ExceptionHandlerImpl implements ExceptionHandler {
    private TryHandler tryHandler;
    private CatchHandler catchHandler;

    public ExceptionHandlerImpl(TryHandler tryHandler, CatchHandler catchHandler) {
        this.tryHandler = tryHandler;
        this.catchHandler = catchHandler;
    }

    @Override
    public void execute() {
        try {
            tryHandler.run();
        } catch (Throwable t) {
            catchHandler.handle(t);
        }
    }
}
