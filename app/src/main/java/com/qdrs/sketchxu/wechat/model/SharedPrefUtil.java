package com.qdrs.sketchxu.wechat.model;

import android.content.Context;
import android.content.SharedPreferences;
import com.qdrs.sketchxu.wechat.WeChatApplication;

public class SharedPrefUtil {

    private static SharedPrefUtil mInstance;

    private SharedPreferences sp;

    private SharedPrefUtil() {
        sp = WeChatApplication.getContext().getSharedPreferences("wechat", Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefUtil get() {
        if (mInstance == null) {
            mInstance = new SharedPrefUtil();
        }

        return mInstance;
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }


}
