package com.viewscenes.util;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Date;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author wangyang
 * @version 1.0
 */

public class LongCalendar {

  /**
   * 名称：获得当前长整形日期方法
   * 功能：获得系统中使用长整形表示的日期
   * 输入参数：
   * 返回参数：long:表示的日期长整形值
   */

  public static long getLongCalendar() {
    try {
      long longCalendar = 0;
      //获得当前日期
      Calendar cldCurrent = Calendar.getInstance();
      //获得年月日
      String strYear  = String.valueOf(cldCurrent.get(Calendar.YEAR));
      String strMonth = String.valueOf(cldCurrent.get(Calendar.MONTH));
      String strDate  = String.valueOf(cldCurrent.get(Calendar.DATE));
      //整理格式
      if (strMonth.length() < 2) {
        strMonth = "0" + strMonth;
      }
      if (strDate.length() < 2) {
        strDate = "0" + strDate;
      }

      //组合结果
      longCalendar = Long.parseLong(strYear + strMonth + strDate);

    //系统默认月份加一
      longCalendar+=100L;

      //创建上初始化上下文环境并返回
      return longCalendar;
    } catch(Exception Exp) {
      return 0;
    }
  }

  public static long getLongTime() {
    try {
      long longCalendar = 0;

      //获得当前日期
      Calendar cldCurrent = Calendar.getInstance();

      //获得年月日
      String strYear  = String.valueOf(cldCurrent.get(Calendar.YEAR));
      String strMonth = String.valueOf(cldCurrent.get(Calendar.MONTH)+1);
      String strDate  = String.valueOf(cldCurrent.get(Calendar.DATE));
      String strHour  = String.valueOf(cldCurrent.get(Calendar.HOUR));
      String strAM_PM = String.valueOf(cldCurrent.get(Calendar.AM_PM));
      String strMinute= String.valueOf(cldCurrent.get(Calendar.MINUTE));
      String strSecond= String.valueOf(cldCurrent.get(Calendar.SECOND));

          //把时间转换为24小时制
      //strAM_PM=="1",表示当前时间是下午，所以strHour需要加12
      if(strAM_PM.equals("1")){
        strHour=String.valueOf(Long.parseLong(strHour)+12);
      }

          //整理格式
      if (strMonth.length() < 2) {
        strMonth = "0" + strMonth;
      }
      if (strDate.length() < 2) {
        strDate = "0" + strDate;
      }
      if (strHour.length() < 2) {
        strHour = "0" + strHour;
      }
      if (strMinute.length() < 2) {
        strMinute = "0" + strMinute;
      }
      if (strSecond.length() < 2) {
        strSecond = "0" + strSecond;
      }
      //组合结果
      longCalendar = Long.parseLong(strYear + strMonth + strDate + strHour + strMinute + strSecond );

      //创建上初始化上下文环境并返回
      return longCalendar;
    } catch(Exception Exp) {
      return 0;
    }
  }

  /**
   * 名称：getCurrentDateTime
   * 功能：得到系统当前时间显示，格式：yyyymmddhhmmss
   * 输入参数：String:系统当前日期(格式：yyyymmdd)
   * 返回参数：long:系统当前日期时间
   */

  public static String getCurrentDateTimeByParaT(int intDate){
    String strCalendar = "";
    //获得当前日期
    Calendar cldCurrent = Calendar.getInstance();

    //获得年月日
    String strYear  = String.valueOf(cldCurrent.get(Calendar.YEAR));
    String strMonth = String.valueOf(cldCurrent.get(Calendar.MONTH)+1);
    cldCurrent.set(Calendar.DATE,Calendar.DATE+intDate);
    String strDate  = String.valueOf(cldCurrent.DATE);

    String strHour  = String.valueOf(cldCurrent.get(Calendar.HOUR));
    String strAM_PM = String.valueOf(cldCurrent.get(Calendar.AM_PM));
    String strMinute= String.valueOf(cldCurrent.get(Calendar.MINUTE));
    String strSecond= String.valueOf(cldCurrent.get(Calendar.SECOND));

        //把时间转换为24小时制
    //strAM_PM=="1",表示当前时间是下午，所以strHour需要加12
    if(strAM_PM.equals("1")){
      strHour=String.valueOf(Long.parseLong(strHour)+12);
    }

    //整理格式
    if (strMonth.length() < 2) {
      strMonth = "0" + strMonth;
    }
    if (strDate.length() < 2) {
      strDate = "0" + strDate;
    }
    if (strHour.length() < 2) {
      strHour = "0" + strHour;
    }
    if (strMinute.length() < 2) {
      strMinute = "0" + strMinute;
    }
    if (strSecond.length() < 2) {
      strSecond = "0" + strSecond;
    }
    //组合结果
    strCalendar = strYear +"-"+ strMonth +"-"+ strDate +" "+ strHour +":"+ strMinute +":"+ strSecond ;


    return strCalendar;
  }

  public static String getCurrentDateTimeByPara(int intDate){
          GregorianCalendar worldTour = new GregorianCalendar();
          worldTour.add(GregorianCalendar.DATE, intDate);
          Date d = worldTour.getTime();
          SimpleDateFormat df=new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
          return df.format(d);
      }

  public static void main(String [] args){
    //获得当前日期，年月日
    System.out.println(LongCalendar.getLongCalendar());
    //获得当前时间，年月日时分秒
    System.out.println(LongCalendar.getLongTime());
    //根据参数获得当前日期时间的相对日期时间
    System.out.println(LongCalendar.getCurrentDateTimeByPara(-2));
  }

}
