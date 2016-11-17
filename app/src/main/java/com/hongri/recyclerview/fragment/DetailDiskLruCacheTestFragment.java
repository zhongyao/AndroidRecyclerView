package com.hongri.recyclerview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.adapter.DetailDiskLruCacheTestAdapter;
import com.hongri.recyclerview.common.APPConstants;
import com.hongri.recyclerview.utils.DataUtil;

/**
 * @author：zhongyao on 2016/7/27 14:40
 * @description:测试图片缓存Fragment（可与DetailLoadPicsTestFragment对比查看）
 */
public class DetailDiskLruCacheTestFragment extends Fragment {
    private static DetailDiskLruCacheTestFragment diskLruCacheTestFragment;
    private RecyclerView rv;

    private DetailDiskLruCacheTestFragment() {
    }

    public static Fragment newInstance() {
        if (diskLruCacheTestFragment == null){
            synchronized (DetailCallbackTestFragment.class){
                if (diskLruCacheTestFragment == null){
                    diskLruCacheTestFragment = new DetailDiskLruCacheTestFragment();
                }
            }
        }
        return diskLruCacheTestFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_disklrucachetest,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), APPConstants.Column_Nums));
        rv.setAdapter(new DetailDiskLruCacheTestAdapter(getActivity(), DataUtil.getImageThumbUrls()));
    }
}
