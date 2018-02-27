package com.viewscenes.task.thread;

import com.viewscenes.task.TaskProcessor;

public class TaskWorkThread extends Thread{
    public TaskWorkThread(){

    }

    public void run(){
        while (true) {
            TaskProcessor task = (TaskProcessor)TaskManagerThread.taskQueue.get();
            if(task == null){
                task = (TaskProcessor)TaskManagerThread.taskQueue.get();
                boolean b = task.process();
                if(!b){
                    task.recordError(task);
               }
            }
        }
    }
}
