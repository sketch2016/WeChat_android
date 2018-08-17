package com.qdrs.sketchxu.wechat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.qdrs.sketchxu.wechat.R;
import com.qdrs.sketchxu.wechat.data.UserMessage;
import service.ChatService;

public class ChatActivity extends AppCompatActivity {

    private ImageView sendView;
    private EditText edtMessage;

    private ChatService mChatService;

    private String chatWith;

    public static final int EVENT_SEND_MESSAGE = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch(msg.what) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getChatWithInfo(getIntent());

        edtMessage = (EditText) findViewById(R.id.edit_msg);
        sendView = (ImageView) findViewById(R.id.send);
        sendView.setOnClickListener(mOnClickListener);
    }

    private void getChatWithInfo(Intent intent) {
        chatWith = intent.getStringExtra("chat_with");
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.send:
                    sendMessage();
                    break;
                case R.id.emoji:
                    break;
                default:
                    break;
            }
        }
    };

    private void sendMessage() {
        UserMessage mUserMessage = getUserMessage();
        if (mUserMessage == null) {
            Toast.makeText(this, "信息错误", Toast.LENGTH_LONG).show();
            return;
        }
        Message message = mHandler.obtainMessage(EVENT_SEND_MESSAGE);
        mChatService.getInstance().send(mUserMessage, message);
    }

    private UserMessage getUserMessage() {
        String body = edtMessage.getText().toString();
        UserMessage um = new UserMessage(body, chatWith);
        um.setSendTime(System.currentTimeMillis());

        return um;
    }
}
