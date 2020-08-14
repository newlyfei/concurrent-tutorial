package com.tutorial.netty.two;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Logger;

/**
 * @author lyf
 * @date 2020-08-13 10:08:16
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(EchoClientHandler.class.getName());

    private int counter;
    private static final String ECHO_REQ = "Hi, lyf. Welcome to Netty.$_";

    public EchoClientHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("This is: "+ ++counter +" ; times receive server : ["+ body + "]");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0; i<10; i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
