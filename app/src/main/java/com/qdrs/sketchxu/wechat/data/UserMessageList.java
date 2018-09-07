package com.qdrs.sketchxu.wechat.data;

import android.util.Log;
import com.qdrs.sketchxu.wechat.Config;

import java.util.ArrayList;
import java.util.Collection;

public class UserMessageList extends ArrayList<UserMessage> {

    public static final String TAG = "UserMessageList";

    private ArrayList<String> urlList = new ArrayList<>();

    @Override
    public boolean add(UserMessage mUserMessage) {
        checkUrl(mUserMessage);

        return super.add(mUserMessage);
    }

    @Override
    public boolean addAll(Collection c) {
        for (Object obj : c) {
            if (obj instanceof UserMessage) {
                UserMessage mUserMessage = (UserMessage) obj;
                checkUrl(mUserMessage);
            }
        }

        return super.addAll(c);
    }

    public ArrayList<String> getUrlList() {
        return urlList;
    }

    private void checkUrl(UserMessage mUserMessage) {
        if (mUserMessage.getStringBody().equals(Config.MESSAGE_PIC)) {
            String key = null;
            if (mUserMessage.getType() == UserMessage.TYPE_SEND) {
                key = mUserMessage.getKey() + Config.MESSAGE_PIC_KEY_SMALL;
            } else {
                key = mUserMessage.getUrl();
            }

            Log.d(TAG, "add to url list:" + key);
            if (!urlList.contains(key)) {
                urlList.add(key);
            }
        }
    }
}
