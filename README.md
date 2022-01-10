## 该项目中有多种关于RecyclerView方面的应用，可直接运行项目查看。另外在SettingActivity中有总结项目中用到的其他Android碎片化知识点，有需要的也可按需查阅。
<br><br/>
<br><br/>

### RecyclerView四级缓存:
#### (1)、Scrap是屏幕内的缓存，一般不需要我们做处理。
#### (2)、Cache可直接拿来复用的缓存，性能高效。
#### (3)、ViewCacheExtension，需要开发者自定义的缓存，API设计比较奇怪，慎用。
#### (4)、RecycledViewPool四级缓存，可以避免用户调用onCreateViewHolder()方法，提高性能。在ViewPager+RecyclerView的应用场景下可以大有作为
#### [让你彻底掌握RecyclerView的缓存机制](https://www.jianshu.com/p/3e9aa4bdaefd)
<br><br/>

- 以下是其他Android知识点总结:
### 1、浏览器唤端：
#### 在浏览器中打开目录中的deeplink.html文件，文件有写到唤端路由："hongri://recyclerview:8888/welcome?id=100&name=yao"
#### 唤起WelcomeActivity页面，WelcomeActivity中有相关的唤端参数的解析
<br><br/>

### 2、Native唤端：
#### BaseActivity中有跳转至SettingActivity【"hongri://recyclerview:6666/setting?id=99&name=jack"】的页面唤端（模仿APP唤端，原理一致），有写到几种唤端方式。
#### Native唤端最好先通过SchemeUtil.isSchemeValid方法判断该scheme是否存在，如果存在时再进行跳转，否则会引起crash。
<br><br/>

### 3、WebView跳转到activity示例:
#### <1>通过shouldOverrideUrlLoading重定向：
#### <2>通过scheme跳转【intent-filter】即类似上面的--浏览器唤端
#### <3>JS与Android交互跳转 addJavascriptInterface 等
<br><br/>

### 4、EditText剪切 复制 粘贴事件捕获：
#### override EditText的onTextContextMenuItem方法。
<br><br/>

### 5、EditText及TextView对输入的文字进行过滤：
#### this.setFilters(new InputFilter[]{new EditPatternFilter("^\\s{0,1}[0-9]{0,1}$")}); //后面是进行过滤的正则表达式
<br><br/>

### 6、DrawableUtil工具类方法createRectangleDrawable--可设置各个角的圆角效果。
<br><br/>

### 7、各图片文件夹对应分辨率及dpi
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
<br><br/>

### 8、APP进入到某个页面后，按home键，在回到APP，如果想保持原有界面，方法有：
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
<br><br/>

### 9、Activity启动模式：
#### (1)singleInstance 的使用场景：适合需要与程序分离开的页面。如闹钟提醒，将闹铃提醒与闹铃设置分离。
#### (2)singleTask 适合作为程序的入口点。
#### (3)singleTop 适合接收通知启动的内容显示页面。
<br><br/>

### 10、EditText属性：
#### 默认非单行，故点击软键盘回车键，会自动换行。
#### android:singleLine="true"(必加，下同) android:imeOptions="actionSend" -->"发送"
#### android:imeOptions="actionSearch" -->"搜索"
#### android:imeOptions="actionNext" -->"下一步"
#### android:imeOptions="actionDone" -->"完成"
#### android:imeOptions="actionGo" -->"前往/开始"
<br><br/>


### 11、状态栏/导航栏
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

#### Android 5.0后支持修改系统状态栏颜色，6.0之后支持修改状态栏字体【深色(黑色)或浅色(白色)】
<br><br/>

### 12、EditText的beforeTextChanged onTextChanged的 CharSequence s 参数赋值问题
#### 【赋值给CharSequence 与赋值给String的区别】需注意。String 单独存在常量池中，故不变化。 
#### 而内部CharSequence公用同一个s,故会变化。
####
<br><br/>

### 13、软键盘【隐藏/显示软键盘方法】
#### 封装SoftKeyBoardUtil 软键盘显示/隐藏工具类。
#### 以上工具类新增 isSoftShowing 方法，用于判断软键盘是否正在显示【比较可靠】。
<br><br/>

