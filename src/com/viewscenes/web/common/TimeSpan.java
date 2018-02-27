package com.viewscenes.web.common;

/**
 * <p>Title: 运行图时间段</p>
 * <p>Description: 运行图时间段的封装</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company</p>
 * @author 
 * @version 1.0
 */

public class TimeSpan {
  private long startTime; //起始时刻 (秒)
  private long endTime; //结束时刻 (秒)
  private long type; // 运行图类型 1:播出,2:检修,3:停播
  private ProgramInfo programInfo;

  /**
   * 构造函数
   */
  public TimeSpan() {
    startTime = 0;
    endTime = 0;
    type = 0;
    programInfo = null;
  }

  /**
   * 构造函数
   * @param startTime (秒)
   * @param endTime (秒)
   * @param type 1:播出,2:检修
   */
  public TimeSpan(long startTime, long endTime, long type) {
    setStartTime(startTime);
    setEndTime(endTime);
    setType(type);
    programInfo = null;
  }

  public TimeSpan(long startTime, long endTime, long type,
		  ProgramInfo programInfo) {
    setStartTime(startTime);
    setEndTime(endTime);
    setType(type);
    setProgramInfo(programInfo);
  }

  /**
   * 构造函数
   * @param startTime  HH24:MI:SS
   * @param endTime HH24:MI:SS
   * @param type 1:播出,2:检修
   */
  public TimeSpan(String startTime, String endTime, String type) {
    setStartTime(startTime);
    setEndTime(endTime);
    setType(type);
  }

  public TimeSpan(String startTime, String endTime, String type,
		  ProgramInfo programInfo) {
    setStartTime(startTime);
    setEndTime(endTime);
    setType(type);
    setProgramInfo(programInfo);
  }

  /**
   * 返回开始时刻
   * @return (秒)
   */
  public long getStartTime() {
    return startTime;
  }

  /**
   * 设置开始时刻
   * @param startTime (秒)
   */
  public void setStartTime(long startTime) {
    if (startTime >= 24 * 3600 - 1 || startTime < 0) {
      startTime = 0;
    }
    this.startTime = startTime;
  }

  /**
   * 设置开始时刻
   * @param startStr HH24:MI:SS
   */
  public void setStartTime(String startStr) {
    this.startTime = parse(startStr);
    if (startTime >= 24 * 3600 - 1 || startTime < 0) {
      startTime = 0;
    }
  }

  /**
   * 获取结束时刻
   * @return (秒)
   */
  public long getEndTime() {
    return endTime;
  }

  /**
   * 设置结束时刻
   * @param endTime (秒)
   */
  public void setEndTime(long endTime) {
    if (endTime >= 24 * 3600 - 1 || endTime <= 0) {
      endTime = 24 * 3600;
    }
    this.endTime = endTime;
  }

  /**
   * 设置结束时刻
   * @param endStr HH24:MI:SS
   */
  public void setEndTime(String endStr) {
    this.endTime = parse(endStr);
    if (endTime >= 24 * 3600 - 1 || endTime <= 0) {
      endTime = 24 * 3600;
    }
  }

  /**
   * 返回总时长
   * @return (秒)
   */
  public long getInterval() {
    long returnValue = endTime - startTime;
    if (returnValue < 0) {
      returnValue += 24 * 3600;
    }
    return returnValue;
  }

  /**
   * 返回运行图类型
   * @return 1:播出,2:检修
   */
  public long getType() {
    return type;
  }

  /**
   * 设置运行图类型
   * @param type 1:播出,2:检修
   */
  public void setType(long type) {
    this.type = type;
  }

  /**
   * 设置运行图类型
   * @param type 1:播出,2:检修
   */
  public void setType(String type) {
    try {
      this.type = new Long(type).longValue();
    }
    catch (Exception e) {
      e.printStackTrace();
      this.type = 0;
    }
  }

  public ProgramInfo getProgramInfo() {
    return programInfo;
  }

  public void setProgramInfo(ProgramInfo programInfo) {
    this.programInfo = programInfo;
  }

  /**
   * 检查时间格式
   * @param str  HH24:MI:SS
   * @return 成功返回规范的 HH24:MI:SS 格式,否则返回 NULL
   */
  public static String checkParse(String str) {
    return checkParse(str, false, false);
  }

  /**
   * 检查时间格式
   * @param str  HH24:MI:SS
   * @param overDays true:允许跨天,false:24小时以内
   * @return 成功返回规范的 HH24:MI:SS 格式,否则返回 NULL
   */
  public static String checkParse(String str, boolean overDays) {
    return checkParse(str, overDays, false);
  }

