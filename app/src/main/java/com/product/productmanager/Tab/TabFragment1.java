package com.product.productmanager.Tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.product.productmanager.GongdanListActivity;
import com.product.productmanager.Model.home_current_model;
import com.product.productmanager.Model.home_model;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.R;
import com.product.productmanager.UserActivity;
import com.product.productmanager.http.RetrofitFactory;
import com.product.productmanager.http.base.BaseObserver;
import com.product.productmanager.http.bean.BaseEntity;

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

        RetrofitFactory.getInstence()
                .API()
                .findNewWork(Singleton.instance.getUserModel().getId())
                .compose(this.<BaseEntity<home_current_model>>setThread())
                .subscribe(new BaseObserver<home_current_model>() {
                    @Override
                    protected void onSuccees(BaseEntity<home_current_model> t) throws Exception {
                        home_current_model model = t.getObject();
                        if (model.getId().length() > 0) {
                            styleText.setText(model.getStyleName());
                            gongxuText.setText(model.getName());
                            timeText.setText("交货时间：" + model.getCompleteTime());
                            linIng.setVisibility(View.VISIBLE);
                            workText.setVisibility(View.GONE);
                            imgHand.setVisibility(View.GONE);
                        }
                        else {
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
                intent = new Intent(getActivity(),UserActivity.class);
                startActivity(intent);
                break;
            case R.id.current_lin:
                intent.putExtra("Type", 0);
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
