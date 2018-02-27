package com.viewscenes.web.common;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.jmask.web.controller.EXEException;

import com.viewscenes.bean.DicServicesBean;
import com.viewscenes.bean.DicStateBean;
import com.viewscenes.bean.ResAntennaBean;
import com.viewscenes.bean.ResCityBean;
import com.viewscenes.bean.ResHeadendBean;
import com.viewscenes.bean.RunplanTypeBean;
import com.viewscenes.bean.SeasonBean;
import com.viewscenes.bean.ZdicLanguageBean;
import com.viewscenes.bean.pub.StationBean;
import com.viewscenes.bean.runplan.RunplanBean;
import com.viewscenes.dao.XmlReader;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.SystemTableCache;
import com.viewscenes.sys.TableInfoCache;
import com.viewscenes.util.ChineseUtil;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.util.business.ReceiverListGetUtil;
import com.viewscenes.util.business.SimplePing;
import com.viewscenes.web.sysmgr.user.PubUserManagerServiceDao;

import flex.messaging.io.amf.ASObject;

public class Common {
	public static ArrayList<Object> headendListDistinct;
	public static ArrayList<Object> headendList;
	/**
	 * 获取系统公共数据
	 * @detail  大洲、站点、
	 * @method  
	 * @param obj
	 * @return 
	 * @return  Object  
	 * @author  zhaoyahui
	 * @version 2012-7-19 上午10:30:17
	 */
//	public Object getConfigData(ASObject obj){
//	ArrayList list = new ArrayList();
//	ArrayList<DicStateBean> stateList = (ArrayList<DicStateBean>)getStateList(new ASObject());
//	ArrayList<ResHeadendBean> headList = (ArrayList<ResHeadendBean>)getHeadendList(new ASObject());
//	HashMap<String,String> hm = new HashMap<String,String>();
//	for(int j=0;j<stateList.size();j++){
//		hm.put(stateList.get(j).getState(),stateList.get(j).getState_name());
//	}
//	for(int i=0;i<headList.size();i++){
//		ResHeadendBean headbean = headList.get(i);
//		headbean.setStateName((hm.get(headbean.getState()) == null?"":hm.get(headbean.getState())));
//		headList.set(i,headbean);
//	}
//	list.add(stateList);
//	list.add(headList);
//	return list;
//}

	public Object getConfigData(ASObject obj){
		ArrayList list = new ArrayList();
		ArrayList stateList = new ArrayList();
		ArrayList headList = new ArrayList();
		
		String sqlHead = "select \n" +
						 "		 head.*,state.state_name \n" +
						 "from   res_headend_tab head,dic_state_tab state \n" +
						 "where \n" +
						 "       head.state=state.state \n" +
						 "order by head.code";
		String sqlState = "select * from dic_state_tab ";
		
		try {
			GDSet gdState = DbComponent.Query(sqlState.toString());
			GDSet gdHead = DbComponent.Query(sqlHead.toString());
			
			
			stateList = TableInfoCache.gdSetToPojoList(gdState, DicStateBean.class.getName());
			headList = TableInfoCache.gdSetToPojoList(gdHead, ResHeadendBean.class.getName());
			headendList = headList;
		} catch (Exception e) {
			LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
		}
		//Collections.sort(headList,new HeadendSort(true));
		list.add(stateList);
		list.add(headList);
		return list;
	}
	
	/**
	 * 根据code查询ResHeadendBean
	 * @param headcode
	 * @param type NOAB：从不分AB中查询
	 * @return
	 */
	public static ResHeadendBean getHeadendByCode(String headcode,String type){
		if(type.equals("NOAB")){
			for(int x=0; x<headendListDistinct.size(); x++){
				ResHeadendBean hbean = (ResHeadendBean)headendListDistinct.get(x);
				if(hbean.getCode_noab().equalsIgnoreCase(headcode)){
					return hbean;
				}
			}
		} else{
			for(int x=0; x<headendList.size(); x++){
				ResHeadendBean hbean = (ResHeadendBean)headendList.get(x);
				if(hbean.getCode().equalsIgnoreCase(headcode)){
					return hbean;
				}
			}
		}
		return new ResHeadendBean();
	}
	/**
	 * 获取前端列表,不传headCode查询全部
	 * <p>class/function:com.viewscenes.web.common
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-17
	 * @param:
	 * @return:
	 */
	public static Object getHeadendList(ASObject obj){
		ArrayList list = new ArrayList();
		String headCode = (String)obj.get("headCode");
		
		list = queryDataForCacheMap(headCode,SystemTableCache.RES_HEAD_END_TAB);
		//Collections.sort(list,new SortCommon());
		return list;
	}
	/**
	 * 获取前端列表,不传headCode查询全部
	 * <p>class/function:com.viewscenes.web.common
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-17
	 * @param:
	 * @return:
	 */
	public static String getHeadendnameByCode(String headcode){
	
		
		ArrayList list = queryDataForCacheMap(headcode,SystemTableCache.RES_HEAD_END_TAB);
		ResHeadendBean head=(ResHeadendBean) list.get(0);
		return head.getShortname();
	}
	
	/**
	 * 获取前端列表,返回的站点名称不带AB
	 * <p>class/function:com.viewscenes.web.common
	 * <p>explain:
	 * <p>author-date:刘斌
	 * @param:
	 * @return:
	 */
	public static String getHeadendnameNOABByCode(String headcode){
	
		ArrayList list = queryDataForCacheMap(headcode,SystemTableCache.RES_HEAD_END_TAB);
		ResHeadendBean head=(ResHeadendBean) list.get(0);
		if(headcode.endsWith("A")||headcode.endsWith("B")||headcode.endsWith("C")||headcode.endsWith("D")||headcode.endsWith("E")||headcode.endsWith("F")||headcode.endsWith("G"))
			{
			return head.getShortname().substring(0, head.getShortname().length()-1);
			}
		else
		   return head.getShortname();
	}
	
	public static ResHeadendBean getHeadendBeanByCode(String headcode){
		ArrayList list = queryDataForCacheMap(headcode,SystemTableCache.RES_HEAD_END_TAB);
		ResHeadendBean head=(ResHeadendBean) list.get(0);
		return head;
	}


	public static ResHeadendBean getHeadendListById(String head_id){
		ArrayList<ResHeadendBean> list = queryDataForCacheMap("",SystemTableCache.RES_HEAD_END_TAB);
		for(int i=0;i<list.size();i++){
			ResHeadendBean headendBean = list.get(i);
			if(headendBean.getHead_id().equals(head_id)){
				return headendBean;
			}
			//return headendBean.getHead_id().equals(head_id)? headendBean:null;
		}
		return null;
	}
	
	/**
	 * 获取前端列表,按HEAD_ID查询
	 * <p>class/function:com.viewscenes.web.common
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-17
	 * @param:
	 * @return:
	 */
	public static Object getHeadendListById(ASObject obj){
		String head_id = (String)obj.get("head_id");
		return getHeadendListById(head_id);
	}
	
