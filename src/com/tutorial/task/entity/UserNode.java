package com.tutorial.task.entity;

public class UserNode extends TaskNode {
    private int userId;
    private String userName;
    private String description;

    public UserNode(int userId, String userName, String description) {
        this.userId = userId;
        this.userName = userName;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(TaskNode o) {
        int ret=1;
        if(o instanceof UserNode){
            UserNode node=(UserNode)o;
            ret=this.userId-node.userId;
        }
        return ret;
    }
}
