package com.viewscenes.web.daily.queryData;

import com.viewscenes.bean.RadioMarkZstViewBean;
import com.viewscenes.bean.RadioStreamResultBean;
import com.viewscenes.bean.device.FileRetrieveResult;
import com.viewscenes.bean.runplan.RunplanBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.SystemConfig;
import com.viewscenes.util.StringTool;
import com.viewscenes.util.business.SiteVersionUtil;
import flex.messaging.io.amf.ASObject;
import org.jmask.web.controller.EXEException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ????????????
 * @author thinkpad
 * 10.15.6.11/12
 */
public class RecFileQuery {

	/**
	 * ????????в????????????????
	 * ????Ч?????(Ч?????????С??????????)?????????
	 * <p>class/function:com.viewscenes.web.daily.queryData
	 * <p>explain:
	 * <p>author-date:???? 2012-8-14
	 * @param:
	 * @return:
	 */
	public Object recFileQuery(ASObject obj){
		ArrayList<RadioStreamResultBean> list = new ArrayList<RadioStreamResultBean>();
		String recType = (String)obj.get("recType");// ????????0??Ч???????1?????????2???????3????????
		String runplanType = (String)obj.get("runplanType");	//?????????1????????????2?????????  ???????????0??ò???????Ч
		String headCode = (String)obj.get("headCode");
		String freq = (String)obj.get("freq");
		String dateType = (String)obj.get("dateType");
		String date = (String)obj.get("date");
		String startDateTime = (String)obj.get("startDateTime");
		String endDateTime = (String)obj.get("endDateTime");
		String handle = (String)obj.get("handle");//?????1-:???,0:δ????(δ????????????????????<100?δ????)???????
		
		String headtype = (String)obj.get("headtype");	//???????,????Χ:101,102
		String abCode = (String)obj.get("abCode");	//A??B??????,????Χ??xxxA,xxxB,'';
		String receiver = (String)obj.get("receiver");	//??????,????Χ??R1,R2,'';
		String stationName=(String)obj.get("station");
		String language_name=(String)obj.get("language");
		
		String sql = "";
		sql = " select t.task_id,hd.shortname,t.equ_code,t.frequency,result_id,t.band task_band,start_datetime,end_datetime,t.frequency task_freq,filename,filesize, t.head_id, ";
		sql += " t.language,t.url,report_type,is_stored,t.store_datetime task_store_datetime,mark_file_name,t.equ_code task_equcode,run.*,decode(run.runplan_type_id,1,run.station_name,2,run.redisseminators,3,run.station_name) station_name_right, " ;//language,t.station_name station,
		sql += " mark_id, mark_user, mark_datetime,        mark.head_code, counti, counto, counts, description, mark_type, edit_user, " ;
		sql += " mark.unit, mark_file_url, file_name, file_length, record_start_time, record_end_time, headname, ";
		sql += " play_time, task_name,  (case when t.level_value>0 then  t.level_value  else '0' end) as level_value ,  t.fm_modulation, t.am_modulation, offset_value, mark.remark mark_remark,  ";
		sql += " asr_type,result_type,status,wavelen,musicratio,noiseratio,speechlen,totalcm,audibilityscore,audibilityconfidence,channelname, ";
		sql += " channelnameconfidence,programname,programnameconfidence,languagename1,languagename2,languagename3,languagename4,languagename5, ";
		sql += " languageconfidence1,languageconfidence2,languageconfidence3,languageconfidence4,languageconfidence5, decode(task.is_temporary, 1, '???????', 2, '???????') is_temporary  ";
		sql += " from radio_stream_result_tab t,radio_mark_zst_view_tab mark,radio_unify_task_tab task, ";
		sql += " (select run.* ";//, p.program_name
		sql += " from zres_runplan_tab run, zdic_language_tab lan ";//, zdic_program_name_tab p
		sql += " where run.language_id = lan.language_id(+) ";
//		sql += " and run.program_id = p.program_id(+) ";
	//	sql += " and sysdate between run.valid_start_time and run.valid_end_time ";
		
	//	sql += " and run.is_delete = 0 ";
		
		//Ч?????
   		if (recType.equals("0")){
   			//??????????????????
   			if (!runplanType.equals("0")){
   				sql += " and run.runplan_type_id= "+runplanType+" ";
   			}
   		}
		sql += " ) run,res_headend_tab hd  ";
		sql += " where t.head_id = hd.head_id ";
		sql += " and t.task_id = task.task_id ";
		sql += " and t.filename = mark.file_name(+) ";
		sql += " and t.is_delete=0 ";
		
		if(stationName!=null)
		{
		   if(stationName.indexOf("?")!=-1)
		   {
			   stationName=stationName.substring(0, stationName.length()-1);
		   }
			sql += " and t.station_name='"+stationName+"' ";
		}
		if(language_name!=null)
		{
			
			sql += " and t.language='"+language_name+"' ";
		}
		
		//?????????????????????????Щ?????????????????????????????е????????????
		System.out.println("recType========"+recType);
		if (recType.equals("-1") || recType.equals("2") || recType.equals("3"))
			sql += " and t.runplan_id = run.runplan_id(+) ";
		//?????Ч?????????????????????????????ù??????????????????????????????й????????
		else
			sql += " and t.runplan_id = run.runplan_id ";
			
			
		String headid = "";
		//??????A,B?????AB?????????????????
		if (abCode ==null || abCode.equals("")){
			if (headCode != null && !headCode.equals("")){
			
				if(headtype==null||headtype.equals(""))
				{
					headtype=SiteVersionUtil.getSiteType(headCode);
				}
				//?????
				if (headtype.equals("101")){
					headid = "'"+SiteVersionUtil.getSiteHeadId(headCode)+"'";
				//????
				}else {
					headid +=  SiteVersionUtil.getSiteHeadIdsByCodeNoAB(headCode);
					
				}
			}
			
		}else{
			if(headtype==null||headtype.equals(""))
			{
				headtype=SiteVersionUtil.getSiteType(headCode);
			}
			//?????
			if (headtype.equals("101")){
				headid = "'"+SiteVersionUtil.getSiteHeadId(abCode)+"'";
			//????
			}else{
				headid = "'" + SiteVersionUtil.getSiteHeadId(abCode) +"'";
				
				}
			
		}
		if (!headid.equals(""))
			sql += " and t.head_id in ("+headid+") ";
		
		//?????R1,R2
		if (receiver != null && !receiver.equals("")){
			sql += " and t.equ_code = '"+receiver.toUpperCase()+"' ";
		}
		
		
       	
       	
       	//????????????
       	if (!recType.equals("-1")){
       		sql += " and t.report_type = '"+recType+"'  ";
       		
       	}
       	
       	if (freq != null && !freq.equals(""))
       		sql += " and t.frequency = "+freq+" ";
		
		if (dateType.equals("byDate")){
			sql += " and t.start_datetime >= to_date('"+date+" 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ";
			sql += "  and t.end_datetime <= to_date('"+date+" 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ";
		}else{
			sql += " and t.start_datetime >= to_date('"+startDateTime+"', 'yyyy-mm-dd hh24:mi:ss') ";
			sql += "  and t.end_datetime <= to_date('"+endDateTime+"', 'yyyy-mm-dd hh24:mi:ss') ";
		}
		
		//?????
		if (handle != null && handle.equals("0")){//or AUDIBILITYCONFIDENCE <100
			//sql += " and (mark_id = null  or (counts is null or counti is null or counto is null )) ";
			//δ?????????????????????????????????????????
			sql += " and (mark.counto is null or (t.language!=mark.languagename1 and mark.edit_user is  null) )";
			
		}else if (handle !=null && handle.equals("1")){
			//sql += " and (counts is not null and counti is not null and counto is not null) ";
			//??????????????????????????????????????????????????????????????????????
			sql += " and (t.language=mark.languagename1 or mark.edit_user is not null  or  mark.mark_user!='??????')";
			
		}
		/*else if (handle != null && handle.equals("1")){
			sql += " and edit_user <> 0  ";
		}*/
		sql += " order by t.start_datetime asc ";
		
		
		ASObject resultObj = null;
		try {
			
			resultObj = StringTool.pageQuerySql(sql, obj);
			//Ч?????????С??????????,
			//map???????Ч?????????????,
			//?????????.
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			
			ArrayList<ASObject> resultList = (ArrayList)resultObj.get("resultList");
			for(int i=0;i<resultList.size();i++){
				ASObject rowObj = (ASObject)resultList.get(i);
				
				String result_id = (String)rowObj.get("result_id");
				String band = (String)rowObj.get("task_band");
				String task_id = (String)rowObj.get("task_id");
				String start_datetime = (String)rowObj.get("start_datetime");
				String end_datetime = (String)rowObj.get("end_datetime");
				String frequency = (String)rowObj.get("task_freq");
				String filename = (String)rowObj.get("filename");
				String filesize = (String)rowObj.get("filesize");
				String head_id = (String)rowObj.get("head_id");
				String url = (String)rowObj.get("url");
				String level_value=(String)rowObj.get("level_value");
				String am=(String)rowObj.get("am_modulation");
				String fm=(String)rowObj.get("fm_modulation");
				String is_temporary=(String)rowObj.get("is_temporary");
				//?????url??????????????????????IP???????????????url???????IP???????????????IP
				if(SystemConfig.runat.equals("1")){//??????????
					url = url.replace(SystemConfig.getLocVideoUrl(), SystemConfig.getVideoUrl());
				}
				String report_type = (String)rowObj.get("report_type");
				String is_stored = (String)rowObj.get("is_stored");
//				String is_delete = (String)rowObj.get("is_delete");
				String store_datetime = (String)rowObj.get("task_store_datetime");
				String mark_file_name = (String)rowObj.get("mark_file_name");
				String runplan_id = (String)rowObj.get("runplan_id");
				String equ_code = (String)rowObj.get("equ_code");
				String station = (String)rowObj.get("station_name_right");
				String language = (String)rowObj.get("language");
				String shortname = (String)rowObj.get("shortname");
//				String head_code = (String)rowObj.get("head_code");
//				Ч?????????С???????????,??????????
//				if (recType.equalsIgnoreCase("0")){
//					
//					if (report_type.equals("0")){
//					
//						Date d = StringTool.stringToDate(start_datetime);
//						int year = d.getYear()+1900;
//						String month = d.getMonth()+1>10?(d.getMonth()+1)+"":"0"+(d.getMonth()+1);
//						String day = d.getDate()>10?d.getDate()+"":"0"+d.getDate();
//						String hour = d.getHours()>10?d.getHours()+"":"0"+d.getHours();
//						String min = d.getMinutes()>10?d.getMinutes()+"":"0"+d.getMinutes();
//						
//						if (Integer.parseInt(min) >30)
//							min = "30";
//						else
//							min = "0";
//						
//						String key = year + month + day +hour + min + frequency + head_code; 
//						
////						System.out.println("????????Σ?"+key);
//						Object  o = map.get(key);
//						
//						if (o == null){
//							map.put(key, 1);
//						}else{
//							continue;
//						}
//					}
//				}
				
				
				RadioStreamResultBean rsrb = new  RadioStreamResultBean();
				
				//????????????????????
				if (!runplan_id.equals("")){
					RunplanBean  runplanBean = new RunplanBean();
					
					runplanBean.setRunplan_id((String)rowObj.get("runplan_id"));
					runplanBean.setRunplan_type_id((String)rowObj.get("runplan_type_id"));
					runplanBean.setStation_id((String)rowObj.get("station_id"));
					runplanBean.setTransmiter_no((String)rowObj.get("transmiter_no"));
					runplanBean.setFreq((String)rowObj.get("freq"));
					runplanBean.setValid_start_time((String)rowObj.get("valid_start_time"));
					runplanBean.setValid_end_time((String)rowObj.get("valid_end_time"));
					runplanBean.setDirection((String)rowObj.get("direction"));
					runplanBean.setPower((String)rowObj.get("power"));
					runplanBean.setService_area((String)rowObj.get("service_area"));
					runplanBean.setAntennatype((String)rowObj.get("antennatype"));
					runplanBean.setRest_datetime((String)rowObj.get("rest_datetime"));
					runplanBean.setRest_time((String)rowObj.get("rest_time"));
					runplanBean.setSentcity_id((String)rowObj.get("sentcity_id"));
					runplanBean.setStart_time((String)rowObj.get("start_time"));
					runplanBean.setEnd_time((String)rowObj.get("end_time"));
					runplanBean.setSatellite_channel((String)rowObj.get("satellite_channel"));
					runplanBean.setStore_datetime((String)rowObj.get("store_datetime"));
					runplanBean.setProgram_type_id((String)rowObj.get("program_type_id"));
					runplanBean.setLanguage_id((String)rowObj.get("language_id"));
					runplanBean.setWeekday((String)rowObj.get("weekday"));
					runplanBean.setInput_person((String)rowObj.get("input_person"));
					runplanBean.setRevise_person((String)rowObj.get("revise_person"));
					runplanBean.setRemark((String)rowObj.get("remark"));
					runplanBean.setProgram_id((String)rowObj.get("program_id"));
				  	runplanBean.setMon_area((String)rowObj.get("mon_area"));
				  	runplanBean.setBand((String)rowObj.get("band"));
				  	runplanBean.setProgram_type((String)rowObj.get("program_type"));
				  	runplanBean.setRedisseminators((String)rowObj.get("redisseminators"));
				  	runplanBean.setLocal_time((String)rowObj.get("local_time"));
				  	runplanBean.setSummer((String)rowObj.get("summer"));
				  	runplanBean.setSummer_starttime((String)rowObj.get("summer_starttime"));
				  	runplanBean.setSummer_endtime((String)rowObj.get("summer_endtime"));
				  	runplanBean.setSeason_id((String)rowObj.get("season_id"));
				  	runplanBean.setAntenna((String)rowObj.get("antenna"));
				  	runplanBean.setStation_name((String)rowObj.get("station_name"));
				  	runplanBean.setCiraf((String)rowObj.get("ciraf"));
//				  	runplanBean.setShortname((String)rowObj.get("shortname"));
//				  	runplanBean.setSendcity((String)rowObj.get("sendcity"));
//				  	runplanBean.setLanguage_name((String)rowObj.get("language_name"));
				  	runplanBean.setProgram_name((String)rowObj.get("language"));
//				  	runplanBean.setType((String)rowObj.get("type"));
//				  	runplanBean.setRunplanType((String)rowObj.get("runplanType"));
				  	runplanBean.setLaunch_country((String)rowObj.get("launch_country"));
				  	
				  	rsrb.setRunplanBean(runplanBean);
				}
				
				
				
				
				String mark_id = (String)rowObj.get("mark_id");
				//??????????????
				if (!mark_id.equals("")){
					RadioMarkZstViewBean rmzvb = new RadioMarkZstViewBean();

					rmzvb.setMark_id((String)rowObj.get("mark_id"));
					rmzvb.setMark_user((String)rowObj.get("mark_user"));
					rmzvb.setMark_datetime((String)rowObj.get("mark_datetime"));
					rmzvb.setHead_code((String)rowObj.get("head_code"));
					rmzvb.setEqu_code((String)rowObj.get("equ_code"));
					rmzvb.setFrequency((String)rowObj.get("frequency"));
					rmzvb.setRunplan_id((String)rowObj.get("runplan_id"));
					//??????2013/09/22??? ?????????I,O,S??????????????;201409255?????????????????????????????????????????????????????????д??????????o
					if(rowObj.get("languagename1").equals(language))//??????2013/09/22??? ?????????I,O,S??????????????;201409255?????????????????????????????????????????????????????????д??????????o
					{
						rmzvb.setCounti((String)rowObj.get("counti"));
						rmzvb.setCounto((String)rowObj.get("counto"));
						rmzvb.setCounts((String)rowObj.get("counts"));
					}else
					{
						if(rowObj.get("mark_user").equals("??????"))
						{
							if(rowObj.get("edit_user")==null||rowObj.get("edit_user").equals(""))
							{
							    rmzvb.setCounto((String)rowObj.get("counto"));	
							}else
							{
								rmzvb.setCounti((String)rowObj.get("counti"));
								rmzvb.setCounto((String)rowObj.get("counto"));
								rmzvb.setCounts((String)rowObj.get("counts"));
							}
						}else 
						{
								rmzvb.setCounti((String)rowObj.get("counti"));
								rmzvb.setCounto((String)rowObj.get("counto"));
								rmzvb.setCounts((String)rowObj.get("counts"));
						}
					}
					rmzvb.setDescription((String)rowObj.get("description"));
					rmzvb.setMark_type((String)rowObj.get("mark_type"));
					rmzvb.setEdit_user((String)rowObj.get("edit_user"));
					rmzvb.setUnit((String)rowObj.get("unit"));
					rmzvb.setMark_file_url((String)rowObj.get("mark_file_url"));
					rmzvb.setFile_name((String)rowObj.get("file_name"));
					rmzvb.setFile_length((String)rowObj.get("file_length"));
					rmzvb.setRecord_start_time((String)rowObj.get("record_start_time"));
					rmzvb.setRecord_end_time((String)rowObj.get("record_end_time"));
					rmzvb.setStation_id((String)rowObj.get("station_id"));
					rmzvb.setStation_name((String)rowObj.get("station_name_right"));
					rmzvb.setHeadname((String)rowObj.get("headname"));
//					rmzvb.setLanguage_name((String)rowObj.get("language_name"));
					rmzvb.setPlay_time((String)rowObj.get("play_time"));
					rmzvb.setTask_id((String)rowObj.get("task_id"));
					rmzvb.setTask_name((String)rowObj.get("task_name"));
					rmzvb.setLevel_value((String)rowObj.get("level_value"));
					rmzvb.setFm_value(fm);
					rmzvb.setAm_value(am);
					rmzvb.setOffset_value((String)rowObj.get("offset_value"));
					rmzvb.setRemark((String)rowObj.get("mark_remark"));
					
					rmzvb.setAsr_type((String)rowObj.get("asr_type"));
					rmzvb.setResult_type((String)rowObj.get("result_type"));
					rmzvb.setWavelen((String)rowObj.get("wavelen"));
					rmzvb.setMusicratio((String)rowObj.get("musicratio"));
					rmzvb.setNoiseratio((String)rowObj.get("noiseratio"));
					rmzvb.setSpeechlen((String)rowObj.get("speechlen"));
					rmzvb.setStatus((String)rowObj.get("status"));
					rmzvb.setTotalcm((String)rowObj.get("totalcm"));
					rmzvb.setAudibilityscore((String)rowObj.get("audibilityscore"));
					rmzvb.setAudibilityconfidence((String)rowObj.get("audibilityconfidence"));
					rmzvb.setChannelname((String)rowObj.get("channelname"));
					rmzvb.setChannelnameconfidence((String)rowObj.get("channelnameconfidence"));
					rmzvb.setProgramname((String)rowObj.get("programname"));
					rmzvb.setProgramnameconfidence((String)rowObj.get("programnameconfidence"));
					rmzvb.setLanguageconfidence1((String)rowObj.get("languageconfidence1"));
					rmzvb.setLanguageconfidence2((String)rowObj.get("languageconfidence2"));
					rmzvb.setLanguageconfidence3((String)rowObj.get("languageconfidence3"));
					rmzvb.setLanguageconfidence4((String)rowObj.get("languageconfidence4"));
					rmzvb.setLanguageconfidence5((String)rowObj.get("languageconfidence5"));
					rmzvb.setLanguagename1((String)rowObj.get("languagename1"));
					rmzvb.setLanguagename2((String)rowObj.get("languagename2"));
					rmzvb.setLanguagename3((String)rowObj.get("languagename3"));
					rmzvb.setLanguagename4((String)rowObj.get("languagename4"));
					rmzvb.setLanguagename5((String)rowObj.get("languagename5"));
					
					rsrb.setRadioMarkZstViewBean(rmzvb);
				}
				
				rsrb.setResult_id(result_id);
				rsrb.setBand(band);
				rsrb.setTask_id(task_id);
				rsrb.setStart_datetime(start_datetime);
				rsrb.setEnd_datetime(end_datetime);
				rsrb.setFrequency(frequency);
				rsrb.setFilename(filename);
				rsrb.setFilesize(filesize);
				rsrb.setHead_id(head_id);
				rsrb.setUrl(url);
				rsrb.setReport_type(report_type);
				rsrb.setIs_stored(is_stored);
				rsrb.setStore_datetime(store_datetime);
				rsrb.setMark_file_name(mark_file_name);
				rsrb.setRunplan_id(runplan_id);
				rsrb.setEqu_code(equ_code);
				rsrb.setStationName(station);
				rsrb.setLanguage(language);
				rsrb.setShortname(shortname);
				rsrb.setLevelValue(level_value);
				rsrb.setAmModulation(am);
				rsrb.setFmModulation(fm);
				rsrb.setIs_temporary(is_temporary);
				list.add(rsrb);
				
				
			}
			
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[???:" + headCode + "]?????????|"
					+ e.getMessage(), "");
		} catch (GDSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[???:" + headCode + "]?????????|"
					+ e.getMessage(), "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[???:" + headCode + "]?????????|"
					+ e.getMessage(), "");
		}
