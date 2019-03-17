package com.tutorial.concurrent.volat;

/**
 * 典型用法：检查某个状态标记以判断是否退出循环。
 *
 * volatile具备两种特性，第一就是保证共享变量对所有线程的可见性。将一个共享变量声明为volatile后：
 * 1.当写一个volatile变量时，JMM会把该线程对应的本地内存中的变量强制刷新到主内存中去；
 * 2.这个写会操作会导致其他线程中的缓存无效。
 */
public class VolatileExample {
//    private volatile boolean status=false;
    private boolean status=false;   //此时线程t2可能看不到status的变更，导致不能正常输出

    public void changeStatus(){
        status=true;
    }

    public void run(){
        if(status){
            System.out.println("runing...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileExample volatileExample=new VolatileExample();
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                volatileExample.changeStatus();
            }
        });

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                volatileExample.run();
            }
        });

        t1.start();
        t2.start();
    }
}
