package com.tutorial.executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskExecutionWebServer {
    private static final int NTHREADS=100;
    private static final Executor exec= Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket socket=new ServerSocket(80);
        while (true){
            final Socket connection=socket.accept();
            Runnable task=new Runnable() {
                @Override
                public void run() {
                    handleRequest(connection);
                }
            };
            exec.execute(task);
        }
    }

    static void handleRequest(Socket connection){
        try {
            Thread.sleep(1000);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lineText="";
            while((lineText=in.readLine())!=null){
                System.out.println(String.format("会话：%s(%s),文本：%s",Thread.currentThread().getName(),Thread.currentThread().getId(),lineText));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
