package com.product.productmanager.http;

import com.product.productmanager.Model.UserModel;
import com.product.productmanager.http.bean.ABean;
import com.product.productmanager.http.bean.BaseEntity;
import com.product.productmanager.http.config.URLConfig;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author yemao
 * @date 2017/4/9
 * @description API接口!
 */

public interface APIFunction {

    @POST(URLConfig.login_url)
    Observable<BaseEntity<UserModel>> login(@Query("userName") String userName,@Query("password") String password,@Query("timestamp") String time);

    @GET(URLConfig.findCount_url)
    Observable<BaseEntity<String>> findCount(@Query("userId") String userId);
}
