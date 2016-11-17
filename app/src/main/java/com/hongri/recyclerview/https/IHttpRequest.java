package com.hongri.recyclerview.https;

/**
 * @author：zhongyao on 2016/7/20 19:52
 * @description:
 */
public interface IHttpRequest {
    String METHOD_GET = "GET";
    String METHOD_POST = "POST";

    //获得接口数据
    Object getDataObject();

    void request(HttpIntent httpIntent, IHttpRequestCallBack callBack);

    abstract class IHttpRequestCallBack {

        public abstract void onSuccess(IHttpRequest request);

        public abstract void onFailed(String failReason);

        }
}
