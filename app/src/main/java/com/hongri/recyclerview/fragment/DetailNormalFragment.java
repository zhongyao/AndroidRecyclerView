package com.hongri.recyclerview.fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.adapter.DetailNormalViewAdapter;
import com.hongri.recyclerview.common.APPConstants;
import com.hongri.recyclerview.utils.DataUtil;
import com.hongri.recyclerview.utils.Logger;
import com.hongri.recyclerview.utils.ToastUtil;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/6/30 17:01
 * @description:基础的RecyclerView的使用
 */
public class DetailNormalFragment extends Fragment implements DetailNormalViewAdapter.DetailNormalItemClickListener {

    public static DetailNormalFragment detailNormalFragment = null;
    private int position;
    private String title;
    private FragmentActivity mActivity;
    private RecyclerView mRecyclerView;
    private DetailNormalViewAdapter detailNormalViewAdapter;
    private ArrayList<String> mData = new ArrayList<>();
    private GridLayoutManager mLayoutManager;

    private DetailNormalFragment() {

    }

    /**
     * 使用单例模式，只生成一个实例
     * @param position
     * @param title
     * @return
     */
    public static DetailNormalFragment newInstance(int position, String title) {
        if (detailNormalFragment == null) {
            synchronized (DetailNormalFragment.class) {
                if (detailNormalFragment == null) {
                    detailNormalFragment = new DetailNormalFragment();
                }
            }
        }
        //获取传递过来的参数
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("title", title);
        detailNormalFragment.setArguments(bundle);
        return detailNormalFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        Logger.d("DetailNormalFragment--onCreate()");
        Bundle bundle = getArguments();
        position = bundle.getInt("position", APPConstants.Type_List_Layout);
        title = bundle.getString("title", APPConstants.Detail_Fragment_Title_Default);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.d("DetailNormalFragment--onCreateView()");
        View view = inflater.inflate(R.layout.fragment_detail_normal, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d("DetailNormalFragment--onViewCreated()");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d("DetailNormalFragment--onActivityCreated()");
        mData = DataUtil.getData();

        if (position == APPConstants.Type_List_Layout) {
            APPConstants.type = position;

//            ListView:PORTRAIT
          mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

            //ListView:HORIZONTAL
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
        } else if (position == APPConstants.Type_Grid_Layout) {
            APPConstants.type = position;
            mLayoutManager = new GridLayoutManager(mActivity, APPConstants.Column_Nums);
            /**
             * Column_Nums为3（可以看做一行分为三份列空间），返回1就表示该position占有一个列空间，返回2则表示该position占有两个列空间
             */
            mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position % 2 == 0) {
                        return 1;
                    } else {
                        return 2;
                    }
                }
            });
            mRecyclerView.setLayoutManager(mLayoutManager);


        } else if (position == APPConstants.Type_Staggered_Layout) {
            APPConstants.type = position;
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(APPConstants.Column_Nums, OrientationHelper.VERTICAL));
        } else {

        }
        detailNormalViewAdapter = new DetailNormalViewAdapter(mActivity, mData);
        detailNormalViewAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(detailNormalViewAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onItemClick(View v, int position) {
        ToastUtil.ShowBottomShort(mActivity, mData.get(position)+".");
    }
}
