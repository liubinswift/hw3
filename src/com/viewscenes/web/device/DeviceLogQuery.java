package com.viewscenes.web.device;


import org.jmask.web.controller.EXEException;

import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;

public class DeviceLogQuery {
	 public Object getLog_ManageList(ASObject obj){


		 
		    String state=(String)obj.get("state");
	        String headendcode=(String)obj.get("headendcode");
	        String codeAB=(String)obj.get("codeAB");
	  
	        String start_datetime=(String)obj.get("start_datetime");
	        String end_datetime=(String)obj.get("end_datetime");
	        if(!codeAB.equals(""))
		    {
		    	headendcode=codeAB;
		    }
	       
	        StringBuffer sql= new StringBuffer();
	    

	   
	           sql.append("select equ.log_id,equ.head_code,LOG_DATETIME,tt.type,t.category,equ.remark, head.shortname,equ.description from radio_equ_log_tab  equ,dic_equ_log_type_tab tt,dic_equ_category_tab t,  res_headend_tab head " +
	           		"where equ.type_id=tt.type_id(+) and     equ.head_code=head.code and tt.category_id=t.category_id(+) ");
	        

            if(!state.equals("100"))
            {
            	 sql.append(" and head.state = "+state);
            }
	        if(headendcode.length()>0)
	            sql.append(" and equ.head_code like '"+headendcode+"' ");

	        if(start_datetime.length()>0)
	            sql.append(" and equ.log_datetime  >=to_date('" +start_datetime+ "','yyyy-MM-dd HH24:MI:SS') ");

	        if(end_datetime.length()>0)
	            sql.append(" and equ.log_datetime  <=to_date('" +end_datetime+ "','yyyy-MM-dd HH24:MI:SS') ");
	
	        sql.append(" order by log_datetime desc ");


               try {
				return StringTool.pageQuerySql(sql.toString(), obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				  return new EXEException("","查询设备日志出错："+ e.getMessage(),null);
			}
	     

	   

	    }
}
