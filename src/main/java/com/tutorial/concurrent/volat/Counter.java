package com.tutorial.concurrent.volat;

import java.util.concurrent.CountDownLatch;

/**
 * 错误实例
 */
public class Counter {
    public static volatile int num=0;
    static CountDownLatch countDownLatch=new CountDownLatch(30);

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<30;i++){
            new Thread(){
                @Override
                public void run() {
                    for(int j=0;j<1000;j++){
                        num++;      //num++不是个原子性的操作，而是个复合操作
                    }
                    countDownLatch.countDown();
                }
            }.start();
        }

        countDownLatch.await(); //由于countDown()方法的调用，计数达到零后继续执行
        System.out.println(num);
    }
}
