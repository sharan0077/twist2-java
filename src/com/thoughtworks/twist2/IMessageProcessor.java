package com.thoughtworks.twist2;

import main.Messages;

public interface IMessageProcessor {
    Messages.Message process(Messages.Message message);
}
