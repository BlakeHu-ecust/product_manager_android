package com.product.productmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.product.productmanager.Other.Singleton;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends BaseActivity {
    @BindView(R.id.nameText)
    TextView nameText;
    @BindView(R.id.headerImg)
    ImageView headerImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        nameText.setText(Singleton.getInstance().getUserModel().getRealName());
        String s = "http://" + Singleton.getInstance().getUserModel().getProduceDb().getDbIp() + "/image" + Singleton.getInstance().getUserModel().getImage();
        Picasso.get().load(s).into(headerImg);
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
                Intent intent = new Intent(UserActivity.this, ForgotPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.logout_btn:
                Intent intent1 = new Intent(UserActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("auto", false);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
