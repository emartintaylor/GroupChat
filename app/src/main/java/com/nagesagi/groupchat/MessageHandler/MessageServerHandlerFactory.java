package com.nagesagi.groupchat.MessageHandler;

import com.nagesagi.groupchat.MemberData;

public class MessageServerHandlerFactory {

    public static MessageServerHandler GetHandler(String room, MemberData member, MessageProcessor processor){
        return new ScaledroneHandler(member, room, processor);
    }
}