### 14、adb 启动Activity并传参.
#### 指令举例:adb shell am start -n com.hongri.recyclerview/com.hongri.recyclerview.activity.SettingActivity --es name zhongyao --ei age 18
#### 在Activity中通过getIntent解析数据，具体可查看，SettingActivity.
<br><br/>

### 15、正则表达式：
##### 如：^[^\r]{0,100}$ 表示可以输入100个字符，包含回车键 
##### 参考：https://www.liaoxuefeng.com/wiki/1016959663602400/1017639890281664
<br><br/>

### 16、动画导致setVisibility/removeView失效问题解决方案:
##### 在这两处操作之前 view.clearAnimation()即可
###### 另外有一种情况setVisibility/removeView不会失效，即父布局的height是wrap_content。
<br><br/>

### 17、Android 检测App是否调用了用户的敏感信息的方案:
[Android审核：用户授权前获取mac地址，imei等用户敏感信息的方法](https://www.jianshu.com/p/84127032d15a)
<br><br/>

### 18、关于Drawable的缓存机制【围绕mutate】：
#### 1、同一资源不同实例的BitmapDrawable，使用的是同一BitmapState对象。
#### 2、对某个特殊Drawable可使用mutate，使其单独使用一个BitmapState对象，该操作不可逆转。
#### 3、Drawable.getConstantState().newDrawable()虽然公用一个BitmapState,但是其Drawable.mBounds是不同的，会进行重绘。
[Android 关于Drawable的缓存机制](https://www.heqiangfly.com/2017/06/15/android-knowledge-point-drawable-cache/)
<br><br/>

### 19、支持渠道打包：
#### 详见gradle文件中 productFlavors 相关配置。
[Android 多渠道打包配置](https://juejin.cn/post/6844904072131117063)


### 20、无法捕获的Exception：
#### 在try catch中开启新的线程，不能捕获线程里面的异常，需要在线程里面代码处再添加try catch捕获。
#### 不能判断是Exception还是Error错误，则使用其父类Throwable捕获较好。

### 21、Activity的各种属性：
#### [Activity属性配置](https://developer.android.com/guide/topics/manifest/activity-element)
##### 
##### '''
                android:allowEmbedded=["true" | "false"] 
                【一般为开发可穿戴设备时使用】
                
                android:allowTaskReparenting=["true" | "false"]
                【这个标示和 Application 的标识意义一样，所以如果同时声明该标识，这个标识会覆盖 Application 的标识】
                
                android:alwaysRetainTaskState=["true" | "false"]
                【这个标识用来指示系统是否始终保持 Activity 所在任务的状态。该属性只对任务的根 Activity 有意义】
                
                android:autoRemoveFromRecents=["true" | "false"]
                
                
                android:banner="drawable resource"
                【和 application 里面的 banner 标识一样，用在 android TV 上】
                
                android:clearTaskOnLaunch=["true" | "false"]
                【当应用从主屏幕重新启动时是否都从中移除除根 Activity 之外的所有 Activity。该属性只对任务的根 Activity 有意义】
                
                android:colorMode=[ "hdr" | "wideColorGamut"]
                【广色域】
                
                android:configChanges=["mcc", "mnc", "locale",
                                       "touchscreen", "keyboard", "keyboardHidden",
                                       "navigation", "screenLayout", "fontScale",
                                       "uiMode", "orientation", "density",
                                       "screenSize", "smallestScreenSize"]
                【列出 Activity 将自行处理的配置更改消息】                       
                                       
                android:directBootAware=["true" | "false"]
                【】
                android:documentLaunchMode=["intoExisting" | "always" |
                                        "none" | "never"]
                android:enabled=["true" | "false"]
                【该属性用来标示系统是否可将 Activity 实例化】
                
                android:excludeFromRecents=["true" | "false"]
                【是否应将该 Activity 启动的任务排除在最近使用的应用列表（即概览屏幕）之外】
                
                android:exported=["true" | "false"]
                【指明Activity 是否可由其他应用的组件启动】
                
                android:finishOnTaskLaunch=["true" | "false"]
                【标示每当用户再次启动其任务（在主屏幕上选择任务）时，是否应关闭（完成）现有 Activity 实例 】
                
                android:hardwareAccelerated=["true" | "false"]
                【是否应为此 Activity 启用硬件加速渲染】
                
                android:icon="drawable resource"
                【一个表示 Activity 的图标。该图标会在需要在屏幕上表示 Activity 时显示给用户。】
                
                android:immersive=["true" | "false"]
                android:label="string resource"
                【一种可由用户读取的 Activity 标签。该标签会在必须将 Activity 呈现给用户时显示在屏幕上】
                
                android:launchMode=["standard" | "singleTop" |
                                    "singleTask" | "singleInstance"]
                android:lockTaskMode=["normal" | "never" |
                                    "if_whitelisted" | "always"]
                android:maxRecents="integer"
                【该标识用来指明概览屏幕中位于此 Activity 根位置的任务数上限。 达到该条目数时，系统会从概览屏幕中移除最近最少使用的实例。】
                
                android:maxAspectRatio="float"
                android:multiprocess=["true" | "false"]
                【该标识用来指明是否可以将 Activity 实例启动到启动该实例的组件进程内】
                
                android:name="string"
                android:noHistory=["true" | "false"]  
                【当用户离开 Activity 并且其在屏幕上不再可见时，是否应从 Activity 堆栈中将其移除并完成。如果为true，系统永远不会调用 onActivityResult()】
                
                android:parentActivityName="string" 
                【Activity 逻辑父项的类名称。】
                android:persistableMode=["persistRootOnly" | 
                                         "persistAcrossReboots" | "persistNever"]
                android:permission="string"
                android:process="string"
                【应在其中运行 Activity 的进程的名称】
                android:relinquishTaskIdentity=["true" | "false"]
                android:resizeableActivity=["true" | "false"]
                【代表这个 activity 是否支持分屏模式。】
                
                android:screenOrientation=["unspecified" | "behind" |
                                           "landscape" | "portrait" |
                                           "reverseLandscape" | "reversePortrait" |
                                           "sensorLandscape" | "sensorPortrait" |
                                           "userLandscape" | "userPortrait" |
                                           "sensor" | "fullSensor" | "nosensor" |
                                           "user" | "fullUser" | "locked"]
                android:showForAllUsers=["true" | "false"]
                android:stateNotNeeded=["true" | "false"]
                【能否在不保存 Activity 状态的情况下将其终止并成功重新启动】
                
                android:supportsPictureInPicture=["true" | "false"]
                【指定 Activity 是否支持画中画显示，设置该属性的同时，需要将 android:resizeableActivity 标识设置为 true】
                
                android:taskAffinity="string"[TODO 对此属性尚有疑惑]
                【任务相关性---这个参数标识了一个Activity所需要的任务栈的名字，taskAffinity属性主要和singleTask启动模式或者allowTaskReparenting属性配对使用，在其他情况下没有意义
                1、当taskAffinity和singleTask启动模式配对使用的时候，它是具有该模式的Activity的目前任务栈的名字，待启动的Activity会运行在名字和taskAffinity相同的任务栈中。
                2、当taskAffinity和allowTaskReparenting结合的时候，会产生特殊的效果。如当一个应用A启动了一个应用B中的某个Activity后，如果这个Activity的allowTaskReparenting属性为true的话，
                那么当应用B被启动后，此Activity会直接从应用A的任务栈转移到应用B的任务栈中。】
                android:theme="resource or theme"
                android:uiOptions=["none" | "splitActionBarWhenNarrow"]
                【主要是用来针对 action bar 的】
                
                android:windowSoftInputMode=["stateUnspecified",
                                             "stateUnchanged", "stateHidden",
                                             "stateAlwaysHidden", "stateVisible",
                                             "stateAlwaysVisible", "adjustUnspecified",
                                             "adjustResize", "adjustPan"]
                 【这个标识用来设置 Activity 的主窗口与包含屏幕软键盘的窗口的交互方式】
##### '''

### 22、添加缓存清理类 APPCacheUtil 对比原有的 CacheClearManager类。

### Java8 interface新特性：
#### 1、支持静态成员，成员默认是public final static的。
#### 2、可以有方法的实现(加default关键字)。
#### 3、可以有静态方法(加static关键字)。
#### 4、支持函数式接口(加functionalInterface关键字)

### 23、定时器【间隔器】业务处理，查看：TimeCountDown

### 24、新增自定义Toast【CustomToast】
#### 可控制Toast的布局跟时长





