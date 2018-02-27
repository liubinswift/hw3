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
 * �㲥Ƶƫ��ʷ��ѯ�������������ϱ��ӿ�
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RadioOffsetHistoryReport implements IUpMsgProcessor {
    private String srcCode;
    private String equ_code = "";
    private String task_id = "";
    private String head_id = "";
    public RadioOffsetHistoryReport() {
    }

public void processUpMsg(Element root) throws SQLException,
UpMess2DBException, GDSetException, DbException, UtilException, NoRecordException {
    java.util.Date start = Calendar.getInstance().getTime();
    this.srcCode = root.getAttributeValue("SrcCode");//ǰ��վ��code
    String sitetype = SiteVersionUtil.getSiteType(srcCode);
    ArrayList<OffsetHistoryReportBean> list = new ArrayList<OffsetHistoryReportBean>();
//    if(sitetype.equals("103")){//�߾�վ
//
//    }
//    else{
		list = getDataFromRootV8(root);
//    }
    data2Db(list);

    java.util.Date end = Calendar.getInstance().getTime();
    long period = end.getTime() - start.getTime();
    LogTool.debug("Ƶƫ���������ϱ����ʱ�䣺" + period);
    System.out.println("Ƶƫ���������ϱ����ʱ�䣺" + period);
}

/**
 * (radio_offset_result_tab)
 * ����preparedStatement��ָ���������
 * @throws UpMess2DBException
 */
private void data2Db(ArrayList<OffsetHistoryReportBean> list) throws UpMess2DBException {
    if(this.task_id==null||this.task_id.trim().length()==0)
           {
               this.task_id="";
           }

    // ָ�����sql���
    String sql =  "insert into radio_offset_result_tab(result_id, equ_code, task_id, frequency, " +
  	      "check_datetime, offset, band,report_type, head_id, is_delete, store_datetime) " +
  	      "values(RADIO_DATA_RECOVERY_SEQ.nextval, '" + this.equ_code + "', '" +
  	this.task_id + "', ?, ?, ?,?, 1, " + this.head_id + ", 0, sysdate)";
    DbComponent db = new DbComponent();
    DbComponent.DbQuickExeSQL prepExeSQL = db.new DbQuickExeSQL(sql);
    prepExeSQL.getConnect();
    try {
        //checkData(equ_code, task_id, "task_id", task_id);
    for (int i = 0; i < list.size(); i++) {
    	OffsetHistoryReportBean bean= list.get(i);
        // ���checkdatetime
	  	String checkdatetime = bean.getCheck_datetime();
	  	String freq = bean.getFrequency();
	  	String offset = bean.getOffset();
	  	String band = bean.getBand();
        checkData(equ_code, task_id, "checkdatetime", checkdatetime);
        checkData(equ_code, task_id, "freq", freq);
        checkData(equ_code, task_id, "offset", offset);
  	    checkData(equ_code, task_id, "band", band);
  		prepExeSQL.setString(1, freq);
  		prepExeSQL.setString(2, checkdatetime);
  		prepExeSQL.setString(3, offset);
  		prepExeSQL.setString(4, band);
  		prepExeSQL.exeSQL();
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
 * ��root�е����ݽ�����ArrayList��(v8��)
 * @param root
 * @return ����OffsetHistoryReportBean�ļ���
 */
private ArrayList<OffsetHistoryReportBean> getDataFromRootV8(Element root) {
    ArrayList<OffsetHistoryReportBean> list = new ArrayList<OffsetHistoryReportBean>();

    // ����й�����
    Element ele = null;
    ele = root.getChild("offsethistoryreport");
    this.equ_code = ele.getAttributeValue("equcode");
    this.task_id = ele.getAttributeValue("taskid");
    if (task_id == null) {
        task_id = "";
    }
    this.head_id = SiteVersionUtil.getSiteHeadId(root.getAttributeValue("SrcCode"));
    List list_index = ele.getChildren(); // ���OffsetIndexԪ��
    for (int i = 0; i < list_index.size(); i++) {
        Element sub = (Element) list_index.get(i);
        OffsetHistoryReportBean bean = new OffsetHistoryReportBean(sub);
//    	  bean.setEqu_code(equ_code);
//    	  bean.setHead_id(head_id);
//    	  bean.setTask_id(task_id);
        list.add(bean);
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
private void checkData(String equcode,String task_id,String data,String value) throws UpMess2DBException{
    if(value==null||value.equals("")){
        throw new UpMess2DBException("srcCode="+srcCode+";equcode=" + equ_code + ";task_id="+task_id+"Ƶƫ��ʷ��ѯ�ϱ�����ȱ�ٱ�Ҫ����:"+data+"="+value);
    }
}
}


/*
 * Ƶƫ��ʷ��ѯ����������
 */
class OffsetHistoryReportBean {
private String equ_code;
private String task_id;
private String head_id;

private String frequency;
private String check_datetime;
private String band;
private String offset;

public OffsetHistoryReportBean(Element attrs) {
    this.frequency = attrs.getAttributeValue("freq");
this.band = attrs.getAttributeValue("band");
this.check_datetime = attrs.getAttributeValue("checkdatetime");
this.offset = attrs.getAttributeValue("offset");
}

public String getEqu_code() {
	return equ_code;
}

public void setEqu_code(String equ_code) {
	this.equ_code = equ_code;
}

public String getTask_id() {
	return task_id;
}

public void setTask_id(String task_id) {
	this.task_id = task_id;
}

public String getHead_id() {
	return head_id;
}

public void setHead_id(String head_id) {
	this.head_id = head_id;
}

public String getFrequency() {
	return frequency;
}

public void setFrequency(String frequency) {
	this.frequency = frequency;
}

public String getCheck_datetime() {
	return check_datetime;
}

public void setCheck_datetime(String check_datetime) {
	this.check_datetime = check_datetime;
}

public String getBand() {
	return band;
}

public void setBand(String band) {
	this.band = band;
}

public String getOffset() {
	return offset;
}

public void setOffset(String offset) {
	this.offset = offset;
}
}
