package com.hongri.recyclerview.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.hongri.recyclerview.R;

/**
 * Created by zhongyao on 16/12/2.
 */
public class DetailReboundAdapter extends RecyclerView.Adapter<DetailReboundAdapter.ImageViewHolder> {
    private Context mActivity;
    private LayoutInflater inflater;
    public DetailReboundAdapter(Context mActivity) {
        this.mActivity = mActivity;
        inflater = LayoutInflater.from(mActivity);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_detail_android_art_rebound,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        holder.image.setImageResource(R.drawable.icon_landscape_image2);

        SpringSystem springSystem = SpringSystem.create();
        Spring spring = springSystem.createSpring();
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(100,1));
        spring.addListener(new SimpleSpringListener(){
            @Override
            public void onSpringUpdate(Spring spring) {
//                          super.onSpringUpdate(spring);
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                holder.image.setScaleX(scale);
//                holder.image.setScaleY(scale);
            }
        });
        spring.setEndValue(1);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        public ImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
