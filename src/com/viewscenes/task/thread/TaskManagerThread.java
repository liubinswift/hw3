package com.viewscenes.task.thread;

import com.viewscenes.util.ObjectQueue;

public class TaskManagerThread{
     final int threaCount = 20;
     public static ObjectQueue taskQueue;
     static TaskWorkThread[] workThread = null;

    public TaskManagerThread(){
        taskQueue = new ObjectQueue();
        System.out.println("<<<<<<<<<<批量管理线程启动>>>>>>>>>>>");
        initTask();
    }

    public void initTask(){
        workThread = new TaskWorkThread[threaCount];
        for (int i = 0; i < threaCount; i++) {
            workThread[i] = new TaskWorkThread();
            workThread[i].setPriority(Thread.NORM_PRIORITY - 2);
            workThread[i].start();
        }
    }
}
