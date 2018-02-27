package com.viewscenes.web.online.dataretrieve;

import java.util.Collection;
import java.util.Iterator;

import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.device.api.SpectrumAPI;
import com.viewscenes.device.radio.MsgSpectrumHistoryQueryRes;
import com.viewscenes.sys.Security;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;

public class SpectrumDataCallback {
	/**
	 * 频谱数据回收调用接口
	 * @param obj
	 * @return
	 */
	public Object spectrumDataCallback(ASObject obj){
		String res="频谱数据回收成功";
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
			MsgSpectrumHistoryQueryRes.Result result= SpectrumAPI.msgSpectrumHistoryQueryCmd(code, receiverCode, taskId, startDate, endDate, sampleNumber, unit, priority);
			// 解析返回结果
	        String equ_code = result.getEquCode();
	        String task_id = result.getTaskID();
	        Collection resu = result.getSpectrumScan();
	        String frequency = "";
	        String band = "";
	        String report_type = "1";
	        String is_delete = "0";
			if(result!=null){
				if(resu!=null){
					//根据条件删除原来回收的数据
					StringBuffer delsql = new StringBuffer("delete from radio_spectrum_result_tab where check_datetime <= TO_DATE('"+endDate+"','YYYY-MM-DD HH24:MI:SS')");
					delsql.append(" and check_datetime >= TO_DATE('"+startDate+"','YYYY-MM-DD HH24:MI:SS')");
					delsql.append(" and head_id = '"+head_id+"'");
					DbComponent.exeUpdate(delsql.toString());
					//解析回收的数据，然后入库。
					Iterator iterator = resu.iterator();
					while(iterator.hasNext()){
						  MsgSpectrumHistoryQueryRes.SpectrumScan spectrum = (MsgSpectrumHistoryQueryRes.SpectrumScan) iterator.next();
						  String check_datetime = spectrum.getScanTime();
						  String spectrumid=spectrum.getSpectrumID();
						  Collection scanresult = spectrum.getScanResults();
						  if(scanresult!=null){
							  Iterator q = scanresult.iterator();
							  while(q.hasNext()){
								  MsgSpectrumHistoryQueryRes.ScanResult scan = (MsgSpectrumHistoryQueryRes.ScanResult) q.next();
								  band = scan.getBand();
								  frequency = scan.getFreq();
	                              String e_level = scan.getLevel();
	                              StringBuffer addsql = new StringBuffer(" insert into radio_spectrum_result_tab ");
	                              addsql.append(" (result_id, equ_code, task_id, check_datetime, frequency,band, e_level, report_type, head_id,  is_delete, store_datetime) ");
	                              addsql.append(" values ");
	                              addsql.append(" (RADIO_DATA_RECOVERY_SEQ.nextval, '"+equ_code+"', '"+task_id+"', ");
	                              addsql.append("to_date('"+check_datetime+"','YYYY-MM-DD HH24:MI:SS'), "+frequency+", ");
	                              addsql.append("'"+band+"', ");
	                              addsql.append("'"+e_level+"', ");
	                              addsql.append("'"+report_type+"', ");
	                              addsql.append("'"+head_id+"', ");
	                              addsql.append(is_delete+", ");
	                              addsql.append("sysdate )");
	                              DbComponent.exeUpdate(addsql.toString());

							  }
						  }
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage().indexOf("回收数据量过大，后台自动回收到数据库中，请到数据查询中查询上报的数据!")>-1){
				String sql = " delete from radio_spectrum_result_tab where check_datetime <= TO_DATE('"+endDate+"','YYYY-MM-DD HH24:MI:SS') "
      			+ " and check_datetime >= TO_DATE('"+startDate+"','YYYY-MM-DD HH24:MI:SS') "
      			+ " and head_id = '"+head_id+"'";
                try {
					DbComponent.exeUpdate(sql);
				} catch (DbException e1) {
					e1.printStackTrace();
					res = e1.getMessage();
				}
			}
			res=e.getMessage();
		} 
	    
		return res;
	}
	/**
	 * 查询频谱回收数据
	 * @return
	 */
	public Object querySpectrumData(ASObject obj){
		ASObject resObj= new ASObject();
		String head_id = (String) obj.get("head_id");
		String equCode = (String) obj.get("receiverCode");
		String startDataTime = (String) obj.get("startDate");
		String endDataTime = (String) obj.get("endDate");
		StringBuilder sql= new StringBuilder("select t.equ_code,t.task_id,t.check_datetime,t.band,t.frequency,t.e_level,t.spectrum_id ");
        sql.append("  from radio_spectrum_result_tab t ");
        sql.append(" where 1=1 and  check_datetime >=TO_DATE('"+startDataTime+"', 'YYYY-MM-DD HH24:MI:SS') ");
        sql.append(" and check_datetime <=TO_DATE('"+endDataTime+"', 'YYYY-MM-DD HH24:MI:SS') ");
        if(head_id != null&&!head_id.equalsIgnoreCase("")) {
            sql.append(" and head_id = " + head_id + " ");
        }
        if(equCode != null&&!equCode.equalsIgnoreCase("")) {
            sql.append(" and equ_code = '" + equCode + "' ");
        }
        sql.append(" and is_delete = 0 order by check_datetime,frequency  ");
        try {
			resObj=StringTool.pageQuerySql(sql.toString(), obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("","频谱数据查询异常："+e.getMessage(),"");
		}
		return resObj;
	}

}
