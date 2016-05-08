package me.stuntguy3000.java.ledhub.impl.conditionals;

import me.stuntguy3000.java.ledhub.interfaces.conditionals.Condition;

import java.util.function.Supplier;

public class ConditionImpl implements Condition {
    private Supplier<Boolean> supplier;

    public ConditionImpl(Supplier<Boolean> supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean compute() {
        return supplier.get();
    }
}
