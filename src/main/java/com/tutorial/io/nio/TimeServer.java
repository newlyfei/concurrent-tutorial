package com.tutorial.io.nio;

/**
 * NIO时间服务器
 *
 * @author lyf
 * @date 2020-08-11 14:02:57
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        if (args!=null && args.length>0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){

            }
        }

        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}
