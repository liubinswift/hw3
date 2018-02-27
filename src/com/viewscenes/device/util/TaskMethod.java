package com.viewscenes.device.util;

import com.viewscenes.pub.GDSet;

//���°����ڻ��ϵͳʱ��


/**
 * <p>Title:TaskMethod</p>
 * <p>Description: �й������һЩ����</p>
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
   * ҳ���select����
   * @param varΪҪ�Ƚϵı�����paraΪҳ���ϵ�select��ѡ��ֵ��ע��Ҫ����˳��һ�¡�
   * @return һ�����飬���ж�Ӧ���Ϊ" selected "������Ϊ������
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
   * �������ɳɹ��Ժ󣬰�������񼤻is_active=1
   * @param ����id��������ı�
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
      //throw new ModuleException("�������ɹ��������쳣���ߴ���ʱ�Ѹ�����ɾ������", ex);
    }
  }

  /**
   * ��¼����ı�ע��Ϣ������ʶ���ڣ�
   * @param
   * @return
   */
  public static void saveNote(String tablename, String task_id, String note) {
    //���¼����������ڻ�õ�ǰʱ��,���ʹ�����һ��д��ÿ������������note�ֶ���
//    String now_datetime = TimeMethod.get_nowdate();
//
//    try {
//      String b[] = {
//          "task_id", "note"};
//      String e[] = {
//          task_id, "��" + now_datetime + "��" + note};
//
//      GDSet set1 = new GDSet(tablename, b);
//      set1.addRow(e);
//      DAOOperator d1 = (DAOOperator) DaoFactory.create(DaoFactory.
//          DAO_OBJECT);
//      int[] n1 = d1.Update(set1);
//    }
//    catch (Exception ex) {
//      ex.printStackTrace();
//      //throw new ModuleException("�������ɹ��������쳣���ߴ���ʱ�Ѹ�����ɾ������", ex);
//    }
  }

  /**
   * ��¼�´������̷߳���������������ʱ�䣬�Ա��ж��Ƿ�������������´��̻߳��ڹ���
   * @param ip String ��������ip��ַ
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
   * �������ʶ�ɼ������Ϊ�������񣬵��Ǳ����ֹ�������ͼ�����Ͳ��䣻
   * @param
   * @return type_id�������type_id
   */
//  public static String simpleTask2Complex(String tablename, String task_id) {
    //���Ȼ��ԭ����type_id
//    String type_id = "0";
//    try {
//      GDSet data2 = code_to_id("type_id", "task_id", "number", task_id,
//                               tablename);
//      int rowCount2 = data2.getRowCount();
//      if (rowCount2 == 0) {
//        //throw new ModuleException("������û�з����վ�㡣");
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
//      //throw new ModuleException("�������ɹ��������쳣���ߴ���ʱ�Ѹ�����ɾ������", ex);
//    }
//    return type_id;
//}

  /**
   * �ж���������ͣ�������type_id
   * ������������Ϊ�ۺ�����ֻ��һ�����Ͷ���ֻ������һ��վ�㣬���Ǽ����񣻷����ۺϣ�
   * Ƶ���������ֻ������һ��վ����Ǽ򵥣������ۺϣ�
   * @param
   * @return type_id
   */
//  public static String getTypeid(String tablename, String task_id) {
//    //���Ȼ��ԭ����type_id
//    String type_id = "0";
//    try {
//      GDSet data2 = code_to_id("type_id", "task_id", "number", task_id,
//                               RADIO_UNIFY_TASK_TAB);
//      int rowCount2 = data2.getRowCount();
//      if (rowCount2 == 0) {
//        //throw new ModuleException("������û�з����վ�㡣");
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
//      //throw new ModuleException("�������ɹ��������쳣���ߴ���ʱ�Ѹ�����ɾ������", ex);
//    }
//    return type_id;
//  }

  /**
   * ͨ��Ƶ��ȷ������
   * @param
   * @return band �����band=""����ô˵����������Ƶ�ʲ��Ϸ���0���̲���1���в���2����Ƶ
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
   * �������ɹ��������쳣���ߴ���ʱ�Ѹ�����ɾ��
   * @param
   * @return
   */
  public static void delTask(String tablename, String task_id) {
    try {
      //�����ݿ���ɾ��
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
   * �����޸Ĺ������д�����ô�ͻָ�ԭ��������
   * @param
   * @return
   */
  public static void undelTask(String task_id) {
    try {
      //�����ݿ���ɾ��
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
   * �����������Ӧ��վ���Ƿ����ߡ����߾ͷ���true��������false
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
   * ��������״̬�Ĵ��룬��Ӧ�������״̬
   * @param
   * @return
   */
  public static String task_status2desc(String status) {
    String desc = "";
    if (status.equalsIgnoreCase("0")) {
      desc = "��ǰʱ������ִ����";
    }
    else if (status.equalsIgnoreCase("1")) {
      desc = "�����ѿ�ʼִ�У���ǰʱ��δִ��";
    }
    else if (status.equalsIgnoreCase("-1")) {
      desc = "�豸��û���������";
    }
    else if (status.equalsIgnoreCase("2")) {
      desc = "���������";
    }
    else if (status.equalsIgnoreCase("3")) {
      desc = "�����δ��ʼִ��";
    }
    else {
      desc = "�豸������Ϣ��" + status + "��ϵͳ���ܽ���������״̬δ֪";
    }
    return desc + "(" + status + ")";
  }

}
