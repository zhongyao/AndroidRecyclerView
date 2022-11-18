package com.hongri.recyclerview.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.speech.RecognizerIntent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.activity.MainActivity;
import com.hongri.recyclerview.activity.ShareTestActivity;
import com.hongri.recyclerview.badge.BadgeClient;
import com.hongri.recyclerview.cache.CacheClearManager;
import com.hongri.recyclerview.cache.ImageWorker;
import com.hongri.recyclerview.threadpool.ThreadPoolTester;
import com.hongri.recyclerview.utils.APPUtils;
import com.hongri.recyclerview.utils.CommonNumberUtil;
import com.hongri.recyclerview.utils.CustomToast;
import com.hongri.recyclerview.utils.DisplayUtil;
import com.hongri.recyclerview.utils.PreloadImageUtil;
import com.hongri.recyclerview.utils.SoftKeyboardUtil;
import com.hongri.recyclerview.utils.TimeCountDown;
import com.hongri.recyclerview.utils.ToastUtil;
import com.hongri.recyclerview.widget.AlxTextView;
import com.hongri.recyclerview.widget.MyEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author：zhongyao on 2016/8/3 17:41
 * @description:
 */
public class SettingFragment extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {
    private static final String TAG = SettingFragment.class.getSimpleName();
    private static SettingFragment settingFragment;
    private static boolean hasClicked = false;
    private Activity mActivity;
    private Vibrator vibrator;
    private Button speechRecognizer;
    private MyEditText editText;
    private NestedScrollView nestedScrollView;
    private LinearLayout ll_clearCache, ll_convert;
    private Button vibrate, execApp;
    private Button btn_open_new_fragment;
    private EditText editTextKeyboard;
    private Button testKB;
    private Button badgeBtn;
    private Button threadExecutorBtn;
    private LinearLayout layoutAnim;
    private Button viewAnim;
    private AlphaAnimation animation;
    private LinearLayout layoutDrawable;
    private ImageView first, second;
    private Button flavorProduct;
    private Button exceptionBtn;
    private Button timer;
    private TextView tvTimer;
    private TextView textH, textContent;
    private TextView textW;
    private TextView tvToast;
    private TextView tvShare;
    private AppCompatTextView tvAutoSize;
    private TextView tvAutoSize2;
    private TextView tvSpanColor;
    private TextView tvNumberDispose;
    private ImageView preloadImageView;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private String content = "关关雎鸠，在河之洲，窈窕淑女，君子好逑";
    private CustomToast toast;
    //是否从后台回来
    private boolean comeFromBackground;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        mActivity = getActivity();
    }

    public SettingFragment() {
    }

    public static SettingFragment getInstance() {
        if (settingFragment == null) {
            synchronized (SettingFragment.class) {
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                }
            }
        }
        hasClicked = false;
        return settingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        initVibrate();
    }

    private void initRecognizer() {
        PackageManager pm = mActivity.getPackageManager();
        List activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0); // 本地识别程序
        // new Intent(RecognizerIntent.ACTION_WEB_SEARCH), 0); // 网络识别程序

        /*
         * 此处没有使用捕捉异常，而是检测是否有语音识别程序。
         * 也可以在startRecognizerActivity()方法中捕捉ActivityNotFoundException异常
         */
        if (activities.size() != 0) {
            speechRecognizer.setOnClickListener(this);
        } else {
            // 若检测不到语音识别程序在本机安装，测将扭铵置灰
            speechRecognizer.setEnabled(false);
            speechRecognizer.setText("未检测到语音识别设备");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "hidden:" + hidden);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");

