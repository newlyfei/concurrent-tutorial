package com.tutorial.executor.core;

import java.util.concurrent.Executor;

/**
 * 为每一个请求启动一个新线程的Executor
 */
public class ThreadPerTaskExecutor implements Executor {
    @Override
    public void execute(Runnable r) {
        new Thread(r).start();
    }
}
