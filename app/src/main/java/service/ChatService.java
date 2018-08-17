package service;

import android.os.Message;
import com.google.gson.GsonBuilder;
import com.qdrs.sketchxu.wechat.data.UserMessage;

public class ChatService {

    private static ChatService mInstance;

    private ChatService() {

    }

    public static synchronized ChatService getInstance() {
        if (mInstance == null) {
            mInstance = new ChatService();
        }

        return mInstance;
    }

    public void send(UserMessage mUserMessage, Message handlerMessage) {
        GsonBuilder gb = new GsonBuilder();
        String mUsermessage = gb.create().toJson(mUserMessage);

    }
}
