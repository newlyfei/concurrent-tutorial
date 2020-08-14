package com.tutorial.thread.deadlock;

/**
 * 顺序死锁
 * 查看死锁：
 * jps：查看java进程ID
 * jstack: jstack <进程ID>，查看stack信息
 */
public class LiftRightDeadLock {
    private final Object left=new Object();
    private final Object right=new Object();

    public void leftRight(){
        synchronized (left){
            synchronized (right){
                System.out.println("left-right"+Thread.currentThread().getName());
            }
        }
    }

    public void rightLeft(){
        synchronized (right){
            synchronized (left){
                System.out.println("right-left"+Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        LiftRightDeadLock liftRightDeadLock=new LiftRightDeadLock();
        for(int i=0;i<50;i++){
            Thread t1=new Thread(new Runnable() {
                @Override
                public void run() {
                    liftRightDeadLock.leftRight();
                }
            });

            Thread t2=new Thread(new Runnable() {
                @Override
                public void run() {
                    liftRightDeadLock.rightLeft();
                }
            });

            t1.start();
            t2.start();
        }
    }
}
