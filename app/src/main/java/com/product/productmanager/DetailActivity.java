package com.product.productmanager;

import android.os.Bundle;
import android.view.View;

import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.RetrofitFactory;
import com.product.productmanager.http.base.BaseObserver;
import com.product.productmanager.http.bean.BaseEntity;
import com.product.productmanager.http.config.HttpConfig;
import com.product.productmanager.http.config.URLConfig;

import java.io.IOException;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd_detail);
        ButterKnife.bind(this);
        String id = getIntent().getStringExtra("id");
        if (id.length() > 0) {
            OkHttpClient okHttpClient = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(HttpConfig.BASE_URL + "userApp/workOrderDetail")
                    .newBuilder();
            urlBuilder.addQueryParameter("userId", Singleton.instance.getUserModel().getId());
            urlBuilder.addQueryParameter("id",id);

            final Request request = new Request.Builder()
                    .url(urlBuilder.build())
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("authorization", Singleton.instance.getToken())
                    .addHeader("enterprise", Singleton.instance.getEnterprise())
                    .addHeader("timestamp", String.valueOf(System.currentTimeMillis()))
                    .addHeader("dbname",Singleton.instance.getUserModel().getProduceDb().getDbName())
                    .addHeader("dbip",Singleton.instance.getUserModel().getProduceDb().getDbIp())
                    .get()//默认就是GET请求，可以不写
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String s = e.getLocalizedMessage();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String s = response.body().toString();
                }
            });

//            RetrofitFactory.getInstence()
//                    .API()
//                    .workOrderDetail(Singleton.instance.getUserModel().getId(), id)
//                    .compose(this.<BaseEntity<Object>>setThread())
//                    .subscribe(new BaseObserver<Object>() {
//                        @Override
//                        protected void onSuccees(BaseEntity<Object> t) throws Exception {
//                            String s = t.getMes();
//                        }
//
//                        @Override
//                        protected void onCodeError(BaseEntity t) throws Exception {
//                            ToolClass.showMessage(t.getMes(), DetailActivity.this);
//                        }
//
//                        @Override
//                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                            ToolClass.showMessage(e.getLocalizedMessage(), DetailActivity.this);
//                        }
//                    });
        } else {
            ToolClass.showMessage("工单id为空", DetailActivity.this);
            finish();
        }
    }

    @OnClick({R.id.btn_back, R.id.lin_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.lin_back:
                finish();
                break;
                default:
                    break;
        }
    }
}
