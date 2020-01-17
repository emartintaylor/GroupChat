package com.nagesagi.groupchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.nagesagi.groupchat.Message.Message;
import com.nagesagi.groupchat.Message.MessageAdapter;
import com.nagesagi.groupchat.MessageHandler.MessageProcessor;
import com.nagesagi.groupchat.MessageHandler.MessageServer;
import com.nagesagi.groupchat.MessageHandler.MessageServerFactory;

public class MainActivity extends AppCompatActivity implements MessageProcessor {

    private EditText editText;
    private MessageServer handler;
    private MessageAdapter messageAdapter;
    private ListView messagesView;
    private String roomName = "testchat";

    /**
     * Sets up the Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        messageAdapter = new MessageAdapter(this);
        messagesView = findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        MemberData data = new MemberData(Randomizer.getRandomName(), Randomizer.getRandomColor());
        handler = MessageServerFactory.GetHandler(roomName, data, this);
    }

    /**
     * Sends a message to the server and clears the EditText control.
     * @param view View that we are sending the message
     */
    public void sendMessage(View view) {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            handler.sendMessage(message);
            editText.getText().clear();
        }
    }

    /**
     * Handles a message coming from the server
     * @param message Message from the server
     */
    public void processMessage(final Message message){

        // Run it on the UI thread to ensure that
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageAdapter.add(message);
                messagesView.setSelection(messagesView.getCount() - 1);
            }
        });
    }
}