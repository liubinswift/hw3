package com.viewscenes.web.sysmgr.dicManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jmask.web.controller.EXEException;

import com.viewscenes.bean.ResHeadendBean;
import com.viewscenes.bean.ZdicLanguageBean;
import com.viewscenes.dao.database.DbException;

import com.viewscenes.web.sysmgr.dicManager.DicDao;

import com.viewscenes.pub.GDSet;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;
public class DicService {
	public DicService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:查询大洲，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 */
	public Object getState(ASObject obj){
		    ASObject resObj;
	        
	        String sql = "select * from dic_state_tab order by state_name desc ";
	        
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
	 * <p>explain:查询前端数据 ，如果查询出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 */
	public Object getHeadend(ASObject obj){
		    ASObject resObj;
		    String type_id = (String)obj.get("type_id");
		    String is_online = (String)obj.get("is_online");
		    String shortname = (String)obj.get("shortname");
		    String site = (String)obj.get("site");
		    String code = (String)obj.get("code");
		    String com_protocol = (String)obj.get("com_protocol");
//		    String is_delete = (String)obj.get("is_delete");
		    
		    
	        String sql = "select * from res_headend_tab where is_delete=0";
	        
	        if(type_id.length()>0)
	        	 sql += " and type_id  in('"+type_id+"') ";
	        if(!com_protocol.equals("0")){
	        	if(com_protocol.equals("2")){
	        		 sql += " and com_protocol  ='"+com_protocol+"'";
	        	}else {
	        		sql += " and (com_protocol is null or com_protocol ='1')";
	        	}
	        }
	        	
	        
	        if(shortname.length()>0)
	        	sql += " and shortname  like('%"+shortname+"%')";
	        
	        if(code.length()>0)
	        	sql += " and code like('%"+code.toUpperCase()+"%')";
	        if(is_online.length()>0){
	            sql +=" and is_online ='"+is_online+"'";
	        }
//	        if(is_delete.length()>0){
//	            sql +=" and is_delete ='"+is_delete+"'";
//	        }
	        if(site.length()>0)
	        	sql += " and site like('%"+site+"%')";
	        
	        sql +=" order by shortname desc ";
	        try {
				resObj = StringTool.pageQuerySql(sql, obj);
			} catch (Exception e) {
				LogTool.fatal(e);
				return new EXEException("",e.getMessage(),"");
			}			resObj.get("resultList");
		
	    	return resObj;
	}
	
	
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:查询语言，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 */
	public Object getLanguage(ZdicLanguageBean bean){
		    ASObject resObj;
	        
	        String sql = "select * from zdic_language_tab where is_delete =0  order by language_name desc ";
	        
	        try {
				resObj = StringTool.pageQuerySql(sql, bean);
			} catch (Exception e) {
				LogTool.fatal(e);
				return new EXEException("",e.getMessage(),"");
			}
	    	
	    	return resObj;
	}
	
}
