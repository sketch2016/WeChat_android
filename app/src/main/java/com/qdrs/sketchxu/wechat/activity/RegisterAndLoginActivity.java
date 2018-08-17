package com.qdrs.sketchxu.wechat.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.qdrs.sketchxu.wechat.Config;
import com.qdrs.sketchxu.wechat.R;
import com.qdrs.sketchxu.wechat.WeChatApplication;
import com.qdrs.sketchxu.wechat.controller.RegisteAndLoginController;
import com.qdrs.sketchxu.wechat.data.HttpResponse;
import com.qdrs.sketchxu.wechat.data.UserInfo;
import service.HttpService;

//com.google.gson

public class RegisterAndLoginActivity extends AppCompatActivity implements RegisteAndLoginController.registerCallback{

    private LinearLayout mRegisterContent;
    private LinearLayout mLoginContent;
    private TextView mRegisterTitle;
    private TextView mLoginTitle;
    private Button mLoginBt;
    private Button mRegisterBt;
    //RecyclerView recyclerView;

    private Drawable mTitleBackgroud;
    //private RegisteAndLoginController mRegisteAndLoginController;

    private HttpService mHttp;

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.registe_title:
                    mRegisterTitle.setBackground(mTitleBackgroud);
                    mLoginTitle.setBackground(null);
                    mRegisterContent.setVisibility(View.VISIBLE);
                    mLoginContent.setVisibility(View.GONE);
                    break;
                case R.id.login_title:
                    mLoginTitle.setBackground(mTitleBackgroud);
                    mRegisterTitle.setBackground(null);
                    mLoginContent.setVisibility(View.VISIBLE);
                    mRegisterContent.setVisibility(View.GONE);
                    break;
                case R.id.bt_registe:
                    register();
                    break;
                case R.id.bt_login:
                    login();
                    break;
                default:
                    break;
            }
        }
    };

    /*


     */

    //event
    private static final int EVENT_REGIST_COMPETE = 0;
    private static final int EVENT_LOGIN_COMPETE = 1;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case EVENT_REGIST_COMPETE:
                    alertMessage("注册结果:");
                    apply(msg.arg1, TYPE_REGISTER);
                    break;
                case EVENT_LOGIN_COMPETE:
                    alertMessage("登陆结果:");
                    apply(msg.arg1, TYPE_LOGIN);
                    break;
                default :
                    break;

            }
            super.handleMessage(msg);
        }
    };

    private Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        mContext = WeChatApplication.getContext();

        mHttp = HttpService.getInstance();

        mTitleBackgroud = getDrawable(R.color.login_title_bg);

        mRegisterTitle = findViewById(R.id.registe_title);
        mLoginTitle = findViewById(R.id.login_title);
        mRegisterContent = findViewById(R.id.registe_content);
        mLoginContent = findViewById(R.id.login_content);

        mRegisterBt = mRegisterContent.findViewById(R.id.bt_registe);
        mLoginBt = mLoginContent.findViewById(R.id.bt_login);

        mRegisterTitle.setOnClickListener(mListener);
        mLoginTitle.setOnClickListener(mListener);
        mRegisterBt.setOnClickListener(mListener);
        mLoginBt.setOnClickListener(mListener);

        //mRegisteAndLoginController = new RegisteAndLoginController(WeChatApplication.getContext());
    }

    private static final int TYPE_REGISTER = 1;
    private static final int TYPE_LOGIN = 2;

    private UserInfo getUserInfo(int type) {
        if (type == TYPE_REGISTER) {
            EditText userName = (EditText) mRegisterContent.findViewById(R.id.registe_user_name);
            EditText password = (EditText) mRegisterContent.findViewById(R.id.registe_password);
            EditText passwordConfirm = (EditText) mRegisterContent.findViewById(R.id.registe_password_confirm);
            EditText nickName = (EditText) mRegisterContent.findViewById(R.id.registe_nick_name);
            EditText introduction = (EditText) mRegisterContent.findViewById(R.id.registe_intro);
            if (TextUtils.isEmpty(userName.getText().toString())) {
                alertMessage("用户名不能为空");
                return null;
            }
            if (TextUtils.isEmpty(password.getText().toString())
                    || TextUtils.isEmpty(passwordConfirm.getText().toString())) {
                alertMessage("密码不能为空");
                return null;
            }
            if (!password.getText().toString().equals(passwordConfirm.getText().toString())) {
                alertMessage("两次输入的密码不一致");
                return null;
            }

            UserInfo userInfo = new UserInfo(userName.getText().toString(), password.getText().toString()
                    ,nickName.getText().toString(), introduction.getText().toString());
            return userInfo;
        }
        if (type == TYPE_LOGIN) {
            EditText userName = (EditText) mLoginContent.findViewById(R.id.login_user_name);
            EditText password = (EditText) mLoginContent.findViewById(R.id.login_password);
            if (TextUtils.isEmpty(userName.getText().toString())) {
                alertMessage("用户名不能为空");
                return null;
            }
            if (TextUtils.isEmpty(password.getText().toString())
                    || TextUtils.isEmpty(password.getText().toString())) {
                alertMessage("密码不能为空");
                return null;
            }
            UserInfo userInfo = new UserInfo(userName.getText().toString(), password.getText().toString(),
                    null, null);
            return userInfo;
        }
        return null;

    }

    private void register() {
        UserInfo userInfo = getUserInfo(TYPE_REGISTER);
        if (userInfo == null) {
            return;
        }

        //GsonBuilder gb = new GsonBuilder();
        //String jsonUserInfo = gb.create().toJson(userInfo);
        //mRegisteAndLoginController.register(jsonUserInfo, this);
        Message msg = mHandler.obtainMessage(EVENT_REGIST_COMPETE);
        mHttp.registUser(userInfo, msg);
    }

    private void login() {
        UserInfo userInfo = getUserInfo(TYPE_LOGIN);
        if (userInfo == null) {
            return;
        }

        Message msg = mHandler.obtainMessage(EVENT_LOGIN_COMPETE);
        mHttp.login(userInfo, msg);
    }

    private void apply(int result, int type) {
        if (result == Config.SUCCESS) {
            Intent loginIntent = new Intent(RegisterAndLoginActivity.this, MainActivity.class);
            startActivity(loginIntent);
        } else {
            HttpResponse r = HttpResponse.getHttpResponse(result);
            switch (r) {
                case PARA_ERROR:
                    alertMessage("用户名不能为空");
                    break;
                case LOGIN_PASSWORD_ERROR:
                    alertMessage("密码错误");
                    break;
                case LOGIN_INTERNAL_ERROR:
                    alertMessage("登陆错误");
                    break;
                case LOGIN_USERNAME_ERROR:
                    alertMessage("用户名错误");
                    break;
                case REGISTER_USER_EXIST:
                    alertMessage("用户已存在");
                    break;
                case REGISTER_INTERNAL_ERROR:
                    alertMessage("注册失败");
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void onRegisterResult(int registerResult) {
        alertMessage("registerResult");
    }

    public void alertMessage(String msg) {
        alertMessage(msg, Toast.LENGTH_LONG);
    }

    public void alertMessage(String msg, int time) {
        Toast.makeText(mContext, msg, time).show();
    }
}
