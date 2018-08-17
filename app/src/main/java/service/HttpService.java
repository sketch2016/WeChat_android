package service;

import android.os.Message;
import com.google.gson.GsonBuilder;
import com.qdrs.sketchxu.wechat.Config;
import com.qdrs.sketchxu.wechat.data.UserInfo;
import com.qdrs.sketchxu.wechat.model.HttpUtil;

public class HttpService {

    private static HttpService mInstance;

    public static synchronized  HttpService getInstance() {
        if(mInstance == null) {
            mInstance = new HttpService();
        }

        return mInstance;
    }

    private HttpService() {
        //TODO
    }


    public void registUser(final UserInfo userInfo,final Message msg) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                GsonBuilder gb = new GsonBuilder();
                String jsonUserInfo = gb.create().toJson(userInfo);
                int result = HttpUtil.put(Config.HOST + Config.REGISTE, jsonUserInfo);
                msg.arg1 = result;
                msg.sendToTarget();
            }
        });

        t.start();
    }

    public void login(final UserInfo userInfo, final Message msg) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                GsonBuilder gb = new GsonBuilder();
                String jsonUserInfo = gb.create().toJson(userInfo);
                int result = HttpUtil.post(Config.HOST + Config.LOGIN, jsonUserInfo);
                msg.arg1 = result;
                msg.sendToTarget();
            }
        });

        t.start();
    }

}
