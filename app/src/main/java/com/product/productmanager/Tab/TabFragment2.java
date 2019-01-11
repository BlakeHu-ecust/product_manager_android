package com.product.productmanager.Tab;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.product.productmanager.Adapter.ScanListViewAdapter;
import com.product.productmanager.Model.common.MyBaseModel;
import com.product.productmanager.Model.common.orderProductModel_model;
import com.product.productmanager.Model.orderProductModel;
import com.product.productmanager.Model.takeOrderModel;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.R;
import com.product.productmanager.http.config.HttpInterface;
import com.product.productmanager.http.config.HttpUtils;
import com.product.productmanager.http.config.URLConfig;
import com.product.productmanager.scanner.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    //private Map<String,orderProductModel>recodes = new HashMap<>();
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
                if (position < arrayList.size()) {
                    arrayList.get(position).setChoosed(!arrayList.get(position).isChoosed());
                    adapter.notifyDataSetChanged();
                    refreshView();
                }
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
                openCamera();
                break;
            case R.id.confirm_btn:
                final ArrayList<orderProductModel> tem = new ArrayList<>();
                for (orderProductModel m : arrayList){
                    if (m.isChoosed()) {
                        tem.add(m);
                    }
                }

                if (tem.size() == 0){
                    ToolClass.showMessage("请选择工单",getActivity());
                    return;
                }
                int num = 0;
                for (orderProductModel m : tem){
                    if (m.getUrgent() == 1){
                        num++;
                    }
                }
                if (num >= 2){
                    ToolClass.showMessage("最多只能同时接一个加急工单！",getActivity());
                    return;
                }
                //生成模型
                takeOrderModel model = new takeOrderModel();
                model.setId(Singleton.instance.getUserModel().getId());

                String[] array = new String[tem.size()];
                JsonArray jsonArray = new JsonArray();
                for (int i = 0;i < tem.size();i++){
                    array[i] = tem.get(i).getId();
                    jsonArray.add(tem.get(i).getId());
                }
                model.setIdList(array);

                //转换成json
                Gson gson = new Gson();
                String data = gson.toJson(model,takeOrderModel.class);

                HashMap<String,Object> map = new HashMap();
                map.put("id",Singleton.instance.getUserModel().getId());
                map.put("idList",array);

                ToolClass.showProgress(getActivity());
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.startPostRequest(URLConfig.takeOrder_url,data, new HttpInterface() {

                    @Override
                    public void onResponse(String s) {
                        ToolClass.progressDismisss();
                        Gson gson = new Gson();
                        MyBaseModel model = gson.fromJson(s, MyBaseModel.class);
                        if (model.isSuccess()) {
                            ToolClass.showMessage("接单成功", getActivity());
                        } else {
                            ToolClass.showMessage(model.getMessage(), Singleton.instance.getContext());
                        }
                        for (orderProductModel m : tem){
                            String id = m.getId();
                            for (int i = 0;i < arrayList.size();i++){
                                if (arrayList.get(i).getId().equals(id)){
                                    arrayList.remove(i);
                                    break;
                                }
                            }
                        }
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                refreshView();
                            }
                        });

                    }
                });
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

    public void openCamera(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            //扫码
            goScan();
        }
    }

    private void goScan(){
        Intent intent = new Intent(getActivity(),CaptureActivity.class);
        startActivityForResult(intent,101);
    }

    private void refreshUI(){
//        for (String s : recodes.keySet()){
//            if (!arrayList.contains(recodes.get(s))) {
//                arrayList.add(recodes.get(s));
//            }
//        }
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
                    for (orderProductModel m : arrayList){
                        if (m.getId().equals(s)){
                            ToolClass.showMessage("此工单已在列表中",getActivity());
                            return;
                        }
                    }
                    ToolClass.showProgress(getActivity());
                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.startGetRequest(URLConfig.findWorkOrderScanById_url + "?userId=" + Singleton.instance.getUserModel().getId() + "&id=" + s, new HttpInterface() {
                        @Override
                        public void onResponse(String s) {
                            ToolClass.progressDismisss();
                            Gson gson = new Gson();
                            orderProductModel_model tem = gson.fromJson(s,orderProductModel_model.class);
                            if (tem.isSuccess()){
                                //recodes.put(s,tem.getObject());
                                arrayList.add(tem.getObject());
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        refreshUI();
                                        refreshView();
                                    }
                                });
                            }
                            else{
                                ToolClass.showMessage(tem.getMessage(),getActivity());
                            }
                        }
                    });

                }
                break;
            default:
                break;
        }
    }
}
