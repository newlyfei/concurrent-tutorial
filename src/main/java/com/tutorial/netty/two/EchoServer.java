package com.tutorial.netty.two;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author lyf
 * @date 2020-08-12 15:08:44
 */
public class EchoServer {

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
            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
            socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
            socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
            socketChannel.pipeline().addLast(new EchoServerHandler());
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

        new EchoServer().bind(port);
    }
}
