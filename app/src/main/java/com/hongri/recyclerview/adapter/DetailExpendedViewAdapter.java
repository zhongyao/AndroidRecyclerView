package com.hongri.recyclerview.adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongri.recyclerview.R;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/7/11 17:10
 * @description:
 */
public class DetailExpendedViewAdapter extends RecyclerView.Adapter<DetailExpendedViewAdapter.ExpendedViewHolder>{
    private ArrayList<String> mData;
    private Context context;
    private LayoutInflater inflater;
    private ItemClickListener mItemClickListener;
    public DetailExpendedViewAdapter(Context context, ArrayList<String> mData) {
        this.context = context;
        this.mData = mData;
        inflater = LayoutInflater.from(context);
    }

    public void setmItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public ExpendedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_recyclerview_item,parent,false);
        ExpendedViewHolder holder = new ExpendedViewHolder(view,this.mItemClickListener,this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ExpendedViewHolder holder, int position) {
        holder.tv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0:mData.size();
    }

    public class ExpendedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CardView cv_item;
        private TextView tv;
        ItemClickListener mItemClickListener;
        DetailExpendedViewAdapter mAdapter;
        public ExpendedViewHolder(View itemView, ItemClickListener mItemClickListener,DetailExpendedViewAdapter mAdapter) {
            super(itemView);
            this.mAdapter = mAdapter;
            this.mItemClickListener = mItemClickListener;
            cv_item = (CardView) itemView.findViewById(R.id.cv_item);
            tv = (TextView) itemView.findViewById(R.id.tv);
            cv_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
               mItemClickListener.ItemClick(getLayoutPosition());
            if (getLayoutPosition() == 1){
                mAdapter.removeText(getLayoutPosition());
            }else {
                mAdapter.addText(getLayoutPosition());
            }
        }
    }

    private void removeText(int position) {
        mData.remove(mData.get(position));
        notifyItemRemoved(position);
    }

    private void addText(int position) {
        mData.add(1,"addedText-"+position);
        notifyItemInserted(1);
    }

    public interface ItemClickListener{
        void ItemClick(int position);
    }
}
