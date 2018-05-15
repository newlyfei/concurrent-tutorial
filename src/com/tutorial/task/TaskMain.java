package com.tutorial.task;

import com.tutorial.task.entity.TaskNode;
import com.tutorial.task.entity.UserNode;
import com.tutorial.task.entity.VirtualNode;
import com.tutorial.task.service.TaskManager;
import com.tutorial.task.service.impl.TaskManagerImpl;

import javax.annotation.PostConstruct;

public class TaskMain implements Runnable{
    private TaskManager taskManager;

    public TaskMain(){
        taskManager=new TaskManagerImpl();
        init();
    }

    public void init(){
        taskManager.addTask(new UserNode(1,"java","ssss"));
        taskManager.addTask(new UserNode(2,"php","ssss"));
        taskManager.addTask(new VirtualNode(1,"beijing","sssssssss"));
        taskManager.addTask(new VirtualNode(1,"tianjin","sssssssss"));
    }

    @Override
    public void run() {
        while (true){
            long time=System.currentTimeMillis();
            TaskNode node=this.taskManager.getTask(time);
            if(node!=null){
                if(node instanceof UserNode){
                    UserNode user=(UserNode)node;
                    //调用服务执行自己的逻辑
                    System.out.println("user name:"+user.getUserName());
                }else if(node instanceof VirtualNode){
                    VirtualNode virtual=(VirtualNode)node;
                    System.out.println("virtual name:"+virtual.getVirName());
                }
            }else {
                try {
                    Thread.sleep(60*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        TaskMain taskMain=new TaskMain();

        new Thread(taskMain).start();


        //多次写入数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    taskMain.init();
                    try {
                        Thread.sleep(60*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
