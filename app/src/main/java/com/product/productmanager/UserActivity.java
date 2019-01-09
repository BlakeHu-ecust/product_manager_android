package com.product.productmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_back, R.id.lin_back, R.id.changePwd, R.id.logout_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.lin_back:
                finish();
                break;
            case R.id.changePwd:
                Intent intent = new Intent(UserActivity.this,ForgotPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.logout_btn:
                Intent intentmain=new Intent(UserActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentmain);
                break;
                default:
                    break;
        }
    }
}
