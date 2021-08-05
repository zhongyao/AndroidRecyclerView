package com.hongri.recyclerview.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;

import java.text.NumberFormat;

/**
 * @author：zhongyao on 2016/6/29 16:17
 * @description:工具类
 */
public class APPUtils {

    /**
     * @return 是否是 MI PAD
     */
    public static boolean isMiPad() {
        return !TextUtils.isEmpty(android.os.Build.MODEL) && android.os.Build.MODEL.contains("MI PAD");
    }

    /**
     * 不含7寸pad。如果要所有的pad。
     *
     * @return
     */
    public static boolean isPad(Context context) {
        return Configuration.ORIENTATION_LANDSCAPE == getDeviceDefaultOrientation(context)
                || isMiPad();
    }

    /**
     * 获取设备默认的Orientation
     *
     * @param context
     * @return
     */
    private static int DEFAULT_ORIENTATION;

    public static int getDeviceDefaultOrientation(Context context) {
        if (0 == DEFAULT_ORIENTATION) {
            WindowManager windowManager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            Configuration config = context.getResources().getConfiguration();
            int rotation = windowManager.getDefaultDisplay().getRotation();
            if (((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) && config.orientation == Configuration.ORIENTATION_LANDSCAPE)
                    || ((rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) && config.orientation == Configuration.ORIENTATION_PORTRAIT)) {
                DEFAULT_ORIENTATION = Configuration.ORIENTATION_LANDSCAPE;
            } else {
                DEFAULT_ORIENTATION = Configuration.ORIENTATION_PORTRAIT;
            }
        }
        return DEFAULT_ORIENTATION;
    }

    /**
     * Return 是否是横屏
     */
    public static Boolean isLandscape(Context c) {
        return c.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Return 是否是竖屏
     */
    public static Boolean isPortrait(Context c) {
        return c.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取应用版本号
     */
    public static int getAPPVersion(Context context) {
        PackageInfo version = null;
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return version.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 定义一个文件存储路径（可修改）
     *
     * @return
     */
    public static String getFilePath() {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhong/";
        return filePath;
    }

    /**
     * 取百分比
     */
    private static NumberFormat numberFormat;
    public static String getPercent(float current,float total){

        if (numberFormat == null){
            // 创建一个数值格式化对象
            numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后0位
            numberFormat.setMaximumFractionDigits(0);
        }
        String result = numberFormat.format(current/total*100)+"%";
        return result;
    }
    /**
     * 获取手机屏幕的尺寸分辨率等信息
     *
     * 可参考：https://cloud.tencent.com/developer/article/1450335
     */
    public static void getPhoneInfo(Activity mActivity){

        if (DeviceUtil.isHarmonyOS()) {
            Logger.d("手机系统: HarmonyOS");
        } else {
            Logger.d("手机系统: Android");
        }

        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels; // 宽
        int height = dm.heightPixels; // 高

        Logger.d("手机屏幕的width（像素）:"+width +"\n");
        Logger.d("手机屏幕的height（像素）:"+height + "\n");

        final float scale = mActivity.getResources().getDisplayMetrics().density;
        Logger.d("手机屏幕的密度(dpi--dots per inch):" + scale);

        int px2dpX = (int) (width/scale + 0.5f);
        Logger.d("手机屏幕的width的dp:"+px2dpX);

        int dp2px = (int) (px2dpX * scale + 0.5f);
        Logger.d("手机屏幕的width的px:"+dp2px);

        int px2dpY = (int) (height/scale + 0.5f);
        Logger.d("手机屏幕的height的dp:"+px2dpY);

        int dp2pxH = (int) (px2dpY*scale + 0.5f);
        Logger.d("手机屏幕的height的px:"+dp2pxH);

        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);

        Logger.d("手机屏幕x轴dpi:"+dm.xdpi);
        Logger.d("手机屏幕y轴dpi:"+dm.ydpi);



        Logger.d("手机屏幕width(英寸):"+dm.widthPixels/dm.xdpi);
        Logger.d("手机屏幕height(英寸):"+dm.heightPixels/dm.ydpi);

        // 屏幕尺寸
        double screenInches = Math.sqrt(x + y);
        Logger.d("手机屏幕尺寸:"+screenInches);
    }

}


