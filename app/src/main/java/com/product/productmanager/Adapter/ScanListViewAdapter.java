package com.product.productmanager.Adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.product.productmanager.Model.orderProductModel;
import com.product.productmanager.R;

import java.util.List;

public class ScanListViewAdapter extends BaseAdapter {
    private List<orderProductModel> mData;
    private Context mContext;

    public ScanListViewAdapter(List<orderProductModel> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.saomiao_item,parent,false);
        TextView product = convertView.findViewById(R.id.productText);
        TextView gongxu = convertView.findViewById(R.id.gongxuText);
        TextView name = convertView.findViewById(R.id.nameText);
        RelativeLayout urgent = convertView.findViewById(R.id.urgent_lin);
        TextView time = convertView.findViewById(R.id.time_text);
        ImageView button = convertView.findViewById(R.id.choose_state_btn);

        orderProductModel model = mData.get(position);
        product.setText("产品：" + model.getName());
        gongxu.setText("工序：" + model.getProcessName());
        name.setText("单号：" + model.getWorkOrderCode());
        time.setText("交货日期：" + model.getDeliveryTime());
        urgent.setVisibility(model.getUrgent() == 0 ? View.GONE : View.VISIBLE);
        button.setImageDrawable(ContextCompat.getDrawable(mContext,model.isChoosed() ? R.drawable.icon_choose:R.drawable.icon_unchoose));
        return convertView;
    }
}
