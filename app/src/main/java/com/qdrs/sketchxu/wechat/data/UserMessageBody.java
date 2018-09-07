package com.qdrs.sketchxu.wechat.data;

import com.google.gson.JsonArray;
import com.qdrs.sketchxu.wechat.model.FormatUtil;

import java.util.ArrayList;
import java.util.List;

public class UserMessageBody {

    private List<MessageContent> messages = new ArrayList<>();
    private String jsonString;

    public UserMessageBody() {

    }

    public UserMessageBody(JsonArray jsonArray) {
        jsonString = jsonArray.toString();
        messages = FormatUtil.jsonArrayString2MessageContentList(jsonString);
        android.util.Log.d("UserMessageBody", "messages.size=" + messages.size());
    }

    public String toJsonString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("[");
//        for (MessageContent content : messages) {
//            String cont = "{\"type\":" + content.getType() + ",\"content\":" + content.getContent() + "}";
//            if (content != messages.get(messages.size() - 1)) cont += ",";
//            sb.append(cont);
//        }
//        sb.append("]");
//        android.util.Log.d("UserMessageBody", sb.toString());
        return jsonString;
    }

    public static class MessageContent {
        public static final int TYPE_STRING = 1;
        public static final int TYPE_EMOJI = 2;
        public static final int TYPE_PIC = 3;
        public static final int TYPE_FILE = 4;
        int type;

        String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getStringType() {
            String result = "";
            switch(type) {
                case TYPE_STRING:
                    result = "string";
                    break;
                case TYPE_EMOJI:
                    result = "emoji";
                    break;
                case TYPE_PIC:
                    result = "pic";
                    break;
                case TYPE_FILE:
                    result = "file";
                    break;
                default:
                    break;
            }
            return result;
        }
    }

}
