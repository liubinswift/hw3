package com.viewscenes.web.sysmgr.userLog;

import com.viewscenes.pub.GDSet;
import com.viewscenes.dao.database.DbComponent;

import java.util.ArrayList;

import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;

import org.jmask.web.controller.EXEException;

/**
 * *************************************   
*    
* 项目名称：HW   
* 类名称：OperateLogManager   
* 类描述：   用户日志查询。
* 创建人：刘斌
* 创建时间：Aug 10, 2012 3:16:25 PM   
* 修改人：刘斌
* 修改时间：Aug 10, 2012 3:16:25 PM   
* 修改备注：   
* @version    
*    
***************************************
 */
public class UserLog {
    public UserLog() {
    }
    
    /**
     * queryOperateLog
     * 获得用户操作日志列表
     * @param msg 
     * @return
     */
    public Object queryOperateLog(ASObject obj) {
 
        String param_userID = (String)obj.get("param_userID"); 		//用户ID
        String param_opType =(String)obj.get("param_opType"); 		//操作类型
        String param_begintime =(String)obj.get("param_begintime"); 	//开始时间
        String param_endtime = (String)obj.get("param_endtime"); 		//结束时间

 

        String subsql = "";
        String tableStr = "";
        if(!param_userID.equals("")){
        	subsql = " and user_id="+param_userID;
        }
        if(!param_opType.equals("")){
        	subsql = " and detail.operation_name=ol.operation_name and detail.detail_id="+param_opType;
        	tableStr = " ,sec_operation_detail_tab detail ";
        }
        String sql = " select ol.log_datetime,ol.user_name,ol.operation_name,ol.description "
        		   + " from sec_operation_log_tab ol "+tableStr+" where 1=1"+subsql;
    
        sql += " and ol.log_datetime>=to_date('"+param_begintime+"','YYYY-MM-DD HH24:MI:SS') "
        	+  " and ol.log_datetime<=to_date('"+param_endtime+"','YYYY-MM-DD HH24:MI:SS') ";

        sql += " order by ol.log_datetime desc";
        
        try{
        	
        
        	return StringTool.pageQuerySql(sql.toString(), obj);
        
        }catch (Exception e) {

        	  return new EXEException("","获得用户操作日志列表错误："+ e.getMessage(),null);
		}
    

    }
    
    /**
     * getAllUserList
     * 获得用户列表
     * @param msg 
     * @return
     */
    public Object getAllUserList() {
        
        String sql = "select  user_id, user_name from sec_user_tab order by user_name";
        ArrayList list=new ArrayList();
        try{
	        GDSet set = DbComponent.Query(sql);
	        ASObject obj2=new ASObject();
	        obj2.put("label","全部");
	        obj2.put("data","");
        
        	list.add(obj2);
	        for(int i=0;i<set.getRowCount();i++){

	        	ASObject obj=new ASObject();
	        	obj.put("label",set.getString(i, "user_name"));
	        	obj.put("data",set.getString(i, "user_id"));
	        
	        	list.add(obj);
	        }
	        
        
        }catch (Exception e) {
        	e.printStackTrace();
        	
            return new EXEException("","查询用户列表出错："+ e.getMessage(),null);
		}
      
        return list;

    }
    
    /**
     * getAllOpTypeList
     * 获得日志操作类型列表
     * @param msg 
     * @return
     */
    public Object getAllOpTypeList( ) {

        ArrayList list = new ArrayList();

        String sql = " select detail_id,operation_name from sec_operation_detail_tab where is_log=1 ";
        
        try{
	        GDSet set = DbComponent.Query(sql);
	        ASObject obj2=new ASObject();
	        obj2.put("label","全部");
	        obj2.put("data","");
	        list.add(obj2);
	        
	        for(int i=0;i<set.getRowCount();i++){

	        	ASObject obj=new ASObject();
	        	obj.put("data",set.getString(i, "detail_id"));
	        	obj.put("label",set.getString(i, "operation_name"));
	        	list.add(obj);
	        }
	        
        
        }catch (Exception e) {
     	e.printStackTrace();	
            return new EXEException("","查询用户列表出错："+ e.getMessage(),null);
		}
        
        return list;

    }
}



