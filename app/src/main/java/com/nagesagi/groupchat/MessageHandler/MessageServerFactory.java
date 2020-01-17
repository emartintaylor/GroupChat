package com.nagesagi.groupchat.MessageHandler;

import com.nagesagi.groupchat.MemberData;

/**
 * Creates an instance of the server
 */
public class MessageServerFactory {

    /**
     * Creates a MessageServer
     * @param room Room to connect to.
     * @param member Data representing this user
     * @param processor Method to call when there is an incoming message.
     * @return Returns a MessageServer
     */
    public static MessageServer GetHandler(String room, MemberData member, MessageProcessor processor){
        return new ScaledroneHandler(member, room, processor);
    }
}
