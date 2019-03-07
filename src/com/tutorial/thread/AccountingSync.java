package com.tutorial.thread;

/**
 * 同一对象同步锁
 */
public class AccountingSync implements Runnable {
    static AccountingSync accountingSync=new AccountingSync();
    static int i=0;
    @Override
    public void run() {
        for(int j=0;j<10000000;j++){
            synchronized (accountingSync){
                i++;
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(accountingSync);
        Thread t2=new Thread(accountingSync);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }
}
