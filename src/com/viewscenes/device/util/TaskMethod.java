package com.viewscenes.device.util;

import com.viewscenes.pub.GDSet;

//以下包用于获得系统时间


/**
 * <p>Title:TaskMethod</p>
 * <p>Description: 有关任务的一些方法</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author zr
 * @version 1.0
 */
public class TaskMethod {
  public TaskMethod() {
  }

  public static String RADIO_UNIFY_TASK_TAB = "radio_unify_task_tab";
  public static String RADIO_SPECTRUM_TASK_TAB = "radio_spectrum_task_tab";
  public static String RADIO_EQU_TASK_TAB = "radio_equ_task_tab";
  public static String RADIO_SUB_TASK_TAB = "radio_sub_task_tab";
  public static String RADIO_TASK_FREQ_TAB = "radio_task_freq_tab";
  public static String RADIO_TASK_TIME_CYCLE_TAB =
      "radio_task_time_cycle_tab";
  public static String RADIO_TASK_TIME_SINGLE_TAB =
      "radio_task_time_single_tab";

  /**
   * 页面的select处理
   * @param var为要比较的变量，para为页面上的select的选项值，注意要保持顺序一致。
   * @return 一个数组，其中对应项的为" selected "，其他为“”；
   */
  public static String[] doSelect(String var, String[] para) {
    int count = para.length;
    String[] selected = new String[count];
    String select = " selected ";
    for (int q = 1; q <= count; q++) {
      selected[q - 1] = "";
    }
    for (int i = 0; i < count; i++) {
      if (var != null && var.equalsIgnoreCase(para[i])) {
        selected[i] = select;
      }
    }
    return selected;
  }

  /**
   * 任务生成成功以后，把这个任务激活：is_active=1
   * @param 任务id，和任务的表
   * @return
   */
  public static void activeTask(String task_id, String tablename) {
    try {
      String b[] = {
          "task_id", "is_active", "is_send", "send_times"};
      String e[] = {
          task_id, "1", "0", "0"};

      GDSet set1 = new GDSet(tablename, b);
      set1.addRow(e);

//      DAOOperator d1 = (DAOOperator) DaoFactory.create(DaoFactory.
//          DAO_OBJECT);
//      int[] n1 = d1.Update(set1);

    }
    catch (Exception ex) {
      ex.printStackTrace();
      //throw new ModuleException("任务生成过程中有异常或者错误时把该任务删除出错", ex);
    }
  }

  /**
   * 记录任务的备注信息，并标识日期；
   * @param
   * @return
   */
  public static void saveNote(String tablename, String task_id, String note) {
    //以下几个变量用于获得当前时间,并和处理结果一起写入每批处理的任务的note字段中
//    String now_datetime = TimeMethod.get_nowdate();
//
//    try {
//      String b[] = {
//          "task_id", "note"};
//      String e[] = {
//          task_id, "【" + now_datetime + "】" + note};
//
//      GDSet set1 = new GDSet(tablename, b);
//      set1.addRow(e);
//      DAOOperator d1 = (DAOOperator) DaoFactory.create(DaoFactory.
//          DAO_OBJECT);
//      int[] n1 = d1.Update(set1);
//    }
//    catch (Exception ex) {
//      ex.printStackTrace();
//      //throw new ModuleException("任务生成过程中有异常或者错误时把该任务删除出错", ex);
//    }
  }

  /**
   * 记录下达任务线程服务器的运行最新时间，以便判断是否这个服务器的下达线程还在工作
   * @param ip String 服务器的ip地址
   * @return Exception
   */
//  public static void updateLastActiveTime(String ip) throws Exception {
//    DbComponent db = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);
//    String sql = "update sys_active_server_tab set last_datetime = to_date('" +
//        TimeMethod.get_nowdate() +
//        "','YYYY-MM-DD HH24:MI:SS') where ip = '" + ip + "'";
//    boolean r = db.exeUpdate(sql);
//  }

