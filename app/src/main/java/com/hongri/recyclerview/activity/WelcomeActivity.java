package com.hongri.recyclerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author：zhongyao on 2016/7/29 11:14
 * @description:
 */
public class WelcomeActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
