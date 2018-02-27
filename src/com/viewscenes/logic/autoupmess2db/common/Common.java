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
 * 公共类
 */

public class Common {

  private final static int _PERIOD = 1200000;

  private final static Map _headInfoMap = new HashMap(); //前端信息

  private final static Map _chInfoMap = new HashMap(); //频道信息



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

   * 根据前端code得到head_id

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
            "Common getHeadidByCode:根据SrcCode找不到对应的headId:" + headCode);
      }
    }
    else {
      throw new NoRecordException(
          "Common getHeadidByCode:根据SrcCode找不到对应的headId:" + headCode);
    }
  }


  /**

   * 把当前时间格式转换成字符串形式

   * @return 时间字串('YYYY-MM-DD')

   * @throws UtilAppException

   */

  public static String getCurDate() {

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");

    return df.format(Calendar.getInstance().getTime());

  }

  /**
   * 把当前时间格式转换成字符串形式
   * @return 时间字串('YYYY-MM-DD')
   * @throws UtilAppException
   */

  public static String getCurDateTime() {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return df.format(Calendar.getInstance().getTime());
  }

  /**

   * 加载前端信息到_headInfoMap中

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

   * 根据前端的code 获得前端对应信息

   * @param code: 前端code

   * @return: 前端相关信息: head_id,head_name,center_id,center_name,parent_center_id;

   */

  public static GDSet getHeadInfoByCode(String code) throws NoRecordException {

    GDSet gd = (GDSet) _headInfoMap.get(code);

    if (gd == null || gd.getRowCount() == 0) {

      throw new NoRecordException("此报警的前端不存在:\r\n");

    }



    return gd;

  }



  /**

   * 该前端下的频道是否存在

   * @param key: 如果是有线报警此key值是前端code + 频道code,

   *             如果是广播报警此key值是前端code + 频道频率

   * @return 返回频道相关信息包含: ch_id,ch_name

   * @throws NoRecordException

   */

  public static GDSet IsChanExist(String key) throws NoRecordException {

    GDSet gd = (GDSet) _chInfoMap.get(key);

    if (gd == null || gd.getRowCount() == 0) {

      throw new NoRecordException("此报警的频道不存在:\r\n");

    }



    return gd;

  }



  /**

   * 有线频道信息放入缓存

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

    // 缓存有线频道信息

    for (int i = 0; i < gd.getRowCount(); i++) {

      _chInfoMap.put(gd.getString(i, "head_code") + gd.getString(i, "ch_code"),

                     gd.getSubGDSet(i, 1));



    }

    //把频道更换表的ANALOG_CH置为0

//    con.exeUpdate("UPDATE MON_CHANNEL_CHANGE_TAB SET ANALOG_CH = 0");

  }



  /**

   * 缓存广播频道信息

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

    // 缓存有线频道信息

    for (int i = 0; i < gd.getRowCount(); i++) {

      _chInfoMap.put(gd.getString(i, "head_code") + gd.getString(i, "freq"),

                     gd.getSubGDSet(i, 1));



    }

    //把频道更换表的ANALOG_CH置为0

    DbComponent.exeUpdate("UPDATE MON_CHANNEL_CHANGE_TAB SET RADIO_CH = 0");

  }



  /**

   * 获得报警的基本信息

   * @param tabName: 要获得报警的表名

   * @param key    : 报警的主键

   * @param alarmid: 报警ID

   * @param head_id: 报警的前端ID

   * @return: 查询到的报警(alarmid,CHECKTIME,head_id,center_id)

   * @throws UpMess2DBException

   */

  public static GDSet getAlarmInfo(String tabName, String key, String alarmid,

                                   String head_id) throws DbException,

      NoRecordException {

//{{-----根据alarmid 和 head_id判断此条报警是否已经存在

//    DbComponent con = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);

    String sql = "select " + key +

        ",alarmid,checktime,head_id,center_id from " + tabName +

        " where alarmid = " + alarmid + " and head_id = " + head_id;

    GDSet gd = DbComponent.Query(sql);

    gd.setTableName(tabName);

    return gd;

  }



  /**

   * 根据alarmid 和 head_id判断此条报警是否已经存在

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

   * 计算异常时间

   * @param resumeTime 异常恢复时间

   * @param reportTime 异常上报时间

   * @return 异常恢复时间

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

   * 设置报警表的报警恢复

   * @param resumeTime 报警

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

   * 删除误报记录
   * @param gd

   * @throws DbException

   */

  private static void deleteErrorAlarm(GDSet gd) throws DbException {

//    DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);

//    DbComponent.Delete(gd);

  }



  /**

   * 解析XML串,获得指定的节点

   *

   * @param message ： 要解析的xml串,不可以空

   *

   * @return: 一个Element的节点

   */

  public static Element parseXml(String message) throws UpMess2DBException {

    try {

//初步解析得到Document

      StringReader read = new StringReader(message);

      org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(false);

      Document doc = builder.build(read);



//得到根节点LIT

      return doc.getRootElement();

    }

    catch (Exception e) {

      throw new UpMess2DBException("解析上行消息错误", e);

    }

  }



  /**

   * 对此报警进行错误检查

   * @param mode 0：报警 1：恢复报警

   * @param gd

   * @throws NoRecordException

   */

  public static void checkAlarm(String mode, GDSet gd) throws NoRecordException {

    //如果是恢复报警，但在报警表中找不到记录，则抛异常

    if (mode.equals("1") && gd.getRowCount() == 0) {

      throw new NoRecordException("此条恢复报警在报警表中不存在:\r\n");

    }

    //如果是报警，但在报警表中已存在，则抛异常

    if (mode.equals("0") && gd.getRowCount() > 0) {

      throw new NoRecordException("此条报警在报警表中已经存在:\r\n");

    }

  }



  /**

   * element元素写到日志文件中

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
