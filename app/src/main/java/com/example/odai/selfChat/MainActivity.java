package com.example.odai.selfChat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements msgAdapter.OnClickListener, msgFragment.MessageDeletedListener {
    private static final String TAG = "MainActivity";
    private static final String MESSAGES = "messages";



    Button sendButt;
    RecyclerView recycView;
    EditText userNameData ,messageContent;
    msgAdapter myAdapter;



    @Override
    public void onSaveInstanceState(Bundle outState) {
        ArrayList<String> msgs;
        msgs = new ArrayList<String>(myAdapter.data.size());
        for (Message msg : myAdapter.data) {
            msgs.add(msg.toString());

        }
        outState.putStringArrayList(MESSAGES, msgs);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycView = findViewById(R.id.list);

        ArrayList<Message> dataInput = getInput(savedInstanceState);
        myAdapter = new msgAdapter(this,dataInput);

        recycView.setAdapter(myAdapter);
        recycView.setHasFixedSize(true);
        recycView.setLayoutManager(new LinearLayoutManager(this));

        sendButt = (Button) findViewById(R.id.send);
        userNameData = (EditText) findViewById(R.id.userData);
        messageContent = (EditText) findViewById(R.id.messageData);


        sendButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(messageContent.getText()) || TextUtils.isEmpty(userNameData.getText())) {
                    Snackbar.make(messageContent, "you have to put a message and a user here please!", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                Message insertedMessage = new Message(userNameData.getText().toString(), messageContent.getText().toString(), System.currentTimeMillis());
                myAdapter.addMessage(insertedMessage);
                userNameData.setText("");
                messageContent.setText("");


            }
        });
    }


    private ArrayList<Message> getInput(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return new ArrayList<>();
        }
        if (savedInstanceState.getStringArrayList(MESSAGES) == null) {
            return new ArrayList<>();
        }
        ArrayList<Message> output = new ArrayList<>();
        for (String singleMessage : Objects.requireNonNull(savedInstanceState.getStringArrayList(MESSAGES))) {
            output.add(new Message(userNameData.getText().toString(), userNameData.getText().toString(), System.currentTimeMillis()));

        }
        return output;
    }


    @Override
    public void onClick(Message message) {
        msgFragment frag = msgFragment.newInstance(message);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.frame,frag).addToBackStack(null)
                .commit();
    }

    @Override
    public void onMessageDeleted(Message msg) {
        myAdapter.removeItem(msg);
        getSupportFragmentManager().popBackStack();
    }

}

