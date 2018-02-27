package com.viewscenes.web.sysmgr.indexPan;

import com.viewscenes.web.sysmgr.indexPan.CustomService;

import flex.messaging.io.amf.ASObject;

public class IndexPan {
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.manager.appManagement.SystemServiceManager.java/getCustomList
	 * <p>explain:获取已定制的功能列表
	 * <p>时间：2012-3-8
	 * <p>作者：wxb
	 * @param obj
	 * @return
	 */
	public Object getCustomList(ASObject obj){
		CustomService cs = new CustomService();
		return cs.getCustomList(obj);
	}

}