//        initRecognizer();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        //滑到底部
        nestedScrollView.fullScroll(View.FOCUS_DOWN);

        if (comeFromBackground) {
            PreloadImageUtil.preloadImageAndShow(getActivity(), preloadImageView, PreloadImageUtil.IMAGE_URL);
            comeFromBackground = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        comeFromBackground = true;
        PreloadImageUtil.preloadImage(getActivity(), PreloadImageUtil.IMAGE_URL);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        mActivity = null;
    }

    private View initView(View view) {
        nestedScrollView = view.findViewById(R.id.nested_scroll_view);
        ll_clearCache = (LinearLayout) view.findViewById(R.id.ll_clearCache);
        ll_convert = (LinearLayout) view.findViewById(R.id.ll_convert);
        vibrate = (Button) view.findViewById(R.id.vibrate);
        speechRecognizer = (Button) view.findViewById(R.id.speechRecognizer);
        editText = view.findViewById(R.id.editText);
        execApp = view.findViewById(R.id.btn_exec);
        btn_open_new_fragment = view.findViewById(R.id.btn_open_new_fragment);
        editTextKeyboard = view.findViewById(R.id.editTextKeyboard);
        testKB = view.findViewById(R.id.testKB);

        badgeBtn = view.findViewById(R.id.badgeBtn);
        threadExecutorBtn = view.findViewById(R.id.threadExecutorBtn);
        layoutAnim = view.findViewById(R.id.layoutAnim);
        viewAnim = view.findViewById(R.id.viewAnim);
        animation = new AlphaAnimation(0, 1);
        animation.setDuration(2000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
        viewAnim.startAnimation(animation);

        layoutDrawable = view.findViewById(R.id.layoutDrawable);
        first = view.findViewById(R.id.first);
        second = view.findViewById(R.id.second);
        flavorProduct = view.findViewById(R.id.flavorProduct);
        exceptionBtn = view.findViewById(R.id.exceptionBtn);
        timer = view.findViewById(R.id.timer);
        tvTimer = view.findViewById(R.id.tvTimer);
        textH = view.findViewById(R.id.textH);
        textW = view.findViewById(R.id.textW);
        textContent = view.findViewById(R.id.textContent);
        tvToast = view.findViewById(R.id.tvToast);
        tvShare = view.findViewById(R.id.tvShare);
        tvAutoSize = view.findViewById(R.id.tvAutoSize);
        tvAutoSize2 = view.findViewById(R.id.tvAutoSize2);
        tvSpanColor = view.findViewById(R.id.tvSpanColor);
        tvNumberDispose = view.findViewById(R.id.tvNumberDispose);
        preloadImageView = view.findViewById(R.id.pre_img);


        ll_clearCache.setOnClickListener(this);
        ll_convert.setOnClickListener(this);
        vibrate.setOnClickListener(this);
        speechRecognizer.setOnClickListener(this);
        editText.setOnClickListener(this);
        execApp.setOnClickListener(this);
        btn_open_new_fragment.setOnClickListener(this);
        editTextKeyboard.setOnClickListener(this);
        editTextKeyboard.setOnEditorActionListener(this);
        testKB.setOnClickListener(this);
        badgeBtn.setOnClickListener(this);
        threadExecutorBtn.setOnClickListener(this);
        layoutAnim.setOnClickListener(this);
        viewAnim.setOnClickListener(this);
        layoutDrawable.setOnClickListener(this);
        flavorProduct.setOnClickListener(this);
        exceptionBtn.setOnClickListener(this);
        timer.setOnClickListener(this);
        textH.setOnClickListener(this);
        textW.setOnClickListener(this);
        tvToast.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        tvAutoSize.setOnClickListener(this);
        tvAutoSize2.setOnClickListener(this);

        String text = "注册领取{20CNY}奖励";
        tvSpanColor.setText(DisplayUtil.getFinalTextSpan(getContext(), text, R.color.cardview_dark_background, R.color.red, true));

        editText.setText("00");

        String num = "3748.59482";
        //精度
        int precision = 4;
        String finalNum = CommonNumberUtil.getSafeHandleDec(CommonNumberUtil.formatNum(num, precision), precision);
        tvNumberDispose.setText("数字通用处理:" + finalNum);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 缓存清理
             */
            case R.id.ll_clearCache:
//                CacheClearManager.getInstance().clearCache();
                if (!hasClicked) {
                    hasClicked = true;
                    CacheClearManager.getInstance().cleanApplicationData(getActivity(), ImageWorker.bitmapPath);
                    ToastUtil.ShowBottomShort(getActivity(), R.string.clearCache);
                }
                break;
            /**
             * 多屏适配调研
             */
            case R.id.ll_convert:
                APPUtils.getPhoneInfo(getActivity());
//                int dp = DisplayUtil.px2dip(getActivity(),24);
//                int px = DisplayUtil.sp2px(getActivity(),13);
//                ToastUtil.ShowBottomLong(getActivity(),"24px="+dp+"dp"+"  13sp="+px+"px");
                break;
            /**
             * 震动
             */
            case R.id.vibrate:
                startVibrate();
                break;
            /**
             * 语音识别
             */
            case R.id.speechRecognizer:
                //开始识别
                startRecognizerActivity();
                break;
            case R.id.editText:
                editTextTest();
                break;
            case R.id.btn_exec:
                execTest();
                break;

            case R.id.btn_open_new_fragment:
                openNewFragment();
                break;

            case R.id.editTextKeyboard:
                onEditTextClick();
                break;
            case R.id.testKB:
                boolean isSoftShowing = SoftKeyboardUtil.isSoftShowing(getActivity());
                Log.d(TAG, "isSoftShowing:" + isSoftShowing);
                if (isSoftShowing) {
                    onTestKeyBoard();
                }
                break;
            case R.id.badgeBtn:
                badgeShow();
                break;
            case R.id.threadExecutorBtn:
                ThreadPoolTester threadPoolTester = new ThreadPoolTester();
                threadPoolTester.testCustomerExecutorException();
                break;
            case R.id.viewAnim:
                viewAnim.clearAnimation();
                layoutAnim.removeView(viewAnim);
                break;
            case R.id.layoutDrawable:
                BitmapDrawable firstDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.welcome);
                BitmapDrawable secondDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.welcome);

                //两个Drawable采用的是同一个ConstantState 即BitmapState。
                Log.d(TAG, "fistDrawable:" + firstDrawable.getConstantState() + " secondDrawable:" + secondDrawable.getConstantState());
