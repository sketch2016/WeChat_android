package com.qdrs.sketchxu.wechat;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class WeChatApplication extends Application {

    public static Context mContext;

    private SharedPreferences pref;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
