package me.stuntguy3000.java.ledhub.interfaces.factories;

import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.CatchExecutor;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.CatchHandler;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.ExceptionExecutor;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.TryHandler;

public interface ExceptionHandlingFactory {
    ExceptionExecutor createExceptionExecutor(TryHandler tryHandler, CatchExecutor[] catchExecutors);

    CatchExecutor createCatchExecutor(Class<? extends Throwable> type, CatchHandler handler);
}
