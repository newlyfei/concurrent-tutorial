package com.tutorial.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch=new CountDownLatch(3);
        Waiter waiter=new Waiter(latch);
        Decrementer decrementer=new Decrementer(latch);

        new Thread(waiter).start();
        new Thread(decrementer).start();

        Thread.sleep(4000);
    }
}

class Waiter implements Runnable{
    CountDownLatch latch=null;

    public Waiter(CountDownLatch latch){
        this.latch=latch;
    }

    @Override
    public void run() {
        System.out.println("Waiter await");
        try{
            latch.await();      //主线程的操作就会在这个方法上阻塞，直到其他线程完成各自的任务
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Waiter Released");
    }
}


class Decrementer implements Runnable{
    CountDownLatch latch=null;

    public Decrementer(CountDownLatch latch){
        this.latch=latch;
    }

    @Override
    public void run() {
        try{
            Thread.sleep(1000);
            this.latch.countDown();

            System.out.println(this.latch.getCount());

            Thread.sleep(1000);
            this.latch.countDown();

            System.out.println(this.latch.getCount());

            Thread.sleep(1000);
            this.latch.countDown();

            System.out.println(this.latch.getCount());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}