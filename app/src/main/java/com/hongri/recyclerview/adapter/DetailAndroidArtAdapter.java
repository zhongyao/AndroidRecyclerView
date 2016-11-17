package com.hongri.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongri.recyclerview.R;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/9/21 14:53
 * @description:
 */
public class DetailAndroidArtAdapter extends RecyclerView.Adapter<DetailAndroidArtAdapter.AndroidArtViewHolder> {

    private Context context;
    private ArrayList<String> andoridArtData;
    private LayoutInflater inflater;
    public onItemViewClickListener listener;

    public DetailAndroidArtAdapter(Context context, ArrayList<String> androidArtData) {

        this.context = context;
        this.andoridArtData = androidArtData;
        inflater = LayoutInflater.from(context);

    }

    public void setOnItemViewClickListener(onItemViewClickListener listener){
        this.listener = listener;
    }

    @Override
    public AndroidArtViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view = inflater.inflate(R.layout.fragment_detail_andorid_art_item,parent,false);
        return new AndroidArtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AndroidArtViewHolder holder, int position) {
        holder.tv.setText(andoridArtData.get(position));
    }

    @Override
    public int getItemCount() {
        return andoridArtData.size();
    }

    public class AndroidArtViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cv;
        private TextView tv;

        public AndroidArtViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            tv = (TextView) itemView.findViewById(R.id.tv);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.itemOnClick(v,getAdapterPosition());
        }
    }

    public interface onItemViewClickListener {
        void itemOnClick(View view,int position);
    }
}
