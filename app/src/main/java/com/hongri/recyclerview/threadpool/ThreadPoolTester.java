package com.hongri.recyclerview.threadpool;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by zhongyao on 2021/9/1
 * Description:
 */
public class ThreadPoolTester {

    private final String TAG = "ThreadPoolTester";
    public ExecutorService fixedExecutorService = Executors.newFixedThreadPool(5);
    public ExecutorService cachedExecutorService = Executors.newCachedThreadPool();
    public ExecutorService singleExecutorService = Executors.newSingleThreadExecutor();
    public ExecutorService customerExecutorService = new ThreadPoolExecutor(3, 3, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    /**
     * 原因一：显示关闭掉线程池
     * test for *** java.util.concurrent.RejectedExecutionException ***
     *
     * 解决方案：
     * 1、不要显示的调用shutdown方法---【一般调用 shutdown() 方法之后，JVM会得到一个关闭线程池的信号，并不会立即关闭线程池，原来线程池里未执行完的任务仍然在执行，等到任务都执行完后才关闭线程池，但是JVM不允许再提交新任务给线程池。】
     * 2、调用线程池时，判断是否已经shutdown，通过API方法isShutDown方法判断
     */
    public void testExecutorException() {
        for (int i = 0; i < 10; i++) {
            fixedExecutorService.execute(new SayHelloRunnable());
            fixedExecutorService.shutdown();
        }
    }

    /**
     * 原因二：线程数量超过maximumPoolSize
     * test for *** java.util.concurrent.RejectedExecutionException ***
     *
     * 解决方案：
     * 1、尽量调大maximumPoolSize，例如设置为Integer.MAX_VALUE
     * 2、使用其他排队策略，例如LinkedBlockingQueue
     */
    public void testCustomerExecutorException() {
        for (int i = 0; i < 100; i ++) {
            customerExecutorService.execute(new SayHelloRunnable());
        }
    }

    private class SayHelloRunnable implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.d(TAG, "exception:" + e.toString());
                e.printStackTrace();
            } finally {
                System.out.println("hello world!");
            }

        }
    }
}
