package com.tutorial.netty.one;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author lyf
 * @date 2020-08-12 15:08:44
 */
public class TimeServer {

    public void bind(int port) throws Exception {
        /**
         * 创建两个线程组
         * 一个用于处理服务器端接收的客户端连接
         * 一个用于网络通信的网络读写
         */
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)  //指定NIO模式：NioServerSocketChannel对应TCP，NioDatagramChannel对应UDP
                    .option(ChannelOption.SO_BACKLOG,1024) //设置TCP缓冲区
                    .childHandler(new ChildChannelHandler());
            //绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args!=null && args.length>0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){

            }
        }

        new TimeServer().bind(port);
    }
}
