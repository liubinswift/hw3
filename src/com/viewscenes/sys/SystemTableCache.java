package com.viewscenes.sys;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放系统表缓存的公共类，map中以表名称为键，每张表的map为值。
 * @author 王福祥
 * @since 2012/07/13
 */
public class SystemTableCache {
	
	  public static final String RES_HEAD_END_TAB = "res_headend_tab";//前端表
	  public static final String RES_CITY_TAB = "res_city_tab";		  //外国城市表
	  public static final String DIC_STATE_TAB = "dic_state_tab";	  //大洲表	
	  public static final String RES_TRANSMIT_STATION_TAB = "res_transmit_station_tab";	//发射台
	  public static final String ZDIC_LANGUAGE_TAB = "zdic_language_tab";//语言
	  public static final String DIC_SERVICESAREA_TAB = "dic_servicesarea_tab";//服务区
	  /**
	   * 缓存数据MAP
	   * 数据结构:cachedTableMapMap{key,Map{key,Object}}
	   * 
	   */
	  public static HashMap<String,Map<String,Object>> cachedTableMapMap = new HashMap<String,Map<String,Object>>();
	  
	  public SystemTableCache() {

	  }
	  public static void setTableMap(String tableName, Map<String,Object> map){
		  synchronized (cachedTableMapMap){
			  cachedTableMapMap.put(tableName,map);
		  }
	  }

	  public static Map<String,Object> getDataMap(String tableName){
	    return cachedTableMapMap.get(tableName);
	  }
}
