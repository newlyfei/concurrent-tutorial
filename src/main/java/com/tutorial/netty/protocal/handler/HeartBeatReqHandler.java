package com.tutorial.netty.protocal.handler;

import com.tutorial.netty.protocal.entity.Header;
import com.tutorial.netty.protocal.entity.NettyMessage;
import com.tutorial.netty.protocal.constant.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 心跳检测
 *
 * @author lyf
 * @date 2020-08-17 14:46:41
 */
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {
    private volatile ScheduledFuture<?> heartBeat;

    /**
     * 向服务器发送消息
     * @param ctx
     * @throws Exception
     */
//    @Override
////    public void channelActive(ChannelHandlerContext ctx) throws Exception {
////        NettyMessage message = new NettyMessage();
////        Header header=new Header();
////        header.setType(MessageType.HEARTBEAT_REQ.value());
////        message.setHeader(header);
////        ctx.writeAndFlush(message);
////    }

    /**
     * 读取服务器端返回的数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if(message.getHeader()!= null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()){
            //每5秒发送一条心跳消息
            heartBeat = ctx.executor().scheduleAtFixedRate(
                    new HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS
            );
        } else if(message.getHeader()!=null && message.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()){
            System.out.println("Client receive server heart beat message : ---> "+ message);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private class HeartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;

        public HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            NettyMessage heatBeat = buildHeatBeat();
            System.out.println("Client send heart beat message to server : -------> " + heatBeat);
            ctx.writeAndFlush(heatBeat);
        }

        private NettyMessage buildHeatBeat(){
            NettyMessage message = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            message.setHeader(header);
            return message;
        }
    }
}
