package com.viewscenes.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.viewscenes.util.business.runplan.TimeSpan;



/**
 * <p>Title:TimeMethod</p>
 * <p>Description: 有关时间的一些处理方法</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author zr
 * @version 1.0
 */
public class TimeMethod {
  public TimeMethod() {
  }

  public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public final static String TIME_FORMAT = "HH:mm:ss";

  /**
   * 返回一个表示时间String的小时数值为int，比如13:15，将返回13
   * @param
   * @return
   */
  public static int get_hour(String time) {
    int hour = (new Integer(time.substring(0, time.indexOf(":")))).intValue();
    return hour;
  }
  /**
   * 返回一个表示时间的小时数值为int，比如00:15:00，将返回15
   * @param
   * @return
   */
//  public static int getMinutesFromtime(String time) {
////   String time.split(":")
////    return hour;
//  }
  /**
   * 比较两个周期时间(00:00:00)字符串的大小,如果第一个晚于第二个就返回true，否则false
   * 首先检查两个时间的字符串是否合法，有一个不合法的就返回false
   * @param
   * @return
   */
  public static boolean compare_time(String[] time) {
    //false：出现25：00：00 \u2014\u2014 01：00：00  ，而含有字母等无效字符，就返回null ；
    //true  允许出现25：00：00，并处理。，而含有字母等无效字符，就返回null ；
    String start_time = null;
    String end_time = null;
    try {
      start_time = TimeSpan.checkParse(time[0], false);
      end_time = TimeSpan.checkParse(time[1], false);
      if ( (start_time == null) || (end_time == null)) {
	return false;
      }
      long start = TimeSpan.parse(start_time, false);
      long end = TimeSpan.parse(end_time, false);
      //因为有跨天的情况，所以周期时间中不考虑起止时间的大小问题
      //if (start>=end) return false;
    }
    catch (Exception e) {}
    time[0] = start_time;
    time[1] = end_time;
    return true;
  }

  /**
   * 比较两个周期时间(00:00:00)字符串的大小,如果第一个晚于第二个就返回true，否则false
   * 首先检查两个时间的字符串是否合法，有一个不合法的就返回false
   * @param
   * @return true 开始时间大于等于结束时间；否则返回false
   */
  public static boolean compare_time(String start_time, String end_time) {
    //出现25：00：00 >>> 01：00：00  ，而含有字母等无效字符，就会将相应位和以后的位置为0；
    //如果格式完全错误就认为是0
    long start = 0;
    long end = 0;
    try {
      start = TimeSpan.parse(start_time);
      end = TimeSpan.parse(end_time);
    }
    catch (Exception e) {}
    if (start >= end) {
      return true;
    }
    return false;
  }

  /**
   * 检查周期时间(00:00:00)字符串的格式是否正确
   * @param
   * @return boolean true:yes  false:no
   */
  public static boolean checkParse(String time) {
    String a = TimeSpan.checkParse(time, false);
    if (a == null) {
      return false;
    }
    return true;
  }

  /**
   * 比较两个date(2004-06-02 13:15:24 )字符串的大小,如果第一个晚于第二个就返回true，否则false
   * 并且在这个方法中，如果输入的两个日期的格式有问题，那么DateA和DateB将返回null，这里也将返回true值
   * @param dateA 开始时间
   * @param dateB 结束时间
   * @return
   */
  public static boolean compare_date(String dateA, String dateB) {
    boolean result = true;
    Date DateA = stringToDate(dateA);
    Date DateB = stringToDate(dateB);
    if ( (DateA != null) && (DateB != null)) {
      try {
	result = DateA.after(DateB);
      }
      catch (Exception e) {}
    }
    return result;
  }

