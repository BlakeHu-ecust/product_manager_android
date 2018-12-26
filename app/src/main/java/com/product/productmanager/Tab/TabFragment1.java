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
import com.product.productmanager.LoginActivity;
import com.product.productmanager.Model.UserModel;
import com.product.productmanager.Other.SharedPreferencesHelper;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.R;
import com.product.productmanager.http.RetrofitFactory;
import com.product.productmanager.http.base.BaseObserver;
import com.product.productmanager.http.bean.BaseEntity;

import java.util.ArrayList;

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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tab_1, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        Log.d("CurrentToken",Singleton.instance.getToken());
        RetrofitFactory.getInstence()
                .API()
                .findCount(Singleton.instance.getUserModel().getId())
                .compose(this.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccees(BaseEntity<String> t) throws Exception {
                        Log.d("123123",t.getObject());
                    }

                    @Override
                    protected void onCodeError(BaseEntity t) throws Exception {
                        ToolClass.showMessage(t.getMes(),getActivity());
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.e("NetError",e.toString());
                        ToolClass.showMessage(e.getLocalizedMessage(),getActivity());
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
                break;
            case R.id.current_lin:
                intent.putExtra("Type", 0);
                break;
            case R.id.today_lin:
                intent.putExtra("Type", 1);
                break;
            case R.id.complete_lin:
                intent.putExtra("Type", 2);
                break;
            case R.id.month_lin:
                intent.putExtra("Type", 3);
                break;
            case R.id.notComplete_lin:
                intent.putExtra("Type", 4);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
