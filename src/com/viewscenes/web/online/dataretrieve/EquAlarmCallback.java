package com.viewscenes.web.online.dataretrieve;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.device.api.EquipmentAPI;
import com.viewscenes.device.radio.MsgEquipmentAlarmHistoryQueryRes;
import com.viewscenes.pub.GDSet;
import com.viewscenes.sys.Security;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;
/**
 * �豸����������
 * @author leeo
 * @date 2012/08/10
 */
public class EquAlarmCallback {
/**
 * �����豸�������սӿڣ��ɹ��󽫷������ݴ������ݿ�
 * @param obj
 * @return
 */
	public Object equAlarmCallback(ASObject obj){
		String res="�豸�������ճɹ�";
		String user_id = (String) obj.get("user_id");
		String priority = (String) obj.get("priority");
		String code = (String) obj.get("code");
		String head_id = (String) obj.get("head_id");
		String startDate = (String) obj.get("startDate");
		String endDate = (String) obj.get("endDate");
		String alarmType="";
		Security security = new Security();
        long msgPrio = 0;
        if (user_id != null) {
           try {
               msgPrio = security.getMessagePriority(user_id, 2, 0, 0);
               priority = new Long(msgPrio).toString();
           } catch (Exception ex1) {
              res = ex1.getMessage();
           }
        }
        try {
        	 Collection result =EquipmentAPI.msgEquipmentAlarmHistoryQueryCmd(code, startDate, endDate, alarmType, priority);
        	 if(result!=null){
        		 Iterator i = result.iterator();
        		 while(i.hasNext()){
                     String origin_alarmid = "";
                     String equ_code = "";
                     String type = "";
                     String reason = "";
                     String mode = "";
                     String name = "";
                     String description = "";
                     String alarm_datetime = "";
                     String resume_datetime = "";
                     String is_resume = "";
                     String val = "";
                     String head_code = code;
                     String outputlinelevel = "";
                     String check_datetime = "";
                     String inputlinelevel = "";
                     String upsstatus = "";
                     String batterylevel = "";
                     String linefrequency = "";
                     String is_handle = "";
                     String handle_datetime = "";
                     String operator = "";
                     String remark = "";
                     //�����豸���ص�����
                     MsgEquipmentAlarmHistoryQueryRes.EquipmentAlarm equipmentalarm = ( MsgEquipmentAlarmHistoryQueryRes.EquipmentAlarm) i.next();

                     origin_alarmid = equipmentalarm.getAlarmID();
                     check_datetime = equipmentalarm.getCheckTime();
                     description = equipmentalarm.getDesc();
                     equ_code = equipmentalarm.getDeviceCode();
                     mode = equipmentalarm.getMode();
                     reason = equipmentalarm.getReason();
                     type = equipmentalarm.getType();
                         //MAP
                     Map alarmparams = equipmentalarm.getAlarmParams();
                     if (alarmparams != null) {
                         for (Iterator it = alarmparams.entrySet().iterator(); it.hasNext(); ) {
                            Map.Entry o = (Map.Entry) it.next();
                            val = o.getValue().toString();
                            name = o.getKey().toString();
                            //�ж�nameֵ��valueֵ
                            if (name.equalsIgnoreCase("OutputLineLevel")) {
                               outputlinelevel = val;
                            }
                            else if (name.equalsIgnoreCase("InputLineLevel")) {
                               inputlinelevel = val;
                            }
                            else if (name.equalsIgnoreCase("LineFrequency")) {
                               linefrequency = val;
                            }
                            else if (name.equalsIgnoreCase("BatteryLevel")) {
                               batterylevel = val;
                            }
                            else if (name.equalsIgnoreCase("UPSStatus")) {
                               upsstatus = val;
                            }
                        }
                     }
                     //���ݱ���id��code��ѯ�����Ƿ��Ѿ�����
                     String sql =
                         "select alarm_id from radio_equ_alarm_tab where  origin_alarmid=" +
                         origin_alarmid + " and head_code='" + head_code + "'  ";

                     GDSet gd = DbComponent.Query(sql);
                     if(gd.getRowCount()>0){//����Ѿ������޸�ԭ���ı���
                    	 String sql1 = "";
                         if (mode.equalsIgnoreCase("1")) {
                                 sql1 = "update radio_equ_alarm_tab set is_resume=1, resume_datetime=to_date('"
                                         + check_datetime
                                         + "', 'YYYY-MM-DD HH24:MI:SS') where origin_alarmid = "
                                         + origin_alarmid
                                         + " and head_code = '"
                                         + head_code
                                         + "'";
                         }
                         if (mode.equalsIgnoreCase("0")) {
                                 sql1 = "update radio_equ_alarm_tab set alarm_datetime=to_date('"
                                         + check_datetime
                                         + "', 'YYYY-MM-DD HH24:MI:SS') where origin_alarmid = "
                                         + origin_alarmid
                                         + " and head_code = '"
                                         + head_code
                                         + "'";
                         }
                         DbComponent.exeUpdate(sql1);
                     }else{//�����ڽ����������
                    	 is_handle = "0";
                         is_resume = mode;
                         if (mode.equals("0")) {
                             alarm_datetime = check_datetime;
                         } else {
                             resume_datetime = check_datetime;
                         }
                         StringBuffer addsql = new StringBuffer("insert into radio_equ_alarm_tab ");
                     	addsql.append("( alarm_id,head_id,head_code,equ_code,origin_alarmid,alarm_datetime,resume_datetime,is_resume,");
                     	addsql.append("type,description,reason,outputlinelevel,inputlinelevel,linefrequency,batterylevel,upsstatus,report_type,");
                     	addsql.append("is_handle,handle_datetime,operator,remark,is_delete)");
                     	addsql.append(" values (RADIO_ALARM_SEQ.nextval,'"+head_id+"','"+head_code+"','"+equ_code+"',");
                     	addsql.append("'"+origin_alarmid+"','"+alarm_datetime+"','"+resume_datetime+"','"+is_resume+"','"+type+"',");
                     	addsql.append("'"+description+"','"+reason+"','"+outputlinelevel+"','"+inputlinelevel+"','"+linefrequency+"',");
                     	addsql.append("'"+batterylevel+"','"+upsstatus+"',1,'"+is_handle+"','"+handle_datetime+"','"+operator+"','"+remark+"',0)");
                     	DbComponent.exeUpdate(addsql.toString());
                     }
        		 }
        	 }
		} catch (Exception e) {
			e.printStackTrace();
			res="�豸��̬����ʧ�ܣ�ԭ��"+e.getMessage();
		} 
		return res;
	}
	/**
	 * �����ݿ��ѯ�豸���յı�����Ϣ
	 * @param object
	 * @return
	 */
	public Object queryEquAlarm(ASObject obj){
		ASObject resObj = new ASObject();
		String code = (String) obj.get("code");
		String head_id = (String) obj.get("head_id");
		String startDate = (String) obj.get("startDate");
		String endDate = (String) obj.get("endDate");
		StringBuffer sql= new StringBuffer();
		sql.append("select a.alarm_id,b.shortname head_name,c.type as type_name," );
		sql.append("decode(a.is_resume,0,'��'||a.alarm_datetime||'δ���',1,'��'||a.alarm_datetime||'��'||a.resume_datetime) as alarm_datetime,");
		sql.append("decode(a.is_handle,0,'δ����',1,'�Ѵ�����'||a.handle_datetime) is_handle,a.reason");
		sql.append(" from radio_equ_alarm_tab a,res_headend_tab b,dic_equ_alarm_type_tab c where b.is_delete=0 and a.head_code=b.code and a.type=c.alarm_type_id  ");
		if(code!=null&&!code.equalsIgnoreCase("")){
			sql.append(" and code ='"+code+"'");
		}
		if(startDate.length()>0){
			sql.append(" and alarm_datetime >= '"+startDate+"' ");
		}
		if(endDate.length()>0){
			sql.append(" and alarm_datetime <= '"+endDate+"' ");
		}
		sql.append(" order by alarm_datetime desc ");
		try {
			resObj = StringTool.pageQuerySql(sql.toString(), obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("","�豸������ѯ�쳣:>>"+e.getMessage(),"");
		}
		return resObj;
	}
}
