package com.hongri.recyclerview.helper;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author：zhongyao
 * @date：2023/3/6
 * @description： 参考:https://www.jb51.cc/android/1073784.html
 * <p>
 * CountDownLatch可以看作一个计数器，只不过该计数器是原子操作，
 * 即同时只能有一个线程去操作这个计数器，也就是同时只能有一个线程去减这个计数器里面的值。
 * <p>
 * 应用场景：
 * 针对有一个任务想要往下执行，但必须要等到其他任务执行完毕后才可以的场景，我们可以调用其await()方法，
 * 待CountDownLatch对象的计数值减到0时，便会往下继续执行。
 */
public class CountDownLatchHelper {

    private CountDownLatchHelper() {
    }

    public static CountDownLatchHelper getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final CountDownLatchHelper INSTANCE = new CountDownLatchHelper();
    }

    public void test() {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        MyTask myTask = new MyTask(countDownLatch);
        Thread thread1 = new Thread(myTask, "线程1");
        Thread thread2 = new Thread(myTask, "线程2");
        Thread thread3 = new Thread(myTask, "线程3");
        Thread thread4 = new Thread(myTask, "线程4");
        Thread thread5 = new Thread(myTask, "线程5");

        ArrayList<Thread> arrayList = new ArrayList<>();
        arrayList.add(thread1);
        arrayList.add(thread2);
        arrayList.add(thread3);
        arrayList.add(thread4);
        arrayList.add(thread5);

        for (Thread thread : arrayList) {
            thread.start();
        }

        try {
            countDownLatch.await();

            //如果10s还没执行完，则不管CountDownLatch对象的计数值是否减到0，均会往下继续执行
//            boolean isZero = countDownLatch.await(10 * 1000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("子线程执行完毕， 开始执行主线程");
    }

    private static class MyTask implements Runnable {
        private CountDownLatch countDownLatch;

        public MyTask(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
            countDownLatch.countDown();
        }
    }

}
