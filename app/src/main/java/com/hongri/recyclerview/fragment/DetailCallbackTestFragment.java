package com.hongri.recyclerview.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.cache.BitmapCache;
import com.hongri.recyclerview.cache.BitmapDiskCache;
import com.hongri.recyclerview.common.APPConstants;
import com.hongri.recyclerview.https.HttpRequest;
import com.hongri.recyclerview.https.IHttpRequest;
import com.hongri.recyclerview.utils.Logger;

/**
 * @author：zhongyao on 2016/7/19 18:26
 * @description:
 */
public class DetailCallbackTestFragment extends Fragment {
    public static DetailCallbackTestFragment callbackTestFragment;
    private int position;
    private String title;
    private ImageView iv;
    private Bitmap bitmap;
    private BitmapCache bitmapCache;

    public static Fragment newInstance(int position, String title) {
        if (callbackTestFragment == null) {
            synchronized (DetailCallbackTestFragment.class) {
                if (callbackTestFragment == null) {
                    callbackTestFragment = new DetailCallbackTestFragment();
                }
            }
        }
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("title", title);
        callbackTestFragment.setArguments(bundle);
        return callbackTestFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        position = bundle.getInt("position", 0);
        title = bundle.getString("title", "title");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_callbacktest, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv = (ImageView) view.findViewById(R.id.iv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        HttpRequest.getUserRequest(callBack, APPConstants.urlImage,getActivity());

    }

    private IHttpRequest.IHttpRequestCallBack callBack = new IHttpRequest.IHttpRequestCallBack() {
        @Override
        public void onSuccess(IHttpRequest request) {

            /**
             * 加载图片
             */
            loadBitmap(request);
//            if (request.getDataObject() != null && !request.getDataObject().equals("")) {
//                iv.setImageBitmap((Bitmap) request.getDataObject());
//                Logger.d("onSuccess");
//            }
        }

        @Override
        public void onFailed(String failReason) {
            Logger.d("onFailed");
        }
    };

    private void loadBitmap(IHttpRequest request){
//        bitmap = (Bitmap) request.getDataObject();
        /**
         * 缓存中获取图片,有图片缓存则显示
         * 没有则从sd卡获取，有则显示
         * 没有则需重新网络加载
         */
        bitmap = BitmapCache.getInstance().getBitmap(APPConstants.urlImage);
        if (bitmap == null) {
            bitmap = BitmapDiskCache.getInstance(getActivity()).getBitmapFromDiskCache(APPConstants.urlImage);
            if (bitmap == null) {
                HttpRequest.getUserRequest(callBack, APPConstants.urlImage,getActivity());
            } else {
                iv.setImageBitmap(bitmap);
            }

        } else {
            iv.setImageBitmap(bitmap);
        }
    }
}
