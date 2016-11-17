package com.hongri.recyclerview.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.fragment.SettingFragement;
import com.hongri.recyclerview.utils.APPUtils;
import com.hongri.recyclerview.utils.Logger;
import com.hongri.recyclerview.utils.ToastUtil;


/**
 * @author：zhongyao on 2016/6/29 16:01
 * @description:所有Activity父类
 */
public class BaseActivity extends AppCompatActivity {
    private Context context;
    private LinearLayout rootLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("BaseActivity---onCreate()");
        // 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        initToolbar();

        context = this;
        if (APPUtils.isPad(context)) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            Logger.d("该设备为：PAD");
        } else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
            Logger.d("该设备为：Phone");
        }
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);//设置导航栏图标
//       toolbar.setLogo(R.drawable.hongri);//设置app logo
        toolbar.setTitle("红日");//设置主标题
//        toolbar.setSubtitle("Subtitle");//设置子标题

        toolbar.inflateMenu(R.menu.menu_main);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        ToastUtil.ShowBottomShort(BaseActivity.this, R.string.action_search);
                        break;
                    case R.id.action_favorite:
                        ToastUtil.ShowBottomShort(BaseActivity.this, R.string.action_favorite);
                        break;
                    case R.id.action_settings:
                        ToastUtil.ShowBottomShort(BaseActivity.this, R.string.action_settings);
                        break;
                }
                return true;
            }
        });
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        // Configure the search info and add any event listeners...
        MenuItemCompat.OnActionExpandListener listener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                ToastUtil.ShowBottomShort(BaseActivity.this, "onMenuItemActionExpand");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                ToastUtil.ShowBottomShort(BaseActivity.this, "onMenuItemActionCollapse");
                return true;
            }
        };

        MenuItemCompat.setOnActionExpandListener(searchItem, listener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                ToastUtil.ShowBottomShort(BaseActivity.this, R.string.action_search);
                break;
            case R.id.action_favorite:
                ToastUtil.ShowBottomShort(BaseActivity.this, R.string.action_favorite);
                break;
            case R.id.action_settings:
//                Intent intent = new Intent(this,SettingActivity.class);
//                startActivity(intent);
                /**
                 * 隐式启动SettingActivity
                 * 参考：
                 * http://fenglingdangyang.blog.sohu.com/231856618.html
                 */

                //通过匹配action,来隐式启动该Activity
                /*Intent intent = new Intent();
                intent.setAction("com.hongri.recyclerview.activity.SettingActivity");
                startActivity(intent);*/

                //通过匹配action,data 来隐式启动该Activity
                Intent intent = new Intent();
                intent.setAction("com.hongri.recyclerview.activity.SettingActivity");
                //Uri的格式:scheme://host:port/path or pathPrefix or pathPattern
                intent.setData(Uri.parse("hongri://recyclerview/setting"));
                startActivity(intent);

                ToastUtil.ShowBottomShort(BaseActivity.this, R.string.action_settings);
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        if (rootLayout == null) return;
        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (APPUtils.isLandscape(this)) {
            Logger.d("横屏");
        } else if (APPUtils.isPortrait(this)) {
            Logger.d("竖屏");
        }
    }
}

