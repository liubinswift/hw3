package com.viewscenes.logic.autoupmess2db.MessProcess.v8;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jdom.Element;

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
 * �㲥ָ����ʷ��ѯ���������ϱ��ӿ�
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RadioQualityHistoryReport implements IUpMsgProcessor {
	private String headid;
	private String signalType="radioup";//���ֹ㲥�������ϱ� radioup �㲥 tvup����
	private String srcCode;
	private String sitetype;//101 102 103�߾�վ
        int version = 0;//�汾
  public RadioQualityHistoryReport() {
  }

  public void processUpMsg(Element root) throws SQLException,
	UpMess2DBException, GDSetException, DbException, UtilException,
	NoRecordException {

    radioQuality2DB(root);
  }

  /**
   * �㲥ָ�������ϱ�(RADIO_QUALITY_RESULT_TAB)
   * v8 6 QualityHistoryReport
   * @param root
   * @throws SQLException
   * @throws DbException
   * @throws UpMess2DBException
   * @throws GDSetException
   * @throws NoRecordException
   */
  private void radioQuality2DB(Element root) throws SQLException, DbException,
      UpMess2DBException, GDSetException, NoRecordException {

    java.util.Date start = Calendar.getInstance().getTime();
    this.srcCode = root.getAttributeValue("SrcCode");//ǰ��վ��code
//    this.sitetype = SiteVersionUtil.getSiteType(srcCode);
    ArrayList<QualityHistoryReportBean> list = new ArrayList<QualityHistoryReportBean>();
//    if(this.sitetype.equals("103")){//�߾�վ
//        this.signalType = root.getAttributeValue("Type").toLowerCase();
//        list = getDataFromRootFrontier(root);
//    }
//    else{
//    this.version = Integer.parseInt(root.getAttributeValue("Version"));
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
    LogTool.debug("ָ����ʷ��ѯ�ϱ����������ϱ����ʱ�䣺" + period);
    System.out.println("ָ����ʷ��ѯ�ϱ����������ϱ����ʱ�䣺" + period);
  }


  /**
   * �㲥ָ�������ϱ�(RADIO_QUALITY_RESULT_TAB)
   * ����preparedStatement��ָ���������
   * @throws UpMess2DBException
   */
  private void data2Db(ArrayList<QualityHistoryReportBean> list) throws UpMess2DBException {
	// ָ�����sql���
      String sql =
	  "insert into radio_quality_result_tab(result_id, equ_code, task_id, frequency, " +
	  "check_datetime, type, value1, value2,description,band,report_type, head_id, is_delete, store_datetime) " +
	  "values(RADIO_DATA_RECOVERY_SEQ.nextval, ?,?, ?, ?, ?, ?, ?,? ,?,1, ?, 0, sysdate)";

      DbComponent db = new DbComponent();
      DbComponent.DbQuickExeSQL prepExeSQL = db.new DbQuickExeSQL(sql);
      prepExeSQL.getConnect();
      try {
      for (int i = 0; i < list.size(); i++) {
    	  QualityHistoryReportBean bean = (QualityHistoryReportBean) list.get(i);
    	  String taskId = bean.getTask_id();
    	  // ���Ƶ��
          String freq = bean.getFreq();
          // ���checkdatetime
          String checkdatetime = bean.getScandatetime();
          // ����ղ�Ƶ�ʵĲ���
          String band = bean.getBand();
          String equcode = bean.getEqu_code();
          String minvalue = bean.getValue1();
          String maxvalue = bean.getValue2();
          String value = bean.getValue1();
          String type = bean.getType();
//          if(version!=5){  ��ӿڱ���һ��
//              checkData(equcode, taskId, type, "equcode", equcode);
//          }
          if(taskId==null||taskId.trim().length()==0)
              {
                  taskId="";
              }

          //checkData(equcode, taskId, type, "taskId", taskId);

//    	  if(this.signalType.equals("radioup")){
//	          checkData(equcode, taskId, type, "signalType="+signalType+";band", band);
//    	  }
          checkData(equcode, taskId, type, "checkdatetime", checkdatetime);
          checkData(equcode, taskId, type, "freq", freq);
		  // Modified by ld 2003-12-20.��Ϊ54��ң��վ���ܿ��Ƶ������ϱ�ȫ���ղ����ݣ�����ϵͳ���غܴ󡣶�300��˲ʱ���ƶ�ֵ�����塣���Թ���
		  //if (!type.equalsIgnoreCase("2")) {
          checkData(equcode, taskId, type, "type", type);
//	      if(this.signalType.equals("radioup")){
		      if(type.equals("5")){
	              checkData(equcode, taskId, type, "minvalue", minvalue);
	              checkData(equcode, taskId, type, "maxvalue", maxvalue);
	          }
		      else{
	              checkData(equcode, taskId, type, "value", value);
		      }
//	      }
//	      if(this.sitetype.equals("103")&&this.signalType.equals("tvup")){
//              checkData(equcode, taskId, type, "value", value);
//          }
	      String desc = bean.getDesc();
	      if(desc==null){
	          desc = "";
	      }

    	  prepExeSQL.setString(1, equcode);
    	  prepExeSQL.setString(2, taskId);
    	  prepExeSQL.setString(3, freq);
	      prepExeSQL.setString(4, checkdatetime);
	      prepExeSQL.setString(5, type);
    	  prepExeSQL.setString(6, minvalue);
    	  prepExeSQL.setString(7, maxvalue);
	      prepExeSQL.setString(8, desc);
	      prepExeSQL.setString(9, band);
	      prepExeSQL.setString(10, bean.getHead_id());
	      prepExeSQL.exeSQL();

		  //}
      }

    }
    catch (Exception ex) {
    	throw new UpMess2DBException("�����ϱ��쳣��", ex);
    }
    finally{
    	prepExeSQL.closeConnect();
    }
  }

  /**
   * getDataFromRootV8
   * ��root�е����ݽ�����ArrayList��(v8��)
   * @param root
   * @return ����QualityHistoryReportBean�ļ���
   */
  public ArrayList<QualityHistoryReportBean> getDataFromRootV8(Element root) {
	  ArrayList<QualityHistoryReportBean> list = new ArrayList<QualityHistoryReportBean>();
	  Element ele = null;
      ele = root.getChild("qualityhistoryreport");
      String equcode = ele.getAttributeValue("equcode");
      String taskid = ele.getAttributeValue("taskid");
      if (taskid == null) {
    	  taskid = "";
      }
      this.headid = SiteVersionUtil.getSiteHeadId(root.getAttributeValue("SrcCode"));

      //prepState = con.prepareStatement(sql);
      List list_quality = ele.getChildren(); // ���QualityԪ��
      for (int i = 0; i < list_quality.size(); i++) {
		Element sub = (Element) list_quality.get(i);
		// ���Ƶ��
		String freq = sub.getAttributeValue("freq");
		// ���checkdatetime
		String checkdatetime = sub.getAttributeValue("checkdatetime");
		// ����ղ�Ƶ�ʵĲ���
		String band = sub.getAttributeValue("band");
		List sublist = sub.getChildren(); // ���QualityIndexԪ��
		for (int j = 0; j < sublist.size(); j++) {
		  Element scanResult = (Element) sublist.get(j);
		  String type = scanResult.getAttributeValue("type");
		  QualityHistoryReportBean bean = new QualityHistoryReportBean(scanResult,"radioup");
		  bean.setEqu_code(equcode);
		  bean.setTask_id(taskid);
		  bean.setHead_id(headid);
		  bean.setFreq(freq);
		  bean.setScandatetime(checkdatetime);
		  bean.setBand(band);
		  list.add(bean);
		}
      }
	  return list;
  }

  /**
   * getDataFromRootFrontier
   * ��root�е����ݽ�����ArrayList��(�߾���)
   * @param root
   * @return ����QualityHistoryReportBean�ļ���
   */
  public ArrayList<QualityHistoryReportBean> getDataFromRootFrontier(Element root) {
      ArrayList<QualityHistoryReportBean> list = new ArrayList<QualityHistoryReportBean>();
      Element ele = null;
      ele = root.getChild("qualityhistoryreport");

      this.headid = SiteVersionUtil.getSiteHeadId(root.getAttributeValue("SrcCode"));
      //prepState = con.prepareStatement(sql);
      List list_qualityTask = ele.getChildren(); // ���QualityTaskԪ��
      for (int i = 0; i < list_qualityTask.size(); i++) {
        Element sub = (Element) list_qualityTask.get(i);
        String equcode = sub.getAttributeValue("equcode");
        String taskid = sub.getAttributeValue("taskid");
        List list_quality = sub.getChildren(); // ���QualityԪ��
        for (int k = 0; k < list_quality.size(); k++) {
            Element subQT = (Element) list_quality.get(k);
            // ���Ƶ��
            String freq = subQT.getAttributeValue("freq");
            // ���checkdatetime
            String checkdatetime = subQT.getAttributeValue("checkdatetime");
            // ����ղ�Ƶ�ʵĲ���
            String band = "";
            if(this.signalType=="radioup"){
                band = subQT.getAttributeValue("band");
            }
            List sublist = subQT.getChildren(); // ���QualityIndexԪ��
            for (int j = 0; j < sublist.size(); j++) {
              Element scanResult = (Element) sublist.get(j);
              String type = scanResult.getAttributeValue("type");
              QualityHistoryReportBean bean = new QualityHistoryReportBean(scanResult,this.signalType);
              bean.setEqu_code(equcode);
              bean.setTask_id(taskid);
              bean.setHead_id(headid);
              bean.setFreq(freq);
              bean.setScandatetime(checkdatetime);
              bean.setBand(band);
              list.add(bean);
            }
         }
      }
      return list;
  }

  /**
   * ������������� ȱ���׳��쳣
   * @param equcode
   * @param task_id
   * @param data ������
   * @param value ����ֵ
   * @throws UpMess2DBException
   */
  private void checkData(String equcode,String task_id,String type,String data,String value) throws UpMess2DBException{
      if(value==null||value.equals("")){
          throw new UpMess2DBException("srcCode="+srcCode+";equcode=" + equcode + ";task_id="+task_id+";type="+type+"ָ����ʷ��ѯ�ϱ�����ȱ�ٱ�Ҫ����:"+data+"="+value);
      }
  }
}

