package com.viewscenes.timeTask;
import java.util.Date;

public interface ScheduleIterator 
{
    public Date next();//获取下一个触发的时间点
}