  /**
   * 将任务标识由简单任务变为复杂任务，但是保持手工和运行图的类型不变；
   * @param
   * @return type_id调整后的type_id
   */
//  public static String simpleTask2Complex(String tablename, String task_id) {
    //首先获得原来的type_id
//    String type_id = "0";
//    try {
//      GDSet data2 = code_to_id("type_id", "task_id", "number", task_id,
//                               tablename);
//      int rowCount2 = data2.getRowCount();
//      if (rowCount2 == 0) {
//        //throw new ModuleException("该任务还没有分配给站点。");
//      }
//      else {
//        type_id = data2.getString(0, "type_id");
//      }
//    }
//    catch (Exception ex) {
//      ex.printStackTrace();
//    }
//
//    if (type_id.equalsIgnoreCase("1")) {
//      type_id = "0";
//    }
//    else if (type_id.equalsIgnoreCase("3")) {
//      type_id = "2";
//
//    }
//    try {
//      String b[] = {
//          "task_id", "type_id"};
//      String e[] = {
//          task_id, type_id};
//
//      GDSet set1 = new GDSet(tablename, b);
//      set1.addRow(e);
//      DAOOperator d1 = (DAOOperator) DaoFactory.create(DaoFactory.
//          DAO_OBJECT);
//      int[] n1 = d1.Update(set1);
//    }
//    catch (Exception ex) {
//      ex.printStackTrace();
//      //throw new ModuleException("任务生成过程中有异常或者错误时把该任务删除出错", ex);
//    }
//    return type_id;
//}

  /**
   * 判断任务的类型，并返回type_id
   * 如果这个任务作为综合任务，只有一个类型而且只发给了一个站点，就是简单任务；否则综合；
   * 频谱任务，如果只发给了一个站点就是简单；否则综合；
   * @param
   * @return type_id
   */
//  public static String getTypeid(String tablename, String task_id) {
//    //首先获得原来的type_id
//    String type_id = "0";
//    try {
//      GDSet data2 = code_to_id("type_id", "task_id", "number", task_id,
//                               RADIO_UNIFY_TASK_TAB);
//      int rowCount2 = data2.getRowCount();
//      if (rowCount2 == 0) {
//        //throw new ModuleException("该任务还没有分配给站点。");
//      }
//      else {
//        type_id = data2.getString(0, "type_id");
//      }
//    }
//    catch (Exception ex) {
//      ex.printStackTrace();
//    }
//
//    if (type_id.equalsIgnoreCase("1")) {
//      type_id = "0";
//    }
//    else if (type_id.equalsIgnoreCase("3")) {
//      type_id = "2";
//
//    }
//    try {
//      String b[] = {
//          "task_id", "type_id"};
//      String e[] = {
//          task_id, type_id};
//
//      GDSet set1 = new GDSet(tablename, b);
//      set1.addRow(e);
////      DAOOperator d1 = (DAOOperator) DaoFactory.create(DaoFactory.
////          DAO_OBJECT);
////      int[] n1 = d1.Update(set1);
//    }
//    catch (Exception ex) {
//      ex.printStackTrace();
//      //throw new ModuleException("任务生成过程中有异常或者错误时把该任务删除出错", ex);
//    }
//    return type_id;
//  }

  /**
   * 通过频率确定波段
   * @param
   * @return band ，如果band=""，那么说明这个输入的频率不合法；0：短波；1：中波；2：调频
   */
  public static String freq2band(String freq) {
    String band = "";
    try {
      float freq2 = new Float(freq).floatValue();
      if ( (freq2 >= 2300) && (freq2 <= 26100)) {
        band = "0";
      }
      else {
        if ( (freq2 >= 531) && (freq2 <= 1602)) {
          band = "1";
        }
        else {
          if ( (freq2 >= 87000) && (freq2 <= 108000)) {
            band = "2";
          }
        }
      }
    }
    catch (Exception e) {}
    return band;
  }

