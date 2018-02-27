package com.viewscenes.web.runplan.wgt_runplan;

import java.util.ArrayList;

import org.jmask.web.controller.EXEException;

import com.viewscenes.bean.runplan.GJTLDRunplanBean;
import com.viewscenes.bean.runplan.WGTRunplanBean;
import com.viewscenes.pub.GDSet;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;

/**
 * 外国台运行图处理类
 * @author leeo
 * @date 2012/08/01
 */
public class WGTRunplan {

	public WGTRunplan() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 根据条件查询外国台运行图
	 * @param bean
	 * @return list
	 */
	public Object queryRunplan(WGTRunplanBean bean){
		
		WGTRunplanDAO dao = new WGTRunplanDAO();
		ASObject resObj;
		ArrayList result;
		try{
			 resObj = dao.queryRunplan(bean);
			 ArrayList list =(ArrayList) resObj.get("resultList") ;
			 String classpath = WGTRunplanBean.class.getName();
			 result = StringTool.convertFlexToJavaList(list, classpath);
			 resObj.put("resultList", result);
		}catch(Exception e){
			e.printStackTrace();
			return new EXEException("",e.getMessage(),"");
		}
		return resObj;
	}

}
