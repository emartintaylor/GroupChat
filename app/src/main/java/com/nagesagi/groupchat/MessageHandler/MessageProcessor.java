package com.nagesagi.groupchat.MessageHandler;

import com.nagesagi.groupchat.Message.Message;

public interface MessageProcessor {
    void processMessage(final Message message);
}
