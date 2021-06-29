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


