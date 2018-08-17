package com.qdrs.sketchxu.wechat.data;

public class UserInfo {

    private String userName;
    private String password;
    private String nickName;
    private String introduction;

    public UserInfo() {

    }

    public UserInfo(String userName, String password, String nickName, String introduction) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.introduction = introduction;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
