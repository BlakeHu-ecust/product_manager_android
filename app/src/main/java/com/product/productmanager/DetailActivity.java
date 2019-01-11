package com.product.productmanager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.product.productmanager.Adapter.CircleAdapter;
import com.product.productmanager.Adapter.DetailListViewAdapter;
import com.product.productmanager.Model.common.MyBaseModel;
import com.product.productmanager.Model.common.complete_model;
import com.product.productmanager.Model.common.gongxuList_model;
import com.product.productmanager.Model.common.orderProductModel_model;
import com.product.productmanager.Model.detail_list_model;
import com.product.productmanager.Model.gongxuModel;
import com.product.productmanager.Model.orderProductDtoModel;
import com.product.productmanager.Model.orderProductModel;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.View.HorizontalListView;
import com.product.productmanager.http.config.HttpInterface;
import com.product.productmanager.http.config.HttpUtils;
import com.product.productmanager.http.config.URLConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @BindView(R.id.horizonList)
    HorizontalListView horizonList;
    @BindView(R.id.clickStart)
    LinearLayout clickStart;
    @BindView(R.id.back_btn)
    Button backBtn;

    private orderProductModel model = new orderProductModel();
    private ArrayList<detail_list_model> listModels = new ArrayList<>();
    private ArrayList<gongxuModel> gongxuList = new ArrayList<>();
    private CircleAdapter circleAdapter;
    private int selectedNum = -1;

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

    private void getMainData(String id) {
//        RetrofitFactory.getInstence()
//                .API()
//                .workOrderDetail(Singleton.instance.getUserModel().getId(), id)
//                .compose(this.<BaseEntity<orderProductModel>>setThread())
//                .subscribe(new BaseObserver<orderProductModel>() {
//                    @Override
//                    protected void onSuccees(BaseEntity<orderProductModel> t) throws Exception {
//                        model = t.getObject();
//                        getGongxuList();
//                        setUI();
//                    }
//                });
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.startGetRequest(URLConfig.workOrderDetail_url + "?userId=" + Singleton.instance.getUserModel().getId() + "&id=" + id, new HttpInterface() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                orderProductModel_model tem = gson.fromJson(s, orderProductModel_model.class);
                if (tem.isSuccess()) {
                    model = tem.getObject();
                    getGongxuList();
                    setUI();
                } else {
                    ToolClass.showMessage(tem.getMessage(), DetailActivity.this);
                }
            }
        });
    }

    private void setUI() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                titleText.setText(model.getName());
                detailTextOrderNo.setText(detailTextOrderNo.getText().toString() + model.getWorkOrderCode());
                if (model.getProcessName() != null) {
                    detailTextgx.setText(detailTextgx.getText().toString() + model.getProcessName());
                } else {
                    detailTextgx.setVisibility(View.GONE);
                }
                detailTextBanxin.setText(detailTextBanxin.getText().toString() + model.getVersionType());
                detailTextRemark.setText(detailTextRemark.getText().toString() + model.getRemark());
                if (model.getEndTime() != null) {
                    detailTextCompleteTime.setText(detailTextCompleteTime.getText().toString() + model.getEndTime());
                } else {
                    detailTextCompleteTime.setVisibility(View.GONE);
                }
                detailTextGongyi.setText(detailTextGongyi.getText().toString() + model.getTechnology());
                if (model.getDeliveryTime() != null) {
                    detailTextDeveTime.setText(detailTextDeveTime.getText().toString() + model.getDeliveryTime());
                }
                detailTextDeveTime.setVisibility(View.VISIBLE);
                detailTextShiyi.setText(detailTextShiyi.getText().toString() + (model.getFittingRequire() == 0 ? "不需试衣" : "需要试衣"));
                StringBuffer tem = new StringBuffer();
                for (int i = 0; i < model.getOrderfabricList().size(); i++) {
                    orderProductModel.orderfabricListModel m = model.getOrderfabricList().get(i);
                    if (m.getValue().length() > 0) {
                        tem.append("，" + m.getValue());
                    }
                }
                if (tem.toString().length() > 0) {
                    tem.deleteCharAt(0);
                }
                detailTextMianliao.setText(detailTextMianliao.getText().toString() + tem.toString());

                switch (model.getStatus()) {
                    case 0:
                        detailClickComplete.setVisibility(View.GONE);
                        detailComplete.setVisibility(View.GONE);
                        clickStart.setVisibility(View.VISIBLE);
                        horizonList.setVisibility(View.VISIBLE);
                        backBtn.setVisibility(View.VISIBLE);
                        detailTextCompleteTime.setVisibility(View.GONE);
                        detailTextTime.setVisibility(View.GONE);
                        break;
                    case 1:
                        detailClickComplete.setVisibility(View.GONE);
                        detailComplete.setVisibility(View.GONE);
                        clickStart.setVisibility(View.VISIBLE);
                        horizonList.setVisibility(View.VISIBLE);
                        backBtn.setVisibility(View.VISIBLE);
                        detailTextCompleteTime.setVisibility(View.GONE);
                        detailTextTime.setVisibility(View.GONE);
                        break;
                    case 2:
                        detailClickComplete.setVisibility(View.VISIBLE);
                        detailComplete.setVisibility(View.GONE);
                        clickStart.setVisibility(View.GONE);
                        horizonList.setVisibility(View.GONE);
                        detailTextTime.setVisibility(View.GONE);
                        backBtn.setVisibility(View.GONE);
                        detailTextCompleteTime.setVisibility(View.GONE);
                        detailTextTime.setVisibility(View.GONE);
                        break;
                    case 3:
                        detailClickComplete.setVisibility(View.GONE);
                        detailComplete.setVisibility(View.VISIBLE);
                        clickStart.setVisibility(View.GONE);
                        horizonList.setVisibility(View.GONE);
                        backBtn.setVisibility(View.GONE);
                        detailTextCompleteTime.setVisibility(View.VISIBLE);
                        detailTextTime.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (model.getStartTime() != null && model.getStartTime().length() > 0 && model.getEndTime() != null && model.getEndTime().length() > 0) {
                    try {
                        Date date = format.parse(model.getStartTime());
                        Date dateNow = format.parse(model.getEndTime());
                        detailTextTime.setText(detailTextTime.getText() + getDatePoor(dateNow, date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                setListView();
            }
        });
    }

    private String getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;

        StringBuffer stringBuffer = new StringBuffer();
        if (sec > 0) {
            stringBuffer.insert(0, sec + "秒");
        }
        if (min > 0) {
            stringBuffer.insert(0, sec + "分");
        }
        if (hour > 0) {
            stringBuffer.insert(0, sec + "时");
        }
        if (day > 0) {
            stringBuffer.insert(0, sec + "天");
        }
        return stringBuffer.toString();
    }

    private void getGongxuList() {
//        RetrofitFactory.getInstence()
//                .API()
//                .workOrderProcess(model.getStyleId())
//                .compose(this.<BaseEntity<ArrayList<gongxuModel>>>setThread())
//                .subscribe(new BaseObserver<ArrayList<gongxuModel>>() {
//                    @Override
//                    protected void onSuccees(BaseEntity<ArrayList<gongxuModel>> t) throws Exception {
//                        gongxuList = t.getObject();
//                        circleAdapter = new CircleAdapter(gongxuList, DetailActivity.this);
//                        horizonList.setAdapter(circleAdapter);
//                        horizonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                if (gongxuList.get(position).isChoosed()){
//                                    selectedNum = -1;
//                                    gongxuList.get(position).setChoosed(false);
//                                    circleAdapter.notifyDataSetChanged();
//                                    return;
//                                }
//                                if (gongxuList.get(position).getStatus() == 2 || gongxuList.get(position).getStatus() == 3){
//                                    return;
//                                }
//                                for (gongxuModel m : gongxuList){
//                                    m.setChoosed(false);
//                                }
//                                gongxuList.get(position).setChoosed(true);
//                                circleAdapter.notifyDataSetChanged();
//                                selectedNum = position;
//                            }
//                        });
//                    }
//                });
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.startGetRequest(URLConfig.workOrderProcess_url + "?styleId=" + model.getStyleId() + "&workOrderId=" + model.getWorkOrderId(), new HttpInterface() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                final gongxuList_model tem = gson.fromJson(s, gongxuList_model.class);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (tem.isSuccess()) {
                            gongxuList = tem.getObject();
                            circleAdapter = new CircleAdapter(gongxuList, DetailActivity.this);
                            horizonList.setAdapter(circleAdapter);
                            horizonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (position < gongxuList.size()) {
                                        if (gongxuList.get(position).isChoosed()) {
                                            selectedNum = -1;
                                            gongxuList.get(position).setChoosed(false);
                                            circleAdapter.notifyDataSetChanged();
                                            return;
                                        }
                                        if (gongxuList.get(position).getStatus() == 2 || gongxuList.get(position).getStatus() == 3) {
                                            return;
                                        }
                                        for (gongxuModel m : gongxuList) {
                                            m.setChoosed(false);
                                        }
                                        gongxuList.get(position).setChoosed(true);
                                        circleAdapter.notifyDataSetChanged();
                                        selectedNum = position;
                                    }
                                }
                            });
                        } else {
                            ToolClass.showMessage(tem.getMessage(), DetailActivity.this);
                        }
                    }
                });
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

    @OnClick({R.id.btn_back, R.id.lin_back, R.id.detail_clickComplete, R.id.detail_complete, R.id.clickStart,R.id.back_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.lin_back:
                finish();
                break;
            case R.id.detail_clickComplete:
                complete_model tem = new complete_model();
                tem.setId(model.getId());
                tem.setWorkOrderId(model.getWorkOrderId());
                //tem.setUserId(Singleton.getInstance().getUserModel().getId());
                ToolClass.showProgress(DetailActivity.this);
                Gson gson = new Gson();
                String data1 = gson.toJson(tem, complete_model.class);
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.startPostRequest(URLConfig.endWork_url, data1, new HttpInterface() {
                    @Override
                    public void onResponse(String s) {
                        ToolClass.progressDismisss();
                        Gson gson = new Gson();
                        MyBaseModel model = gson.fromJson(s, MyBaseModel.class);
                        if (model.isSuccess()) {
                            ToolClass.showMessage("已经完成工序", DetailActivity.this);
                            refreshDataView();
                        } else {
                            ToolClass.showMessage(model.getMessage(), Singleton.instance.getContext());
                        }
                    }
                });
                break;
            case R.id.detail_complete:

                break;
            case R.id.clickStart:
                if (selectedNum == -1) {
                    ToolClass.showMessage("请选择工序", DetailActivity.this);
                    return;
                }
                ToolClass.showProgress(DetailActivity.this);
//                beginGongxuModel submit = new beginGongxuModel();
//                submit.setId(gongxuList.get(selectedNum).getId());
//                submit.setCreateTime(gongxuList.get(selectedNum).getCreateTime());
//                submit.setEndTime(gongxuList.get(selectedNum).getEndTime());
//                submit.setLogId(gongxuList.get(selectedNum).getLogId());
//                submit.setName(gongxuList.get(selectedNum).getName());
//                submit.setProcessId(gongxuList.get(selectedNum).getProcessId());
//                submit.setRemark(gongxuList.get(selectedNum).getRemark());
//                submit.setStartTime(gongxuList.get(selectedNum).getStartTime());
//                submit.setStyleId(gongxuList.get(selectedNum).getStyleId());
//                submit.setStatus(gongxuList.get(selectedNum).getStatus());
//                submit.setUpdateTime(gongxuList.get(selectedNum).getUpdateTime());
//                submit.setUserId(Singleton.instance.getUserModel().getId());
//                submit.setWorkOrderId(gongxuList.get(selectedNum).getWorkOrderId());
                //gson = new Gson();
                //String data = gson.toJson(gongxuList.get(selectedNum),gongxuModel.class);

                tem = new complete_model();
                tem.setId(model.getId());
                tem.setWorkOrderId(model.getWorkOrderId());
                tem.setUserId(Singleton.getInstance().getUserModel().getId());
                tem.setProcessId(gongxuList.get(selectedNum).getId());
                //ToolClass.showProgress(DetailActivity.this);
                gson = new Gson();
                String data = gson.toJson(tem, complete_model.class);
                httpUtils = new HttpUtils();
                httpUtils.startPostRequest(URLConfig.beginWork_url, data, new HttpInterface() {
                    @Override
                    public void onResponse(String s) {
                        ToolClass.progressDismisss();
                        Gson gson = new Gson();
                        MyBaseModel model = gson.fromJson(s, MyBaseModel.class);
                        if (model.isSuccess()) {
                            ToolClass.showMessage("已经开始工序", DetailActivity.this);
                            refreshDataView();
                        } else {
                            ToolClass.progressDismisss();
                            ToolClass.showMessage(model.getMessage(), DetailActivity.this);
                        }
                    }
                });
                break;
            case R.id.back_btn:
                ToolClass.showProgress(DetailActivity.this);
                httpUtils = new HttpUtils();
                httpUtils.startPostRequest("userApp/undoOrder?id=" + model.getWorkOrderId(), null, new HttpInterface() {

                    @Override
                    public void onResponse(String s) {
                        ToolClass.progressDismisss();
                        Gson gson = new Gson();
                        MyBaseModel model = gson.fromJson(s, MyBaseModel.class);
                        if (model.isSuccess()) {
                            ToolClass.showMessage("退回成功", DetailActivity.this);
                            refreshDataView();
                        } else {
                            ToolClass.showMessage(model.getMessage(), Singleton.instance.getContext());
                        }

                    }
                });
                break;
            default:
                break;
        }
    }
}
