package com.product.productmanager.Tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.product.productmanager.Adapter.ListViewAdapter;
import com.product.productmanager.Model.gongdanModel;
import com.product.productmanager.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TabFragment3 extends Fragment {
    @BindView(R.id.tipsBar_1)
    View tipsBar1;
    @BindView(R.id.lin1)
    LinearLayout lin1;
    @BindView(R.id.tipsBar_2)
    View tipsBar2;
    @BindView(R.id.lin2)
    LinearLayout lin2;
    @BindView(R.id.tipsBar_3)
    View tipsBar3;
    @BindView(R.id.lin3)
    LinearLayout lin3;
    @BindView(R.id.listView)
    ListView listView;
    Unbinder unbinder;

    private int selectedNum = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tab_3, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        refrshBar(selectedNum);
        List<gongdanModel> list = new ArrayList();
        gongdanModel g = new gongdanModel("123","fsfesf","123");
        list.add(g);
        list.add(g);
        list.add(g);
        list.add(g);
        ListViewAdapter listViewAdapter = new ListViewAdapter(list,this.getContext());
        listView.setAdapter(listViewAdapter);
    }

    private void refrshBar(int num){
        selectedNum = num;
        switch (selectedNum){
            case 1:
                tipsBar1.setVisibility(View.VISIBLE);
                tipsBar2.setVisibility(View.GONE);
                tipsBar3.setVisibility(View.GONE);
                break;
            case 2:
                tipsBar1.setVisibility(View.GONE);
                tipsBar2.setVisibility(View.VISIBLE);
                tipsBar3.setVisibility(View.GONE);
                break;
            case 3:
                tipsBar1.setVisibility(View.GONE);
                tipsBar2.setVisibility(View.GONE);
                tipsBar3.setVisibility(View.VISIBLE);
                break;
                default:
                    break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.lin1, R.id.lin2, R.id.lin3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin1:
                refrshBar(1);
                break;
            case R.id.lin2:
                refrshBar(2);
                break;
            case R.id.lin3:
                refrshBar(3);
                break;
        }
    }
}
