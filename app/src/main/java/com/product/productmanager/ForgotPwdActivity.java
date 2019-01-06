package com.product.productmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.product.productmanager.Model.UserModel;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.RetrofitFactory;
import com.product.productmanager.http.base.BaseObserver;
import com.product.productmanager.http.bean.BaseEntity;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ForgotPwdActivity extends BaseActivity {
    @BindView(R.id.oldPwd)
    EditText oldPwd;
    @BindView(R.id.newPwd)
    EditText newPwd;
    @BindView(R.id.comfirmPwd)
    EditText comfirmPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_back, R.id.lin_back, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.lin_back:
                finish();
                break;
            case R.id.btn_save:
                if (oldPwd.getText().toString().length() == 0){
                    ToolClass.showMessage("请输入旧密码",ForgotPwdActivity.this);
                    return;
                }
                if (newPwd.getText().toString().length() == 0){
                    ToolClass.showMessage("请输入新密码",ForgotPwdActivity.this);
                    return;
                }
                if (comfirmPwd.getText().toString().length() == 0){
                    ToolClass.showMessage("请确认密码",ForgotPwdActivity.this);
                    return;
                }
                if (!newPwd.getText().toString().equals(comfirmPwd.getText().toString())){
                    ToolClass.showMessage("密码不一致，请重新输入",ForgotPwdActivity.this);
                    return;
                }
                ToolClass.showProgress(ForgotPwdActivity.this);
                RetrofitFactory.getInstence()
                        .API()
                        .changePwd(Singleton.getInstance().getUserModel().getId(),oldPwd.getText().toString(),newPwd.getText().toString())
                        .compose(this.<BaseEntity<Map>>setThread())
                        .subscribe(new BaseObserver<Map>() {
                            @Override
                            protected void onSuccees(BaseEntity<Map> t) throws Exception {
                                ToolClass.showMessage("修改密码成功",ForgotPwdActivity.this);
                                finish();
                            }
                        });
                break;
                default:
                    break;
        }
    }
}
