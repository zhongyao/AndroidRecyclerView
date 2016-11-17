package com.hongri.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.utils.Logger;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/6/29 17:34
 * @description:HomeFragment--RecyclerView适配器
 */
public class HomeViewAdapter extends RecyclerView.Adapter<HomeViewAdapter.HomeViewHolder> {

    private Context context;
    private  ArrayList<String> mData;
    private HomeViewItemClickListener mItemClickListener;
    private LayoutInflater mLayoutInflater;
    public HomeViewAdapter(Context context, ArrayList<String> mData){
        this.context = context;
        this.mData = mData;
        this.mLayoutInflater = LayoutInflater.from(context);
        Logger.d("HomeViewAdapter");
    }

    public void setItemClickListener(HomeViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Logger.d("HomeViewAdapter--onCreateViewHolder()");
        View view = this.mLayoutInflater.inflate(R.layout.home_recyclerview_item,parent,false);
        HomeViewHolder holder = new HomeViewHolder(view,this.mItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        Logger.d("HomeViewAdapter--onBindViewHolder()");

        holder.tv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        Logger.d("HomeViewAdapter--getItemCount()");
        return mData.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        HomeViewItemClickListener mItemClickListener;
        CardView cv_item;
        TextView tv;
        public HomeViewHolder(View itemView ,HomeViewItemClickListener mItemClickListener) {
            super(itemView);
            this.mItemClickListener = mItemClickListener;
            Logger.d("HomeViewAdapter--HomeViewHolder");

            cv_item = (CardView) itemView.findViewById(R.id.cv_item);
            tv = (TextView) itemView.findViewById(R.id.tv);

            cv_item.setOnClickListener(this);
            cv_item.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Logger.d("onClick()");
            if (mItemClickListener != null){
                mItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }

        /**
         * 返回值为true时：长按时只调用onLongClick；
         * 返回值为false时：长按时先调用onLongClick，然后调用onClick。
         * @param v
         * @return
         */
        @Override
        public boolean onLongClick(View v) {
            Logger.d("onLongClick()");
            if (mItemClickListener != null){
                mItemClickListener.onItemLongClick(v,getAdapterPosition());
            }
            return true;
        }
    }

    public interface HomeViewItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
}
