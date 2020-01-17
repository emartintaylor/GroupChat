package com.nagesagi.groupchat.MessageHandler;

import com.nagesagi.groupchat.Message.Message;

/**
 * Handles messages from the server.
 */
public interface MessageProcessor {

    /**
     * Handles messages from the server.
     * @param message Message from the server
     */
    void processMessage(final Message message);
}
