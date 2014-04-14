package com.thoughtworks.twist2;

import main.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class ScenarioExecutionEndingProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        Set<Method> beforeSpecHooks = HooksRegistry.getAfterScenarioHooks();
        MethodExecutor methodExecutor = new MethodExecutor();
        for (Method beforeSpecHook : beforeSpecHooks) {
            Messages.ExecutionStatus status = methodExecutor.execute(beforeSpecHook);
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
