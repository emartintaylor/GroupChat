package com.nagesagi.groupchat.Message;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nagesagi.groupchat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to convert messages to a view
 */
public class MessageAdapter extends BaseAdapter {

    List<Message> messages = new ArrayList<Message>();
    Context context;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    /**
     * Add a message to the list.
     * @param message Message to add
     */
    public void add(Message message) {
        this.messages.add(message);
        notifyDataSetChanged(); // to render the list we need to notify
    }

    /**
     * Get the count of messages that have been added.
     * @return Int
     */
    @Override
    public int getCount() {
        return messages.size();
    }

    /**
     * Get a specific message.
     * @param i Index of the item to get
     * @return Message
     */
    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    /**
     * Get ID.
     * @param i ID
     * @return Int
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * This is the backbone of the class, it handles the creation of single ListView row (chat bubble)
     * @param i
     * @param convertView
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = messages.get(i);

        // this message was sent by us so let's create a basic chat bubble on the right
        if (message.isBelongsToCurrentUser()) {
            convertView = messageInflater.inflate(R.layout.my_message, null);
            holder.messageBody = convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getText());
        } // this message was sent by someone else so let's create an advanced chat bubble on the left
        else
        {
            convertView = messageInflater.inflate(R.layout.their_message, null);
            holder.avatar = convertView.findViewById(R.id.avatar);
            holder.name = convertView.findViewById(R.id.name);
            holder.messageBody = convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);

            holder.name.setText(message.getMemberData().getName());
            holder.messageBody.setText(message.getText());
            GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
            drawable.setColor(Color.parseColor(message.getMemberData().getColor()));
        }

        return convertView;
    }
}