package com.tutorial.executor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskExecutionWebClient {
    private static final int NTHREADS=10000;
    private static final Executor exec= Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        for (int i=0;i<NTHREADS;i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    handleResponse();
                }
            };
            exec.execute(runnable);
        }
    }

    static void handleResponse(){
        try {
            Socket socket=new Socket("localhost",80);
            OutputStream outputStream=socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(outputStream);
            printWriter.print("客户端内容 ："+Thread.currentThread().getName());
            printWriter.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