//                first.setImageDrawable(firstDrawable);
//                second.setImageDrawable(secondDrawable);

                //两个drawable均会变透明
//                secondDrawable.setAlpha(150);
                //只有第二个drawable会变透明
//                secondDrawable.mutate().setAlpha(150);

                //也能正常显示【虽然公用同一个ConstantState，但是Drawable.mBounds不同会进行重绘，所以也是显示正常的】
                first.setImageDrawable(firstDrawable);
                second.setImageDrawable(firstDrawable.getConstantState().newDrawable());

                break;
            case R.id.flavorProduct:
                if (mActivity == null) {
                    return;
                }
                ApplicationInfo appInfo = null;
                try {
                    appInfo = mActivity.getPackageManager().getApplicationInfo(mActivity.getPackageName(), PackageManager.GET_META_DATA);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                String channelName;
                if (appInfo != null) {
                    channelName = appInfo.metaData.getString("APP_STORE_CHANNEL");
                } else {
                    channelName = "default";
                }
                Log.d(TAG, "channelName:" + channelName);
                break;
            case R.id.exceptionBtn:
                String a = null;
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (a.equals("")) {
                                    Log.e("TAG", "it is right, no exception");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("TAG", "e-inner:" + e);
                            }
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "e-outer:" + e);
                }

                break;
            case R.id.timer:
                String[] strings = new String[]{"你好", "我好", "大家好", "同志们好", "乡亲们好"};
                TimeCountDown timeCountDown = new TimeCountDown(strings.length * 1000, 300);
                timeCountDown.setListener(new TimeCountDown.ICountListener() {
                    int count = 0;
                    StringBuilder builder = new StringBuilder();

                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (count < 0 || count >= strings.length) {
                            return;
                        }
                        builder.append(strings[count]);
                        tvTimer.setText(builder.toString());
                        count++;
                    }

                    @Override
                    public void onFinish() {
                        tvTimer.setText("结束");
                    }
                });
                timeCountDown.start();
                break;
            case R.id.textH:
                AlxTextView alxTextView = new AlxTextView(getActivity(), content, 200, 15);
                textContent.setText(content);
                textContent.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "inner --> height:" + textContent.getHeight());
                    }
                }, 500);

                int[] measureResult = alxTextView.measureTextViewHeight(textContent, content, 210, 100);
                Log.d(TAG, "measureHeight:" + measureResult[1]);
                break;

            case R.id.textW:
                textW.setTextSize(30);
                float contentWidth = AlxTextView.getTextSize(textW, content);
                Log.d(TAG, "contentWidth:" + contentWidth);
                textW.setText(content);
                break;
            case R.id.tvToast:

