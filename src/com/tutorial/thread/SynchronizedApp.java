package com.tutorial.thread;

/**
 * 一个对象有多个synchronized方法，只要一个线程访问了其中的一个synchronized方法，其它线程不能同时访问这个对象中任何一个synchronized方法
 * 不同对象实例的synchronized方法是不相干预的。也就是说，其它线程可以同时访问此类下的另一个对象实例中的synchronized方法；
 */
public class SynchronizedApp {
    private String name;

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) throws InterruptedException {
        this.name = name;
    }

    public static void main(String[] args) {
        SynchronizedApp synchronizedApp=new SynchronizedApp();
        new Thread(){
            public void run(){
                try {
                    synchronizedApp.setName("test");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread(){
            public void run(){
                System.out.println(synchronizedApp.getName());
            }
        }.start();
    }
}
