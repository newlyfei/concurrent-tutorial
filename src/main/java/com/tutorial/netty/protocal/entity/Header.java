package com.tutorial.netty.protocal.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lyf
 * @date 2020-08-17 11:03:26
 */
public class Header implements Serializable {
    private int crcCode = 0xabef0101;
    private int length; //消息长度
    private long sessionId; //会话ID
    private byte type;  //消息类型
    private byte priority;  //消息优先级
    private Map<String,Object> attachment= new HashMap<>();

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }
}
