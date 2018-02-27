package com.viewscenes.logic.autoupmess2db.MessProcess.v8;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jdom.Element;

import com.viewscenes.bean.device.SpectrumHistoryReportBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.logic.autoupmess2db.Exception.NoRecordException;
import com.viewscenes.logic.autoupmess2db.Exception.UpMess2DBException;
import com.viewscenes.logic.autoupmess2db.MessProcess.IUpMsgProcessor;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.UtilException;
import com.viewscenes.util.business.SiteVersionUtil;
/**
 * 广播频谱历史查询返回主动上报接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RadioSpectrumHistoryReport implements IUpMsgProcessor {
	private String srcCode;
	private String headid;
	private String signalType="radioup";//区分广播、电视上报 radioup 广播 tvup电视
	private String sitetype;//101 102 103边境站
  public RadioSpectrumHistoryReport() {
  }

  public void processUpMsg(Element root) throws SQLException,
	UpMess2DBException, GDSetException, DbException, UtilException,
	NoRecordException {
    java.util.Date start = Calendar.getInstance().getTime();
    this.srcCode = root.getAttributeValue("SrcCode");//前端站点code
    this.headid = SiteVersionUtil.getSiteHeadId(srcCode);
//    this.sitetype = SiteVersionUtil.getSiteType(srcCode);
    ArrayList<SpectrumHistoryReportBean> list = new ArrayList<SpectrumHistoryReportBean>();
//    if(this.sitetype.equals("103")){//边境站
//        this.signalType = root.getAttributeValue("Type").toLowerCase();
//        list = getDataFromRootFrontier(root);
//    }
//    else{
//    int version = Integer.parseInt(root.getAttributeValue("Version"));
//    switch (version) {
//	  case 8:
		list = getDataFromRootV8(root);
//		break;
//
//	  default:
//		break;
//	  }
//    }
    data2Db(list);

    java.util.Date end = Calendar.getInstance().getTime();
    long period = end.getTime() - start.getTime();
    LogTool.debug("频谱收测数据主动上报入库时间：" + period);
    System.out.println("频谱收测数据主动上报入库时间：" + period);

  }

  
  /**
   * (radio_spectrum_result_tab)
   * 利用preparedStatement将指标数据入库
   * @throws UpMess2DBException
   */
  public void data2Db(ArrayList<SpectrumHistoryReportBean> list) throws UpMess2DBException {
      // 指标入库sql语句
      String sql =
      "insert into radio_spectrum_result_tab(result_id, equ_code, task_id, " +
      "check_datetime, band, frequency, e_level, report_type, head_id, is_delete, store_datetime,spectrum_id) " +
      "values(RADIO_DATA_RECOVERY_SEQ.nextval, ?, ?, ?, ?, ?, ?, 1, " + headid + ", 0, sysdate,?)";
      DbComponent db = new DbComponent();
      DbComponent.DbQuickExeSQL prepExeSQL = db.new DbQuickExeSQL(sql);
      prepExeSQL.getConnect();
      try {
          for (int j = 0; j < list.size(); j++) {
        	SpectrumHistoryReportBean bean = (SpectrumHistoryReportBean) list.get(j);
        	String band = bean.getBand();
        	String freq = bean.getFreq();
        	String level = bean.getLevel();
        	String checkdatetime = bean.getScandatetime();
        	String taskid = bean.getTask_id();
        	String equCode = bean.getEqu_code();
        	String spectrumid = bean.getSpectrumid();

                if(taskid==null||taskid.trim().length()==0)
                {
                    taskid="";
                }
        	//checkData(taskid, "taskid", taskid);
//        	if(this.sitetype.equals("103")){
//        	    checkData(taskid, "sitetype="+sitetype+";"+"equCode", equCode);
//        	    checkData(taskid, "sitetype="+sitetype+";"+"spectrumid", spectrumid);
//        	}
//        	if(this.signalType.equals("radioup")){
        	    checkData(taskid, "band", band);
//            }
        	checkData(taskid, "freq", freq);
        	checkData(taskid, "level", level);
        	checkData(taskid, "checkdatetime", checkdatetime);

        	prepExeSQL.setString(1, equCode);

                    prepExeSQL.setString(2, taskid);

                prepExeSQL.setString(3,checkdatetime);
        	prepExeSQL.setString(4, band);
        	prepExeSQL.setString(5, freq);
        	prepExeSQL.setString(6, level);

                     prepExeSQL.setString(7, spectrumid);

                prepExeSQL.exeSQL();
          }

      }
      catch (Exception ex) {
          throw new UpMess2DBException("主动上报异常：", ex);
      }
      finally{
          prepExeSQL.closeConnect();
      }
  }

  /**
   * getDataFromRootV8
   * 将root中的数据解析到ArrayList中(v8版)
   * @param root
   * @return 包含SpectrumHistoryReportBean的集合
   */
  public ArrayList<SpectrumHistoryReportBean> getDataFromRootV8(Element root) {
	  ArrayList<SpectrumHistoryReportBean> list = new ArrayList<SpectrumHistoryReportBean>();
      // 获得有关数据
      Element ele = null;
      ele = root.getChild("spectrumhistoryreport");
      // 获得equcode
      String equcode = ele.getAttributeValue("equcode");
      // 获得taskid
      String taskid = ele.getAttributeValue("taskid");
      // 获得SpectrumScan元素
      List list_spec = ele.getChildren();
      for (int i = 0; i < list_spec.size(); i++) {
          Element sub = (Element) list_spec.get(i);
          String checkdatetime = sub.getAttributeValue("scandatetime");
          List sublist = sub.getChildren(); // 获得ScanResult元素
          for (int j = 0; j < sublist.size(); j++) {
              Element scanResult = (Element) sublist.get(j);
              SpectrumHistoryReportBean bean = new SpectrumHistoryReportBean(scanResult);
              bean.setEqu_code(equcode);
              bean.setHead_id(headid);
              bean.setTask_id(taskid);
              bean.setScandatetime(checkdatetime);
  				bean.setReport_type("1");
              list.add(bean);
          }
      }
	  return list;
  }

  /**
   * getDataFromRootFrontier
   * 将root中的数据解析到ArrayList中(边境版)
   * @param root
   * @return 包含SpectrumHistoryReportBean的集合
   */
  public ArrayList<SpectrumHistoryReportBean> getDataFromRootFrontier(Element root) {
      ArrayList<SpectrumHistoryReportBean> list = new ArrayList<SpectrumHistoryReportBean>();
      // 获得有关数据
      Element ele = null;
      ele = root.getChild("spectrumhistoryreport");

      // 获得SpectrumTask元素
      List list_specTask = ele.getChildren();
      for (int k = 0; k < list_specTask.size(); k++) {
          Element subTask = (Element) list_specTask.get(k);
          // 获得equcode
          String equcode = subTask.getAttributeValue("equcode");
          // 获得taskid
          String taskid = subTask.getAttributeValue("taskid");
          // 获得SpectrumScan元素
          List list_spec = subTask.getChildren();
          for (int i = 0; i < list_spec.size(); i++) {
              Element sub = (Element) list_spec.get(i);
              String checkdatetime = sub.getAttributeValue("scandatetime");
              String spectrumid = sub.getAttributeValue("spectrumid");
              List sublist = sub.getChildren(); // 获得ScanResult元素
              for (int j = 0; j < sublist.size(); j++) {
                  Element scanResult = (Element) sublist.get(j);
                  SpectrumHistoryReportBean bean = new SpectrumHistoryReportBean(scanResult,this.signalType);
                  bean.setEqu_code(equcode);
                  bean.setHead_id(headid);
                  bean.setTask_id(taskid);
                  bean.setScandatetime(checkdatetime);
                  bean.setSpectrumid(spectrumid);
                  list.add(bean);
              }
          }
      }
      return list;
  }

  /**
   * 检查数据完整性 缺少抛出异常
   * @param task_id
   * @param data 数据名
   * @param value 数据值
   * @throws UpMess2DBException
   */
  private void checkData(String task_id,String data,String value) throws UpMess2DBException{
      if(value==null||value.equals("")){
          throw new UpMess2DBException("srcCode="+srcCode+"task_id=" + task_id + "频谱历史上报数据缺少必要参数:"+data+"="+value);
      }
  }

}


