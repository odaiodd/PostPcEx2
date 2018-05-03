package com.example.odai.selfChat;


import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class msgFragment extends Fragment {

    public static final String KEY_MESSAGE_CONTENT = "content";
    public static final String KEY_MESSAGE_TIME_STAMP = "timeStamp";
    public static final String KEY_MESSAGE_NAME = "name";

    private Button DeleteButt;
    MessageDeletedListener listen;
    Message message;

    TextView title,timestamp,content;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.msg_fragment, container, false);
        title = v.findViewById(R.id.textAuther);
        timestamp = v.findViewById(R.id.textTimeStamp);
        content = v.findViewById(R.id.textViewMessageContent);
        updateMessage();
        ButterKnife.bind(this, v);
        CharSequence text1 = message.getContent();
        content.setText(text1);
        DeleteButt = v.findViewById(R.id.buttonDelete);
        title.setText(message.getName());
        CharSequence text = DateUtils.getRelativeTimeSpanString(message.getTimestamp(), System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL);
        timestamp.setText(text);


        DeleteButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listen.onMessageDeleted(message);
            }
        });
        return v;
    }
    private void updateMessage() {
        if (getArguments() != null) {
            String var1 = getArguments().get(KEY_MESSAGE_NAME).toString();
            String var2 = getArguments().get(KEY_MESSAGE_CONTENT).toString();
            Long var3 = (long)getArguments().get(KEY_MESSAGE_TIME_STAMP);
            message = new Message(var1,var2,var3);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MessageDeletedListener) {
            this.listen = (MessageDeletedListener) context;
        }
    }


    public static msgFragment newInstance(Message message) {

        Bundle args = new Bundle();
        args.putString("content", message.getContent());
        args.putLong("timeStamp", message.getTimestamp());
        args.putString("name", message.getName());

        msgFragment fragment = new msgFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface MessageDeletedListener {
        void onMessageDeleted(Message msg);
    }
}
