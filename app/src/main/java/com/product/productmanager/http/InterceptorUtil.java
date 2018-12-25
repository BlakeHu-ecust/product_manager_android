package com.product.productmanager.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;
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

    /**
     * token验证的拦截器
     *
     * @return
     */
    public static void tokenInterceptor1() {
        new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拿到请求体,并添加header携带上token
                Request mRequest = chain.request().newBuilder()
                        .addHeader("Token", Token)
                        .addHeader("enterprise", "a")
                        .build();
// 新的请求
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.method(chain.request().method(), chain.request().body());

                return chain.proceed(requestBuilder.build());
            }
        };
    }

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
                Request mRequest = chain.request().newBuilder()
                        .addHeader("Token", Token)
                        .addHeader("enterprise", "a")
                        .build();

                //拿到响应体
                Response mResponse = chain.proceed(mRequest);
                ResponseBody responseBody = mResponse.body();

                //得到缓冲源
                BufferedSource source = responseBody.source();
                //请求全部
                source.request(Long.MAX_VALUE); // Buffer the entire body.

                return chain.proceed(mRequest);
            }
        };
    }
}