  /**
   * 检查时间格式
   * @param str  HH24:MI:SS
   * @param overDays true:允许跨天,false:24小时以内
   * @param enableNegative true:允许负数,false:负数取反
   * @return 成功返回规范的 HH24:MI:SS 格式,否则返回 NULL
   */
  public static String checkParse(String str, boolean overDays,
				  boolean enableNegative) {
    String returnValue = null;
    try {
      long hour = 0;
      long min = 0;
      long sec = 0;
      boolean nagativeValue = false;
      String val[] = str.split(":");
      if (val.length > 0 && val[0].length() > 0) {
	if (val[0].charAt(0) == '-') {
	  nagativeValue = true;
	  hour = new Long(val[0].substring(1)).longValue();
	}
	else {
	  hour = new Long(val[0]).longValue();
	}
      }
      if (val.length > 1 && val[1].length() > 0) {
	min = new Long(val[1]).longValue();
      }
      if (val.length > 2 && val[2].length() > 0) {
	sec = new Long(val[2]).longValue();
      }
      if (sec > 59) {
	min += sec / 60;
	sec %= 60;
      }
      if (min > 59) {
	hour += min / 60;
	min %= 60;
      }
      if (sec < 0) {
	sec = 0;
      }
      if (min < 0) {
	min = 0;
      }
      long parseVal = hour * 3600 + min * 60 + sec;
      if (nagativeValue && enableNegative) {
	parseVal *= -1;
      }
      returnValue = format(parseVal, overDays, enableNegative);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return returnValue;
  }

  /**
   * 解析时间串,正数,24小时内
   * @param str HH24:MI:SS
   * @return (秒)
   */
  public static long parse(String str) {
    return parse(str, false, false);
  }

  /**
   * 解析时间串,正数
   * @param str HH24:MI:SS
   * @return (秒)
   */
  public static long parse(String str, boolean overDays) {
    return parse(str, overDays, false);
  }

  /**
   * 解析时间串
   * @param str HH24:MI:SS
   * @param overDays true:允许跨天,false:24小时以内
   * @param enableNegative true:允许负数,false:负数取反
   * @return (秒)
   */
  public static long parse(String str, boolean overDays, boolean enableNegative) {
    long returnValue = 0;
    long hour = 0;
    long min = 0;
    long sec = 0;
    boolean nagativeValue = false;
    try {
      String val[] = str.split(":");
      if (val.length > 0 && val[0].length() > 0) {
	if (val[0].charAt(0) == '-') {
	  nagativeValue = true;
	  hour = new Long(val[0].substring(1)).longValue();
	}
	else {
	  hour = new Long(val[0]).longValue();
	}
      }
      if (val.length > 1 && val[1].length() > 0) {
	min = new Long(val[1]).longValue();
      }
      if (val.length > 2 && val[2].length() > 0) {
	sec = new Long(val[2]).longValue();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (sec > 59) {
      min += sec / 60;
      sec %= 60;
    }
    if (min > 59) {
      hour += min / 60;
      min %= 60;
    }
    if (sec < 0) {
      sec = 0;
    }
    if (min < 0) {
      min = 0;
    }
    if (!overDays && (hour > 24 || min != 0 || sec != 0)) {
      hour %= 24;
    }
    returnValue = hour * 3600 + min * 60 + sec;
    if (nagativeValue && enableNegative) {
      returnValue *= -1;

    }
    return returnValue;
  }

  /**
   * 格式化字符串,正数,24小时内
   * @param val (秒)
   * @return HH24:MI:SS
   */
  public static String format(long val) {
    return format(val, false, false);
  }

  /**
   * 格式化字符串,正数
   * @param val (秒)
   * @return HH24:MI:SS
   */
  public static String format(long val, boolean overDays) {
    return format(val, overDays, false);
  }

  /**
   * 格式化字符串
   * @param val (秒)
   * @param overDays true:允许跨天,false:24小时以内
   * @param enableNegative true:允许负数,false:负数取反
   * @return HH24:MI:SS
   */
  public static String format(long val, boolean overDays,
			      boolean enableNegative) {
    String returnValue;
    long tmpVal;
    tmpVal = Math.abs(val / 3600);
    if (!overDays) {
      tmpVal %= 24;
    }
    String tmpStr = new Long(tmpVal).toString();
    if (tmpStr.length() < 2) {
      tmpStr = "0" + tmpStr;
    }

    returnValue = tmpStr + ":";
    tmpVal = Math.abs( (val % 3600) / 60);
    tmpStr = new Long(tmpVal).toString();
    if (tmpStr.length() < 2) {
      tmpStr = "0" + tmpStr;
    }
    returnValue += tmpStr + ":";

    tmpVal = Math.abs(val % 60);
    tmpStr = new Long(tmpVal).toString();
    if (tmpStr.length() < 2) {
      tmpStr = "0" + tmpStr;
    }
    returnValue += tmpStr;

    if (val < 0 && enableNegative) {
      returnValue = "-" + returnValue;

    }
    return returnValue;
  }

  public static boolean checkConflict(String startA, String endA, String startB,
				      String endB) {
    return checkConflict(startA, endA, "all", startB, endB, "all");
  }

  /**
   * 检查两时间段是否有冲突
   * @param sa
   * @param ea
   * @param sb
   * @param eb
   * @return
   */
  public static boolean checkConflict(String startA, String endA,
				      String weekDayA, String startB,
				      String endB, String weekDayB) {
    boolean returnValue = false;
    long sa = parse(startA);
    long ea = parse(endA);
    long sb = parse(startB);
    long eb = parse(endB);
    long wa = 0;
    long wb = 0;
    if (weekDayA.equalsIgnoreCase("all")) {
      wa = 8;
    }
    else {
      wa = new Long(weekDayA).longValue();
    }
    if (weekDayB.equalsIgnoreCase("all")) {
      wb = 8;
    }
    else {
      wb = new Long(weekDayB).longValue();
    }

    if (wa != 8 && wb != 8) {
      //任何一各都不是每天，直接比较
      if (wa == 6 && wb == 0) {
	//处理周六与周日
	wb = 7;
      }
      if (wb == 6 && wa == 0) {
	//处理周六与周日
	wa = 7;
      }
      //间隔超过一天，必然不冲突
      if (Math.abs(wa - wb) > 1) {
	return false;
      }
      //获取连续时间段
      if (ea <= sa) {
	ea += 24 * 3600;
      }
      if (eb <= sb) {
	eb += 24 * 3600;
	//天数大的加24小时
      }
      if (wa < wb) {
	sb += 24 * 3600;
	eb += 24 * 3600;
      }
      else if (wa > wb) {
	sa += 24 * 3600;
	ea += 24 * 3600;
      }
    }

    int sPos, ePos;
    sPos = isSecondBetween(sa, sb, eb, false);
    ePos = isSecondBetween(ea, sb, eb, true);

    if (sa < ea) {
      if (sb < eb) {
	//包容或交叉
	if (sPos == 0 || ePos == 0 || (sPos < 0 && ePos > 0)) {
	  returnValue = true;
	}
      }
      else {
	//被跨天包容
	if (sPos == 0 || ePos == 0) {
	  returnValue = true;
	}
      }
    }
    else {
      if (sb < eb) {
	//包容或交叉
	if (sPos <= 0 || ePos >= 0) {
	  returnValue = true;
	}
      }
      else {
	//均跨天,一定冲突
	returnValue = true;
      }
    }
    return returnValue;
  }

  /**
   * 检查一个时刻是否在时段范围内
   * @param checkSecond
   * @param startSecond
   * @param endSecond
   * @return
   */
  public static int isSecondBetween(long checkSecond, long startSecond,
				    long endSecond, boolean isCheckEnd) {
    int returnValue = 0;
    if (startSecond < endSecond) {
      if (isCheckEnd) {
	if (checkSecond <= startSecond) {
	  returnValue = -1;
	}
	if (checkSecond > endSecond) {
	  returnValue = 1;
	}
      }
      else {
	if (checkSecond < startSecond) {
	  returnValue = -1;
	}
	if (checkSecond >= endSecond) {
	  returnValue = 1;
	}
      }
    }
    else {
      if (isCheckEnd) {
	if (checkSecond > endSecond && checkSecond <= startSecond) {
	  returnValue = -1;
	}
      }
      else {
	if (checkSecond >= endSecond && checkSecond < startSecond) {
	  returnValue = -1;
	}
      }
    }
    return returnValue;
  }

  /**
   * 转化为描述字符串
   * @return  HH24:MI:SS - HH24:MI:SS [1/2]
   */
  public String toString() {
    String returnValue = "";
    returnValue = format(startTime) + " - " + format(endTime) + " [" + type +
	"]\n";
    return returnValue;
  }

}
