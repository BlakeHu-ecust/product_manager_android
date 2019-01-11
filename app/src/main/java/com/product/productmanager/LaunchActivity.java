package com.product.productmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.product.productmanager.Model.LogModel;
import com.product.productmanager.Other.SharedPreferencesHelper;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.config.HttpInterface;
import com.product.productmanager.http.config.HttpUtils;
import com.product.productmanager.http.config.URLConfig;

import java.util.HashMap;

public class LaunchActivity extends BaseActivity {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            autoLogin();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        sharedPreferencesHelper = new SharedPreferencesHelper(LaunchActivity.this,"save");
        Singleton.instance.setEnterprise(sharedPreferencesHelper.getSharedPreference(COMPANY_NO,"").toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(0,1500);
            }
        }).start();
    }

    private void autoLogin(){
        String company = sharedPreferencesHelper.getSharedPreference(COMPANY_NO,"").toString();
        String user = sharedPreferencesHelper.getSharedPreference(USER_NAME, "").toString();
        String pwd = sharedPreferencesHelper.getSharedPreference(PASSWORD, "").toString();
        if (company.length() > 0 && user.length() > 0 && pwd.length() > 0){
            ToolClass.showMessage("自动登录中...",LaunchActivity.this);
            HashMap<String, Object> map = new HashMap();
            map.put("userName", user);
            map.put("password", pwd);
            map.put("timestamp", String.valueOf(System.currentTimeMillis()));
            StringBuffer stringBuffer = new StringBuffer();
            if (map != null && map.keySet().size() > 0) {
                for (String key : map.keySet()) {
                    stringBuffer.append(key + "=" + map.get(key) + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
            ToolClass.showProgress(LaunchActivity.this);
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.startPostRequest(URLConfig.login_url, stringBuffer.toString(), new HttpInterface() {

                @Override
                public void onResponse(String s) {
                    ToolClass.progressDismisss();
                    Gson gson = new Gson();
                    LogModel logModel = gson.fromJson(s, LogModel.class);
                    if (logModel.isSuccess()) {
                        //ToolClass.showMessage("登录成功", LaunchActivity.this);
                        Singleton.instance.setUserModel(logModel.getUserModel());
                        Intent intent = new Intent(LaunchActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        ToolClass.showMessage(logModel.getMessage(), Singleton.instance.getContext());
                        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
        else{
            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
