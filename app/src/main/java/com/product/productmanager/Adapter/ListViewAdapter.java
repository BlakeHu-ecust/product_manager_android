package com.product.productmanager.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.product.productmanager.Model.gd_model;
import com.product.productmanager.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListViewAdapter<T> extends BaseAdapter {

    private List<T> mData;
    private Context mContext;

    public ListViewAdapter(List<T> mData, Context mContext) {
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
        ImageView imageView = convertView.findViewById(R.id.tip_img);
        RelativeLayout relativeLayout = convertView.findViewById(R.id.urgent_lin);

        gd_model model = (gd_model)mData.get(position);

        name.setText(model.getStyleName());
        status.setText(model.getStatus() == 3 ? "已完成 " : "未完成");
        imageView.setImageDrawable(ContextCompat.getDrawable(mContext,model.getStatus() == 3 ? R.drawable.icon_right:R.drawable.icon_ing));
//        if (model.getDeliveryTime() != null && model.getDeliveryTime().length() > 0) {
////            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////            long lt = new Long(model.getDeliveryTime());
////            Date date = new Date(lt);
////            time.setText(simpleDateFormat.format(date));
//        }
//        else{
//            time.setText("");
//        }
        if (model.getStatus() == 3){
            time.setText(model.getEndTime());
        }
        else {
            time.setText(model.getDeliveryTime());
        }

        relativeLayout.setVisibility(model.getUrgent() == 0 ? View.GONE : View.VISIBLE);
        return convertView;
    }
}
