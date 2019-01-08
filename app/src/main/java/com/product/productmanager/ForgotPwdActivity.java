package com.product.productmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.product.productmanager.Model.LogModel;
import com.product.productmanager.Model.MyBaseModel;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.bean.BaseEntity;
import com.product.productmanager.http.config.HttpConfig;
import com.product.productmanager.http.config.HttpInterface;
import com.product.productmanager.http.config.HttpUtils;
import com.product.productmanager.http.config.URLConfig;

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

    public void SendDataByPost() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.startPostRequest(HttpConfig.BASE_URL + URLConfig.changePwd_url + "?id=" + Singleton.instance.getUserModel().getId() + "&oldPassword=" + oldPwd.getText().toString() + "&newPassword=" + comfirmPwd.getText().toString(), null, new HttpInterface() {

            @Override
            public void onResponse(String s) {
                ToolClass.progressDismisss();
                Gson gson = new Gson();
                MyBaseModel model = gson.fromJson(s, MyBaseModel.class);
                if (model.isSuccess()) {
                    ToolClass.showMessage("修改密码成功", ForgotPwdActivity.this);
                    finish();
                } else {
                    ToolClass.showMessage(model.getMessage(), Singleton.instance.getContext());
                }
            }
        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection connection = null;
//                BufferedReader reader = null;
//                try {
//                    URL url = new URL("http://47.105.135.107:9081/produce/user/modifyPasswordByPeople?id=" + Singleton.instance.getUserModel().getId() + "&oldPassword=" + oldPwd.getText().toString() + "&newPassword=" + comfirmPwd.getText().toString());
//                    connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("POST");
//                    connection.setDoOutput(true);
//                    connection.setDoInput(true);
//                    connection.setRequestProperty("Accept", "application/json");
//                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//                    connection.setRequestProperty("authorization", Singleton.instance.getToken());
//                    connection.setRequestProperty("enterprise", Singleton.instance.getEnterprise());
//                    connection.setRequestProperty("timestamp", String.valueOf(System.currentTimeMillis()));
//                    connection.setRequestProperty("dbname", Singleton.instance.getUserModel().getProduceDb().getDbName());
//                    connection.setRequestProperty("dbip", Singleton.instance.getUserModel().getProduceDb().getDbIp());
//                    connection.setConnectTimeout(10000);
//                    connection.setReadTimeout(10000);
//                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//                    // POST的请求参数写在正文中
//                    out.write("test" + "=" + "1");
//                    out.flush();
//                    out.close();
//
//                    int status = connection.getResponseCode();
//                    InputStream in;
//                    if (status != HttpURLConnection.HTTP_OK) {
//                        in = connection.getErrorStream();
//                    } else {
//                        in = connection.getInputStream();
//                    }
//                    //下面对获取到的输入流进行读取
//                    reader = new BufferedReader(new InputStreamReader(in));
//                    StringBuilder response = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        response.append(line);
//                    }
//                    String s = response.toString();
//                    Log.d("res", s);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (reader != null) {
//                        try {
//                            reader.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (connection != null) {
//                        connection.disconnect();
//                    }
//                }
//            }
//        }).start();

    }

    @OnClick({R.id.btn_back, R.id.lin_back, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }).start();
                break;
            case R.id.lin_back:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }).start();

                break;
            case R.id.btn_save:
                if (oldPwd.getText().toString().length() == 0) {
                    ToolClass.showMessage("请输入旧密码", ForgotPwdActivity.this);
                    return;
                }
                if (newPwd.getText().toString().length() == 0) {
                    ToolClass.showMessage("请输入新密码", ForgotPwdActivity.this);
                    return;
                }
                if (comfirmPwd.getText().toString().length() == 0) {
                    ToolClass.showMessage("请确认密码", ForgotPwdActivity.this);
                    return;
                }
                if (!newPwd.getText().toString().equals(comfirmPwd.getText().toString())) {
                    ToolClass.showMessage("密码不一致，请重新输入", ForgotPwdActivity.this);
                    return;
                }
                ToolClass.showProgress(ForgotPwdActivity.this);
//                RetrofitFactory.getInstence()
//                        .API()
//                        .changePwd(Singleton.getInstance().getUserModel().getId(), oldPwd.getText().toString(), newPwd.getText().toString())
//                        .compose(this.<BaseEntity<Map>>setThread())
//                        .subscribe(new BaseObserver<Map>() {
//                            @Override
//                            protected void onSuccees(BaseEntity<Map> t) throws Exception {
//                                ToolClass.showMessage("修改密码成功", ForgotPwdActivity.this);
//                                finish();
//                            }
//                        });
                SendDataByPost();
                break;
            default:
                break;
        }
    }
}
