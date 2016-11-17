package com.hongri.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.common.APPConstants;
import com.hongri.recyclerview.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：zhongyao on 2016/7/1 10:56
 * @description:
 */
public class DetailNormalViewAdapter extends RecyclerView.Adapter<DetailNormalViewAdapter.DetailViewHolder> {
    private DetailNormalItemClickListener mItemClickListener;
    private Context context;
    private ArrayList<String> mData;
    private List<Integer> heights;
    private LayoutInflater inflater;
    public DetailNormalViewAdapter(Context context, ArrayList<String> mData) {
        this.context = context;
        this.mData = mData;
        this.inflater = LayoutInflater.from(context);

        if (APPConstants.type == APPConstants.Type_Staggered_Layout){
            //随机生成高度，形成瀑布流效果
            getRandomHeight(this.mData);
        }
    }

    private void getRandomHeight(ArrayList<String> mData) {
        heights = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            heights.add((int)(200+Math.random()*400));
        }
    }

    public void setItemClickListener(DetailNormalItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }
    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.fragment_detail_normal_item,parent,false);
        DetailViewHolder holder = new DetailViewHolder(view,this.mItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        if (APPConstants.type == APPConstants.Type_Staggered_Layout){
            ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
            params.height = heights.get(position);//把随机的高度赋予item布局
            holder.itemView.setLayoutParams(params);//把params设置给item布局
        }

        /**
         * 代码中动态设置布局margin，setMargin(args,args,args,args)中的参数均是px值。
         */
//        APPUtils.getPhoneInfo((Activity) context);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        int topmargin = (int) context.getResources().getDimension(R.dimen.home_item_margin);
//        Logger.d("home_item_margin的dp值:80dp");
//        Logger.d("home_item_margin的px值:"+topmargin);
//        params.setMargins(0,topmargin,0,0);
//        holder.cv.setLayoutParams(params);
//
          holder.tv.setText(mData.get(position));

    }

    @Override
    public int getItemCount() {
        Logger.d("DetailNormalViewAdapter--getItemCount:"+mData.size());
        return mData.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        DetailNormalItemClickListener mItemClickListener;
        private TextView tv;
        private CardView cv;

        public DetailViewHolder(View itemView, DetailNormalItemClickListener mItemClickListener) {
            super(itemView);
            this.mItemClickListener = mItemClickListener;
            tv = (TextView) itemView.findViewById(R.id.tv);
            cv = (CardView) itemView.findViewById(R.id.cv);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v,getAdapterPosition());
        }

    }

    public interface DetailNormalItemClickListener{
        void onItemClick(View v,int position);

    }
}
;