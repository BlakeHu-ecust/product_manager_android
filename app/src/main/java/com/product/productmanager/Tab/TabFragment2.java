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
import com.product.productmanager.HomeActivity;
import com.product.productmanager.LoginActivity;
import com.product.productmanager.Model.gd_model;
import com.product.productmanager.Model.listModel;
import com.product.productmanager.Model.orderProductModel;
import com.product.productmanager.Model.takeOrderModel;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.R;
import com.product.productmanager.http.RetrofitFactory;
import com.product.productmanager.http.base.BaseObserver;
import com.product.productmanager.http.bean.BaseEntity;
import com.product.productmanager.scanner.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
                int num = 0;
                for (orderProductModel m : arrayList){
                    if (m.isChoosed()){
                        num += 1;
                    }
                }
                tipsLabel.setText("共" + arrayList.size() +  "个产品，已选择" + num + "个");
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
             }
                break;
            case R.id.confirm_btn:
                if (arrayList.size() == 0){
                    ToolClass.showMessage("请选择工单",getActivity());
                    return;
                }
                takeOrderModel model = new takeOrderModel();
                model.setId(Singleton.instance.getUserModel().getId());
                for (orderProductModel m : arrayList){
                    model.getIdList().add(m.getId());
                }

                takeOrderModel.paramModel param = new takeOrderModel.paramModel();
                param.setParam(model);
                Gson gson = new Gson();
                String data = gson.toJson(model,takeOrderModel.class);

                JSONObject object = new JSONObject();
                try {
                    object.put("id",Singleton.instance.getUserModel().getId());
                    object.put("idList",model.getIdList());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//
//
//                Map map1 = new HashMap();
//                map1.put("id",Singleton.instance.getUserModel().getId());
//                map1.put("idList",model.getIdList());
//
//                Map map = new HashMap();
//                map.put("param",data);

                ToolClass.showProgress(getActivity());
                RetrofitFactory.getInstence()
                        .API()
                        .takeOrder(data)
                        .compose(this.<BaseEntity<Map>>setThread())
                        .subscribe(new BaseObserver<Map>() {
                            @Override
                            protected void onSuccees(BaseEntity<Map> t) throws Exception {
                                ToolClass.progressDismisss();

                            }
                        });
                break;
                default:
                    break;
        }
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
