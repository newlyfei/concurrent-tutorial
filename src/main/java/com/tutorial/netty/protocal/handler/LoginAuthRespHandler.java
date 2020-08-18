package com.tutorial.netty.protocal.handler;

import com.tutorial.netty.protocal.constant.MessageType;
import com.tutorial.netty.protocal.entity.Header;
import com.tutorial.netty.protocal.entity.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lyf
 * @date 2020-08-18 08:55:59
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {
    private Map<String,Boolean> nodeCheck = new ConcurrentHashMap<>();
    private String[] whiteList = {"127.0.0.1", ""};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;

        if(message.getHeader()!=null
                && message.getHeader().getType() == MessageType.LOGIN_REQ.value()) {
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp = null;
            if(nodeCheck.containsKey(nodeIndex)){
                loginResp = buildResponse((byte) -1);
            }else {
                InetSocketAddress address = (InetSocketAddress)ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOK = false;
                for(String wip: whiteList){
                    if(wip.equals(ip)){
                        isOK = true;
                        break;
                    }
                }

                loginResp = isOK ? buildResponse((byte)0) : buildResponse((byte) -1);
                if(isOK){
                    nodeCheck.put(nodeIndex,true);
                }
                System.out.println("The login response id: "+ loginResp +" body ["+loginResp.getBody() +"]");
                ctx.writeAndFlush(loginResp);
            }
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildResponse(byte result) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.value());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
