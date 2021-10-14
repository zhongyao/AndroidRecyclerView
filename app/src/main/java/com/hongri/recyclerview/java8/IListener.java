package com.hongri.recyclerview.java8;

import android.util.Log;

/**
 * Create by zhongyao on 2021/10/14
 * Description:Java8新特性系列(interface):
 *
 * 1、在Java8中Interface支持静态成员，成员默认是public final static的，可以在类外直接调用。
 * 2、在Java8中，Interface中支持函数有实现，只要在函数前加上default关键字即可【default函数，实现类可以不实现这个方法，如果不想子类去实现的一些方法，可以写成default函数。】
 * 3、在Java8中允许Interface定义static方法，这允许API设计者在接口中定义像getInstance一样的静态工具方法，这样就能够使得API简洁而精练
 */
public interface IListener {
    //[注释一]
    String TAG = "IListener";

    void doSomething();

    //[注释2]
    default void eat() {
        Log.d(TAG, "eat");
    }

    //[注释3]
    static void sleep() {
        Log.d(TAG, "sleep");
    }

}
