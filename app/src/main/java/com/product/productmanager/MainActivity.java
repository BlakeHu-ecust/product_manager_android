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
import com.product.productmanager.Model.takeOrderModel;
import com.product.productmanager.Other.SharedPreferencesHelper;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.InterceptorUtil;
import com.product.productmanager.http.config.HttpConfig;
import com.product.productmanager.http.config.URLConfig;

import java.io.IOException;
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
