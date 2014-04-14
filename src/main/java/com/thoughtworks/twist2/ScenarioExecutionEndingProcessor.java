package com.thoughtworks.twist2;

import main.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class ScenarioExecutionEndingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        Set<Method> afterScenarioHooks = HooksRegistry.getAfterScenarioHooks();
        return execute(afterScenarioHooks, message);
    }
}
