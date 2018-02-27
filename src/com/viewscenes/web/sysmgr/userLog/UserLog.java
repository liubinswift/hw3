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
* ��Ŀ���ƣ�HW   
* �����ƣ�OperateLogManager   
* ��������   �û���־��ѯ��
* �����ˣ�����
* ����ʱ�䣺Aug 10, 2012 3:16:25 PM   
* �޸��ˣ�����
* �޸�ʱ�䣺Aug 10, 2012 3:16:25 PM   
* �޸ı�ע��   
* @version    
*    
***************************************
 */
public class UserLog {
    public UserLog() {
    }
    
    /**
     * queryOperateLog
     * ����û�������־�б�
     * @param msg 
     * @return
     */
    public Object queryOperateLog(ASObject obj) {
 
        String param_userID = (String)obj.get("param_userID"); 		//�û�ID
        String param_opType =(String)obj.get("param_opType"); 		//��������
        String param_begintime =(String)obj.get("param_begintime"); 	//��ʼʱ��
        String param_endtime = (String)obj.get("param_endtime"); 		//����ʱ��

 

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

        	  return new EXEException("","����û�������־�б����"+ e.getMessage(),null);
		}
    

    }
    
    /**
     * getAllUserList
     * ����û��б�
     * @param msg 
     * @return
     */
    public Object getAllUserList() {
        
        String sql = "select  user_id, user_name from sec_user_tab order by user_name";
        ArrayList list=new ArrayList();
        try{
	        GDSet set = DbComponent.Query(sql);
	        ASObject obj2=new ASObject();
	        obj2.put("label","ȫ��");
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
        	
            return new EXEException("","��ѯ�û��б����"+ e.getMessage(),null);
		}
      
        return list;

    }
    
    /**
     * getAllOpTypeList
     * �����־���������б�
     * @param msg 
     * @return
     */
    public Object getAllOpTypeList( ) {

        ArrayList list = new ArrayList();

        String sql = " select detail_id,operation_name from sec_operation_detail_tab where is_log=1 ";
        
        try{
	        GDSet set = DbComponent.Query(sql);
	        ASObject obj2=new ASObject();
	        obj2.put("label","ȫ��");
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
            return new EXEException("","��ѯ�û��б����"+ e.getMessage(),null);
		}
        
        return list;

    }
}



