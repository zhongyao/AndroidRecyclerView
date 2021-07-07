package com.hongri.recyclerview.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.adapter.DetailPullToRefreshAdapter;
import com.hongri.recyclerview.utils.DataUtil;
import com.hongri.recyclerview.utils.ToastUtil;
import com.hongri.recyclerview.widget.CompatSwipeRefreshLayout;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/7/18 18:14
 * @description:
 */
public class DetailPullToRefreshFragment extends Fragment {
    public static DetailPullToRefreshFragment pullToRefreshFragment;
    private int position;
    private String title;
    private CompatSwipeRefreshLayout mContainer;
    private RecyclerView rv;
    private DetailPullToRefreshAdapter mAdapter;
    private static ArrayList<String> mData = new ArrayList<>();
    private int originalNums;//数组的nums
    private DetailPullToRefreshFragment() {
    }

    public static Fragment newInstance(int position, String title) {
        if (pullToRefreshFragment == null) {
            synchronized (DetailPullToRefreshFragment.class) {
                if (pullToRefreshFragment == null) {
                    pullToRefreshFragment = new DetailPullToRefreshFragment();
                }
            }
        }
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("title", title);
        pullToRefreshFragment.setArguments(bundle);
        return pullToRefreshFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        position = bundle.getInt("position", 0);
        title = bundle.getString("title", "title");

        mData = DataUtil.getData();
        originalNums = mData.size();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_pulltorefresh, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContainer = (CompatSwipeRefreshLayout) view.findViewById(R.id.mSwipeRefreshLayout);
        rv = (RecyclerView) view.findViewById(R.id.rv);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mAdapter = new DetailPullToRefreshAdapter(getActivity(), mData,originalNums);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(mAdapter);
    }

    public void refresh() {
        rv.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.ShowBottomShort(getActivity(), R.string.refresh);
                mData.add(0,"ADD-Android");
                mAdapter.notifyDataSetChanged();
                stopRefresh();
            }
        });
        return;
    }

    private void stopRefresh() {
        mContainer.setRefreshing(false);
    }
}
