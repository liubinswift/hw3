package com.viewscenes.web.online.dataretrieve;

import java.util.Collection;
import java.util.Iterator;

import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.device.api.OffsetAPI;
import com.viewscenes.device.radio.MsgOffsetHistoryQueryRes;
import com.viewscenes.sys.Security;
import com.viewscenes.util.StringTool;
import com.viewscenes.web.Daoutil.SiteVersionUtil;

import flex.messaging.io.amf.ASObject;
/**
 * 频偏数据回收类
 * @author leeo
 * @date 2012/08/09
 */
public class FrequencyOffsetDataCallback {
    /**
     * 下发频偏数据回收接口，数据回收入库
     * @param obj
     * @return
     */
	public Object offsetDataCallback(ASObject obj){
		String res="频偏数据回收成功";
		String user_id = (String) obj.get("user_id");
		String priority = (String) obj.get("priority");
		String head_id = (String) obj.get("head_id");
		String code = (String) obj.get("code");
		String receiverCode = (String) obj.get("receiverCode");
		String startDate = (String) obj.get("startDate");
		String endDate = (String) obj.get("endDate");
		String taskId = (String) obj.get("taskId");
		String sampleNumber = (String) obj.get("sampleNumber");
		String unit = (String) obj.get("unit");
		String freq = (String) obj.get("freq");
		String band = (String) obj.get("band");
		Security security = new Security();
	    long msgPrio = 0;
	    if (user_id != null) {
	       try {
	           msgPrio = security.getMessagePriority(user_id, 2, 2, 0);
	           priority = new Long(msgPrio).toString();
	       } catch (Exception ex1) {
	           res=ex1.getMessage();
	       }
	    }
	    try {
	    	String report_type = "1";
	        String is_delete = "0";
			MsgOffsetHistoryQueryRes.Result result = OffsetAPI.msgOffsetHistoryQueryCmd(code, receiverCode, freq, band, taskId, startDate, endDate, sampleNumber, unit, priority);
	        if(result!=null){
	        	//解析返回的结果
				String equ_code = result.getEquCode();
		        String task_id = result.getTaskID();
		        Collection resu = result.getOffsets();
		        if(resu!=null){
		        	//根据条件删除原有回收的数据
		        	StringBuffer delsql = new StringBuffer("delete from radio_offset_result_tab where check_datetime <= TO_DATE('"+endDate+"','YYYY-MM-DD HH24:MI:SS')");
		        	delsql.append(" and check_datetime >= TO_DATE('"+startDate+"','YYYY-MM-DD HH24:MI:SS') ");
		        	delsql.append(" and head_id = '"+head_id+"'");
		        	DbComponent.exeUpdate(delsql.toString());
		        	//解析设备返回的数据并存到数据库
		        	Iterator i = resu.iterator();
		        	while(i.hasNext()){
		        		MsgOffsetHistoryQueryRes.Offset os = (MsgOffsetHistoryQueryRes.Offset) i.next();
		                String check_datetime = os.getCheckTime();
		                String frequency = os.getFreq();
		                String bands = os.getBand();
		                String offset = os.getOffset();
		                StringBuffer addsql = new StringBuffer("insert into radio_offset_result_tab");
		                addsql.append(" (result_id, equ_code, task_id, check_datetime, frequency, band, offset, report_type, head_id, is_delete, store_datetime)");
		                addsql.append(" values (RADIO_DATA_RECOVERY_SEQ.nextval, '"+equ_code+"', '"+task_id+"',");
		                addsql.append(" to_date('"+check_datetime+"','YYYY-MM-DD HH24:MI:SS'), "+frequency+","+bands+",");
		                addsql.append(offset+","+report_type+","+head_id+","+is_delete+",sysdate) ");
		                DbComponent.exeUpdate(addsql.toString());
		        	}
		        }
	        }
		} catch (Exception e) {
			if(e.getMessage().equals("回收数据量过大，后台自动回收到数据库中，请到数据查询中查询上报的数据!")){
				//删除数据 防止有重复的数据
	                String sqlerr = " delete from radio_offset_result_tab where check_datetime <= TO_DATE('"+endDate+"','YYYY-MM-DD HH24:MI:SS') "
	                +" and check_datetime >= TO_DATE('"+startDate+"','YYYY-MM-DD HH24:MI:SS') "
	                +" and head_id = '"+head_id+"'";
	                try {
						DbComponent.exeUpdate(sqlerr);
					} catch (DbException e1) {
						e1.printStackTrace();
						res=e1.getMessage();
					}
			}
			e.printStackTrace();
			res=e.getMessage();
		} 
		return res;
	}
	/**
	 * 根据条件从数据库查询回收的频偏数据
	 * @param obj
	 * @return
	 */
	public Object queryOffsetData(ASObject obj){
		ASObject resObj= new ASObject();
		String head_id = (String) obj.get("head_id");
		String equCode = (String) obj.get("receiverCode");
		String startDataTime = (String) obj.get("startDate");
		String endDataTime = (String) obj.get("endDate");
		String freq = (String) obj.get("freq");
		String band = (String) obj.get("band");
		
		StringBuilder sql= new StringBuilder("select t.equ_code,t.task_id,t.check_datetime,t.offset,t.frequency,t.band ");
        sql.append("  from radio_offset_result_tab t ");
        sql.append(" where 1=1 and  check_datetime >=TO_DATE('"+startDataTime+"', 'YYYY-MM-DD HH24:MI:SS') ");
        sql.append(" and check_datetime <=TO_DATE('"+endDataTime+"', 'YYYY-MM-DD HH24:MI:SS') ");

        if(head_id != null&&!head_id.equalsIgnoreCase("")) {
             sql.append(" and head_id = " + head_id + " ");
        }

        if(equCode != null&&!equCode.equalsIgnoreCase("")) {
             sql.append(" and equ_code = '" + equCode + "' ");
        }

        if(band != null&&!band.equalsIgnoreCase("")) {
             sql.append(" and band = " + band + " ");
        }

        if(freq != null&&!freq.equalsIgnoreCase("")) {
             sql.append(" and frequency = " + freq + " ");
        }
        sql.append(" and is_delete = 0 ");
        sql.append(" order by check_datetime desc,frequency");
        
        try {
			resObj=StringTool.pageQuerySql(sql.toString(), obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("","频偏数据查询异常："+e.getMessage(),"");
		}
		return resObj;
	}
}
