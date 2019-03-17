package com.tutorial.concurrent.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Memoizer2<A,V> implements Computable<A,V> {
    private final Map<A,V> cache=new ConcurrentHashMap<A,V>();
    private final Computable<A,V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * 多线程可以并发地使用它
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if(result==null){
            result=c.compute(arg);  //当两个线程同时调用compute时，可能会导致计算得到相同的值
            cache.put(arg,result);
        }
        return result;
    }
}
