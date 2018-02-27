package com.viewscenes.web.sysmgr.indexPan;

import java.util.ArrayList;

import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.database.DbException;
import com.viewscenes.web.sysmgr.indexPan.CustomDao;
import com.viewscenes.pub.GDSet;

import flex.messaging.io.amf.ASObject;

public class CustomService {
	
	/**
	 * <p>class/function:com.viewscenes.framework.service.pub.CustomService.java/getCustomList
	 * <p>explain:
	 * <p>时间：2012-3-8
	 * <p>作者：wxb
	 * @param obj
	 * @return
	 */
	public  Object getCustomList(ASObject obj) {
		String user_id = (String)obj.get("user_id");
		// TODO Auto-generated method stub
		try {
			GDSet gd = CustomDao.getCustomList(user_id);
			
			ArrayList list = new ArrayList();
			
			for(int i=0; i<gd.getRowCount(); i++){
				
				ASObject customObj = new ASObject();
				
				customObj.put("user_id", gd.getString(i, "user_id")) ;
				
				customObj.put("operation_id", gd.getString(i, "operation_id")) ;			
				
				customObj.put("user_name", gd.getString(i, "user_name")) ;
				
				
				list.add(customObj);
			}
			return list;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return new EXEException("", e.getMessage(), "");
		}
	}

}
