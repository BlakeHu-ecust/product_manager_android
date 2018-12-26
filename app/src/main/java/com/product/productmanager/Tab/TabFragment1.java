package com.product.productmanager.Tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.product.productmanager.GongdanListActivity;
import com.product.productmanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class TabFragment1 extends Fragment {
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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tab_1, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.header, R.id.current_lin, R.id.today_lin, R.id.complete_lin, R.id.month_lin, R.id.notComplete_lin})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getActivity(),GongdanListActivity.class);
        switch (view.getId()) {
            case R.id.header:
                break;
            case R.id.current_lin:
                intent.putExtra("Type",0);
                break;
            case R.id.today_lin:
                intent.putExtra("Type",1);
                break;
            case R.id.complete_lin:
                intent.putExtra("Type",2);
                break;
            case R.id.month_lin:
                intent.putExtra("Type",3);
                break;
            case R.id.notComplete_lin:
                intent.putExtra("Type",4);
                break;
                default:
                    break;
        }
        startActivity(intent);
    }
}
