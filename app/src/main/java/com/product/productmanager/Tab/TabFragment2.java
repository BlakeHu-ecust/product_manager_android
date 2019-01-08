package com.product.productmanager.Tab;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.product.productmanager.Adapter.ScanListViewAdapter;
import com.product.productmanager.ForgotPwdActivity;
import com.product.productmanager.HomeActivity;
import com.product.productmanager.LoginActivity;
import com.product.productmanager.Model.gd_model;
import com.product.productmanager.Model.listModel;
import com.product.productmanager.Model.orderProductModel;
import com.product.productmanager.Model.takeOrderModel;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.R;
import com.product.productmanager.http.InterceptorUtil;
import com.product.productmanager.http.RetrofitFactory;
import com.product.productmanager.http.base.BaseObserver;
import com.product.productmanager.http.bean.BaseEntity;
import com.product.productmanager.http.config.HttpConfig;
import com.product.productmanager.http.config.HttpInterface;
import com.product.productmanager.http.config.HttpUtils;
import com.product.productmanager.http.config.URLConfig;
import com.product.productmanager.scanner.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class TabFragment2 extends BaseFragment {
    @BindView(R.id.scan_btn)
    Button scanBtn;
    @BindView(R.id.sm_listView)
    ListView smListView;
    @BindView(R.id.tips_label)
    TextView tipsLabel;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    Unbinder unbinder;

    private Map<String,orderProductModel>recodes = new HashMap<>();
    private ScanListViewAdapter adapter;
    private ArrayList<orderProductModel> arrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tab_2, null);
        unbinder = ButterKnife.bind(this, view);
        adapter = new ScanListViewAdapter(arrayList,getActivity());
        smListView.setAdapter(adapter);
        smListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                arrayList.get(position).setChoosed(!arrayList.get(position).isChoosed());
                adapter.notifyDataSetChanged();
                refreshView();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.scan_btn, R.id.confirm_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.scan_btn:
             if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
             } else {
                    //扫码
                    goScan();
//                 //生成模型
//                 takeOrderModel model = new takeOrderModel();
//                 model.setId(Singleton.instance.getUserModel().getId());
//
//                 String[] array = new String[1];
//                 array[0] = "6379cc1109ad11e9ba9b00163e064017";
//                 model.setIdList(array);
//
//                 //转换成json
//                 Gson gson = new Gson();
//                 String data = gson.toJson(model,takeOrderModel.class);
//
//
//                 RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),data);
//                 OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
//                         .connectTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
//                         .readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
//                         .writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
//                         .addInterceptor(InterceptorUtil.tokenInterceptor())
//                         .build();
//                 Request request = new Request.Builder()
//                         .url(HttpConfig.BASE_URL + URLConfig.takeOrder_url)
//                         .addHeader("Accept", "application/json")
//                         .addHeader("Content-Type", "text/plain")
//                         .addHeader("authorization", Singleton.instance.getToken())
//                         .addHeader("enterprise", Singleton.instance.getEnterprise())
//                         .addHeader("timestamp", String.valueOf(System.currentTimeMillis()))
//                         .addHeader("dbname",Singleton.instance.getUserModel().getProduceDb().getDbName())
//                         .addHeader("dbip",Singleton.instance.getUserModel().getProduceDb().getDbIp())
//                         .post(requestBody)
//                         .build();
//                 mOkHttpClient.newCall(request).enqueue(new Callback() {
//                     @Override
//                     public void onFailure(Call call, IOException e) {
//                         Log.e("s",e.getLocalizedMessage());
//                     }
//                     @Override
//                     public void onResponse(Call call, Response response){
//                         response.body().toString();
//                     }
//                 });
             }
                break;
            case R.id.confirm_btn:
                ArrayList<orderProductModel> tem = new ArrayList<>();
                for (orderProductModel m : arrayList){
                    if (m.isChoosed()) {
                        tem.add(m);
                    }
                }

                if (tem.size() == 0){
                    ToolClass.showMessage("请选择工单",getActivity());
                    return;
                }
                //生成模型
                takeOrderModel model = new takeOrderModel();
                model.setId(Singleton.instance.getUserModel().getId());

                String[] array = new String[tem.size()];
                for (int i = 0;i < tem.size();i++){
                    array[i] = tem.get(i).getId();
                }
                model.setIdList(array);

                //转换成json
                Gson gson = new Gson();
                String data = gson.toJson(model,takeOrderModel.class);

                HashMap<String,Object> map = new HashMap();
                map.put("id",Singleton.instance.getUserModel().getId());
                map.put("idList",array);
                //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),data);


                ToolClass.showProgress(getActivity());
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.startPostRequest(HttpConfig.BASE_URL + URLConfig.takeOrder_url,null, new HttpInterface() {

                    @Override
                    public void onResponse(String s) {
                        ToolClass.showMessage("接单成功",getActivity());
                    }
                });
