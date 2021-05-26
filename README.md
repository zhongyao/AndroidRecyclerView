# 1、浏览器唤端：
# 在浏览器中打开目录中的deeplink.html文件，文件有写到唤端路由："hongri://recyclerview:8888/welcome?id=100&name=yao"
# 唤起WelcomeActivity页面，WelcomeActivity中有相关的唤端参数的解析

# 2、Native唤端：BaseActivity中有跳转至SettingActivity【"hongri://recyclerview:6666/setting?id=99&name=jack"】的页面唤端（模仿APP唤端，原理一致），有写到几种唤端方式。
# Native唤端最好先通过SchemeUtil.isSchemeValid方法判断该scheme是否存在，如果存在时再进行跳转，否则可能会引起crash。

