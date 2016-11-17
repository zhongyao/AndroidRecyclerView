package com.hongri.recyclerview.fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.adapter.DetailAndroidArtAdapter;
import com.hongri.recyclerview.adapter.DetailAndroidArtGridAdapter;
import com.hongri.recyclerview.utils.DataUtil;
import com.hongri.recyclerview.utils.Logger;
import com.hongri.recyclerview.utils.ToastUtil;
import com.hongri.recyclerview.widget.CustomScrollView;
import com.hongri.recyclerview.widget.CustomView;


/**
 * @author：zhongyao on 2016/9/21 14:32
 * @description:Android 开发艺术探索
 */
public class DetailAndroidArtFragment extends Fragment implements DetailAndroidArtAdapter.onItemViewClickListener {

    private static final String ANDROID_ART = "android_art";
    private static DetailAndroidArtFragment androidArtFragment;
    private RecyclerView rv;
    private Animation animation;
    private LayoutAnimationController controller;
    private Activity mActivity;
    private ScrollView scrollView;
    private LinearLayout secondPage;
    private DetailAndroidArtAdapter mAdapter;
    private MyLocalReceiver receiver;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (receiver == null){
            receiver = new MyLocalReceiver(mActivity,this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null){
            LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(receiver);
        }
    }

    /**
     * LocalBroadcast的内部机制其实也是handler
     */
    private class MyLocalReceiver extends BroadcastReceiver{
        private Context context;
        private Fragment fragment;

        public MyLocalReceiver(Context context, DetailAndroidArtFragment fragment) {
            this.context = context;
            this.fragment = fragment;

            IntentFilter filter = new IntentFilter();
            filter.addAction(ANDROID_ART);
            LocalBroadcastManager.getInstance(mActivity).registerReceiver(this,filter);

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case ANDROID_ART:
                    ToastUtil.ShowBottomShort(context,R.string.android_art);
                    break;
            }

        }
    }


    public static DetailAndroidArtFragment newInstance(int position, String title) {
        if (androidArtFragment == null) {
            androidArtFragment = new DetailAndroidArtFragment();
        }
        return androidArtFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_andorid_art,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rv);

        /**
         * 除了在XML中指定LayoutAnimation外，还可以通过LayoutAnimationController来实现
         * BEGIN
         */
//        animation = AnimationUtils.loadAnimation(mActivity,R.anim.anim_item);
//        controller = new LayoutAnimationController(animation);
//        controller.setDelay(0.5f);
//        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
//        rv.setLayoutAnimation(controller);
        /**
         * END
         */

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        secondPage = (LinearLayout) view.findViewById(R.id.secondPage);

        rv.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        mAdapter = new DetailAndroidArtAdapter(mActivity, DataUtil.getAndroidArtData());
        mAdapter.setOnItemViewClickListener(this);
        rv.setAdapter(mAdapter);
    }

    @Override
    public void itemOnClick(View view, int position) {
        ToastUtil.ShowBottomShort(mActivity,DataUtil.getAndroidArtData().get(position));
        if (position == 0){
            //Animation
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.android_art_animation, null);
            rv.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            secondPage.addView(v);

            ImageView iv_animation = (ImageView) v.findViewById(R.id.iv_animation);
            ImageView iv_frame = (ImageView) v.findViewById(R.id.iv_frame);
            TextView textView = (TextView) v.findViewById(R.id.tv);
            ImageView iv_animator = (ImageView) v.findViewById(R.id.iv_animator);

            //补间动画(View动画)
            Animation animation = AnimationUtils.loadAnimation(mActivity,R.anim.animation_view);
            iv_animation.startAnimation(animation);

            //逐帧动画
            iv_frame.setBackgroundResource(R.drawable.frame_animation);
            AnimationDrawable drawable = (AnimationDrawable) iv_frame.getBackground();
            drawable.start();

            //将一个值从0平滑过渡到1
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f,1f);
            valueAnimator.setDuration(3*1000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animationValue = (float) animation.getAnimatedValue();
                    Logger.d("值0过渡到1的值："+animationValue);
                }
            });
            valueAnimator.start();
            /**
             先进来，然后同时旋转和渐变（代码实现属性动画）
             */
            ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(textView,"alpha",1f,0f,1f);
            ObjectAnimator rotate = ObjectAnimator.ofFloat(textView,"rotation",0f,360f);
            ObjectAnimator moveIn = ObjectAnimator.ofFloat(textView,"translationX",-500f,0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(fadeInOut).with(rotate).after(moveIn);
            animatorSet.setDuration(5*1000);
            //动画监听
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Logger.d("onAnimationStart");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Logger.d("onAnimationEnd");
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Logger.d("onAnimationCancel");
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    Logger.d("onAnimationRepeat");
                }
            });
            //动画监听（简化版）
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    Logger.d("onAnimaitonEnd--AnimatorListenerAdapter()");

                }
            });
            animatorSet.start();

            //属性动画（.xml实现属性动画）
            Animator animator = AnimatorInflater.loadAnimator(mActivity,R.animator.property_animator);
            animator.setTarget(iv_animator);
            animator.start();
        }else if (position == 1){
            /**
             * Android的Drawable
             */
        //BitmapDrawable表示一张图片，开发中直接引用原始的图片即可，但是也可以通过XML的方式来描述它
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.android_art_drawable, null);
            final ImageView iv_level = (ImageView) v.findViewById(R.id.iv_level);
            ImageView iv_transition = (ImageView) v.findViewById(R.id.iv_transition);
            ImageView iv_scale = (ImageView) v.findViewById(R.id.iv_scale);
            ImageView iv_clip = (ImageView) v.findViewById(R.id.iv_clip);
            LinearLayout ll_changeColor = (LinearLayout) v.findViewById(R.id.ll_changeColor);
            final TextView tv_changeColor = (TextView) v.findViewById(R.id.tv_changeColor);
