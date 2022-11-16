package com.monster.test.bingfasms;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName SmsThread
 * @Deacription TODO
 * @Author wrx
 * @Date 2021/12/1/001 15:44
 * @Version 1.0
 **/
public class SmsThread implements Runnable {
    private final CountDownLatch startSignal;

    public SmsThread(CountDownLatch startSignal) {
        super();
        this.startSignal = startSignal;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            //一直阻塞当前线程，直到计时器的值为0
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //实际测试操作
        doWork();
    }

    private void doWork() {
        // TODO Auto-generated method stub
        System.out.println("do work");
    }
}