//		System.out.println("?????????????????:"+list.size());
		resultObj.put("resultList",list);
//		resultObj.put("resultTotal", list.size());
		return resultObj;
	}
	
	
	/**
	 * ???????????
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:???? 2012-7-13
	 * @param:
	 * @return:
	 */
//	public Object sendCmdASRClient(ASObject obj){
//		RadioStreamResultBean radioStremResultBean = (RadioStreamResultBean)obj.get("radioStremResultBean");
//		ResHeadendBean headendBean = (ResHeadendBean)obj.get("headend");
////		RunplanBean runplanBean = (RunplanBean)obj.get("runplanBean");
//		
//		if (radioStremResultBean != null){
//			
//			ASRCmdBean asrCmdBean = new ASRCmdBean();
//			
//			asrCmdBean.setType("TaskStatus");
//			asrCmdBean.setWfType("BC573");
//			asrCmdBean.setFile(radioStremResultBean.getUrl());
//			
//			asrCmdBean.setFileStartTime(radioStremResultBean.getStart_datetime());
//			asrCmdBean.setFileEndTime(radioStremResultBean.getEnd_datetime());
//			
//			asrCmdBean.setTaskStartTime(radioStremResultBean.getStart_datetime());
//			asrCmdBean.setTaskEndTime(radioStremResultBean.getEnd_datetime());
//			
//			
//			asrCmdBean.setFreq(radioStremResultBean.getFrequency());
//			
//			String collectChannel = "";
//			String language = "";
//			RunplanBean runplanBean = radioStremResultBean.getRunplanBean();
//			//??й????????????????
//			if (runplanBean != null && runplanBean.getRunplan_type_id().equals("1")){
//				collectChannel = runplanBean.getSatellite_channel();
//				language = runplanBean.getLanguage_name();
//			}
//			asrCmdBean.setCollectChannel(collectChannel);
//			asrCmdBean.setLanguage(language);
//			
//			asrCmdBean.setCollectMethod(headendBean.getType_id().equalsIgnoreCase("101")?"???????":"??????");
//				
//			ASRResBean asrResBean = ASRClient.exucuteTask(asrCmdBean);
//			
//			return asrResBean;
//		}
//		return new EXEException("", "?????????????????,?????????", "");
//		
//	}
	
	/**
	 * ?????????????
	 * ??????1.???????????2.???????????3.?????????????
	 */
	public Object delSelectFile(String  ids){
		
		
		String[] sqls =  new String[2];
	
		sqls[0] = " delete from radio_mark_zst_view_tab t where t.mark_file_url in(select url from " +
					"	radio_stream_result_tab radio where radio.result_id in("+ids+"))";
			
		sqls[1] = "update radio_stream_result_tab set is_delete=1 where result_id in( "+ids+") ";
	
		
		try {
			//?????????????
			DbComponent.exeBatch(sqls);
			//??????????
//			String querySql="select url from radio_stream_result_tab where result_id in( "+ids+")";
//	       GDSet set = DbComponent.Query(querySql);
//			
//			for(int i=0;i<set.getRowCount();i++){
//				 
//				 Zip.delFileFromFtp((String)set.getString(i, "url"));
//			}
			
			
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return new EXEException("", "???????????,???:"+e.getMessage(), "");
		} 
		
		return "????????????";
		
	}
	/**
	 * ?????????
	 * ??????1.???????????2.???????????3.?????????????
	 */
	public Object delRecFile(RadioStreamResultBean rsrb){
		
		String result_id = rsrb.getResult_id();
		String[] sqls =  new String[1];
		if (rsrb.getRadioMarkZstViewBean()!=null){
			String mark_id = rsrb.getRadioMarkZstViewBean().getMark_id();
			if (mark_id.equals(""))
				mark_id = "0";
			String marksql = " delete from radio_mark_zst_view_tab where mark_id = "+mark_id+" ";
			sqls = new String[2];
			sqls[0] = marksql;
		}
		
		String recsql = "update radio_stream_result_tab set is_delete=1 where result_id = "+result_id+" ";
		if (sqls.length == 2)
			sqls[1] = recsql;
		else
			sqls[0] = recsql;
		
		try {
			//?????????????
			DbComponent.exeBatch(sqls);
			//??????????
			//Zip.delFileFromFtp(rsrb.getUrl());
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return new EXEException("", "???????????,???:"+e.getMessage(), "");
		}
		
		return "????????????";
		
	}
	public static void main(String[] args){
//		XmlReader xml = new XmlReader();
//		System.out.println(xml.getConfigItem("RunAt").getText());
	}
	
	
	/**
	 * ???δ??????????б?
	 * maxCount: ?????????
	 */
	public static synchronized ArrayList<FileRetrieveResult> getNotMarkRecordFileList(int maxCount){
		maxCount = maxCount < 1?500:maxCount;
		ArrayList<FileRetrieveResult> list = new ArrayList<FileRetrieveResult>();
		String sql = " select t.task_id,hd.code,t.url,t.start_datetime,t.end_datetime,t.frequency,t.report_type,t.filename,mark.mark_id,t.receive_type ";
        sql +=    " from radio_stream_result_tab t, ";
        sql +=    " radio_mark_zst_view_tab mark, ";
        sql +=    " (select run.* from zres_runplan_tab run, zdic_language_tab lan ";
        sql +=    " where run.language_id = lan.language_id(+) ";
        sql +=    " and sysdate between run.valid_start_time and run.valid_end_time ";
        sql +=    " ) run, ";
        sql +=    " res_headend_tab hd ";
        sql +=    " where t.head_id = hd.head_id ";
        sql +=    " and t.start_datetime>sysdate-32 ";//????????????????????????????

        sql +=    " and t.url = mark.mark_file_url(+) ";
        sql +=    " and t.runplan_id = run.runplan_id(+) ";
        sql +=    " and mark.mark_id is null ";
        sql +=    " and t.is_delete = 0 ";

        sql +=    " order by t.start_datetime desc ";
		   sql= StringTool.pageSql(sql, 1, maxCount);
		
		try {
			GDSet set = DbComponent.Query(sql);
			
			for(int i=0;i<set.getRowCount();i++){
				FileRetrieveResult  result = new FileRetrieveResult();
				String task_id = (String)set.getString(i, "task_id");
				String code = (String)set.getString(i, "code");
				String url = (String)set.getString(i, "url");
				String start_datetime = (String)set.getString(i, "start_datetime");
				String end_datetime = (String)set.getString(i, "end_datetime");
				String frequency = (String)set.getString(i, "frequency");
				String report_type = (String)set.getString(i, "report_type");
				String mark_id = (String)set.getString(i, "mark_id");
				String filename = (String)set.getString(i, "filename");
				String receive_type = (String)set.getString(i, "receive_type");
				
				result.setTaskId(task_id);
				result.setHeadCode(code);
				result.setUrl(url);
				result.setStartDatetime(start_datetime);
				result.setEndDatetime(end_datetime);
				result.setFreq(frequency);
				result.setRecType(report_type);
				result.setFileName(filename);
				result.setReveiceType(receive_type);
				list.add(result);
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GDSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   
		return list;
	}
}
