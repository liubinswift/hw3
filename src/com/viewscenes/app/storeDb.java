package com.viewscenes.app;

import java.util.*;

import org.jdom.Element;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.pub.GDSetTool;
import com.viewscenes.pub.exception.AppException;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;

/**
 * �������
 * <p>
 * Title:�Խ����ϱ������ݽ���������������
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company:viewscenes
 * </p>
 * 
 * @author ����
 * @version 1.0
 */
public class storeDb {
    public storeDb() {
    }

    /**
     * ���
     * 
     * @param msg
     *   REPORT_ID          INTEGER not null,
  CENTER_ID          INTEGER,
  CENTER_NAME        VARCHAR2(100),
  REPORT_DATE        DATE,
  UPLOAD_DATETIME    DATE,
  AUTHENTIC_STATUS   INTEGER,
  UPLOAD_USER        VARCHAR2(100),
  UPLOAD_STATUS      INTEGER,
  AUTHENTIC_DATETIME DATE,
  AUTHENTIC_USER     VARCHAR2(100),
  UP_VERIFY_USER     VARCHAR2(100),
  UP_AUTHENTIC_USER  VARCHAR2(100),
  DATA_NUM           NUMBER,
  TYPE               NUMBER default 1,
  UP_IMPORT_USER     VARCHAR2(100)
     */
    public void store(String msg) {
    	  Element root = StringTool.getXMLRoot(msg);
    	  Element info = root.getChild("info");
          String REPORT_TYPE = info.getAttributeValue("REPORT_TYPE");//�������ͣ�1���ձ���2�ܱ���3�±���
          String CENTER_ID=info.getAttributeValue("CENTER_ID");//�ϱ�ֱ��̨id;
          String CENTER_NAME=info.getAttributeValue("CENTER_NAME");//�ϱ�ֱ��̨����;
          String UPLOAD_DATETIME=info.getAttributeValue("UPLOAD_DATETIME");//�ϱ�ʱ��;
          String UPLOAD_USER=info.getAttributeValue("UPLOAD_USER");//�ϱ��û�;
          String UPLOAD_STATUS=info.getAttributeValue("UPLOAD_STATUS");//�ϱ�״̬ 0: �����ϱ���1:�����ϱ�;
          String AUTHENTIC_DATETIME=info.getAttributeValue("AUTHENTIC_DATETIME");//�������;
          String REPORT_STARTTIME=info.getAttributeValue("REPORT_STARTTIME");//����ʼʱ��
    	  String REPORT_ENDTIME=info.getAttributeValue("REPORT_ENDTIME");//�������ʱ��
          String AUTHENTIC_STATUS=info.getAttributeValue("AUTHENTIC_STATUS");//���״̬;
          String AUTHENTIC_USER=info.getAttributeValue("UP_AUTHENTIC_USER");//�����Ա;
          String UP_VERIFY_USER=info.getAttributeValue("UP_VERIFY_USER");//�ϱ�У����Ա;
          String UP_AUTHENTIC_USER=info.getAttributeValue("UP_AUTHENTIC_USER");//�ϱ������Ա;
          String TYPE=info.getAttributeValue("TYPE");//1:��������2:վ�㱨������;3;�������к�ά��
          String DATA_NUM=info.getAttributeValue("DATA_NUM");//�ϱ���������;
          String UP_IMPORT_USER=info.getAttributeValue("UP_IMPORT_USER");//�ϱ��Ʊ���Ա;
          String CONFIG_TYPE=info.getAttributeValue("CONFIG_TYPE");//���������ļ�����;
          String ALARM_IDS=info.getAttributeValue("ALARM_IDS");//������id;
          String SUBMIT_REPORT_ID=info.getAttributeValue("SUBMIT_REPORT_ID");
          String UP_IMPORT_DATETIME=info.getAttributeValue("UP_IMPORT_DATETIME");
          String UP_VERIFY_DATETIME=info.getAttributeValue("UP_VERIFY_DATETIME");
          String UP_AUTHENTIC_DATETIME=info.getAttributeValue("UP_AUTHENTIC_DATETIME");
          String b[];
          String e[];
          GDSet set1;
          
          String sql = "select center_id,name from res_center_tab where center_id='"
              + CENTER_ID.trim() + "'";
		      try {
				GDSet result_set = DbComponent.Query(sql);
				if (result_set.getRowCount() > 0) {
					   String centerID = result_set.getString(0, "center_id");
	                    String centerName = result_set.getString(0, "name");
	                    // get report info
	                    String reportID; 
	                    String nowStr = StringTool.Date2String(Calendar.getInstance()
	                            .getTime());
				     if(REPORT_TYPE.equals("1"))
			          {
				    	 String REPORT_DATE=info.getAttributeValue("REPORT_DATE");
						 if(CONFIG_TYPE!=null&&!CONFIG_TYPE.equalsIgnoreCase("")){
							 sql = "select report_id from rep_authentic_tab where center_id='"
		                            + centerID
		                            + "'"
		                            + " and type="+TYPE+" and report_date=to_date('"
		                            + REPORT_DATE + "','yyyy-mm-dd hh24:mi:ss') and CONFIG_TYPE= '"+CONFIG_TYPE+"'" ; 
						 }else {
							 sql = "select report_id from rep_authentic_tab where center_id='"
		                            + centerID
		                            + "'"
		                            + " and type="+TYPE+" and report_date=to_date('"
		                            + REPORT_DATE + "','yyyy-mm-dd hh24:mi:ss')";  
						 }
				    	
			                    result_set = DbComponent.Query(sql); 
			                    if (result_set.getRowCount() > 0) {
			                        // get report id
			                        reportID = result_set.getString(0, "report_id");
			                        // update status
			                        b = new String[] { "report_id", "center_id","center_name","upload_status",
			                                "upload_datetime", "upload_user",
			                                "up_import_user", "up_verify_user","CONFIG_TYPE",
			                                "up_authentic_user", "data_num","ALARM_IDS" ,"SUBMIT_REPORT_ID","UP_IMPORT_DATETIME","UP_VERIFY_DATETIME","UP_AUTHENTIC_DATETIME"};
			                        e = new String[] { reportID,centerID,centerName, "1", nowStr, UPLOAD_USER,
			                        		UP_IMPORT_USER, UP_VERIFY_USER,CONFIG_TYPE, UP_AUTHENTIC_USER,
			                                new Long(DATA_NUM).toString(),ALARM_IDS,SUBMIT_REPORT_ID,UP_IMPORT_DATETIME,UP_VERIFY_DATETIME,UP_AUTHENTIC_DATETIME};
			                        set1 = new GDSet("rep_authentic_tab", b);
			                        set1.addRow(e);
			                        DbComponent.Update(set1);
			                    } else {
			                        // insert new records
			                        b = new String[] { "report_id", "center_id","center_name","report_date","upload_status",
			                                "upload_datetime", "upload_user",
			                                "up_import_user", "up_verify_user","config_type",
			                                "up_authentic_user", "data_num","ALARM_IDS","TYPE" ,"SUBMIT_REPORT_ID","UP_IMPORT_DATETIME","UP_VERIFY_DATETIME","UP_AUTHENTIC_DATETIME"};
			                        set1 = new GDSet("rep_authentic_tab", b);
			                        e = new String[] { "",centerID,centerName,REPORT_DATE,"1", nowStr, UPLOAD_USER,
			                        		UP_IMPORT_USER, UP_VERIFY_USER,CONFIG_TYPE ,UP_AUTHENTIC_USER,
			                                new Long(DATA_NUM).toString(),ALARM_IDS,TYPE ,SUBMIT_REPORT_ID,UP_IMPORT_DATETIME,UP_VERIFY_DATETIME,UP_AUTHENTIC_DATETIME };
			                        set1.addRow(e);
			                        long key[] = new long[1];
			                        DbComponent.Insert(set1, key);
			                        reportID = new Long(key[0]).toString();
			                    }
			                    LogTool.info("autoup2mess", "ֱ��̨��"+CENTER_NAME+"�ϱ��ձ��ɹ���" 
					                      + msg); 
			          }else if(REPORT_TYPE.equals("2"))
			          {
			        	 
			        	  String WEEKLY=info.getAttributeValue("WEEKLY");//�ڼ����ܱ���
			        	  if(CONFIG_TYPE!=null&&!CONFIG_TYPE.equalsIgnoreCase("")){
			        		  sql = "select report_id from REP_WEEKLY_AUTHENTIC_TAB where center_id='"
		                            + centerID
		                            + "'"
		                            + " and type="+TYPE+" and REPORT_STARTTIME=to_date('"
		                            + REPORT_STARTTIME + "','yyyy-mm-dd hh24:mi:ss') " +"  and REPORT_ENDTIME=to_date('"
		    	                            + REPORT_ENDTIME + "','yyyy-mm-dd hh24:mi:ss')"+"and  WEEKLY="+WEEKLY+
		                            		"  and CONFIG_TYPE= '"+CONFIG_TYPE+"'" ;
			        	  }else{
			        		  sql = "select report_id from REP_WEEKLY_AUTHENTIC_TAB where center_id='"
		                            + centerID
		                            + "'"
		                            + " and type="+TYPE+" and REPORT_STARTTIME=to_date('"
		                            + REPORT_STARTTIME + "','yyyy-mm-dd hh24:mi:ss') " +"  and REPORT_ENDTIME=to_date('"
		    	                            + REPORT_ENDTIME + "','yyyy-mm-dd hh24:mi:ss')"+"and  WEEKLY="+WEEKLY+"";
			        	  }
				    	   
	                    result_set = DbComponent.Query(sql); 
	                    if (result_set.getRowCount() > 0) {
	                        // get report id
	                        reportID = result_set.getString(0, "report_id");
	                        // update status
	                        b = new String[] { "report_id","center_id","center_name", "upload_status",
	                                "upload_datetime", "upload_user",
	                                "up_import_user", "up_verify_user","CONFIG_TYPE",
	                                "up_authentic_user", "data_num","ALARM_IDS","TYPE" ,"SUBMIT_REPORT_ID","UP_IMPORT_DATETIME","UP_VERIFY_DATETIME","UP_AUTHENTIC_DATETIME"};
	                        e = new String[] { reportID,centerID,centerName,"1", 
	                        		nowStr, UPLOAD_USER,
	                        		UP_IMPORT_USER, UP_VERIFY_USER,CONFIG_TYPE, UP_AUTHENTIC_USER,
	                                new Long(DATA_NUM).toString(),ALARM_IDS,TYPE,SUBMIT_REPORT_ID,UP_IMPORT_DATETIME,UP_VERIFY_DATETIME,UP_AUTHENTIC_DATETIME  };
	                        set1 = new GDSet("REP_WEEKLY_AUTHENTIC_TAB", b);
	                        set1.addRow(e);
	                        DbComponent.Update(set1);
	                    } else {
	                        // insert new records
	                    	 b = new String[] { "report_id","center_id","center_name","upload_status"," REPORT_STARTTIME","REPORT_ENDTIME","WEEKLY",
		                                "upload_datetime", "upload_user",
		                                "up_import_user", "up_verify_user","CONFIG_TYPE",
		                                "up_authentic_user", "data_num","ALARM_IDS","SUBMIT_REPORT_ID","UP_IMPORT_DATETIME","UP_VERIFY_DATETIME","UP_AUTHENTIC_DATETIME" };
	                        set1 = new GDSet("REP_WEEKLY_AUTHENTIC_TAB", b);
	                        e = new String[] { "",centerID,centerName,"1", REPORT_STARTTIME,REPORT_ENDTIME,WEEKLY,
	                        		nowStr, UPLOAD_USER,
	                        		UP_IMPORT_USER, UP_VERIFY_USER,CONFIG_TYPE, UP_AUTHENTIC_USER,
	                                new Long(DATA_NUM).toString(),ALARM_IDS,SUBMIT_REPORT_ID,UP_IMPORT_DATETIME,UP_VERIFY_DATETIME,UP_AUTHENTIC_DATETIME   };
	                        set1.addRow(e);
	                        long key[] = new long[1];
	                        DbComponent.Insert(set1, key);
	                        reportID = new Long(key[0]).toString();
	                    }
	                    LogTool.info("autoup2mess", "ֱ��̨��"+CENTER_NAME+"�ϱ��ܱ��ɹ���" 
			                      + msg); 
			          }else if(REPORT_TYPE.equals("3"))
			          {
			        	  String MONTH=info.getAttributeValue("MONTH");//�ڼ����±���
			        	  if(CONFIG_TYPE!=null&&!CONFIG_TYPE.equalsIgnoreCase("")){
			        		  sql = "select report_id from REP_MONTH_AUTHENTIC_TAB where center_id='"
		                            + centerID
		                            + "'"
		                            + " and type="+TYPE+" and REPORT_STARTTIME=to_date('"
		                            + REPORT_STARTTIME + "','yyyy-mm-dd hh24:mi:ss') " +"  and REPORT_ENDTIME=to_date('"
		    	                            + REPORT_ENDTIME + "','yyyy-mm-dd hh24:mi:ss')"+"and  MONTH="+MONTH+
		                            		"  and CONFIG_TYPE= '"+CONFIG_TYPE+"'" ;
			        	  }else{
			        		  sql = "select report_id from REP_MONTH_AUTHENTIC_TAB where center_id='"
		                            + centerID
		                            + "'"
		                            + " and type="+TYPE+" and REPORT_STARTTIME=to_date('"
		                            + REPORT_STARTTIME + "','yyyy-mm-dd hh24:mi:ss') " +"  and REPORT_ENDTIME=to_date('"
		    	                            + REPORT_ENDTIME + "','yyyy-mm-dd hh24:mi:ss')"+"and  MONTH="+MONTH+"";
			        	  }
				    	  
	                    result_set = DbComponent.Query(sql); 
	                    if (result_set.getRowCount() > 0) {
	                        // get report id
	                        reportID = result_set.getString(0, "report_id");
	                        // update status
	                        b = new String[] { "report_id","center_id","center_name", "upload_status"," REPORT_STARTTIME","REPORT_ENDTIME","MONTH",
	                                "upload_datetime", "upload_user",
	                                "up_import_user", "up_verify_user","CONFIG_TYPE",
	                                "up_authentic_user", "data_num","ALARM_IDS" ,"TYPE","SUBMIT_REPORT_ID","UP_IMPORT_DATETIME","UP_VERIFY_DATETIME","UP_AUTHENTIC_DATETIME" };
	                        e = new String[] { reportID,centerID,centerName, "1", REPORT_STARTTIME,REPORT_ENDTIME,MONTH,
	                        		nowStr, UPLOAD_USER,
	                        		UP_IMPORT_USER, UP_VERIFY_USER,CONFIG_TYPE, UP_AUTHENTIC_USER,
	                                new Long(DATA_NUM).toString(),ALARM_IDS,TYPE ,SUBMIT_REPORT_ID,UP_IMPORT_DATETIME,UP_VERIFY_DATETIME,UP_AUTHENTIC_DATETIME};
	                        set1 = new GDSet("REP_MONTH_AUTHENTIC_TAB", b);
	                        set1.addRow(e);
	                        DbComponent.Update(set1);
	                    } else {
	                        // insert new records
	                    	 b = new String[] { "report_id","center_id","center_name", "upload_status"," REPORT_STARTTIME","REPORT_ENDTIME","MONTH",
		                                "upload_datetime", "upload_user",
		                                "up_import_user", "up_verify_user","CONFIG_TYPE",
		                                "up_authentic_user", "data_num","ALARM_IDS","TYPE","SUBMIT_REPORT_ID","UP_IMPORT_DATETIME","UP_VERIFY_DATETIME","UP_AUTHENTIC_DATETIME" };
	                        set1 = new GDSet("REP_MONTH_AUTHENTIC_TAB", b);
	                        e = new String[] { "",centerID,centerName,"1", REPORT_STARTTIME,REPORT_ENDTIME,MONTH,
	                        		nowStr, UPLOAD_USER,
	                        		UP_IMPORT_USER, UP_VERIFY_USER,CONFIG_TYPE, UP_AUTHENTIC_USER,
	                                new Long(DATA_NUM).toString(),ALARM_IDS,TYPE ,SUBMIT_REPORT_ID,UP_IMPORT_DATETIME,UP_VERIFY_DATETIME,UP_AUTHENTIC_DATETIME};
	                        set1.addRow(e);
	                        long key[] = new long[1];
	                        DbComponent.Insert(set1, key);
	                        reportID = new Long(key[0]).toString();
	                    }
	                    LogTool.info("autoup2mess", "ֱ��̨��"+CENTER_NAME+"�ϱ��±��ɹ���" 
			                      + msg); 
			          }else 
			          {
			        	  LogTool.info("autoup2mess", "ֱ��̨������ʧ�ܣ�"
			                      + msg); 
			          }
				}
			} catch (DbException e1) {
				  LogTool.info("autoup2mess", "û�д�ֱ��̨��Ϣ��"+ e1.getMessage()+ msg); 
				  e1.printStackTrace();
				  return;
			} catch (GDSetException ee) {
				// TODO Auto-generated catch block
			
			}      
          
    }
   
}