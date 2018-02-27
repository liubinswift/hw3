package com.viewscenes.logic.autoupmess2db.common;



import com.viewscenes.dao.database.*;

import com.viewscenes.dao.*;

import java.sql.ResultSet;

import java.sql.*;

import java.util.*;

import java.text.SimpleDateFormat;

import com.viewscenes.pub.GDSet;

import com.viewscenes.pub.GDSetException;

import com.viewscenes.util.LogTool;

import com.viewscenes.util.*;

import org.jdom.*;

import java.io.StringReader;

import com.viewscenes.logic.autoupmess2db.Exception.*;

import java.io.*;



/**
 * ������
 */

public class Common {

  private final static int _PERIOD = 1200000;

  private final static Map _headInfoMap = new HashMap(); //ǰ����Ϣ

  private final static Map _chInfoMap = new HashMap(); //Ƶ����Ϣ



  static {

    try {

      loadHeadInfo();

      loadAnalogInfo();

      loadRadioInfo();

    }

    catch (Exception ex) {

      LogTool.warning("autoup2mess",

                      "AutoUpMess2DB static block:" + ex.getMessage());

    }

  }



  /**

   * ����ǰ��code�õ�head_id

   * @param headCode

   * @return  head_id

   * @throws SQLException

   * @throws DbException

   * @throws com.viewscenes.logic.autoupmess2db.UpMess2DBException

   */

  public static String getHeadidByCode(String headCode) throws SQLException,

      DbException, NoRecordException {

//    DbComponent db = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);
    GDSet gd = DbComponent.Query(
        "select head_id from mon_headend_tab where code ='" + headCode + "'");
    if (gd.getRowCount()>0) {
      try {
        String head_id = gd.getString(0, "head_id");
        return head_id;
      }
      catch (GDSetException ex) {
        throw new NoRecordException(
            "Common getHeadidByCode:����SrcCode�Ҳ�����Ӧ��headId:" + headCode);
      }
    }
    else {
      throw new NoRecordException(
          "Common getHeadidByCode:����SrcCode�Ҳ�����Ӧ��headId:" + headCode);
    }
  }


  /**

   * �ѵ�ǰʱ���ʽת�����ַ�����ʽ

   * @return ʱ���ִ�('YYYY-MM-DD')

   * @throws UtilAppException

   */

  public static String getCurDate() {

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");

    return df.format(Calendar.getInstance().getTime());

  }

  /**
   * �ѵ�ǰʱ���ʽת�����ַ�����ʽ
   * @return ʱ���ִ�('YYYY-MM-DD')
   * @throws UtilAppException
   */

  public static String getCurDateTime() {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return df.format(Calendar.getInstance().getTime());
  }

  /**

   * ����ǰ����Ϣ��_headInfoMap��

   */

  public static void loadHeadInfo() throws GDSetException, DbException {

//    DbComponent con = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);



    String sql = " select center.parent_center_id,center.center_id,center.name center_name,head.head_id,head.name head_name, " +

        " head.code from mon_center_tab center,mon_headend_tab head where center.center_id = head.center_id ";

    GDSet gd = DbComponent.Query(sql);

    _headInfoMap.clear();

    for (int i = 0; i < gd.getRowCount(); i++) {

      _headInfoMap.put(gd.getString(i, "code"), gd.getSubGDSet(i, 1));

    }
  }



  /**

   * ����ǰ�˵�code ���ǰ�˶�Ӧ��Ϣ

   * @param code: ǰ��code

   * @return: ǰ�������Ϣ: head_id,head_name,center_id,center_name,parent_center_id;

   */

  public static GDSet getHeadInfoByCode(String code) throws NoRecordException {

    GDSet gd = (GDSet) _headInfoMap.get(code);

    if (gd == null || gd.getRowCount() == 0) {

      throw new NoRecordException("�˱�����ǰ�˲�����:\r\n");

    }



    return gd;

  }



