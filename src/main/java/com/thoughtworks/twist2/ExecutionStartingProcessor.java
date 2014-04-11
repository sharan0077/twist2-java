package com.thoughtworks.twist2;

import main.Messages;

public class ExecutionStartingProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        return message;
    }
}
