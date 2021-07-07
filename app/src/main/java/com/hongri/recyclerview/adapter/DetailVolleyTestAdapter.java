package com.hongri.recyclerview.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.hongri.recyclerview.R;
import com.hongri.recyclerview.utils.VolleyUtils;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/7/12 18:14
 * @description:
 */
public class DetailVolleyTestAdapter extends RecyclerView.Adapter<DetailVolleyTestAdapter.VolleyViewHolder> {
    private ArrayList<String> mImageUrls;
    private Context context;
    private LayoutInflater inflater;

    public DetailVolleyTestAdapter(Context context, ArrayList<String> mImageUrls) {
        this.mImageUrls = mImageUrls;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public DetailVolleyTestAdapter.VolleyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_detail_volleytest_item, parent, false);
        return new VolleyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailVolleyTestAdapter.VolleyViewHolder holder, int position) {
//      VolleyUtils.VolleyImageRequest(context,mImageUrls.get(position),holder.iv,0,0,R.mipmap.ic_launcher);

//      VolleyUtils.VolleyImageLoader(context,mImageUrls.get(position),holder.iv,200,200,R.mipmap.ic_launcher,R.mipmap.ic_error);

//      VolleyUtils.VolleyImageLoaderAndCache(context,mImageUrls.get(position),holder.iv,200,200,R.mipmap.ic_launcher,R.mipmap.ic_error);

        VolleyUtils.VolleyNetworkImageView(context,mImageUrls.get(position),holder.iv,R.mipmap.ic_launcher,R.mipmap.ic_error);
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class VolleyViewHolder extends RecyclerView.ViewHolder {
//        private ImageView iv;
          private NetworkImageView iv;
        public VolleyViewHolder(View itemView) {
            super(itemView);
//            iv = (ImageView) itemView.findViewById(R.id.iv);
            iv = (NetworkImageView) itemView.findViewById(R.id.iv);
        }
    }
}
