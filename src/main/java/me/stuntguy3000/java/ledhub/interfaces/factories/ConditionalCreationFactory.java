package me.stuntguy3000.java.ledhub.interfaces.factories;

import me.stuntguy3000.java.ledhub.interfaces.conditionals.Condition;
import me.stuntguy3000.java.ledhub.interfaces.conditionals.ConditionalExecutor;
import me.stuntguy3000.java.ledhub.interfaces.conditionals.FalseConditionalExecutor;
import me.stuntguy3000.java.ledhub.interfaces.conditionals.TrueConditionalExecutor;

import java.util.function.Supplier;

public interface ConditionalCreationFactory {
    ConditionalExecutor createConditionalExecutor(Condition condition, TrueConditionalExecutor trueConditionalExecutor, FalseConditionalExecutor falseConditionalExecutor);

    Condition createCondition(Supplier<Boolean> condition);
}