//                CustomToast customToast = new CustomToast(getActivity(), getActivity().findViewById(R.id.toast_custom_parent));
//                customToast.show(content, 10000);
                toastMessage(content);
                break;
            case R.id.tvShare:
                Intent intent = new Intent(getActivity(), ShareTestActivity.class);
                startActivity(intent);
                break;

            case R.id.tvAutoSize:
                String textStr = "文本文字大小自适应哈哈哈呵呵呵嘿嘿嘿";
                //1、获取文本属性值
                float preTextSize = tvAutoSize.getTextSize();
                int maxTextSize = tvAutoSize.getAutoSizeMaxTextSize();
                int minTextSize = tvAutoSize.getAutoSizeMinTextSize();
                int autoStep = tvAutoSize.getAutoSizeStepGranularity();
                int textType = tvAutoSize.getAutoSizeTextType();
                int width = tvAutoSize.getWidth();
                int height = tvAutoSize.getHeight();
                int maxWidth = tvAutoSize.getMaxWidth();

                Log.d(TAG, "preTextSize:" + preTextSize);
                Log.d(TAG, "maxTextSize:" + maxTextSize);
                Log.d(TAG, "minTextSize:" + minTextSize);
                Log.d(TAG, "autoStep:" + autoStep);
                Log.d(TAG, "textType:" + textType);
                Log.d(TAG, "width:" + width + " height:" + height + " maxWidth:" + maxWidth);
                //2、计算新文本需要占用的宽度

                TextPaint textPaint = tvAutoSize.getPaint();
                float textTotalPaintWidth = textPaint.measureText(textStr);
                Log.d(TAG, "textTotalPaintWidth: " + textTotalPaintWidth);
                if (textTotalPaintWidth >= maxWidth) {
                    tvAutoSize.setWidth(maxWidth);
                } else {
                    tvAutoSize.setWidth((int) textTotalPaintWidth);
                }
                tvAutoSize.setText(textStr);
                break;

            case R.id.tvAutoSize2:
                tvAutoSize2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int lineCount = tvAutoSize2.getLineCount();
                        int textSizeInDip = DisplayUtil.px2dip(requireContext(), tvAutoSize2.getTextSize());

                        //当行数大于1行 & 且字体大于10dp时，缩小字体。直至行数小于1或字体大小小于等于10dp时终止。此后设置lineCount为1
//                        while (lineCount > 1 && textSizeInDip > 10) {
//                            tvAutoSize2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, --textSizeInDip);
////                            lineCount = tvAutoSize2.getLineCount();
//                        }
//                        int maxWidth = tvAutoSize2.getMaxWidth();
                        int maxWidth = DisplayUtil.dip2px(requireContext(), 100);
                        int width = tvAutoSize2.getWidth();

                        Log.d(TAG, "maxWidth:" + maxWidth + " width:" + width);

                        while (width > maxWidth && textSizeInDip > 6) {
                            tvAutoSize2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, --textSizeInDip);
                            width = tvAutoSize2.getWidth();
                            Log.d(TAG, "## -> maxWidth:" + maxWidth + " width:" + width + " textSizeInDip:" + textSizeInDip);
                        }


