package com.tutorial.concurrent.cache;

/**
 * 计算接口，输入类型为A，输出类型为V
 * @param <A>
 * @param <V>
 */
public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
