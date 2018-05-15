package com.tutorial.task.service.impl;

import com.tutorial.task.entity.TaskNode;
import com.tutorial.task.service.TaskManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskManagerImpl implements TaskManager {
    private Lock lock = new ReentrantLock();

    private List<TaskNode> taskQueue = new ArrayList<TaskNode>();

    @Override
    public void addTask(TaskNode node) {
        try {
            this.lock.lock();
            this.taskQueue.add(node);
            Collections.sort(this.taskQueue);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public TaskNode getTask(long time) {
        TaskNode node = null;
        try {
            this.lock.lock();
            if (!this.taskQueue.isEmpty()) {
                node = this.taskQueue.get(0);
                if (node != null && node.getExtime() < time + 60 * 1000) {
                    this.taskQueue.remove(0);
                } else {
                    node = null;
                }
            }
            return node;
        } finally {
            this.lock.unlock();
        }
    }
}
