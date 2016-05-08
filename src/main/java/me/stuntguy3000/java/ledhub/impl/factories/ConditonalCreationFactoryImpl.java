package me.stuntguy3000.java.ledhub.impl.factories;

import me.stuntguy3000.java.ledhub.impl.conditionals.ConditionImpl;
import me.stuntguy3000.java.ledhub.impl.conditionals.ConditionalExecutorImpl;
import me.stuntguy3000.java.ledhub.interfaces.conditionals.Condition;
import me.stuntguy3000.java.ledhub.interfaces.conditionals.ConditionalExecutor;
import me.stuntguy3000.java.ledhub.interfaces.conditionals.FalseConditionalExecutor;
import me.stuntguy3000.java.ledhub.interfaces.conditionals.TrueConditionalExecutor;
import me.stuntguy3000.java.ledhub.interfaces.factories.ConditionalCreationFactory;

import java.util.function.Supplier;

public class ConditonalCreationFactoryImpl implements ConditionalCreationFactory {
    @Override
    public ConditionalExecutor createConditionalExecutor(Condition condition, TrueConditionalExecutor trueConditionalExecutor, FalseConditionalExecutor falseConditionalExecutor) {
        return new ConditionalExecutorImpl(condition, trueConditionalExecutor, falseConditionalExecutor);
    }

    @Override
    public Condition createCondition(Supplier<Boolean> condition) {
        return new ConditionImpl(condition);
    }

    @Override
    public TrueConditionalExecutor createEmptyTrueConditionalExecutor() {
        return () -> {};
    }

    @Override
    public FalseConditionalExecutor createEmptyFalseConditionalExecutor() {
        return () -> {};
    }
}
