package com.hongri.recyclerview.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.utils.Logger;
import com.hongri.recyclerview.widget.ViewPrinciple;

public class DetailViewFragment extends Fragment {
    private LinearLayout layout;
    public ViewPrinciple viewPrinciple;
    public static DetailViewFragment fragment;

    public DetailViewFragment() {
    }

    public static DetailViewFragment newInstance(int position, String title) {
        if (fragment == null) {
            fragment = new DetailViewFragment();
        }
        return fragment;
    }

    public static DetailViewFragment newInstance() {
        if (fragment == null) {
            fragment = new DetailViewFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = (LinearLayout) view.findViewById(R.id.layout);
        viewPrinciple = (ViewPrinciple) view.findViewById(R.id.viewPrinciple);
    }

    @Override
    public void onStart() {
        super.onStart();
        /**
         * 当Activity/Fragment启动的时候，获取某个view的宽/高
         * （2）
         */
        viewPrinciple.post(new Runnable() {
            @Override
            public void run() {
                int width = viewPrinciple.getMeasuredWidth();
                int height = viewPrinciple.getMeasuredHeight();
                Logger.d("method2-post--width:"+width+" height:"+height);
            }
        });

        /**
         * 当Activity/Fragment启动的时候，获取某个view的宽/高
         * （3）
         */
        ViewTreeObserver observer = viewPrinciple.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewPrinciple.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width = viewPrinciple.getMeasuredWidth();
                int height = viewPrinciple.getMeasuredHeight();
                Logger.d("method3--ViewTreeObserver--width:"+width+" height:"+height);
            }
        });
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
