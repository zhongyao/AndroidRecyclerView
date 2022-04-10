package com.hongri.recyclerview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.utils.ShareUtil;

import java.io.File;
import java.util.ArrayList;

public class ShareTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "ShareTestActivity";
    private Button btnShareText, btnShareImage, btnShareMultiImage;
    private String imageUrl = "https://img1.baidu.com/it/u=3754966325,1403601599&fm=253&fmt=auto&app=120&f=JPEG?w=1024&h=640";
    private String imageUrl2 = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Ff7ee82e2354854624b89737fdb82365036eaf18a32bc3-rqjJB2_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652188646&t=fcc27b04f59325e85dafa6d0123991e4";
    //localImage需是你当前测试设备中已有的图片
    private String localImage = Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + "Camera" + File.separator + "IMG_20220225_122555.jpg";
    private String localImage2 = Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + "Camera" + File.separator + "IMG_20220224_133500.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_test);

        btnShareText = findViewById(R.id.btnShareText);
        btnShareImage = findViewById(R.id.btnShareImage);
        btnShareMultiImage = findViewById(R.id.btnShareMultiImage);

        btnShareText.setOnClickListener(this);
        btnShareImage.setOnClickListener(this);
        btnShareMultiImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        switch (id) {
            case R.id.btnShareText:
                ShareUtil.shareText(this, "哈哈哈");
                break;

            case R.id.btnShareImage:
                ShareUtil.sharePic(this, localImage);
                //将mipmap中图片转换成Uri
//                Uri imgUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" +R.drawable.icon_redsun2);
                break;

            case R.id.btnShareMultiImage:
                ArrayList<String> localImages = new ArrayList<>();
                localImages.add(localImage);
                localImages.add(localImage2);
                ShareUtil.shareMultiPic(this, localImages);
                break;

            default:
                break;
        }
    }
}