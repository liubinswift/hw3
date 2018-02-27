package com.viewscenes.sys;
/**
 * 表数据存放到缓存的工具类
 * @author  王福祥
 * @date 2012/07/13
 */
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.viewscenes.app.thread.CheakHeadOnLineThread;
import com.viewscenes.bean.DicStateBean;
import com.viewscenes.bean.ResAntennaBean;
import com.viewscenes.bean.ResCityBean;
import com.viewscenes.bean.ResHeadendBean;
import com.viewscenes.bean.ZdicLanguageBean;
import com.viewscenes.bean.DicServicesBean;
import com.viewscenes.bean.pub.StationBean;
import com.viewscenes.dao.XmlReaderDeviceConfig;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
public class TableInfoCache {
	
	private static Element root;
	/**
	 * @param args
	 */
	public TableInfoCache(){
		setMap();
		loadHeadendIsOnlineMap();
	}
	public TableInfoCache(String tableName){
		loadTableMap(tableName);
	}
    /**
     * 从数据库得到全部站点是否在线信息存放到缓存map中
     * @param msg String
     * @return String
     */
	public void loadHeadendIsOnlineMap(){
		CheakHeadOnLineThread online = new CheakHeadOnLineThread();
		online.getAllOnlineInfo();
	}
	/**
	 * 将所有表生成的 HashMap存放到公共的Map中。
	 */
	public void setMap(){
		ArrayList list = readXML();
		HashMap tableMap;
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				String tableName = (String) list.get(i);
				loadTableMap(tableName);
//				SystemTableCache.setTableMap(tableName, tableMap);
			}
		}
	}
	
	
	/**
	 * 读出配置文件中的所有表名称
	 * @return ArrayList
	 */
	public ArrayList readXML(){
		ArrayList list = new ArrayList();
		try{
			URL configFile = XmlReaderDeviceConfig.class.getClassLoader().getResource("daoconfig.xml");
			 
			SAXBuilder builder = new SAXBuilder(false);
		     
			Document doc = builder.build(configFile);
		    
			root = doc.getRootElement();
			  
		    Element e = root.getChild("tablecachconfig");
			List child =  e.getChildren();
			for(int i=0;i<child.size();i++){
				 Element para = (Element) child.get(i);
				 list.add(para.getAttributeValue("table"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 根据表名读取配置文件中的sql语句和表的主键
	 * @param tableName
	 * @return
	 */
	public ArrayList builderSql(String tableName){
		ArrayList list= new ArrayList();
		try{
			
	      URL configFile = XmlReaderDeviceConfig.class.getClassLoader().getResource("daoconfig.xml");
		 
		  SAXBuilder builder = new SAXBuilder(false);
	     
		  Document doc = builder.build(configFile);
	    
		  root = doc.getRootElement();
		  
		  Element e = root.getChild("tablecachconfig");
		  List child =  e.getChildren();
		  for(int i=0;i<child.size();i++){
			  Element para = (Element) child.get(i);
			  if(para.getAttributeValue("table").equalsIgnoreCase(tableName)){
				 list.add(para.getAttributeValue("sql"));
				 list.add(para.getAttributeValue("key"));
				 break;
			  }
		  }
		  
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据表名创建自己的map,表名称为主键 表的信息list为value值
	 * @param tableName
	 */
	public synchronized void loadTableMap(String tableName){
		try {
			String sql=builderSql(tableName).get(0).toString();
			String key_id = builderSql(tableName).get(1).toString();
			GDSet gd = DbComponent.Query(sql);
			gd.setTableName(tableName);
			if(gd.getRowCount()>0){
			
				Map<String,Object> map = null;
				if (gd.getTableName().equalsIgnoreCase(SystemTableCache.RES_CITY_TAB)){
					map = gdSetToPojoMap(gd, ResCityBean.class.getName(),key_id);
				}else if (gd.getTableName().equalsIgnoreCase(SystemTableCache.RES_HEAD_END_TAB)){
					map = gdSetToPojoMap(gd, ResHeadendBean.class.getName(),key_id);
				}else if (gd.getTableName().equalsIgnoreCase(SystemTableCache.DIC_STATE_TAB)){
					map = gdSetToPojoMap(gd, DicStateBean.class.getName(),key_id);
				}else if (gd.getTableName().equalsIgnoreCase(SystemTableCache.RES_TRANSMIT_STATION_TAB)){
					map = gdSetToPojoMap(gd, StationBean.class.getName(),key_id);
				}else if (gd.getTableName().equalsIgnoreCase(SystemTableCache.ZDIC_LANGUAGE_TAB)){
					map = gdSetToPojoMap(gd, ZdicLanguageBean.class.getName(),key_id);
				}else if (gd.getTableName().equalsIgnoreCase(SystemTableCache.DIC_SERVICESAREA_TAB)){
					map = gdSetToPojoMap(gd, DicServicesBean.class.getName(),key_id);
				}
				SystemTableCache.setTableMap(tableName, map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * 针对表的数据操作，刷新map数据
     * @param map
     * @param tableName 表名称,obj 要更新的对象,keyValue 更新对象的主键值,opt操作类型,1添加,2修改,3删除
     */
	public void refreshTableMap(String tableName,Object obj,String keyValue,String opt){
		
//		String keyValue = null;
//		if (tableName.equalsIgnoreCase(SystemTableCache.RES_HEAD_END_TAB)){
//			ResHeadendBean resHeadendBean = (ResHeadendBean)obj;
//			keyValue = resHeadendBean.getCode();
//		}else if (tableName.equalsIgnoreCase(SystemTableCache.RES_CITY_TAB)){
//			ResCityBean resCityBean = (ResCityBean)obj;
//			keyValue = 
//		}
		HashMap<String,Object> map=(HashMap<String,Object>) SystemTableCache.getDataMap(tableName);
		
		if (opt.equals("1") || opt.equals("2"))
			map.put(keyValue, obj);
		else if (opt.equals("3"))
			map.remove(keyValue);
		SystemTableCache.setTableMap(tableName, map);
		
//			ResHeadendBean  tmpBean = (ResHeadendBean)map.get(resHeadendBean.getCode());
//			if (tmpBean != null){
//				map.remove(resHeadendBean.getCode());
//				map.put(resHeadendBean.getCode(), resHeadendBean);
//			}else{
//				map.put(resHeadendBean.getCode(), resHeadendBean);
//			}
//			SystemTableCache.setTableMap(tableName, map);
//		}else if (tableName.equalsIgnoreCase(SystemTableCache.RES_CITY_TAB)){
//			ResCityBean resCityBean = (ResCityBean)obj;
//			
//			HashMap<String,Object> map=(HashMap<String,Object>) SystemTableCache.getDataMap(tableName);
//			ResCityBean  tmpBean = (ResCityBean)map.get(resCityBean.getId());
//			if (tmpBean != null){
//				map.remove(resCityBean.getId());
//				map.put(resCityBean.getId(), resCityBean);
//			}else{
//				map.put(resCityBean.getId(), resCityBean);
//			}
//			SystemTableCache.setTableMap(tableName, map);
//		}else if (tableName.equalsIgnoreCase(SystemTableCache.DIC_STATE_TAB)){
//			DicStateBean dicStateBean = (DicStateBean)obj;
//			
//			HashMap<String,Object> map=(HashMap<String,Object>) SystemTableCache.getDataMap(tableName);
//			DicStateBean  tmpBean = (DicStateBean)map.get(dicStateBean.getState());
//			if (tmpBean != null){
//				map.remove(dicStateBean.getState());
//				map.put(dicStateBean.getState(), dicStateBean);
//			}else{
//				map.put(dicStateBean.getState(), dicStateBean);
//			}
//			SystemTableCache.setTableMap(tableName, map);
//		}else if (tableName.equalsIgnoreCase(SystemTableCache.RES_ANTENNA_TAB)){
//			ResAntennaBean resAntennaBean = (ResAntennaBean)obj;
//			
//			HashMap<String,Object> map=(HashMap<String,Object>) SystemTableCache.getDataMap(tableName);
//			ResAntennaBean  tmpBean = (ResAntennaBean)map.get(resAntennaBean.getId());
//			if (tmpBean != null){
//				map.remove(resAntennaBean.getId());
//				map.put(resAntennaBean.getId(), resAntennaBean);
//			}else{
//				map.put(resAntennaBean.getId(), resAntennaBean);
//			}
//			SystemTableCache.setTableMap(tableName, map);
//		}else if (tableName.equalsIgnoreCase(SystemTableCache.ZDIC_LANGUAGE_TAB)){
//			ZdicLanguageBean zdicLanguageBean = (ZdicLanguageBean)obj;
//			
//			HashMap<String,Object> map=(HashMap<String,Object>) SystemTableCache.getDataMap(tableName);
//			ZdicLanguageBean  tmpBean = (ZdicLanguageBean)map.get(zdicLanguageBean.getLanguage_id());
//			if (tmpBean != null){
//				map.remove(zdicLanguageBean.getLanguage_id());
//				map.put(zdicLanguageBean.getLanguage_id(), zdicLanguageBean);
//			}else{
//				map.put(zdicLanguageBean.getLanguage_id(), zdicLanguageBean);
//			}
//			SystemTableCache.setTableMap(tableName, map);
//		}
//		
		
	}
	
	
	
	/**
	 * map对象转换成pojo
	 * @param map
	 * @param pojo
	 * @return
	 */
	public static Map<String,Object> gdSetToPojoMap(GDSet set,String className,String key_id){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			for(int i=0;i<set.getRowCount();i++){
				
				Object pojo = null;
				try {
					pojo = Class.forName(className).newInstance();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Class classType = pojo.getClass();
				
				for(int j=0;j<set.getColumnCount();j++){
					Field field;
					field = classType.getDeclaredField(set.getColumnName(j).toLowerCase());
					field.setAccessible(true);
					field.set(pojo, set.getString(i, j));
				}
				resultMap.put(set.getString(i, key_id),pojo);
			}
			
		} catch (SecurityException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (GDSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	} 
	
	/**
	 * GDSet转化成bean列表
	 * @detail  
	 * @method  
	 * @param set
	 * @param className
	 * @return 
	 * @return  ArrayList<Object>  
	 * @author  zhaoyahui
	 * @version 2012-9-27 下午03:54:31
	 */
	public static ArrayList<Object> gdSetToPojoList(GDSet set,String className){
		ArrayList<Object> resultMap = new ArrayList<Object>();
		try {
			for(int i=0;i<set.getRowCount();i++){
				
				Object pojo = null;
				try {
					pojo = Class.forName(className).newInstance();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Class classType = pojo.getClass();
				
				for(int j=0;j<set.getColumnCount();j++){
					Field field;
					field = classType.getDeclaredField(set.getColumnName(j).toLowerCase());
					field.setAccessible(true);
					field.set(pojo, set.getString(i, j));
				}
				resultMap.add(pojo);
			}
			
		} catch (SecurityException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (GDSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	} 
}
