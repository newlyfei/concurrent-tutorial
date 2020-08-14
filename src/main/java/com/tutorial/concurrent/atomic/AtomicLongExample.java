package com.tutorial.concurrent.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongExample {

    static class LongTest{
        private long count;

        public long getCount(){
            return count;
        }

        public void addCount(){
            count++;
        }
    }

    static class AtomicLongTest{
        private AtomicLong count=new AtomicLong(0);

        public long getCount(){
            return count.get();
        }

        public void addCount(){
            count.incrementAndGet();
        }
    }

    static class VolatileTest{
        private volatile long count;

        public long getCount(){
            return count;
        }

        public void addCount(){
            count++;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        LongTest longTest=new LongTest();
        AtomicLongTest atomicLongTest=new AtomicLongTest();
        VolatileTest volatileTest=new VolatileTest();

        List<Thread> workers = new ArrayList<>();
        for(int i=0;i<10000;i++){
            Thread t1=new Thread(new Runnable() {
                @Override
                public void run() {
                    longTest.addCount();
                    atomicLongTest.addCount();
                    volatileTest.addCount();
                }
            });

            t1.start();     //启动线程
            workers.add(t1);
        }
        for(Thread t1:workers){
            t1.join();
        }
        System.out.println("longTest = "+longTest.getCount());
        System.out.println("atomicLongTest = "+atomicLongTest.getCount());
        System.out.println("volatileTest = "+volatileTest.getCount());
    }
}
