package com.product.productmanager.http;

import com.product.productmanager.Model.UserModel;
import com.product.productmanager.Model.gd_model;
import com.product.productmanager.Model.home_current_model;
import com.product.productmanager.Model.home_model;
import com.product.productmanager.Model.listModel;
import com.product.productmanager.Model.orderProductDtoModel;
import com.product.productmanager.Model.orderProductModel;
import com.product.productmanager.http.bean.BaseEntity;
import com.product.productmanager.http.config.URLConfig;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Observable<BaseEntity<home_model>> findCount(@Query("userId") String userId);

    @GET(URLConfig.findNewWork_url)
    Observable<BaseEntity<home_current_model>> findNewWork(@Query("userId") String userId);

    @GET(URLConfig.findWorkOrderByList_url)
    Observable<BaseEntity<listModel<gd_model>>> findWorkOrderByList(@Query("userId") String userId, @Query("page") Integer page, @Query("pageSize") Integer pageSize, @Query("type") Integer type);

    @GET(URLConfig.findAllComplete_url)
    Observable<BaseEntity<listModel<gd_model>>> findAllComplete(@Query("userId") String userId, @Query("page") Integer page, @Query("pageSize") Integer pageSize, @Query("type") Integer type);

    @GET(URLConfig.findWorkOrderScanById_url)
    Observable<BaseEntity<orderProductModel>> findWorkOrderScanById(@Query("userId") String userId, @Query("id") String id);

    @GET(URLConfig.workOrderDetail_url)
    Observable<BaseEntity<orderProductModel>> workOrderDetail(@Query("userId") String userId, @Query("id") String id);

    @POST(URLConfig.takeOrder_url)
    Observable<BaseEntity<Map>> takeOrder(@Body Map param);
}
