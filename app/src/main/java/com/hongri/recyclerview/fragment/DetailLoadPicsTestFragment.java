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
import com.hongri.recyclerview.adapter.DetailLoadPicsTestAdapter;
import com.hongri.recyclerview.common.APPConstants;
import com.hongri.recyclerview.utils.DataUtil;

/**
 * @author：zhongyao on 2016/7/22 10:52
 * @description:网络加载图片性能测试
 */
public class DetailLoadPicsTestFragment extends Fragment{
    private static DetailLoadPicsTestFragment loadPicsTestFragment;
    private RecyclerView rv;
    private DetailLoadPicsTestAdapter mAdapter;
    private DetailLoadPicsTestFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_loadpicstest,container,false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), APPConstants.Column_Nums));
        mAdapter = new DetailLoadPicsTestAdapter(getActivity(), DataUtil.getImageThumbUrls());
        rv.setAdapter(mAdapter);
    }


    public static Fragment newInstance() {
        if (loadPicsTestFragment == null){
            synchronized (DetailLoadPicsTestFragment.class){
                if (loadPicsTestFragment == null){
                    loadPicsTestFragment = new DetailLoadPicsTestFragment();
                }
            }
        }
        return loadPicsTestFragment;
    }
}
