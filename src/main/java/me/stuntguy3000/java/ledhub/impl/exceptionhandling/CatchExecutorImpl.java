package me.stuntguy3000.java.ledhub.impl.exceptionhandling;

import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.CatchExecutor;
import me.stuntguy3000.java.ledhub.interfaces.exceptionhandling.CatchHandler;

public class CatchExecutorImpl implements CatchExecutor {

    private Class<? extends Throwable> clazz;
    private CatchHandler handler;

    public CatchExecutorImpl(Class<? extends Throwable> clazz, CatchHandler handler) {
        this.clazz = clazz;
        this.handler = handler;
    }

    @Override
    public void handle(Throwable throwable) {
        handler.handle(throwable);
    }

    public Class<? extends Throwable> getType() {
        return this.clazz;
    }
}
