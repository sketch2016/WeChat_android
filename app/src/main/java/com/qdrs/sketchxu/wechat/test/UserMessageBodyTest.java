package com.qdrs.sketchxu.wechat.test;

import com.qdrs.sketchxu.wechat.data.UserMessageBody;
import com.qdrs.sketchxu.wechat.model.FormatUtil;

public class UserMessageBodyTest {

    String content = "[{\"type\":1,\"content\":\"hello1\"}," +
            "{\"type\":2,\"content\":\"emoji\"}," +
            "{\"type\":1,\"content\":\"hello2\"}," +
            "{\"type\":3,\"content\":\"pic\"}]";

    //UserMessage mUserMessage;

    public UserMessageBodyTest() {
        //mUserMessage = new UserMessage(content);
    }

    public void test() {
        UserMessageBody body = new UserMessageBody(FormatUtil.String2JsonArray(content));
        body.toJsonString();
    }
}