/*
 * ָ����ʷ��ѯ����������
 */
class QualityHistoryReportBean
{
	private String equ_code;
	private String head_id;
	private String task_id;

	private String freq;
	private String scandatetime;
	private String band;

	private String value1;
	private String value2;
	private String type;
	private String desc;

	public QualityHistoryReportBean(Element attrs,String stationType) {
		this.type = attrs.getAttributeValue("type");
		this.desc = attrs.getAttributeValue("desc");
                if(stationType.equalsIgnoreCase("tvup")){
                    value1 = attrs.getAttributeValue("value");
			value2 = "";
                } else{
                    if (type.equalsIgnoreCase("5")) { // ����ǵ��ƶ����ֵ��ȡ����ֵ
                        value1 = attrs.getAttributeValue("minvalue");
                        value2 = attrs.getAttributeValue("maxvalue");
                    } else { // ���������ȡһ��ֵ
                        value1 = attrs.getAttributeValue("value");
                        value2 = "";
                    }
                }
            }

	public String getEqu_code() {
		return equ_code;
	}

	public void setEqu_code(String equ_code) {
		this.equ_code = equ_code;
	}

	public String getHead_id() {
		return head_id;
	}

	public void setHead_id(String head_id) {
		this.head_id = head_id;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getScandatetime() {
		return scandatetime;
	}

	public void setScandatetime(String scandatetime) {
		this.scandatetime = scandatetime;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
