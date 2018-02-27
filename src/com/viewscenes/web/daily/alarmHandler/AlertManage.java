package com.viewscenes.web.daily.alarmHandler;

import java.util.ArrayList;
import java.util.Date;

import com.viewscenes.bean.RadioAbnormalBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.util.LogTool;

import flex.messaging.io.amf.ASObject;
import com.viewscenes.util.StringTool;

import org.jmask.web.controller.EXEException;
public class AlertManage {

	public AlertManage() {
    }
	
	public Object getAlert_ManageList(ASObject obj){
		
		
		    ASObject resObj;
		 
		    String state = (String)obj.get("state");
		    String headendcode = (String)obj.get("headendcode");
		    String codeAB = (String)obj.get("codeAB");
		    String receive = (String)obj.get("receive");
		    String starttime = (String)obj.get("starttime");
		    String enddate = (String)obj.get("enddate");
		    String isresumed = (String)obj.get("isresumed");
		    String ishandled = (String)obj.get("ishandled");
		    String alerttab = (String)obj.get("alerttab");
		    String qtype = (String)obj.get("qtype");	
		    String etype = (String)obj.get("etype");
		    
		    if(!codeAB.equals(""))
		    {
		    	headendcode=codeAB;
		    }
		    String tablename="";
	        if(alerttab.equals("quality"))
	            tablename="radio_quality_alarm_tab";
	        else if(alerttab.equals("equ"))
	            tablename="radio_equ_alarm_tab";

	        String sql= "";
	            sql +=  "select a.*,b.shortname,b.ip,b.type_id,c.ora_alarm_id from "+tablename+" a,res_headend_tab b,radio_abnormal_tab c" +
	            		"  where b.is_delete=0 and a.head_code=b.code" +
	            		"  and a.alarm_id=c.ora_alarm_id(+)" ;

	        if(isresumed.length()>0)
	        	 sql += " and is_resume='"+isresumed+"' ";
	        
	        if(ishandled.length()>0)
	        	sql += " and is_handle='"+ishandled+"' ";
	        
	        if(alerttab.length()>0 && qtype.length()>0)
	        	sql +=" and a.type='"+qtype+"' ";

	        if(alerttab.length()>0 && etype.length()>0)
	        	sql +=" and a.type='"+etype+"' ";
	        
	        if(starttime.length()>0)
	            sql +=" and alarm_datetime >= to_date('"+starttime+"','yyyy-mm-dd hh24:mi:ss')";

	        if(enddate.length()>0)
	            sql +=" and alarm_datetime <= to_date('"+enddate+"','yyyy-mm-dd hh24:mi:ss') ";
	       
	        if(receive.length()>0)
	            sql +=" and  equ_code = '"+receive+"'";
	        if(headendcode.length()>0)
	        {
	        	 sql +=" and b.code like '%"+headendcode+"%'";
	        	

	        }
	        if(!state.equals("100"))
	        {
	        	 sql +=" and b.state = '"+state+"'";
	        	

	        }
	         sql +=" order by alarm_datetime desc ";
	        
	        try {
				resObj = StringTool.pageQuerySql(sql, obj);
			} catch (Exception e) {
				LogTool.fatal(e);
				return new EXEException("",e.getMessage(),"");
			}
	    	
	    	return resObj;
	   }
	
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:删除前端数据，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 */
    public Object delAlert_ManageList(ASObject asObj){
    	 
		String dellist=(String)asObj.get("dellist");
		String alerttab = (String)asObj.get("alerttab");
		String tablename="";
        if(alerttab.equals("quality"))
            tablename="radio_quality_alarm_tab";
        else if(alerttab.equals("equ"))
            tablename="radio_equ_alarm_tab";
        String sql ="";
   
		try{
			
			sql = "delete  from " + tablename + "  where alarm_id in("+dellist+")";
			
			DbComponent.exeUpdate(sql);
			
		 }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("","后台错误!",null);
		}
		 return " ";
	}
    
    public Object updAlert_DetailList(ASObject asObj){
	         ArrayList<Object> list =  new ArrayList<Object>();
		     String updateSql ="";
    	     String operation = (String)asObj.get("operation");
             String alerttab = (String)asObj.get("alerttab");
             String Alarm_id = (String)asObj.get("alarm_id");
             String datetime = (String)asObj.get("datetime");
             String alarmtime = (String)asObj.get("alarmtime");
             String resume_datetime = (String)asObj.get("resume_datetime");
             String code = (String)asObj.get("head_code");
             String operator = (String)asObj.get("operator");
             String auditor = (String)asObj.get("auditor");
             String remark = (String)asObj.get("remark");
             String errorInDb = (String)asObj.get("errorInDb");
             String abnormal_type = (String)asObj.get("abnormal_type");//暂时用head_id存储
          
             String tablename = "";
	     		if (alerttab.equals("quality"))
	     		tablename = "radio_quality_alarm_tab";
	     		else if (alerttab.equals("equ"))
	     		tablename = "radio_equ_alarm_tab";
             
	     		
	    		if(operation.equals("resume"))
	    		{
	    			if (alerttab.equals("quality")){
	    			    updateSql="update "+tablename+" set is_resume =1, resume_datetime='"+resume_datetime+"', alarm_datetime='"+alarmtime+"' where alarm_id ='"+Alarm_id+"' and head_code='"+code+"'";
	    			}else{
	    				
	    				updateSql="update "+tablename+" set is_resume =1, resume_datetime='"+datetime+"'  where alarm_id ='"+Alarm_id+"' and head_code='"+code+"'";
		    			
	    			}
	    		}
	    		else if(operation.equals("handle"))
	    		{
	    			updateSql="update "+tablename+" set is_handle =1,operator='"+operator+"' ,handle_datetime='"+datetime+"',remark='"+remark+"'" +
	    					" where alarm_id ='"+Alarm_id+"' and head_code='"+code+"'";
	    		}
	    		else if(operation.equals("close"))
	    	    {
	    			if (alerttab.equals("quality")){
	    			    updateSql="update "+tablename+" set   resume_datetime='"+resume_datetime+"',head_id='"+abnormal_type +"',"+
	    			    		" alarm_datetime='"+alarmtime+"',errorInDb='"+errorInDb+"' where alarm_id ='"+Alarm_id+"' and head_code='"+code+"'";
	    			}else{
//	    			    updateSql="update "+tablename+" set remark='"+datetime+"'  where alarm_id ='"+Alarm_id+"' and head_code='"+code+"'";
	    	        }
	    	    }
	    		else if(operation.equals("auditor"))
	    		{
	    			updateSql="update "+tablename+" set auditor ='"+auditor+"'  where alarm_id ='"+Alarm_id+"' and head_code='"+code+"'";
	    		}	

           
             try{
         	 DbComponent.exeUpdate(updateSql);
         	 if(errorInDb!=null&&errorInDb.equals("1"))
         	 {
         		StringBuffer sbf = new StringBuffer(" insert into radio_abnormal_tab (ID,ABNORMAL_TYPE,ora_alarm_id)");
            	sbf.append(" values(RADIO_ABNORMAL_SEQ.nextval,'"+abnormal_type+"',"+Alarm_id+")");
           	 DbComponent.exeUpdate(sbf.toString());
         	 }
            }
            catch (DbException e) {
            	e.printStackTrace();
    			EXEException ex =  new EXEException("", ","+e.getMessage(), "");
    			list.add(ex);
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    			return new EXEException("","后台错误!",null);
    		}
//    		list.add("success");
    		return list;
	}
    /**
     * 查询当日报警
     * @author leeo
     * @date 2012/08/25
     * @param obj
     * @return
     */
    public Object queryAlarm(ASObject obj){
    	  ASObject resObj=null;
    	  String type = (String) obj.get("type");
    	  String tableName ="";
    	  if(type.equalsIgnoreCase("quality")){
    		  tableName="radio_quality_alarm_tab";
    	  }else{
    		  tableName="radio_equ_alarm_tab";
    	  }
    	  String sql= "";
          sql +=  "select a.*,b.shortname from "+tableName+" a,res_headend_tab b  where b.is_delete=0 and a.head_code=b.code and a.alarm_datetime >= trunc(sysdate)" ;
          sql +=" order by alarm_datetime desc ";
      	  try {
			resObj = StringTool.pageQuerySql(sql, obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("",e.getMessage(),"");
		}
    	  return resObj;
    }
    /**
     *获取当天所有未处理的指标和设备报警总数。
     * @return
     */
    public Object countAlarm(ASObject obj){
    	int counts=0;
    	String date = StringTool.Date2String(new Date()).split(" ")[0];
    	StringBuffer sql = new StringBuffer("select count(*) as count from radio_quality_alarm_tab t where t.is_handle = 0 and t.alarm_datetime>=to_date('"+date+" 00:00:00','yyyy-mm-dd HH24:mi:ss')");
    	             sql.append(" and t.alarm_datetime<=to_date('"+date+" 23:59:59','yyyy-mm-dd HH24:mi:ss')");
    	             sql.append(" union all select count(*) from radio_equ_alarm_tab t1 where t1.is_handle = 0");
    	             sql.append(" and t1.alarm_datetime>=to_date('"+date+" 00:00:00','yyyy-mm-dd HH24:mi:ss')");
    	             sql.append(" and t1.alarm_datetime<=to_date('"+date+" 23:59:59','yyyy-mm-dd HH24:mi:ss')");
    	try {
			GDSet gd = DbComponent.Query(sql.toString());
			if(gd.getRowCount()>0){
				for(int i=0;i<gd.getRowCount();i++){
					counts+=Integer.parseInt(gd.getString(i, "count"));
				}
			}
		} catch (Exception e) {
			LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
		}
    	return counts;
    	
    }
    /**
     * 根据报警信息的站点code和频率查询运行图信息
     * @author leeo
     * @date 2013/04/11
     * @param obj
     * @return
     */
    public Object getRunplanInfo(ASObject obj,String abnormal_type){
    	 ASObject resObj=new ASObject();
    	 String freq = (String) obj.get("freq");
    	 String code = (String) obj.get("code");
    	 String type_id = (String) obj.get("type_id");
    	 String alarm_starttime = (String) obj.get("alarm_starttime");
    	 String alarm_endtime = (String) obj.get("alarm_endtime");
    	 try {
	    	 if(type_id.equals("102")){
	    		 code = code.substring(0,code.length()-1);
	    	 }
	    	 String sql = "select t.*,rct.city, rct.contry,zlt.language_name from zres_runplan_tab t,res_city_tab rct,zdic_language_tab zlt " +
    	 			" where t.sentcity_id=rct.id(+) and t.language_id=zlt.language_id(+) and t.freq='"+freq+"' and (t.mon_area like '%"+code+"%' or t.xg_mon_area like '%"+code+"%')" +
    	 			"  and t.valid_end_time>=to_date('"+alarm_starttime+"','yyyy-mm-dd hh24:mi:ss') " +
    	 			" and t.valid_start_time<=to_date('"+alarm_endtime+"','yyyy-mm-dd hh24:mi:ss')  order by t.runplan_id desc  " ;
    	 			//" and to_date('1900-01-01 ' || t.end_time || ':00','yyyy-mm-dd hh24:mi:ss')>=to_date('1900-01-01 "+alarm_starttime.split(" ")[1]+"','yyyy-mm-dd hh24:mi:ss') " +
    	 			//" and to_date('1900-01-01 ' || t.start_time || ':00','yyyy-mm-dd hh24:mi:ss')<=to_date('1900-01-01 "+alarm_endtime.split(" ")[1]+"','yyyy-mm-dd hh24:mi:ss')";
	    	 		
			GDSet gd = DbComponent.Query(sql);
			if(gd.getRowCount()>0){
				resObj.put("station", gd.getString(0, "station_name"));
				resObj.put("station_id", gd.getString(0, "station_id"));
				resObj.put("send_county", gd.getString(0, "contry"));
				resObj.put("send_city", gd.getString(0, "city"));
				resObj.put("redisseminators", gd.getString(0, "redisseminators"));
				resObj.put("tran_no", gd.getString(0, "transmiter_no"));
				resObj.put("language_name", gd.getString(0, "language_name"));
				resObj.put("language_id", gd.getString(0, "language_id"));
				resObj.put("power", gd.getString(0, "power"));
				resObj.put("runplan_type_id", gd.getString(0, "runplan_type_id"));
				resObj.put("start_time", gd.getString(0, "start_time"));
				resObj.put("end_time", gd.getString(0, "end_time"));
				resObj.put("abnormal_type", abnormal_type);
			}
		} catch (Exception e) {
			LogTool.fatal(e);
		    return new EXEException("查询运行图信息异常：",e.getMessage(),"");
		}
    	return resObj;
    }
    /**
     * 审核报警
     * @param obj
     * @return
     */
    public Object auditQualityAlarm(ASObject obj){
    	String res="";
    	String auditor=(String) obj.get("auditor");
		String alarm_id=(String) obj.get("alarm_id");
		String head_code = (String) obj.get("head_code");
    	String station=(String) obj.get("station");
    	String station_id=(String) obj.get("station_id");
    	String send_county = (String) obj.get("send_county");
    	String send_city = (String) obj.get("send_city");
    	String redisseminators = (String) obj.get("redisseminators");
    	String tran_no = (String) obj.get("tran_no");
    	String language_name = (String) obj.get("language_name");
    	String language_id = (String) obj.get("language_id");
    	String frequency = (String) obj.get("frequency");
    	String power = (String) obj.get("power");
    	String remote_station = "";
    	String collection_point = "";
    	String head_type_id = (String) obj.get("head_type_id");
    	if(head_type_id.equals("101")){
    		collection_point = (String) obj.get("remote_name");
    	} else if(head_type_id.equals("102")){
    		remote_station = (String) obj.get("remote_name");
    	}
    	String get_type = (String) obj.get("get_type");
    	String abnormal_type = (String) obj.get("abnormal_type");
    	String type = (String) obj.get("type");
    	String play_time = (String) obj.get("play_time");
    	String abnormal_date = (String) obj.get("abnormal_date");
    	String starttime = (String) obj.get("starttime");
    	String endtime = (String) obj.get("endtime");
    	String remark = (String) obj.get("remark");
    	String updateSql="update radio_quality_alarm_tab set auditor ='"+auditor+"'  where alarm_id ='"+alarm_id+"' and head_code='"+head_code+"'";
    	StringBuffer sbf = new StringBuffer(" update radio_abnormal_tab  " +
    			"set STATION_NAME='" +station+
    			"',STATION_ID='" +station_id+
    			"',SEND_COUNTY='" +send_county+
    			"',SEND_CITY='" +send_city+
    			"',BROADCASTING_ORGANIZATIONS='" +redisseminators+
    			"',TRAN_NO='" +tran_no+
    			"',LANGUAGE_NAME='" +language_name+
    			"',LANGUAGE_ID='" +language_id+
    			"',FREQUENCY='" +frequency+
    			"',POWER='" +power+
    			"',REMOTE_STATION='" +remote_station+
    			"',COLLECTION_POINT='" +collection_point+
    			"',GET_TYPE='" +get_type+
    			"',ABNORMAL_TYPE='" +abnormal_type+
    			"',TYPE='" +type+
    			"',PLAY_TIME='" +play_time+
    			"',ABNORMAL_DATE='" +abnormal_date+
    			"',STARTTIME='" +starttime+
    			"',ENDTIME='" +endtime+
    			"',REMARK='" +remark+
    			"',IS_AUDIT='" +1+
    			"' where ora_alarm_id="+alarm_id);
 
    try {
			if(DbComponent.exeUpdate(sbf.toString())){
				if(DbComponent.exeUpdate(updateSql)){
					res = "审核指标报警成功!";
				}else{
					res="指标报警数据审核入库成功,审核状态更新失败！";
				}
			}else{
				res = "审核指标报警失败!";
			}
		} catch (Exception e) {
			LogTool.fatal(e);
		    return new EXEException("审核指标报警异常：",e.getMessage(),"");
		}
    	return res;
    }
    
    
    /**
     * 查询审核过的数据
     * @param obj
     * @return
     */
    public Object queryRadioAbnormal(ASObject obj){
    	String ora_alarm_id=(String) obj.get("ora_alarm_id");
    	String sql = "select * from radio_abnormal_tab where ora_alarm_id="+ora_alarm_id;
    	GDSet gdset;
		RadioAbnormalBean bean = null;
		String abnormal_type="";
		try {
			gdset = DbComponent.Query(sql);
    	
	    	if(gdset.getRowCount()>0){
	    		bean = new RadioAbnormalBean();
	    		bean.setAbnormal_date(gdset.getString(0, "abnormal_date"));
	    		bean.setAbnormal_type(gdset.getString(0, "abnormal_type"));
	    		abnormal_type=gdset.getString(0, "abnormal_type");
	    		bean.setBroadcasting_organizations(gdset.getString(0, "broadcasting_organizations"));
	    		bean.setCollection_point(gdset.getString(0, "collection_point"));
	    		bean.setEndtime(gdset.getString(0, "endtime"));
	    		bean.setFrequency(gdset.getString(0, "frequency"));
	    		bean.setGet_type(gdset.getString(0, "get_type"));
	    		bean.setId(gdset.getString(0, "id"));
	    		bean.setIs_audit(gdset.getString(0, "is_audit"));
	    		bean.setIs_proofread(gdset.getString(0, "is_proofread"));
	    		bean.setLanguage_id(gdset.getString(0, "language_id"));
	    		bean.setLanguage_name(gdset.getString(0, "language_name"));
	    		bean.setPlay_time(gdset.getString(0, "play_time"));
	    		bean.setPower(gdset.getString(0, "power"));
	    		bean.setRemark(gdset.getString(0, "remark"));
	    		bean.setRemote_station(gdset.getString(0, "remote_station"));
	    		bean.setSend_city(gdset.getString(0, "send_city"));
	    		bean.setSend_county(gdset.getString(0, "send_county"));
	    		bean.setStarttime(gdset.getString(0, "starttime"));
	    		bean.setStation_id(gdset.getString(0, "station_id"));
	    		bean.setStation_name(gdset.getString(0, "station_name"));
	    		bean.setTran_no(gdset.getString(0, "tran_no"));
	    		bean.setType(gdset.getString(0, "type"));
	    	}
		} catch (Exception e) {
			LogTool.fatal(e);
		    return new EXEException("查询审核过的数据异常：",e.getMessage(),"");
		}
		if(bean != null&&bean.getIs_audit().equals("0")){
			return getRunplanInfo(obj,abnormal_type);
		} else {
			return bean;
		}
    }
}
