package com.viewscenes.timeTask;

import java.util.Calendar;
import java.util.Date;

import com.viewscenes.util.StringTool;

public class DailyIterator implements ScheduleIterator {
    private final int calendarType,interval,hourOfDay, minute, second;
    private final Calendar calendar = Calendar.getInstance();

    /**
     * 
     * @param calendarType 参加计算日期值的类型
     * @param interval     日期值，类型由calendarType参数决定
     * @param hourOfDay    时
     * @param minute       分
     * @param second	        秒
     */
    public DailyIterator(int calendarType,int interval,int hourOfDay, int minute, int second)
    {
        this(calendarType,interval,hourOfDay, minute, second, new Date());
    }

    public DailyIterator(int calendarType,int interval,int hourOfDay, int minute, int second, Date date)
    {
    	this.calendarType = calendarType;
    	this.interval = interval;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.second = second;
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        if (!calendar.getTime().before(date)) 
        {
            calendar.add(calendarType, -interval);
        }
//        else{
//        	calendar.setTime(date);
//        }
   
    }

    public Date next()
    {//获取下一个触发的时间点
        calendar.add(calendarType, interval);
        return calendar.getTime();
    }
    
    
    public static void main(String[] args){
    	DailyIterator d = new DailyIterator(Calendar.DATE,1,01,10,0);
    	System.out.println(StringTool.Date2String(d.next()));
//    	int a = 1;
//    	System.out.println(-a);
    }

}
