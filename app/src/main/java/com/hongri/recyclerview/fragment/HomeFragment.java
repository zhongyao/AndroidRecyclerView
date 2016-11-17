package com.hongri.recyclerview.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.activity.DetailActivity;
import com.hongri.recyclerview.adapter.HomeViewAdapter;
import com.hongri.recyclerview.utils.DataUtil;
import com.hongri.recyclerview.utils.Logger;
import com.hongri.recyclerview.utils.ToastUtil;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/6/29 15:52
 * @description:
 */
public class HomeFragment extends Fragment implements HomeViewAdapter.HomeViewItemClickListener{
    private RecyclerView mRecyclerView;
    private HomeViewAdapter mAdapter;
    private static HomeFragment homeFragment = null;
    private ArrayList<String> mData = new ArrayList<>();


    /**
     * 单例模式--双重检查锁定
     * @return
     */
    public static HomeFragment getInstance(){
        if (homeFragment == null){
            synchronized (HomeFragment.class){
                if (homeFragment == null){
                    homeFragment = new HomeFragment();
                }
            }
        }
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("HomeFragment--oncreate()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.d("HomeFragment--onCreateView()");
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d("HomeFragment--onViewCreated()");

        init(view);

        //获取数据
        mData = DataUtil.getHomeData();
    }

    private void init(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d("HomeFragment--onActivityCreated()");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        if (APPUtils.isLandscape(getActivity())){
//            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),8));
//        }else{
//            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
//        }
        mAdapter = new HomeViewAdapter(getActivity(),mData);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        //滑动到RecyclerView的最底部
        mRecyclerView.scrollToPosition(mData.size()-3);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onItemClick(View v,int position) {

        ToastUtil.ShowBottomShort(getActivity(),mData.get(position));

        Intent intent = new Intent(getActivity(),DetailActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("title",mData.get(position));
        startActivity(intent);

    }

    @Override
    public void onItemLongClick(View view, int position) {
        ToastUtil.ShowBottomShort(getActivity(),"longClick:"+mData.get(position));
    }

}
