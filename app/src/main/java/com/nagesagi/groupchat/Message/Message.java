package com.nagesagi.groupchat.Message;

import com.nagesagi.groupchat.MemberData;

/**
 * Message that is processed.
 */
public class Message{
    private String text; // message body
    private MemberData memberData; // data of the user that sent this message
    private boolean belongsToCurrentUser; // is this message sent by us?

    public Message(String text, MemberData memberData, boolean belongsToCurrentUser) {
        this.text = text;
        this.memberData = memberData;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    /**
     * Get the text of the message.
     * @return String
     */
    public String getText() {
        return text;
    }

    /**
     * Get information regarding the user.
     * @return MemberData
     */
    public MemberData getMemberData() {
        return memberData;
    }

    /**
     * Is the message from the current user.
     * @return Bool
     */
    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }
}
