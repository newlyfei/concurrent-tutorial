package com.tutorial.io.aio.client;

import com.tutorial.io.nio.TimeClientHandle;

/**
 * @author lyf
 * @date 2020-08-11 17:29:26
 */
public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;
        if(args!=null && args.length>0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){

            }
        }

        new Thread(new TimeClientHandle("127.0.0.1",port),"TimeClient-001").start();
    }
}
