## 1、浏览器唤端：
#### 在浏览器中打开目录中的deeplink.html文件，文件有写到唤端路由："hongri://recyclerview:8888/welcome?id=100&name=yao"
#### 唤起WelcomeActivity页面，WelcomeActivity中有相关的唤端参数的解析


### 2、Native唤端：
#### BaseActivity中有跳转至SettingActivity【"hongri://recyclerview:6666/setting?id=99&name=jack"】的页面唤端（模仿APP唤端，原理一致），有写到几种唤端方式。
#### Native唤端最好先通过SchemeUtil.isSchemeValid方法判断该scheme是否存在，如果存在时再进行跳转，否则会引起crash。


## 3、WebView跳转到activity示例:
#### <1>通过shouldOverrideUrlLoading重定向：
#### <2>通过scheme跳转【intent-filter】即类似上面的--浏览器唤端
#### <3>JS与Android交互跳转 addJavascriptInterface 等


## 4、EditText剪切 复制 粘贴事件捕获：
#### override EditText的onTextContextMenuItem方法。

## 5、EditText及TextView对输入的文字进行过滤：
#### this.setFilters(new InputFilter[]{new EditPatternFilter("^\\s{0,1}[0-9]{0,1}$")}); //后面是进行过滤的正则表达式


## 6、DrawableUtil工具类方法createRectangleDrawable--可设置各个角的圆角效果。

## 7、各图片文件夹对应分辨率及dpi
###   文件夹名称	分辨率	dpi	density
###     ldpi	 	120dpi	0.75
###     mdpi	 	160dpi 	1
###     hdpi	480*800	240dpi	1.5
###     xhdpi	720*1280	320dpi	2
###     xxhdpi	1080*1920	480dpi	3
###     xxxhdpi	1440*2560	640dpi	4

### 加载顺序：
#### 如果一个设备对应为xxhdpi，则加载图片的优先级依次为drawable-xxhdpi xxxhdpi xhdpi hdpi mdpi ldpi drawable
#### 即先从对应分辨率的文件夹里找，如果没有，则逐渐往高的分辨率的文件夹中找，如果均没有，则从较低的文件夹中依次查找。

#### 从低分辨率的文件夹中加载的图片，会变大，一张图片被放大意味着要占用更多的内存。
#### 从高分辨率的文件夹中加载的图片，会变小。

### 结论：图片资源应该尽量放在高密度文件夹下，这样可以节省图片的内存开支。
#### 参考：https://blog.csdn.net/guolin_blog/article/details/50727753

## 8、APP进入到某个页面后，按home键，在回到APP，如果想保持原有界面，方法有：
####（1）设置启动Activity的launchMode:singleTop ---> standard(或singleTop).并且在启动页跳转MainActivity时finish掉即可(或设置启动页android:noHistory="true"--类似于finish)。
#### (2) 如果启动Activity的launchMode必须设置为singleTask,怎可在启动Activity的onCreate方法中加入如下代码即可： 
#### '''
     if (!this.isTaskRoot()) {
                 Intent intent = getIntent();
                 if (intent != null) {
                     String action = intent.getAction();
                     if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                         finish();
                         return;
                     }
                 }
             }
      
####     '''

## 9、Activity启动模式：
#### (1)singleInstance 的使用场景：适合需要与程序分离开的页面。如闹钟提醒，将闹铃提醒与闹铃设置分离。
#### (2)singleTask 适合作为程序的入口点。
#### (3)singleTop 适合接收通知启动的内容显示页面。


## 10、EditText属性：
#### 默认非单行，故点击软键盘回车键，会自动换行。
#### android:singleLine="true"(必加，下同) android:imeOptions="actionSend" -->"发送"
#### android:imeOptions="actionSearch" -->"搜索"
#### android:imeOptions="actionNext" -->"下一步"
#### android:imeOptions="actionDone" -->"完成"
#### android:imeOptions="actionGo" -->"前往/开始"



## 11、状态栏/导航栏
#### 状态栏和导航栏窗口是系统级窗口而Activity对应的时应用窗口，它们属于不同的窗口层级
#### '''
    <style name="TestTheme" parent="android:Theme.Material.NoActionBar">
        <!--下列两行控制使得应用窗口透明，用于展示一些差异-->
        <item name="android:windowBackground">@color/transparent</item>
        <item name="android:windowIsTranslucent">true</item>
        
        <!--设置导航栏/状态栏窗口color为透明【app布局不会延伸到导航栏/状态栏】-->
        <!--<item name="android:statusBarColor">@android:color/transparent</item>-->
        <!--<item name="android:navigationBarColor">@android:color/transparent</item>-->

        <!--与color transparent的区别【app布局将会延伸到导航栏/状态栏】-->
        <item name="android:windowTranslucentStatus">true</item>
        <item name="android:windowTranslucentNavigation">true</item>
    </style>
#### '''
#### 上面主题最下面两行代码相当于：
#### '''
        //使得布局延伸到状态栏和导航栏区域
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        
        //透明状态栏/导航栏
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
#### '''
#### '''
    在布局中设置:
    android:fitsSystemWindows="true"
    可以使得，系统自动为视图添加一个状态栏/导航栏高度的padding
#### '''
### 12、EditText的beforeTextChanged onTextChanged的 CharSequence s 参数赋值问题
#### 【赋值给CharSequence 与赋值给String的区别】需注意。String 单独存在常量池中，故不变化。 
#### 而内部CharSequence公用同一个s,故会变化。
####



### 13、软键盘【隐藏/显示软键盘方法】
#### 封装SoftKeyBoardUtil 软键盘显示/隐藏工具类。
#### 

### 14、adb 启动Activity并传参.
#### 指令举例:adb shell am start -n com.hongri.recyclerview/com.hongri.recyclerview.activity.SettingActivity --es name zhongyao --ei age 18
#### 在Activity中通过getIntent解析数据，具体可查看，SettingActivity.

### 15、正则表达式：
##### 如：^[^\r]{0,100}$ 表示可以输入100个字符，包含回车键 
##### 参考：https://www.liaoxuefeng.com/wiki/1016959663602400/1017639890281664

### 16、动画导致setVisibility/removeView失效问题解决方案:
##### 在这两处操作之前 view.clearAnimation()即可
###### 另外有一种情况setVisibility/removeView不会失效，即父布局的height是wrap_content。


### Android 检测App是否调用了用户的敏感信息的方案:
[Android审核：用户授权前获取mac地址，imei等用户敏感信息的方法](https://www.jianshu.com/p/84127032d15a)











