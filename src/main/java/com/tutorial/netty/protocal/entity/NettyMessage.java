package com.tutorial.netty.protocal.entity;

import java.io.Serializable;

/**
 * @author lyf
 * @date 2020-08-17 11:02:39
 */
public final class NettyMessage implements Serializable {
    private Header header;
    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