  /**

   * ��ǰ���µ�Ƶ���Ƿ����

   * @param key: ��������߱�����keyֵ��ǰ��code + Ƶ��code,

   *             ����ǹ㲥������keyֵ��ǰ��code + Ƶ��Ƶ��

   * @return ����Ƶ�������Ϣ����: ch_id,ch_name

   * @throws NoRecordException

   */

  public static GDSet IsChanExist(String key) throws NoRecordException {

    GDSet gd = (GDSet) _chInfoMap.get(key);

    if (gd == null || gd.getRowCount() == 0) {

      throw new NoRecordException("�˱�����Ƶ��������:\r\n");

    }



    return gd;

  }



  /**

   * ����Ƶ����Ϣ���뻺��

   * @throws DbException

   * @throws SQLException

   * @throws GDSetException

   */

  public static void loadAnalogInfo() throws DbException, SQLException,

      GDSetException {

//    DbComponent con = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);
    System.gc() ;

    String sql =

        "select head.code head_code,ch.ch_id,ch.name ch_name,ch.code ch_code from " +

        " mon_headend_tab head,mon_head_ch_rel_tab rel,mon_analog_channel_tab ch where " +

        "head.head_id = rel.head_id and rel.ch_id = ch.ch_id";



    GDSet gd = DbComponent.Query(sql);

    // ��������Ƶ����Ϣ

    for (int i = 0; i < gd.getRowCount(); i++) {

      _chInfoMap.put(gd.getString(i, "head_code") + gd.getString(i, "ch_code"),

                     gd.getSubGDSet(i, 1));



    }

    //��Ƶ���������ANALOG_CH��Ϊ0

//    con.exeUpdate("UPDATE MON_CHANNEL_CHANGE_TAB SET ANALOG_CH = 0");

  }



  /**

   * ����㲥Ƶ����Ϣ

   * @throws DbException

   * @throws SQLException

   * @throws GDSetException

   */

  public static void loadRadioInfo() throws DbException, SQLException,

      GDSetException {

//    DbComponent con = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);
    System.gc() ;

    String sql =

        "select head.code head_code,ch.ch_id,ch.name ch_name,ch.freq from " +

        "mon_headend_tab head,mon_head_ch_rel_tab rel,mon_radio_channel_tab ch where " +

        "head.head_id = rel.head_id and rel.ch_id = ch.ch_id";



    GDSet gd = DbComponent.Query(sql);

    // ��������Ƶ����Ϣ

    for (int i = 0; i < gd.getRowCount(); i++) {

      _chInfoMap.put(gd.getString(i, "head_code") + gd.getString(i, "freq"),

                     gd.getSubGDSet(i, 1));



    }

    //��Ƶ���������ANALOG_CH��Ϊ0

    DbComponent.exeUpdate("UPDATE MON_CHANNEL_CHANGE_TAB SET RADIO_CH = 0");

  }



  /**

   * ��ñ����Ļ�����Ϣ

   * @param tabName: Ҫ��ñ����ı���

   * @param key    : ����������

   * @param alarmid: ����ID

   * @param head_id: ������ǰ��ID

   * @return: ��ѯ���ı���(alarmid,CHECKTIME,head_id,center_id)

   * @throws UpMess2DBException

   */

  public static GDSet getAlarmInfo(String tabName, String key, String alarmid,

                                   String head_id) throws DbException,

      NoRecordException {

//{{-----����alarmid �� head_id�жϴ��������Ƿ��Ѿ�����

//    DbComponent con = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);

    String sql = "select " + key +

        ",alarmid,checktime,head_id,center_id from " + tabName +

        " where alarmid = " + alarmid + " and head_id = " + head_id;

    GDSet gd = DbComponent.Query(sql);

    gd.setTableName(tabName);

    return gd;

  }



  /**

   * ����alarmid �� head_id�жϴ��������Ƿ��Ѿ�����

   * @param alarmid

   * @param head_id

   * @return (sg_id,alarmid,reporttime,head_id,center_id)

   * @throws DbException

   * @throws NoRecordException

   */