//                RetrofitFactory.getInstence()
//                        .API()
//                        .takeOrder(requestBody)
//                        .compose(this.<BaseEntity<Map>>setThread())
//                        .subscribe(new BaseObserver<Map>() {
//                            @Override
//                            protected void onSuccees(BaseEntity<Map> t) throws Exception {
//                                ToolClass.progressDismisss();
//                                checkAll();
//                            }
//                            @Override
//                            protected void onCodeError(BaseEntity<Map> t) throws Exception{
//                                ToolClass.progressDismisss();
//                                checkAll();
//                            }
//                            @Override
//                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception{
//                                ToolClass.progressDismisss();
//                                checkAll();
//                            }
//                        });
                break;
                default:
                    break;
        }
    }

    private void refreshView(){
        int num = 0;
        for (orderProductModel m : arrayList){
            if (m.isChoosed()){
                num += 1;
            }
        }
        tipsLabel.setText("共" + arrayList.size() +  "个产品，已选择" + num + "个");
    }

    private void checkAll(){
        for (orderProductModel m : arrayList){
            checkModel(m);
        }
    }

    private void checkModel(orderProductModel m){
        final takeOrderModel model = new takeOrderModel();
        model.setId(Singleton.instance.getUserModel().getId());
        final String[] array = new String[1];
        array[0] = m.getId();
        model.setIdList(array);

        Gson gson = new Gson();
        String data = gson.toJson(model,takeOrderModel.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),data);
        ToolClass.showProgress(getActivity());

        RetrofitFactory.getInstence()
                .API()
                .takeOrder(requestBody)
                .compose(this.<BaseEntity<Map>>setThread())
                .subscribe(new BaseObserver<Map>() {
                    @Override
                    protected void onSuccees(BaseEntity<Map> t) throws Exception {
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception{
                        arrayList.remove(model);
                        adapter.notifyDataSetChanged();
                        refreshView();
                    }
                });
    }
    private void goScan(){
        Intent intent = new Intent(getActivity(),CaptureActivity.class);
        startActivityForResult(intent,101);
    }

    private void refreshUI(){
        for (String s : recodes.keySet()){
            if (!arrayList.contains(recodes.get(s))) {
                arrayList.add(recodes.get(s));
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goScan();
                } else {
                    ToolClass.showMessage("你拒绝了权限申请，无法打开相机扫码哟！",getContext());
                }
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 101:
                if(resultCode==RESULT_OK){
                    /**
                     * 扫码成功
                     */
                    final String s = data.getStringExtra("qrCode");
                    Log.d("QRCode",s);
                    ToolClass.showProgress(getActivity());
                    RetrofitFactory.getInstence()
                            .API()
                            .findWorkOrderScanById(Singleton.instance.getUserModel().getId(), s)
                            .compose(this.<BaseEntity<orderProductModel>>setThread())
                            .subscribe(new BaseObserver<orderProductModel>() {
                                @Override
                                protected void onSuccees(BaseEntity<orderProductModel> t) throws Exception {
                                    ToolClass.progressDismisss();
                                    recodes.put(s,t.getObject());
                                    refreshUI();
                                }
                            });
                }
                break;
            default:
                break;
        }
    }
}
