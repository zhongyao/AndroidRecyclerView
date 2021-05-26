package com.hongri.recyclerview.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.common.APPConstants;
import com.hongri.recyclerview.fragment.DetailAndroidArtFragment;
import com.hongri.recyclerview.fragment.DetailCallbackTestFragment;
import com.hongri.recyclerview.fragment.DetailDiskLruCacheTestFragment;
import com.hongri.recyclerview.fragment.DetailExpendedFragment;
import com.hongri.recyclerview.fragment.DetailImageLoaderTestFragment;
import com.hongri.recyclerview.fragment.DetailLoadPicsTestFragment;
import com.hongri.recyclerview.fragment.DetailMutipleFragment;
import com.hongri.recyclerview.fragment.DetailNormalFragment;
import com.hongri.recyclerview.fragment.DetailPullToRefreshFragment;
import com.hongri.recyclerview.fragment.DetailReboundFragment;
import com.hongri.recyclerview.fragment.DetailViewFragment;
import com.hongri.recyclerview.fragment.DetailVolleyTestFragment;
import com.hongri.recyclerview.utils.Logger;

/**
 * @author：zhongyao on 2016/6/30 17:32
 * @description:详情页Activity
 */
public class DetailActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Logger.d("DetailActivity--onCreate()");
        int position = getIntent().getIntExtra("position", APPConstants.Type_List_Layout);
        String title = getIntent().getStringExtra("title");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);

        switchFragment(position, title);

    }

    private void switchFragment(int position, String title) {
        if (position <= 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailNormalFragment.newInstance(position, title)).commit();
        } else if (position <= 4) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailMutipleFragment.newInstance(position, title)).commit();
        } else if (position == 5) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailExpendedFragment.newInstance(position, title)).commit();
        } else if (position == 6) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailVolleyTestFragment.newInstance(position, title)).commit();
        } else if (position == 7) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailLoadPicsTestFragment.newInstance()).commit();
        } else if(position == 8){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailDiskLruCacheTestFragment.newInstance()).commit();
        } else if(position == 9){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailImageLoaderTestFragment.newInstance()).commit();
        } else if (position == 10) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailPullToRefreshFragment.newInstance(position, title)).commit();
        } else if (position == 11) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailCallbackTestFragment.newInstance(position, title)).commit();
        }else if(position == 12){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailAndroidArtFragment.newInstance(position,title)).commit();
        }else if (position == 13){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailViewFragment.newInstance(position,title)).commit();
        }else if (position == 14) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailReboundFragment.newInstance(position,title)).commit();

        }
    }
}
