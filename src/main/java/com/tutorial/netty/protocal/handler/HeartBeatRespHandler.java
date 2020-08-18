package com.tutorial.netty.protocal.handler;

import com.tutorial.netty.protocal.entity.Header;
import com.tutorial.netty.protocal.entity.NettyMessage;
import com.tutorial.netty.protocal.constant.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务器端的心跳应答
 *
 * @author lyf
 * @date 2020-08-17 15:14:47
 */
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端请求的数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if(message.getHeader()!=null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()){
            System.out.println("Receive client heart beat message: ---> " + message);
            NettyMessage heartBeat = buildHeatBeat();
            System.out.println("Send heart beat response message to client: --->" + heartBeat);
            ctx.writeAndFlush(heartBeat);
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildHeatBeat(){
        NettyMessage message = new NettyMessage();
        Header header=new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }
}
