package com.hongri.recyclerview.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.cache.CacheClearManager;
import com.hongri.recyclerview.cache.ImageWorker;
import com.hongri.recyclerview.utils.APPUtils;
import com.hongri.recyclerview.utils.DisplayUtil;
import com.hongri.recyclerview.utils.ToastUtil;
import com.hongri.recyclerview.widget.MyEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author：zhongyao on 2016/8/3 17:41
 * @description:
 */
public class SettingFragement extends Fragment implements View.OnClickListener {
    private static final String TAG = SettingFragement.class.getSimpleName();
    private static SettingFragement settingFragement;
    private static boolean hasClicked = false;
    private Activity mActivity;
    private Vibrator vibrator;
    private Button speechRecognizer;
    private MyEditText editText;
    private LinearLayout ll_clearCache, ll_convert;
    private Button vibrate, execApp;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

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

    public SettingFragement() {
    }

    public static SettingFragement getInstacne() {
        if (settingFragement == null) {
            synchronized (SettingFragement.class) {
                if (settingFragement == null) {
                    settingFragement = new SettingFragement();
                }
            }
        }
        hasClicked = false;
        return settingFragement;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVibrate();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        initRecognizer();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return initView(view);
    }

    private View initView(View view) {
        ll_clearCache = (LinearLayout) view.findViewById(R.id.ll_clearCache);
        ll_convert = (LinearLayout) view.findViewById(R.id.ll_convert);
        vibrate = (Button) view.findViewById(R.id.vibrate);
        speechRecognizer = (Button) view.findViewById(R.id.speechRecognizer);
        editText = view.findViewById(R.id.editText);
        execApp = view.findViewById(R.id.btn_exec);


        ll_clearCache.setOnClickListener(this);
        ll_convert.setOnClickListener(this);
        vibrate.setOnClickListener(this);
        speechRecognizer.setOnClickListener(this);
        editText.setOnClickListener(this);
        execApp.setOnClickListener(this);

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
            default:
                break;
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
}
