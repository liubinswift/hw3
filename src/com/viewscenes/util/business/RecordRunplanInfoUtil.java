package com.viewscenes.util.business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.util.business.SiteRunplanUtil;
import com.viewscenes.util.business.SiteVersionUtil;


/**
 * 录音文件相关 发射台、节目、运行图类型查询功能抽象类。
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RecordRunplanInfoUtil {
  public static int stationNamePos = 0;
  public static int programNamePos = 1;
  public static int runplanTypeIdPos = 2;
  public static int runplanIdPos = 3;

  private boolean isCenter = true;
  private Connection conn = null;

  //从运行图直接提取。
  private String runSql =
      " select run.station_id,sta.name,run.program_id,pro.program_name," +
      " run.runplan_type_id,run.runplan_id " +
      " from zres_runplan_tab run,RES_TRANSMIT_STATION_TAB sta, ZDIC_PROGRAM_NAME_TAB pro" +
      " where run.station_id=sta.station_id and run.program_id=pro.program_id and run.runplan_id=?";
  private DbComponent.DbQuickExeSQL runStat = null;

  //任务关联运行图查询。
  private String taskSql =
      " select run.station_id,sta.name,run.program_id,pro.program_name,run.runplan_type_id,run.runplan_id " +
      " from RADIO_UNIFY_TASK_TAB task,zres_runplan_tab run," +
      " RES_TRANSMIT_STATION_TAB sta, ZDIC_PROGRAM_NAME_TAB pro" +
      " where task.task_id = ? and task.runplan_id= run.runplan_id " +
      " and run.station_id=sta.station_id and run.program_id=pro.program_id";
  private DbComponent.DbQuickExeSQL taskStat = null;

  //从运行图中关联提取。
  //基本条件
  private String baseSql =
      " select run.station_id,sta.name,run.program_id,pro.program_name,run.runplan_type_id,run.runplan_id   " +
      " from zres_runplan_tab run,RES_TRANSMIT_STATION_TAB sta,ZDIC_PROGRAM_NAME_TAB pro" +
      " where run.station_id=sta.station_id and run.program_id=pro.program_id " +
      " and run.is_delete=0 and run.is_confirm=1 and run.timespan_type_id=1 " +
      " and run.FREQ=? and valid_start_time<=? and valid_end_time>=?  " +
      //周设置为all或者 java时间-1
      " and (run.weekday='all' or run.weekday=?) " +
      //开<1号打头的结，结>1号开；开<2结 结>2开
      " and ( (start_time<? and end_time>?) or (start_time<? and end_time>?) ) ";

  //采集点特殊条件
  private String type101Sql = baseSql +
      " and run.station_id in ( select distinct station_id from res_monitor_station_tab " +
      " where head_id in (select head_id from res_headend_tab  " +
      " where is_delete = 0 and code in (?) ))  ";
  private DbComponent.DbQuickExeSQL type101Stat = null;

  private String type102Sql = null;
  private DbComponent.DbQuickExeSQL type102Stat = null;

  public RecordRunplanInfoUtil() {
    //无用代码，只是表明此处功能有重复，修改时要同时修改此处。
    SiteRunplanUtil.getRunplanSqlByCode(null);

    /**
     * 遥控站资源共享
     */
    isCenter = SystemIdUtil.isCenter();
    //遥控站特殊条件
    //中心拆分表。
    if (isCenter) {
      type102Sql = baseSql +
	  " and runplan_id in (select distinct runplan.runplan_id " +
	  " from zres_runplan_tab runplan,ZDIC_RUNPLAN_STATION_TAB runsta " +
	  " where runplan.station_id=runsta.station_id and runplan.band=runsta.band " +
	  " and runsta.effect_ids like ? and  (runplan.direction=runsta.direction " +
	  " or runplan.direction+360=runsta.direction " +
	  " or runplan.direction=runsta.direction+360) )";
    }
    else { //直属台关联字段。
      type102Sql = baseSql + " and mon_area like ? ";
    }

    type101Sql += " order by run.runplan_id desc ";
    type102Sql += " order by run.runplan_id desc ";
  }

  /**
   * 资源初始化，用于
   * @throws Exception
   */
  public void initResource() throws Exception {
    DbComponent db = new DbComponent();
    this.runStat = db.new DbQuickExeSQL(runSql);
    this.taskStat = db.new DbQuickExeSQL(taskSql);
    this.type101Stat = db.new DbQuickExeSQL(type101Sql);
    this.type102Stat = db.new DbQuickExeSQL(type102Sql);
    this.runStat.getConnect();
    this.taskStat.getConnect();
    this.type101Stat.getConnect();
    this.type102Stat.getConnect();
  }

  /**
   * 资源关闭，用于
   */
  public void releaseResource() {
    //关闭任务查询。
    if (this.runStat != null) {
      try {
	this.runStat.closeConnect();
      }
      catch (Exception ex) {
      }
    }

    if (this.taskStat != null) {
      try {
	this.taskStat.closeConnect();
      }
      catch (Exception ex) {
      }
    }
    if (this.type101Stat != null) {
      try {
	this.type101Stat.closeConnect();
      }

      catch (Exception ex) {
      }
    }
    if (this.type102Stat != null) {
      try {
	this.type102Stat.closeConnect();
      }
      catch (Exception ex) {
      }
    }
    //关闭连接。
    if (this.conn != null) {
      try {
	this.conn.close();
      }
      catch (Exception ex) {
      }
    }
  }

  /**
   * 根据录音任务相应信息提取相应台名、节目名的功能类。
   * 要利用此方法必须要遵循设计步骤使用,以便共享资源
   * @param taskId String
   * @param headCode String
   * @param freq String
   * @param startRecordTime String
   * @param endRecordTime String
   * @return String[] 0 台名，1 节目名， 2 运行图类型id， 3运行图id
   */
  public String[] getRunplanInfoFunc(String runId, String taskId,
				     String headCode, String freq,
				     String startRecordTime,
				     String endRecordTime) {
    /**
     * 先根据runplan_id从运行图中提取相应信息。
     */
    if (runId != null) {
      ResultSet rs = null;
      try {
	this.runStat.setString(1, runId);
	rs = this.runStat.exeQuery();
	if (rs.next()) {
	  return getRetArrayFromRs(rs);
	}
      }
      catch (Exception ex) {
	ex.printStackTrace();
      }
      finally {
	if (rs != null) {
	  try {
	    rs.close();
	  }
	  catch (SQLException ex1) {
	  }
	}
      }
    }

    /**
     * 如果根据返回的任务id---runplan_id从运行图中提取相应信息。
     */
    if (taskId != null&&taskId!="") {

    ResultSet rs = null;
      try {
	this.taskStat.setString(1, taskId);
	rs = this.taskStat.exeQuery();
	if (rs.next()) {
	  return getRetArrayFromRs(rs);
	}
      }
      catch (Exception ex) {
	ex.printStackTrace();
      }
      finally {
	if (rs != null) {
	  try {
	    rs.close();
	  }
	  catch (SQLException ex1) {
	  }
	}
      }
    }

    /**
     * 如果提取不到，从运行图中关联。
     */
    String headType = SiteVersionUtil.getSiteType(headCode);
    //采集点相关
    DbComponent.DbQuickExeSQL stat = type101Stat;
    String headParam = SiteVersionUtil.getSiteOriCode(headCode);
    //遥控站相关
    if ("102".equalsIgnoreCase(headType)) {
      stat = type102Stat;
      //中心还是直属台
      if (isCenter) {
	headParam = "%," + SiteVersionUtil.getSiteHeadId(headCode) + ",%";
      }
      else {
	headParam = "%" + SiteVersionUtil.getSiteName(headCode) + "%";
      }
    }

    ResultSet rs = null;
    try {
      int i = 1;
      //频率
      stat.setString(i++, freq);
      //有效期
//      stat.setTimestamp(i++, StringDateUtil.getTimestamp(startRecordTime));
//     stat.setTimestamp(i++, StringDateUtil.getTimestamp(endRecordTime));
      stat.setString(i++, startRecordTime);
      stat.setString(i++, endRecordTime);

      //周设置
      Calendar timeCal = Calendar.getInstance();
      timeCal.setTime(StringDateUtil.string2Date(startRecordTime));
      int timeWeek = timeCal.get(Calendar.DAY_OF_WEEK) - 1;
      stat.setString(i++, timeWeek + "");

      /**
       * 播音时间
       */
      long timeBegin1; //1开始
      long timeEnd1; //1结束
      long timeBegin2; //2开始
      long timeEnd2; //2结束

      //开始录音时间
      timeCal.setTime(StringDateUtil.string2Date(startRecordTime));
      timeCal.set(Calendar.YEAR, 2000);
      timeCal.set(Calendar.MONTH, 0);
      timeCal.set(Calendar.DAY_OF_MONTH, 1);
      timeBegin1 = timeCal.getTimeInMillis();
      timeCal.set(Calendar.DAY_OF_MONTH, 2);
      timeBegin2 = timeCal.getTimeInMillis();

      //结束录音时间
      timeCal.setTime(StringDateUtil.string2Date(endRecordTime));
      timeCal.set(Calendar.YEAR, 2000);
      timeCal.set(Calendar.MONTH, 0);
      timeCal.set(Calendar.DAY_OF_MONTH, 1);
      timeEnd1 = timeCal.getTimeInMillis();
      timeCal.set(Calendar.DAY_OF_MONTH, 2);
      timeEnd2 = timeCal.getTimeInMillis();

      //设置
      stat.setTimestamp(i++, new Timestamp(timeEnd1));
      stat.setTimestamp(i++, new Timestamp(timeBegin1));
      stat.setTimestamp(i++, new Timestamp(timeEnd2));
      stat.setTimestamp(i++, new Timestamp(timeBegin2));
      //站点条件
      stat.setString(i++, headParam);

      rs = stat.exeQuery();
      if (rs.next()) {
	return getRetArrayFromRs(rs);
      }
    }
    catch (Exception ex2) {
      ex2.printStackTrace();
    }
    finally {
      if (rs != null) {
	try {
	  rs.close();
	}
	catch (SQLException ex1) {
	}
      }
    }

    //空，用于广播网。
    String[] nullArray = {
	"", "", "", ""};
    return nullArray;
  }

  /**
   * 对于单独记录的信息查询，对外直接提供查询结果，以及对于使用的示例。
   * @param taskId String
   * @param headCode String
   * @param freq String
   * @param startRecordTime String
   * @param endRecordTime String
   * @return String[]
   */
  public String[] getRunplanInfo(String runId, String taskId, String headCode,
				 String freq, String startRecordTime,
				 String endRecordTime) {
    try {
      this.initResource();
      return this.getRunplanInfoFunc(runId, taskId, headCode, freq,
				     startRecordTime, endRecordTime);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      String[] nullArray = {
	  "", "", "", ""};
      return nullArray;
    }
    finally {
      this.releaseResource();
    }
  }

  private String[] getRetArrayFromRs(ResultSet rs) throws Exception {
    String[] staProName = new String[4];
    staProName[stationNamePos] = rs.getString(2);
    staProName[programNamePos] = rs.getString(4);
    staProName[runplanTypeIdPos] = rs.getString(5);
    staProName[runplanIdPos] = rs.getString(6);
    return staProName;
  }

  protected void finalize() {
    this.releaseResource();
    try {
      super.finalize();
    }
    catch (Throwable ex) {
      ex.printStackTrace();
    }
  }

  public static void testGetInfo() {
    RecordRunplanInfoUtil recordUtil = new RecordRunplanInfoUtil();
    String taskId = "508111695";
    String headCode = SiteVersionUtil.getHeadCodeById("100400027");
    String freq = "6280";
    String startRecordTime = "2006-10-1 7:02:01";
    String endRecordTime = "2006-10-1 7:02:01";
    System.out.println(recordUtil.getRunplanInfo(null, taskId, headCode, freq,
						 startRecordTime, endRecordTime));
  }

  public static void main(String[] args) {
    testGetInfo();
  }
}
