package com.thoughtworks.twist2;

import main.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class SpecExecutionEndingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        Set<Method> afterSpecHooks = HooksRegistry.getAfterSpecHooks();
        return execute(afterSpecHooks, message);
    }
}
