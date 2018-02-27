package com.viewscenes.timeTask;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Scheduler 
{
    class SchedulerTimerTask extends TimerTask 
    {
        private SchedulerTask schedulerTask;
        private ScheduleIterator iterator;
        public SchedulerTimerTask(SchedulerTask schedulerTask,ScheduleIterator iterator)
        {
            this.schedulerTask = schedulerTask;
            this.iterator = iterator;
        }
        public void run() 
        {
            schedulerTask.run();
            reschedule(schedulerTask, iterator);
        }
    }

    private final Timer timer = new Timer();

    private int state = -1;
    
    public Scheduler() 
    {
    }
    public void cancel() 
    {
        timer.cancel();
    }
    public void schedule(SchedulerTask schedulerTask,ScheduleIterator iterator) 
    {
        Date time = iterator.next();
        if (time == null) 
        {
            schedulerTask.cancel();
        }
        else 
        {
            synchronized(schedulerTask.lock)
            {
                if (schedulerTask.state != SchedulerTask.VIRGIN) 
                {
                    throw new IllegalStateException("Task already scheduled " +
                        "or cancelled");
                }
                schedulerTask.state = SchedulerTask.SCHEDULED;
                schedulerTask.timerTask = new SchedulerTimerTask(schedulerTask, iterator);
                timer.schedule(schedulerTask.timerTask, time);
            }
        }
        
        state = schedulerTask.state;
    }

    private void reschedule(SchedulerTask schedulerTask,ScheduleIterator iterator)
    {
        Date time = iterator.next();
        if (time == null)
        {
            schedulerTask.cancel();
        } 
        else 
        {
            synchronized(schedulerTask.lock)
            {
                if (schedulerTask.state != SchedulerTask.CANCELLED) 
                {
                    schedulerTask.timerTask = new SchedulerTimerTask(schedulerTask, iterator);
                    timer.schedule(schedulerTask.timerTask, time);
                }
            }
        }
    }
    
    public int getState(){
    	return state;
    }
}