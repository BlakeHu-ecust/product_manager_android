package com.product.productmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.product.productmanager.Model.common.MyBaseModel;
import com.product.productmanager.Other.SharedPreferencesHelper;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.config.HttpInterface;
import com.product.productmanager.http.config.HttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @OnClick({R.id.company_button, R.id.company_rootView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.company_button:
                if (companyEdit.getText().toString().length() == 0){
                    ToolClass.showMessage("请输入企业码",MainActivity.this);
                    return;
                }
                ToolClass.showProgress(MainActivity.this);
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.startGetRequest("v1/enterprice/findEnterprise" + "?enterpriseId=" + companyEdit.getText().toString(), new HttpInterface() {
                    @Override
                    public void onResponse(String s) {
                        Gson gson = new Gson();
                        MyBaseModel model = gson.fromJson(s,MyBaseModel.class);
                        ToolClass.progressDismisss();
                        if (model.isSuccess()){
                            sharedPreferencesHelper.put(COMPANY_NO,companyEdit.getText().toString());
                            Singleton.instance.setEnterprise(companyEdit.getText().toString());
                            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            ToolClass.showMessage(model.getMessage(),MainActivity.this);
                        }
                    }
                });

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
