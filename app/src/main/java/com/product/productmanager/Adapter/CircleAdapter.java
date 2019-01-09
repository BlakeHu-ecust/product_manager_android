package com.product.productmanager.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.product.productmanager.Model.gongxuModel;
import com.product.productmanager.R;

import java.util.ArrayList;
import java.util.List;

public class CircleAdapter extends BaseAdapter {
    private ArrayList<gongxuModel> mData;
    private Context mContext;
    public CircleAdapter(ArrayList<gongxuModel> mData, Context mContext) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.horizon_item,parent,false);
        LinearLayout linearLayout = convertView.findViewById(R.id.back);
        TextView textView = convertView.findViewById(R.id.content);
        gongxuModel model = mData.get(position);
        textView.setText(model.getName());

        if (model.isChoosed()){
            linearLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_shape_select));
        }
        else{
            switch (model.getStatus()){
                case 0:
                    linearLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_shape));
                    break;
                case 1:
                    linearLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_shape));
                    break;
                case 2:
                    linearLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_shape1));
                    break;
                case 3:
                    linearLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_shape2));
                    break;
                default:
                    break;
            }
        }
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        params.height = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, mContext.getResources().getDisplayMetrics()));
        params.width = params.height;
        return convertView;
    }
}
