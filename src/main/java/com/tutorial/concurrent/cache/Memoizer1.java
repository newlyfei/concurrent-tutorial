package com.tutorial.concurrent.cache;

import java.util.HashMap;
import java.util.Map;

public class Memoizer1<A,V> implements Computable<A,V> {
    private final Map<A,V> cache=new HashMap<A,V>();
    private final Computable<A,V> c;

    public Memoizer1(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * 每次只有一个线程能够执行该方法，可能被阻塞很长时间
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if(result==null){
            result=c.compute(arg);
            cache.put(arg,result);
        }
        return result;
    }
}
