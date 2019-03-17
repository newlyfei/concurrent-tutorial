package com.tutorial.thread.threadlocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalApp {
    private static AtomicInteger num= new AtomicInteger(0);

    private static ThreadLocal<ThreadLocalApp> threadLocal=new ThreadLocal<ThreadLocalApp>(){
        @Override
        protected ThreadLocalApp initialValue() {
            return ThreadLocalApp.imitateConnect();
        }
    };

    /**
     * 一个私有的模拟方法
     * @return
     */
    private static ThreadLocalApp imitateConnect(){
        ThreadLocalApp threadLocalExample=new ThreadLocalApp();
        return threadLocalExample;
    }

    public static ThreadLocalApp getConnect(){
        return threadLocal.get();
    }

    public static Integer getNum(){
        return num.get();
    }

    public long addNum(){
        return num.incrementAndGet();
    }


    public static void main(String[] args) throws InterruptedException {
        List<Thread> works=new ArrayList<>();
        for(int i=0;i<10;i++){
            Thread t1=new Thread(){
                @Override
                public void run() {
                    ThreadLocalApp threadLocalExample=ThreadLocalApp.getConnect();
                    threadLocalExample.addNum();

                    //ThreadLocal使得变量的作用域为“线程级”，通过线程上下文在ThreadLocalService中可直接使用ThreadLocalApp值。它是一种线程封闭（每个线程独享变量）技术
                    ThreadLocalService.writeValue();
                 }
            };
            t1.start();
            works.add(t1);
        }
        for(Thread t:works){
            t.join();
        }

    }
}
