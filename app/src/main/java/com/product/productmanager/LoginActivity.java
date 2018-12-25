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

import com.product.productmanager.Model.UserModel;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.RetrofitFactory;
import com.product.productmanager.http.base.BaseObserver;
import com.product.productmanager.http.bean.BaseEntity;

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
    }

    @OnClick({R.id.login_button, R.id.login_rootView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
//                if (loginAccount.getText().toString().length() == 0){
//                    ToolClass.showMessage("请输入用户名",LoginActivity.this);
//                    return;
//                }
//                if (loginPwd.getText().toString().length() == 0){
//                    ToolClass.showMessage("请输入密码",LoginActivity.this);
//                    return;
//                }
                RetrofitFactory.getInstence()
                        .API()
                        .login("15322222222","123456",String.valueOf(System.currentTimeMillis()))
                        .compose(this.<BaseEntity<UserModel>>setThread())
                        .subscribe(new BaseObserver<UserModel>() {
                            @Override
                            protected void onSuccees(BaseEntity<UserModel> t) throws Exception {
                                String name = t.getObject().getUserName();
                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            protected void onCodeError(BaseEntity<UserModel> t) throws Exception {
                                ToolClass.showMessage(t.getMes(),LoginActivity.this);
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                Log.e("Error",e.getLocalizedMessage());
                            }
                        });

                break;
            case R.id.login_rootView:
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), 0);
                break;
        }
    }
}
