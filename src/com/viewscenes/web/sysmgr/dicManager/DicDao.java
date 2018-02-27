package com.viewscenes.web.sysmgr.dicManager;


import java.util.ArrayList;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.TableInfoCache;
import com.viewscenes.util.StringTool;

public class DicDao {
	public DicDao() {
		super();
	}
	

	/**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:ɾ�����ݣ������ӳ���᷵�ش�����Ϣ
	 * <p>author-date:���� 2012-7-26
	 * @param:
	 * @return:
	 */
	
	public boolean deleteState(String dellist) throws Exception {
		String sql ="";
		boolean df=false;
		
		sql = "delete from dic_state_tab where state in("+dellist+")";
		
		df=DbComponent.exeUpdate(sql);
		TableInfoCache as =new TableInfoCache();
		return df;
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:ɾ��ǰ�����ݣ������ӳ���᷵�ش�����Ϣ
	 * <p>author-date:���� 2012-7-26
	 * @param:
	 * @return:
	 */
	
	public boolean deleteHead(String dellist) throws Exception {
		String sql ="";
		boolean df=false;
		
		sql = "delete from res_headend_tab where code in("+dellist+")";
		
		df=DbComponent.exeUpdate(sql);
		return df;
	}
	
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:ɾ���������ݣ������ӳ���᷵�ش�����Ϣ
	 * <p>author-date:���� 2012-7-26
	 * @param:
	 * @return:
	 */
	
	public boolean deleteLanguage(String dellist) throws Exception {
		String sql ="";
		boolean df=false;
		
		sql = "delete from zdic_language_tab where language_id in("+dellist+")";
		
		df=DbComponent.exeUpdate(sql);
		return df;
	}
}
