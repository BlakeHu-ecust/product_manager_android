package com.product.productmanager;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.product.productmanager.Adapter.DetailListViewAdapter;
import com.product.productmanager.Model.detail_list_model;
import com.product.productmanager.Model.gongxuModel;
import com.product.productmanager.Model.orderProductDtoModel;
import com.product.productmanager.Model.orderProductModel;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.RetrofitFactory;
import com.product.productmanager.http.base.BaseObserver;
import com.product.productmanager.http.bean.BaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.titleText)
    TextView titleText;
    @BindView(R.id.detail_clickComplete)
    LinearLayout detailClickComplete;
    @BindView(R.id.detail_complete)
    LinearLayout detailComplete;
    @BindView(R.id.detail_header)
    LinearLayout detailHeader;
    @BindView(R.id.detail_text_orderNo)
    TextView detailTextOrderNo;
    @BindView(R.id.detail_text_deveTime)
    TextView detailTextDeveTime;
    @BindView(R.id.detail_textgx)
    TextView detailTextgx;
    @BindView(R.id.detail_text_completeTime)
    TextView detailTextCompleteTime;
    @BindView(R.id.detail_text_time)
    TextView detailTextTime;
    @BindView(R.id.detail_text_remark)
    TextView detailTextRemark;
    @BindView(R.id.detail_text_mianliao)
    TextView detailTextMianliao;
    @BindView(R.id.detail_text_banxin)
    TextView detailTextBanxin;
    @BindView(R.id.detail_text_gongyi)
    TextView detailTextGongyi;
    @BindView(R.id.detail_text_shiyi)
    TextView detailTextShiyi;
    @BindView(R.id.detail_list)
    ListView detailList;
    @BindView(R.id.scrol)
    ScrollView scrol;

    private orderProductModel model = new orderProductModel();
    private ArrayList<detail_list_model> listModels = new ArrayList<>();
    private ArrayList<gongxuModel> gongxuList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd_detail);
        ButterKnife.bind(this);
        String id = getIntent().getStringExtra("id");
        if (id.length() > 0) {
            getMainData(id);
        } else {
            ToolClass.showMessage("工单id为空", DetailActivity.this);
            finish();
        }
    }

    private void getMainData(String id){
        RetrofitFactory.getInstence()
                .API()
                .workOrderDetail(Singleton.instance.getUserModel().getId(), id)
                .compose(this.<BaseEntity<orderProductModel>>setThread())
                .subscribe(new BaseObserver<orderProductModel>() {
                    @Override
                    protected void onSuccees(BaseEntity<orderProductModel> t) throws Exception {
                        model = t.getObject();
                        getGongxuList();
                        setUI();
                    }
                });
    }
    private void setUI() {
        titleText.setText(model.getName());
        detailTextOrderNo.setText(detailTextOrderNo.getText().toString() + model.getWorkOrderCode());
        detailTextgx.setText(detailTextgx.getText().toString() + model.getProcessName());
        detailTextBanxin.setText(detailTextBanxin.getText().toString() + model.getVersionType());
        detailTextRemark.setText(detailTextRemark.getText().toString() + model.getRemark());
        detailTextCompleteTime.setText(detailTextCompleteTime.getText().toString() + model.getEndTime());
        detailTextGongyi.setText(detailTextGongyi.getText().toString() + model.getTechnology());
        detailTextDeveTime.setText(detailTextDeveTime.getText().toString() + model.getDeliveryTime());
        detailTextShiyi.setText(detailTextShiyi.getText().toString() + (model.getFittingRequire() == 0 ? "不试衣" : "试衣"));
        String tem = "";
        for (int i = 0; i < model.getOrderfabricList().size(); i++) {
            orderProductModel.orderfabricListModel m = model.getOrderfabricList().get(i);
            if (i == 0) {
                tem += m.getValue();
            } else {
                tem += "，" + m.getValue();
            }
        }
        detailTextMianliao.setText(detailTextMianliao.getText().toString() + tem);

        setListView();
    }

    private void getGongxuList(){
        RetrofitFactory.getInstence()
                .API()
                .workOrderProcess(model.getStyleId())
                .compose(this.<BaseEntity<ArrayList<gongxuModel>>>setThread())
                .subscribe(new BaseObserver<ArrayList<gongxuModel>>() {
                    @Override
                    protected void onSuccees(BaseEntity<ArrayList<gongxuModel>> t) throws Exception {
                        gongxuList = t.getObject();
                    }
                });
    }

    private void setListView() {
        int count = 0;

        detail_list_model model1 = new detail_list_model();
        model1.setTitle("缝标位置");
        Map<String, String> map1 = new HashMap<>();
        for (orderProductDtoModel.orderTrademarkListModel m : model.getOrderProductDto().getOrderTrademarkListModels()) {
            map1.put(m.getTrademarkType(), m.getValue());
        }
        model1.setMap(map1);
        if (model.getOrderProductDto().getOrderTrademarkListModels().size() > 0) {
            listModels.add(model1);
            count += model.getOrderProductDto().getOrderTrademarkListModels().size() + 1;
        }


        detail_list_model model2 = new detail_list_model();
        model2.setTitle("刺绣方案");
        Map<String, String> map2 = new HashMap<>();
        for (orderProductDtoModel.orderEmbroideryListModel m : model.getOrderProductDto().getOrderEmbroideryList()) {
            map2.put(m.getEmbroideryParts(), m.getValue());
        }
        model2.setMap(map2);
        if (model.getOrderProductDto().getOrderEmbroideryList().size() > 0) {
            listModels.add(model2);
            count += model.getOrderProductDto().getOrderEmbroideryList().size() + 1;
        }

        Set<String> set = new HashSet();
        for (orderProductDtoModel.orderChangeDressingListModel m : model.getOrderProductDto().getOrderChangeDressingList()) {
            set.add(m.getPartLabel());
        }

        Map<String, ArrayList<orderProductDtoModel.orderChangeDressingListModel>> arrayListMap = new HashMap<>();
        for (String key : set) {
            arrayListMap.put(key, new ArrayList());
        }
        for (orderProductDtoModel.orderChangeDressingListModel m : model.getOrderProductDto().getOrderChangeDressingList()) {
            ArrayList tem = arrayListMap.get(m.getPartLabel());
            tem.add(m);
        }
        for (Map.Entry<String, ArrayList<orderProductDtoModel.orderChangeDressingListModel>> entry : arrayListMap.entrySet()) {
            detail_list_model model3 = new detail_list_model();
            model3.setTitle(entry.getKey());
            Map<String, String> map3 = new HashMap<>();
            for (orderProductDtoModel.orderChangeDressingListModel m : entry.getValue()) {
                map3.put(m.getProductParts(), m.getValue());
            }
            model3.setMap(map3);
            if (entry.getValue().size() > 0) {
                listModels.add(model3);
                count += entry.getValue().size() + 1;
            }
        }


        DetailListViewAdapter adapter = new DetailListViewAdapter(listModels, DetailActivity.this);
        detailList.setAdapter(adapter);
        ViewGroup.LayoutParams params = detailList.getLayoutParams();
        params.height = (int) (count * TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics())) + (int) (listModels.size() * TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics())) + 100;
        //params.height = (int) (listModels.size() * TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,getResources().getDisplayMetrics()));
        detailList.setLayoutParams(params);
    }

    @OnClick({R.id.btn_back, R.id.lin_back, R.id.detail_clickComplete, R.id.detail_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.lin_back:
                finish();
                break;
            case R.id.detail_clickComplete:
                break;
            case R.id.detail_complete:
                break;
        }
    }
}
