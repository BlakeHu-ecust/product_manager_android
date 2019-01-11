package com.product.productmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.product.productmanager.Adapter.ListViewAdapter;
import com.product.productmanager.Model.common.listMode_model;
import com.product.productmanager.Model.gd_model;
import com.product.productmanager.Model.listModel;
import com.product.productmanager.Other.Singleton;
import com.product.productmanager.Other.ToolClass;
import com.product.productmanager.http.config.HttpInterface;
import com.product.productmanager.http.config.HttpUtils;
import com.product.productmanager.http.config.URLConfig;
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

    @Override
    protected void onResume() {
        super.onResume();
        //refreshLayout.autoRefresh();
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
                if (currentPage < listModel.getPage().getTotalPage()) {
                    currentPage += 1;
                    refreshData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        });
        listViewAdapter = new ListViewAdapter(arrayList, GongdanListActivity.this);
        gongdanListView.setAdapter(listViewAdapter);
        gongdanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < arrayList.size()){
                    Intent intent = new Intent(GongdanListActivity.this, DetailActivity.class);
                    intent.putExtra("id", arrayList.get(position).getId());
                    startActivityForResult(intent,1000);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
                if (resultCode == RESULT_OK) {
                    refreshLayout.autoRefresh();
                }
            default:
                break;
        }
    }
    private void refreshData() {
//        RetrofitFactory.getInstence()
//                .API()
//                .findWorkOrderByList(Singleton.instance.getUserModel().getId(), currentPage, DEFAULT_SIZE,type)
//                .compose(this.<BaseEntity<listModel<gd_model>>>setThread())
//                .subscribe(new BaseObserver<listModel<gd_model>>() {
//                    @Override
//                    protected void onSuccees(BaseEntity<listModel<gd_model>> t) throws Exception {
//                        listModel = t.getObject();
//                        arrayList.addAll(listModel.getContent());
//                        listViewAdapter.notifyDataSetChanged();
//                        refreshLayout.finishRefresh();
//                        if (currentPage < listModel.getPage().getTotalPage()){
//                            refreshLayout.finishLoadMore();
//                        }
//                        else{
//                            refreshLayout.finishLoadMoreWithNoMoreData();
//                        }
//                    }
//                });
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.startGetRequest(URLConfig.findWorkOrderByList_url + "?userId=" + Singleton.getInstance().getUserModel().getId() + "&page=" + currentPage + "&pageSize=" + DEFAULT_SIZE + "&type=" + type, new HttpInterface() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                final listMode_model list = gson.fromJson(s, listMode_model.class);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (list.isSuccess()) {
                            listModel = list.getObject();
                            arrayList.addAll(listModel.getContent());
                            listViewAdapter.notifyDataSetChanged();
                            refreshLayout.finishRefresh();
                            if (currentPage < listModel.getPage().getTotalPage()) {
                                refreshLayout.finishLoadMore();
                            } else {
                                refreshLayout.finishLoadMoreWithNoMoreData();
                            }
                        } else {
                            ToolClass.showMessage(list.getMessage(), GongdanListActivity.this);
                        }
                    }
                });
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
