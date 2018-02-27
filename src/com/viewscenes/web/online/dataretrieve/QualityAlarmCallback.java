package com.viewscenes.web.online.dataretrieve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.device.api.QualityAPI;
import com.viewscenes.device.radio.MsgQualityAlarmHistoryQueryRes;
import com.viewscenes.pub.GDSet;
import com.viewscenes.sys.Security;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;
/**
 * ָ����̬������
 * @author leeo
 * @2012/08/10
 */
public class QualityAlarmCallback {
   /**
    * ����ָ����̬���սӿڣ����ӿڷ��ص�������⡣
    * @param obj
    * @return
    */
	public Object qualityAlarmCallback(ASObject obj){
		String res="ָ����̬���ճɹ�";
		String user_id = (String) obj.get("user_id");
		String priority = (String) obj.get("priority");
		String code = (String) obj.get("code");
		String head_id = (String) obj.get("head_id");
		String receiverCode = (String) obj.get("receiverCode");
		String startDate = (String) obj.get("startDate");
		String endDate = (String) obj.get("endDate");
		String band = (String) obj.get("band");
		String freq = (String) obj.get("freq");
		Security security = new Security();
	    long msgPrio = 0;
	    if (user_id != null) {
	        try {
	           msgPrio = security.getMessagePriority(user_id, 1, 0, 0);
	           priority = new Long(msgPrio).toString();
	        } catch (Exception ex1) {
	              res=ex1.getMessage();
	        }
	    }
	    try {
			MsgQualityAlarmHistoryQueryRes.Result result = QualityAPI.msgQualityAlarmHistoryQueryCmd(code, receiverCode, freq, band, startDate, endDate, priority);
		    if(result!=null){
		    	//�������ؽ�� ���Ҵ������ݿ�
				Collection resu = result.getQualityAlarms();
				Iterator i = resu.iterator();
				while(i.hasNext()){
					MsgQualityAlarmHistoryQueryRes.QualityAlarm qualityalarm = (MsgQualityAlarmHistoryQueryRes.QualityAlarm) i.next();
	                String origin_alarmid = qualityalarm.getAlarmID();
	                band = qualityalarm.getBand();
	                String frequency = qualityalarm.getFreq();
	                String description = qualityalarm.getDesc();
	                String equ_code = qualityalarm.getEquCode();
	                String check_datetime = qualityalarm.getCheckTime();
	                String mode = qualityalarm.getMode();
	                String reason = qualityalarm.getReason();
	                String type = qualityalarm.getType();
	                String alarmid = qualityalarm.getAlarmID();
	                String alarm_datetime="";
	                String resume_datetime="";
	                String e_level="";
	                String fm_Modulation="";
	                String am_modulation="";
	                String attenuation="";
	                String remark="";
	                String operator="";
	                String handle_datetime="";
	                Map alarmparams = qualityalarm.getAlarmParams();

	                for (Iterator it = alarmparams.entrySet().iterator(); it.hasNext(); ) {

	                    Map.Entry o = (Map.Entry) it.next();
	                    String val = o.getValue().toString();
	                    String name = o.getKey().toString();

	                    //�ж�nameֵ��valueֵ
	                    if (name.equalsIgnoreCase("Level")) {

	                        e_level = val;
	                    }
	                    else if (name.equalsIgnoreCase("FM-Modulation")) {

	                        fm_Modulation = val;
	                    }
	                    else if (name.equalsIgnoreCase("AM-Modulation")) {

	                        am_modulation = val;
	                    }
	                    else if (name.equalsIgnoreCase("Attenuation")) {

	                         attenuation = val;
	                    }

	                }
	                String sql =
	                    "select origin_alarmid from radio_quality_alarm_tab where  origin_alarmid=" +
	                    origin_alarmid + " and head_code='" + code + "'  ";

	                GDSet gd = DbComponent.Query(sql);
	                if(gd.getRowCount()>0){
	                	String updatesql = "";
	                    if (mode.equalsIgnoreCase("1")) {
	                        
	                    	updatesql = "update radio_quality_alarm_tab set is_resume=1, resume_datetime=to_date('"
	                                    + check_datetime
	                                    + "', 'YYYY-MM-DD HH24:MI:SS') where origin_alarmid = "
	                                    + alarmid
	                                    + " and head_code = '"
	                                    + code
	                                    + "'";
	                        
	                    }
	                    if (mode.equalsIgnoreCase("0")) {
	                    	updatesql = "update radio_quality_alarm_tab set alarm_datetime=to_date('"
	                                    + check_datetime
	                                    + "', 'YYYY-MM-DD HH24:MI:SS') where origin_alarmid = "
	                                    + alarmid
	                                    + " and head_code = '"
	                                    + code
	                                    + "'";
	                    }
	                    DbComponent.exeUpdate(updatesql);
	                }else{
	                	 String is_handle = "0";
	                     String is_resume = mode;
	                     if (mode.equals("0")) {
	                        alarm_datetime = check_datetime;
	                     } else {
	                         resume_datetime = check_datetime;
	                     }
	                	StringBuffer addsql = new StringBuffer("insert into radio_quality_alarm_tab ");
	                	addsql.append("( alarm_id,equ_code,origin_alarmid,alarm_datetime,resume_datetime,is_resume,");
	                	addsql.append("frequency,type,description,reason,e_level,am_modulation,fm_modulation,attenuation,");
	                	addsql.append("head_code,head_id,report_type,is_handle,remark,operator,handle_datetime,band)");
	                	addsql.append(" values (RADIO_ALARM_SEQ.nextval,'"+equ_code+"','"+origin_alarmid+"','"+alarm_datetime+"',");
	                	addsql.append("'"+resume_datetime+"','"+is_resume+"','"+frequency+"','"+type+"','"+description+"',");
	                	addsql.append("'"+reason+"','"+e_level+"','"+am_modulation+"','"+fm_Modulation+"','"+attenuation+"',");
	                	addsql.append("'"+code+"','"+head_id+"',1,'"+is_handle+"','"+remark+"','"+operator+"','"+handle_datetime+"','"+band+"')");
	                	DbComponent.exeUpdate(addsql.toString());
	                }


				}
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
			res="ָ����̬����ʧ�ܣ�ԭ��"+e.getMessage();
		}
		return res;
	}
	/**
	 * �����ݿ��ѯ���յ�ָ�걨������
	 * @param obj
	 * @return
	 */
	public Object queryQualityAlarm(ASObject obj){
		ASObject resObj = new ASObject();
		String code = (String) obj.get("code");
		String head_id = (String) obj.get("head_id");
		String receiverCode = (String) obj.get("receiverCode");
		String startDate = (String) obj.get("startDate");
		String endDate = (String) obj.get("endDate");
		String band = (String) obj.get("band");
		String freq = (String) obj.get("freq");
		StringBuilder sql= new StringBuilder(" ");
        sql.append("SELECT res.shortname head_name,tdic.type type_name,tab.frequency," );
        sql.append(" decode(tab.is_resume,0,'��'||alarm_datetime||'δ���','��'||alarm_datetime||'��'||tab.resume_datetime) alarm_datetime,") ;
        sql.append(" decode(tab.is_handle,0,'δ����','�Ѵ���') is_handle,tab.band,tab.alarm_id,tab.resume_datetime,tab.is_resume, edic.name  equ_name");
        sql.append(" FROM radio_quality_alarm_tab  tab,dic_quality_alarm_type_tab tdic,dic_equ_type_tab  edic,res_headend_tab  res ");
        sql.append(" WHERE tab.type = tdic.alarm_type_id ");
        sql.append(" and tab.equ_code = edic.code ");
        sql.append(" and tab.head_code = res.code ");
        sql.append(" and res.is_delete = 0  ");
        sql.append(" and tab.alarm_datetime >= TO_DATE('"+startDate+"', 'YYYY-MM-DD HH24:MI:SS') ");
        sql.append(" and tab.alarm_datetime <=TO_DATE('"+endDate+"', 'YYYY-MM-DD HH24:MI:SS') ");

        if((code != null) && !code.equalsIgnoreCase("")) {
            sql.append(" and head_code = '" + code + "' ");
        }

        if((receiverCode != null) && !receiverCode.equalsIgnoreCase("")) {
            sql.append(" and equ_code = '" + receiverCode + "' ");
        }

        if((band != null) && !band.equalsIgnoreCase("")) {
        	if((band != null) && !band.equalsIgnoreCase("")) {
            	if(band.equals("0"))
            	{
            		sql.append(" and frequency<=26100 and frequency>=2300");
            	}
            	else if(band.equals("1"))
            	{
            		sql.append(" and frequency<=1602 and frequency>=531");
            	}
            	else if(band.equals("2"))
            	{
            		sql.append(" and frequency<=108000 and frequency>=87000");
            	}
              
            }

        }

        if((freq != null) && !freq.equalsIgnoreCase("")) {
            sql.append(" and frequency = " + freq + " ");
        }
        sql.append("order by  tab.frequency,tab.alarm_datetime desc ");
        try {
			resObj = StringTool.pageQuerySql(sql.toString(), obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("","ָ�걨����ѯ�쳣��"+e.getMessage(),"");
		}
		return resObj;
	}
	/**
	 * ����վ��code ��������в�ѯ�Ѿ��·�����Ƶ��
	 * @param obj
	 * @return
	 * �޸��ˣ�����
	 * �޸�Ŀ�ģ�û��check_quality_task���ֶ���TASK_TYPE�б���������1ָ��2¼��3Ƶƫ4Ƶ��
	 * ��������ȡƵ��ʱ������Чʱ���������startTime��endTime
	 */
	public Object getFreqList(ASObject obj){
		ArrayList list = new ArrayList();
		String code = (String) obj.get("code");
		String type = (String) obj.get("type");
		String startTime = (String) obj.get("start_time");
		String endTime = (String) obj.get("end_time");
		String queryColumn="check_quality_task";
        String tableName="radio_unify_task_tab";
        if(type.equalsIgnoreCase("quality")){
        	queryColumn=" and task.TASK_TYPE=1";
        }
        if(type.equalsIgnoreCase("offset")){
        	queryColumn="and task.TASK_TYPE=3";
        }
        if(type.equalsIgnoreCase("stream")){
        	queryColumn="and task.TASK_TYPE=2";
        }
        if(type.equalsIgnoreCase("spectrum")){
        	queryColumn="and task.TASK_TYPE=4";
        	
        }
        if(code!=null&&!code.equalsIgnoreCase("")){
          String sql =
                 "select  distinct subtask.freq  " +
                 " from radio_sub_task_tab subtask," +
                 " "+tableName+" task" +
                 " where subtask.task_id= task.task_id and task.is_send='1' " +
                 " and task.is_delete='0' "+queryColumn+"" +
                 " and  task.head_code='" + code + "' " +
                 " and task.valid_startdatetime>=to_date('"+startTime+"','YYYY-MM-DD HH24:MI:SS')" +
                 " and task.valid_startdatetime<=to_date('"+endTime+"','YYYY-MM-DD HH24:MI:SS')";//�������޸� 2012/01/05 ԭ�����ݽ���ʱ���ж�������
          try {
			GDSet gd = DbComponent.Query(sql);
			for(int i=0;i<gd.getRowCount();i++){
//				ASObject objRes = new ASObject();
//				objRes.put("label", (gd.getString(i, "freq")));
				list.add(gd.getString(i, "freq"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("",e.getMessage(),"");
		}
          
        }
		return list;
	}
}
