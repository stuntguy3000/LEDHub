package me.stuntguy3000.java.ledhub.impl.factories;

import me.stuntguy3000.java.ledhub.impl.exceptionhandling.CatchExecutorImpl;
import me.stuntguy3000.java.ledhub.impl.exceptionhandling.ExceptionExecutorImpl;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.CatchExecutor;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.CatchHandler;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.ExceptionExecutor;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.TryHandler;
import me.stuntguy3000.java.ledhub.interfaces.factories.ExceptionHandlingFactory;

public class ExceptionHandlingFactoryImpl implements ExceptionHandlingFactory {
    @Override
    public ExceptionExecutor createExceptionExecutor(TryHandler tryHandler, CatchExecutor[] catchExecutors) {
        return new ExceptionExecutorImpl(tryHandler, catchExecutors);
    }

    @Override
    public CatchExecutor createCatchExecutor(Class<? extends Throwable> type, CatchHandler handler) {
        return new CatchExecutorImpl(type, handler);
    }
}
