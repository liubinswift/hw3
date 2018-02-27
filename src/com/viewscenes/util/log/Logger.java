package com.viewscenes.util.log;



import java.io.*;

import java.text.*;

import java.util.*;



import com.viewscenes.util.*;



/**

 * Implementation of  methods to write to screen or disk file that signify varying levels of logging.

 *

 * @author  ChenGang

 * @version 1.0, 2003-04

 */


public class Logger {

  protected PrintWriter writer = null;

  private LoggerProperty prop;

  private String logFileName = null;

  private static SimpleDateFormat sdf = new SimpleDateFormat(

      "yyyy-MM-dd HH:mm:ss");

  private static SimpleDateFormat hoursdf = new SimpleDateFormat(
      "yyyy_MM_dd_HH");
  private static SimpleDateFormat datesdf = new SimpleDateFormat(
      "yyyy_MM_dd");
  private static SimpleDateFormat weeksdf = new SimpleDateFormat(
      "yyyy_MM_WW");
  private static SimpleDateFormat monthsdf = new SimpleDateFormat(
      "yyyy_MM");
  private static SimpleDateFormat yearsdf = new SimpleDateFormat(
      "yyyy");


  public Logger(LoggerProperty logProp) {

    this.prop = logProp;
    this.logFileName = logProp.logPath + logProp.logName ;

  }
/**根据时间每天都新生成一个文件夹
 * @author wangfuxiang
 * @date 17/06/2010 
 * @return String 生成文件的路径
 */
  public String LoggerFileName(){
	  String date = new SimpleDateFormat("yyyyMMdd").format(new Date()); 
	  this.logFileName = this.prop.logPath +date+"\\"+ this.prop.logName ;
	  return this.logFileName;
  }

  protected Logger() {}



  public boolean checkLevel(int level) {

    if (level >= this.prop.logLevel) {

      return true;

    }

    return false;

  }



  public void log(Exception e, int level) {
	String logFileName = this.LoggerFileName();
    if (checkLevel(level) && prop.logType != LogManager.TYPE_NONE) {

      StringBuffer buffer = new StringBuffer();



      Date logDate = new Date();

      buffer.append(sdf.format(logDate));

      switch (level) {

        case LogManager.LEVEL_DEBUG:

          buffer.append("[DEBUG]:");

          break;

        case LogManager.LEVEL_INFO:

          buffer.append("[INFO]:");

          break;

        case LogManager.LEVEL_WARNING:

          buffer.append("[WARNING]:");

          break;

        case LogManager.LEVEL_FATAL:

          buffer.append("[FATAL]:");

          break;

        case LogManager.LEVEL_OUTPUT:

          buffer.append("[OUTPUT]:");

          break;

      }

      String sClass=null;
      int lineNum = 0;
      String sMethod = null;

      Throwable t = new Throwable();
      StackTraceElement[] elements = t.getStackTrace();
      if (elements.length>=2){
        sClass = elements[2].getClassName();
        lineNum = elements[2].getLineNumber();
        sMethod = elements[2].getMethodName();
      }
      if (sClass!=null){
        buffer.append("class=");
        buffer.append(sClass);
        buffer.append(",line=");
        buffer.append(lineNum);
        buffer.append(",method=");
        buffer.append(sMethod);
        buffer.append("\r\n");
      }


      buffer.append(e.getMessage());



      if (prop.logType == LogManager.TYPE_SCREEN ||

          prop.logType == LogManager.TYPE_BOTH) {

        System.out.println(buffer.toString());

        if (level >= LogManager.LEVEL_WARNING) {

          e.printStackTrace(System.out);

        }

      }

      if (prop.logType == LogManager.TYPE_FILE ||

          prop.logType == LogManager.TYPE_BOTH) {

        try {
          String fileName = logFileName + ".log";
          switch (this.prop.autoSplit){
            case LogManager.SPLIT_HOURLY:
              fileName = logFileName + hoursdf.format(new Date())+".log";
              break;
            case LogManager.SPLIT_DAILY:
              fileName = logFileName + datesdf.format(new Date())+".log";
              break;
            case LogManager.SPLIT_WEEKLY:
              fileName = logFileName + weeksdf.format(new Date())+".log";
              break;
            case LogManager.SPLIT_MONTHLY:
              fileName = logFileName + monthsdf.format(new Date())+".log";
              break;
            case LogManager.SPLIT_YEARLY:
              fileName = logFileName + yearsdf.format(new Date())+".log";
              break;
          }
          FileTool.writeStackTrace(fileName, buffer.toString(), e);

        }

        catch (UtilException ex) {

          System.out.println("Can't write log file! file path = " + logFileName);

          ex.printStackTrace();

        }

      }

    }



  }



  public void log(String msg, int level) {
    String logFileName = this.LoggerFileName();
    if (checkLevel(level) && prop.logType != LogManager.TYPE_NONE) {

      StringBuffer buffer = new StringBuffer();

      Date logDate = new Date();


      buffer.append(sdf.format(logDate));
     

      String sClass=null;
      int lineNum = 0;
      String sMethod = null;

      Throwable t = new Throwable();
      StackTraceElement[] elements = t.getStackTrace();
      if (elements.length>=2){
        sClass = elements[2].getClassName();
        lineNum = elements[2].getLineNumber();
        sMethod = elements[2].getMethodName();
      }
      if (sClass!=null){
        buffer.append(" ");
        buffer.append(sClass);
        buffer.append(",");
        buffer.append(lineNum);
        buffer.append(",");
        buffer.append(sMethod);
        buffer.append("\r\n");
      }

      switch (level) {

        case LogManager.LEVEL_DEBUG:

          buffer.append("[DEBUG]:");

          break;

        case LogManager.LEVEL_INFO:

          buffer.append("[INFO]:");

          break;

        case LogManager.LEVEL_WARNING:

          buffer.append("[WARNING]:");

          break;

        case LogManager.LEVEL_FATAL:

          buffer.append("[FATAL]:");

          break;

        case LogManager.LEVEL_OUTPUT:

          buffer.append("[OUTPUT]:");

          break;
      }


      if (msg != null) {
        buffer.append(msg);
      }
      buffer.append("\r\n");

      if (prop.logType == LogManager.TYPE_SCREEN ||

          prop.logType == LogManager.TYPE_BOTH) {

          System.out.println(buffer.toString());

      }

      if (prop.logType == LogManager.TYPE_FILE ||

          prop.logType == LogManager.TYPE_BOTH) {

        try {

            String fileName = logFileName + ".log";
            switch (this.prop.autoSplit){
              case LogManager.SPLIT_HOURLY:
                fileName = logFileName +hoursdf.format(new Date())+".log";
                break;
              case LogManager.SPLIT_DAILY:
                fileName = logFileName + datesdf.format(new Date())+".log";
                break;
              case LogManager.SPLIT_WEEKLY:
                fileName = logFileName + weeksdf.format(new Date())+".log";
                break;
              case LogManager.SPLIT_MONTHLY:
                fileName = logFileName + monthsdf.format(new Date())+".log";
                break;
              case LogManager.SPLIT_YEARLY:
                fileName = logFileName + yearsdf.format(new Date())+".log";
                break;
            }

          FileTool.updateFile(fileName, buffer.toString());

        }

        catch (UtilException ex) {

          System.out.println("Can't write log file! file path = " + logFileName);

          ex.printStackTrace();

        }
      }
    }
  }



  protected void finalize() {

  }



}