  public static GDSet IsSigAlarmExist(String alarmid, String head_id) throws

      DbException, NoRecordException {

    return getAlarmInfo("mon_signal_alarm_tab", "sg_id", alarmid, head_id);

  }



  /**

   * �����쳣ʱ��

   * @param resumeTime �쳣�ָ�ʱ��

   * @param reportTime �쳣�ϱ�ʱ��

   * @return �쳣�ָ�ʱ��

   * @throws GDSetException

   * @throws DbException

   */

  public static String calAbnoTime(String resumeTime, String reportTime) throws

      GDSetException, DbException {

    java.util.Date resTime = StringTool.stringToDate(resumeTime);

    java.util.Date rpTime = StringTool.stringToDate(reportTime);
    if(((resTime.getTime() - rpTime.getTime()) / 1000) < 0) {
      return null;
    }

    return String.valueOf( (resTime.getTime() - rpTime.getTime()) / 1000);
  }



  /**

   * ���ñ�����ı����ָ�

   * @param resumeTime ����

   * @param gd(sg_id,alarmid,CHECKTIME,head_id,center_id)

   * @throws DbException

   * @throws GDSetException

   */

  public static void resumeAlarm(String resumeTime, GDSet gd) throws

      DbException,

      GDSetException {



    gd.addColumn("ISRESUMED", "ISRESUMED", "java.lang.String");

    gd.setString(0, "ISRESUMED", "1");

    gd.addColumn("RESUME_TIME", "RESUME_TIME", "java.lang.String");

    gd.setString(0, "RESUME_TIME", resumeTime);

    String abnotime = calAbnoTime(resumeTime, gd.getString(0, "CHECKTIME"));
    if(abnotime != null) {
      gd.addColumn("ABNORMALTIME", "ABNORMALTIME", "java.lang.String");

      gd.setString(0, "ABNORMALTIME", abnotime);

//      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);

      DbComponent.Update(gd);
    }

  }

  /**

   * ɾ���󱨼�¼
   * @param gd

   * @throws DbException

   */

  private static void deleteErrorAlarm(GDSet gd) throws DbException {

//    DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);

//    DbComponent.Delete(gd);

  }



  /**

   * ����XML��,���ָ���Ľڵ�

   *

   * @param message �� Ҫ������xml��,�����Կ�

   *

   * @return: һ��Element�Ľڵ�

   */

  public static Element parseXml(String message) throws UpMess2DBException {

    try {

//���������õ�Document

      StringReader read = new StringReader(message);

      org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(false);

      Document doc = builder.build(read);



//�õ����ڵ�LIT

      return doc.getRootElement();

    }

    catch (Exception e) {

      throw new UpMess2DBException("����������Ϣ����", e);

    }

  }



  /**

   * �Դ˱������д�����

   * @param mode 0������ 1���ָ�����

   * @param gd

   * @throws NoRecordException

   */

  public static void checkAlarm(String mode, GDSet gd) throws NoRecordException {

    //����ǻָ����������ڱ��������Ҳ�����¼�������쳣

    if (mode.equals("1") && gd.getRowCount() == 0) {

      throw new NoRecordException("�����ָ������ڱ������в�����:\r\n");

    }

    //����Ǳ��������ڱ��������Ѵ��ڣ������쳣

    if (mode.equals("0") && gd.getRowCount() > 0) {

      throw new NoRecordException("���������ڱ��������Ѿ�����:\r\n");

    }

  }



  /**

   * elementԪ��д����־�ļ���

   * @param ex

   * @param root

   */

  public static void logElement(Exception ex, Element root) {

    try {

      org.jdom.output.XMLOutputter output = new org.jdom.output.

          XMLOutputter();

      LogTool.warning("autoup2mess",

                      "setRadioSpecGD:" + ex.getMessage() +

                      output.outputString(root));

    }

    catch (IOException ex1) {

      LogTool.warning("autoup2mess",

                      "setRadioSpecGD:" + "logElement:" + ex1.getMessage());

    }

  }

}
