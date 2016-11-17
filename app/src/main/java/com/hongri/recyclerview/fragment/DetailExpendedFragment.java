package com.hongri.recyclerview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.adapter.DetailExpendedViewAdapter;
import com.hongri.recyclerview.common.APPConstants;
import com.hongri.recyclerview.utils.DataUtil;
import com.hongri.recyclerview.utils.ToastUtil;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/7/11 16:46
 * @description:
 */
public class DetailExpendedFragment extends Fragment implements DetailExpendedViewAdapter.ItemClickListener{
    public static DetailExpendedFragment expendedFragment;
    private RecyclerView rv;
    private int position;
    private String title;
    private DetailExpendedViewAdapter mAdapter;
    private ArrayList<String> mData = new ArrayList<>();
    public static Fragment newInstance(int position, String title) {
        if (expendedFragment == null) {
            synchronized (DetailExpendedFragment.class) {
                if (expendedFragment == null) {
                    expendedFragment = new DetailExpendedFragment();
                }
            }
        }
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("title", title);
        expendedFragment.setArguments(bundle);
        return expendedFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        position = bundle.getInt("position", 0);
        title = bundle.getString("title", "title");

        mData = DataUtil.getData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_expended, container, false);
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
        if (position == APPConstants.Type_Expended_List_Text){
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else if (position == APPConstants.Type_Expended_Grid_Text){
            rv.setLayoutManager(new GridLayoutManager(getActivity(),3));
        }
        mAdapter = new DetailExpendedViewAdapter(getActivity(),mData);
        mAdapter.setmItemClickListener(this);
        rv.setAdapter(mAdapter);
    }

    @Override
    public void ItemClick(int position) {
        ToastUtil.ShowBottomShort(getActivity(),"position:"+position);
    }
}
