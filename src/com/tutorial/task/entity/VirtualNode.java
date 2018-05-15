package com.tutorial.task.entity;

public class VirtualNode extends TaskNode {
    private int orderId;
    private String virName;
    private String virDesc;

    public VirtualNode(int orderId, String virName, String virDesc) {
        this.orderId = orderId;
        this.virName = virName;
        this.virDesc = virDesc;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getVirName() {
        return virName;
    }

    public void setVirName(String virName) {
        this.virName = virName;
    }

    public String getVirDesc() {
        return virDesc;
    }

    public void setVirDesc(String virDesc) {
        this.virDesc = virDesc;
    }

    @Override
    public int compareTo(TaskNode o) {
        int ret=1;
        if(o instanceof VirtualNode){
            VirtualNode node=(VirtualNode)o;
            ret=this.orderId-node.orderId;
        }
        return ret;
    }
}
