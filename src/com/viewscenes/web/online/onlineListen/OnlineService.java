package com.viewscenes.web.online.onlineListen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;

import org.apache.axis.utils.StringUtils;
import org.jmask.web.controller.EXEException;

import com.viewscenes.axis.asr.ASRClient;
import com.viewscenes.bean.ASRCmdBean;
import com.viewscenes.bean.ASRResBean;
import com.viewscenes.bean.RealtimeUrlCmdBean;
import com.viewscenes.bean.ResAntennaBean;
import com.viewscenes.bean.ResHeadendBean;
import com.viewscenes.bean.ZdicLanguageBean;
import com.viewscenes.bean.pub.StationBean;
import com.viewscenes.bean.runplan.GJTLDRunplanBean;
import com.viewscenes.bean.runplan.GJTRunplanBean;
import com.viewscenes.bean.runplan.WGTRunplanBean;
import com.viewscenes.dao.XmlReader;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.device.api.StreamAPI;
import com.viewscenes.device.exception.DeviceFilterException;
import com.viewscenes.device.exception.DeviceNotExistException;
import com.viewscenes.device.exception.DeviceProcessException;
import com.viewscenes.device.exception.DeviceReportException;
import com.viewscenes.device.exception.DeviceTimedOutException;
import com.viewscenes.device.radio.MsgStreamRealtimeQueryCmd;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.Security;
import com.viewscenes.sys.SystemConfig;
import com.viewscenes.task.RealRecordManager;
import com.viewscenes.task.RecordRadioClient;
import com.viewscenes.util.Mp3Utils;
import com.viewscenes.util.StringTool;
import com.viewscenes.web.common.Common;
import com.viewscenes.web.runplan.gjt_runplan.GJTRunplan;
import com.viewscenes.web.runplan.gjtld_runplan.GJTLDRunplan;
import com.viewscenes.web.runplan.wgt_runplan.WGTRunplan;

import flex.messaging.io.amf.ASObject;

