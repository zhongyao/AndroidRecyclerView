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
import com.hongri.recyclerview.adapter.DetailVolleyTestAdapter;
import com.hongri.recyclerview.utils.DataUtil;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/7/12 18:06
 * @description:使用volley框架加载网络图片的性能测试
 */
public class DetailVolleyTestFragment extends Fragment{
    private static DetailVolleyTestFragment detailVolleyTestFragment;
    private int position;
    private String title;
    private RecyclerView rv;
    private DetailVolleyTestAdapter mAdapter;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        position = bundle.getInt("position");
        title = bundle.getString("title");

        mImageUrls = DataUtil.getImageThumbUrls();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_volleytest,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = (RecyclerView) view.findViewById(R.id.rv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new DetailVolleyTestAdapter(getActivity(),mImageUrls);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),3));
        rv.setAdapter(mAdapter);
    }

    public static Fragment newInstance(int position, String title) {
        if (detailVolleyTestFragment == null){
            synchronized (DetailVolleyTestFragment.class){
                if (detailVolleyTestFragment == null){
                    detailVolleyTestFragment = new DetailVolleyTestFragment();
                }
            }
        }
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putString("title",title);
        detailVolleyTestFragment.setArguments(bundle);
        return detailVolleyTestFragment;
    }
}
