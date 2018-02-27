package com.viewscenes.web.daily.queryData;

import com.viewscenes.util.StringTool;
import org.jmask.web.controller.EXEException;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.business.SiteVersionUtil;

import flex.messaging.io.amf.ASObject;

public class IndexQuery {
	public IndexQuery() {
    }
	/**
	 * 
	 * <p>class/function com.viewscenes.web.daily.queryData
	 * <p>explain:ָ�����ݲ�ѯ,�����ӳ���᷵�ش�����Ϣ
	 * <p>author-date:����2012-8-20
	 * @param:
	 * @return:
	 */
	
	public Object Query(ASObject obj){
		    
		    ASObject resObj;
		    String code = (String)obj.get("code");
		    String startTime = (String)obj.get("startTime");
		    String endTime = (String)obj.get("endTime");
//		    String headID = (String)obj.get("headID");
		    String receive_mark = (String)obj.get("receive_mark");//�սӻ���,ȡֵ��Χ��R1,R2,'';
		    String taskId = (String)obj.get("taskId");
		    String freq = (String)obj.get("freq");	
		    String dateType = (String)obj.get("dateType");

		    
		    String headtype = (String)obj.get("headtype");//վ������,ȡֵ��Χ:101,102
		    String abCode = (String)obj.get("abCode");//A��Bվ�����,ȡֵ��Χ��xxxA,xxxB,'';
		    
	        String sql= "";
                sql +=  "  select * from radio_quality_result_tab t where t.is_delete =0 " ;

//	        if(startTime.length()>0)
//	            sql +=" and check_datetime >= to_date('"+startTime+"')";
//
//	        if(endTime.length()>0)
//	            sql +=" and check_datetime <= to_date('"+endTime+"') ";

	        if (dateType.equals("byDate")){
				sql += " and check_datetime >= to_date('"+startTime+" 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ";
				sql += "  and check_datetime <= to_date('"+endTime+" 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ";
			}else{
				sql += " and check_datetime >= to_date('"+startTime+"', 'yyyy-mm-dd hh24:mi:ss') ";
				sql += "  and check_datetime <= to_date('"+endTime+"', 'yyyy-mm-dd hh24:mi:ss') ";
			}
	        
	        String headid = "";
	      //û��ָ��A,B����ôAB��¼���ļ�����ѯ����
			if (abCode ==null || abCode.equals("")){
				//�ɼ���
				if (headtype.equals("101")){
					headid = "'"+SiteVersionUtil.getSiteHeadId(code)+"'";
				//ң��վ
				}else{
					headid += "'" + SiteVersionUtil.getSiteHeadId(code +"A") +"',";
  					headid += "'" + SiteVersionUtil.getSiteHeadId(code +"B") +"',";
  					headid += "'" + SiteVersionUtil.getSiteHeadId(code +"C") +"',";
  					headid += "'" + SiteVersionUtil.getSiteHeadId(code +"D") +"',";
  					headid += "'" + SiteVersionUtil.getSiteHeadId(code +"E") +"',";
  					headid += "'" + SiteVersionUtil.getSiteHeadId(code +"F") +"',";
  					headid += "'" + SiteVersionUtil.getSiteHeadId(code +"G") +"'";
				}
			}else{
				//�ɼ���
				if (headtype.equals("101")){
					headid = "'"+SiteVersionUtil.getSiteHeadId(abCode)+"'";
				//ң��վ
				}else{
					headid = "'" + SiteVersionUtil.getSiteHeadId(abCode) +"'";
					
				}
			}
	        sql +=" and head_id in ("+headid+") ";
	        
	      //���ջ�R1,R2
			if (receive_mark != null && !receive_mark.equals("")){
				sql +=" and  equ_code = '"+receive_mark.toUpperCase()+"'";
			}


	        if(freq.length()>0)
	            sql +=" and frequency = '"+freq+"' ";
	        if(taskId!=null&&taskId.length()>0)
	        	sql +=" and task_id = '"+taskId+"'";

	         sql +=" order by check_datetime desc,frequency ";
	        
	        try {
				resObj = StringTool.pageQuerySql(sql, obj);
			} catch (Exception e) {
				LogTool.fatal(e);
				return new EXEException("",e.getMessage(),"");
			}
	    	
	    	return resObj;
	}
}
