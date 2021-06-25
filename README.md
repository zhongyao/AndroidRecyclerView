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
##   文件夹名称	分辨率	dpi	density
##     ldpi	 	120dpi	0.75
##     mdpi	 	160dpi 	1
##     hdpi	480*800	240dpi	1.5
##     xhdpi	720*1280	320dpi	2
##     xxhdpi	1080*1920	480dpi	3
##     xxxhdpi	1440*2560	640dpi	4
