package com.hongri.recyclerview.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringChain;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.hongri.recyclerview.R;
import com.hongri.recyclerview.adapter.DetailReboundAdapter;
import com.hongri.recyclerview.utils.Logger;
import com.hongri.recyclerview.widget.ViewPrinciple;

import java.util.List;

public class DetailReboundFragment extends Fragment {
    private LinearLayout layout;
    public ViewPrinciple viewPrinciple;
    public static DetailReboundFragment fragment;

    public DetailReboundFragment() {
    }

    public static DetailReboundFragment newInstance(int position, String title) {
        if (fragment == null) {
            fragment = new DetailReboundFragment();
        }
        return fragment;
    }

    public static DetailReboundFragment newInstance() {
        if (fragment == null) {
            fragment = new DetailReboundFragment();
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
        return inflater.inflate(R.layout.android_art_rebound, container, false);
    }

//    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);


        final ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        layout = (LinearLayout) v.findViewById(R.id.layout);
        HorizontalScrollView scrollView = (HorizontalScrollView) v.findViewById(R.id.scrollView);
        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpringSystem springSystem = SpringSystem.create();
                Spring spring = springSystem.createSpring();
                spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(100, 1));
                spring.addListener(new SimpleSpringListener() {
                    @Override
                    public void onSpringUpdate(Spring spring) {
//                          super.onSpringUpdate(spring);
                        float value = (float) spring.getCurrentValue();
                        float scale = 1f - (value * 0.5f);
                        imageView.setScaleX(scale);
                        imageView.setScaleY(scale);
                    }
                });
                spring.setEndValue(1);
            }
        });

//        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Logger.d();
//            }
//        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Logger.d("onScrollChanged--horizontal");
//                    springAnim();
//                    if ()
                    int maxScroll = layout.getChildAt(0).getMeasuredWidth() - layout.getMeasuredWidth();
                    //滑动最左
                    if (layout.getScrollX() == 0){
                        Logger.d("left");
                    }else if (layout.getScrollX() == maxScroll) {
                        //最后
                        Logger.d("right");
                    }{

                    }
                }
            });

        }

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        springAnim();

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                springAnim();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        DetailReboundAdapter reboundAdapter = new DetailReboundAdapter(getActivity());
        recyclerView.setAdapter(reboundAdapter);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpringChain springChain = SpringChain.create(40, 6, 50, 7);
                int viewCount = recyclerView.getChildCount();
                for (int i = 0; i < viewCount; i++) {
                    final View viewChild = recyclerView.getChildAt(i);
                    springChain.addSpring(new SimpleSpringListener() {
                        @Override
                        public void onSpringUpdate(Spring spring) {
                            super.onSpringUpdate(spring);
                            viewChild.setTranslationX((float) spring.getCurrentValue());
                        }
                    });
                }

                List<Spring> springs = springChain.getAllSprings();

                for (int i = 0; i < springs.size(); i++) {
                    springs.get(i).setCurrentValue(400);
                }

                springChain.setControlSpringIndex(2).getControlSpring().setEndValue(0);
            }
        });

    }


    private void springAnim() {
        SpringChain springChain = SpringChain.create(40, 6, 50, 7);
        int viewCount = layout.getChildCount();
        for (int i = 0; i < viewCount; i++) {
            final View viewChild = layout.getChildAt(i);
            springChain.addSpring(new SimpleSpringListener() {
                @Override
                public void onSpringUpdate(Spring spring) {
//                              super.onSpringUpdate(spring);
                    viewChild.setTranslationX((float) spring.getCurrentValue());

                }
            });
        }

        List<Spring> springs = springChain.getAllSprings();
        for (int i = 0; i < springs.size(); i++) {
            springs.get(i).setCurrentValue(400);
        }
        springChain.setControlSpringIndex(2).getControlSpring().setEndValue(0);

    }
}