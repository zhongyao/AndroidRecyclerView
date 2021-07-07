package com.hongri.recyclerview.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.adapter.DetailImageLoaderTestAdapter;
import com.hongri.recyclerview.common.APPConstants;
import com.hongri.recyclerview.utils.DataUtil;
import com.hongri.recyclerview.utils.Logger;

/**
 * @author：zhongyao on 2016/7/29 15:17
 * @description:
 */
public class DetailImageLoaderTestFragment extends Fragment {
    private static DetailImageLoaderTestFragment imageLoaderTestFragment;
    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_imageloadertest, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), APPConstants.Column_Nums));
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Logger.d("onScrolled............");
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState){
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Logger.d("停滞");
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        Logger.d("拖拽滑动");
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Logger.d("惯性滑动");
                        break;
                }
            }


        });
        rv.setAdapter(new DetailImageLoaderTestAdapter(getActivity(), DataUtil.getImageUrls()));
        return view;
    }

    public static Fragment newInstance() {
        if (imageLoaderTestFragment == null) {
            synchronized (DetailImageLoaderTestFragment.class) {
                if (imageLoaderTestFragment == null) {
                    imageLoaderTestFragment = new DetailImageLoaderTestFragment();
                }
            }
        }
        return imageLoaderTestFragment;
    }
}
