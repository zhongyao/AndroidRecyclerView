package com.hongri.recyclerview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hongri.recyclerview.R;
import com.hongri.recyclerview.cache.BitmapCache;
import com.hongri.recyclerview.cache.BitmapDiskCache;
import com.hongri.recyclerview.cache.ImageWorker;
import com.hongri.recyclerview.https.HttpRequest;
import com.hongri.recyclerview.https.IHttpRequest;
import com.hongri.recyclerview.utils.Logger;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/7/22 11:09
 * @description:
 */
public class DetailLoadPicsTestAdapter extends RecyclerView.Adapter<DetailLoadPicsTestAdapter.LoadPicsViewHolder> {
    private Context context;
    private ArrayList<String> imageUrls;
    private LayoutInflater inflater;
    private Handler mHandler = new Handler();

    public DetailLoadPicsTestAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public LoadPicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_detail_loadpicstest_item, parent, false);
        return new LoadPicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LoadPicsViewHolder holder, final int position) {
        /**
         * 使用自己写的内存缓存+SD卡缓存（貌似效果甚至比使用DiskLruCache的效果好，这就比较蛋疼了，没看出DiskLruCache的优点）
         */
        ImageWorker.loadImage(holder.iv,imageUrls.get(position) ,mHandler);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class LoadPicsViewHolder extends ViewHolder {
        private ImageView iv;

        public LoadPicsViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }


}
