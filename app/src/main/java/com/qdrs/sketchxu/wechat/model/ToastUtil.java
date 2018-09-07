package com.qdrs.sketchxu.wechat.model;

import android.widget.Toast;
import com.qdrs.sketchxu.wechat.WeChatApplication;

public class ToastUtil {

    public static void alertMessage(String msg) {
        alertMessage(msg, Toast.LENGTH_SHORT);
    }

    public static void alertMessage(String msg, int time) {
        Toast.makeText(WeChatApplication.getContext(), msg, time).show();
    }
}
