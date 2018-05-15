package com.tutorial.task.service;

import com.tutorial.task.entity.TaskNode;

public interface TaskManager {

    void addTask(TaskNode node);

    TaskNode getTask(long time);
}
