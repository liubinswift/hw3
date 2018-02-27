package com.viewscenes.util.business;

import java.text.*;
import java.util.*;

/**
 * <p>Title: 时间与字符串工具类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class StringDateUtil {
  public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public final static String TIME_FORMAT = "HH:mm:ss";
  public final static String CAL_FORMAT  = "yyyy-MM-dd";

  public StringDateUtil() {
  }

  /**
   * 时间字符串到Date类型.
   * @param timeStr String
   * @return Date
   */
  public static Date string2Date(String dateStr) throws Exception {
    Date strDate = null;
    if(dateStr==null || dateStr.length()==0){
      return strDate;
    }

    /**
     * 先要验证时间格式正确。以后再扩充。
     */
    try {
      SimpleDateFormat d = new SimpleDateFormat(DATE_FORMAT);
      ParsePosition pos = new ParsePosition(0);
      strDate = d.parse(dateStr, pos);
    }
    catch (Exception ex) {
      throw new Exception("字符转时间格式有误：" + ex.getMessage(), ex);
    }
    return strDate;
  }

  /**
   * Date类型格式化成字符串.
   * @param timeStr String
   * @return Date
   */
  public static String date2String(Date strDate) throws Exception {
    if(strDate==null){
      strDate = new Date();
    }
    String dateStr = null;
    /**
     * 先要验证时间格式正确。以后再扩充。
     */
    try {
      SimpleDateFormat d = new SimpleDateFormat(DATE_FORMAT);
      dateStr = d.format(strDate);
    }
    catch (Exception ex) {
      throw new Exception("时间转字符格式有误：" + ex.getMessage(), ex);
    }
    return dateStr;
  }

  /**
   * 检查字符串是否时期格式.
   * @param dateStr String
   * @return Date null不是,否则返回正确的日期.
   */
  public static Date checkString(String dateStr) {
    Date strDate = null;
    try {
      strDate = string2Date(dateStr);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return strDate;
  }

  /**
   * 根据时间字符串得到时间Timestamp，如"2004-01-02 4:45:34"可以转换成同样日期的Date
   * @param dateStr String
   * @throws SysDateException
   * @return Date
   */
  public static java.sql.Timestamp getTimestamp(String timeStr) throws
      Exception {
    try {
      return new java.sql.Timestamp(getDateTime(timeStr).getTime());
    }
    catch (Exception e) {
      throw new Exception("字符转时间格式有误：" + e.getMessage(), e);
    }
  }

  /**
   * 根据时间字符串得到时间Date，如"2004-01-02 4:45:34"可以转换成同样日期的Date
   * @param dateStr String
   * @throws SysDateException
   * @return Date
   */
  public static Date getDateTime(String dateTimeStr) throws Exception {
    try {
      if (dateTimeStr == null) {
        return null;
      }
      SimpleDateFormat sd = new SimpleDateFormat(DATE_FORMAT);
      return sd.parse(dateTimeStr);
    }
    catch (Exception e) {
      throw e;
    }
  }
  
  public static Date getDayTime(String dateTimeStr) throws Exception {
	    try {
	      if (dateTimeStr == null) {
	        return null;
	      }
	      SimpleDateFormat sd = new SimpleDateFormat(CAL_FORMAT);
	      return sd.parse(dateTimeStr);
	    }
	    catch (Exception e) {
	      throw e;
	    }
  }

  public static String getTimeHour(Date time) throws Exception {
    try {
      Calendar cd = Calendar.getInstance();
      cd.setTime(time);
      String hour = String.valueOf(cd.get(Calendar.HOUR_OF_DAY));
      if (hour.length() == 1) {
        hour = "0" + hour;
      }
      return hour;

    }
    catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  public static String getTimeMin(Date time) throws Exception {
    try {
      Calendar cd = Calendar.getInstance();
      cd.setTime(time);
      String min = String.valueOf(cd.get(Calendar.MINUTE));
      if (min.length() == 1) {
        min = "0" + min;
      }
      return min;

    }
    catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  public static String getTimeSec(Date time) throws Exception {
    try {
      Calendar cd = Calendar.getInstance();
      cd.setTime(time);
      String sec = String.valueOf(cd.get(Calendar.SECOND));
      if (sec.length() == 1) {
        sec = "0" + sec;
      }
      return sec;

    }
    catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  public static String getTimeYear(Date time) throws Exception {
    try {
      Calendar cd = Calendar.getInstance();
      cd.setTime(time);
      String year = String.valueOf(cd.get(Calendar.YEAR));
      return year;

    }
    catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * 李纲：发现原来的getTimeMonth函数(把Date对象中的月份值转换成字符串型的月份值)存在以下两个问题：
   * （1）、Calendar.MONTH返回值为月份值减1，即返回0代表1月份，返回9代表10月份，所以应该使用Calendar.MONTH < 9判断月份值是否小于10月份
   * （2）、对于大于等于10月份的处理没能进行加1操作，导致11月份被处理成10月份
   * @param time Date
   * @throws Exception
   * @return String
   */
  public static String getTimeMonth(Date time) throws Exception {
    try {
      Calendar cd = Calendar.getInstance();
      cd.setTime(time);
      int m = cd.get(Calendar.MONTH)+ 1;
      String month = m < 10 ? "0" + m : "" + m;
      return month;
    }
    catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  public static String getTimeDay(Date time) throws Exception {
    try {
      Calendar cd = Calendar.getInstance();
      cd.setTime(time);
      String day = String.valueOf(cd.get(Calendar.DAY_OF_MONTH));
      if (day.length() == 1) {
        day = "0" + day;
      }
      return day;

    }
    catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * 返回A是否易于B
   * 比较两个date(2004-06-02 13:15:24 )字符串的大小,如果第一个晚于第二个就返回true，否则false
   * 并且在这个方法中，如果输入的两个日期的格式有问题，那么DateA和DateB将返回null，这里也将返回true值
   * @param dateA 开始时间
   * @param dateB 结束时间
   * @return
   */
  public static boolean isAfter(String dateAStr, String dateBStr) throws
      Exception {
    boolean result = true;
    Date dateA = string2Date(dateAStr);
    Date dateB = string2Date(dateBStr);
    if ( (dateA != null) && (dateB != null)) {
      try {
        result = dateA.after(dateB);
      }
      catch (Exception e) {}
    }
    return result;
  }

  /**
   * 返回时间A是否在时间B与时间C的中间（不带等于）。
   * @param dateAStr String
   * @param dateBStr String
   * @param dateCStr String
   * @throws Exception
   * @return boolean
   */
  public static boolean isMiddle(String dateAStr, String dateBStr,
                                 String dateCStr) throws Exception {
    boolean result = true;
    Date dateA = string2Date(dateAStr);
    Date dateB = string2Date(dateBStr);
    Date dateC = string2Date(dateCStr);
    if ( (dateA != null) && (dateB != null) && dateC != null) {
      try {
        if(dateA.after(dateB) && dateC.after(dateA) ){
          result = true;
        }
        else{
          result = false;
        }
      }
      catch (Exception e) {}
    }
    return result;

  }

  /**
   * 李纲：发现原来的getTimeMonth函数(把Date对象中的月份值转换成字符串型的月份值)存在以下两个问题：
   * （1）、Calendar.MONTH返回值为月份值减1，即返回0代表1月份，返回9代表10月份，所以应该使用Calendar.MONTH < 9判断月份值是否小于10月份
   * （2）、对于大于等于10月份的处理没能进行加1操作，导致11月份被处理成10月份
   * @param time Date
   * @throws Exception
   * @return String
   */
  public static String getTimeMonthLG(Date time){
    Calendar cd = Calendar.getInstance();
    cd.setTime(time);
    int m = cd.get(Calendar.MONTH)+ 1;
    String month = m < 10 ? "0" + m : "" + m;
    return month;
  }

}
