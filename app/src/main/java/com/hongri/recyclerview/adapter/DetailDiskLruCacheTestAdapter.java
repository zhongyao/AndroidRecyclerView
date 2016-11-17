package com.hongri.recyclerview.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.cache.ImageTask;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/7/27 14:51
 * @description:
 */
public class DetailDiskLruCacheTestAdapter extends RecyclerView.Adapter<DetailDiskLruCacheTestAdapter.CacheViewHolder> {
    private Context context;
    private ArrayList<String> imageUrls;
    private LayoutInflater inflater;
    public DetailDiskLruCacheTestAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CacheViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_detail_disklrucachetest_item,parent,false);
        return new CacheViewHolder(view);
    }



    @Override
    public void onBindViewHolder(CacheViewHolder holder, int position) {
        ImageTask.getInstance().loadImage(context,holder.iv,imageUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }


    public class CacheViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        public CacheViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}
