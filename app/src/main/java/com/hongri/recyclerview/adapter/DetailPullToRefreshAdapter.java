package com.hongri.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.utils.Logger;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/7/18 19:01
 * @description:
 */
public class DetailPullToRefreshAdapter extends RecyclerView.Adapter<DetailPullToRefreshAdapter.RefreshViewHolder>{
    private Context context;
    private ArrayList<String> mData;
    private LayoutInflater inflater;
    private int orginalNums;

    public DetailPullToRefreshAdapter(Context context, ArrayList<String> mData, int originalNums) {
        this.context = context;
        this.mData = mData;
        this.orginalNums = originalNums;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RefreshViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_detail_pulltorefresh_item,parent,false);
        return new RefreshViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RefreshViewHolder holder, int position) {
//        if (mData.size()>orginalNums){
//            //将刷新出来的item标上背景色
//            Logger.d("position:"+position);
//            if (mData.get(position).contains("ADD-Android")){
//                Logger.d("position-bg"+position);
//                holder.tv.setBackgroundColor(context.getResources().getColor(R.color.color_31c27c));
//            }
//            holder.tv.setText(mData.get(position));
//
//        }else{
            holder.tv.setText(mData.get(position));
//        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RefreshViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        public RefreshViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
