package com.nagesagi.groupchat.MessageHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagesagi.groupchat.MainActivity;
import com.nagesagi.groupchat.MemberData;
import com.nagesagi.groupchat.Message.Message;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;

public class ScaledroneHandler implements RoomListener {

    private Scaledrone scaledrone;

    // replace this with a real channelID from Scaledrone dashboard
    private String channelID = "cwW2E5EpnsW1lNCJ";
    private String roomName = "observable-room";
    private MessageProcessor processor;

    public ScaledroneHandler(MemberData data, MessageProcessor processor){
        this.processor = processor;
        scaledrone = new Scaledrone(channelID, data);

        scaledrone.connect(new Listener() {
            @Override
            public void onOpen() {
                System.out.println("Scaledrone connection open");
                scaledrone.subscribe(roomName, ScaledroneHandler.this);
            }

            @Override
            public void onOpenFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onClosed(String reason) {
                System.err.println(reason);
            }
        });
    }


    public void sendMessage(String message){
        scaledrone.publish(roomName, message);
    }


    @Override
    public void onOpen(Room room) {
        System.out.println("Conneted to room");
    }

    @Override
    public void onOpenFailure(Room room, Exception ex) {
        System.err.println(ex);
    }

    @Override
    public void onMessage(Room room, com.scaledrone.lib.Message receivedMessage) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final MemberData data = mapper.treeToValue(receivedMessage.getMember().getClientData(), MemberData.class);
            boolean belongsToCurrentUser = receivedMessage.getClientID().equals(scaledrone.getClientID());
            final Message message = new Message(receivedMessage.getData().asText(), data, belongsToCurrentUser);
            processor.processMessage(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
