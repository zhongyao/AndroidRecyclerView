package com.hongri.recyclerview.https;


import android.annotation.SuppressLint;
import android.content.Intent;
/**
 * @author：zhongyao on 2016/7/120 14:56
 * @description:
 */
@SuppressLint("ParcelCreator")
public class HttpIntent extends Intent{
    public static final String URI = "uri";

    public static final String METHOD = "method";

    public HttpIntent(String uri, String reqMethod) {
        putExtra(URI, uri);
        putExtra(METHOD, reqMethod);
    }
}