  /**
   * 将String转为Date
   * @param strDate 时间字符串
   * @return 日期
   */
  public static Date stringToDate(String strDate) {
    try {
      SimpleDateFormat d = new SimpleDateFormat(DATE_FORMAT);
      ParsePosition pos = new ParsePosition(0);
      Date currentTime = d.parse(strDate, pos);
      return currentTime;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 获得现在的日期
   * @param
   * @return
   */
  public static String get_nowdate() {
    Calendar now = Calendar.getInstance();
    java.util.Date now_time = now.getTime();
    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT,
	Locale.getDefault());
    String now_datetime = formatter.format(now_time);
    return now_datetime;
  }
  /**
   * 根据开始时间结束时间算出周几对应的所有日期。
   * @weekday  	all	每天
					0	周日
					1	周一
					2	周二
					3	周三
					4	周四
					5	周五
					6	周六
   * @return String[]
   */
	public static ArrayList getAllDataWeekdayFromStartTimeAndEndtime(String starttime,String endtime ,String weekday) {
	  ArrayList alltime=new ArrayList();
	  
	Date startDate=StringTool.stringToDate(starttime);
	Date endDate=StringTool.stringToDate(endtime);
	Calendar cal=Calendar.getInstance();
	cal.setTime(startDate);
    String week= (cal.get(Calendar.DAY_OF_WEEK)-1)+"";
    if(startDate.getTime()>=endDate.getTime())
    {
    	return alltime;//开始时间大于结束时间错误。
    }
    if(weekday.equalsIgnoreCase("all"))
    {
    	 startDate=StringTool.stringToDate(starttime.substring(0,10)+" 00:00:00");
    	 endDate=StringTool.stringToDate(endtime.substring(0,10)+" 00:00:00");
    	 while(startDate.getTime()<=endDate.getTime())
    	 {
    		 alltime.add(StringTool.Date2String(startDate).substring(0,10));	
    		 startDate.setDate(startDate.getDate()+1);
    	 }
    	
    	 
    }//循环每天执行。
    else
    {
	  if(week.compareTo(weekday)<0)//当前星期小于循环的话这周可以执行一次
	    {
	    	startDate.setDate(startDate.getDate()+(Integer.parseInt(weekday)-Integer.parseInt(week)));
	    	alltime.add(StringTool.Date2String(startDate).substring(0,10));
	    }else
	    {
	    	startDate.setDate(startDate.getDate()+(Integer.parseInt(weekday)-Integer.parseInt(week)+7));
	    	alltime.add(StringTool.Date2String(startDate).substring(0,10));	
	    }
    while(startDate.getTime()<=endDate.getTime())
    {
    	startDate.setDate(startDate.getDate()+7);
    	if(startDate.getTime()<=endDate.getTime())
    	{
    		alltime.add(StringTool.Date2String(startDate).substring(0,10));		
    	}
    }//循环周几
   }

    return alltime;
  }
  /**
   * 根据开始时间结束时间,任务开始时间结束时间计算单元30分钟为一单元。
   * @weekday  	all	每天
					0	周日
					1	周一
					2	周二
					3	周三
					4	周四
					5	周五
					6	周六
   * @return String[]
   */
  public static ArrayList getUnitTime30(String startdate,String enddate,String starttime,String endtime ,String weekday) {
	  ArrayList alltime=new ArrayList();
	    ArrayList list=getAllDataWeekdayFromStartTimeAndEndtime(startdate,enddate,weekday);  
	    String tempDate="";
	    String tempTime="";
	    ArrayList hourList=new ArrayList();
	   String[] split= starttime.split(":");
	    int shour=Integer.parseInt(split[0]);
	    int sminutes=Integer.parseInt(split[1]);
	    int ssecond=Integer.parseInt(split[2]);
	    String[] eplit= endtime.split(":");
	    int ehour=Integer.parseInt(eplit[0]);
	    int dminutes=Integer.parseInt(eplit[1]);
	    int dsecond=Integer.parseInt(eplit[2]);
	    if(sminutes>=30&&sminutes<=59)
	    {
	    	hourList.add(shour+":30:00");
	    }else
	    {
	    	
	    	hourList.add(shour+":00:00");
	    	if(shour!=ehour)
	    	{
	    	hourList.add(shour+":30:00");
	    	}
	    }
	    shour++;
	    while(shour<ehour)
	    {
	    	hourList.add(shour+":00:00");
	    	hourList.add(shour+":30:00");
	    	shour++;
	    }
	    
	    if(shour==ehour){
	    if(dminutes>30&&dminutes<=59)
	    {
	    	hourList.add(ehour+":00:00");
	    	hourList.add(ehour+":30:00");
	    }else if(dminutes>0||dsecond>0)
	    {
	    	hourList.add(ehour+":00:00");
	    }
	    }
	    for(int i=0;i<list.size();i++)
	    {
	    	 tempDate=(String) list.get(i);
	    	 for(int k=0;k<hourList.size();k++)
	    	 {
	    		
	    		 tempTime=(String) hourList.get(k);
	    		 alltime.add(tempDate+" "+tempTime);
	    	 }
	    	 
	    }

    return alltime;
  }

  public static void main(String[] args)
  {
	 
//	ArrayList list=TimeMethod.getUnitTime30("2012-08-22 13:30:00","2012-08-22 14:30:00","13:07:00","14:38:00","all");
//	for(int i=0;i<list.size();i++)
//	{
//		System.out.println(list.get(i));
//	}
//	 ArrayList list=new ArrayList();
//	 ArrayList list2=new ArrayList();
//	 list.add("123");
//	 list.add("123444");
//	 list2.add("123");
//	if(list.contains("123"))
//	{
//		list.remove("123");
//	}
	System.out.println("2012-12-12 00:00:00".substring(0,12));
  }
}