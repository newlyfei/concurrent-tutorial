package com.tutorial.concurrent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 计数信号量
 * @param <T>
 */
public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int bound){
        this.set= Collections.synchronizedSet(new HashSet<T>());
        sem=new Semaphore(bound);   //通过构造函数指定许可的初始数量
    }

    public boolean add(T o) throws InterruptedException{
        sem.acquire();  //获得许可证，如果有可用并立即返回，则将可用许可证数量减少一个，没有许可时将阻塞
        boolean wasAdded=false;
        try{
            wasAdded=set.add(o);
            return wasAdded;
        }finally {
            if(!wasAdded)
                sem.release();  //释放许可证，将其返回到信号量。
        }
    }

    public boolean remove(Object obj) {
        boolean wasRemoved=set.remove(obj);
        if(wasRemoved)
            sem.release();
        return wasRemoved;
    }

    public static void main(String[] args) {
        BoundedHashSet<String> boundedHashSet=new BoundedHashSet<>(1);
        int i=5;
        while(i>0){
            i--;

            new Thread(){
                @Override
                public void run() {
                    try {
                        Boolean result=boundedHashSet.add(new String("A"));
                        System.out.println(String.format("add is: %s",result));
//                        Thread.sleep(1000);
                        Boolean remove=boundedHashSet.remove(new String("A"));
                        System.out.println(String.format("remove is: %s",remove));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
