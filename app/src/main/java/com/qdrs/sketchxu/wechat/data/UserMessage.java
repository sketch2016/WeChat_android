package com.qdrs.sketchxu.wechat.data;

public class UserMessage {

    private long sendTime;

    private long receiveTime;

    private String fromUser;

    private String toUser;

    private String body = "message:";


    public UserMessage(String msg) {
        this.body = msg;
    }

    public UserMessage(String msg, String toUser) {
        this.body = msg;
        this.toUser = toUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFromUser() {
        return fromUser;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(long receiveTime) {
        this.receiveTime = receiveTime;
    }

}
