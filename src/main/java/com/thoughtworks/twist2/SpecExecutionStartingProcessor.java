package com.thoughtworks.twist2;

import java.lang.reflect.Method;
import java.util.Set;

import static main.Messages.*;

public class SpecExecutionStartingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    @Override
    public Message process(Message message) {
        Set<Method> beforeSpecHooks = HooksRegistry.getBeforeSpecHooks();
        return execute(beforeSpecHooks, message);
    }
}
