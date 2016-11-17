package com.hongri.recyclerview.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hongri.recyclerview.R;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/10/25 19:06
 * @description:
 */

public class DetailAndroidArtGridAdapter extends BaseAdapter {
    private Activity mActivity;
    private ArrayList<String> homeData;
    private LayoutInflater inflater;
    public DetailAndroidArtGridAdapter(Activity mActivity, ArrayList<String> homeData) {
        this.mActivity = mActivity;
        this.homeData = homeData;
        inflater = LayoutInflater.from(mActivity);
    }

    @Override
    public int getCount() {
        return homeData.size();
    }

    @Override
    public Object getItem(int position) {
        return homeData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null){
            holder = new MyViewHolder();
            convertView = inflater.inflate(R.layout.andorid_art_gridview_item,parent,false);
            holder.ll1 = (RelativeLayout) convertView.findViewById(R.id.ll1);
            holder.ll2 = (LinearLayout) convertView.findViewById(R.id.ll2);
            holder.ll3 = (LinearLayout) convertView.findViewById(R.id.ll3);
            holder.textView4 = (TextView) convertView.findViewById(R.id.textview4);
//            holder.textView4.setTextColor(mActivity.getResources().getColor(R.drawable.plugin_collection_list_item_title_selector));
//            holder.ll1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ToastUtil.ShowBottomShort(mActivity,"ll点击");
//                   Logger.d("ll1");
//                }
//            });
//            holder.ll2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Logger.d("ll2");
//                }
//            });
//            holder.ll3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Logger.d("ll3");
//                }
//            });
//            holder.textView4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ToastUtil.ShowBottomShort(mActivity,"textview点击");
//                    Logger.d("textView4");
//                }
//            });
            convertView.setTag(holder);
        }else {
           holder = (MyViewHolder) convertView.getTag();
        }

        holder.textView4.setText(homeData.get(position));

        return convertView;
    }

    public  class MyViewHolder{
        RelativeLayout ll1;
        LinearLayout ll2,ll3;
        TextView textView4;
    }
}
