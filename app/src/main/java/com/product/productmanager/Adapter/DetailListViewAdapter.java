package com.product.productmanager.Adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.product.productmanager.Model.detail_list_model;
import com.product.productmanager.R;

import java.util.ArrayList;


public class DetailListViewAdapter extends BaseAdapter {
    private ArrayList<detail_list_model> mData;
    private Context mContext;

    public DetailListViewAdapter(ArrayList<detail_list_model> mData, Context mContext) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.detail_item,parent,false);
        ListView listView = convertView.findViewById(R.id.detail_sub_list);
        DetailListSubViewAdapter adapter = new DetailListSubViewAdapter(mData.get(position),mContext);
        listView.setAdapter(adapter);

        ViewGroup.LayoutParams params = listView.getLayoutParams();


        params.height = (int) ((mData.get(position).getMap().keySet().size() + 1) * TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30,mContext.getResources().getDisplayMetrics())) + (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,mContext.getResources().getDisplayMetrics()));
        listView.setLayoutParams(params);

        return convertView;
    }
}
