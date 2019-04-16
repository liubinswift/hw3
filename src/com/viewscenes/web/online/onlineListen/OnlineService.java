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
	//ϵͳ����λ��0:������������1������
	private static String runat = "1";
	static {
		runat = XmlReader.getConfigItem("RunConfig").getAttributeValue("runat");
	}
	/**
	 * ȡ��վ��,�������ԡ�����̨��������
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-8-2
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
	 * ȡ�÷���̨,�������ԡ�վ���������
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-8-2
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
	 * ȡ������,����վ�㡢����̨��������
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-8-2
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
	 * ���ɲ�ѯվ�㡢����̨�����Ե�SQL���
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-8-2
	 * @param:
	 * @return:
	 */
	public String buildsql(String fields,String code,String station_name,String language_id){
		station_name = station_name.equals("ȫ��")?null:station_name;
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
	 * ��ѯ����ͼ�б�
	 * <p>
	 * class/function:com.viewscenes.web.online
	 * <p>
	 * explain:
	 * <p>
	 * author-date:̷��ΰ 2012-7-11
	 * 
	 * @param:
	 * @return:
	 */
	public Object getRunplanList(ASObject obj) {
		String runplanType = (String)obj.get("runplanType");
		
		
		
		//����̨����ͼ
		if(runplanType.equals("1")){
			GJTRunplan gjtRunplan = new GJTRunplan();
			GJTRunplanBean bean = (GJTRunplanBean)obj.get("bean");
			bean.setRunplan_type_id(runplanType);
			bean.setFlag("unit");
			Object resultObj = gjtRunplan.queryRunplan(bean);
			
			return resultObj;
		//����̨�������ͼ	
		}else if (runplanType.equals("2")){
			GJTLDRunplan gjtldRunplan = new GJTLDRunplan();
			GJTLDRunplanBean bean = (GJTLDRunplanBean)obj.get("bean");
			bean.setRunplan_type_id(runplanType);
			bean.setFlag("unit");
			Object resultObj = gjtldRunplan.queryRunplan(bean);
			return resultObj;
		//���̨����ͼ	
		}else if (runplanType.equals("3")){
			WGTRunplan wgtRunplan = new WGTRunplan();
			WGTRunplanBean bean = (WGTRunplanBean)obj.get("bean");
			bean.setRunplan_type_id(runplanType);
			bean.setFlag("unit");
			Object resultObj  = wgtRunplan.queryRunplan(bean);
			return resultObj;
		}	//���ʵ���Ƶ�ʱ�	
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
	 * ��Ƶʵʱ��ѯ��������ʼ¼��ʱ�Զ�����45��¼������,����¼���Զ��ر�45��¼������
	 * <p>
	 * class/function:com.viewscenes.web.online
	 * <p>
	 * explain:
	 * <p>
	 * author-date:̷��ΰ 2012-7-11
	 * 
	 * @param:
	 * @return:
	 */
	public Object getRealtimeUrl(RealtimeUrlCmdBean realTimeUrlCmdBean) {
		
		
		String actionTemp = "";
		if (realTimeUrlCmdBean.getAction().equalsIgnoreCase("Stop")) {
			actionTemp = "ֹͣ";
			//������������������¼��
		  if (runat.equals("1")){
			  RealRecordManager.stop45SecRecord(realTimeUrlCmdBean);
			
			  RealRecordManager.stopNormalRecord(realTimeUrlCmdBean);
		  }
		} else if (realTimeUrlCmdBean.getAction().equalsIgnoreCase("Start")) {
			actionTemp = "��ʼ";
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

		// ���վ�����
		// throw new EXEException("", "վ���������", "");
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
			return new EXEException("", "[վ��:" + realTimeUrlCmdBean.getCode()
					+ "]" + actionTemp + "ʵʱ��Ƶ����ʧ��|" + e.getMessage(),
					realTimeUrlCmdBean);
		} catch (DeviceFilterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[վ��:" + realTimeUrlCmdBean.getCode()
					+ "]" + actionTemp + "ʵʱ��Ƶ����ʧ��|" + e.getMessage(),
					realTimeUrlCmdBean);
		} catch (DeviceProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[վ��:" + realTimeUrlCmdBean.getCode()
					+ "]" + actionTemp + "ʵʱ��Ƶ����ʧ��|" + e.getMessage(),
					realTimeUrlCmdBean);
		} catch (DeviceTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[վ��:" + realTimeUrlCmdBean.getCode()
					+ "]" + actionTemp + "ʵʱ��Ƶ����ʧ��|" + e.getMessage(),
					realTimeUrlCmdBean);
		} catch (DeviceReportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[վ��:" + realTimeUrlCmdBean.getCode()
					+ "]" + actionTemp + "ʵʱ��Ƶ����ʧ��|" + e.getMessage(),
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
			//����URL�ɹ�
//			url = (String) urls.get(0);
			
			
	
			
		} else {
			if (actionTemp.equals("��ʼ"))
				return new EXEException("", "[վ��:"
						+ realTimeUrlCmdBean.getCode() + "]" + "û�з��ؿɲ��ŵ�URL",
						realTimeUrlCmdBean);
		}
       if(url!=null) {
    	   //������Ҫ�ж��Ƿ�Ǩ�ƺ��վ�㣬�����Ǩ�ƺ��վ����Ҫ�޸Ķ�Ӧ�Ĳ��ŵ�ַ��
    	   ResHeadendBean resHeadend = Common.getHeadendBeanByCode(realTimeUrlCmdBean.getCode());
    	   if(resHeadend!=null&&("2").equals(resHeadend.getCom_protocol())) {
    		   if(realTimeUrlCmdBean.getEquCode()==null||realTimeUrlCmdBean.getEquCode().length()==0) {
    			   url =  SystemConfig.getNewPlayUrl() +realTimeUrlCmdBean.getCode()+realTimeUrlCmdBean.getFreq()+".mp3";
    		   }else {
    			   url =  SystemConfig.getNewPlayUrl() +realTimeUrlCmdBean.getCode()+realTimeUrlCmdBean.getEquCode()+realTimeUrlCmdBean.getFreq()+".mp3";
    		   }
    		   try {
    			System.out.println("ʵʱ���ŵ�ַ���ز������ӳ����뿪ʼ��");
				Thread.sleep(2000);
				System.out.println("ʵʱ���ŵ�ַ���ز������ӳ����������");
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
	 * ������ʶ�𷢽ӿ�
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-13
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
		
		//Ϊnull������45��¼������ʶ��
		if (start_datetime == null || end_datetime == null){
			System.out.println("�����·���......");
			String path = SystemConfig.getRecord45SecPath();
			String address = StringTool.getIpAndPortByUrl(url);
			String fileName = RecordRadioClient.get45SecFileName(address.split(":")[0],Integer.parseInt(address.split(":")[1]));
			File mp3File = new File(path+fileName);
			if (mp3File.exists()){
				
				Date sdate = new Date(mp3File.lastModified());
				//��ǰ��ȡ�ı��ص�¼���ļ��ѳ�����ǰʱ��60�룬���µ�¼���ļ���û������
				if (System.currentTimeMillis() - sdate.getTime() > 90*1000){
					return new EXEException("", "û������¼���ļ�,���Ժ�����", "");
				}
				
				int sec = Mp3Utils.getMp3Duration(mp3File);
				
				if (sec == 0)
					return new EXEException("", "¼���ļ�ʱ���ȡ����,¼���ļ�ʱ��Ϊ:"+sec, "");
				
				if (sec < 20)
					return new EXEException("", "¼��ʱ�䲻��20��,�޷����͸�����ʶ��ϵͳ,���Ժ�����", "");
				
				
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
		//�û��ֶ���������ʶ��	
		}else{
			System.out.println("�û��ֶ���������ʶ�������·���......");
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
		
		
		return new EXEException("", "û������ʶ����õ��ļ�,���Ժ�����", "");
		
	}
	
	
	/**
	 * �·���ʼ��ֹͣ¼��ָ��
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-25
	 * @param:
	 * @return:
	 */
	public Object realRecord(ASObject obj){
		//��ǰ������Ŀ��һЩ��Ϣ
		RealtimeUrlCmdBean realTimeUrlCmdBean = (RealtimeUrlCmdBean)obj.get("urlCmdBean");
		//���ڲ��ŵ�URL
		String url = (String)obj.get("url");
		
		//��ʼ¼�� start ����¼��stop
		String cmd = (String)obj.get("cmd");
		String address = StringTool.getIpAndPortByUrl(url);
		if (address.split(":").length<1){
			return new EXEException("", "URL��ʽ����ȷ,�޷�����¼��,URL="+url, "");
		}
		String ip = address.split(":")[0];
		int port = Integer.parseInt(address.split(":")[1]);
		
		//��ʼ¼��
		if (cmd.equalsIgnoreCase("start")){
			
			RealRecordManager.startNormalRecord(realTimeUrlCmdBean, ip, port);
			
			return "¼���ѿ�ʼ";
			
		//����¼��	
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
		String s = "վ�����_0_"+sDate+"_¼����������ʱ��_Ƶ��_���ջ�����.mp3";
		int pos =s.indexOf("_0_")+3;
		System.out.println(pos);
		System.out.println(s.substring(pos, 21));
	}
	
	
	
	
	
}
