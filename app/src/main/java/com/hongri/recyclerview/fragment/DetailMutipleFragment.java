package com.hongri.recyclerview.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.adapter.DetailMultipleViewAdapter;
import com.hongri.recyclerview.adapter.DetailNormalViewAdapter;
import com.hongri.recyclerview.common.APPConstants;
import com.hongri.recyclerview.utils.DataUtil;
import com.hongri.recyclerview.utils.ToastUtil;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/7/7 17:32
 * @description:七七事变，勿忘国耻~~
 */
public class DetailMutipleFragment extends Fragment implements DetailMultipleViewAdapter.MultipleItemClickListener {
    public static DetailMutipleFragment mutipleFragment;
    public RecyclerView rv;
    public DetailMultipleViewAdapter adapter;

    private ArrayList<String> mData = new ArrayList<>();
    private ArrayList<Integer> mImageData = new ArrayList<>();
    private DetailMutipleFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getArguments().getString("title");
        int position = getArguments().getInt("position",3);
        if (position == 3){
            APPConstants.complicatedList = false;
        }else if(position == 4){
            APPConstants.complicatedList = true;
        }
        mData = DataUtil.getData();
        mImageData = DataUtil.getImageData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_mutiple, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = (RecyclerView) view.findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DetailMultipleViewAdapter(getActivity(), mData,mImageData,APPConstants.complicatedList);
        adapter.setMultipleItemClickListener(this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public static Fragment newInstance(int position, String title) {
        if (mutipleFragment == null) {
            synchronized (DetailMutipleFragment.class) {
                if (mutipleFragment == null) {
                    mutipleFragment = new DetailMutipleFragment();
                }
            }
        }
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putString("title",title);
        mutipleFragment.setArguments(bundle);

        return mutipleFragment;
    }

    @Override
    public void onItemClick(int position) {
        ToastUtil.ShowBottomShort(getActivity(), "Click-position:" + position);
    }

    @Override
    public void onItemLongClick(int position) {
        ToastUtil.ShowBottomShort(getActivity(), "longClick-position:" + position);
    }
}
