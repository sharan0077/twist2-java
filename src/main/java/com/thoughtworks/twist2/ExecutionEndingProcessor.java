package com.thoughtworks.twist2;

import main.Messages;

public class ExecutionEndingProcessor implements IMessageProcessor {

    @Override
    public Messages.Message process(Messages.Message message) {
        return message;
    }

}
