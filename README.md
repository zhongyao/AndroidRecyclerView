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

