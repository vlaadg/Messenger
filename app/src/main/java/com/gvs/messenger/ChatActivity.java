package com.gvs.messenger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import Model.Messages;

public class ChatActivity extends AppCompatActivity {
    private FirebaseListAdapter<Messages> adapter;
    public FloatingActionButton sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        displayAllMessages();
        sendButton = findViewById(R.id.send_btn);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textField = findViewById(R.id.message_field);
                if(textField.getText().toString() =="")
                    return;

                FirebaseDatabase.getInstance().getReference().push().setValue(
                        new Messages(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                textField.getText().toString()
                        )
                );
                textField.setText("");
            }
        });
    }

    private void displayAllMessages() {
        ListView messages = findViewById(R.id.list_of_messages);
        adapter = new FirebaseListAdapter<Messages>(this, Messages.class, R.layout.messages_list, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, Messages model, int position) {
                TextView message_user, message_time, message_text;
                message_user = v.findViewById(R.id.message_user);
                message_time = v.findViewById(R.id.message_time);
                message_text = v.findViewById(R.id.message_text);

                message_user.setText(model.getUsername());
                message_text.setText(model.getTextMessage());
                message_time.setText(DateFormat.format("dd-mm-yyyy HH:mm:ss", model.getMessageDate()));

            }
        };
        messages.setAdapter(adapter);
    }
}