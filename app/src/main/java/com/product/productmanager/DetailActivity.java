package com.product.productmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.product.productmanager.Model.orderProductModel;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.RetrofitFactory;
import com.product.productmanager.http.base.BaseObserver;
import com.product.productmanager.http.bean.BaseEntity;

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

    private orderProductModel model = new orderProductModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd_detail);
        ButterKnife.bind(this);
        String id = getIntent().getStringExtra("id");
        if (id.length() > 0) {
            RetrofitFactory.getInstence()
                    .API()
                    .workOrderDetail(Singleton.instance.getUserModel().getId(), id)
                    .compose(this.<BaseEntity<orderProductModel>>setThread())
                    .subscribe(new BaseObserver<orderProductModel>() {
                        @Override
                        protected void onSuccees(BaseEntity<orderProductModel> t) throws Exception {
                            model = t.getObject();
                            setUI();
                        }
                    });
        } else {
            ToolClass.showMessage("工单id为空", DetailActivity.this);
            finish();
        }
    }

    private void setUI(){
        titleText.setText(model.getName());
        detailTextOrderNo.setText(detailTextOrderNo.getText().toString() + model.getWorkOrderCode());
        detailTextgx.setText(detailTextgx.getText().toString() + model.getProcessName());
        detailTextBanxin.setText(detailTextBanxin.getText().toString() + model.getVersionType());
        detailTextRemark.setText(detailTextRemark.getText().toString() + model.getRemark());
        detailTextCompleteTime.setText(detailTextCompleteTime.getText().toString() + model.getEndTime());
        detailTextGongyi.setText(detailTextGongyi.getText().toString() + model.getTechnology());
        detailTextShiyi.setText(detailTextShiyi.getText().toString() + (model.getFittingRequire() == 0 ? "不试衣" : "试衣"));
        String tem = "";
        for (int i = 0;i < model.getOrderfabricList().size();i++){
            orderProductModel.orderfabricListModel m = model.getOrderfabricList().get(i);
            if (i == 0){
                tem += m.getValue();
            }
            else{
                tem += "，" + m.getValue();
            }
        }
        detailTextMianliao.setText(detailTextMianliao.getText().toString() + tem);
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
