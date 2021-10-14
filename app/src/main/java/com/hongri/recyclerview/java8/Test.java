package com.hongri.recyclerview.java8;

import android.util.Log;

import com.hongri.recyclerview.java8.IListener;

/**
 * Create by zhongyao on 2021/10/14
 * Description:
 */
public class Test implements IListener {

    public static void main(String[] strings) {
//        Test test = new Test();
//        test.doSomething();
//        test.eat();

        IMyListener myListener = msg -> msg + "world!";

        //原型
        IMyListener listener = new IMyListener() {
            @Override
            public String study(String msg) {
                return msg + "world!";
            }
        };

        System.out.println("test functional:" + myListener.study("hello,"));

    }

    @Override
    public void doSomething() {
        Log.d(TAG, "doSomething");
    }
}
