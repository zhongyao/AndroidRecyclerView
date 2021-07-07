package com.hongri.recyclerview.adapter;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongri.recyclerview.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/7/29 15:45
 * @description:
 */
public class DetailImageLoaderTestAdapter extends RecyclerView.Adapter<DetailImageLoaderTestAdapter.ImageLoaderViewHolder> {
    private ArrayList<String> imageUrls;
    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private Context context;
    public DetailImageLoaderTestAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        inflater = LayoutInflater.from(context);

        //显示图片的配置
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public DetailImageLoaderTestAdapter.ImageLoaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_detail_imageloadertest_item, parent, false);
        return new ImageLoaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailImageLoaderTestAdapter.ImageLoaderViewHolder holder, int position) {
        /**
         * 使用方式一
         */
//        ImageLoader.getInstance().loadImage(imageUrls.get(position), new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                holder.iv.setImageBitmap(loadedImage);
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//
//            }
//        });

        /**
         *方式二
         */
//        ImageSize mImageSize = new ImageSize(100, 100);
//        ImageLoader.getInstance().loadImage(imageUrls.get(position), mImageSize, options, new SimpleImageLoadingListener(){
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view,
//                                          Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
//                holder.iv.setImageBitmap(loadedImage);
//            }
//
//        });

        /**
         * 方式三
         */
        ImageLoader.getInstance().displayImage(imageUrls.get(position), holder.iv, options);
        /**
         * Google推荐图片加载框架Glide：
         * https://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en
         */
//        Glide.with(context).load(imageUrls.get(position)).into(holder.iv);

        //添加加载进度
//        ImageLoader.getInstance().displayImage(imageUrls.get(position), holder.iv, options, new SimpleImageLoadingListener(), new ImageLoadingProgressListener() {
//            @Override
//            public void onProgressUpdate(String imageUri, View view, int current, int total) {
//                Logger.d("current:"+current+"  total:"+total);
//                if (current < total){
//                    holder.tv.setVisibility(View.VISIBLE);
//                    holder.tv.setText(APPUtils.getPercent((float)current,(float)total));
//                }else {
//                    holder.tv.setVisibility(View.GONE);
//                }
//            }
//        });

        /**
         * 方式四（加载本地图片）
         *
         * //图片来源于Content provider
         String contentprividerUrl = "content://media/external/audio/albumart/13";

         //图片来源于assets
         String assetsUrl = Scheme.ASSETS.wrap("image.png");

         //图片来源于
         String drawableUrl = Scheme.DRAWABLE.wrap("R.drawable.image");
         */
//        String imagePath  = Environment.getExternalStorageDirectory().getAbsolutePath()+"/zhong/image.jpg";// 图片在硬盘中的存储地址
//        String imageUrl = ImageDownloader.Scheme.FILE.wrap(imagePath);
//        ImageLoader.getInstance().displayImage(imageUrl,holder.iv,options);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class ImageLoaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv;

        public ImageLoaderViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
