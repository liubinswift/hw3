package com.viewscenes.web.daily.queryData;

import com.viewscenes.util.StringTool;
import org.jmask.web.controller.EXEException;
import com.viewscenes.util.LogTool;
import flex.messaging.io.amf.ASObject;

public class SpectrumQuery {
	public SpectrumQuery() {
    }
	/**
	 * 
	 * <p>class/function com.viewscenes.web.daily.queryData
	 * <p>explain:频谱数据查询,如果添加出错会返回错误信息
	 * <p>author-date:张文2012-8-20
	 * @param:
	 * @return:
	 */
	
	public Object Query(ASObject obj){
	    
	    ASObject resObj;
	    String code = (String)obj.get("code");
	    String codeAB = (String)obj.get("codeAB");
	    String startTime = (String)obj.get("startTime");
	    String endTime = (String)obj.get("endTime");
	    String receive_mark = (String)obj.get("receive_mark");
	    String taskId = (String)obj.get("taskId");
	    String freq = (String)obj.get("freq");	
	    String band = (String)obj.get("band");	
	    String dateType = (String)obj.get("dateType");
	    if(!codeAB.equals(""))
	    {
	    	code=codeAB;
	    }
        String sql= "";
            sql +=  "  select t.* from radio_spectrum_result_tab t,res_headend_tab tt  " +
            		"where t.head_id=tt.head_id and tt.code like '"+code+"' and tt.is_delete=0   and t.is_delete=0  " ;

//        if(startTime.length()>0)
//            sql +=" and check_datetime >= to_date('"+startTime+"')";
//
//        if(endTime.length()>0)
//            sql +=" and check_datetime <= to_date('"+endTime+"') ";
            if (dateType.equals("byDate")){
				sql += " and check_datetime >= to_date('"+startTime+" 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ";
				sql += "  and check_datetime <= to_date('"+endTime+" 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ";
			}else{
				sql += " and check_datetime >= to_date('"+startTime+"', 'yyyy-mm-dd hh24:mi:ss') ";
				sql += "  and check_datetime <= to_date('"+endTime+"', 'yyyy-mm-dd hh24:mi:ss') ";
			}
        if(receive_mark.length()>0)
            sql +=" and  equ_code = '"+receive_mark+"'";

        if(freq.length()>0)
            sql +=" and frequency = '"+freq+"' ";
        
        if(band!=null&&band.length()>0)
        	sql +=" and band = '"+band+"'";

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
