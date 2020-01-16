package com.nagesagi.groupchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.nagesagi.groupchat.Message.Message;
import com.nagesagi.groupchat.Message.MessageAdapter;
import com.nagesagi.groupchat.MessageHandler.MessageProcessor;
import com.nagesagi.groupchat.MessageHandler.ScaledroneHandler;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements MessageProcessor {

    private EditText editText;
    private ScaledroneHandler handler;
    private MessageAdapter messageAdapter;
    private ListView messagesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        messageAdapter = new MessageAdapter(this);
        messagesView = findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        MemberData data = new MemberData(Randomizer.getRandomName(), Randomizer.getRandomColor());
        handler = new ScaledroneHandler(data, this);
    }

    public void sendMessage(View view) {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            handler.sendMessage(message);
            editText.getText().clear();
        }
    }

    public void processMessage(final Message message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageAdapter.add(message);
                messagesView.setSelection(messagesView.getCount() - 1);
            }
        });
    }
}