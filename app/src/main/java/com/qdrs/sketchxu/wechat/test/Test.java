package com.qdrs.sketchxu.wechat.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import com.qdrs.sketchxu.wechat.activity.MainActivity;

public class Test {

    private Context mContext;

    public Test(Context context) {
        this.mContext = context;
    }

    @SuppressLint("NewApi")
    public void test() {
        UserMessageBodyTest umbTest = new UserMessageBodyTest();
        umbTest.test();
        ACacheTest aCacheTest = new ACacheTest(mContext);
        aCacheTest.test();
    }

    public static void switchToMain(Context context) {
        Intent loginIntent = new Intent(context, MainActivity.class);
        context.startActivity(loginIntent);
    }
}
