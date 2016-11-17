package com.hongri.recyclerview.https;

import android.content.Context;

/**
 * @author：zhongyao on 2016/7/20 19:56
 * @description:
 */
public class HttpRequest {
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

//    public static void getUserRequest(IHttpRequest.IHttpRequestCallBack callBack, String url) {
//        HttpIntent httpIntent = new HttpIntent(url, METHOD_GET);
//        IHttpRequest httpRequest = new HttpRequestManager();
//        httpRequest.request(httpIntent, callBack);
//    }

    public static void getUserRequest(IHttpRequest.IHttpRequestCallBack callBack, String url, Context context) {
        HttpIntent httpIntent = new HttpIntent(url, METHOD_GET);
        IHttpRequest httpRequest = new HttpRequestManager(context);
        httpRequest.request(httpIntent, callBack);
    }

}
