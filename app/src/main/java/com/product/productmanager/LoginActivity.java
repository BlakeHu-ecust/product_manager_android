package com.product.productmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.product.productmanager.Model.LogModel;
import com.product.productmanager.Other.SharedPreferencesHelper;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.config.HttpConfig;
import com.product.productmanager.http.config.HttpInterface;
import com.product.productmanager.http.config.HttpUtils;
import com.product.productmanager.http.config.URLConfig;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_account)
    EditText loginAccount;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.login_rootView)
    LinearLayout loginRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedPreferencesHelper = new SharedPreferencesHelper(LoginActivity.this, "save");
        loginAccount.setText(sharedPreferencesHelper.getSharedPreference(USER_NAME, "").toString());
        loginPwd.setText(sharedPreferencesHelper.getSharedPreference(PASSWORD, "").toString());
    }

    @OnClick({R.id.login_button, R.id.login_rootView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                if (loginAccount.getText().toString().length() == 0) {
                    ToolClass.showMessage("请输入账号", LoginActivity.this);
                    return;
                }
                if (loginPwd.getText().toString().length() == 0) {
                    ToolClass.showMessage("请输入密码", LoginActivity.this);
                    return;
                }
                sharedPreferencesHelper.put(USER_NAME, loginAccount.getText().toString());
                sharedPreferencesHelper.put(PASSWORD, loginPwd.getText().toString());

                ToolClass.showProgress(LoginActivity.this);

//                RetrofitFactory.getInstence()
//                        .API()
//                        .login(loginAccount.getText().toString(),loginPwd.getText().toString(),String.valueOf(System.currentTimeMillis()))
//                        .compose(this.<BaseEntity<UserModel>>setThread())
//                        .subscribe(new BaseObserver<UserModel>() {
//                            @Override
//                            protected void onSuccees(BaseEntity<UserModel> t) throws Exception {
//                                Singleton.instance.setUserModel(t.getObject());
//                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
//                                startActivity(intent);
//                                finish();
//                                ToolClass.progressDismisss();
//                            }
//                        });
                HashMap<String, Object> map = new HashMap();
                map.put("userName", loginAccount.getText().toString());
                map.put("password", loginPwd.getText().toString());
                map.put("timestamp", String.valueOf(System.currentTimeMillis()));

//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("userName",loginAccount.getText().toString());
//                jsonObject.addProperty("password",loginPwd.getText().toString());
//                jsonObject.addProperty("timestamp",String.valueOf(System.currentTimeMillis()));

                StringBuffer stringBuffer = new StringBuffer();
                if (map != null && map.keySet().size() > 0) {
                    for (String key : map.keySet()) {
                        stringBuffer.append(key + "=" + map.get(key) + "&");
                    }
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                }
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.startPostRequest(URLConfig.login_url, stringBuffer.toString(), new HttpInterface() {

                    @Override
                    public void onResponse(String s) {
                        ToolClass.progressDismisss();
                        Gson gson = new Gson();
                        LogModel logModel = gson.fromJson(s, LogModel.class);
                        if (logModel.isSuccess()) {
                            ToolClass.showMessage("登录成功", LoginActivity.this);
                            Singleton.instance.setUserModel(logModel.getUserModel());
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            ToolClass.showMessage(logModel.getMessage(), Singleton.instance.getContext());
                        }
                    }
                });
                break;
            case R.id.login_rootView:
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), 0);
                break;
            default:
                break;
        }
    }
}
