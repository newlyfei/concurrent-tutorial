package com.tutorial.netty.protocal.constant;

/**
 * @author lyf
 * @date 2020-08-17 14:50:29
 */
public enum  MessageType {
    LOGIN_REQ(Byte.valueOf("1")),
    LOGIN_RESP(Byte.valueOf("2")),
    HEARTBEAT_REQ(Byte.valueOf("3")),
    HEARTBEAT_RESP(Byte.valueOf("4"));

    private byte value;

    MessageType(byte value) {
        this.value = value;
    }

    public byte value() {
        return value;
    }
}
