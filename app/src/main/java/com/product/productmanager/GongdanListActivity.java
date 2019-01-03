package com.product.productmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.product.productmanager.Adapter.ListViewAdapter;
import com.product.productmanager.Model.gd_model;
import com.product.productmanager.Model.listModel;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.RetrofitFactory;
import com.product.productmanager.http.base.BaseObserver;
import com.product.productmanager.http.bean.BaseEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GongdanListActivity extends BaseActivity {
    @BindView(R.id.gongdan_title)
    TextView gongdanTitle;
    @BindView(R.id.gongdan_listView)
    ListView gongdanListView;

    private static final int TYPE_CURRENT = 0;
    private static final int TYPE_TODAY = 1;
    private static final int TYPE_COMPLETE = 2;
    private static final int TYPE_MONTH = 3;
    private static final int TYPE_NOT_COMPLETE = 4;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int currentPage = 1;
    private ArrayList<gd_model> arrayList = new ArrayList<>();
    private ListViewAdapter<gd_model> listViewAdapter;
    private listModel listModel = new listModel();
    private int type = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongdan);
        ButterKnife.bind(this);
        initView();
        refreshLayout.autoRefresh();
    }

    private void initView() {
        switch (getIntent().getIntExtra("Type", 0)) {
            case TYPE_CURRENT:
                gongdanTitle.setText("当前工单");
                break;
            case TYPE_TODAY:
                gongdanTitle.setText("今日工单");
                type = 2;
                break;
            case TYPE_COMPLETE:
                gongdanTitle.setText("已完成");
                type = 1;
                break;
            case TYPE_MONTH:
                gongdanTitle.setText("本月工单");
                break;
            case TYPE_NOT_COMPLETE:
                gongdanTitle.setText("未完成");
                type = 0;
                break;
            default:
                break;
        }

        refreshLayout.setRefreshHeader(new ClassicsHeader(GongdanListActivity.this));
        refreshLayout.setRefreshFooter(new BallPulseFooter(GongdanListActivity.this).setSpinnerStyle(SpinnerStyle.Scale));
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
        listViewAdapter = new ListViewAdapter(arrayList, GongdanListActivity.this);
        gongdanListView.setAdapter(listViewAdapter);
        gongdanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String id = arrayList.get(position).getId();
                Intent intent = new Intent(GongdanListActivity.this,DetailActivity.class);
                intent.putExtra("id",arrayList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void refreshData() {
        RetrofitFactory.getInstence()
                .API()
                .findAllComplete(Singleton.instance.getUserModel().getId(), currentPage, DEFAULT_SIZE,type)
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
                    }
                });
    }

    @OnClick({R.id.btn_back, R.id.lin_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.lin_back:
                finish();
                break;
            default:
                break;
        }
    }
}