	/**
	 * 获取城市列表,不传city_id查询全部
	 * <p>class/function:com.viewscenes.web.common
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-17
	 * @param:
	 * @return:
	 */
	public Object getCityList(ASObject obj){
		ArrayList list = new ArrayList();
		String city_id = (String)obj.get("city_id");
		
		list = queryDataForCacheMap(city_id,SystemTableCache.RES_CITY_TAB);
		Collections.sort(list,new SortCommon());
		return list;
	}
	/**
	 * 获得国家列表
	 * @return
	 *  @author 王福祥
	 * @date 2012/09/07 
	 */
	public Object getCountryList(){
		ArrayList list = new ArrayList();
		String sql="select distinct(t.contry) as country from res_city_tab t order by contry";
		try {
			GDSet gd = DbComponent.Query(sql);
			if(gd.getRowCount()>0){
				for(int i=0;i<gd.getRowCount();i++){
					
					list.add(gd.getString(i, "country"));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
		}
		Collections.sort(list,new SortCommon());
		return list;
	}
	/**
	 * 获取服务区列表,不传id查询全部
	 * <p>class/function:com.viewscenes.web.common
	 * <p>explain:
	 * <p>author-date:张文2012-10-31
	 * @param:
	 * @return:
	 */
	public Object getServiceList(ASObject obj){
		ArrayList list = new ArrayList();
		String id = (String)obj.get("id");
		
		list = queryDataForCacheMap(id,SystemTableCache.DIC_SERVICESAREA_TAB);
		Collections.sort(list,new SortCommon());
		return list;
	}
	
	/**
	 * 获取国家列表的方法,不传contry查询全部
	 * @detail  
	 * @method  
	 * @param obj
	 * @return 
	 * @return  Object  
	 * @author  zhaoyahui
	 * @version 2012-10-26 下午04:57:24
	 */
	public Object getCountryList(ASObject obj){
		ArrayList list = new ArrayList();
		String contry = (String)obj.get("contry");
		String continents_id=(String)obj.get("continents_id");
		String subSql = " where  1=1 ";
		if(contry !=null && contry.length()>0){
			subSql += " and t.contry = '"+contry+"'";
		}
		if(continents_id !=null && continents_id.length()>0){
			
			subSql += " and t.continents_id = "+continents_id;
		}
		String sql = "select distinct(t.contry),t.* from res_city_tab t"+subSql+" order by t.contry";
		try {
			GDSet gd = DbComponent.Query(sql);
			list = TableInfoCache.gdSetToPojoList(gd, ResCityBean.class.getName());
		} catch (DbException e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
		}
		Collections.sort(list,new SortCommon());
		return list;
	}
	
	/**
	 * 获取大洲列表
	 * <p>class/function:com.viewscenes.web.common
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-17
	 * @param:
	 * @return:
	 */
	public Object getStateList(ASObject obj){
		ArrayList list = new ArrayList();
		String state_id = (String)obj.get("state_id");
		
		list = queryDataForCacheMap(state_id,SystemTableCache.DIC_STATE_TAB);
		Collections.sort(list,new SortCommon());
		return list;
	}
	
	
	/**
	 * 发射台名称列表( 只包含名称)
	 * <p>class/function:com.viewscenes.web.common
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-19
	 * @param:
	 * @return:
	 */
	public Object getStationList(){
		ArrayList list = new ArrayList();
		list = queryDataForCacheMap("",SystemTableCache.RES_TRANSMIT_STATION_TAB);
		Collections.sort(list,new SortCommon());
		return list;
	}
	
	
	/**
	 * 获取运行图类型
	 * <p>class/function:com.viewscenes.web.common
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-19
	 * @param:
	 * @return:
	 */
	public Object getRunplanType(){
		ArrayList list = new ArrayList();
		RunplanTypeBean bean = new RunplanTypeBean();
		bean.setRunplanTypeId("1");
		bean.setRunplanType("国际台运行图");
		
		RunplanTypeBean bean1 = new RunplanTypeBean();
		bean1.setRunplanTypeId("2");
		bean1.setRunplanType("国际台海外落地运行图");
		
		RunplanTypeBean bean2 = new RunplanTypeBean();
		bean2.setRunplanTypeId("3");
		bean2.setRunplanType("本地运行图");
		
		list.add(bean);
		list.add(bean1);
		list.add(bean2);
		
		return list;
	}
	
	/**
	 * 语言列表
	 * <p>class/function:com.viewscenes.web.common
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-19
	 * @param:
	 * @return:
	 */
	public Object getLanguageList(ASObject obj){
		ArrayList list = new ArrayList();
		String language_id = (String)obj.get("language_id");
		list = queryDataForCacheMap(language_id,SystemTableCache.ZDIC_LANGUAGE_TAB);
		Collections.sort(list,new SortCommon());
		return list;
	}
	
	public Object getLanguageList( ){
		ArrayList list = new ArrayList();
	
		list = queryDataForCacheMap("",SystemTableCache.ZDIC_LANGUAGE_TAB);
		Collections.sort(list,new SortCommon());
		return list;
	}
	
	/**
	 * 获取指定表名的缓存,key为空,返回全部值
	 * <p>class/function:com.viewscenes.web.common
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-17
	 * @param:
	 * @return:
	 */
	public static  ArrayList queryDataForCacheMap(String key,String tableName){
		ArrayList list = new ArrayList();
		Map<String,Object> map = SystemTableCache.getDataMap(tableName);
		
		if (key != null && !key.equals("")){
			Object tBean = (Object)map.get(key);
			
			list.add(tBean);
			
		}else{
			Set<String> set = map.keySet();
			
			TreeMap treemap = new TreeMap(map);
			 Set mappings = treemap.entrySet(); 
		      for (Iterator i = mappings.iterator(); i.hasNext();) {
		         Map.Entry me = (Map.Entry)i.next(); 
		         Object ov = me.getValue();
		         list.add(ov); 
		      }
		}
		return list;
	}
	
	/**
	 * 根据前端code返回此站点对应的 运行图。
	 * <p>class/function:com.viewscenes.web.common
	 * <p>explain:
	 * <p>author-date:刘斌  2012-7-17
	 * @param:
	 * @return:
	 */
	public Object getRunplanListByHeadCode(ASObject obj){
		ArrayList<RunplanBean> list = new ArrayList<RunplanBean>();
		ArrayList stationCode = (ArrayList)obj.get("stationCode");
		String freq = (String)obj.get("freq");
		String runplanType = (String) obj.get("runplanType");
		String season="";//国际台运行图的季节代号
		String seasonType="";//海外落地运行图的季节类型  夏季；冬季
		if(runplanType.equalsIgnoreCase("1")){
			if(obj.get("season")!=null){
				season=(String) obj.get("season");
			}
		}else{
			if(obj.get("seasonType")!=null){
				seasonType=(String) obj.get("seasonType");
			}
		}
		String monarea = " and (( ";
		String xg_mon_area = " or ( ";
		String subsql = "";
		if(freq!=null && !freq.trim().equals("")){
			subsql += " and t.freq='"+freq+"'";
		}
		if(season!=null&&!season.equalsIgnoreCase("")){
			subsql += " and t.season_id='"+season+"' ";
		}
		if(seasonType!=null&&!seasonType.equalsIgnoreCase("")){
			subsql += " and t.summer='"+seasonType+"' ";
		}
		for(int i=0;i<stationCode.size();i++){
			String code=stationCode.get(i).toString();
			if(code.endsWith("A")||code.endsWith("B")||code.endsWith("C")||code.endsWith("D")||code.endsWith("E")||code.endsWith("F")||code.endsWith("G"))
			{
				code=code.substring(0, code.length()-1);
			}
			monarea += " t.mon_area like '%"+code+"%' or ";
			xg_mon_area += " t.xg_mon_area like '%"+code+"%' or ";
		}
		if(monarea.equals(" and ( ")){
			monarea = "";
			xg_mon_area = "";
		} else{
			monarea = monarea.substring(0, monarea.lastIndexOf("or"))+")";
			xg_mon_area = xg_mon_area.substring(0, xg_mon_area.lastIndexOf("or"))+"))";
		}
		String sql="select t.runplan_id,t.runplan_type_id,t.station_id,t.transmiter_no,t.freq,t.valid_start_time," +
				"t.valid_end_time,t.direction,t.power,t.service_area,t.antennatype,t.rest_datetime,t.rest_time,t.sentcity_id,city.city sendcity ,t.start_time," +
				"t.end_time,t.satellite_channel,t.store_datetime,t.program_type_id,t.language_id," +
				"t.weekday,t.input_person,t.revise_person,t.remark,t.program_id,t.mon_area,t.xg_mon_area," +
				"t.band,t.program_type,t.redisseminators,t.local_time,t.summer,t.summer_starttime," +
				"t.summer_endtime,t.season_id,t.antenna,t.station_name,t.ciraf," +
				"lang.language_name, runtype.type,decode(t.runplan_type_id,1,'国际台运行图',2,'海外落地',3,'外国台运行图') runplanType" +
				" from zres_runplan_tab t,res_city_tab city,  " +
				" zdic_language_tab lang, dic_runplan_type_tab runtype where t.is_delete=0 and t.runplan_type_id='"+runplanType+"' " + 
				" and t.language_id=lang.language_id" + monarea + xg_mon_area +
				" and t.runplan_type_id=runtype.runplan_type_id" +
				" and t.sentcity_id=city.id(+) "+
				subsql +
				" and t.valid_end_time>sysdate order by t.runplan_type_id asc  ";
		   GDSet gdSet = null;
	         try {
				gdSet = DbComponent.Query(sql);
				 for (int i = 0; i < gdSet.getRowCount(); i++) {
				      RunplanBean runplanBean=new RunplanBean();
				      runplanBean.setRunplan_id(gdSet.getString(i, "runplan_id"));
				      runplanBean.setRunplan_type_id(gdSet.getString(i, "runplan_type_id"));
				      runplanBean.setStation_id(gdSet.getString(i, "station_id"));
				      runplanBean.setTransmiter_no(gdSet.getString(i,"transmiter_no"));
				      runplanBean.setFreq(gdSet.getString(i, "freq"));
				      runplanBean.setValid_start_time(gdSet.getString(i, "valid_start_time"));
				      runplanBean.setValid_end_time(gdSet.getString(i, "valid_end_time"));
				      runplanBean.setDirection(gdSet.getString(i, "direction"));
				      runplanBean.setPower(gdSet.getString(i, "power"));
				      runplanBean.setService_area(gdSet.getString(i, "service_area"));
				      runplanBean.setAntennatype(gdSet.getString(i, "antennatype"));
				      runplanBean.setRest_datetime(gdSet.getString(i, "rest_datetime"));
				      runplanBean.setRest_time(gdSet.getString(i, "rest_time"));
				      runplanBean.setSentcity_id(gdSet.getString(i, "sentcity_id"));
				      runplanBean.setStart_time(gdSet.getString(i, "start_time"));
				      runplanBean.setEnd_time(gdSet.getString(i, "end_time"));
				      runplanBean.setSatellite_channel(gdSet.getString(i, "satellite_channel"));
				      runplanBean.setStore_datetime(gdSet.getString(i, "store_datetime"));
				      runplanBean.setProgram_type_id(gdSet.getString(i, "program_type_id"));
				      runplanBean.setLanguage_id(gdSet.getString(i, "language_id"));
				      runplanBean.setWeekday(gdSet.getString(i, "weekday"));
				      runplanBean.setInput_person(gdSet.getString(i, "input_person"));
				      runplanBean.setRevise_person(gdSet.getString(i, "revise_person"));
				      runplanBean.setRemark(gdSet.getString(i, "remark"));
				      runplanBean.setProgram_id(gdSet.getString(i, "program_id"));
				      runplanBean.setMon_area(gdSet.getString(i, "mon_area"));
				      runplanBean.setXg_mon_area(gdSet.getString(i, "xg_mon_area"));
				      runplanBean.setBand(gdSet.getString(i, "band"));
				      runplanBean.setProgram_type(gdSet.getString(i, "program_type"));
				      runplanBean.setRedisseminators(gdSet.getString(i, "redisseminators"));
				      runplanBean.setLocal_time(gdSet.getString(i, "local_time"));
				      runplanBean.setSummer(gdSet.getString(i, "summer"));
				      runplanBean.setSummer_starttime(gdSet.getString(i, "summer_starttime"));
				      runplanBean.setSummer_endtime(gdSet.getString(i, "summer_endtime"));
				      runplanBean.setSeason_id(gdSet.getString(i, "season_id"));
				      runplanBean.setAntenna(gdSet.getString(i, "antenna"));
				      runplanBean.setStation_name(gdSet.getString(i, "station_name"));
				      runplanBean.setCiraf(gdSet.getString(i, "ciraf"));
//				      runplanBean.setShortname(gdSet.getString(i, "shortname"));
				    runplanBean.setSendcity(gdSet.getString(i, "sendcity"));
				      runplanBean.setLanguage_name(gdSet.getString(i, "language_name"));
//				      runplanBean.setProgram_name(gdSet.getString(i, "program_name"));
				      runplanBean.setType(gdSet.getString(i, "type"));
				      runplanBean.setRunplanType(gdSet.getString(i, "runplanType"));
				      
			          list.add(runplanBean);

			         }
			} catch (DbException e) {
				// TODO Auto-generated catch block
				LogTool.fatal(e);
				return new EXEException("", "[站点:" + stationCode
						+ "]查询运行图信息实失败|" + e.getMessage(),
						null);
			} catch (GDSetException e) {
				// TODO Auto-generated catch block
				LogTool.fatal(e);
				return new EXEException("", "[站点:" + stationCode
						+ "]查询运行图信息实失败|" + e.getMessage(),
						null);
			}
			
		
		return list;
		
	}
	
	/**
     * 获取接收机列表
     * @detail  
     * @method  
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-8-10 上午10:04:15
     */
    public Object getEquList(String headendCode){
    	Vector equlist = new Vector();
        try {
            equlist = ReceiverListGetUtil.getReceiverList(headendCode, true);
        } catch (Exception e) {
            LogTool.fatal("查询接收机错误:", e);
            return new EXEException("","查询接收机错误","");
        }

		ArrayList equList = new ArrayList();
        for (int j = 0; j < equlist.size(); j = j + 2) {
			ASObject equi = new ASObject();
			equi.put("label",  (String) equlist.get(j + 1));
			equi.put("data",  (String) equlist.get(j));
			equList.add(equi);
        }
    	
    	return equList;
    }
    
    /**
     * 获取接收机列表 简化查询 原来的太复杂了
     * @detail  
     * @method  
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-8-10 上午10:04:15
     */
    public Object getEquListNew(String headendCode){
    	Vector equlist = new Vector();
   	 Vector receiverVector = new Vector();
        try {
        	    receiverVector.add("");
        	    receiverVector.add("自动选择");
            String sql = "select d.receiver_name_list,d.receiver_code_list from DIC_HEADEND_MANUFACTURER_TAB d,res_headend_tab h " +
            			" where d.manufacturer_name=h.manufacturer and d.version=h.version and d.headend_type=h.type_id " +
            			" AND  h.code='"+headendCode+"'";
            GDSet gd=DbComponent.Query(sql);
            if (gd.getRowCount() <= 0) {
              throw new Exception("提取不到"+headendCode+"接收机配置！");
            }
            String nameIds = gd.getString(0, "receiver_name_list");
            String codeIds = gd.getString(0, "receiver_code_list");
            Vector recVec = new Vector();
            String[] codeArray = codeIds.split(",");
            String[] nameArray = nameIds.split(",");
            boolean isRadio = true;
            for (int i = 0; i < codeArray.length; i++) {
//	              //如果取接收机,不能大于要求值,不满足条件返回.
//	              if (isRadio && new Integer(codeArray[i]).intValue() >= 200) {
//	                continue;
//	              }
//	              //如果取摄像头，不能小于要求值,不满足条件返回。
//	              else if (!isRadio && new Integer(codeArray[i]).intValue() < 200) {
//	                continue;
//	              }
	              String code = ReceiverListGetUtil.getCodeByCodeId(codeArray[i]);
	              receiverVector.add(code);
	              receiverVector.add(code + ":" + ReceiverListGetUtil.getCodeNameByNameId(nameArray[i]));
            }
        } catch (Exception e) {
            LogTool.fatal( e);
            return new EXEException("",e.getMessage(),"");
        }

		ArrayList equList = new ArrayList();
        for (int j = 0; j < receiverVector.size(); j = j + 2) {
			ASObject equi = new ASObject();
			equi.put("label",  (String) receiverVector.get(j + 1));
			equi.put("data",  (String) receiverVector.get(j));
			equList.add(equi);
        }
    	
    	return equList;
    }
    /**
     * 查询用户可以使用的功能菜单
     * @detail  
     * @method  
     * @param obj
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-8-28 下午03:54:26
     */
    public Object getUserMenu(ASObject obj){
		
		String roleId = (String)obj.get("roleId");
		String broadcast_type = (String)obj.get("broadcast_type");
		String userName = (String)obj.get("userName");
		if(broadcast_type == null)
			broadcast_type = "0";
		try {
			GDSet gd =null;
			if(userName.equalsIgnoreCase("admin")){
				gd = PubUserManagerServiceDao.getFuncSetByAdmin(broadcast_type);
			}  else if(roleId.equals("323")){
				gd=PubUserManagerServiceDao.getFuncSetSpical();
			}else{
				gd=PubUserManagerServiceDao.getFuncSetByRoleId(roleId,broadcast_type);
			}
			
			ArrayList rootList = new ArrayList();
			
			Map nodeMap = new HashMap();
			for(int i=0; i<gd.getRowCount(); i++){
				String funcId = gd.getString(i, "operation_id");
				String name = gd.getString(i, "operation_name");
				String levels = "1";
				String parentFuncId = gd.getString(i, "parent_operation_id");
				if(parentFuncId.equals("0")){
					levels = "0";
				}
				String showFlag = gd.getString(i, "show_flag");
				String showName = gd.getString(i, "show_name");
				String classPath = gd.getString(i, "class_path");
				String iconSource = gd.getString(i, "icon_source");
				String fastIconSource = gd.getString(i, "fast_icon_source");
				String funcType = gd.getString(i, "func_type");
				
				if(!funcType.equals("1")){ //不是菜单功能
					continue;
				}
				
				
				
				Element node = new Element("node");
				node.addAttribute(new Attribute("id",funcId));
				
				if(funcId.equals("400601")){  //  默认选中定制模块
					node.addAttribute(new Attribute("select","true"));
				}
				
				node.addAttribute(new Attribute("label",name));
				
				node.addAttribute(new Attribute("showLabel",showName));
				
				if(classPath != null && !classPath.equals("")){
					node.addAttribute(new Attribute("classPath",classPath));
				}
				node.addAttribute(new Attribute("source",iconSource));
				
				node.addAttribute(new Attribute("fastSource",fastIconSource));
				
				nodeMap.put(funcId, node);
				
				if(nodeMap.containsKey(parentFuncId)){
					Element parentNode = (Element)nodeMap.get(parentFuncId);
					
					List childList = parentNode.getChildren();
					
					childList.add(node);
				}
				if(levels.equals("0") && !funcId.equals("0")){ //第一级且不是地域权限
					rootList.add(node);
				}
			}
			
			Element root1 = new Element("menu");
		    root1.addAttribute("id", "0");
		    Document doc = new Document(root1);
			
			Element root = doc.getRootElement();
			
			root.setChildren(rootList);
			
			XMLOutputter out = new XMLOutputter("  ", true, "GB2312");

			StringWriter sw = new StringWriter();
			
			try {

				out.output(doc, sw);

			} catch (IOException ex2) {

				ex2.printStackTrace();
			}
			LogTool.fatal(sw.toString());
			return sw.toString();
			
			
		} catch (Exception e) {
			LogTool.fatal(e);
			
			return new EXEException("","读取用户权限异常"+e.getMessage(),"");
		}
		
	}
    
    /**
     * 提供给运行状态图的站点信息
     * @param obj
     * @return
     */
    public Object getHeadend(ASObject obj){
    	ArrayList list = new ArrayList();//存放全部站点
    	ArrayList list0 = new ArrayList();//存放亚洲站点
    	ArrayList list1 = new ArrayList();//存放欧洲站点
    	ArrayList list2 = new ArrayList();//存放非洲站点
    	ArrayList list3 = new ArrayList();//存放北美洲站点
    	ArrayList list4 = new ArrayList();//存放南美洲站点
    	ArrayList list5 = new ArrayList();//存放大洋洲站点
    	ArrayList list6 = new ArrayList();//存放南极洲站点
    	String headname=(String) obj.get("name");
    	String headcode=(String) obj.get("code");
    	String sql="select * from res_headend_tab t where t.is_delete=0 ";
    	if(headname!=null&&!headname.equalsIgnoreCase("all")){
    		sql+=" and t.shortname like '%"+headname+"%'";
    	}
    	if(headcode!=null&&!headcode.equalsIgnoreCase("all")){
    		sql+=" and upper(t.code) like upper('%"+headcode+"%')";
    	}
    	sql+=" order by state,type_id,code,shortname";  
    	try {
			GDSet gd=DbComponent.Query(sql);
			if(gd.getRowCount()>0){
				for(int i=0;i<gd.getRowCount();i++){
					ResHeadendBean bean = new ResHeadendBean();
					bean.setType_id(gd.getString(i, "type_id"));
					bean.setHead_id(gd.getString(i, "head_id"));
					bean.setCode(gd.getString(i, "code"));
					bean.setShortname(gd.getString(i, "shortname"));
					bean.setIs_online(gd.getString(i, "is_online"));
					bean.setIp(gd.getString(i, "ip"));
					bean.setAddress(gd.getString(i, "address"));
					bean.setSite_status(gd.getString(i, "site_status"));
					bean.setState(gd.getString(i, "state"));
					if(gd.getString(i, "state").equalsIgnoreCase("0")){
						list0.add(bean);
					}
					if(gd.getString(i, "state").equalsIgnoreCase("1")){
						list1.add(bean);
					}
					if(gd.getString(i, "state").equalsIgnoreCase("2")){
						list2.add(bean);
					}
					if(gd.getString(i, "state").equalsIgnoreCase("3")){
						list3.add(bean);
					}
					if(gd.getString(i, "state").equalsIgnoreCase("4")){
						list4.add(bean);
					}
					if(gd.getString(i, "state").equalsIgnoreCase("5")){
						list5.add(bean);
					}
					if(gd.getString(i, "state").equalsIgnoreCase("6")){
						list6.add(bean);
					}
					 
				}
			}
			list.add(list0);
			list.add(list1);
			list.add(list2);
			list.add(list3);
			list.add(list4);
			list.add(list5);
			list.add(list6);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
		}
    	
    	return list;
    }
    /**
     * 提供给运行状态图的站点信息
     * @param obj
     * @return
     */
    public Object getHeadendByState(ASObject obj){
    	ArrayList list = new ArrayList();
    	String headname=(String) obj.get("name");
    	String headcode=(String) obj.get("code");
    	String state=(String)obj.get("stateNow");
    	String sql="select * from res_headend_tab t where t.is_delete=0 ";
    	if(headname!=null&&!headname.equalsIgnoreCase("all")){
    		sql+=" and t.shortname like '%"+headname+"%'";
    	}
    	if(headcode!=null&&!headcode.equalsIgnoreCase("all")){
    		sql+=" and upper(t.code) like upper('%"+headcode+"%')";
    	}
    	if(state!=null&&!state.equalsIgnoreCase("all")){
    		sql+=" and t.state = "+state+"";
    	}
    	sql+=" order by state,type_id,code,shortname";  
    	try {
			GDSet gd=DbComponent.Query(sql);
			if(gd.getRowCount()>0){
				for(int i=0;i<gd.getRowCount();i++){
					ResHeadendBean bean = new ResHeadendBean();
					bean.setType_id(gd.getString(i, "type_id"));
					bean.setHead_id(gd.getString(i, "head_id"));
					bean.setCode(gd.getString(i, "code"));
					bean.setShortname(gd.getString(i, "shortname"));
					bean.setIs_online(gd.getString(i, "is_online"));
					bean.setIp(gd.getString(i, "ip"));
					bean.setAddress(gd.getString(i, "address"));
					bean.setSite_status(gd.getString(i, "site_status"));
					bean.setState(gd.getString(i, "state"));				
					list.add(bean);														 
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
		}
    	
    	return list;
    }
    
    /**
     * 查询站点CODE和名称 ，AB对应的站点用一个表示
     * @detail  
     * @method  
     * @param bean
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-9-27 下午03:29:01
     */
    public Object getHeadendListDistinct(ResHeadendBean bean){
    	String headtype = bean.getType_id();
		
    	StringBuffer sql = new StringBuffer("select distinct x.* from (");
    	sql.append(" select   (decode(t.type_id||t.version, '102V8', substr(t.shortname, 0, length(t.shortname) - 1), t.shortname)) as shortname_noab  ,");
		sql.append(" 		          t.state,t.type_id,t.version,");
		sql.append(" 		          state.state_name,");
		sql.append(" 		          t.x,t.y,");
		sql.append(" 		          t.country,");
		sql.append(" 		          t.longitude,t.latitude,");
		sql.append(" 		          decode(upper(t.type_id||t.version), upper('102V8'), substr(t.code, 0, length(t.code) - 1), t.code) as code_noab");
		sql.append(" 		  from res_headend_tab t, dic_state_tab state");
		sql.append(" 		 where t.is_delete = 0");
		sql.append(" 		   and t.state = state.state");
		sql.append(" 		 order by  t.code,t.shortname ");
				sql.append(" 		 ) x order by  code_noab");

//		StringBuffer sql = new StringBuffer("select distinct (decode(t.type_id,102,substr(t.shortname, 0, length(t.shortname) - 1),t.shortname)) as shortname_noab,t.*,");
//					sql.append(" state.state_name ,");
//		             sql.append(" decode(t.type_id,102,substr(t.code, 0, length(t.code) - 1),t.code) as code_noab from res_headend_tab t,dic_state_tab state  where t.is_delete = 0  and  t.state=state.state");
		if(headtype != null && !headtype.equalsIgnoreCase("") && !headtype.equalsIgnoreCase("all")){
			sql.append(" and type_id='"+headtype+"'");
		}
		try {
			System.out.println(sql);
			GDSet gd = DbComponent.Query(sql.toString());
			
			
			headendListDistinct = TableInfoCache.gdSetToPojoList(gd, ResHeadendBean.class.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
		}
		//Collections.sort(headendListDistinct,new HeadendSort(false));
		return headendListDistinct;
	}
    /**
     * 王福祥 
     * 根据站点code，获取站点指定的质量或者效果录音任务规则
     * @date 2013/01/04
     * @param asobj
     * @return
     */
    public Object getRecordRules(ASObject asobj){
    	//String code = (String) asobj.get("code");
    	//String type = (String) asobj.get("type");

		ASObject objAll = new ASObject();
    	ArrayList list= new ArrayList();
    	try{
    		//if(type.equalsIgnoreCase("质量")){
        		String sql = "select * from res_headend_monitor_rule_tab t order by t.head_code  ";
        		GDSet gd = DbComponent.Query(sql);
        		if(gd.getRowCount()>0){

        			for(int i=0;i<gd.getRowCount();i++){
        				ASObject obj = new ASObject();
        				obj.put("head_code",  gd.getString(i, "head_code"));
        				obj.put("quality_sleep_time", gd.getString(i, "quality_sleep_time")) ;
        				obj.put("quality_record_length", gd.getString(i, "quality_record_length"));
        				obj.put("quality_bps",  gd.getString(i, "quality_bps"));
        				obj.put("effect_sleep_time", gd.getString(i, "effect_sleep_time")) ;
        				obj.put("effect_record_length", gd.getString(i, "effect_record_length"));
        				obj.put("effect_bps",  gd.getString(i, "effect_bps"));
        				objAll.put(gd.getString(i, "head_code"), obj);
//        				list.add(obj);
        			}
        		}
        	//}
//    		if(type.equalsIgnoreCase("效果")){
//    			String sql="select t.head_code,t.effect_sleep_time,t.effect_record_length,t.effect_bps from res_headend_monitor_rule_tab t where t.head_code like '%"+code+"%'";
//    			GDSet gd = DbComponent.Query(sql);
//    			if(gd.getRowCount()>0){
//    				for(int i=0;i<gd.getRowCount();i++){
//    					obj.put("sleep_time", gd.getString(i, "effect_sleep_time")) ;
//        				obj.put("record_length", gd.getString(i, "effect_record_length"));
//        				obj.put("bps",  gd.getString(i, "effect_bps"));
//        				obj.put("head_code",  gd.getString(i, "head_code"));
//    				}
//    			}
//    		}
    	}catch(Exception e){
    		LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
    	}
    	return objAll;
    }
    
    /**
     * 根据语言名称查询id
     * @param name
     * @return
     */
    public static String getLanguageIDByName(String name){
    	String language_id="";
    	String sql="select language_id from zdic_language_tab where language_name='"+name+"'";
    	try {
			GDSet gd = DbComponent.Query(sql);
			if(gd.getRowCount()>0){
				language_id=gd.getString(0, "language_id");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
		}
    	return language_id;
    }
    /**
     * 根据语言名称查询id
     * @param name
     * @return
     */
    public static String getStationIDByName(String name){
    	String station_id="";
    	String sql="select station_id from res_transmit_station_tab where name like '%"+name+"%'";
    	try {
			GDSet gd = DbComponent.Query(sql);
			if(gd.getRowCount()>0){
				station_id=gd.getString(0, "station_id");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
		}
    	return station_id;
    }
    /**
     * 根据国家名返回相应信息（默认返回空）
     * @param obj
     * @return
     */
    public ArrayList<Object> getContinentsIdByCountry(ASObject obj){
        String country=(String)obj.get("country");
        ArrayList<Object> result=new ArrayList<Object>();
        if(country!=null&&country.length()!=0){
            String sql="select distinct(t.contry) ,continents_id ,capital ,default_language from res_city_tab t where contry ='"+country+"'";
            try {
                GDSet gd=DbComponent.Query(sql);
                if(gd.getRowCount()>0){
                    result.add(Integer.parseInt(gd.getString(0, 1)));
                    result.add(gd.getString(0, 2));
                    result.add(gd.getString(0, 3));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return result;
    }
    /**
     * 根据语言名称查询id
     * @param name
     * @return
     */
    public static String getCityIDByName(String name){
    	String id="";
    	String sql="select id from res_city_tab where city like '%"+name+"%'";
    	try {
			GDSet gd = DbComponent.Query(sql);
			if(gd.getRowCount()>0){
				id=gd.getString(0, "id");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
		}
    	return id;
    }
    
    /**
	 * 获取站点信息这里将带AB的站点合并为一个站点
	 * @return
	 */
	public Object getHeadList(ASObject asobj){
		ArrayList list = new ArrayList();
		String headtype = (String) asobj.get("type_id");
		String state = (String) asobj.get("state");
		StringBuffer sql = new StringBuffer("select distinct (decode(upper(t.type_id||t.version),upper('102V8'),substr(t.shortname, 0, length(t.shortname) - 1),t.shortname)) as shortname,");
		             sql.append(" decode(upper(t.type_id||t.version),upper('102V8'),substr(t.code, 0, length(t.code) - 1),t.code) as code from res_headend_tab t where t.is_delete = 0  ");
		if(!headtype.equalsIgnoreCase("all")){
			sql.append(" and type_id='"+headtype+"'");
		}
		if(!state.equalsIgnoreCase("100")&&!state.equalsIgnoreCase("all")){
			sql.append(" and state="+state+"");
		}
		sql.append("   order by code,shortname");
		try {
			GDSet gd = DbComponent.Query(sql.toString());
			if(gd.getRowCount()>0){
				for(int i=0;i<gd.getRowCount();i++){
					ASObject obj = new ASObject();
					obj.put("shortname", gd.getString(i, "shortname"));
					obj.put("value", gd.getString(i, "code"));
					obj.put("label", gd.getString(i, "shortname")+"["+gd.getString(i, "code")+"]");
					obj.put("selected", false);
					list.add(obj);
				}
			}
		} catch (Exception e) {
			LogTool.fatal(e);
		}
		Collections.sort(list,new SortCommon());
		return list;
	}
	/**
	 * <p>
	 * <p>explain:获取大洲站点接收机树
	 * <p>author-date:
	 * @param:
	 * @return:
	 * @throws Exception 
	 */
	public static ArrayList<Object> getHeadendTree(ASObject obj) throws Exception{
		String result = "";
		String typeString=(String)obj.get("type");
		ArrayList<Object> returnlist =  new ArrayList<Object>();
		String sqlString="";
		if(typeString.equals("all"))
		{
		 sqlString="select t.*, tt.state_name,ttt.receiver_code_list" +
				"  from res_headend_tab t, dic_state_tab tt, dic_headend_manufacturer_tab ttt" +
				" where t.state = tt.state  and t.is_delete = 0  and t.manufacturer=ttt.manufacturer_name order by t.state asc,t.code,shortname asc ";
		}else 
		{
			 sqlString="select t.*, tt.state_name,ttt.receiver_code_list" +
				"  from res_headend_tab t, dic_state_tab tt, dic_headend_manufacturer_tab ttt" +
				" where t.state = tt.state  and t.is_delete = 0 and t.type_id=" +typeString+
				"  and t.manufacturer=ttt.manufacturer_name order by t.state asc,t.code,shortname asc ";
		
		}
		GDSet headSet = DbComponent.Query(sqlString);
		
		ArrayList list = new ArrayList();

		Document doc = StringTool.getXmlMsg();

		Element rootNode = doc.getRootElement();
		
		Element root = new Element("tree");
		root.addAttribute(new Attribute("text",""));
		
		Element title = new Element("node");
		title.addAttribute(new Attribute("text","功能列表"));
		
		ArrayList rootList = new ArrayList();
		
		HashMap nodeMap = new HashMap();
		Element node0 = new Element("node");
		node0.addAttribute(new Attribute("text","亚洲"));
		node0.addAttribute(new Attribute("id","0"));
		node0.addAttribute("checked","0");
		node0.addAttribute(new Attribute("data","0"));
		rootList.add(node0);
		
		Element node1 = new Element("node");
		node1.addAttribute(new Attribute("text","欧洲"));
		node1.addAttribute(new Attribute("id","1"));
		node1.addAttribute("checked","0");
		node1.addAttribute(new Attribute("data","0"));
		rootList.add(node1);
		Element node2 = new Element("node");
		node2.addAttribute(new Attribute("text","非洲"));
		node2.addAttribute(new Attribute("id","2"));
		node2.addAttribute("checked","0");
		node2.addAttribute(new Attribute("data","0"));
		rootList.add(node2);
		Element node3= new Element("node");
		node3.addAttribute(new Attribute("text","北美洲"));
		node3.addAttribute(new Attribute("id","3"));
		node3.addAttribute("checked","0");
		node3.addAttribute(new Attribute("data","0"));
		rootList.add(node3);
		Element node4 = new Element("node");
		node4.addAttribute(new Attribute("text","南美洲"));
		node4.addAttribute(new Attribute("id","4"));
		node4.addAttribute("checked","0");
		node4.addAttribute(new Attribute("data","0"));
		rootList.add(node4);
		Element node5 = new Element("node");
		node5.addAttribute(new Attribute("text","大洋洲"));
		node5.addAttribute(new Attribute("id","5"));
		node5.addAttribute("checked","0");
		node5.addAttribute(new Attribute("data","0"));
		rootList.add(node5);
		Element node6 = new Element("node");
		node6.addAttribute(new Attribute("text","南极洲"));
		node6.addAttribute(new Attribute("id","6"));
		node6.addAttribute("checked","0");
		node6.addAttribute(new Attribute("data","0"));
		rootList.add(node6);
		 nodeMap.put("0", node0);
		 nodeMap.put("1", node1);
		 nodeMap.put("2", node2);
		 nodeMap.put("3", node3);
		 nodeMap.put("4", node4);
		 nodeMap.put("5", node5);
		 nodeMap.put("6", node6);
		for(int i=0 ; i<headSet.getRowCount(); i++){
			Element node = new Element("node");
			
			node.addAttribute(new Attribute("text",headSet.getString(i, "shortname")));

			node.addAttribute(new Attribute("id",headSet.getString(i, "head_id")));

			 node.addAttribute(new Attribute("data","0"));
			 node.addAttribute("checked","0");
			 
			 nodeMap.put(headSet.getString(i, "head_id"), node);
			 
				 String parentFuncId = headSet.getString(i, "state");
				 Element parentNode = (Element)nodeMap.get(parentFuncId);
//				 if(parentNode != null){
//					 if(headSet.getString(i, "receiver_code_list").equals("1"))
//					 {
//						 List childList1 = node.getChildren();
//							Element r1 = new Element("node");
//							r1.addAttribute(new Attribute("text","R1"));
//							r1.addAttribute(new Attribute("id","R1"));
//							r1.addAttribute("checked","0");
//							r1.addAttribute(new Attribute("data","0"));
//							childList1.add(r1);
//					 }else 
//						 {
//							 List childList2 = node.getChildren();
//								Element r1 = new Element("node");
//								r1.addAttribute(new Attribute("text","R1"));
//								r1.addAttribute(new Attribute("id","R1"));
//								r1.addAttribute("checked","0");
//								r1.addAttribute(new Attribute("data","0"));
//	                           Element r2 = new Element("node");
//								r2.addAttribute(new Attribute("text","R2"));
//								r2.addAttribute(new Attribute("id","R2"));
//								r2.addAttribute("checked","0");
//								r2.addAttribute(new Attribute("data","0"));
//								childList2.add(r1); 
//								childList2.add(r2); 
//						 }
//					}
					 List childList3 = parentNode.getChildren();
					 childList3.add(node);
				
				 }
		
//		for(int i=0; i<rootList.size(); i++){
//			Element treeNode = (Element)rootList.get(i);
//			treeNode.addAttribute("checked","0");
//		}
		
		title.setChildren(rootList);
		ArrayList tList = new ArrayList();
		tList.add(title);
		root.setChildren(tList);
		list.add(root);
		rootNode.setChildren(list);
		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");
		StringWriter sw = new StringWriter();
		try {
			out.output(doc, sw);
		} catch (IOException ex2) {
			ex2.printStackTrace();
		}
		result = sw.toString();
//		}catch(Exception e){
//			e.printStackTrace();
//			return new EXEException("", "获取权限列表失败,"+e.getMessage(), "");
//		}
		
		returnlist.add(result);
System.out.println(result);
		return returnlist;
	}

    /**
     * ************************************************
    
    * @Title: checkSystemRunning
    
    * @Description: TODO(网络运行状态监测)
    
    * @param @param obj
    * @param @return    设定文件
    
    * @author  刘斌
    
    * @return Object    返回类型
    
    * @throws
    
    ************************************************
     */
    public Object checkSystemRunning(ASObject obj) {
    	String str="";
    	List list=XmlReader.getAttrValueList("NetCheck", "para", "label","ip", "key");
		for(int i=0;i<list.size();i++)
		{
			List list2= (List) list.get(i);
			String label=list2.get(0).toString();
			String ip= list2.get(1).toString();
		    String key= list2.get(2).toString();
		    if (SimplePing.ping(ip)) { // 联通
		  	 str+=label+",1"+"&&";
		  		
		  	}
		  	else {
		  	  str+=label+",0"+"&&";
		  	}
		}
		System.out.println(str);
    	   return str;
    }

    /**
     * 可对有AB的站点和没有AB的站点进行排序
     * hasAB:是否有AB站点
     * @author thinkpad
     *
     */
    static class HeadendSort extends SortCommon{
    	boolean hasAB = false;
    	public HeadendSort(boolean hasAB){
    		this.hasAB =  hasAB;
    	}
		@Override
		public int compare(Object o1, Object o2) {
			// TODO Auto-generated method stub
			
			ResHeadendBean s1 = (ResHeadendBean) o1;
			ResHeadendBean s2 = (ResHeadendBean) o2;
			if (hasAB){
				return ChineseUtil.cn2py(s1.getShortname().substring(0,1)).compareTo(ChineseUtil.cn2py(s2.getShortname().substring(0,1)));

			}else{
				return ChineseUtil.cn2py(s1.getShortname_noab().substring(0,1)).compareTo(ChineseUtil.cn2py(s2.getShortname_noab().substring(0,1)));

			}
		}

    		 
    }
    
    /** 
     * 支持发射台、语言、大州、服务区、城市排序
     * @author thinkpad
     *
     */
   static class SortCommon implements Comparator{

		@Override
		public int compare(Object o1, Object o2) {
			// TODO Auto-generated method stub
			
			if (o1 instanceof DicServicesBean && o2 instanceof DicServicesBean){
				DicServicesBean s1 = (DicServicesBean) o1;
				DicServicesBean s2 = (DicServicesBean) o2;
				
				return ChineseUtil.cn2py(s1.getChinese_name().substring(0,1)).compareTo(ChineseUtil.cn2py(s2.getChinese_name().substring(0,1)));
			}else if (o1 instanceof DicStateBean && o2 instanceof DicStateBean){
				DicStateBean s1 = (DicStateBean) o1;
				DicStateBean s2 = (DicStateBean) o2;
				
				return ChineseUtil.cn2py(s1.getState_name()).compareTo(ChineseUtil.cn2py(s2.getState_name()));
			}else if (o1 instanceof ResAntennaBean && o2 instanceof ResAntennaBean){
				ResAntennaBean s1 = (ResAntennaBean) o1;
				ResAntennaBean s2 = (ResAntennaBean) o2;
				
				return ChineseUtil.cn2py(s1.getAntenna_no()).compareTo(ChineseUtil.cn2py(s2.getAntenna_no()));
			}else if (o1 instanceof ResCityBean && o2 instanceof ResCityBean){
				ResCityBean s1 = (ResCityBean) o1;
				ResCityBean s2 = (ResCityBean) o2;
				
				return ChineseUtil.cn2py(s1.getContry().substring(0,1)).compareTo(ChineseUtil.cn2py(s2.getContry().substring(0,1)));
			}else if (o1 instanceof ZdicLanguageBean && o2 instanceof ZdicLanguageBean){
				ZdicLanguageBean s1 = (ZdicLanguageBean) o1;
				ZdicLanguageBean s2 = (ZdicLanguageBean) o2;
				
				return ChineseUtil.cn2py(s1.getLanguage_name().substring(0,1)).compareTo(ChineseUtil.cn2py(s2.getLanguage_name().substring(0,1)));
			}else if (o1 instanceof StationBean && o2 instanceof StationBean){
				StationBean s1 = (StationBean) o1;
				StationBean s2 = (StationBean) o2;
				
				return ChineseUtil.cn2py(s1.getName().substring(0,1)).compareTo(ChineseUtil.cn2py(s2.getName().substring(0,1)));	
			}
			
			return 0;
		}
    	
    }
   
   public static SortCommon getSortCommon(){
	
	   return new SortCommon();
   }
   
   public static HeadendSort getHeadendSort(boolean b){
		
	   return new HeadendSort(b);
   }
   
   public static void main(String[] args) {

	   ResHeadendBean zlj = new ResHeadendBean();
	   ResHeadendBean dxy = new ResHeadendBean();
	   ResHeadendBean cjc = new ResHeadendBean();
	   ResHeadendBean lgc = new ResHeadendBean();

	   zlj.setShortname("十分钟");
	   dxy.setShortname("基材");
	   cjc.setShortname("茜茜");
	   lgc.setShortname("菜");

	   List<ResHeadendBean> studentList = new ArrayList<ResHeadendBean>();
	   studentList.add(cjc);
	   studentList.add(lgc);
	   studentList.add(dxy);
	   studentList.add(zlj);
	   
	  
	   
	   Collections.sort(studentList,new SortCommon());
	   for(int i=0;i<studentList.size();i++){
		   System.out.println(studentList.get(i).getShortname());
	   }
   }
   /**
	 * 季节代号
	 * @return list
	 * @author 王福祥
	 * @date 2012/09/06
	 */
	public Object getSeasonList(){
		ArrayList list = new ArrayList();
		String sql="select * from dic_season_tab order by  end_time desc  ";
		try {
			GDSet gd = DbComponent.Query(sql);
			if(gd.getRowCount()>0){
				for(int i=0;i<gd.getRowCount();i++){
					SeasonBean bean = new SeasonBean();
					bean.setCode(gd.getString(i, "code"));
					bean.setStart_time(gd.getString(i, "start_time"));
					bean.setEnd_time(gd.getString(i, "end_time"));
					bean.setIs_now(gd.getString(i, "is_now"));
					list.add(bean);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	 /**add by 王福祥
     * @date 2012/12/13
     * 为其它模块提供查询运行图信息
     * @param bean
     * @return
     */
    public Object getAllRunplan(RunplanBean bean){
    	ASObject resObj=null;
    	ArrayList result;
    	String freq = bean.getFreq();
    	String starttime = bean.getStart_time();
    	String endtime = bean.getEnd_time();
    	String headendCode = bean.getShortname();//暂时用这个字段传递code，关联查询mon_area和xg_mon_area
    	String runplanType=bean.getRunplan_type_id();
    	String sql="select t.*,decode(t.runplan_type_id ,1,'国际台运行图',2,'海外落地运行图',3,'外国台运行图') as runplantype," +
    			"zlt.language_name from zres_runplan_tab t ,zdic_language_tab zlt " +
    			"where t.language_id=zlt.language_id and t.valid_end_time>=sysdate";
    	if(freq!=null&&!freq.equalsIgnoreCase("")){
    		sql+=" and t.freq="+freq+"";
    	}
    	if(runplanType!=null&&!runplanType.equals("")){
    		sql+=" and t.runplan_type_id='"+runplanType+"'";
    	}
    	if(headendCode!=null&&!headendCode.equals("")){
    		sql+=" and (t.mon_area like '%"+headendCode+"%' or t.xg_mon_area like '%"+headendCode+"%') ";
    	}
    	if(!starttime.equals("") && !endtime.equals("")){
    		sql +=  " and to_date('1900-01-01 ' || t.start_time || ':00','yyyy-mm-dd hh24:mi:ss') <= to_date('1900-01-01 ' || '"+endtime+"' || ':00','yyyy-mm-dd hh24:mi:ss') ";
    		sql +=  " and to_date('1900-01-01 ' || t.end_time || ':00','yyyy-mm-dd hh24:mi:ss') >= to_date('1900-01-01 ' || '"+starttime+"' || ':00','yyyy-mm-dd hh24:mi:ss') ";
    	}
    	
    	sql += " order by t.freq";
    	try {
    		resObj=StringTool.pageQuerySql(sql, bean);
			
		} catch (Exception e) {
			LogTool.fatal(e);
			return new EXEException("", e.getMessage(), "");
		}
    	
    	return resObj;
    }
    /**
     * 系统拓扑模块 根据站点code可以直接接触报警
     * add by wfx
     * @date 2013/07/03
     * @param code
     * @return
     */
    public Object HandleAlarm(String code){
    	String res="";
    	String sql="update res_headend_tab  set site_status=0 where code like '%"+code+"%'";
    	try {
			if(DbComponent.exeUpdate(sql)){
				res="true";
			}else{
			    res="false";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("","解除报警异常："+e.getMessage(),"");
		}
    	return res;
    }
    /**
     * 查看语音识别内部工作组机器的状态
     * @date 2013/07/10
     * @author 王福祥
     * @return
     */
    public ArrayList checkInfo(){
    	ArrayList list=new ArrayList();
    	String sql="select id,ip,work_port,manage_port,decode(status,'1001','正常在线','1002','忙碌中','1003','异常下线','1004','正在启动') as status, " +
    			" update_time,cpu_performance,memory_all,memory_used from CTRL_NODE_VIEW";
    	try {
			GDSet gd = DbComponent.Query(sql, "pooldb_yysb");
			if(gd.getRowCount()>0){
				for(int i=0;i<gd.getRowCount();i++){
					ASObject obj = new ASObject();
					obj.put("id", gd.getString(i, "id"));
					obj.put("ip", gd.getString(i, "ip"));
					obj.put("work_port", gd.getString(i, "work_port"));
					obj.put("manage_port", gd.getString(i, "manage_port"));
					obj.put("status", gd.getString(i, "status"));
					obj.put("update_time", gd.getString(i, "update_time"));
					obj.put("cpu_performance", gd.getString(i, "cpu_performance"));
					obj.put("memory_all", gd.getString(i, "memory_all"));
					obj.put("memory_used", gd.getString(i, "memory_used"));
					list.add(obj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return new EXEException("","语音识别工作组状态查询异常："+e.getMessage(),"");
		}
    	return list;
    }
    /**获取参考源数据采集的监控状态信息。
     * @author leeo
     * @date 2013/07/11
     * @param asobj
     * @return
     */
    public ArrayList getInfo(){
    	ArrayList list= new ArrayList();
    	String sql="select exception_id,exception,exception_time,solve_method,decode(if_deal,'0','未解决','1','已解决') as if_deal from sa_exception_view";
    	try {
			GDSet gd = DbComponent.Query(sql, "pooldb_yysb");
			if(gd.getRowCount()>0){
				for(int i=0;i<gd.getRowCount();i++){
					ASObject obj = new ASObject();
					obj.put("exception_id", gd.getString(i, "exception_id"));
					obj.put("exception", gd.getString(i, "exception"));
					obj.put("exception_time", gd.getString(i, "exception_time"));
					obj.put("solve_method", gd.getString(i, "solve_method"));
					obj.put("if_deal", gd.getString(i, "if_deal"));
					list.add(obj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return list;
    }
    
    
}
