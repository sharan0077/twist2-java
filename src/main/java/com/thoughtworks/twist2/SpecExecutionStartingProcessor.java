package com.thoughtworks.twist2;

import java.lang.reflect.Method;
import java.util.Set;

import static main.Messages.*;

public class SpecExecutionStartingProcessor implements IMessageProcessor {
    @Override
    public Message process(Message message) {
        Set<Method> beforeSpecHooks = HooksRegistry.getBeforeSpecHooks();
        MethodExecutor methodExecutor = new MethodExecutor();
        for (Method beforeSpecHook : beforeSpecHooks) {
            ExecutionStatus status = methodExecutor.execute(beforeSpecHook);
            if (!status.hasPassed()) {
                return Message.newBuilder().setMessageId(message.getMessageId())
                        .setMessageType(Message.MessageType.ExecutionStatusResponse)
                        .setExecutionStatusResponse(ExecutionStatusResponse.newBuilder().setExecutionStatus(status).build())
                        .build();
            }
        }
        ExecutionStatus passingExecution = ExecutionStatus.newBuilder().setPassed(true).build();
        return Message.newBuilder().setMessageId(message.getMessageId())
                .setMessageType(Message.MessageType.ExecutionStatusResponse)
                .setExecutionStatusResponse(ExecutionStatusResponse.newBuilder().setExecutionStatus(passingExecution).build())
                .build();
    }
}
