package com.nagesagi.groupchat.MessageHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagesagi.groupchat.MemberData;
import com.nagesagi.groupchat.Message.Message;
import com.nagesagi.groupchat.Randomizer;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;

/**
 * Communicates with the Scaledrone server
 */
public class ScaledroneHandler implements RoomListener, MessageServer {

    private Scaledrone scaledrone;
    private String roomName;

    // replace this with a real channelID from Scaledrone dashboard
    private String channelID = "cwW2E5EpnsW1lNCJ";

    private MessageProcessor processor;

    /**
     * Creates the ScaledroneHandler
     * @param data
     * @param room
     * @param processor
     */
    public ScaledroneHandler(MemberData data, String room, MessageProcessor processor){
        this.processor = processor;
        scaledrone = new Scaledrone(channelID, data);
        roomName = room;

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

    public void registerOnMessageRecieved(MessageProcessor processor){
        this.processor = processor;
    }
    public void sendMessage(String message){
        scaledrone.publish(roomName, message);
    }

    @Override
    public void onOpen(Room room) {
        System.out.println("Connected to room");
    }

    @Override
    public void onOpenFailure(Room room, Exception ex) {
        System.err.println(ex);
    }

    @Override
    public void onMessage(Room room, com.scaledrone.lib.Message receivedMessage) {
                try {

            MemberData data = new MemberData(Randomizer.getRandomName(), Randomizer.getRandomColor());
            if(receivedMessage.getMember() != null) {
                final ObjectMapper mapper = new ObjectMapper();
                data = mapper.treeToValue(receivedMessage.getMember().getClientData(), MemberData.class);
            }

            boolean belongsToCurrentUser = false;
            if(receivedMessage.getClientID() != null)
                belongsToCurrentUser = receivedMessage.getClientID().equals(scaledrone.getClientID());

            String text = "Didn't work";
            if(receivedMessage.getData() != null)
                text = receivedMessage.getData().asText();

            final Message message = new Message(text, data, belongsToCurrentUser);
            processor.processMessage(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
