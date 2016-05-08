package me.stuntguy3000.java.ledhub.impl.conditionals;

import lombok.SneakyThrows;
import me.stuntguy3000.java.ledhub.interfaces.conditionals.Condition;
import me.stuntguy3000.java.ledhub.interfaces.conditionals.ConditionalExecutor;
import me.stuntguy3000.java.ledhub.interfaces.conditionals.FalseConditionalExecutor;
import me.stuntguy3000.java.ledhub.interfaces.conditionals.TrueConditionalExecutor;

public class ConditionalExecutorImpl implements ConditionalExecutor {
    private Condition condition;
    private TrueConditionalExecutor ifTrue;
    private FalseConditionalExecutor ifFalse;

    public ConditionalExecutorImpl(Condition condition, TrueConditionalExecutor trueConditionalExecutor, FalseConditionalExecutor falseConditionalExecutor) {
        this.condition = condition;
        this.ifTrue = trueConditionalExecutor;
        this.ifFalse = falseConditionalExecutor;
    }

    @Override
    @SneakyThrows
    public void execute() {
        if (condition.compute()) {
            ifTrue.execute();
        } else {
            ifFalse.execute();
        }
    }
}
