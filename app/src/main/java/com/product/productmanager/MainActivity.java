package com.product.productmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.product.productmanager.Model.LogModel;
import com.product.productmanager.Model.takeOrderModel;
import com.product.productmanager.Other.SharedPreferencesHelper;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.InterceptorUtil;
import com.product.productmanager.http.config.HttpConfig;
import com.product.productmanager.http.config.HttpInterface;
import com.product.productmanager.http.config.HttpUtils;
import com.product.productmanager.http.config.URLConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    @BindView(R.id.company_edit)
    EditText companyEdit;
    @BindView(R.id.company_button)
    Button companyButton;
    @BindView(R.id.company_rootView)
    LinearLayout companyRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sharedPreferencesHelper = new SharedPreferencesHelper(MainActivity.this,"save");
        companyEdit.setText(sharedPreferencesHelper.getSharedPreference(COMPANY_NO,"").toString());
        Singleton.instance.setEnterprise(companyEdit.getText().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra("auto",true)){
            autoLogin();
        }
    }
    private void autoLogin(){
        String company = sharedPreferencesHelper.getSharedPreference(COMPANY_NO,"").toString();
        String user = sharedPreferencesHelper.getSharedPreference(USER_NAME, "").toString();
        String pwd = sharedPreferencesHelper.getSharedPreference(PASSWORD, "").toString();
        if (company.length() > 0 && user.length() > 0 && pwd.length() > 0){
            ToolClass.showMessage("自动登录中...",MainActivity.this);
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
            ToolClass.showProgress(MainActivity.this);
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.startPostRequest(URLConfig.login_url, stringBuffer.toString(), new HttpInterface() {

                @Override
                public void onResponse(String s) {
                    ToolClass.progressDismisss();
                    Gson gson = new Gson();
                    LogModel logModel = gson.fromJson(s, LogModel.class);
                    if (logModel.isSuccess()) {
                        ToolClass.showMessage("登录成功", MainActivity.this);
                        Singleton.instance.setUserModel(logModel.getUserModel());
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        ToolClass.showMessage(logModel.getMessage(), Singleton.instance.getContext());
                    }
                }
            });
        }
    }

    @OnClick({R.id.company_button, R.id.company_rootView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.company_button:
                if (companyEdit.getText().toString().length() == 0){
                    ToolClass.showMessage("请输入企业码",MainActivity.this);
                    return;
                }
                sharedPreferencesHelper.put(COMPANY_NO,companyEdit.getText().toString());
                Singleton.instance.setEnterprise(companyEdit.getText().toString());
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.company_rootView:
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                break;
                default:
                    break;
        }
    }
}
