package com.product.productmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.product.productmanager.Adapter.ListViewAdapter;
import com.product.productmanager.Model.gongdanModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GongdanListActivity extends Activity {
    @BindView(R.id.gongdan_title)
    TextView gongdanTitle;
    @BindView(R.id.gongdan_listView)
    ListView gongdanListView;

    private static final int TYPE_CURRENT = 0;
    private static final int TYPE_TODAY = 1;
    private static final int TYPE_COMPLETE = 2;
    private static final int TYPE_MONTH = 3;
    private static final int TYPE_NOT_COMPLETE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongdan);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        switch (getIntent().getIntExtra("Type", 0)) {
            case TYPE_CURRENT:
                gongdanTitle.setText("当前工单");
                break;
            case TYPE_TODAY:
                gongdanTitle.setText("今日工单");
                break;
            case TYPE_COMPLETE:
                gongdanTitle.setText("已完成");
                break;
            case TYPE_MONTH:
                gongdanTitle.setText("本月工单");
                break;
            case TYPE_NOT_COMPLETE:
                gongdanTitle.setText("未完成");
                break;
            default:
                break;
        }
        List<gongdanModel> list = new ArrayList();
        gongdanModel g = new gongdanModel("123", "fsfesf", "123");
        list.add(g);
        list.add(g);
        list.add(g);
        list.add(g);
        ListViewAdapter listViewAdapter = new ListViewAdapter(list, this);
        gongdanListView.setAdapter(listViewAdapter);
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
