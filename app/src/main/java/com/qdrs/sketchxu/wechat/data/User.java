package com.qdrs.sketchxu.wechat.data;

import android.widget.ImageView;

public class User {

    private String name;

    private String introduction;

    private ImageView pic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public ImageView getPic() {
        return pic;
    }

    public void setPic(ImageView pic) {
        this.pic = pic;
    }
}
