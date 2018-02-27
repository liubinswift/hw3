package com.viewscenes.web.sysmgr.indexPan;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;

public class CustomDao {
	
	public static GDSet getCustomList(String user_id) throws DbException{
		String sql = "select c.*, user_name from sec_user_operation_custom_tab c,sec_user_tab u where u.user_id=c.user_id and u.user_id='"+user_id+"'";
		
		return DbComponent.Query(sql);
	}

}
