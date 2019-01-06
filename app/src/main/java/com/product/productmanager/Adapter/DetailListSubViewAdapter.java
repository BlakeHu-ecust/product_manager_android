package com.product.productmanager.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.product.productmanager.Model.detail_list_model;
import com.product.productmanager.R;

import java.util.ArrayList;
import java.util.Map;


public class DetailListSubViewAdapter extends BaseAdapter {
    private detail_list_model model;
    private Context mContext;

    public DetailListSubViewAdapter(detail_list_model model, Context mContext) {
        this.model = model;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return model.getMap().keySet().size() + 1;
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.detail_item_sub,parent,false);
        TextView textView1 = convertView.findViewById(R.id.textLeft);
        TextView textView2 = convertView.findViewById(R.id.textMiddle);
        TextView textView3 = convertView.findViewById(R.id.textRight);
        if (position == 0){
            textView2.setText(model.getTitle());
            textView1.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);
        }
        else{
            ArrayList<String> keys = new ArrayList();
            ArrayList<String> values = new ArrayList();
            for (Map.Entry entry : model.getMap().entrySet()) {
                keys.add((String) entry.getKey());
                values.add((String)entry.getValue());
            }
            convertView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.list_item_shape_sub1));
            textView1.setText(keys.get(position - 1));
            textView3.setText(values.get(position - 1));
            textView2.setVisibility(View.GONE);
        }
        return convertView;
    }
}