public class OnlineService {
	//系统运行位置0:外网的内网，1：外网
	private static String runat = "1";
	static {
		runat = XmlReader.getConfigItem("RunConfig").getAttributeValue("runat");
	}
	/**
	 * 取得站点,根据语言、发射台进行联动
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-8-2
	 * @param:
	 * @return:
	 */
	public Object getHeadendList(ASObject obj){
		String fields = (String)obj.get("fields");
		String code = (String)obj.get("code");
		String language_id = (String)obj.get("language_id");
		String station_name = (String)obj.get("station_name");
		
		String sql = buildsql(fields,code,station_name,language_id);
		
		ArrayList list = new ArrayList();
		
		try {
			GDSet set = DbComponent.Query(sql);
			if (fields.equals("h")){
				for(int i=0;i<set.getRowCount();i++){
					try {
						ResHeadendBean bean = new ResHeadendBean();
						String _code = set.getString(i, "code");
						String shortname = set.getString(i, "shortname");
						String state_name = set.getString(i, "state_name");
						String country = set.getString(i, "country");
						String longitude = set.getString(i, "longitude");
						String latitude = set.getString(i, "latitude");
						String time_diff = set.getString(i, "time_diff");
						String type_id = set.getString(i, "type_id");
						
						String is_online = set.getString(i, "is_online");
						bean.setCode(_code);
						bean.setShortname(shortname+"["+_code+"]["+state_name+"]");
						bean.setState_name(state_name);
						bean.setIs_online(is_online);
						bean.setCountry(country);
						bean.setLatitude(latitude);
						bean.setLongitude(longitude);
						bean.setTime_diff(time_diff);
						bean.setType_id(type_id);
						list.add(bean);
					} catch (GDSetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(list,Common.getHeadendSort(true));
		return list;
	}
	
	/**
	 * 取得发射台,根据语言、站点进行联动
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-8-2
	 * @param:
	 * @return:
	 */
	public Object getStationList(ASObject obj){
		String fields = (String)obj.get("fields");
		String code = (String)obj.get("code");
		String language_id = (String)obj.get("language_id");
		String station_name = (String)obj.get("station_name");
		
		String sql = buildsql(fields,code,station_name,language_id);
		
		ArrayList list = new ArrayList();
		try {
			GDSet set = DbComponent.Query(sql);
			if (fields.equals("s")){
				for(int i=0;i<set.getRowCount();i++){
					try {
						StationBean bean = new StationBean();
						String _station_name = set.getString(i, "name");
					
						bean.setName(_station_name);
						
						list.add(bean);
					} catch (GDSetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(list,Common.getSortCommon());
		return list;
		
	}
	
	/**
	 * 取得语言,根据站点、发射台进行联动
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-8-2
	 * @param:
	 * @return:
	 */
	public Object getLanguageList(ASObject obj){
		String fields = (String)obj.get("fields");
		String code = (String)obj.get("code");
		String language_id = (String)obj.get("language_id");
		String station_name = (String)obj.get("station_name");
		
		String sql = buildsql(fields,code,station_name,language_id);
		
		ArrayList list = new ArrayList();
		try {
			GDSet set = DbComponent.Query(sql);
			if (fields.equals("l")){
				for(int i=0;i<set.getRowCount();i++){
					try {
						ZdicLanguageBean bean = new ZdicLanguageBean();
						String _language_id = set.getString(i, "language_id");
						String language_name = set.getString(i, "language_name");
					
						bean.setLanguage_id(_language_id);
						bean.setLanguage_name(language_name);
						
						list.add(bean);
					} catch (GDSetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(list,Common.getSortCommon());
		return list;
		
	}
	
	/**
	 * 生成查询站点、发射台、语言的SQL语句
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-8-2
	 * @param:
	 * @return:
	 */
	public String buildsql(String fields,String code,String station_name,String language_id){
		station_name = station_name.equals("全部")?null:station_name;
		String result = "";
		if (fields.equals("h")){
			result = " rd.*,state.state_name ";
		}else if (fields.equals("l")){
			result = " lan.* ";
		}else if (fields.equals("s")){
			result = " station.* ";
		}
		
		String sql = " select distinct "+result+" ";
		sql += " from zres_runplan_tab  run, ";
		sql += " res_headend_tab   rd, ";
		sql += " zdic_language_tab lan, ";
		sql += " res_transmit_station_tab   station, ";
		sql += " dic_state_tab     state ";
		sql += " where run.is_delete = 0 ";
		sql += " and sysdate between run.valid_start_time and run.valid_end_time ";
		sql += "  and rd.is_delete = 0 ";
		sql += "  and rd.is_online = 1 ";
		sql += " and lan.is_delete = 0 ";
		sql += " and rd.state = state.state ";
//		sql += " and rd.code like run.mon_area ";
		sql += " and ((decode(rd.TYPE_ID,102,substr(rd.code, 0, length(rd.code) - 1),rd.code)) like run.mon_area ";
		sql += " or (decode(rd.TYPE_ID,102,substr(rd.code, 0, length(rd.code) - 1),rd.code)) like run.xg_mon_area) ";
		
		sql += " and lan.language_id = run.language_id ";
		if (fields.equals("s"))
			sql += " and run.station_id = station.station_id ";
		else
			sql += " and run.station_id = station.station_id(+) ";
		//and station.station_name like '%61154%'
		if (code != null && !code.equals("")){
			sql += " and ( '"+((code.endsWith("A") || code.endsWith("B")|| code.endsWith("C")|| code.endsWith("D")|| code.endsWith("E")|| code.endsWith("F")|| code.endsWith("G"))?(code.substring(0,code.length()-1)):code)+"' like run.mon_area ";
			sql += " or '"+((code.endsWith("A") || code.endsWith("B")|| code.endsWith("C")|| code.endsWith("D")|| code.endsWith("E")|| code.endsWith("F")|| code.endsWith("G"))?(code.substring(0,code.length()-1)):code)+"' like run.xg_mon_area) ";
//			sql += " and '"+code+"' like run.mon_area ";
		}

		if (station_name != null && !station_name.equals(""))
			sql += " and station.name like '%"+station_name+"%'";
		
		if (language_id != null && !language_id.equals(""))
			sql += " and run.language_id = "+language_id+" ";
		
		//sql += " and '2000-01-01 '||to_char(sysdate,'hh24:mi:ss') between '2000-01-01 ' || run.start_time || ':00' and '2000-01-01 ' || run.end_time || ':00' ";
		
		
		sql += "  and ((('2000-01-01 ' || to_char(sysdate, 'hh24:mi:ss') between" +
				"'2000-01-01 ' || run.start_time || ':00' and" +
				" '2000-01-01 ' || run.end_time || ':00') and run.start_time<run.end_time )" +
				" or (('2000-01-01 ' || to_char(sysdate, 'hh24:mi:ss') between" +
				"  '2000-01-01 ' || run.start_time || ':00' and  '2000-01-02 ' || run.end_time || ':00')" +
				"  and run.start_time>run.end_time ))";
		sql += " order by 1 ";
				
		return sql;
	}
	
	/**
	 * 查询运行图列表
	 * <p>
	 * class/function:com.viewscenes.web.online
	 * <p>
	 * explain:
	 * <p>
	 * author-date:谭长伟 2012-7-11
	 * 
	 * @param:
	 * @return:
	 */
	public Object getRunplanList(ASObject obj) {
		String runplanType = (String)obj.get("runplanType");
		
		
		
		//国际台运行图
		if(runplanType.equals("1")){
			GJTRunplan gjtRunplan = new GJTRunplan();
			GJTRunplanBean bean = (GJTRunplanBean)obj.get("bean");
			bean.setRunplan_type_id(runplanType);
			bean.setFlag("unit");
			Object resultObj = gjtRunplan.queryRunplan(bean);
			
			return resultObj;
		//国际台落地运行图	
		}else if (runplanType.equals("2")){
			GJTLDRunplan gjtldRunplan = new GJTLDRunplan();
			GJTLDRunplanBean bean = (GJTLDRunplanBean)obj.get("bean");
			bean.setRunplan_type_id(runplanType);
			bean.setFlag("unit");
			Object resultObj = gjtldRunplan.queryRunplan(bean);
			return resultObj;
		//外国台运行图	
		}else if (runplanType.equals("3")){
			WGTRunplan wgtRunplan = new WGTRunplan();
			WGTRunplanBean bean = (WGTRunplanBean)obj.get("bean");
			bean.setRunplan_type_id(runplanType);
			bean.setFlag("unit");
			Object resultObj  = wgtRunplan.queryRunplan(bean);
			return resultObj;
		}	//国际电联频率表	
		else if (runplanType.equals("4")){
			GJTLDRunplan gjtldRunplan = new GJTLDRunplan();
			GJTLDRunplanBean bean = (GJTLDRunplanBean)obj.get("bean");
			bean.setRunplan_type_id(runplanType);
			//bean.setFlag("unit");
			Object resultObj = gjtldRunplan.queryRunplan(bean);
			return resultObj;
		}
		
		return null;
	}

	/**
	 * 音频实时查询方法，开始录音时自动启动45秒录音功能,结束录音自动关闭45秒录音功能
	 * <p>
	 * class/function:com.viewscenes.web.online
	 * <p>
	 * explain:
	 * <p>
	 * author-date:谭长伟 2012-7-11
	 * 
	 * @param:
	 * @return:
	 */
	public Object getRealtimeUrl(RealtimeUrlCmdBean realTimeUrlCmdBean) {
		
		
		String actionTemp = "";
		if (realTimeUrlCmdBean.getAction().equalsIgnoreCase("Stop")) {
			actionTemp = "停止";
			//运行在外网的内网才录音
		  if (runat.equals("1")){
			  RealRecordManager.stop45SecRecord(realTimeUrlCmdBean);
			
			  RealRecordManager.stopNormalRecord(realTimeUrlCmdBean);
		  }
		} else if (realTimeUrlCmdBean.getAction().equalsIgnoreCase("Start")) {
			actionTemp = "开始";
		}

		Security security = new Security();
  
		String priority="1";
		long msgPrio = 0;
		if (realTimeUrlCmdBean.getUserid() != null) {
		       try {
		           msgPrio = security.getMessagePriority(realTimeUrlCmdBean.getUserid(), 3, 0, 9);
		           priority = new Long(msgPrio).toString();
		       } catch (Exception ex1) {
		       }
		}
		
		// SiteRunplanUtil.getBandFromFreq(freq);

		// 检查站点代码
		// throw new EXEException("", "站点代码有误！", "");
		if(realTimeUrlCmdBean.getLastUrl()==null){
		   realTimeUrlCmdBean.setLastUrl("");
		}
		MsgStreamRealtimeQueryCmd.RealtimeStream[] reals = new MsgStreamRealtimeQueryCmd.RealtimeStream[] { new MsgStreamRealtimeQueryCmd.RealtimeStream(
				realTimeUrlCmdBean.getEquCode(), realTimeUrlCmdBean
						.getLastUrl(), realTimeUrlCmdBean.getBand(),
				realTimeUrlCmdBean.getFreq(), realTimeUrlCmdBean.getBps(),
				realTimeUrlCmdBean.getEncode(), realTimeUrlCmdBean.getAction()) };

		Map urls;
		try {
			urls = StreamAPI.msgStreamRealtimeQueryCmd(realTimeUrlCmdBean
					.getCode(), reals, priority);
		} catch (DeviceNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + realTimeUrlCmdBean.getCode()
					+ "]" + actionTemp + "实时音频监听失败|" + e.getMessage(),
					realTimeUrlCmdBean);
		} catch (DeviceFilterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + realTimeUrlCmdBean.getCode()
					+ "]" + actionTemp + "实时音频监听失败|" + e.getMessage(),
					realTimeUrlCmdBean);
		} catch (DeviceProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + realTimeUrlCmdBean.getCode()
					+ "]" + actionTemp + "实时音频监听失败|" + e.getMessage(),
					realTimeUrlCmdBean);
		} catch (DeviceTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + realTimeUrlCmdBean.getCode()
					+ "]" + actionTemp + "实时音频监听失败|" + e.getMessage(),
					realTimeUrlCmdBean);
		} catch (DeviceReportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + realTimeUrlCmdBean.getCode()
					+ "]" + actionTemp + "实时音频监听失败|" + e.getMessage(),
					realTimeUrlCmdBean);
		}
		String url = null;
		if (urls != null && urls.size() > 0) {
			 Set urlSet = urls.entrySet(); 
			 for (Iterator i = urlSet.iterator(); i.hasNext();) { 
		         Map.Entry me = (Map.Entry)i.next(); 
//		         String ok = (String)me.getKey(); 
		         url = (String)me.getValue(); 
		         if (!url.equals(""))
		        	 break;
		      }
			//返回URL成功
//			url = (String) urls.get(0);
			
			
	
			
		} else {
			if (actionTemp.equals("开始"))
				return new EXEException("", "[站点:"
						+ realTimeUrlCmdBean.getCode() + "]" + "没有返回可播放的URL",
						realTimeUrlCmdBean);
		}
       if(url!=null) {
    	   //这里需要判断是否迁移后的站点，如果是迁移后的站点需要修改对应的播放地址；
    	   ResHeadendBean resHeadend = Common.getHeadendBeanByCode(realTimeUrlCmdBean.getCode());
    	   if(resHeadend!=null&&("2").equals(resHeadend.getCom_protocol())) {
    		   if(realTimeUrlCmdBean.getEquCode()==null||realTimeUrlCmdBean.getEquCode().length()==0) {
    			   url =  SystemConfig.getNewPlayUrl() +realTimeUrlCmdBean.getCode()+realTimeUrlCmdBean.getFreq()+".mp3";
    		   }else {
    			   url =  SystemConfig.getNewPlayUrl() +realTimeUrlCmdBean.getCode()+realTimeUrlCmdBean.getEquCode()+realTimeUrlCmdBean.getFreq()+".mp3";
    		   }
    		   try {
    			System.out.println("实时播放地址返回播放器延迟两秒开始！");
				Thread.sleep(2000);
				System.out.println("实时播放地址返回播放器延迟两秒结束！");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	   }else {
    			String address = StringTool.getIpAndPortByUrl(url);
    			
    			String ip  = address.split(":")[0];
    			int port = Integer.parseInt(address.split(":")[1]);
    			
    			if (runat.equals("1"))
    				RealRecordManager.start45RecRecord(realTimeUrlCmdBean, ip, port);
    	   }
       }
		return url;
	}

	
	
	/**
	 * 向语音识别发接口
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-13
	 * @param:
	 * @return:
	 */
	public Object sendCmdASRClient(ASObject obj){
		String url = (String)obj.get("url");
		ResHeadendBean headendBean = (ResHeadendBean)obj.get("headend");
		String collectChannel = (String)obj.get("collectChannel");
		String language = (String)obj.get("language");
		String freq = (String)obj.get("freq");
		String start_datetime = (String)obj.get("start_datetime");
		String end_datetime = (String)obj.get("end_datetime");
		ASRClient client = new ASRClient();
		
		//为null代表是45秒录音语音识别
		if (start_datetime == null || end_datetime == null){
			System.out.println("命令下发中......");
			String path = SystemConfig.getRecord45SecPath();
			String address = StringTool.getIpAndPortByUrl(url);
			String fileName = RecordRadioClient.get45SecFileName(address.split(":")[0],Integer.parseInt(address.split(":")[1]));
			File mp3File = new File(path+fileName);
			if (mp3File.exists()){
				
				Date sdate = new Date(mp3File.lastModified());
				//当前读取的本地的录音文件已超过当前时间60秒，而新的录音文件还没有生成
				if (System.currentTimeMillis() - sdate.getTime() > 90*1000){
					return new EXEException("", "没有最新录音文件,请稍后再试", "");
				}
				
				int sec = Mp3Utils.getMp3Duration(mp3File);
				
				if (sec == 0)
					return new EXEException("", "录音文件时间读取出错,录音文件时间为:"+sec, "");
				
				if (sec < 20)
					return new EXEException("", "录音时间不足20秒,无法发送给语音识别系统,请稍后再试", "");
				
				
				Date edate = new Date(sdate.getTime() + sec * 1000);
				
				ASRCmdBean asrCmdBean = new ASRCmdBean();
				String fileStartTime = StringTool.Date2String(sdate);
				String fileEndTime = StringTool.Date2String(edate);
				
				
				asrCmdBean.setType("TaskStatus");
				asrCmdBean.setWfType("BC573");
				String filepath = SystemConfig.getRecord45SecUrl() + fileName;
				asrCmdBean.setFile(filepath);
				
				asrCmdBean.setFileStartTime(fileStartTime);
				asrCmdBean.setFileEndTime(fileEndTime);
				
				asrCmdBean.setTaskStartTime(fileStartTime);
				asrCmdBean.setTaskEndTime(fileEndTime);
				
				asrCmdBean.setLanguage(language);
				asrCmdBean.setFreq(freq);
				
				asrCmdBean.setCollectChannel(collectChannel);
				asrCmdBean.setCollectMethod(headendBean.getType_id().equalsIgnoreCase("101")?"3":"1");
					
				ASRResBean asrResBean=null;
				try {
					asrResBean = client.exucuteTask(asrCmdBean);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return asrResBean;
			}
		//用户手动进行语音识别	
		}else{
			System.out.println("用户手动进行语音识别命令下发中......");
			ASRCmdBean asrCmdBean = new ASRCmdBean();
			
			
			asrCmdBean.setType("TaskStatus");
			asrCmdBean.setWfType("BC573");
			asrCmdBean.setFile(url);
			
			asrCmdBean.setFileStartTime(start_datetime);
			asrCmdBean.setFileEndTime(end_datetime);
			
			asrCmdBean.setTaskStartTime(start_datetime);
			asrCmdBean.setTaskEndTime(end_datetime);
			
			asrCmdBean.setLanguage(language);
			asrCmdBean.setFreq(freq);
			
			asrCmdBean.setCollectChannel(collectChannel);
			asrCmdBean.setCollectMethod(headendBean.getType_id().equalsIgnoreCase("101")?"3":"1");
				
			ASRResBean asrResBean=null;
			try {
				asrResBean = client.exucuteTask(asrCmdBean);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return asrResBean;
		}
		
		
		return new EXEException("", "没有语音识别可用的文件,请稍后再试", "");
		
	}
	
	
	/**
	 * 下发开始、停止录音指令
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-25
	 * @param:
	 * @return:
	 */
	public Object realRecord(ASObject obj){
		//当前播音节目的一些信息
		RealtimeUrlCmdBean realTimeUrlCmdBean = (RealtimeUrlCmdBean)obj.get("urlCmdBean");
		//正在播放的URL
		String url = (String)obj.get("url");
		
		//开始录音 start 结束录音stop
		String cmd = (String)obj.get("cmd");
		String address = StringTool.getIpAndPortByUrl(url);
		if (address.split(":").length<1){
			return new EXEException("", "URL格式不正确,无法进行录音,URL="+url, "");
		}
		String ip = address.split(":")[0];
		int port = Integer.parseInt(address.split(":")[1]);
		
		//开始录音
		if (cmd.equalsIgnoreCase("start")){
			
			RealRecordManager.startNormalRecord(realTimeUrlCmdBean, ip, port);
			
			return "录音已开始";
			
		//结束录音	
		}else{
			
			return RealRecordManager.stopNormalRecord(realTimeUrlCmdBean);
			
		}
		
		
		
		
	}
	
	
	public static void main(String[] args){
//		String content ="ltsdgfsdfgp://192.168.100.1:8080/asdfasd";
		
//		String content = "";
//		
//		Pattern p1  = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d+)");
//		Pattern p2 = Pattern.compile("\\([^)]+\\)");
//		Pattern p3 = Pattern.compile("\\(([^(]+)/(.+)/((.+)[^)])\\)");
//									 // \\([^(]+)/(.+)/(.+):([^)]+)";
//
//		Matcher matcher1 = p1.matcher(content);
////		
//		matcher1.find();
//		System.out.println(matcher1.group(0));
//		String content1 = matcher1.group(0);
		
//		D/*ate d = new Date();
//		long l =System.currentTimeMillis() -(d.getTime()-45*1000);
//		System.out.println(l);*/
		
		String sDate = StringTool.Date2String(null);
		sDate = sDate.replaceAll(" |:|-", "");
		String s = "站点代号_0_"+sDate+"_录音结束日期时间_频率_接收机代号.mp3";
		int pos =s.indexOf("_0_")+3;
		System.out.println(pos);
		System.out.println(s.substring(pos, 21));
	}
	
	
	
	
	
}
