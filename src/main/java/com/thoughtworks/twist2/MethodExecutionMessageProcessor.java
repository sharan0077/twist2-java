package com.thoughtworks.twist2;

import main.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public abstract class MethodExecutionMessageProcessor {
    public Messages.Message execute(Set<Method> methods, Messages.Message message) {
        MethodExecutor methodExecutor = new MethodExecutor();
        for (Method method : methods) {
            Messages.ExecutionStatus status = methodExecutor.execute(method);
            if (!status.hasPassed()) {
                return Messages.Message.newBuilder()
                        .setMessageId(message.getMessageId())
                        .setMessageType(Messages.Message.MessageType.ExecutionStatusResponse)
                        .setExecutionStatusResponse(Messages.ExecutionStatusResponse.newBuilder().setExecutionStatus(status).build())
                        .build();
            }
        }

        Messages.ExecutionStatus passingExecution = Messages.ExecutionStatus.newBuilder().setPassed(true).build();
        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setMessageType(Messages.Message.MessageType.ExecutionStatusResponse)
                .setExecutionStatusResponse(Messages.ExecutionStatusResponse.newBuilder().setExecutionStatus(passingExecution).build())
                .build();
    }
}
