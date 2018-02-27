package com.viewscenes.sys;

import java.text.*;
import java.util.*;

import com.viewscenes.dao.*;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.pub.*;

public final class Operation_log {

  private static final String OPERATION_TAB = "sec_operation_log_tab";
  private static final String USER_TAB = "sec_user_tab";
  private static final String OPERATION_DETAIL_TAB = "sec_operation_detail_tab";
  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public Operation_log() {
  }

  /**
   * 记录操作信息
   * @param String 操作员ID 可以为null
   * @param String 操作ID 可以为null
   * @param String 操作的具体描述，如给哪个采集点发了什么任务，或添加运行图时给那个发射台添加的运行图等等具体描述（不能超过200字）。
   * @return boolean false:添加日志失败 ; true:添加日志成功
   * @throws Exception
   */
  public static boolean writeOperLog(String user_id, String detail_id,
                                     String description) throws Exception {
    boolean result = false;

    //定义公共变量
    int rowCount = 0;

    //获取当前时间
    String now_datetime = getNowDatetime();

    //获得这个用户名字
    String user_name = null;
    if (user_id != null) {
      user_name = id2name("user_id", user_id, "user_name", USER_TAB);
    }

    //获得该操作的具体名称
    String detail_name = null;
    if (detail_id != null) {
      detail_name = id2name("detail_id", detail_id, "operation_name",
                            OPERATION_DETAIL_TAB);
    }
     String sql="insert into "+OPERATION_TAB+"(log_id,user_id,user_name,operation_id,operation_name,log_datetime," +
		"description)values( sec_seq.nextval,"+user_id+",'"+user_name+"',"+detail_id+",'"+detail_name+"',to_date('"+now_datetime
		+"','yyyy-mm-dd hh24:mi:ss'),'"+description+"')";
    


    return DbComponent.exeUpdate(sql);
  }

  /**
   * 根据id获取name
   * @param String id所在表中的字段名
   * @param String id的值
   * @param String name所在表中的字段名
   * @param String 表名
   * @param String 当前时间
   * @return String name 当有异常时返回name为""
   * @throws Exception
   */
  private static String id2name(String id_row_name, String id_value,
                                String name_row_name, String table_name) throws
      Exception {
 
    GDSet data_user_name = null;
    String name = null;

   String sql="select "+name_row_name+" from  "+table_name +" where "+id_row_name+"="+id_value;
  
    data_user_name = DbComponent.Query(sql);
    int rowCount = data_user_name.getRowCount();
    if (rowCount == 0) {
      return null;
    }
    else {
      name = data_user_name.getString(0, name_row_name);
    }
    return name;
  }

  /**
   * 获取当前时间
   * @return String eg. 2008-01-23 15:01:00
   */
  public static String getNowDatetime() {
    //获取当前时间
    Calendar now;
    java.util.Date now_time;
    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT,
        Locale.getDefault());
    String now_datetime = "";
    now = Calendar.getInstance();
    now_time = now.getTime();
    now_datetime = formatter.format(now_time);
    return now_datetime;
  }

}