//                        if (lineCount > 1) {
//                            tvAutoSize2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
//                        } else {
//                            tvAutoSize2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
//                        }
                        tvAutoSize2.setMaxLines(1);
                        tvAutoSize2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
                tvAutoSize2.setText("红红火火恍恍惚惚或或或或或或或或或或或或或或或呵呵呵呵");
                break;

            default:
                break;
        }
    }

    private void toastMessage(String content) {
        if (toast != null) {
            toast.hide();
        }
        toast = new CustomToast(getActivity(),
                (ViewGroup) getActivity().findViewById(R.id.toast_custom_parent));
        toast.show(content, 30000);
    }

    /**
     * 测试软件盘[显/隐]
     */
    private void onTestKeyBoard() {
        //隐藏软键盘---不丢失焦点
        List<View> listViews = new ArrayList<>();
        listViews.add(editTextKeyboard);
        SoftKeyboardUtil.hideSoftKeyboard(getContext(), listViews);
    }

    /**
     * 角标测试
     */
    private void badgeShow() {
        BadgeClient.setBadgeCount(getActivity(), MainActivity.class, 2);
    }

    private void onEditTextClick() {

    }

    private void openNewFragment() {
        if (openCallback != null) {
            openCallback.openNewFragment();
        }

    }

    /**
     * Runtime.getRuntime().exec()方法
     */
    private void execTest() {
        try {
            //关闭其他应用
            Process exec = Runtime.getRuntime().exec("am force-stop com.tencent.mm");

            //打开其他应用
//            Process exec = Runtime.getRuntime().exec("am start -n 包名/启动类名称");

            if (exec.waitFor() == 0) {
                Log.d(TAG, "执行成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * EditText事件监听
     */
    private void editTextTest() {
        editText.setOnPasteCallback(new MyEditText.OnPasteCallback() {
            @Override
            public void onCut() {
                Log.d(TAG, "onCut");
            }

            @Override
            public void onCopy() {
                Log.d(TAG, "onCopy");
            }

            @Override
            public void onPaste() {
                Log.d(TAG, "onPaste");
            }

            /**
             * 键盘点击事件捕获
             * @param v
             * @param actionId
             * @param event
             */
            @Override
            public void onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d(TAG, "onEditorAction:" + event.getAction());
            }
        });
    }

    private void startRecognizerActivity() {
        try {
            //通过Intent传递语音识别的模式，开启语音
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            //语言模式和自由模式的语音识别
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            //提示语音开始
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speak now");
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
            mActivity.startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            ToastUtil.ShowBottomShort(mActivity, "未检测到语音设备...");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == mActivity.RESULT_OK) {
            ArrayList<String> results;
            results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String resultString = "";
            for (int i = 0; i < results.size(); i++) {
                resultString += results.get(i);
            }
            ToastUtil.ShowBottomLong(mActivity, resultString);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void stopVibrate() {
        vibrator.cancel();
    }

    private void startVibrate() {
        //震动1s
        vibrator.vibrate(1000);
    }

    private void initVibrate() {
        String vibratorService = Context.VIBRATOR_SERVICE;
        vibrator = (Vibrator) mActivity.getSystemService(vibratorService);
        //使用震动方式
//       long[] pattern = {1000,2000,4000,8000,16000};
//       vibrator.vibrate(pattern,0);
    }

    private static OpenCallback openCallback;

    public static void setOpenCallback(OpenCallback callback) {
        openCallback = callback;
    }

    /**
     * 输入中文时点击回车键，此方法响应
     * 输入英文时点击回车键，此方法不响应。
     * 待调研~
     *
     * @param v
     * @param actionId
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.d(TAG, "onEditorAction");
        if (event != null) {
            Log.d(TAG, "actionId:" + actionId + " event:" + event.getAction());
        }
        return true;
    }

    public interface OpenCallback {
        void openNewFragment();
    }
}
