package com.product.productmanager.Tab;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.product.productmanager.DetailActivity;
import com.product.productmanager.GongdanListActivity;
import com.product.productmanager.Model.current_model;
import com.product.productmanager.Model.home_current_model;
import com.product.productmanager.Model.home_model;
import com.product.productmanager.Model.orderProductModel;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.R;
import com.product.productmanager.UserActivity;
import com.product.productmanager.http.RetrofitFactory;
import com.product.productmanager.http.base.BaseObserver;
import com.product.productmanager.http.bean.BaseEntity;
import com.product.productmanager.http.config.HttpConfig;
import com.product.productmanager.http.config.HttpInterface;
import com.product.productmanager.http.config.HttpUtils;
import com.product.productmanager.http.config.URLConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class TabFragment1 extends BaseFragment {
    @BindView(R.id.header)
    ImageView header;
    @BindView(R.id.current_lin)
    LinearLayout currentLin;
    @BindView(R.id.current_text)
    TextView currentText;
    @BindView(R.id.today_lin)
    LinearLayout todayLin;
    @BindView(R.id.complete_text)
    TextView completeText;
    @BindView(R.id.complete_lin)
    LinearLayout completeLin;
    @BindView(R.id.month_text)
    TextView monthText;
    @BindView(R.id.month_lin)
    LinearLayout monthLin;
    @BindView(R.id.notComplete_text)
    TextView notCompleteText;
    @BindView(R.id.notComplete_lin)
    LinearLayout notCompleteLin;
    Unbinder unbinder;
    @BindView(R.id.nameLabel)
    TextView nameLabel;
    @BindView(R.id.companyLabel)
    TextView companyLabel;
    @BindView(R.id.time_ingText)
    TextView timeIngText;
    @BindView(R.id.lin_ing)
    LinearLayout linIng;
    @BindView(R.id.styleText)
    TextView styleText;
    @BindView(R.id.gongxuText)
    TextView gongxuText;
    @BindView(R.id.timeText)
    TextView timeText;
    @BindView(R.id.workText)
    TextView workText;
    @BindView(R.id.img_hand)
    ImageView imgHand;

    private home_current_model model = new home_current_model();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tab_1, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        Log.d("CurrentToken", Singleton.instance.getToken());
        nameLabel.setText(Singleton.instance.getUserModel().getRealName());
        companyLabel.setText(Singleton.instance.getUserModel().getAddress());

        RetrofitFactory.getInstence()
                .API()
                .findCount(Singleton.instance.getUserModel().getId())
                .compose(this.<BaseEntity<home_model>>setThread())
                .subscribe(new BaseObserver<home_model>() {
                    @Override
                    protected void onSuccees(BaseEntity<home_model> t) throws Exception {
                        completeText.setText(t.getObject().getComplete());
                        notCompleteText.setText(t.getObject().getUnComplete());
                        currentText.setText(t.getObject().getTodayTotal());
                        monthText.setText(t.getObject().getMonthComplete());
                    }
                });

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.startGetRequest(URLConfig.findNewWork_url + "?userId=" + Singleton.getInstance().getUserModel().getId(), new HttpInterface() {
            @Override
            public void onResponse(String s) {
                ToolClass.progressDismisss();
                Gson gson = new Gson();
                final current_model m = gson.fromJson(s, current_model.class);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (m.isSuccess()) {
                            model = m.getObject();
                            if (model != null && model.getId().length() > 0) {
                                styleText.setText(model.getStyleName());
                                gongxuText.setText(model.getName());
                                timeText.setText("交货时间：" + model.getCompleteTime());
                                linIng.setVisibility(View.VISIBLE);
                                workText.setVisibility(View.GONE);
                                imgHand.setVisibility(View.GONE);
                                timeIngText.setVisibility(View.VISIBLE);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                long lt = new Long(model.getStartTime());
                                Date date = new Date(lt);
                                Date dateNow = new Date();
                                timeIngText.setText(getDatePoor(dateNow,date));
                            } else {
                                linIng.setVisibility(View.GONE);
                                styleText.setVisibility(View.GONE);
                                gongxuText.setVisibility(View.GONE);
                                timeText.setVisibility(View.GONE);
                                workText.setVisibility(View.VISIBLE);
                                imgHand.setVisibility(View.VISIBLE);
                            }
                        } else {
                            //ToolClass.showMessage(m.getMessage(), Singleton.instance.getContext());
                            linIng.setVisibility(View.GONE);
                            styleText.setVisibility(View.GONE);
                            gongxuText.setVisibility(View.GONE);
                            timeText.setVisibility(View.GONE);
                            workText.setVisibility(View.VISIBLE);
                            imgHand.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    private String getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.header, R.id.current_lin, R.id.today_lin, R.id.complete_lin, R.id.month_lin, R.id.notComplete_lin})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getActivity(), GongdanListActivity.class);
        switch (view.getId()) {
            case R.id.header:
                intent = new Intent(getActivity(), UserActivity.class);
                startActivity(intent);
                break;
            case R.id.current_lin:
                if (model != null && model.getId().length() > 0) {
                    intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("id", model.getId());
                    startActivity(intent);
                }
                break;
            case R.id.today_lin:
                intent.putExtra("Type", 1);
                startActivity(intent);
                break;
            case R.id.complete_lin:
                intent.putExtra("Type", 2);
                startActivity(intent);
                break;
            case R.id.month_lin:
                //intent.putExtra("Type", 3);
                break;
            case R.id.notComplete_lin:
                intent.putExtra("Type", 4);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
