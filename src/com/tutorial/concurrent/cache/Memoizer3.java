package com.tutorial.concurrent.cache;

import java.util.Map;
import java.util.concurrent.*;

public class Memoizer3<A,V> implements Computable<A,V> {
    private final Map<A,Future<V>> cache=new ConcurrentHashMap<A,Future<V>>();  //使用Future
    private final Computable<A,V> c;

    public Memoizer3(Computable<A, V> c) {
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
        Future<V> f = cache.get(arg);
        if(f==null){    //先检查后执行，非原子操作，仍然可能出现计算相同的值
            Callable<V> eval=new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft=new FutureTask<V>(eval);
            f=ft;
            cache.put(arg,ft);
            ft.run();   //运行Callable下的call方法
        }
        try{
            return f.get();
        }catch (ExecutionException e){
            throw new InterruptedException(e.getMessage());
        }
    }
}