//            ll_changeColor.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    tv_changeColor.setDuplicateParentStateEnabled(true);
//                }
//            });
//            tv_changeColor.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ToastUtil.ShowBottomShort(mActivity,"Text点击了");
////                    tv_changeColor.setDuplicateParentStateEnabled(false);
//                }
//            });
            rv.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            secondPage.addView(v);
            /**
             * LevelListDrawable
             * 根据不同的level，切换不同的Drawable
             */
            iv_level.post(new Runnable() {
                @Override
                public void run() {
                    {
                        for (int i=0;i<=1;i++)
                        iv_level.setImageLevel(i);
                        try {
                            Thread.sleep(1*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            /**
             * TransitionDrawable：
             * 用于实现两个Drawable之间的淡入淡出效果
             */
            TransitionDrawable drawable = (TransitionDrawable) getResources().getDrawable(R.drawable.transition_drawable);
            iv_transition.setImageDrawable(drawable);
            drawable.startTransition(3*1000);

            /**
             * ScaleDrawable:
             * 将Drawable缩放一定的比例
             */
            ScaleDrawable scaleDrawable = (ScaleDrawable) iv_scale.getBackground();
            scaleDrawable.setLevel(1);

            /**
             * ClipDrawable:
             * 对Drawable进行裁剪(等级从0~10000，0表示完全裁剪)
             */
            ClipDrawable clipDrawable = (ClipDrawable) iv_clip.getDrawable();
            clipDrawable.setLevel(5000);
        }else if (position == 2) {
            View v = LayoutInflater.from(mActivity).inflate(R.layout.andorid_art_gridview,null,false);
            GridView gridview = (GridView) v.findViewById(R.id.gridview);
            rv.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            secondPage.addView(v);
            DetailAndroidArtGridAdapter adapter = new DetailAndroidArtGridAdapter(mActivity,DataUtil.getHomeData());
            gridview.setAdapter(adapter);
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    ToastUtil.ShowBottomShort(mActivity,"ItemClick点击"+position);
                    Logger.d("gridItemClick0");
                }
            });

        }else if (position == 3) {
            View v = LayoutInflater.from(mActivity).inflate(R.layout.android_art_view,null,false);
            RelativeLayout ll = (RelativeLayout) v.findViewById(R.id.ll);
            CustomView customView = (CustomView) v.findViewById(R.id.customView);
            Button btn = (Button) v.findViewById(R.id.btn);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) btn.getLayoutParams();
            params.width +=500;
            params.leftMargin +=300;
            btn.requestLayout();
            btn.setLayoutParams(params);
            rv.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            secondPage.addView(v);
        }else if (position == 5){
            LocalBroadcastManager.getInstance(mActivity).sendBroadcast(new Intent(ANDROID_ART));
        }else if (position == 4){
            View v = LayoutInflater.from(mActivity).inflate(R.layout.android_art_scroll_view,null,false);
            CustomScrollView customScrollView = (CustomScrollView) v.findViewById(R.id.customScrollView);
            rv.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            secondPage.addView(v);
            customScrollView.invalidate();
        }
    }
}
