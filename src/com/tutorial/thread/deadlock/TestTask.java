package com.tutorial.thread.deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestTask implements Runnable {
    private Object o1;
    private Object o2;
    private int order;

    public TestTask(Object o1, Object o2, int order) {
        this.o1 = o1;
        this.o2 = o2;
        this.order = order;
    }

    public void o1o2(){
        synchronized (o1){
            Thread.yield(); //建议线程调取器切换到其它线程运行
            synchronized (o2){
                System.out.println("o1o2 - " + Thread.currentThread().getName());
            }
        }
    }

    public void o2o1(){
        synchronized (o2){
            Thread.yield(); //建议线程调取器切换到其它线程运行
            synchronized (o1){
                System.out.println("o1o2 - " + Thread.currentThread().getName());
            }
        }
    }

    @Override
    public void run() {
        while (true){
            if(order==1){
                o1o2();
            }else{
                o2o1();
            }
        }
    }

    public static void main(String[] args) {
        Object o1=new Object();
        Object o2 =new Object();
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            int order = i%2;
            executorService.execute(new TestTask(o1,o2,order));
        }
    }
}
