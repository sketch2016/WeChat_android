package com.qdrs.sketchxu.wechat.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.qdrs.sketchxu.wechat.WeChatApplication;
import com.qdrs.sketchxu.wechat.data.NetworkStatus;

public class NetworkUtil {

    public static NetworkStatus getNetworkStats() {
        ConnectivityManager connectivityManager = (ConnectivityManager)WeChatApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return NetworkStatus.NETWORK_MOBILE;
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return NetworkStatus.NETWORK_WIFI;
            }
        }

        return NetworkStatus.NETWORK_NONE;
    }


}
