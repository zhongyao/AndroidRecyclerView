package com.hongri.recyclerview.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 参考：https://www.jianshu.com/p/24c6b42ad23e
 *
 * @author：hongri
 * @date：4/10/22
 * @description：
 */
public class ShareUtil {

    private static final int SHARE_RESULT_FILE_NOT_FOUND = -1;
    private static final int SHARE_RESULT_NO_ERROR = 1;
    private static final String MIME_TYPE_TEXT = "text/plain";
    private static final String MIME_TYPE_IMAGE = "image/*";

    public static void shareText(Context context, String contentText) {
        Intent intent = new Intent();
        //1、Action
        intent.setAction(Intent.ACTION_SEND);
        //2、Type
        intent.setType(MIME_TYPE_TEXT);
        //3、Extra
        intent.putExtra(Intent.EXTRA_TEXT, contentText);
        //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
        context.startActivity(Intent.createChooser(intent, "Here is the title of Select box"));
    }

    /**
     * 参考：https://www.shangmayuan.com/a/cdd2ea0d9ac645e295579c3c.html
     * Android7.0及以上手机使用系统分享图片，需要用到FileProvider，分四部进行：
     * 1、指定一个FileProvider，在AndroidManifest.xml文件中声明一个条目
     * 2、指定想要分享的目录，在res目录下新建一个xml目录，在xml目录下新建一个xml文件【如file_paths.xml】
     * 3、为一个文件生成contentUri
     * 4、分享一个contentUri
     *
     * @param context
     * @param picFilePath
     * @return
     */
    public static int sharePic(Context context, String picFilePath) {
        try {
            File shareFile = new File(picFilePath);
            if (!shareFile.exists()) {
                return SHARE_RESULT_FILE_NOT_FOUND;
            }
            Intent intent = new Intent(Intent.ACTION_SEND);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", shareFile);
                intent.putExtra(Intent.EXTRA_STREAM, contentUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(shareFile));
            }
            intent.setType(MIME_TYPE_IMAGE);
            Intent chooser = Intent.createChooser(intent, "share");
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(chooser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SHARE_RESULT_NO_ERROR;
    }

    /**
     * 多图分享
     * //TODO 微信多图分享有限制,无法分享成功,后续研究下
     *
     * @param context
     * @param localImages
     */
    public static int shareMultiPic(Context context, ArrayList<String> localImages) {
        try {
            ArrayList<Uri> imgUris = new ArrayList<>();

            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                for (int i = 0; i < localImages.size(); i++) {
                    File shareFile = new File(localImages.get(i));
                    if (!shareFile.exists()) {
                        return SHARE_RESULT_FILE_NOT_FOUND;
                    }
                    Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", shareFile);
                    imgUris.add(contentUri);
                }
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imgUris);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                for (int i = 0; i < localImages.size(); i++) {
                    imgUris.add(Uri.parse(localImages.get(i)));
                }
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imgUris);
            }
            intent.setType(MIME_TYPE_IMAGE);
            Intent chooser = Intent.createChooser(intent, "share");
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(chooser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SHARE_RESULT_NO_ERROR;

    }

    public static int shareSpecifiedApp(Context context, String contentText) {
        try {
            List<Intent> targetIntents = new ArrayList<>();
            //获取所有支持ACTION_SEND的应用
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            PackageManager pm = context.getPackageManager();
            List<ResolveInfo> resInfos = pm.queryIntentActivities(shareIntent, 0);
            //对目标引用进行判断
            if (resInfos != null && resInfos.size() > 0) {
                for (ResolveInfo info : resInfos) {
                    if ("com.tencent.mobileqq".equals(info.activityInfo.packageName)
                            || "com.tencent.mm".equals(info.activityInfo.packageName)) {

                        Intent target = new Intent(Intent.ACTION_SEND);
                        target.setType("text/plain");
                        target.putExtra(Intent.EXTRA_TEXT, contentText);
                        target.setComponent(new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
                        targetIntents.add(target);
                    }
                }
            }
            //创建应用选择框
            Intent chooserIntent = Intent.createChooser(targetIntents.remove(0), "share");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetIntents.toArray(new Parcelable[]{}));
            context.startActivity(chooserIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SHARE_RESULT_NO_ERROR;
    }
}
