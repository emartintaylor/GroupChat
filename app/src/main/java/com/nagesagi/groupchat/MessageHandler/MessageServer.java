package com.nagesagi.groupchat.MessageHandler;

/**
 * The server that the application talks to.
 */
public interface MessageServer {

    /**
     * Sends a message to the server.
     * @param message Message to the server.
     */
    void sendMessage(String message);
}
