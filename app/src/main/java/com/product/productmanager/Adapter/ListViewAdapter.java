package com.product.productmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.product.productmanager.Model.gongdanModel;
import com.product.productmanager.R;

import java.util.LinkedList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private List<gongdanModel> mData;
    private Context mContext;

    public ListViewAdapter(List<gongdanModel> mData, Context mContext) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.gongdan_item,parent,false);
        TextView name = (TextView) convertView.findViewById(R.id.nameText);
        TextView time = (TextView) convertView.findViewById(R.id.timeText);
        TextView status = (TextView) convertView.findViewById(R.id.statusText);
        gongdanModel model = mData.get(position);
        name.setText(model.getName());
        time.setText(model.getTime());
        status.setText(model.getStatus());
        return convertView;
    }
}
