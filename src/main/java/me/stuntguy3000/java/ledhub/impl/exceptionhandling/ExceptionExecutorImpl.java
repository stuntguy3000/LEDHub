package me.stuntguy3000.java.ledhub.impl.exceptionhandling;

import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.CatchExecutor;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.ExceptionExecutor;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.TryHandler;

public class ExceptionExecutorImpl implements ExceptionExecutor {
    private TryHandler tryHandler;
    private CatchExecutor[] catchExecutors;

    public ExceptionExecutorImpl(TryHandler tryHandler, CatchExecutor[] catchExecutors) {
        this.tryHandler = tryHandler;
        this.catchExecutors = catchExecutors;
    }

    @Override
    public void execute() {
        try {
            tryHandler.run();
        } catch (Throwable t) {
            for (CatchExecutor catchExecutor : catchExecutors) {
                CatchExecutorImpl impl = (CatchExecutorImpl) catchExecutor;
                if (t.getClass().isAssignableFrom(impl.getType())) {
                    catchExecutor.handle(t);
                    break;
                }
            }
        }
    }
}
