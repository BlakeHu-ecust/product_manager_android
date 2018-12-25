package com.product.productmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.product.productmanager.Adapter.ListViewAdapter;
import com.product.productmanager.Model.gongdanModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GongdanListActivity extends Activity {
    @BindView(R.id.gongdan_title)
    TextView gongdanTitle;
    @BindView(R.id.gongdan_listView)
    ListView gongdanListView;

    private static final int TypeCurrent = 0;
    private static final int TypeToday = 1;
    private static final int TypeComplete = 2;
    private static final int TypeMonth = 3;
    private static final int TypeNotComplete = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongdan);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
       switch ( getIntent().getIntExtra("Type",0)){
           case TypeCurrent:
               gongdanTitle.setText("当前工单");
               break;
           case TypeToday:
               gongdanTitle.setText("今日工单");
               break;
           case TypeComplete:
               gongdanTitle.setText("已完成");
               break;
           case TypeMonth:
               gongdanTitle.setText("本月工单");
               break;
           case TypeNotComplete:
               gongdanTitle.setText("未完成");
               break;
               default:
                   break;
       }
        List<gongdanModel> list = new ArrayList();
        gongdanModel g = new gongdanModel("123","fsfesf","123");
        list.add(g);
        list.add(g);
        list.add(g);
        list.add(g);
        ListViewAdapter listViewAdapter = new ListViewAdapter(list,this);
        gongdanListView.setAdapter(listViewAdapter);
    }
}
