package com.product.productmanager.http.config;

import android.util.Log;

import com.google.gson.Gson;
import com.product.productmanager.Model.MyBaseModel;
import com.product.productmanager.Model.UserModel;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.bean.BaseEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class HttpUtils{
    public void startPostRequest(final String path, final String body, final HttpInterface httpInterface){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(path);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                    connection.setRequestProperty("authorization", Singleton.instance.getToken());
                    connection.setRequestProperty("enterprise", Singleton.instance.getEnterprise());
                    connection.setRequestProperty("timestamp", String.valueOf(System.currentTimeMillis()));
                    connection.setRequestProperty("dbname", Singleton.instance.getUserModel().getProduceDb().getDbName());
                    connection.setRequestProperty("dbip", Singleton.instance.getUserModel().getProduceDb().getDbIp());
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                    // POST的请求参数写在正文中
//                    if (map != null && map.keySet().size() > 0) {
//                        for (String key : map.keySet()) {
//                            out.write(key + "=" + map.get(key) + "&");
//                        }
//                        out.flush();
//                        out.close();
//                    }
                    if (body != null && body.length() > 0) {
                        out.write(body);
                        out.flush();
                        out.close();
                    }
                    int status = connection.getResponseCode();
                    InputStream in;
                    if (status != HttpURLConnection.HTTP_OK) {
                        in = connection.getErrorStream();
                    } else {
                        in = connection.getInputStream();
                    }
                    //下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    String s = response.toString();
                    Log.d("==================result==================", s);
                    httpInterface.onResponse(s);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
