package com.tutorial.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Vector是一个线程安全的容器
 */
public class VectorMultiThread {
    static List<Integer> al=new Vector<Integer>(10);

    public static class AddThread implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<1000000;i++){
                al.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new AddThread());
        Thread t2=new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(al.size());
    }
}
