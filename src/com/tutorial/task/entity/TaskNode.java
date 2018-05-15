package com.tutorial.task.entity;

public class TaskNode implements Comparable<TaskNode>{
    /**
     * 执行时间
     */
    private long extime;

    public long getExtime() {
        return extime;
    }

    public void setExtime(long extime) {
        this.extime = extime;
    }

    @Override
    public int compareTo(TaskNode o) {
        return (int) (this.extime - o.extime);
    }
}
