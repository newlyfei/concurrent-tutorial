package com.tutorial.io.aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author lyf
 * @date 2020-08-12 09:17:56
 */
public class AsyncTimeServerHandler implements Runnable {
    private int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try{
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port: " + port);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();

        try {
            latch.await();  //导致当前线程等到锁存器计数到零，除非线程是 interrupted 。
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void doAccept(){
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
