package com.qdrs.sketchxu.wechat.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.qdrs.sketchxu.wechat.Config;
import com.qdrs.sketchxu.wechat.model.HttpUtil;

public class RegisteAndLoginController {
    private Context mContext;

    public RegisteAndLoginController(Context context) {
        this.mContext = context;
    }

    public void registe() {

    }

    public void login() {

    }


    public void register(final String jsonUserInfo, final registerCallback cb) {
        new AsyncTask<Void, Void, Integer>(){
            @Override
            protected Integer doInBackground(Void... voids) {
                int registResult = HttpUtil.put(Config.HOST + Config.REGISTE, jsonUserInfo);
                return registResult;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Integer registerResult) {
                super.onPostExecute(registerResult);
                cb.onRegisterResult(registerResult);
                Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }.execute();
        //int registResult = HttpUtil.put(Config.HOST + Config.REGISTE,jsonUserInfo);
    }


    public interface registerCallback {
        void onRegisterResult(int registerResult);
    }

    //interface
}