  /**
   *
   * @param
   * @return
   */
//  public static GDSet code_to_id(String wantRowName, String inputRowName,
//                                 String type, String inputValue,
//                                 String tablename) {
//    DAOOperator db = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
//    GDSet result = null;
//    try {
//      DAOCondition condition = new DAOCondition(tablename);
//      if ( (tablename.equalsIgnoreCase("radio_equ_task_tab")) ||
//          (tablename.equalsIgnoreCase("res_headend_tab"))
//          || (tablename.equalsIgnoreCase("res_transmit_station_tab")) ||
//          (tablename.equalsIgnoreCase("radio_unify_task_tab"))
//          || (tablename.equalsIgnoreCase("radio_equ_task_tab")) ||
//          (tablename.equalsIgnoreCase("res_radio_station_tab"))) {
//        condition.addCondition("is_delete", "number", "=", "0", "and");
//      }
//      if (type.equalsIgnoreCase("varchar")) {
//        type = "VARCHAR";
//      }
//      if (type.equalsIgnoreCase("number")) {
//        type = "NUMBER";
//      }
//      condition.addCondition(inputRowName, type, "=", inputValue, "and");
//      result = db.Query(wantRowName, condition);
//      return result;
//    }
//    catch (Exception ex) {
//      ex.printStackTrace();
//    }
//    return result;
//  }

  /**
   * 任务生成过程中有异常或者错误时把该任务删除
   * @param
   * @return
   */
  public static void delTask(String tablename, String task_id) {
    try {
      //从数据库中删除
      String b[] = {
          "task_id", "is_delete"};
      String e[] = {
          task_id, "1"};

      GDSet set1 = new GDSet(tablename, b);
      set1.addRow(e);
//      DAOOperator d1 = (DAOOperator) DaoFactory.create(DaoFactory.
//          DAO_OBJECT);
//      int[] n1 = d1.Update(set1);

    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * 任务修改过程中有错误，那么就恢复原来的任务。
   * @param
   * @return
   */
  public static void undelTask(String task_id) {
    try {
      //从数据库中删除
      String b[] = {
          "task_id", "is_delete"};
      String e[] = {
          task_id, "0"};

      GDSet set1 = new GDSet(RADIO_UNIFY_TASK_TAB, b);
      set1.addRow(e);
//      DAOOperator d1 = (DAOOperator) DaoFactory.create(DaoFactory.
//          DAO_OBJECT);
//      int[] n1 = d1.Update(set1);

    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * 检查任务所对应的站点是否在线。在线就返回true，否则是false
   * @param
   * @return
   */
  public static boolean is_online(String tablename, String task_id) {
    String is_online = "0";
    String sql = "select is_online from res_headend_tab where is_delete = 0 and code in (select head_code from radio_equ_task_tab where is_delete = 0 and task_id = " +
        task_id + ") order by head_id";

//    DbComponent db = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);
//    GDSet data = null;
    try {
//      data = db.Query(sql);
//      is_online = data.getString(0, "is_online");
    }
    catch (Exception ex) {}
    if (is_online.equalsIgnoreCase("1")) {
      return true;
    }
    return false;
  }

  /**
   * 根据任务状态的代码，对应出具体的状态
   * @param
   * @return
   */
  public static String task_status2desc(String status) {
    String desc = "";
    if (status.equalsIgnoreCase("0")) {
      desc = "当前时刻正在执行中";
    }
    else if (status.equalsIgnoreCase("1")) {
      desc = "任务已开始执行，当前时刻未执行";
    }
    else if (status.equalsIgnoreCase("-1")) {
      desc = "设备上没有这个任务";
    }
    else if (status.equalsIgnoreCase("2")) {
      desc = "任务已完成";
    }
    else if (status.equalsIgnoreCase("3")) {
      desc = "任务从未开始执行";
    }
    else {
      desc = "设备返回消息（" + status + "）系统不能解析，任务状态未知";
    }
    return desc + "(" + status + ")";
  }

}
