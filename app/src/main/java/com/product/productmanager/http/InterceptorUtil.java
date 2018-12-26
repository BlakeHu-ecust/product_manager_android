package com.product.productmanager.http;

import android.util.Log;

import com.product.productmanager.LoginActivity;
import com.product.productmanager.Other.SharedPreferencesHelper;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.http.bean.BaseEntity;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;

public class InterceptorUtil {
    public static String TAG = "----";
    private static String Token = "";
    public final static Charset UTF8 = Charset.forName("UTF-8");

    public static Interceptor tokenInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                /**
                 * 1.拦截到返回的数据
                 * 2.判断token是否失效
                 * 3.失效获取新的token
                 * 4.重新请求接口
                 */
                //拿到请求体,并添加header携带上token
                Log.d("CurrentTime",String.valueOf(System.currentTimeMillis()));
                Request mRequest = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .addHeader("authorization", Singleton.instance.getToken())
                        .addHeader("enterprise", Singleton.instance.getEnterprise())
                        .addHeader("timestamp", String.valueOf(System.currentTimeMillis()))
                        .addHeader("dbname","produce")
                        .addHeader("dbip","47.105.135.107")
                        .build();
                Log.d("CurrentHeader",mRequest.headers().toString());
                //拿到响应体
                Response mResponse = chain.proceed(mRequest);
                ResponseBody responseBody = mResponse.body();

                //得到缓冲源
                BufferedSource source = responseBody.source();
                //请求全部
                source.request(Long.MAX_VALUE);

                return chain.proceed(mRequest);
            }
        };
    }
}
