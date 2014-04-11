package com.thoughtworks.twist2;

import main.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class SpecExecutionEndingProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        Set<Method> afterSpecHooks = HooksRegistry.getAfterSpecHooks();
        MethodExecutor methodExecutor = new MethodExecutor();
        for (Method afterSpecHook : afterSpecHooks) {
            Messages.ExecutionStatus status = methodExecutor.execute(afterSpecHook);
            if (!status.hasPassed()) {
                return Messages.Message.newBuilder().setMessageId(message.getMessageId())
                        .setMessageType(Messages.Message.MessageType.ExecutionStatusResponse)
                        .setExecutionStatusResponse(Messages.ExecutionStatusResponse.newBuilder().setExecutionStatus(status).build())
                        .build();
            }
        }

        Messages.ExecutionStatus passingExecution = Messages.ExecutionStatus.newBuilder().setPassed(true).build();
        return Messages.Message.newBuilder().setMessageId(message.getMessageId())
                .setMessageType(Messages.Message.MessageType.ExecutionStatusResponse)
                .setExecutionStatusResponse(Messages.ExecutionStatusResponse.newBuilder().setExecutionStatus(passingExecution).build())
                .build();
    }
}
