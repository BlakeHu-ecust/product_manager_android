package com.product.productmanager.Tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.product.productmanager.Adapter.ListViewAdapter;
import com.product.productmanager.Model.gd_model;
import com.product.productmanager.Model.listModel;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.R;
import com.product.productmanager.http.RetrofitFactory;
import com.product.productmanager.http.base.BaseObserver;
import com.product.productmanager.http.bean.BaseEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TabFragment3 extends BaseFragment {
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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int selectedNum = 1;
    private int currentPage = 1;

    private ArrayList<gd_model> arrayList = new ArrayList<>();
    private ListViewAdapter<gd_model> listViewAdapter;
    private listModel listModel = new listModel();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tab_3, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
       // final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //传入false表示刷新失败
                currentPage = 1;
                arrayList.clear();
                refreshData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (currentPage < listModel.getPage().getTotalPage()){
                    currentPage += 1;
                    refreshData();
                }
                else{
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        });

        listViewAdapter = new ListViewAdapter(arrayList, getContext());
        listView.setAdapter(listViewAdapter);
        refrshBar(selectedNum);
    }

    private void refreshData() {
        RetrofitFactory.getInstence()
                .API()
                .findWorkOrderByList(Singleton.instance.getUserModel().getId(), currentPage, DEFAULT_SIZE, selectedNum == 3 ? null : (selectedNum - 1))
                .compose(this.<BaseEntity<listModel<gd_model>>>setThread())
                .subscribe(new BaseObserver<listModel<gd_model>>() {
                    @Override
                    protected void onSuccees(BaseEntity<listModel<gd_model>> t) throws Exception {
                        listModel = t.getObject();
                        arrayList.addAll(listModel.getContent());
                        listViewAdapter.notifyDataSetChanged();
                        refreshLayout.finishRefresh();
                        if (currentPage < listModel.getPage().getTotalPage()){
                            refreshLayout.finishLoadMore();
                        }
                        else{
                            refreshLayout.finishLoadMoreWithNoMoreData();
                        }
//                        if (listModel.getContent().size() < DEFAULT_SIZE){
//                            refreshLayout.finishLoadMoreWithNoMoreData();
//                            refreshLayout.setEnableAutoLoadMore(false);
//                        }
//                        else {
//                            refreshLayout.finishLoadMore();
//                        }
                    }
                });
    }

    private void refrshBar(int num) {
        selectedNum = num;
        switch (selectedNum) {
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
        arrayList.clear();
        //refreshData();
        if (refreshLayout.isRefreshing()){
            return;
        }
        refreshLayout.autoRefresh();
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
            default:
                break;
        }
    }
}
