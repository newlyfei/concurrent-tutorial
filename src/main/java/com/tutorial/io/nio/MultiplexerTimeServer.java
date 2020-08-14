package com.tutorial.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lyf
 * @date 2020-08-11 14:14:37
 */
public class MultiplexerTimeServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);   //设置为非阻塞模式
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //注册多路复用器selection,监听accept事件
            System.out.println("The time server is start in port: " + port);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()){
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    }catch (Exception e){
                        if(key!=null){
                            key.cancel();
                            if(key.channel()!=null){
                                key.channel().close();
                            }
                        }
                    }
                }
            }catch (Throwable t){
                t.printStackTrace();
            }
        }

        if(selector!=null){
            try {
                selector.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 接收客户端的数据
     * @param key
     * @throws IOException
     */
    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            if(key.isAcceptable()){
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            }

            if(key.isReadable()){
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);  //开辟1M的缓冲区
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0){
                    readBuffer.flip();  //设置缓存区当前limit的position为0，用于后续对缓冲区的读取操作
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);  //将缓冲区可读的字节数组复制到字节数组中

                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order : " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    doWrite(sc, currentTime);

                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                } else;
            }
        }
    }

    /**
     * 通过通道channel向客户端发送数据
     * @param channel
     * @param response
     * @throws IOException
     */
    private void doWrite(SocketChannel channel, String response) throws IOException {
        if(response != null && response.trim().length() >0 ){
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }
}
