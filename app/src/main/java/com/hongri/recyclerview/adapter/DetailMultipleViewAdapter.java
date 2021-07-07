package com.hongri.recyclerview.adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.common.APPConstants;
import com.hongri.recyclerview.utils.AsyncUtil;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/7/7 16:44
 * @description:
 */
public class DetailMultipleViewAdapter extends RecyclerView.Adapter<DetailMultipleViewAdapter.MultipleViewHolder> {
    private ArrayList<String> mData;
    private ArrayList<Integer> mImageData;
    private Context context;
    private LayoutInflater inflater;
    public MultipleItemClickListener multipleItemClickListener;
    public boolean complicatedList;
    public int reqWidth = 100;
    public int reqHeight = 100;

    public DetailMultipleViewAdapter(Context context, ArrayList<String> mData, ArrayList<Integer> mImageData, boolean complicatedList) {
        this.context = context;
        this.mData = mData;
        this.mImageData = mImageData;
        inflater = LayoutInflater.from(context);
        this.complicatedList = complicatedList;
    }

    public void setMultipleItemClickListener(MultipleItemClickListener multipleItemClickListener) {
        this.multipleItemClickListener = multipleItemClickListener;
    }

    @Override
    public MultipleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == APPConstants.Type_Multiple_Image) {
            view = inflater.inflate(R.layout.fragment_detail_mutiple_item_image, parent, false);
            return new MultipleImageViewHolder(view, multipleItemClickListener);
        } else if(viewType == APPConstants.Type_Multiple_Text){
            view = inflater.inflate(R.layout.fragment_detail_mutiple_item_text, parent, false);
            return new MultipleTextViewHolder(view, multipleItemClickListener);
        }else if (viewType == APPConstants.Type_Multiple_BigImageText_Vertical){
            view = inflater.inflate(R.layout.fragment_detail_mutiple_item_bigimagetext,parent,false);
            return new MultipleBigImageTextViewHolder(view,multipleItemClickListener);
        }else if(viewType == APPConstants.Type_Multiple_ImageText_Horizontal){
            view = inflater.inflate(R.layout.fragment_detail_mutiple_item_imagetext,parent,false);
            return new MultipleImageTextViewHolder(view,multipleItemClickListener);
        }else {
            view = inflater.inflate(R.layout.fragment_detail_mutiple_item_textmultipleimage,parent,false);
            return new MultipleTextMultipleImageViewHolder(view,multipleItemClickListener);
        }
    }


    @Override
    public void onBindViewHolder(MultipleViewHolder holder, int position) {
        if (holder instanceof MultipleImageViewHolder) {
            ((MultipleImageViewHolder) holder).iv.setBackgroundResource(R.drawable.girl);
        } else if (holder instanceof MultipleTextViewHolder) {
            ((MultipleTextViewHolder) holder).tv.setText(mData.get(position));
        }else if (holder instanceof MultipleBigImageTextViewHolder){
//            ((MultipleBigImageTextViewHolder) holder).iv.setBackgroundResource(mImageData.get(position));
//            ((MultipleBigImageTextViewHolder) holder).iv.setImageBitmap(BitmapUtil.decodeSampledBitmapFromResource(context.getResources(),mImageData.get(position),reqWidth,reqHeight));
            ((MultipleBigImageTextViewHolder) holder).tv.setText(mData.get(position));
            AsyncUtil.loadBitmap(context,mImageData.get(position),((MultipleBigImageTextViewHolder) holder).iv);
        }else if(holder instanceof MultipleImageTextViewHolder){
               AsyncUtil.loadBitmap(context,mImageData.get(position),((MultipleImageTextViewHolder) holder).iv);
            ((MultipleImageTextViewHolder) holder).tv.setText(mData.get(position));
        }else if (holder instanceof MultipleTextMultipleImageViewHolder){
            ((MultipleTextMultipleImageViewHolder) holder).tv.setText(mData.get(position));
            AsyncUtil.loadBitmap(context,mImageData.get(position),((MultipleTextMultipleImageViewHolder) holder).iv1);
            AsyncUtil.loadBitmap(context,mImageData.get(position),((MultipleTextMultipleImageViewHolder) holder).iv2);
            AsyncUtil.loadBitmap(context,mImageData.get(position),((MultipleTextMultipleImageViewHolder) holder).iv3);
        }
    }

    @Override
    public int getItemCount() {
        if (mData.size()<=mImageData.size()){
            return mData.size();
        }
        return mImageData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (complicatedList) {
            if (position == 2 || position == 4) {
                return APPConstants.Type_Multiple_BigImageText_Vertical;
            } else if (position == 5 || position == 8 || position == 6) {
                return APPConstants.Type_Multiple_TextMultipleImage_Vertical;
            } else {
                return APPConstants.Type_Multiple_ImageText_Horizontal;
            }
        } else {
            return position % 2 == 0 ? APPConstants.Type_Multiple_Image : APPConstants.Type_Multiple_Text;
        }
    }

    public class MultipleViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{
        public MultipleViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public class MultipleTextViewHolder extends MultipleViewHolder{
        private CardView cv;
        private TextView tv;
        MultipleItemClickListener multipleItemClickListener;

        public MultipleTextViewHolder(View itemView, MultipleItemClickListener multipleItemClickListener) {
            super(itemView);
            this.multipleItemClickListener = multipleItemClickListener;
            tv = (TextView) itemView.findViewById(R.id.tv);
            cv = (CardView) itemView.findViewById(R.id.cv);
            cv.setOnClickListener(this);
            tv.setOnClickListener(this);
            cv.setOnLongClickListener(this);
            tv.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            multipleItemClickListener.onItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            multipleItemClickListener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }

    public class MultipleImageViewHolder extends MultipleViewHolder {
        private ImageView iv;
        private CardView cv;
        MultipleItemClickListener multipleItemClickListener;

        public MultipleImageViewHolder(View itemView, MultipleItemClickListener multipleItemClickListener) {
            super(itemView);
            this.multipleItemClickListener = multipleItemClickListener;
            cv = (CardView) itemView.findViewById(R.id.cv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            cv.setOnClickListener(this);
            iv.setOnClickListener(this);
            cv.setOnLongClickListener(this);
            iv.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            multipleItemClickListener.onItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            multipleItemClickListener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }

    public interface MultipleItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);

    }

    private class MultipleBigImageTextViewHolder extends MultipleViewHolder {
        MultipleItemClickListener multipleItemClickListener;
        private ImageView iv;
        private TextView tv;
        private CardView cv;
        public MultipleBigImageTextViewHolder(View view, MultipleItemClickListener multipleItemClickListener) {
            super(view);
            this.multipleItemClickListener = multipleItemClickListener;
            cv = (CardView) view.findViewById(R.id.cv);
            iv = (ImageView) view.findViewById(R.id.iv);
            tv = (TextView) view.findViewById(R.id.tv);
            cv.setOnClickListener(this);
            cv.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            multipleItemClickListener.onItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            multipleItemClickListener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }

    private class MultipleImageTextViewHolder extends MultipleViewHolder {
        private MultipleItemClickListener multipleItemClickListener;
        private CardView cv;
        private ImageView iv;
        private TextView tv;
        public MultipleImageTextViewHolder(View view, MultipleItemClickListener multipleItemClickListener) {
            super(view);
            this.multipleItemClickListener = multipleItemClickListener;
            cv = (CardView) view.findViewById(R.id.cv);
            iv = (ImageView) view.findViewById(R.id.iv);
            tv = (TextView) view.findViewById(R.id.tv);
            cv.setOnClickListener(this);
            cv.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            multipleItemClickListener.onItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            multipleItemClickListener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }

    private class MultipleTextMultipleImageViewHolder extends MultipleViewHolder {
        MultipleItemClickListener multipleItemClickListener;
        private CardView cv;
        private ImageView iv1,iv2,iv3;
        private TextView tv;
        public MultipleTextMultipleImageViewHolder(View view, MultipleItemClickListener multipleItemClickListener) {
            super(view);
            this.multipleItemClickListener = multipleItemClickListener;
            cv = (CardView) view.findViewById(R.id.cv);
            tv = (TextView) view.findViewById(R.id.tv);
            iv1 = (ImageView) view.findViewById(R.id.iv1);
            iv2 = (ImageView) view.findViewById(R.id.iv2);
            iv3 = (ImageView) view.findViewById(R.id.iv3);
            cv.setOnClickListener(this);
            cv.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            multipleItemClickListener.onItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            multipleItemClickListener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }
}
