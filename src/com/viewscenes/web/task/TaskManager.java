package com.viewscenes.web.task;


import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;


import org.jmask.web.controller.EXEException;


import com.viewscenes.bean.ResHeadendBean;
import com.viewscenes.bean.task.CycleSubTask;
import com.viewscenes.bean.task.SingleSubTask;
import com.viewscenes.bean.task.Subtask;
import com.viewscenes.bean.task.Task;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;

import com.viewscenes.device.api.OffsetAPI;
import com.viewscenes.device.api.QualityAPI;
import com.viewscenes.device.api.SpectrumAPI;
import com.viewscenes.device.api.StreamAPI;
import com.viewscenes.device.api.TaskAPI;

import com.viewscenes.device.exception.DeviceFilterException;
import com.viewscenes.device.exception.DeviceNotExistException;
import com.viewscenes.device.exception.DeviceProcessException;
import com.viewscenes.device.exception.DeviceReportException;
import com.viewscenes.device.exception.DeviceTimedOutException;
import com.viewscenes.device.radio.MsgOffsetTaskSetCmd;
import com.viewscenes.device.radio.MsgQualityReportTaskSetCmd;
import com.viewscenes.device.radio.MsgQualityStreamTaskSetCmd;
import com.viewscenes.device.radio.MsgSpectrumTaskSetCmd;
import com.viewscenes.device.radio.MsgStreamTaskSetCmd;
import com.viewscenes.device.radio.MsgTaskStatusQueryRes;
import com.viewscenes.device.util.TaskMethod;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.Security;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.util.TimeMethod;
import com.viewscenes.util.business.SiteVersionUtil;
import com.viewscenes.web.Daoutil.SiteRunplanUtil;
import com.viewscenes.web.common.Common;

import flex.messaging.io.amf.ASObject;



public class TaskManager {
	public final double CountMinutesLimit=27;
	/**
	 * ************************************************
	* @Title: queryAllTaskList
	* @Description: TODO(查询任务列表 包括任务详细)
	* @param @param task
	* @param @return    设定文件
	* @author  刘斌
	* @return String    返回类型
	* @throws
	
	************************************************
	 */
	    public Object queryAllTaskList(Task obj) {
	    	ASObject objRes = new ASObject();
	         String code = obj.getHead_code();//前端code
	         String equCode = obj.getEqu_code();//equ code
	         String task_type = obj.getTask_type();//任务类型 
	         String valid_end_datetime =obj.getValid_enddatetime();//有效开始时间
	         String valid_start_datetime = obj.getValid_startdatetime();//有效结束时间
	         String is_send = obj.getIs_send();//下发状态
	         String send_way=obj.getIs_temporary();//生成方式。
	           ArrayList<Task> list = new ArrayList();
	           GDSet gdSet = null;
		         GDSet gdSet2 = null;

	       String tasklistsql="";
	       String totalsql="select count(*) count from radio_unify_task_tab task ,zres_runplan_tab runplan," +
	       		"radio_sub_task_tab sub, radio_task_time_cycle_tab cyc, radio_task_time_single_tab sin "+
           " where task.task_id=sub.task_id and task.is_delete=0 and sub.sub_task_id=cyc.sub_task_id(+)"+
           " and sub.sub_task_id=sin.sub_task_id(+)  and task.runplan_id = runplan.runplan_id(+) ";
               //0代表全部，1：指标任务； 2：录音任务； 3：频偏任务； 4频谱任务
	        
//	       tasklistsql+="	 select distinct task.task_id,sub.sub_task_id ,decode(task.task_type,1,'指标任务',2,'录音任务',3,'频偏任务',4,'频谱任务',5,'指标录音任务') task_type,cyc.time_id cyc_time_id,sin.time_id sin_time_id,decode(head.type_id,102,substr(head.shortname,0,length(head.shortname)-1)) as shortname,task.equ_code spe_equ_task,task.head_code," +
//	 		"task.valid_startdatetime,task.valid_enddatetime,task.check_level,decode(task.is_temporary,1,'临时任务',2,'日常任务') is_temporary," +
//	 		"task.check_fm_modulation,task.check_am_modulation,task.check_fm_modulation_max,task.create_user,task.authentic_user,task.send_user," +
//	 		"task.check_offset,task.check_bandwidth,decode(task.unit,1,'分钟',2,'秒钟',3,'小时') unit,task.samples_number,decode(task.is_send,0,'未下达',1,'已下达',2,'下达失败') is_send,task.recordlength," +
//	 		"task.runplan_id,task.is_active,task.priority,task.quality_sleeptime,task.stream_sleeptime,task.offset_sleeptime," +
//	 		"task.spectrum_sleeptime,sub.band,sub.bps,sub.startfreq,sub.endfreq,sub.stepfreq,sub.from_runplan,sub.freq,sub.equ_code," +
//	 		"cyc.dayofweek,cyc.starttime cs,cyc.endtime cend,cyc.reportmode cm,cyc.reportinterval cr ,cyc.expiredays ce,task.check_alarm," +
//	 		"sin.reportmode sm,sin.reportinterval sr ,sin.expiredays se,sin.startdatetime ss,sin.enddatetime send,task.note,task.batch_no" + 		
//  " from  radio_unify_task_tab task,"+
//  " res_headend_tab head, radio_sub_task_tab sub, radio_task_time_cycle_tab cyc, radio_task_time_single_tab sin "+
//  " where head.code  like task.head_code||'%' and task.task_id=sub.task_id and task.is_delete=0 and sub.sub_task_id=cyc.sub_task_id(+)"+
//  " and sub.sub_task_id=sin.sub_task_id(+)  ";
	       
	        	 tasklistsql+="	 select task.task_id,sub.sub_task_id ,decode(task.task_type,1,'指标任务',2,'录音任务',3,'频偏任务',4,'频谱任务',5,'指标录音任务') task_type,cyc.time_id cyc_time_id,sin.time_id sin_time_id,task.equ_code spe_equ_task,task.head_code," +
	        	 		"task.valid_startdatetime,task.valid_enddatetime,task.check_level,decode(task.is_temporary,1,'临时任务',2,'日常任务') is_temporary," +
	        	 		"task.check_fm_modulation,task.check_am_modulation,task.check_fm_modulation_max,task.create_user,task.authentic_user,task.send_user," +
	        	 		"task.check_offset,task.check_bandwidth,decode(task.unit,2,'分钟',1,'秒钟',3,'小时') unit,task.samples_number,decode(task.is_send,0,'未下达',1,'已下达',2,'下达失败') is_send,task.recordlength," +
	        	 		"task.runplan_id,task.is_active,task.priority,task.quality_sleeptime,task.stream_sleeptime,task.offset_sleeptime," +
	        	 		"task.spectrum_sleeptime,sub.band,sub.bps,sub.startfreq,sub.endfreq,sub.stepfreq,sub.from_runplan,sub.freq,sub.equ_code," +
	        	 		"cyc.dayofweek,cyc.starttime cs,cyc.endtime cend,cyc.reportmode cm,cyc.reportinterval cr ,cyc.expiredays ce,task.check_alarm," +
	        	 		"sin.reportmode sm,sin.reportinterval sr ,sin.expiredays se,sin.startdatetime ss,sin.enddatetime send,task.note,task.batch_no," +
	        	 		"task.head_type_id,   sub.station_name,sub.language,task.record_type,runplan.start_time||'-'||runplan.end_time as play_time " + 		
	           " from  radio_unify_task_tab task,zres_runplan_tab runplan,"+
//               " res_headend_tab head, radio_sub_task_tab sub, radio_task_time_cycle_tab cyc, radio_task_time_single_tab sin "+
//	           " where task.head_code=head.code and task.task_id=sub.task_id and task.is_delete=0 and sub.sub_task_id=cyc.sub_task_id(+)"+
	           " radio_sub_task_tab sub, radio_task_time_cycle_tab cyc, radio_task_time_single_tab sin "+
	           " where task.task_id=sub.task_id and task.is_delete=0 and sub.sub_task_id=cyc.sub_task_id(+)"+
	           " and sub.sub_task_id=sin.sub_task_id(+) and task.runplan_id = runplan.runplan_id(+) ";
	        	 if(!task_type.equals("0")){
	        		 tasklistsql+=" and task.task_type = "+ task_type;
	        		 totalsql+=" and task.task_type = "+ task_type;
	        	 }
	        
	        	 if(!code.equals(""))
	        	 {   
	        		 if(code.endsWith(",")){
	        			 code += "分AB";
	        		 }
	        		 String[] codeArr = code.split(",");//不分AB，分AB
	        		 if(!codeArr[1].equals("") && !codeArr[1].equals("分AB")){
		        		 tasklistsql+=" and task.head_code ='"+codeArr[1]+"'";
		        		 totalsql+=" and task.head_code ='"+codeArr[1]+"'";
	        		 } else{
	        			 tasklistsql+=" and ( (task.head_code='"+ codeArr[0]+"' and task.is_send<>1) or (task.head_code like '"+ codeArr[0]+"%' ) )";
	        			 totalsql+=" and ( (task.head_code='"+ codeArr[0]+"' and task.is_send<>1) or (task.head_code like '"+ codeArr[0]+"%'  ) )";
	        		 }
//	        		 tasklistsql+=" and (('"+ code+"' like task.head_code || '%'  and task.is_send<>1) OR (task.head_code='"+ code+"' and task.is_send=1)) ";
//	        		 totalsql+=" and (('"+ code+"' like task.head_code || '%'  and task.is_send<>1) OR (task.head_code='"+ code+"' and task.is_send=1)) ";
	        	 }
	        	if(!equCode.equals("")){
	        		tasklistsql+=" and sub.equ_code='"+equCode+"'";
	        		totalsql+=" and sub.equ_code='"+equCode+"'";
	        	}
		        	 if(valid_start_datetime!=""&&valid_end_datetime!="")
		        	 {
		        		 tasklistsql+=" and to_date(task.batch_no,'yyyy-mm-dd hh24:mi:ss' )>= to_date('"+ valid_start_datetime+"','yyyy-mm-dd hh24:mi:ss')  and to_date(task.batch_no,'yyyy-mm-dd hh24:mi:ss' )<= to_date('"+ valid_end_datetime+"','yyyy-mm-dd hh24:mi:ss')   ";
		        		 totalsql+=" and to_date(task.batch_no,'yyyy-mm-dd hh24:mi:ss' )>= to_date('"+ valid_start_datetime+"','yyyy-mm-dd hh24:mi:ss') and to_date(task.batch_no,'yyyy-mm-dd hh24:mi:ss' )<= to_date('"+ valid_end_datetime+"','yyyy-mm-dd hh24:mi:ss')  ";
			       }  
	        	
	        	 /**
	        	  * {label:"全部", data:"0"}, 
					{label:"未下发未审核", data:"1"}, 
					{label:"已审核未下发", data:"2"},
					{label:"已下发", data:"3"} ]);
	        	  */
	        	 if(!is_send.equals("0"))
	        	 {
	        		 if(is_send.equals("1"))
	        		 {
	        		 tasklistsql+=" and task.is_send= 0 and task.authentic_status=0 ";
	        		 totalsql+=" and task.is_send= 0 and task.authentic_status=0 ";
	        		 }
	        		 else if(is_send.equals("2"))
	        		 {
	        			 tasklistsql+=" and task.is_send= 0 and task.authentic_status=1 ";
	        			 totalsql+=" and task.is_send= 0 and task.authentic_status=1 ";
	        		 }
	        		 else if(is_send.equals("3"))
	        		 {
	        			 tasklistsql+=" and task.is_send= 2 ";
	        			 totalsql+=" and task.is_send= 2 ";
	        		 }
	        		 else if(is_send.equals("4"))
	        		 {
	        			 tasklistsql+=" and task.is_send= 3 ";
	        			 totalsql+=" and task.is_send= 3 ";
	        		 }
	        		 else if(is_send.equals("5"))
	        		 {
	        			 tasklistsql+=" and task.is_send= 1 ";
	        			 totalsql+=" and task.is_send= 1 ";
	        		 }
	        	 }
	        	 if(!send_way.equals("0"))
	        	 {
	        		 tasklistsql+=" and task.IS_TEMPORARY= "+ send_way+" ";
	        		 totalsql+=" and task.IS_TEMPORARY= "+ send_way+" ";
	        	 }
	        	 tasklistsql+=" order by task.task_id desc, sub.sub_task_id desc ";
	        	 String temp_sub_take_id="";
	        	 String temp_cyc_time_id="";
	        	 String temp_sin_time_id="";
	      
	         Task tasktemp=new Task();
	         
	      
	       
	         try {
	        	
	        	
	        			Integer startRow = (Integer)obj.getStartRow();
	        			Integer endRow = (Integer)obj.getEndRow();
	        		
				gdSet = DbComponent.Query(StringTool.pageSql(tasklistsql.toString(),startRow,endRow));
				gdSet2= DbComponent.Query(totalsql);

				   for (int i = 0; i < gdSet.getRowCount(); i++) {
					   Task task=new Task();
					    
					      ArrayList cycList =new ArrayList();
				          ArrayList sinList=new ArrayList();
				          ArrayList subList =new ArrayList();
					   if(i==0)
					   {
							  temp_sub_take_id=gdSet.getString(i, "sub_task_id")+",";
				        	  temp_cyc_time_id=gdSet.getString(i, "cyc_time_id")+",";
				        	  temp_sin_time_id=gdSet.getString(i, "sin_time_id")+",";  
				        	  String is_temporary = gdSet.getString(i, "is_temporary");
				        	  String record_type = gdSet.getString(i, "record_type");
				        	  is_temporary = is_temporary+(record_type.equals("")?"":"["+record_type+"]");
				        	  tasktemp.setIs_temporary(is_temporary);
				        	  tasktemp.setTask_id(gdSet.getString(i, "task_id"));
				        	  tasktemp.setEqu_code(gdSet.getString(i, "spe_equ_task"));
				        	  tasktemp.setBatch_no(gdSet.getString(i, "batch_no"));
				        	  tasktemp.setHead_code(gdSet.getString(i, "head_code"));
				        	  ResHeadendBean noabname=Common.getHeadendByCode(gdSet.getString(i, "head_code"),"NOAB");
				        	  String shortname = "";
				        	  if(noabname!=null)
				        	  {
				        		  shortname =noabname.getShortname_noab(); 
				        	  }
				        	
				        		
				        	  if(shortname.equals("")){
				        		  shortname = Common.getHeadendByCode(gdSet.getString(i, "head_code"),"").getShortname();
				        	  }
				        	  tasktemp.setShortname(shortname+"["+tasktemp.getHead_code()+"]");
				        	  tasktemp.setTask_type(gdSet.getString(i, "task_type"));
				        	  tasktemp.setValid_startdatetime(gdSet.getString(i, "valid_startdatetime"));
				        	  tasktemp.setValid_enddatetime(gdSet.getString(i, "valid_enddatetime"));
				        	  tasktemp.setCheck_level(gdSet.getString(i, "check_level"));
				        	  tasktemp.setCheck_fm_modulation(gdSet.getString(i,"check_fm_modulation"));
				        	  tasktemp.setCheck_am_modulation(gdSet.getString(i, "check_am_modulation"));
				        	  tasktemp.setCheck_bandwidth(gdSet.getString(i, "check_bandwidth"));
				        	  tasktemp.setCheck_offset(gdSet.getString(i, "check_offset"));
				        	  tasktemp.setUnit(gdSet.getString(i, "unit"));
				        	  tasktemp.setSamples_number(gdSet.getString(i, "samples_number"));
				        	  tasktemp.setIs_send(gdSet.getString(i, "is_send"));
				        	  tasktemp.setRecordlength(gdSet.getString(i, "recordlength"));
				        	  tasktemp.setRunplan_id(gdSet.getString(i, "runplan_id"));
				        	  tasktemp.setPriority(gdSet.getString(i, "priority"));
				        	  tasktemp.setQuality_sleeptime(gdSet.getString(i, "quality_sleeptime"));
				        	  tasktemp.setStream_sleeptime(gdSet.getString(i, "stream_sleeptime"));
				        	  tasktemp.setOffset_sleeptime(gdSet.getString(i, "offset_sleeptime"));
				        	  tasktemp.setSpectrum_sleeptime(gdSet.getString(i, "spectrum_sleeptime"));
				        	  tasktemp.setCreate_user(gdSet.getString(i, "create_user"));
				        	  tasktemp.setAuthentic_user(gdSet.getString(i, "authentic_user"));
				        	  tasktemp.setSend_user(gdSet.getString(i, "send_user"));
				        	  tasktemp.setFreq(gdSet.getString(i,"freq"));
				        	  tasktemp.setValidDate("自"+gdSet.getString(i, "valid_startdatetime")+"到"+gdSet.getString(i, "valid_enddatetime"));
				        	  tasktemp.setCheck_alarm(gdSet.getString(i,"check_alarm"));
				        	  tasktemp.setHead_type_id(gdSet.getString(i,"head_type_id"));
				        	  tasktemp.setRecord_type(gdSet.getString(i, "record_type"));
				        	  String play_time = gdSet.getString(i, "play_time");
				        	  play_time = play_time.equals("-")?"":play_time;
				        	  tasktemp.setPlay_time(play_time);
				        	  tasktemp.setStation_name(gdSet.getString(i, "station_name"));
				        	  tasktemp.setLanguage(gdSet.getString(i, "language"));
				               
				               
				               Subtask sub=new Subtask();
				               sub.setBand(gdSet.getString(i, "band"));
				               sub.setBps(gdSet.getString(i, "bps"));
				               sub.setEndfreq(gdSet.getString(i, "endfreq"));
				               sub.setEqu_code(gdSet.getString(i, "equ_code"));
				               sub.setFreq(gdSet.getString(i,"freq"));
				               sub.setFrom_runplan(gdSet.getString(i, "from_runplan"));
				               sub.setStartfreq(gdSet.getString(i, "startfreq"));
				               sub.setStepfreq(gdSet.getString(i, "stepfreq"));
				               sub.setSub_task_id(gdSet.getString(i, "sub_task_id"));
				               sub.setTask_id(gdSet.getString(i, "task_id"));
				               sub.setStation_name(gdSet.getString(i, "station_name"));
				               sub.setLanguage(gdSet.getString(i, "language"));
				               
				            
				           
				               
				               CycleSubTask cyc=new CycleSubTask();
				               String cyc_time_id=gdSet.getString(i, "cyc_time_id");
				               cyc.setDayofweek(gdSet.getString(i, "dayofweek"));
				               cyc.setEndtime(gdSet.getString(i, "cend"));
				               cyc.setStarttime(gdSet.getString(i, "cs"));
				               cyc.setExpiredays(gdSet.getString(i, "ce"));
				               cyc.setReportinterval(gdSet.getString(i, "cr"));
				               cyc.setReportmode(gdSet.getString(i, "cm"));
				               cyc.setSub_task_id(gdSet.getString(i, "sub_task_id"));
				               cyc.setTime_id(cyc_time_id);
				               cycList.add(cyc);
				               
				               SingleSubTask sin=new SingleSubTask();
				               String sin_time_id=gdSet.getString(i, "sin_time_id");
				               sin.setEnddatetime(gdSet.getString(i, "send"));
				               sin.setExpiredays(gdSet.getString(i, "se"));
				               sin.setReportinterval(gdSet.getString(i, "sr"));
				               sin.setReportmode(gdSet.getString(i, "sm"));
				               sin.setStartdatetime(gdSet.getString(i, "ss"));
				               sin.setSub_task_id(gdSet.getString(i, "sub_task_id"));
				               sin.setTime_id(sin_time_id);
				               sinList.add(sin);
				               
				               sub.setCyctask(cycList);
				               sub.setSintask(sinList);
				               subList.add(sub);
				               tasktemp.setSubtask(subList);
				               if(gdSet.getRowCount()==1)
				               {
				            	   list.add(tasktemp);   
				               }
				               
					   }else
					   {
						   if(!tasktemp.getTask_id().equals(gdSet.getString(i, "task_id")))
						   {
							   list.add(tasktemp);   
							   tasktemp=new Task();
							   
							   
							      temp_sub_take_id=gdSet.getString(i, "sub_task_id")+",";
					        	  temp_cyc_time_id=gdSet.getString(i, "cyc_time_id")+",";
					        	  temp_sin_time_id=gdSet.getString(i, "sin_time_id")+",";  
					        	  String is_temporary = gdSet.getString(i, "is_temporary");
					        	  String record_type = gdSet.getString(i, "record_type");
					        	  is_temporary = is_temporary+(record_type.equals("")?"":"["+record_type+"]");
					        	  tasktemp.setIs_temporary(is_temporary);
					        	  tasktemp.setTask_id(gdSet.getString(i, "task_id"));
					        	  tasktemp.setEqu_code(gdSet.getString(i, "equ_code"));
					        	  tasktemp.setHead_code(gdSet.getString(i, "head_code"));
					        	  String shortname = Common.getHeadendByCode(gdSet.getString(i, "head_code"),"NOAB").getShortname_noab();
					        	  if(shortname.equals("")){
					        		  shortname = Common.getHeadendByCode(gdSet.getString(i, "head_code"),"").getShortname();
					        	  }
					        	  tasktemp.setShortname(shortname+"["+tasktemp.getHead_code()+"]");
					        	  tasktemp.setTask_type(gdSet.getString(i, "task_type"));
					        	  tasktemp.setBatch_no(gdSet.getString(i, "batch_no"));
					        	  tasktemp.setValid_startdatetime(gdSet.getString(i, "valid_startdatetime"));
					        	  tasktemp.setValid_enddatetime(gdSet.getString(i, "valid_enddatetime"));
					        	  tasktemp.setCheck_level(gdSet.getString(i, "check_level"));
					        	  tasktemp.setCheck_fm_modulation(gdSet.getString(i,"check_fm_modulation"));
					        	  tasktemp.setCheck_am_modulation(gdSet.getString(i, "check_am_modulation"));
					        	  tasktemp.setCheck_bandwidth(gdSet.getString(i, "check_bandwidth"));
					        	  tasktemp.setCheck_offset(gdSet.getString(i, "check_offset"));
					        	  tasktemp.setUnit(gdSet.getString(i, "unit"));
					        	  tasktemp.setSamples_number(gdSet.getString(i, "samples_number"));
					        	  tasktemp.setIs_send(gdSet.getString(i, "is_send"));
					        	  tasktemp.setRecordlength(gdSet.getString(i, "recordlength"));
					        	  tasktemp.setRunplan_id(gdSet.getString(i, "runplan_id"));
					        	  tasktemp.setPriority(gdSet.getString(i, "priority"));
					        	  tasktemp.setQuality_sleeptime(gdSet.getString(i, "quality_sleeptime"));
					        	  tasktemp.setStream_sleeptime(gdSet.getString(i, "stream_sleeptime"));
					        	  tasktemp.setOffset_sleeptime(gdSet.getString(i, "offset_sleeptime"));
					        	  tasktemp.setSpectrum_sleeptime(gdSet.getString(i, "spectrum_sleeptime"));
					        	  tasktemp.setCreate_user(gdSet.getString(i, "create_user"));
					        	  tasktemp.setAuthentic_user(gdSet.getString(i, "authentic_user"));
					        	  tasktemp.setSend_user(gdSet.getString(i, "send_user"));
					        	  tasktemp.setFreq(gdSet.getString(i,"freq"));
					        	  tasktemp.setValidDate("自"+gdSet.getString(i, "valid_startdatetime")+"到"+gdSet.getString(i, "valid_enddatetime"));
					        	  tasktemp.setCheck_alarm(gdSet.getString(i,"check_alarm"));
					        	  tasktemp.setHead_type_id(gdSet.getString(i,"head_type_id"));
					        	  tasktemp.setRecord_type(record_type);
					        	  String play_time = gdSet.getString(i, "play_time");
					        	  play_time = play_time.equals("-")?"":play_time;
					        	  tasktemp.setPlay_time(play_time);
					        	  tasktemp.setStation_name(gdSet.getString(i, "station_name"));
					        	  tasktemp.setLanguage(gdSet.getString(i, "language"));
					               
					               
					               
					               Subtask sub=new Subtask();
					               sub.setBand(gdSet.getString(i, "band"));
					               sub.setBps(gdSet.getString(i, "bps"));
					               sub.setEndfreq(gdSet.getString(i, "endfreq"));
					               sub.setEqu_code(gdSet.getString(i, "equ_code"));
					               sub.setFreq(gdSet.getString(i,"freq"));
					               sub.setFrom_runplan(gdSet.getString(i, "from_runplan"));
					               sub.setStartfreq(gdSet.getString(i, "startfreq"));
					               sub.setStepfreq(gdSet.getString(i, "stepfreq"));
					               sub.setSub_task_id(gdSet.getString(i, "sub_task_id"));
					               sub.setTask_id(gdSet.getString(i, "task_id"));
					               sub.setStation_name(gdSet.getString(i, "station_name"));
					               sub.setLanguage(gdSet.getString(i, "language"));
					           
					               
					               CycleSubTask cyc=new CycleSubTask();
					               String cyc_time_id=gdSet.getString(i, "cyc_time_id");
					               cyc.setDayofweek(gdSet.getString(i, "dayofweek"));
					               cyc.setEndtime(gdSet.getString(i, "cend"));
					               cyc.setStarttime(gdSet.getString(i, "cs"));
					               cyc.setExpiredays(gdSet.getString(i, "ce"));
					               cyc.setReportinterval(gdSet.getString(i, "cr"));
					               cyc.setReportmode(gdSet.getString(i, "cm"));
					               cyc.setSub_task_id(gdSet.getString(i, "sub_task_id"));
					               cyc.setTime_id(cyc_time_id);
					               cycList.add(cyc);
					               
					               SingleSubTask sin=new SingleSubTask();
					               String sin_time_id=gdSet.getString(i, "sin_time_id");
					               sin.setEnddatetime(gdSet.getString(i, "send"));
					               sin.setExpiredays(gdSet.getString(i, "se"));
					               sin.setReportinterval(gdSet.getString(i, "sr"));
					               sin.setReportmode(gdSet.getString(i, "sm"));
					               sin.setStartdatetime(gdSet.getString(i, "ss"));
					               sin.setSub_task_id(gdSet.getString(i, "sub_task_id"));
					               sin.setTime_id(sin_time_id);
					               sinList.add(sin);
					               
					               sub.setCyctask(cycList);
					               sub.setSintask(sinList);
					               subList.add(sub);
					               tasktemp.setSubtask(subList);
			               
						   }else
						   {							   
							   
							   String cyc_time_id=gdSet.getString(i, "cyc_time_id"); 
					        	  if(temp_cyc_time_id.indexOf(cyc_time_id+",")==-1)
					        	  {
					        	        
						               CycleSubTask cyc=new CycleSubTask();
						               cyc.setDayofweek(gdSet.getString(i, "dayofweek"));
						               cyc.setEndtime(gdSet.getString(i, "cend"));
						               cyc.setStarttime(gdSet.getString(i, "cs"));
						               cyc.setExpiredays(gdSet.getString(i, "ce"));
						               cyc.setReportinterval(gdSet.getString(i, "cr"));
						               cyc.setReportmode(gdSet.getString(i, "cm"));
						               cyc.setSub_task_id(gdSet.getString(i, "sub_task_id"));
						               cyc.setTime_id(cyc_time_id);
						               tasktemp.getSubtask().get(0).getCyctask().add(cyc);
						               temp_cyc_time_id+=cyc_time_id+",";

					        	  }
							       String sin_time_id=gdSet.getString(i, "sin_time_id");
							       
							       if(temp_sin_time_id.indexOf(sin_time_id+",")==-1)
							       {
							           
						               SingleSubTask sin=new SingleSubTask();
						      
						               sin.setEnddatetime(gdSet.getString(i, "send"));
						               sin.setExpiredays(gdSet.getString(i, "se"));
						               sin.setReportinterval(gdSet.getString(i, "sr"));
						               sin.setReportmode(gdSet.getString(i, "sm"));
						               sin.setStartdatetime(gdSet.getString(i, "ss"));
						               sin.setSub_task_id(gdSet.getString(i, "sub_task_id"));
						               sin.setTime_id(sin_time_id);
						               tasktemp.getSubtask().get(0).getSintask().add(sin);
						               temp_sin_time_id+=sin_time_id+",";
							       }
 
						   }
						   

					   }
					  if(i== gdSet.getRowCount()-1&&i!=0)
					  {
						  list.add(tasktemp);  
					  }
			         }
				   
				   objRes.put("resultList", list);
					objRes.put("resultTotal", gdSet2.getString(0, "count"));   
			} catch (DbException e) {
				// TODO Auto-generated catch block
				LogTool.fatal(e);
				return new EXEException("", "查询任务信息失败！"+ e.getMessage(),null);
			} catch (GDSetException e) {
				// TODO Auto-generated catch block
				LogTool.fatal(e);
				return new EXEException("", "查询任务信息失败！"+ e.getMessage(),null);
			}
             
			
		
	         return objRes;
	    }
	  /**
	   * ************************************************
	  
	  * @Title: AddSyn_Task
	  
	  * @Description: TODO(生成任务)
	  
	  * @param @param msg
	  * @param @return
	  * @param @throws DbException
	  * @param @throws GDSetException
	  * @param @throws SQLException    设定文件
	  
	  * @author  刘斌
	  
	  * @return String    返回类型
	  
	  * @throws
	  
	  ************************************************
	   */
	public Object AddSyn_Task(ArrayList<Task> totallist) {

		int count = 0;// 记录总共生成的任务数量。
		for (int x = 0; x < totallist.size(); x++) {
			Task task = totallist.get(x);
			// 按照录音，频偏，指标的格式传递后台解析 例如：1,1,1
			String task_types = task.getTask_type();
			if (task_types.equals("4"))// 添加频谱任务
			{
				ArrayList<Subtask> list = task.getSubtask();
				Subtask sub = (Subtask) list.get(0);
				try {
					this.insertoDb(task, sub, "4");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					LogTool.fatal(e);

				}
				++count;
			} 
			else if (task_types.equals("5"))// 添加录音指标任务
			{
				ArrayList<Subtask> list = task.getSubtask();

				for (int i = 0; i < list.size(); i++) {
					Subtask sub = (Subtask) list.get(i);
					try {
						//质量任务优先级认为给调高点
						if(task.getRecord_type().equals("质量")){
							int prio = 10;
							if(task.getPriority() == null && task.getPriority().equals("")){
								
							} else{
								prio = Integer.parseInt(task.getPriority())+10;
							}
							task.setPriority(prio+"");
						}
						this.insertoDb(task, sub, "5");
					} catch (DbException e) {
						LogTool.fatal(e);
						continue;
					} catch (GDSetException e) {
						LogTool.fatal(e);
					}
					++count;
				}
			} 
			else// 添加综合任务。
			{
				String[] types = task_types.split(",");
				String stream_task = types[0];
				String offset_task = types[1];
				String quality_task = types[2];

				// 循环插入前台传递过的的所有频道任务，一个频率对应一个任务。
				ArrayList<Subtask> list = task.getSubtask();

				for (int i = 0; i < list.size(); i++) {
					Subtask sub = (Subtask) list.get(i);
					if (stream_task.equals("1"))// 插入录音任务
					{
                       task.setRecord_type("临时");
						try {
							this.insertoDb(task, sub, "5");
						} catch (DbException e) {
							// TODO Auto-generated catch block
							LogTool.fatal(e);
							continue;
						} catch (GDSetException e) {
							// TODO Auto-generated catch block
							LogTool.fatal(e);
							continue;
						}
						++count;
					}

					if (offset_task.equals("1"))// 插入频偏任务
					{
						try {
							this.insertoDb(task, sub, "3");
						} catch (DbException e) {
							// TODO Auto-generated catch block
							LogTool.fatal(e);
							continue;
						} catch (GDSetException e) {
							// TODO Auto-generated catch block
							LogTool.fatal(e);
							continue;
						}
						++count;
					}
					if (quality_task.equals("1"))// 插入指标任务
					{
						try {
							this.insertoDb(task, sub, "1");
						} catch (DbException e) {
							// TODO Auto-generated catch block
							LogTool.fatal(e);
							continue;
						} catch (GDSetException e) {
							// TODO Auto-generated catch block
							LogTool.fatal(e);
						}
						++count;
					}
				}
			}
		}
		return count;
	}
	        public boolean insertoDb(Task task,Subtask sub ,String taskType) throws DbException, GDSetException  {
	            GDSet gdSet = null;
	            String code = task.getHead_code();
	            String unit =task.getUnit();
	            String valid_startdatetime =task.getValid_startdatetime();
	            String valid_enddatetime = task.getValid_enddatetime();
	            String priority = task.getPriority();
	            String check_level = task.getCheck_level();
	            String check_am_modulation = task.getCheck_am_modulation();
	            String check_fm_modulation = task.getCheck_fm_modulation();
	            String check_bandwidth = task.getCheck_bandwidth();
	            String check_alarm = task.getCheck_alarm();
	            String quality_sleeptime =task.getQuality_sleeptime();
	            String stream_sleeptime = task.getStream_sleeptime();
	            String offset_sleeptime =task.getOffset_sleeptime();
	            String spectrum_sleeptime=task.getSpectrum_sleeptime();
	            String equ_code= task.getEqu_code();
	            String recordlength =task.getRecordlength();
	            String note = task.getNote();
	            String batch_no = task.getBatch_no();
	            String is_temporary=task.getIs_temporary();
	            String samples_number=task.getSamples_number();
	            String create_user=task.getCreate_user();
	            String head_type_id=task.getHead_type_id();
	            if(head_type_id.equals("")){
	            	head_type_id = SiteVersionUtil.getSiteType(code);
	            }
	            String record_type = task.getRecord_type();
	        	String sql =null;
	       	  ArrayList<CycleSubTask> cycList=sub.getCyctask();
        	  ArrayList<SingleSubTask>  sinList=sub.getSintask();
        	  String[] sqls=new String[sinList.size()+cycList.size()+2];
        	  
      	
			
	        	 //循环插入前台传递过的的所有频道任务，一个频率对应一个任务。
          
                    
			                    int k=0;
			                    sql = "select radio_task_seq.nextval as task_id from dual";
			                   
									gdSet = DbComponent.Query(sql);
							
			                    String task_id = null;
							
									task_id = gdSet.getString(0, "task_id");
								
			                
			                	 String runplan_id=sub.getFrom_runplan();

			                	 //判断是否是来自运行图。
			                	 int comefromrunplan=0;
			                	 if(runplan_id.length()!=0)
			                	 {
			                		 comefromrunplan=1; 
			                	 }
			                     sql = "select radio_task_seq.nextval as subtask_id from dual";
			                     
									gdSet = DbComponent.Query(sql);
							
			                     String subtask_id = null;
								
									subtask_id = gdSet.getString(0, "subtask_id");
								
			                     
							
			            
			                	
			                	  int sqlcount=0;
			                	 	 sqls[sqlcount++]=" insert into radio_unify_task_tab(" +
		                      			"TASK_ID,HEAD_CODE,VALID_STARTDATETIME,VALID_ENDDATETIME,CHECK_LEVEL," +
		                      			"CHECK_FM_MODULATION,CHECK_AM_MODULATION,CHECK_BANDWIDTH,UNIT,SAMPLES_NUMBER," +
		                      			"RECORDLENGTH,NOTE,RUNPLAN_ID,BATCH_NO,PRIORITY,IS_TEMPORARY,TASK_TYPE,CREATE_USER," +
		                      			"QUALITY_SLEEPTIME,STREAM_SLEEPTIME,OFFSET_SLEEPTIME,spectrum_sleeptime,equ_code," +
		                      			"check_alarm,head_type_id,record_type" +
		                      			") values(" +task_id+",'"+code+"',to_date('" + valid_startdatetime +"','YYYY-MM-DD HH24:MI:SS'),to_date('" 
		                            		+ valid_enddatetime +"','YYYY-MM-DD HH24:MI:SS'),'"+check_level+"','"+check_fm_modulation+"','"+check_am_modulation+
		                                  "','"+check_bandwidth+"','"+unit+"','"+samples_number+"','"+recordlength+"','"+note+"','"+runplan_id+"','"+batch_no+"','"+
		                                  priority+"','"+is_temporary+"','"+taskType+"','"+create_user+"','"+quality_sleeptime+"','"+stream_sleeptime+"','"+
		                                  offset_sleeptime+"','"+spectrum_sleeptime+"','"+equ_code+"','"+check_alarm+"','"+head_type_id+"','"+record_type+"')";  
		         
			                	  sqls[sqlcount++]="insert into radio_sub_task_tab(SUB_TASK_ID,TASK_ID,equ_code,bps,freq,from_runplan,band,startfreq,endfreq," +
			                	  		"stepfreq,station_name,language) values(" +
				                     subtask_id + "," + task_id +",'" + (sub.getEqu_code()==null?"":sub.getEqu_code()) + "','" + sub.getBps()+"','" +
				                     sub.getFreq()+"','"+comefromrunplan+"','"+sub.getBand()+"','"+sub.getStartfreq()+"','"+sub.getEndfreq()+"','"+
				                     sub.getStepfreq()+"','"+sub.getStation_name()+"','"+sub.getLanguage()+"')";
				                      
				               
			                	 for(int c=0;c< cycList.size();c++)
			                	 {
			                		 CycleSubTask cyc=cycList.get(c); 
			                		 
			                		 sqls[sqlcount++]="insert into radio_task_time_cycle_tab(TIME_ID,DAYOFWEEK,STARTTIME,ENDTIME," +
			                		 		"REPORTMODE,REPORTINTERVAL,EXPIREDAYS,SUB_TASK_ID) " +
			                		 		" values(radio_task_seq.nextval,'"+cyc.getDayofweek()+"','"+
			                		 		cyc.getStarttime()+"','"+cyc.getEndtime()+"',"+cyc.getReportmode()+",'"+cyc.getReportinterval()+"',"+cyc.getExpiredays()+","+subtask_id+")";
			                			//ArrayList unitList=
			                      			//TimeMethod.getUnitTime30(task.getValid_startdatetime(),
			                      				//	task.getValid_enddatetime(), cyc.getStarttime(), cyc.getEndtime(), cyc.getDayofweek());
			                	 }
			                	 for(int s=0;s< sinList.size();s++)
			                	 {
			                		 SingleSubTask sin=sinList.get(s); 
			                		 sqls[sqlcount++]="insert into radio_task_time_single_tab(TIME_ID,STARTDATETIME,ENDDATETIME," +
				                		 		"REPORTMODE,REPORTINTERVAL,EXPIREDAYS,SUB_TASK_ID) " +
				                		 		"values(radio_task_seq.nextval,'"+
				                		 		sin.getStartdatetime()+"','"+sin.getEnddatetime()+"',"+sin.getReportmode()+",'"+sin.getReportinterval()+"',"+sin.getExpiredays()+","+subtask_id+")";
			                	 }
			             
                 DbComponent.exeBatch(sqls);
                 return true;
	        }
	        
	        /**
	         * ************************************************
	        
	        * @Title: DelSyn_TaskList 这里是删除任务列表的任务不是批量删除
	        
	        * @Description: TODO(删除任务功能，没有下发的直接删除数据库，下发的需要删除设备上的任务。)
	        
	        * @param @param msg
	        * @param @return
	        * @param @throws DbException
	        * @param @throws GDSetException    设定文件
	        
	        * @author  刘斌
	        
	        * @return String    返回类型
	        
	        * @throws
	        
	        ************************************************
	         */
 public Object DelSyn_TaskList(ArrayList taskList) {


        String not_online_task[]=new String[taskList.size()];
        System.out.println(taskList.size());

        String sql = "";
        boolean isDel = false;
        int j=0;
        int count1=0;  //记录删除任务成功的计数
        int count2=0;  //记录删除任务失败的计数
        String message="";//记录任务删除失败信息;
        for (int i = 0; i < taskList.size(); i++) {
       	 
     
        	
        	Task task=(Task) taskList.get(i);
            String task_id =task.getTask_id() ;
          
            String task_type =task.getTask_type();
            String is_send = task.getIs_send();
            String code=task.getHead_code();
            String shortName=task.getShortname();
            
             if(is_send.equals("已下达"))//删除数据库也删除设备上的任务。
             {
            	  //下发任务删除接口
                 String sql_online="select t.is_online from res_headend_tab t where t.is_delete=0 and t.code like '%"+code+"%'";
                 GDSet gdset = null;
				try {
					gdset = DbComponent.Query(sql_online);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					LogTool.fatal(e);
				}
                 if(gdset.getRowCount()>0)
                 {
            	 String is_online = "";
				try {
					is_online = gdset.getString(0,"is_online");
				} catch (GDSetException e) {
					// TODO Auto-generated catch block
					LogTool.fatal(e);
				}
		                 if(is_online.equals("1"))
		                 {
		                     //删除设备上的任务
		                 	
		
		            			String freq=task.getFreq();
		            			String band=SiteRunplanUtil.getBandFromFreq(freq);
		            			String valid_startdatetime=task.getValid_startdatetime();
		            			String valid_enddatetime=task.getValid_enddatetime();
		            			String taskTypeWithEnglish=this.checkTaskType(task_type);
		            			String priority=task.getPriority();
		                 		 try {
		            
										isDel = TaskAPI.msgTaskDeleteCmd(code, "",
											freq, band, valid_startdatetime,
											 valid_enddatetime, "",taskTypeWithEnglish ,
											 priority,task_id);
									/**
									 * 如果是录音任务需要删除对应时间单元表中的单元时间总分钟数。
									 */
									
									if(taskTypeWithEnglish.equals("QualityStreamTask"))
									{
									  	this.updateUnitToAdc(task);
									}
								} catch (DeviceNotExistException e) {
									// TODO Auto-generated catch block
									LogTool.fatal(e);
							         count2++;
		                             message+="站点:"+shortName+task_type+" 任务删除失败！\r";
		                             continue;
								} catch (DeviceFilterException e) {
									// TODO Auto-generated catch block
									LogTool.fatal(e);
							         count2++;
		                             message+="站点:"+shortName+task_type+" 任务删除失败！\r";
		                             continue;
								} catch (DeviceProcessException e) {
									// TODO Auto-generated catch block
									LogTool.fatal(e);
							         count2++;
		                             message+="站点:"+shortName+task_type+" 任务删除失败！\r";
		                             continue;
								} catch (DeviceTimedOutException e) {
									// TODO Auto-generated catch block
									LogTool.fatal(e);
							         count2++;
		                             message+="站点:"+shortName+task_type+" 任务删除失败！\r";
		                             continue;
								} catch (DeviceReportException e) {
									LogTool.fatal(e);
									if(e.getMessage().indexOf("任务不存在")<0){
								         count2++;
			                             message+="站点:"+shortName+task_type+" 任务删除失败！\r";
			                             continue;
									}
								} catch (Exception e) {
									LogTool.fatal(e);
							         count2++;
		                             message+="站点:"+shortName+task_type+" 任务删除失败！\r";
		                             continue;
								}
		                    
		                         
		                         	 sql = "update radio_unify_task_tab set is_delete =1 where task_id = '"+ task_id + "'";
		                              Boolean gd1 = null;
		                              try {
										gd1 = DbComponent.exeUpdate(sql.toString());
									} catch (DbException e) {
										// TODO Auto-generated catch block
										LogTool.fatal(e);
									}
		                      
							
		                              if(gd1){
		                              	count1++;
		                              }
		                          
		
		                 } else
		                 {
		                     
		                 	not_online_task[j]=task_id;
		                     j++;
		                     count2++;
		                     message+="站点:"+shortName+"站点不在线,"+task_type+" 任务删除失败！\r";
		                  
		                 } 
                 }
             }else //只删除数据库 。 
             {
            		sql = "update radio_unify_task_tab set is_delete =1 where task_id = '" +
                    task_id + "'";
                    Boolean gd1 = false;
                    try {
						gd1 = DbComponent.exeUpdate(sql.toString());
					} catch (DbException e) {
						// TODO Auto-generated catch block
						LogTool.fatal(e);
					}
                    if(gd1){
                    	count1++;
                    } 
             }

           
        }
      deleteTimeUnit0();
      ArrayList returnlist=new ArrayList();
      returnlist.add(count1);
      returnlist.add(count2);
      returnlist.add(message);
      return returnlist;
    }
 
 /**
  * 删除0分钟的记录
  * @detail  
  * @method   
  * @return  void  
  * @author  zhaoyahui
  * @version 2013-3-28 下午05:52:26
  */
 private void deleteTimeUnit0(){
	 String sql = "delete res_task_unit_tab   where count_minutes = 0";
     try {
			DbComponent.exeUpdate(sql.toString());
		} catch (DbException e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
		}
 }
 /**
  * ************************************************
 
 * @Title: delBatchTaskList
 
 * @Description: TODO(批量删除任务模块功能，没有下发的直接删除数据库，下发的需要删除设备上的任务)
 
 * @param @param msg
 * @param @return
 * @param @throws DbException
 * @param @throws GDSetException    设定文件
 
 * @author  刘斌
 
 * @return String    返回类型
 
 * @throws
 
 ************************************************
  */
public Object delBatchTaskList(ASObject obj) {
	String headcodes = (String)obj.get("headcodes");
	String userName = (String)obj.get("userName");
	String startDate = (String)obj.get("startDate");
	String endDate = (String)obj.get("endDate");
	String date = (String)obj.get("date");
	String freq = (String)obj.get("freq");
	String check_quality_task = (String)obj.get("check_quality_task");
	String check_stream_task = (String)obj.get("check_stream_task");
	String check_offset_task = (String)obj.get("check_offset_task");
	String check_spec_task = (String)obj.get("check_spec_task");
	String check_qualityStream_task = (String)obj.get("check_qualityStream_task");
	String band = (String)obj.get("band");
	int count=0;
		if(check_quality_task.equals("1"))
		{
			check_quality_task="QualityTask";
			count++;
		}
		
		if(check_stream_task.equals("1"))
		{
			check_stream_task="StreamTask";
			count++;
		}
		if(check_offset_task.equals("1"))
		{
			check_offset_task="OffsetTask";
			count++;
		}
		if(check_spec_task.equals("1"))
		{
			check_spec_task="SpectrumTask";
			count++;
		}
		if(check_qualityStream_task.equals("1"))
		{
			check_qualityStream_task="QualityStreamTask";
			count++;
		}
		String sql = "";
		boolean isDel = false;
		boolean isDelStreamQuality = false;
		int j=0;
		int count1=0;  //记录删除任务成功的计数
		int count2=0;  //记录删除任务失败的计数
		String message="";//记录任务删除失败信息;
		String[] deleteCodes=headcodes.split(",");
		
	      Security security = new Security();
	       String priority="1";
	       long msgPrio=0;
			  if ( userName!= null) {
			      try {
			          msgPrio = security.getMessagePriorityByUserName(userName, 0, 0, 0);
			          priority = new Long(msgPrio).toString();
			      } catch (Exception ex1) {

			      }
			  }
			  String deleByTasktype="";
		for (int i = 0; i < deleteCodes.length; i++) {
			deleByTasktype="";
			isDelStreamQuality = false;
		 String code=deleteCodes[i];
		 String   shortName=Common.getHeadendnameByCode(code);  
		 //删除设备上的任务
		
		 
            if(count==5)
            {
            		
            	try {
						isDel = TaskAPI.msgTaskDeleteCmd(code, "",
							freq, band, startDate,
							endDate, date,"" ,
							 priority,"");
						isDelStreamQuality = isDel;
				} catch (Exception e) {
					if(e.getMessage() != null && e.getMessage().indexOf("任务不存在")>-1){
				         message+="站点:"+shortName+" 任务删除设备端失败！"+e.getMessage()+"\r";
					} else{
						deleByTasktype=" and 1=2";
				         message+="站点:"+shortName+" 任务删除失败！"+e.getMessage()+"\r";
				         count2++;
					}
					LogTool.fatal(e);
				}
            }else
            {
            	if("QualityStreamTask".equals(check_qualityStream_task))
        		{
            		deleByTasktype +="5,";
//                        " and task.task_type=5 ";
            		try{
                		isDel = TaskAPI.msgTaskDeleteCmd(code, "",
    							freq, band, startDate,
    							endDate, date,check_qualityStream_task ,
    							 priority,"");
						isDelStreamQuality = isDel;
                	} catch (Exception e) {
    					// TODO Auto-generated catch block
                		LogTool.fatal(e);
				         if(e.getMessage() != null && e.getMessage().indexOf("任务不存在")>-1){
	    			         message+="站点:"+shortName+check_qualityStream_task+" 任务批量删除设备端失败！"+e.getMessage()+"\r";
				         }  else{
				        	 message+="站点:"+shortName+check_qualityStream_task+" 任务批量删除失败！"+e.getMessage()+"\r";
					         count2++;
				         }
    				}
        		}
            	if("QualityTask".equals(check_quality_task))
            	{
            		deleByTasktype +="1,";
            		try{
            		isDel = TaskAPI.msgTaskDeleteCmd(code, "",
							freq, band, startDate,
							endDate, date,check_quality_task ,
							 priority,"");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						LogTool.fatal(e);
				         if(e.getMessage() != null && e.getMessage().indexOf("任务不存在")>-1){
	    			         message+="站点:"+shortName+check_quality_task+" 任务批量删除设备端失败！"+e.getMessage()+"\r";
				         }  else{
				        	 message+="站点:"+shortName+check_quality_task+" 任务批量删除失败！"+e.getMessage()+"\r";
					         count2++;
				         }
					}
            	}
            	if("StreamTask".equals(check_stream_task))
            	{
            		deleByTasktype +="2,";
            		try{
            			isDel = TaskAPI.msgTaskDeleteCmd(code, "",
    						freq, band, startDate,
    						endDate, date,check_stream_task ,
    						 priority,"");
            		
            		
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					LogTool.fatal(e);
				         if(e.getMessage() != null && e.getMessage().indexOf("任务不存在")>-1){
	    			         message+="站点:"+shortName+check_stream_task+" 任务批量删除设备端失败！"+e.getMessage()+"\r";
				         }  else{
				        	 message+="站点:"+shortName+check_stream_task+" 任务批量删除失败！"+e.getMessage()+"\r";
					         count2++;
				         }
    				}
            	}
            	if("OffsetTask".equals(check_offset_task))
            	{
            		deleByTasktype +="3,";
            		try{
            		isDel = TaskAPI.msgTaskDeleteCmd(code, "",
    						freq, band, startDate,
    						endDate, date,check_offset_task ,
    						 priority,"");
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					LogTool.fatal(e);
				         if(e.getMessage() != null && e.getMessage().indexOf("任务不存在")>-1){
	    			         message+="站点:"+shortName+check_offset_task+" 任务批量删除设备端失败！"+e.getMessage()+"\r";
				         }  else{
				        	 message+="站点:"+shortName+check_offset_task+" 任务批量删除失败！"+e.getMessage()+"\r";
					         count2++;
				         }
    				}
            	}
            	if("SpectrumTask".equals(check_spec_task))
            	{
            		deleByTasktype +="4,";
            		try{
            		isDel = TaskAPI.msgTaskDeleteCmd(code, "",
    						freq, band, startDate,
    						endDate, date,check_spec_task ,
    						 priority,"");
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					LogTool.fatal(e);
				         if(e.getMessage() != null && e.getMessage().indexOf("任务不存在")>-1){
	    			         message+="站点:"+shortName+check_spec_task+" 任务批量删除设备端失败！"+e.getMessage()+"\r";
				         }  else{
				        	 message+="站点:"+shortName+check_spec_task+" 任务批量删除失败！"+e.getMessage()+"\r";
					         count2++;
				         }
    				}
            	}
            	if(deleByTasktype.length()>0){
            		deleByTasktype = deleByTasktype.substring(0, deleByTasktype.length()-1);
            		deleByTasktype = " and task.task_type in("+deleByTasktype+") ";
            	} else{
            		deleByTasktype = " and 1=2";
            	}
            }
//          if(isDelStreamQuality==true)
////            if(true)
//          {
        	  String updatesql="	 select task.task_id,sub.sub_task_id ,task.task_type,cyc.time_id cyc_time_id,sin.time_id sin_time_id,task.head_code," +
  	 		" task.head_type_id,task.valid_startdatetime,task.valid_enddatetime," +
	 	     "task.is_send,task.recordlength,sub.equ_code," +
	 		"cyc.dayofweek,cyc.starttime cs,cyc.endtime cend ," +
	 		"sin.startdatetime ss,sin.enddatetime send ,task.record_type" + 		
		   " from  radio_unify_task_tab task,"+
		   " res_headend_tab head, radio_sub_task_tab sub, radio_task_time_cycle_tab cyc, radio_task_time_single_tab sin "+
		   " where task.head_code=head.code and task.task_id=sub.task_id and task.is_delete=0 and sub.sub_task_id=cyc.sub_task_id(+)"+
		   " and sub.sub_task_id=sin.sub_task_id(+) and task.head_code='"+code+"' ";
          	 sql = " update radio_unify_task_tab set is_delete=1 where  task_id in(" +
          	 		" select task.task_id from  radio_unify_task_tab task ,radio_sub_task_tab sub" +
          	 		" where task.task_id=sub.task_id and '"+code+"' like  task.head_code || '%' and task.is_delete=0 ";
          	 
          	 sql += deleByTasktype;
          	updatesql += deleByTasktype;
          	  if (!freq.trim().equalsIgnoreCase("")) {
                  sql +=   "  and freq = " + freq;
                  updatesql +=   "  and sub.freq = " + freq;
                 
                }
                else if ( band.equalsIgnoreCase("0")) {
                  sql+=
                      " and freq >=2300 and freq <=26100  ";
                  updatesql+=
                      " and sub.freq >=2300 and sub.freq <=26100  ";
                }
                else if ( band.equalsIgnoreCase("1")) {
                  sql +=
                      " and freq >=531 and freq <=1602  ";
                  updatesql +=
                      " and sub.freq >=531 and sub.freq <=1602  ";
                }
                else if (band != null && band.equalsIgnoreCase("2")) {
                  sql +=
                      " and freq >=87000 and freq <=108000  ";
                  updatesql +=
                      " and sub.freq >=87000 and sub.freq <=108000  ";
                }

          	  if (!startDate.equalsIgnoreCase("") &&
                      !endDate.equalsIgnoreCase("")) {
                    sql += " and valid_startdatetime < to_date('" +
                    endDate +
                        "','YYYY-MM-DD HH24:MI:SS') ";
                    updatesql += " and valid_startdatetime < to_date('" +
                    endDate +
                        "','YYYY-MM-DD HH24:MI:SS') ";
                    sql += " and valid_enddatetime > to_date('" +
                    startDate +
                        "','YYYY-MM-DD HH24:MI:SS') ";
                    updatesql += " and valid_enddatetime > to_date('" +
                    startDate +
                        "','YYYY-MM-DD HH24:MI:SS') ";
                  }
                 

                  if (date != null && date.trim().length() == 10) {
                    sql += " and send_datetime < to_date('" +
                        date.substring(0, 10) +
                        " 23:59:59" + "','YYYY-MM-DD HH24:MI:SS') ";
                    updatesql += " and send_datetime < to_date('" +
                    date.substring(0, 10) +
                    " 23:59:59" + "','YYYY-MM-DD HH24:MI:SS') ";
                    sql += " and send_datetime >= to_date('" +
                        date.substring(0, 10) +
                        " 00:00:00" + "','YYYY-MM-DD HH24:MI:SS') ";
                    updatesql += " and send_datetime >= to_date('" +
                    date.substring(0, 10) +
                    " 00:00:00" + "','YYYY-MM-DD HH24:MI:SS') ";
                  }
                  sql+=")";
                  updatesql+=" order by task.task_id desc, sub.sub_task_id desc ";
  	        	

		                           Boolean gd1 = null;
		                           try {
									
										 String temp_sub_take_id="";
							        	 String temp_cyc_time_id="";
							        	 String temp_sin_time_id="";
							      ArrayList list =new ArrayList();
							      Task tasktemp=new Task();
							      if(isDelStreamQuality==true){
									GDSet	gdSet = DbComponent.Query(updatesql);
									
										   for (int k = 0; k < gdSet.getRowCount(); k++) {
											   Task task=new Task();
											    
											      ArrayList cycList =new ArrayList();
										          ArrayList sinList=new ArrayList();
										          ArrayList subList =new ArrayList();
											   if(k==0)
											   {
													  temp_sub_take_id=gdSet.getString(k, "sub_task_id")+",";
										        	  temp_cyc_time_id=gdSet.getString(k, "cyc_time_id")+",";
										        	  temp_sin_time_id=gdSet.getString(k, "sin_time_id")+",";  
                                                      tasktemp.setTask_id(gdSet.getString(k, "task_id"));
										        	  tasktemp.setHead_code(gdSet.getString(k, "head_code"));
										        	  tasktemp.setHead_type_id(gdSet.getString(k, "head_type_id"));
										        	  tasktemp.setTask_type(gdSet.getString(k, "task_type"));
										        	  tasktemp.setIs_send(gdSet.getString(k, "is_send"));
										        	  tasktemp.setValid_startdatetime(gdSet.getString(k, "valid_startdatetime"));
										        	  tasktemp.setValid_enddatetime(gdSet.getString(k, "valid_enddatetime"));
										        	 
										        	  tasktemp.setRecordlength(gdSet.getString(k, "recordlength"));
										        	  tasktemp.setRecord_type(gdSet.getString(k, "record_type"));

										               Subtask sub=new Subtask();
										               sub.setEqu_code(gdSet.getString(k, "equ_code"));
										               sub.setSub_task_id(gdSet.getString(k, "sub_task_id"));
										               sub.setTask_id(gdSet.getString(k, "task_id"));
  
										               CycleSubTask cyc=new CycleSubTask();
										               String cyc_time_id=gdSet.getString(k, "cyc_time_id");
										               cyc.setDayofweek(gdSet.getString(k, "dayofweek"));
										               cyc.setEndtime(gdSet.getString(k, "cend"));
										               cyc.setStarttime(gdSet.getString(k, "cs"));
										               cyc.setSub_task_id(gdSet.getString(k, "sub_task_id"));
										               cyc.setTime_id(cyc_time_id);
										               cycList.add(cyc);
										               
										               SingleSubTask sin=new SingleSubTask();
										               String sin_time_id=gdSet.getString(k, "sin_time_id");
										               sin.setEnddatetime(gdSet.getString(k, "send"));
										               sin.setStartdatetime(gdSet.getString(k, "ss"));
										               sin.setSub_task_id(gdSet.getString(k, "sub_task_id"));
										               sin.setTime_id(sin_time_id);
										               sinList.add(sin);
										               
										               sub.setCyctask(cycList);
										               sub.setSintask(sinList);
										               subList.add(sub);
										               tasktemp.setSubtask(subList);
										               if(gdSet.getRowCount()==1)
										               {
										            	   list.add(tasktemp);   
										               }
										               
											   }else
											   {
												   if(!tasktemp.getTask_id().equals(gdSet.getString(k, "task_id")))
												   {
													   list.add(tasktemp);   
													   tasktemp=new Task();
													   
													   
													      temp_sub_take_id=gdSet.getString(k, "sub_task_id")+",";
											        	  temp_cyc_time_id=gdSet.getString(k, "cyc_time_id")+",";
											        	  temp_sin_time_id=gdSet.getString(k, "sin_time_id")+",";  
											        	  tasktemp.setTask_id(gdSet.getString(k, "task_id"));
											        	  tasktemp.setHead_code(gdSet.getString(k, "head_code"));
											        	  tasktemp.setHead_type_id(gdSet.getString(k, "head_type_id"));
											        	  tasktemp.setTask_type(gdSet.getString(k, "task_type"));
											        	  tasktemp.setValid_startdatetime(gdSet.getString(k, "valid_startdatetime"));
											        	  tasktemp.setValid_enddatetime(gdSet.getString(k, "valid_enddatetime"));
											        	  tasktemp.setIs_send(gdSet.getString(k, "is_send"));
											        	  tasktemp.setRecordlength(gdSet.getString(k, "recordlength"));
											        	  tasktemp.setRecord_type(gdSet.getString(k, "record_type"));
											               Subtask sub=new Subtask();

											               sub.setSub_task_id(gdSet.getString(k, "sub_task_id"));
											               sub.setTask_id(gdSet.getString(k, "task_id"));
											               sub.setEqu_code(gdSet.getString(k, "equ_code"));
											               
											               CycleSubTask cyc=new CycleSubTask();
											               String cyc_time_id=gdSet.getString(k, "cyc_time_id");
											               cyc.setDayofweek(gdSet.getString(k, "dayofweek"));
											               cyc.setEndtime(gdSet.getString(k, "cend"));
											               cyc.setStarttime(gdSet.getString(k, "cs"));
											               cyc.setSub_task_id(gdSet.getString(k, "sub_task_id"));
											               cyc.setTime_id(cyc_time_id);
											               cycList.add(cyc);
											               
											               SingleSubTask sin=new SingleSubTask();
											               String sin_time_id=gdSet.getString(k, "sin_time_id");
											               sin.setEnddatetime(gdSet.getString(k, "send"));
											               sin.setStartdatetime(gdSet.getString(k, "ss"));
											               sin.setSub_task_id(gdSet.getString(k, "sub_task_id"));
											               sin.setTime_id(sin_time_id);
											               sinList.add(sin);
											               
											               sub.setCyctask(cycList);
											               sub.setSintask(sinList);
											               subList.add(sub);
											               tasktemp.setSubtask(subList);
									               
												   }else
												   {							   
													   
													   String cyc_time_id=gdSet.getString(k, "cyc_time_id"); 
											        	  if(temp_cyc_time_id.indexOf(cyc_time_id+",")==-1)
											        	  {
											        	        
												               CycleSubTask cyc=new CycleSubTask();
												               cyc.setDayofweek(gdSet.getString(k, "dayofweek"));
												               cyc.setEndtime(gdSet.getString(k, "cend"));
												               cyc.setStarttime(gdSet.getString(k, "cs"));
												               cyc.setSub_task_id(gdSet.getString(k, "sub_task_id"));
												               cyc.setTime_id(cyc_time_id);
												               tasktemp.getSubtask().get(0).getCyctask().add(cyc);
												               temp_cyc_time_id+=cyc_time_id+",";

											        	  }
													       String sin_time_id=gdSet.getString(k, "sin_time_id");
													       
													       if(temp_sin_time_id.indexOf(sin_time_id+",")==-1)
													       {
													           
												               SingleSubTask sin=new SingleSubTask();
												      
												               sin.setEnddatetime(gdSet.getString(k, "send"));
												               sin.setStartdatetime(gdSet.getString(k, "ss"));
												               sin.setSub_task_id(gdSet.getString(k, "sub_task_id"));
												               sin.setTime_id(sin_time_id);
												               tasktemp.getSubtask().get(0).getSintask().add(sin);
												               temp_sin_time_id+=sin_time_id+",";
													       }
						 
												   }
												   

											   }
											  if(k== gdSet.getRowCount()-1&&k!=0)
											  {
												  list.add(tasktemp);  
											  }
									         }
	                                       for(int m=0;m<list.size();m++)
	                                       {
	                                    	   Task task=(Task) list.get(m);
//	                                    	   if(task.getIs_send().equals("1")&&task.getTask_type().equals("5"))
	                                    	   if(task.getTask_type().equals("5") && task.getIs_send().equals("1"))//已下达的
	                                    	   {
	                                    		 /**
	                           					 	* 如果删除成功需要删除录音任务时间单元的分钟数。
	                           					 */
	                                    	     this.updateUnitToAdc(task);  
	                                    	   }
	                                       }
		                           		}//查询录音任务结束
	                                   	gd1 = DbComponent.exeUpdate(sql.toString());
									} catch (DbException e) {
										// TODO Auto-generated catch block
										LogTool.fatal(e);
										  message+="站点:"+shortName+check_spec_task+" 任务删除失败！\r";
									} catch (GDSetException e) {
										// TODO Auto-generated catch block
										LogTool.fatal(e);
										 message+="站点:"+shortName+check_spec_task+" 任务删除失败！\r";
									}
		                           if(gd1){
		                           	count1++;
		                           }
		                       
//		      }
		 
		  }//循环所有站点结束for

		deleteTimeUnit0();
		ArrayList returnlist=new ArrayList();
		returnlist.add(count1);
		returnlist.add(count2);
		returnlist.add(message);
		return returnlist;
}
 /**
  * ************************************************
 
 * @Title: authSyn_Task
 
 * @Description: TODO(审核任务)
 
 * @param @param str
 * @param @return    设定文件
 
 * @author  刘斌
 
 * @return String    返回类型
 
 * @throws
 
 ************************************************
  */
 public Object authSyn_Task(ASObject obj) {
		String authList = (String)obj.get("authList");
		String userName = (String)obj.get("userName");
		String sql = "update radio_unify_task_tab t set t.authentic_status=1 , t.authentic_user='"+userName+"' where t.task_id in("+authList+")";
		try {
			DbComponent.exeUpdate(sql);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
			return new EXEException("", "审核任务信息失败！"+ e.getMessage(),null);
		}
		return true;
 }
 /**
  * 下发全部任务(当前查询条件查到的任务)
  * @param task
  * @return
  */
 public Object sendAllTask(Task task){
	 task.setStartRow(1);
	 task.setEndRow(200000);
	 ASObject asobj = (ASObject)this.queryAllTaskList(task);
	 ArrayList taskList = (ArrayList)asobj.get("resultList");
	 int tsize = taskList.size();
	 for(int i=(taskList.size()-1);i>-1;i--)
	 {
		 Task tasktemp=(Task) taskList.get(i);
		 if(!"".equals(tasktemp.getAuthentic_user()) && !tasktemp.getIs_send().equals("1")){
			 
		 } else{
			 taskList.remove(i);
		 }
	 }
	 if(taskList.size() == 0){
		 ArrayList returnlist = new ArrayList();
		 returnlist.add(0);
		 returnlist.add(tsize);
		 returnlist.add("选择下发任务中有已经下达过的任务或者未审核的任务，请重新选择下发任务。");
		 return returnlist;
	 }
	 Object resObj =  this.sendSyn_Task(taskList);
	 return resObj;
 }
 /**
  * ************************************************
 
 * @Title: checkTaskType
 
 * @Description: TODO(这里用一句话描述这个方法的作用)
 
 * @param @param str
 * @param @return    设定文件
 
 * @author  刘斌
 
 * @return String    返回类型
 
 * @throws
 
 ************************************************
  */
 public Object sendSyn_Task(ArrayList taskList) {
     int count1=0;  //记录删除任务成功的计数
     int count2=0;  //记录删除任务失败的计数
     String message="";//记录任务删除失败信息;

     ArrayList taskListZL = new ArrayList();
     //先循环一次把指标录音任务的质量任务拿出来，先下发
     for(int i=taskList.size()-1;i>=0;i--)
	 {
		 Task task=(Task) taskList.get(i);
		 if(task.getRecord_type().equals("质量")){
			 /**
			  * 这里不需要指定接受机。
			  */
//			 if(task.getEqu_code().equals("")){
//				 return new EXEException("",  task.getHead_code()+"_"+task.getFreq()+"质量录音任务必须指定一个接收机",null);
//			 }
			 taskListZL.add(task);
			 taskList.remove(i);
		 }
	     
	 }
     //下发指标录音任务的质量任务，目的保证质量任务占用接收机后，效果任务好判断
     for(int i=0;i<taskListZL.size();i++)
	 {
		 Task task=(Task) taskListZL.get(i);
		 ArrayList tempList = this.sendSingleTask(task);
		 count1 = count1 +(Integer)tempList.get(0);
		 count2 = count2 +(Integer)tempList.get(1);
		 message = message +(String)tempList.get(2);
         
	 }
     //下发其他任务
	 for(int i=0;i<taskList.size();i++)
	 {
		 Task task=(Task) taskList.get(i);
		 ArrayList tempList = this.sendSingleTask(task);
		 count1 = count1 +(Integer)tempList.get(0);
		 count2 = count2 +(Integer)tempList.get(1);
		 message = message +(String)tempList.get(2);
         
	 }
     ArrayList returnlist=new ArrayList();
     returnlist.add(count1);
     returnlist.add(count2);
     returnlist.add(message);
     return returnlist;
 }
 
 public ArrayList sendSingleTask(Task task){
	 ArrayList returnlist=new ArrayList();
	 int count1=0;  //记录删除任务成功的计数
     int count2=0;  //记录删除任务失败的计数
     String message="";//记录任务删除失败信息;
	 String task_type=task.getTask_type();
     String task_id=task.getTask_id();
     String send_user=task.getSend_user();
     String equCode=task.getEqu_code();//值为：“”，“R1”，“R2”， “”
     String headcode = task.getHead_code();
     String headtype = task.getHead_type_id();

         boolean sendOK=false;
       String task_typewithenglish=this.checkTaskType(task_type);
        if(task_typewithenglish.equals("StreamTask"))
       {
      	 try {
      			 sendOK=sendStreamTask(task,"Set"); 
      			 count1++;
//      		 }

			    String note = "["+ TimeMethod.get_nowdate()+"]任务下达成功";
		        updateSendToEquipmentState("radio_unify_task_tab",1,note,TimeMethod.get_nowdate(),task_id,send_user,task.getHead_code());
			 
      	 }  catch (Exception e) {
				  message += task.getShortname()+task_type+"下发失败,频率："+task.getFreq()+e.getMessage()+"\r";
				  count2++;

				   updateSendToEquipmentState("radio_unify_task_tab",2,"["+ TimeMethod.get_nowdate()+"]任务下发失败",TimeMethod.get_nowdate(),
						   task_id,send_user,headcode);
					
				LogTool.fatal(e);
			}
       }
      else if(task_typewithenglish.equals("QualityStreamTask")){
      	 try {
      		
      			
      		 if(task.getRecord_type().equals("质量")){
      			 
      			 task.setCheck_alarm("0");//质量
      		 } else if(task.getRecord_type().equals("效果")){
      			 task.setCheck_alarm("1");//效果
      		 }else
      		 {
      			 task.setCheck_alarm("3");//临时任务 
      		 }
      		
      	

	 				sendOK=sendQualtiyStreamTask(task,"Set");
	 				ArrayList subTaskList = task.getSubtask(); 
	 				//每个任务只能有一个subtask
	 				Subtask sub=(Subtask) subTaskList.get(0);
	 				/*‘
	 				 * 不需要更新时间单元。
	 				 */
	 				//boolean updateUnit=this.updateUnit(task,task.getHead_code(),sub.getEqu_code());
	 				count1++;
      	
      		 
			    String note = "["+ TimeMethod.get_nowdate()+"]任务下达成功";
		        updateSendToEquipmentState("radio_unify_task_tab",1,note,TimeMethod.get_nowdate(),task_id,send_user,task.getHead_code());

			}  catch (Exception e) {
				// TODO Auto-generated catch block
				  message += task.getShortname()+task_type+"下发失败,频率："+task.getFreq()+e.getMessage()+"\r";
				  count2++;
				   updateSendToEquipmentState("radio_unify_task_tab",2,"["+ TimeMethod.get_nowdate()+"]任务下发失败",TimeMethod.get_nowdate(),
						   task_id,send_user,headcode);
					
				LogTool.fatal(e);
			}
       }
        else if(task_typewithenglish.equals("QualityTask"))
       {
      	 try {
      	
				sendOK=sendQualityTask(task,"Set");
			     String note = "["+ TimeMethod.get_nowdate()+"]任务下达成功";
		         updateSendToEquipmentState("radio_unify_task_tab",1,note,TimeMethod.get_nowdate(),task_id,send_user,task.getHead_code());
				count1++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				  message+= task.getShortname()+task_type+"下发失败,频率："+task.getFreq()+e.getMessage()+"\r";
				  count2++;
				  updateSendToEquipmentState("radio_unify_task_tab",2,"["+ TimeMethod.get_nowdate()+"]任务下发失败",TimeMethod.get_nowdate(),
						  task_id,send_user,headcode);
					
				LogTool.fatal(e);
			}
       }
       else if(task_typewithenglish.equals("OffsetTask"))
       {
      	 
      	 try{
      	 sendOK=sendOffsetTask(task,"Set");
      		count1++;
      	
			     String note = "["+ TimeMethod.get_nowdate()+"]任务下达成功";
		         updateSendToEquipmentState("radio_unify_task_tab",1,note,TimeMethod.get_nowdate(),task_id,send_user,task.getHead_code());
           }  catch (Exception e) {
				// TODO Auto-generated catch block
				  message+= task.getShortname()+task_type+"下发失败,频率："+task.getFreq()+e.getMessage()+"\r";
				  count2++;
				  updateSendToEquipmentState("radio_unify_task_tab",2,"["+ TimeMethod.get_nowdate()+"]任务下发失败",TimeMethod.get_nowdate(),
						  task_id,send_user,headcode);
					
				LogTool.fatal(e);
			}
       }
       else
       {
      	 try{
      	 sendOK=sendSpectrumTask(task,"Set");
   		 count1++;
   	
		     String note = "["+ TimeMethod.get_nowdate()+"]任务下达成功";
	         updateSendToEquipmentState("radio_unify_task_tab",1,note,TimeMethod.get_nowdate(),task_id,send_user,task.getHead_code());
         }  catch (Exception e) {
			// TODO Auto-generated catch block
			  message = task.getShortname()+task_type+"下发失败,频率："+task.getFreq()+e.getMessage()+"\r";
			  count2++;
			  updateSendToEquipmentState("radio_unify_task_tab",2,"["+ TimeMethod.get_nowdate()+"]任务下发失败",TimeMethod.get_nowdate(),
					  task_id,send_user,headcode);
				
			LogTool.fatal(e);
		     }
       }
       
        returnlist.add(count1);
        returnlist.add(count2);
        returnlist.add(message);
        return returnlist;
 }
 /**
  * 更新任务管理下发后的状态
  * @param tabName String
  * @param val int
  * @return boolean
  */
 public boolean updateSendToEquipmentState(String tabName, int val,String note,String send_datetime, String taskid,String send_user,String headCode){
   boolean returnValue = true;
   String sql ="";

//   if(val==2){
// 	  sql = "update " + tabName + " set is_send = " + val + ",note='"+note+"',send_user='"+send_user+"',head_code='"+headCode+"' where task_id = '" + taskid + "' ";
//   }else
   sql = "update " + tabName + " set is_send = " + val + ",note='"+note+"',send_datetime='"+send_datetime+"',send_user='"+send_user+"',head_code='"+headCode+"' where task_id = '" + taskid + "' ";
   try {
       DbComponent.exeUpdate(sql);
   } catch(Exception e) {
       LogTool.fatal(e);
   } 
   return returnValue;
 }
 
/**
 * @throws GDSetException 
 * @throws DbException 
 * ************************************************

* @Title: checkUnit30

* @Description: TODO(用来检测任务27分钟)

* @param @param task
* @param @return    设定文件

* @author  刘斌

* @return boolean    返回类型

* @throws

************************************************
 */
 public boolean checkUnit30(Task task) throws DbException, GDSetException
 {
	 boolean  checkboo=false;
	 String valid_startdatetime=task.getValid_startdatetime();
	 String valid_enddatetime=task.getValid_enddatetime();
//     String headcode=task.getHead_code().substring(0,task.getHead_code().length()-1);//取code不带AB
     String headcode=task.getHead_code();//取code不带AB
     String head_type_id = task.getHead_type_id();
 	 ArrayList subTaskList = task.getSubtask(); 
 	boolean flagA=true;//站点带A的是否可以下发
 	boolean flagBR1=true;//站点带B的接收机R1是否可以下发。
 	boolean flagBR2=true;//站点带B的接收机R2是否可以下发。
	  //每个任务只能有一个subtask
	  Subtask sub=(Subtask) subTaskList.get(0);

	ArrayList cycTaskList = sub.getCyctask(); 

	ArrayList sinTAskList = sub.getSintask(); 
	 for(int i=0; i<sinTAskList.size(); i++) {
	        SingleSubTask sin=(SingleSubTask) sinTAskList.get(i);
	         
	           String starttime=sin.getStartdatetime();
	           if(starttime.equals(""))
	           {
	        	   continue;
	           }
	           String endtime=sin.getEnddatetime();
	           String recordlength=task.getRecordlength();
	           String stime=starttime.substring(12,starttime.length());
	           String etime=endtime.substring(12,endtime.length());
	           double count_minutes=this.getMinutes(recordlength);
	           ArrayList unittime=
	        	   TimeMethod.getUnitTime30(starttime, endtime, stime, etime, "all");
//	        String sqlxg = "";
	        String sqlzl = "";
	          ArrayList flag=new ArrayList();
	        for(int k=0;k<unittime.size();k++)
           {
        	   flag.add(unittime.get(k));
        	   if(k%900==0)
        	   {
//        		   sqlxg+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
//	        		" where t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+" and t.head_code like '"+headcode+"%' " +
//	        				" and t.record_type='效果' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//	        		" group by t.head_code,t.equ_code union ";   
        		   sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
	        		" where t.head_code like '"+headcode+"%' " + 
	        		"  and ( (t.record_type='效果' and t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+") " +
	        				"or (t.record_type='质量') ) and t.unittime in" +this.getSqlQueryTimeList(flag)+
	        		" group by t.head_code,t.equ_code union ";   
        		   flag=new ArrayList();
        	   }
           }
//	        sqlxg+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
//    		" where t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+" and t.head_code like '"+headcode+"%' " +
//    				" and t.record_type='效果' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//    		" group by t.head_code,t.equ_code ";
//	        sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
//       		" where  t.head_code like '"+headcode+"' " +
//       				" and t.record_type='质量' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//       		" group by t.head_code,t.equ_code ";
	        sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
    		" where t.head_code like '"+headcode+"%' " + 
    		"  and ( (t.record_type='效果' and t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+") " +
    				"or (t.record_type='质量') ) and t.unittime in" +this.getSqlQueryTimeList(flag)+
    		" group by t.head_code,t.equ_code union " +
    		"  select head.code head_code ,'R1' equ_code " +
    		"  from res_headend_tab head where head.code = '"+headcode+"A'"+" and head.is_online=0" +
    		"  union select head.code head_code ,'R2' equ_code" +
    		"  from res_headend_tab head  where head.code ='"+headcode+"B'"+" and head.is_online=0";
  
       
	        
//	        GDSet setxg = DbComponent.Query(sqlxg); 
	        GDSet setzl = DbComponent.Query(sqlzl); 
	      //先根据质量暂居的接收机判断一次
	        if(head_type_id.equals("101")){
	        	if(setzl.getRowCount()==2){//采集点一个接收机
	        		return false;
	        	} else{
	        		for(int j=0;j<setzl.getRowCount();j++)
		        	{
		        		String equ_codeflag=setzl.getString(j, "equ_code");
	        			if(equ_codeflag.equals("R1"))
	        			{
	        				flagBR1=false;
	        			}else  
	        			{
	        				flagBR2=false;	
	        			}
		        	}
	        	}
	        }else{
	        	flagBR1=false;
	        	//731的R1不能做指标，所以这个接收机不再做运行图任务了
		        if(setzl.getRowCount()==2)//查询出来二条说明任务已经不符合条件，不能下发。
		        {
		        	return false;
		        } else
		        {
		        	for(int j=0;j<setzl.getRowCount();j++)
		        	{
		        		String codeflag=setzl.getString(j, "head_code");
		        		String equ_codeflag=setzl.getString(j, "equ_code");
		        		if(codeflag.endsWith("A"))
		        		{
		        			flagA=false;
		        		}else if(codeflag.endsWith("B"))
		        		{

		        				flagBR2=false;	

		        		}
		        	}
		        }
	         }
//	      //再根据效果暂居的接收机判断一次
//	        if(head_type_id.equals("101")){
//	        	if(setxg.getRowCount()==2){//采集点一个接收机
//	        		return false;
//	        	} else{
//	        		for(int j=0;j<setxg.getRowCount();j++)
//		        	{
//		        		String equ_codeflag=setxg.getString(j, "equ_code");
//	        			if(equ_codeflag.equals("R1"))
//	        			{
//	        				flagBR1=false;
//	        			}else  
//	        			{
//	        				flagBR2=false;	
//	        			}
//		        	}
//	        	}
//	        }else{
//		        if(setxg.getRowCount()==3)//查询出来三条说明任务已经不符合条件，不能下发。
//		        {
//		        	return false;
//		        } else
//		        {
//		        	for(int j=0;j<setxg.getRowCount();j++)
//		        	{
//		        		String codeflag=setxg.getString(j, "head_code");
//		        		String equ_codeflag=setxg.getString(j, "equ_code");
//		        		if(codeflag.endsWith("A"))
//		        		{
//		        			flagA=false;
//		        		}else if(codeflag.endsWith("B"))
//		        		{
//		        			if(equ_codeflag.equals("R1"))
//		        			{
//		        				flagBR1=false;
//		        			}else  
//		        			{
//		        				flagBR2=false;	
//		        			}
//		        		}
//		        	}
//		        }
//	         }
	        
	      }
	       for(int i=0; i<cycTaskList.size(); i++) {
	          CycleSubTask cyc=(CycleSubTask) cycTaskList.get(i);
	       
	           String recordlength=task.getRecordlength();
	           String stime=cyc.getStarttime();
	           if(stime.equals(""))
	           {
	        	   continue;
	           }
	           String etime=cyc.getEndtime();
	           double count_minutes=this.getMinutes(recordlength);
	           ArrayList unittime=
	        	   TimeMethod.getUnitTime30(valid_startdatetime, valid_enddatetime, stime, etime, cyc.getDayofweek());
//	          String sqlxg = "";
	          String sqlzl = "";
	          ArrayList flag=new ArrayList();
	           for(int k=0;k<unittime.size();k++)
	           {
	        	   flag.add(unittime.get(k));
	        	   if(k%900==0)
	        	   {
//	        		   sqlxg+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
//		        		" where t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+" and t.head_code like '"+headcode+"%' " +
//		        				" and t.record_type='效果' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//		        		" group by t.head_code,t.equ_code union ";   
//	        		   sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
//		        		" where t.head_code like '"+headcode+"' " +
//		        				"  and t.record_type='质量' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//		        		" group by t.head_code,t.equ_code union ";   
	        		   sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
		        		" where t.head_code like '"+headcode+"%' " + 
		        		"  and ( (t.record_type='效果' and t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+") " +
		        				"or (t.record_type='质量') ) and t.unittime in" +this.getSqlQueryTimeList(flag)+
		        		" group by t.head_code,t.equ_code union ";   
	        		   flag=new ArrayList();
	        	   }
	           }
//	           sqlxg+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
//	        		" where t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+" and t.head_code like '"+headcode+"%' " +
//	        				" and t.record_type='效果' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//	        		" group by t.head_code,t.equ_code ";
//	           sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
//		       		" where  t.head_code like '"+headcode+"' " +
//		       				" and t.record_type='质量' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//		       		" group by t.head_code,t.equ_code ";
	           sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
       		" where t.head_code like '"+headcode+"%' " + 
       		"  and ( (t.record_type='效果' and t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+") " +
       				"or (t.record_type='质量') ) and t.unittime in" +this.getSqlQueryTimeList(flag)+
       	    		" group by t.head_code,t.equ_code union " +
    		"  select head.code head_code ,'R1' equ_code " +
    		"  from res_headend_tab head where head.code = '"+headcode+"A'"+ " and head.is_online=0 " +
    		"  union select head.code head_code ,'R2' equ_code" +
    		"  from res_headend_tab head  where head.code ='"+headcode+"B'"+"  and head.is_online=0 ";
	        
//	        GDSet setxg = DbComponent.Query(sqlxg); 
	        GDSet setzl = DbComponent.Query(sqlzl); 
	       
	        //先根据质量暂居的接收机判断一次
	        if(head_type_id.equals("101")){
	        	if(setzl.getRowCount()==2){//采集点一个接收机
	        		return false;
	        	} else{
	        		for(int j=0;j<setzl.getRowCount();j++)
		        	{
		        		String equ_codeflag=setzl.getString(j, "equ_code");
	        			if(equ_codeflag.equals("R1"))
	        			{
	        				flagBR1=false;
	        			}else  
	        			{
	        				flagBR2=false;	
	        			}
		        	}
	        	}
	        }else{
	        	flagBR1=false;
	        	//731的R1不能做指标，所以这个接收机不再做运行图任务了
		        if(setzl.getRowCount()==2)//查询出来二条说明任务已经不符合条件，不能下发。
		        {
		        	return false;
		        } else
		        {
		        	for(int j=0;j<setzl.getRowCount();j++)
		        	{
		        		String codeflag=setzl.getString(j, "head_code");
		        		String equ_codeflag=setzl.getString(j, "equ_code");
		        		if(codeflag.endsWith("A"))
		        		{
		        			flagA=false;
		        		}else if(codeflag.endsWith("B"))
		        		{
//		        			if(equ_codeflag.equals("R1"))
//		        			{
//		        				flagBR1=false;
//		        			}else  
//		        			{
		        				flagBR2=false;	
//		        			}
		        		}
		        	}
		        }
	         }
	        
//	        //先根据质量暂居的接收机判断一次
//	        if(head_type_id.equals("101")){
//	        	if(setxg.getRowCount()==2){//采集点一个接收机
//	        		return false;
//	        	} else{
//	        		for(int j=0;j<setxg.getRowCount();j++)
//		        	{
//		        		String equ_codeflag=setxg.getString(j, "equ_code");
//	        			if(equ_codeflag.equals("R1"))
//	        			{
//	        				flagBR1=false;
//	        			}else  
//	        			{
//	        				flagBR2=false;	
//	        			}
//		        	}
//	        	}
//	        }else{
//		        if(setxg.getRowCount()==3)//查询出来三条说明任务已经不符合条件，不能下发。
//		        {
//		        	return false;
//		        } else
//		        {
//		        	for(int j=0;j<setxg.getRowCount();j++)
//		        	{
//		        		String codeflag=setxg.getString(j, "head_code");
//		        		String equ_codeflag=setxg.getString(j, "equ_code");
//		        		if(codeflag.endsWith("A"))
//		        		{
//		        			flagA=false;
//		        		}else if(codeflag.endsWith("B"))
//		        		{
//		        			if(equ_codeflag.equals("R1"))
//		        			{
//		        				flagBR1=false;
//		        			}else  
//		        			{
//		        				flagBR2=false;	
//		        			}
//		        		}
//		        	}
//		        }
//	         }
	      }
	       
	       
	       if(head_type_id.equals("101")){
	    	   if(flagBR2==true)
				  {
					  sub.setEqu_code("R2");  
					  checkboo=true;
				  }
				  else if(flagBR1==true)
				  {
					  sub.setEqu_code("R1"); 
					  checkboo=true;
				  }
	       } else{
	    	   if(flagA==true)
				  {
					  task.setHead_code(headcode+"A");

					  task.setEqu_code("R1");
					  sub.setEqu_code("R1");
					  checkboo=true;
				  }else if(flagBR2==true)
				  {
					  task.setHead_code(headcode+"B");

					  task.setEqu_code("R2");  
					  sub.setEqu_code("R2");  
					  checkboo=true;
				  }
				  else if(flagBR1==true)
				  {
					  task.setHead_code(headcode+"B");
					  task.setEqu_code("R1");
					  sub.setEqu_code("R1"); 
					  checkboo=true;
				  }
	       }
		return checkboo;
}
 /**
  * ************************************************
 
 * @Title: getMinutes
 
 * @Description: TODO(根据时间取分钟数)
 
 * @param @param time 00:01:02
 * @param @return    设定文件
 
 * @author  刘斌
 
 * @return float    返回类型
 
 * @throws
 
 ************************************************
  */
 public double getMinutes(String time)
 {
	 String[] str=time.split(":");
	 String hour=str[0];
	 String minute=str[1];
	 String seconds=str[2];
	 
	 double minutes=Integer.parseInt(hour)*60+Integer.parseInt(minute)+Double.parseDouble(seconds)/60;
	  
     return minutes;
 }
 /**
  * ************************************************
 
 * @Title: getMinutes
 
 * @Description: TODO(根据list中的时间组合成sql可以查询的方式)
 
 * @param @param time 00:01:02
 * @param @return    设定文件
 
 * @author  刘斌
 
 * @return float    返回类型
 
 * @throws
 
 ************************************************
  */
 public String getSqlQueryTimeList(ArrayList list)
 {
	 String str="(";
	 for(int i=0;i<list.size();i++)
	 {
		 str+="'"+list.get(i)+"',";
	 }
	 if(list.size()==0){
		 str = "('')";
	 } else{
		 str=str.substring(0,str.length()-1)+")";
	 }
     return str;
 }
 /**
  * @throws DbException 
  * @throws GDSetException 
  * ************************************************

 * @Title: updateUnit

 * @Description: TODO(用来更新任务27分钟单元，主要用于减小单元分钟数)

 * @param @param task,head_code,equ_code
 * @param @return    设定文件

 * @author  刘斌

 * @return boolean    返回类型

 * @throws

 ************************************************
  */
 public boolean updateUnitToAdc(Task task) throws GDSetException, DbException
 {
 	 String valid_startdatetime=task.getValid_startdatetime();
 	 String valid_enddatetime=task.getValid_enddatetime();
      String head_code=task.getHead_code();
  	ArrayList subTaskList = task.getSubtask(); 
  
 	  //每个任务只能有一个subtask
 	  Subtask sub=(Subtask) subTaskList.get(0);
 	String equ_code=sub.getEqu_code();
 	ArrayList cycTaskList = sub.getCyctask(); 

 	ArrayList sinTAskList = sub.getSintask(); 
 	
 	if(task.getHead_type_id().equals("102")){//遥控站
			if(equ_code.equals("NI_R1")){
				equ_code = "R1";
			} else if(equ_code.equals("713_R1")){
				equ_code = "R1";
			} else if(equ_code.equals("713_R2")){
				equ_code = "R2";
			}
		 }
 	
 	 for(int i=0; i<sinTAskList.size(); i++) {
 	        SingleSubTask sin=(SingleSubTask) sinTAskList.get(i);
 	      
 	           String starttime=sin.getStartdatetime();
 	           if(starttime.equals(""))
 	           {
 	        	   continue;
 	           }
 	           String endtime=sin.getEnddatetime();
 	           String recordlength=task.getRecordlength();
 	           String stime=starttime.substring(12,starttime.length());
 	           String etime=endtime.substring(12,endtime.length());
 	           double count_minutes=this.getMinutes(recordlength);
 	            ArrayList unittime=
 	        	TimeMethod.getUnitTime30(starttime, endtime, stime, etime, "all");
 	            String sql="";
 	           ArrayList flag=new ArrayList();
 		        for(int k=0;k<unittime.size();k++)
 	           {
 	        	   flag.add(unittime.get(k));
 	        	   if(k%900==0)
 	        	   {
 	        		   sql+="select id,unittime from res_task_unit_tab t where t.head_code='"+head_code+"' and equ_code='"+equ_code+
 	        		   		"' and  RECORD_TYPE='"+task.getRecord_type()+"' and unittime in"+this.getSqlQueryTimeList(flag)+" union ";   
 	        		   flag=new ArrayList();
 	        	   }
 	           }
 		       sql+="select id,unittime from res_task_unit_tab t where t.head_code='"+head_code+"' and equ_code='"+equ_code+
		   		"' and  RECORD_TYPE='"+task.getRecord_type()+"' and unittime in"+this.getSqlQueryTimeList(flag);   
 		        
 		        GDSet set = DbComponent.Query(sql);
 			
// 	            String updateListsql="update res_task_unit_tab set count_minutes=count_minutes-"+count_minutes+" where  RECORD_TYPE='"+task.getRecord_type()+"' and id in";
 		      
 		        ArrayList haveUnittimelistId=new ArrayList();
 		       
 		        for(int j=0;j<set.getRowCount();j++)
 		        {
 		        	haveUnittimelistId.add(set.getString(j, "id"));
 		        	
 		        }
 		       flag=new ArrayList();
		        ArrayList updateList =new ArrayList();
		        for(int k=0;k<haveUnittimelistId.size();k++)
	           {
	        	   flag.add(haveUnittimelistId.get(k));
	        	   if(k%900==0)
	        	   {
	        		  updateList.add("update res_task_unit_tab set count_minutes=count_minutes-"+count_minutes+" " +
	        		  		"where RECORD_TYPE='"+task.getRecord_type()+"' and  id in"+this.getSqlQueryTimeList(flag)+" ");
	        		  flag=new ArrayList();
	        	   }
	           }
		        updateList.add("update res_task_unit_tab set count_minutes=count_minutes-"+count_minutes+" " +
				" where RECORD_TYPE='"+task.getRecord_type()+"' and id in"+this.getSqlQueryTimeList(flag)+" ");
		        String[] updateArr=new String[updateList.size()];
		        for(int k=0;k<updateList.size();k++)
		        {
		        	updateArr[k]=(String) updateList.get(k);
		        }
		        
 		       
 		        //更新现有的单元
		        if(updateArr.length>0)
		        	DbComponent.exeBatch(updateArr);
 		     
 	        }
 	       for(int i=0; i<cycTaskList.size(); i++) {
 	          CycleSubTask cyc=(CycleSubTask) cycTaskList.get(i);
 	    
 	           String recordlength=task.getRecordlength();
 	           String stime=cyc.getStarttime();
 	           if(stime.equals(""))
 	           {
 	        	   continue;
 	           }
 	           String etime=cyc.getEndtime();
 	           double count_minutes=this.getMinutes(recordlength);
 	            ArrayList unittime=
 	        	TimeMethod.getUnitTime30(valid_startdatetime, valid_enddatetime, stime, etime, cyc.getDayofweek());
 	           String sql="";
 	           ArrayList flag=new ArrayList();
 		        for(int k=0;k<unittime.size();k++)
 	           {
 	        	   flag.add(unittime.get(k));
 	        	   if(k%900==0)
 	        	   {
 	        		   sql+="select id,unittime from res_task_unit_tab t where t.head_code='"+head_code+"' and equ_code='"+equ_code+
 	        		   		"' and  RECORD_TYPE='"+task.getRecord_type()+"' and unittime in"+this.getSqlQueryTimeList(flag)+" union ";   
 	        		   flag=new ArrayList();
 	        	   }
 	           }
 		       sql+="select id,unittime from res_task_unit_tab t where t.head_code='"+head_code+"' and equ_code='"+equ_code+
		   		"' and  RECORD_TYPE='"+task.getRecord_type()+"' and unittime in"+this.getSqlQueryTimeList(flag);   
 		       
 		        GDSet set=set = DbComponent.Query(sql);
// 	            String updateListsql="update res_task_unit_tab set count_minutes=count_minutes-"+count_minutes+" where RECORD_TYPE='"+task.getRecord_type()+"' and  id in";
 		     
 		        ArrayList haveUnittimelistId=new ArrayList();
 		  
 		        for(int j=0;j<set.getRowCount();j++)
 		        {
 		        	haveUnittimelistId.add(set.getString(j, "id"));
 		     
 		        }

 		       flag=new ArrayList();
		        ArrayList updateList =new ArrayList();
		        for(int k=0;k<haveUnittimelistId.size();k++)
	           {
	        	   flag.add(haveUnittimelistId.get(k));
	        	   if(k%900==0)
	        	   {
	        		  updateList.add("update res_task_unit_tab set count_minutes=count_minutes-"+count_minutes+" " +
	        		  		"where RECORD_TYPE='"+task.getRecord_type()+"' and  id in"+this.getSqlQueryTimeList(flag)+" ");
	        		  flag=new ArrayList();
	        	   }
	           }
		        updateList.add("update res_task_unit_tab set count_minutes=count_minutes-"+count_minutes+" " +
				" where RECORD_TYPE='"+task.getRecord_type()+"' and id in"+this.getSqlQueryTimeList(flag)+" ");
		        String[] updateArr=new String[updateList.size()];
		        for(int k=0;k<updateList.size();k++)
		        {
		        	updateArr[k]=(String) updateList.get(k);
		        }
		        
 		   
 		        //先更新现有的单元
 		        if(updateArr.length>0)
 		        {
 		           DbComponent.exeBatch(updateArr);
 		        }
 		  
 	        }
 		return true;
 }
/**
 * @throws DbException 
 * @throws GDSetException 
 * ************************************************

* @Title: updateUnit

* @Description: TODO(用来更新任务27分钟单元主要用于新增和更新单元)

* @param @param task,head_code,equ_code
* @param @return    设定文件

* @author  刘斌

* @return boolean    返回类型

* @throws

************************************************
 */
public boolean updateUnit(Task task,String head_code,String equ_code) throws GDSetException, DbException
{
	 String valid_startdatetime=task.getValid_startdatetime();
	 String valid_enddatetime=task.getValid_enddatetime();
     String headcode=task.getHead_code();
 	ArrayList subTaskList = task.getSubtask(); 
 
	  //每个任务只能有一个subtask
	  Subtask sub=(Subtask) subTaskList.get(0);

	ArrayList cycTaskList = sub.getCyctask(); 

	ArrayList sinTAskList = sub.getSintask(); 
	 for(int i=0; i<sinTAskList.size(); i++) {
	        SingleSubTask sin=(SingleSubTask) sinTAskList.get(i);
	      
	           String starttime=sin.getStartdatetime();
	           if(starttime.equals(""))
	           {
	        	   continue;
	           }
	           String endtime=sin.getEnddatetime();
	           String recordlength=task.getRecordlength();
	           String stime=starttime.substring(12,starttime.length());
	           String etime=endtime.substring(12,endtime.length());
	           double count_minutes=this.getMinutes(recordlength);
	            ArrayList unittime=
	        	TimeMethod.getUnitTime30(starttime, endtime, stime, etime, "all");
	            String sql="";
 	           ArrayList flag=new ArrayList();
 		        for(int k=0;k<unittime.size();k++)
 	           {
 	        	   flag.add(unittime.get(k));
 	        	   if(k%900==0)
 	        	   {
 	        		   sql+="select id,unittime from res_task_unit_tab t where t.head_code='"+head_code+"' and equ_code='"+equ_code+
 	        		   		"' and  RECORD_TYPE='"+task.getRecord_type()+"' and unittime in"+this.getSqlQueryTimeList(flag)+" union ";   
 	        		   flag=new ArrayList();
 	        	   }
 	           }
 		       sql+="select id,unittime from res_task_unit_tab t where t.head_code='"+head_code+"' and equ_code='"+equ_code+
		   		"' and  RECORD_TYPE='"+task.getRecord_type()+"' and unittime in"+this.getSqlQueryTimeList(flag);   
		        GDSet set = DbComponent.Query(sql);
			
//	            String updateListsql="update res_task_unit_tab set count_minutes=count_minutes+"+count_minutes+" " +
//	            						" where RECORD_TYPE='"+task.getRecord_type()+"' and id in";
		        ArrayList insertListsql=new ArrayList();
		        ArrayList haveUnittimelistId=new ArrayList();
		        ArrayList haveUnittimelist=new ArrayList();
		        for(int j=0;j<set.getRowCount();j++)
		        {
		        	haveUnittimelistId.add(set.getString(j, "id"));
		        	haveUnittimelist.add(set.getString(j, "unittime"));
		        }
		        flag=new ArrayList();
		        ArrayList updateList =new ArrayList();
		        for(int k=0;k<haveUnittimelistId.size();k++)
 	           {
 	        	   flag.add(haveUnittimelistId.get(k));
 	        	   if(k%900==0)
 	        	   {
 	        		  updateList.add("update res_task_unit_tab set count_minutes=count_minutes+"+count_minutes+" " +
						" where RECORD_TYPE='"+task.getRecord_type()+"' and id in"+this.getSqlQueryTimeList(flag)+" ");
 	        		  flag=new ArrayList();
 	        	   }
 	           }
		        updateList.add("update res_task_unit_tab set count_minutes=count_minutes+"+count_minutes+" " +
				" where RECORD_TYPE='"+task.getRecord_type()+"' and id in"+this.getSqlQueryTimeList(flag)+" ");
		        String[] updateArr=new String[updateList.size()];
		        for(int k=0;k<updateList.size();k++)
		        {
		        	updateArr[k]=(String) updateList.get(k);
		        }
		        unittime.removeAll(haveUnittimelist);
		        //先更新现有的单元
		        if(updateArr.length>0)
			        DbComponent.exeBatch(updateArr); 
		        //插入没有的单元
		        for(int k=0;k<unittime.size();k++)
		        {
		        	String str="insert into  res_task_unit_tab values(res_resourse_seq.nextval,'"+head_code+"','"+unittime.get(k)+"',"+count_minutes+",'"+equ_code+"','"+task.getRecord_type()+"')";
		        	insertListsql.add(str);
		        }
		        String[] insertList=new String[insertListsql.size()];
		        for(int k=0;k<insertListsql.size();k++)
		        {
		        	insertList[k]=(String) insertListsql.get(k);
		        }
		        if(insertList.length>0)
		        DbComponent.exeBatch(insertList); 
	        }
	       for(int i=0; i<cycTaskList.size(); i++) {
	          CycleSubTask cyc=(CycleSubTask) cycTaskList.get(i);
	    
	           String recordlength=task.getRecordlength();
	           String stime=cyc.getStarttime();
	           if(stime.equals(""))
	           {
	        	   continue;
	           }
	           String etime=cyc.getEndtime();
	           double count_minutes=this.getMinutes(recordlength);
	            ArrayList unittime=
	        	TimeMethod.getUnitTime30(valid_startdatetime, valid_enddatetime, stime, etime, cyc.getDayofweek());
	            String sql="";
	 	           ArrayList flag=new ArrayList();
	 		        for(int k=0;k<unittime.size();k++)
	 	           {
	 	        	   flag.add(unittime.get(k));
	 	        	   if(k%900==0)
	 	        	   {
	 	        		   sql+="select id,unittime from res_task_unit_tab t where t.head_code='"+head_code+"' and equ_code='"+equ_code+
	 	        		   		"' and  RECORD_TYPE='"+task.getRecord_type()+"' and unittime in"+this.getSqlQueryTimeList(flag)+" union ";   
	 	        		   flag=new ArrayList();
	 	        	   }
	 	           }
	 		       sql+="select id,unittime from res_task_unit_tab t where t.head_code='"+head_code+"' and equ_code='"+equ_code+
			   		"' and  RECORD_TYPE='"+task.getRecord_type()+"' and unittime in"+this.getSqlQueryTimeList(flag);   
		        GDSet set=set = DbComponent.Query(sql);
			
//	            String updateListsql="update res_task_unit_tab set count_minutes=count_minutes+"+count_minutes+" where  RECORD_TYPE='"+task.getRecord_type()+"' and id in";
		        ArrayList insertListsql=new ArrayList();
		        ArrayList haveUnittimelistId=new ArrayList();
		        ArrayList haveUnittimelist=new ArrayList();
		        for(int j=0;j<set.getRowCount();j++)
		        {
		        	haveUnittimelistId.add(set.getString(j, "id"));
		        	haveUnittimelist.add(set.getString(j, "unittime"));
		        }
		        flag=new ArrayList();
		        ArrayList updateList =new ArrayList();
		        for(int k=0;k<haveUnittimelistId.size();k++)
 	           {
 	        	   flag.add(haveUnittimelistId.get(k));
 	        	   if(k%900==0)
 	        	   {
 	        		  updateList.add("update res_task_unit_tab set count_minutes=count_minutes+"+count_minutes+" " +
						" where RECORD_TYPE='"+task.getRecord_type()+"' and id in"+this.getSqlQueryTimeList(flag)+" ");
 	        		  flag=new ArrayList();
 	        	   }
 	           }
		        updateList.add("update res_task_unit_tab set count_minutes=count_minutes+"+count_minutes+" " +
				" where RECORD_TYPE='"+task.getRecord_type()+"' and id in"+this.getSqlQueryTimeList(flag)+" ");
		        String[] updateArr=new String[updateList.size()];
		        for(int k=0;k<updateList.size();k++)
		        {
		        	updateArr[k]=(String) updateList.get(k);
		        }
		        unittime.removeAll(haveUnittimelist);
		        //先更新现有的单元
		        if(updateArr.length>0)
			        DbComponent.exeBatch(updateArr); 
		        //插入没有的单元
		        for(int k=0;k<unittime.size();k++)
		        {
		        	String str="insert into  res_task_unit_tab values(res_resourse_seq.nextval,'"+head_code+"','"+unittime.get(k)+"',"+count_minutes+",'"+equ_code+"','"+task.getRecord_type()+"')";
		        	insertListsql.add(str);
		        }
		        String[] insertList=new String[insertListsql.size()];
		        for(int k=0;k<insertListsql.size();k++)
		        {
		        	insertList[k]=(String) insertListsql.get(k);
		        }
		        if(insertList.length>0)
		        DbComponent.exeBatch(insertList); 
	        }
		return true;
}
 
 public boolean sendSpectrumTask(Task task,String action) throws DeviceNotExistException, DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException {
	// TODO Auto-generated method stub
	 
	// TODO Auto-generated method stub

     ArrayList list = new ArrayList();
     ArrayList spectrumTaskSet = new ArrayList();
     ArrayList spectrumTask = new ArrayList();
     ArrayList singleReportTime = new ArrayList();
     ArrayList cycleReportTime = new ArrayList();
    
	
   	ArrayList subTaskList = task.getSubtask(); 
   
    //每个任务只能有一个subtask
    Subtask sub=(Subtask) subTaskList.get(0);
  
	ArrayList cycTaskList = sub.getCyctask(); 
	ArrayList sinTAskList =sub.getSintask(); 

	String equCode=sub.getEqu_code();//遥控站(102)值为：“”，“NI_R1”，“713_R1”，“713_R2”  采集点(101)值为“”，“R1”，“R2”
    String headcode = task.getHead_code();
    String headtype = task.getHead_type_id();//101采集点 102遥控站
//    if("101".equals(headtype)){
////    	if(!equCode.equals("")){
//		task.setEqu_code(equCode);
////    	sub.setEqu_code(equCode);
////    	}
//    } else if("102".equals(headtype)){
//		if(!equCode.equals("")){
//			if(equCode.equals("NI_R1")){
//				task.setHead_code(task.getHead_code()+"A");
//				task.setEqu_code("R1");
//				sub.setEqu_code("R1");
//			} else if(equCode.equals("713_R1")){
//				task.setHead_code(task.getHead_code()+"B");
//				task.setEqu_code("R1");
//				sub.setEqu_code("R1");
//			} else if(equCode.equals("713_R2")){
//				task.setHead_code(task.getHead_code()+"B");
//				task.setEqu_code("R2");
//				sub.setEqu_code("R2");
//			}
//					
//		} else{
//			task.setHead_code(task.getHead_code()+"A");
//			task.setEqu_code("R1");
//			sub.setEqu_code("R1");
//		}
//    }

    MsgSpectrumTaskSetCmd.SpectrumTaskSet s1678 =
       new  MsgSpectrumTaskSetCmd.SpectrumTaskSet(task.getEqu_code(),task.getTask_id(),action,task.getValid_startdatetime(),task.getValid_enddatetime(),task.getSamples_number(),checkUnit(task.getUnit()));
    spectrumTaskSet.add(s1678);

    MsgSpectrumTaskSetCmd.SpectrumTask s2678 =
       new MsgSpectrumTaskSetCmd.SpectrumTask(sub.getBand(),"0",sub.getStartfreq(),sub.getEndfreq(),sub.getStepfreq(),task.getSpectrum_sleeptime());
    spectrumTask.add(s2678);
    

     
      for(int i=0; i<sinTAskList.size(); i++) {
       SingleSubTask sin=(SingleSubTask) sinTAskList.get(i);
       if(sin.getStartdatetime().length()>0)
       {
  
              MsgSpectrumTaskSetCmd.SingleTask s4 =
                  new MsgSpectrumTaskSetCmd.SingleTask(sin.getStartdatetime(),sin.getEnddatetime(),
                   		sin.getReportmode(),sin.getReportinterval(),sin.getReportinterval(),sin.getExpiredays());
           singleReportTime.add(s4);
       }
       }
      for(int i=0; i<cycTaskList.size(); i++) {
         CycleSubTask cyc=(CycleSubTask) cycTaskList.get(i);
         if(cyc.getStarttime().length()>0)
         {   
             
             MsgSpectrumTaskSetCmd.CircleTask s3 =
                 new MsgSpectrumTaskSetCmd.CircleTask(cyc.getStarttime(),cyc.getEndtime(),cyc.getDayofweek(),
                		 cyc.getReportmode(),cyc.getReportinterval(),cyc.getReportinterval(),cyc.getExpiredays());
              cycleReportTime.add(s3);

         }
       }

         
      MsgSpectrumTaskSetCmd.TaskChannel cs =
          new MsgSpectrumTaskSetCmd.TaskChannel(spectrumTaskSet,spectrumTask,singleReportTime,cycleReportTime);

       list.add(cs);

      Security security = new Security();
      String priority="1";
      long msgPrio=0;
		  if ( task.getSend_user()!= null) {
		      try {
		          msgPrio = security.getMessagePriorityByUserName(task.getSend_user(), task.getIs_temporary().equals("临时任务")?2:1, 4, Integer.parseInt(task.getPriority()));
		          priority = new Long(msgPrio).toString();
		      } catch (Exception ex1) {

		      }
		  }
      
		  SpectrumAPI.msgSpectrumTaskSetCmd(task.getHead_code(), list, priority);
	return true;
}
 public boolean sendOffsetTask(Task task,String action) throws DeviceNotExistException, DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException {
	// TODO Auto-generated method stub
	  ArrayList list = new ArrayList();
      ArrayList offsetTaskSet = new ArrayList();
      ArrayList offsetTask = new ArrayList();
      ArrayList singleReportTime = new ArrayList();
      ArrayList cycleReportTime = new ArrayList();
      ArrayList qualityIndex  = new ArrayList();
      ArrayList channel = new ArrayList();
     
 	
    	ArrayList subTaskList =task.getSubtask(); 
    
     //每个任务只能有一个subtask
     Subtask sub=(Subtask) subTaskList.get(0);
   
 	ArrayList cycTaskList = sub.getCyctask(); 
 	ArrayList sinTAskList = sub.getSintask(); 
 
 	String equCode=sub.getEqu_code();//值为“”，“R1”，“R2”
    String headcode = task.getHead_code();
    String headtype = task.getHead_type_id();//101采集点 102遥控站
//    if("101".equals(headtype)){
////    	if(!equCode.equals("")){
//		task.setEqu_code(equCode);
////    	sub.setEqu_code(equCode);
////    	}
//    } else if("102".equals(headtype)){
//		if(!equCode.equals("")){
//			if(equCode.equals("NI_R1")){
//				task.setHead_code(task.getHead_code()+"A");
//				task.setEqu_code("R1");
//				sub.setEqu_code("R1");
//			} else if(equCode.equals("713_R1")){
//				task.setHead_code(task.getHead_code()+"B");
//				task.setEqu_code("R1");
//				sub.setEqu_code("R1");
//			} else if(equCode.equals("713_R2")){
//				task.setHead_code(task.getHead_code()+"B");
//				task.setEqu_code("R2");
//				sub.setEqu_code("R2");
//			}
//					
//		} else{
//			task.setHead_code(task.getHead_code()+"A");
//			task.setEqu_code("R1");
//			sub.setEqu_code("R1");
//		}
//    }

    
    MsgOffsetTaskSetCmd.OffsetTaskSet o1678 =
        new MsgOffsetTaskSetCmd.OffsetTaskSet(sub.getEqu_code(),task.getTask_id(),action,task.getValid_startdatetime()
                                   ,task.getValid_enddatetime(),task.getSamples_number(),checkUnit(task.getUnit()));
    offsetTaskSet.add(o1678);
     
    MsgOffsetTaskSetCmd.OffsetTask o2678 =
        new MsgOffsetTaskSetCmd.OffsetTask(task.getStream_sleeptime());
    offsetTask.add(o2678);

     
    MsgOffsetTaskSetCmd.Channel o3 =
        new MsgOffsetTaskSetCmd.Channel(sub.getFreq(),sub.getBand());
    channel.add(o3);
      
       for(int i=0; i<sinTAskList.size(); i++) {
        SingleSubTask sin=(SingleSubTask) sinTAskList.get(i);
        if(sin.getStartdatetime().length()>0)
        {
            
        	   MsgOffsetTaskSetCmd.SingleTask o5 =
                   new MsgOffsetTaskSetCmd.SingleTask(sin.getStartdatetime(),sin.getEnddatetime(),
                      		sin.getReportmode(),sin.getReportinterval(),sin.getReportinterval(),sin.getExpiredays());
               singleReportTime.add(o5);
        }
        }
       for(int i=0; i<cycTaskList.size(); i++) {
          CycleSubTask cyc=(CycleSubTask) cycTaskList.get(i);
          if(cyc.getStarttime().length()>0)
          {   
        	   MsgOffsetTaskSetCmd.CircleTask o4 =
                   new MsgOffsetTaskSetCmd.CircleTask(cyc.getStarttime(),cyc.getEndtime(),cyc.getDayofweek(),
                   		cyc.getReportmode(),cyc.getReportinterval(),cyc.getReportinterval(),cyc.getExpiredays());
              cycleReportTime.add(o4);

          }
        }

          
       MsgOffsetTaskSetCmd.TaskChannel os =
           new MsgOffsetTaskSetCmd.TaskChannel(offsetTaskSet, offsetTask,qualityIndex, channel, singleReportTime, cycleReportTime);

     list.add(os);

       Security security = new Security();
       String priority="1";
       long msgPrio=0;
		  if ( task.getSend_user()!= null) {
		      try {
		          msgPrio = security.getMessagePriorityByUserName(task.getSend_user(), task.getIs_temporary().equals("临时任务")?2:1, 4, Integer.parseInt(task.getPriority()));
		          priority = new Long(msgPrio).toString();
		      } catch (Exception ex1) {

		      }
		  }
       
		  OffsetAPI.msgOffsetTaskSetCmd(task.getHead_code(), list, priority);
	return true;
}
 
 public boolean sendQualtiyStreamTask(Task task,String action) throws DeviceNotExistException, DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
	  
	     ArrayList streamTaskSet = new ArrayList();
	     ArrayList streamTask = new ArrayList();
	     ArrayList streamSingleReportTime = new ArrayList();
	     ArrayList streamCycleReportTime = new ArrayList();
	     ArrayList streamChannel = new ArrayList();

	     ArrayList qualityTaskSet = new ArrayList();
	     ArrayList qualityIndex = new ArrayList();
	     ArrayList qualityTask = new ArrayList();
	     ArrayList qualityAlarm = new ArrayList();
	     ArrayList qualityChannel = new ArrayList();
	     ArrayList qualitySingleReportTime = new ArrayList();
	     ArrayList qualityCycleReportTime = new ArrayList();
	 
	    	ArrayList subTaskList = task.getSubtask(); 
	    
	     //每个任务只能有一个subtask
	     Subtask sub=(Subtask) subTaskList.get(0);
	  
	 	ArrayList cycTaskList = sub.getCyctask(); 
	 
	 	ArrayList sinTAskList = sub.getSintask(); 
	 	ArrayList qualityList = new ArrayList();
//	 	QualityTaskChannel
	 	ArrayList recordList = new ArrayList();
	    
	 	  MsgQualityStreamTaskSetCmd.StreamTaskSet sStreamTaskSet =
	          new MsgQualityStreamTaskSetCmd.StreamTaskSet(sub.getEqu_code(),task.getTask_id(),action,task.getValid_startdatetime()
	                       ,task.getValid_enddatetime(),sub.getBps(),"MPEG2");
	     streamTaskSet.add(sStreamTaskSet);
	     
	     MsgQualityStreamTaskSetCmd.StreamTask sStreamTask  =
	         new MsgQualityStreamTaskSetCmd.StreamTask(task.getStream_sleeptime());
	    streamTask.add(sStreamTask);
	     
	    
	      
	       for(int i=0; i<sinTAskList.size(); i++) {
	        SingleSubTask sin=(SingleSubTask) sinTAskList.get(i);
	        if(sin.getStartdatetime().length()>0)
	        {

	           MsgQualityStreamTaskSetCmd.StreamSingleTask s5 =
	               new MsgQualityStreamTaskSetCmd.StreamSingleTask(sin.getStartdatetime(),sin.getEnddatetime(),
	            		   sin.getReportmode(),sin.getReportinterval(),sin.getExpiredays(),task.getRecordlength());
	           streamSingleReportTime.add(s5);
	        }
	        }
	       for(int i=0; i<cycTaskList.size(); i++) {
	          CycleSubTask cyc=(CycleSubTask) cycTaskList.get(i);
	          if(cyc.getStarttime().length()>0)
	          {   
	           MsgQualityStreamTaskSetCmd.StreamCircleTask s4 =
	               new MsgQualityStreamTaskSetCmd.StreamCircleTask(cyc.getStarttime(),cyc.getEndtime(),cyc.getDayofweek(),
	            		   cyc.getReportmode(),cyc.getReportinterval(),cyc.getExpiredays(),task.getRecordlength());
	           streamCycleReportTime.add(s4);

	          }
	        }
	       MsgQualityStreamTaskSetCmd.StreamChannel s3 =
	           new MsgQualityStreamTaskSetCmd.StreamChannel(sub.getFreq(),sub.getBand(),sub.getStation_name(),sub.getLanguage());
	       streamChannel.add(s3);
	          
	          MsgQualityStreamTaskSetCmd.StreamTaskChannel ss =
	              new MsgQualityStreamTaskSetCmd.StreamTaskChannel(streamTaskSet,streamTask,
	            		  streamChannel,streamSingleReportTime,streamCycleReportTime);
	          recordList.add(ss);
/////////////////////////////////////////
	   
	          MsgQualityStreamTaskSetCmd.QualityTaskSet q1678 =
	              new MsgQualityStreamTaskSetCmd.QualityTaskSet(sub.getEqu_code(),task.getTask_id(),action,task.getValid_startdatetime()
	                                               ,task.getValid_enddatetime(),task.getSamples_number(),checkUnit(task.getUnit()));
	           qualityTaskSet.add(q1678);
	           if(task.getCheck_bandwidth().endsWith("1"))
	           {
	         	     MsgQualityStreamTaskSetCmd.QualityParam qband =
	                      new MsgQualityStreamTaskSetCmd.QualityParam("8","BandWidth");
	                   qualityIndex.add(qband);
	           }
	           if(task.getCheck_am_modulation().equals("1"))
	           {
	         	   MsgQualityStreamTaskSetCmd.QualityParam qam =
	                    new MsgQualityStreamTaskSetCmd.QualityParam("3","AM-Modulation");
	                 qualityIndex.add(qam);
	           }
	           if(task.getCheck_fm_modulation().equals("1"))
	           {

	               MsgQualityStreamTaskSetCmd.QualityParam qfm=
	                  new MsgQualityStreamTaskSetCmd.QualityParam("5","FM-ModulationMax");
	               qualityIndex.add(qfm);
	           }
	           if(task.getCheck_level().equals("1"))
	           {
	         	  MsgQualityStreamTaskSetCmd.QualityParam qlevel =
	                   new MsgQualityStreamTaskSetCmd.QualityParam("1","Level");
	                qualityIndex.add(qlevel);
	           }

	           if(task.getCheck_alarm().equals("1"))
	           {
	         	  MsgQualityStreamTaskSetCmd.AlarmParam qalarm =
	                   new MsgQualityStreamTaskSetCmd.AlarmParam("1","NoAlarm");
	         	  qualityAlarm.add(qalarm);
	           } else if(task.getCheck_alarm().equals("0")){
	        	   MsgQualityStreamTaskSetCmd.AlarmParam qalarm =
	                   new MsgQualityStreamTaskSetCmd.AlarmParam("0","Alarm");
	        	   qualityAlarm.add(qalarm);
	           }
	           else{
	        	   MsgQualityStreamTaskSetCmd.AlarmParam qalarm =
	                   new MsgQualityStreamTaskSetCmd.AlarmParam("3","Alarm");
	        	   qualityAlarm.add(qalarm);
	           }
	           MsgQualityStreamTaskSetCmd.QualityTask q3678 =
	               new MsgQualityStreamTaskSetCmd.QualityTask(task.getQuality_sleeptime());
	            qualityTask.add(q3678);
	            
	            for(int i=0; i<sinTAskList.size(); i++) {
	             SingleSubTask sin=(SingleSubTask) sinTAskList.get(i);
	             if(sin.getStartdatetime().length()>0)
	             {
	                MsgQualityStreamTaskSetCmd.QualitySingleTask q6 =
	                   new MsgQualityStreamTaskSetCmd.QualitySingleTask(sin.getStartdatetime(),sin.getEnddatetime(),
	                                              sin.getReportmode(),sin.getReportinterval(),sin.getReportinterval(),sin.getExpiredays());
	                qualitySingleReportTime.add(q6);
	             }
	             }
	            for(int i=0; i<cycTaskList.size(); i++) {
	               CycleSubTask cyc=(CycleSubTask) cycTaskList.get(i);
	               if(cyc.getStarttime().length()>0)
	               {
	                MsgQualityStreamTaskSetCmd.QualityCircleTask q5 =
	                   new MsgQualityStreamTaskSetCmd.QualityCircleTask(cyc.getStarttime(),cyc.getEndtime(),cyc.getDayofweek(),
	                                               cyc.getReportmode(),cyc.getReportinterval(),cyc.getReportinterval(),cyc.getExpiredays());
	                qualityCycleReportTime.add(q5);
	               }
	             }
	            MsgQualityStreamTaskSetCmd.QualityChannel qchannel =
	                new MsgQualityStreamTaskSetCmd.QualityChannel(sub.getFreq(),sub.getBand());

	            qualityChannel.add(qchannel);
	             MsgQualityStreamTaskSetCmd.QualityTaskChannel qs =
	                 new MsgQualityStreamTaskSetCmd.QualityTaskChannel(qualityTaskSet,qualityIndex,qualityTask,
	                		 qualityChannel,qualitySingleReportTime,qualityCycleReportTime,qualityAlarm);
	             qualityList.add(qs);   
	          
	          
	       Security security = new Security();
	       String priority="1";
	       long msgPrio=0;
			  if ( task.getSend_user()!= null) {
			      try {
			          msgPrio = security.getMessagePriorityByUserName(task.getSend_user(), task.getIs_temporary().equals("临时任务")?2:1, 4, Integer.parseInt(task.getPriority()));
			          priority = new Long(msgPrio).toString();
			      } catch (Exception ex1) {

			      }
			  }

			 	StreamAPI.msgQualityStreamTaskSetCmd(task.getHead_code(),qualityList,recordList, priority);
		return true;
	}
 
 public boolean sendStreamTask(Task task,String action) throws DeviceNotExistException, DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException {
	// TODO Auto-generated method stub
	// TODO Auto-generated method stub
     ArrayList list = new ArrayList();
  
     ArrayList streamTaskSet = new ArrayList();
     ArrayList streamTask = new ArrayList();
     ArrayList singleReportTime = new ArrayList();
     ArrayList cycleReportTime = new ArrayList();
     ArrayList channel = new ArrayList();
     
 
    	ArrayList subTaskList = task.getSubtask(); 
    
     //每个任务只能有一个subtask
     Subtask sub=(Subtask) subTaskList.get(0);
  
 	ArrayList cycTaskList = sub.getCyctask(); 
 
 	ArrayList sinTAskList = sub.getSintask(); 

    String equCode=sub.getEqu_code();//“R1”，“R2”
    String headcode = task.getHead_code();
    String headtype = task.getHead_type_id();//101采集点 102遥控站
//    if("101".equals(headtype)){
////    	if(!equCode.equals("")){
//		task.setEqu_code(equCode);
////    	sub.setEqu_code(equCode);
////    	}
//    } else if("102".equals(headtype)){
//		if(!equCode.equals("")){
//			if(equCode.equals("NI_R1")){
//				task.setHead_code(task.getHead_code()+"A");
//				task.setEqu_code("R1");
//				sub.setEqu_code("R1");
//			} else if(equCode.equals("713_R1")){
//				task.setHead_code(task.getHead_code()+"B");
//				task.setEqu_code("R1");
//				sub.setEqu_code("R1");
//			} else if(equCode.equals("713_R2")){
//				task.setHead_code(task.getHead_code()+"B");
//				task.setEqu_code("R2");
//				sub.setEqu_code("R2");
//			}
//					
//		} else{
//			task.setHead_code(task.getHead_code()+"A");
//			task.setEqu_code("R1");
//			sub.setEqu_code("R1");
//		}
//    }
    
 	  MsgStreamTaskSetCmd.StreamTaskSet sStreamTaskSet =
          new MsgStreamTaskSetCmd.StreamTaskSet(sub.getEqu_code(),task.getTask_id(),action,task.getValid_startdatetime()
                       ,task.getValid_enddatetime(),sub.getBps(),"MPEG2");
     streamTaskSet.add(sStreamTaskSet);
     
     MsgStreamTaskSetCmd.StreamTask sStreamTask  =
         new MsgStreamTaskSetCmd.StreamTask(task.getStream_sleeptime());
    streamTask.add(sStreamTask);
     
    
      
       for(int i=0; i<sinTAskList.size(); i++) {
        SingleSubTask sin=(SingleSubTask) sinTAskList.get(i);
        if(sin.getStartdatetime().length()>0)
        {

           
           MsgStreamTaskSetCmd.SingleTask s5 =
               new MsgStreamTaskSetCmd.SingleTask(sin.getStartdatetime(),sin.getEnddatetime(),
            		   sin.getReportmode(),sin.getReportinterval(),sin.getExpiredays(),task.getRecordlength());
            singleReportTime.add(s5);
        }
        }
       for(int i=0; i<cycTaskList.size(); i++) {
          CycleSubTask cyc=(CycleSubTask) cycTaskList.get(i);
          if(cyc.getStarttime().length()>0)
          {   
           MsgStreamTaskSetCmd.CircleTask s4 =
               new MsgStreamTaskSetCmd.CircleTask(cyc.getStarttime(),cyc.getEndtime(),cyc.getDayofweek(),
            		   cyc.getReportmode(),cyc.getReportinterval(),cyc.getExpiredays(),task.getRecordlength());
            cycleReportTime.add(s4);

          }
        }
       MsgStreamTaskSetCmd.Channel s3 =
           new MsgStreamTaskSetCmd.Channel(sub.getFreq(),sub.getBand(),sub.getStation_name(),sub.getLanguage());
          channel.add(s3);
          
          MsgStreamTaskSetCmd.TaskChannel ss =
              new MsgStreamTaskSetCmd.TaskChannel(streamTaskSet,streamTask,
                     channel,singleReportTime,cycleReportTime);
   list.add(ss);

       Security security = new Security();
       String priority="1";
       long msgPrio=0;
		  if ( task.getSend_user()!= null) {
		      try {
		          msgPrio = security.getMessagePriorityByUserName(task.getSend_user(), task.getIs_temporary().equals("临时任务")?2:1, 4, Integer.parseInt(task.getPriority()));
		          priority = new Long(msgPrio).toString();
		      } catch (Exception ex1) {

		      }
		  }
       
		  StreamAPI.msgStreamTaskSetCmd(task.getHead_code(), list, priority);
	return true;
}
 public boolean sendQualityTask(Task task,String action) throws DeviceNotExistException, DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException {
	// TODO Auto-generated method stub
     ArrayList list = new ArrayList();
     ArrayList qualityTaskSet = new ArrayList();
     ArrayList qualityIndex = new ArrayList();
     ArrayList qualityTask = new ArrayList();
     ArrayList singleReportTime = new ArrayList();
     ArrayList cycleReportTime = new ArrayList();
     ArrayList channel = new ArrayList();
     ArrayList qualityAlarm = new ArrayList();
 	
    	ArrayList subTaskList = task.getSubtask(); 
    
     //每个任务只能有一个subtask
     Subtask sub=(Subtask) subTaskList.get(0);
  
 	ArrayList cycTaskList =sub.getCyctask(); 
 	ArrayList sinTAskList = sub.getSintask(); 

 	String equCode=sub.getEqu_code();//遥控站(102)值为：“”，“NI_R1”，“713_R1”，“713_R2”  采集点(101)值为“”，“R1”，“R2”
    String headcode = task.getHead_code();
    String headtype = task.getHead_type_id();//101采集点 102遥控站
//    if("101".equals(headtype)){
////    	if(!equCode.equals("")){
//		task.setEqu_code(equCode);
////    	sub.setEqu_code(equCode);
////    	}
//    } else if("102".equals(headtype)){
//		if(!equCode.equals("")){
//			if(equCode.equals("NI_R1")){
//				task.setHead_code(task.getHead_code()+"A");
//				task.setEqu_code("R1");
//				sub.setEqu_code("R1");
//			} else if(equCode.equals("713_R1")){
//				task.setHead_code(task.getHead_code()+"B");
//				task.setEqu_code("R1");
//				sub.setEqu_code("R1");
//			} else if(equCode.equals("713_R2")){
//				task.setHead_code(task.getHead_code()+"B");
//				task.setEqu_code("R2");
//				sub.setEqu_code("R2");
//			}
//					
//		} else{
//			task.setHead_code(task.getHead_code()+"A");
//			task.setEqu_code("R1");
//			sub.setEqu_code("R1");
//		}
//    }
    
     MsgQualityReportTaskSetCmd.QualityTaskSet q1678 =
         new MsgQualityReportTaskSetCmd.QualityTaskSet(sub.getEqu_code(),task.getTask_id(),action,task.getValid_startdatetime()
                                          ,task.getValid_enddatetime(),task.getSamples_number(),checkUnit(task.getUnit()));
      qualityTaskSet.add(q1678);
      if(task.getCheck_bandwidth().endsWith("1"))
      {
    	     MsgQualityReportTaskSetCmd.QualityParam qband =
                 new MsgQualityReportTaskSetCmd.QualityParam("8","BandWidth");
              qualityIndex.add(qband);
      }
      if(task.getCheck_am_modulation().equals("1"))
      {
    	   MsgQualityReportTaskSetCmd.QualityParam qam =
               new MsgQualityReportTaskSetCmd.QualityParam("3","AM-Modulation");
            qualityIndex.add(qam);
      }
      if(task.getCheck_fm_modulation().equals("1"))
      {

          MsgQualityReportTaskSetCmd.QualityParam qfm=
             new MsgQualityReportTaskSetCmd.QualityParam("5","FM-ModulationMax");
          qualityIndex.add(qfm);
      }
      if(task.getCheck_level().equals("1"))
      {
    	  MsgQualityReportTaskSetCmd.QualityParam qlevel =
              new MsgQualityReportTaskSetCmd.QualityParam("1","Level");
           qualityIndex.add(qlevel);
      }
      
      if(task.getCheck_alarm().equals("1"))
      {
    	  MsgQualityReportTaskSetCmd.AlarmParam qalarm =
              new MsgQualityReportTaskSetCmd.AlarmParam("1","NoAlarm");
    	  qualityAlarm.add(qalarm);
      } else{
    	  MsgQualityReportTaskSetCmd.AlarmParam qalarm =
              new MsgQualityReportTaskSetCmd.AlarmParam("0","Alarm");
    	  qualityAlarm.add(qalarm);
      }
      
      MsgQualityReportTaskSetCmd.QualityTask q3678 =
          new MsgQualityReportTaskSetCmd.QualityTask(task.getQuality_sleeptime());
       qualityTask.add(q3678);
       
       for(int i=0; i<sinTAskList.size(); i++) {
        SingleSubTask sin=(SingleSubTask) sinTAskList.get(i);
        if(sin.getStartdatetime().length()>0)
        {
           MsgQualityReportTaskSetCmd.SingleTask q6 =
              new MsgQualityReportTaskSetCmd.SingleTask(sin.getStartdatetime(),sin.getEnddatetime(),
                                         sin.getReportmode(),sin.getReportinterval(),sin.getReportinterval(),sin.getExpiredays());
           singleReportTime.add(q6);
        }
        }
       for(int i=0; i<cycTaskList.size(); i++) {
          CycleSubTask cyc=(CycleSubTask) cycTaskList.get(i);
          if(cyc.getStarttime().length()>0)
          {
           MsgQualityReportTaskSetCmd.CircleTask q5 =
              new MsgQualityReportTaskSetCmd.CircleTask(cyc.getStarttime(),cyc.getEndtime(),cyc.getDayofweek(),
                                          cyc.getReportmode(),cyc.getReportinterval(),cyc.getReportinterval(),cyc.getExpiredays());
           cycleReportTime.add(q5);
          }
        }
       MsgQualityReportTaskSetCmd.Channel qchannel =
           new MsgQualityReportTaskSetCmd.Channel(sub.getFreq(),sub.getBand());

        channel.add(qchannel);
        MsgQualityReportTaskSetCmd.TaskChannel qs =
            new MsgQualityReportTaskSetCmd.TaskChannel(qualityTaskSet,qualityIndex,qualityTask,
                                                       channel,singleReportTime,cycleReportTime,qualityAlarm);
       list.add(qs);
       Security security = new Security();
       String priority="1";
       long msgPrio=0;
		  if ( task.getSend_user()!= null) {
		      try {
		          msgPrio = security.getMessagePriorityByUserName(task.getSend_user(), task.getIs_temporary().equals("临时任务")?2:1, 4, Integer.parseInt(task.getPriority()));
		          priority = new Long(msgPrio).toString();
		      } catch (Exception ex1) {

		      }
		  }
       QualityAPI.msgQualityReportTaskSetCmd(task.getHead_code(), list, priority);
	return true;
}
 /**
 * @throws DeviceReportException 
 * @throws DeviceTimedOutException 
 * @throws DeviceProcessException 
 * @throws DeviceFilterException 
 * @throws DeviceNotExistException 
  * ************************************************
 
 * @Title: getTaskStatus
 
 * @Description: TODO(查看任务执行状态。)
 
 * @param @param ASObject
 * @param @return    设定文件
 
 * @author  刘斌
 
 * @return String    返回类型
 
 * @throws
 
 ************************************************
  */
 public Object getTaskStatus(ASObject obj) {

	
              String code = (String)obj.get("stationCode");
              String taskid = (String)obj.get("task_id");
              Vector taskIds = new Vector();
              taskIds.add(taskid);
              String priority = "";

             String user_id=(String)obj.get("userid");

             long msgPrio = 0;
                      Security security = new Security();
                      if (user_id != null) {
                          try {
                              msgPrio = security.getMessagePriority(user_id, 3, 0, 0);
                             priority = new Long(msgPrio).toString();
                          } catch (Exception ex1) {
                                  ex1.printStackTrace();
                          }
                        }


         //	调用状态查询方法
                      String status_reply = "";
          // 解析返回结果
          Collection result;
		try {
			result = TaskAPI.msgTaskStatusQueryCmd(code, taskIds, priority);
		    Iterator i = result.iterator();
	          while (i.hasNext()) {
	            MsgTaskStatusQueryRes.TaskStatus taskStatus = (
	                MsgTaskStatusQueryRes.TaskStatus) i.next();
	           
	            status_reply = taskStatus.getStatus();
	            if (status_reply != null || !status_reply.equalsIgnoreCase("")) {
	            	status_reply = TaskMethod.task_status2desc(status_reply) + ".";
	            }
	          }
		} catch (DeviceNotExistException e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
			return new EXEException("",  e.getMessage(),null);
			
		} catch (DeviceFilterException e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
			return new EXEException("",  e.getMessage(),null);
			
		} catch (DeviceProcessException e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
			return new EXEException("",  e.getMessage(),null);
			
		} catch (DeviceTimedOutException e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
			return new EXEException("", e.getMessage(),null);
			
		} catch (DeviceReportException e) {
			// TODO Auto-generated catch block
			LogTool.fatal(e);
			return new EXEException("",  e.getMessage(),null);
			
		}
      
      
        return status_reply;

 }

 
 public String checkUnit(String str)
 {
 	if(str.equals("分钟"))
 	{
 		return "2";
 	}else if(str.equals("秒钟"))
 	{
 		return "1";
 	}else if(str.equals("小时"))
 	{
 		return "3";
 	}else
 	{
 		return str;
 	}
 }
public String checkTaskType(String str)
        {
        	if(str.equals("指标任务"))
        	{
        		return "QualityTask";
        	}else if(str.equals("录音任务"))
        	{
        		//现在没有单独的录音任务直接下发录音指标任务。
        		return "QualityStreamTask";
        		//return "StreamTask";
        	}else if(str.equals("频偏任务"))
        	{
        		return "OffsetTask";
        	}else if(str.equals("指标录音任务"))
        	{
        		return "QualityStreamTask";
        	}else
        	{
        		return "SpectrumTask";
        	}
        }

/**
 * 批量修改任务的执行设备
 * 操作项：
 */
public Object updateSelectTask(String  sql){
	
	
	


	
	try {
	     String[] sqlsStrings=sql.split(";");
		DbComponent.exeBatch(sqlsStrings);

		
	} catch (DbException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
		return new EXEException("", "批量更新任务执行设备失败,原因:"+e.getMessage(), "");
	} 
	
	return "批量更新任务执行设备成功";
	
}
    /**
     * 修改任务信息
     * @author 王福祥
     * @date 2012/09/15    
     * @param obj
     * @return
     */
   public Object updateTask(ASObject obj){
	   String res ="修改任务成功!";
	   String[] sql = new String[2];
	   String task_id = (String) obj.get("task_id");
	   String task_type = (String) obj.get("task_type");
	   String valid_starttime = (String) obj.get("valid_starttime");
	   String valid_endtime = (String) obj.get("valid_endtime");
	   String sleep_time_quality = (String) obj.get("sleep_time_quality");
	   String sleep_time_record = (String) obj.get("sleep_time_record");
	   String sleep_time_offset = (String) obj.get("sleep_time_offset");
	   String record_length = (String) obj.get("record_length");
	   String bps = (String) obj.get("bps");
	   String head_code= (String) obj.get("head_code");
	   String equ_code = (String) obj.get("equ_code");
	   String freq = (String) obj.get("freq");
	   String band = (String) obj.get("band");
	   //String station_name = (String) obj.get("station_name");
	   String language = (String) obj.get("language");

	   String sband = (String) obj.get("sband");
	   String ssleep_time = (String) obj.get("ssleep_time");
	   String sstepfreq = (String) obj.get("sstepfreq");
	   String sstartfreq = (String) obj.get("sstartfreq");
	   String sendfreq = (String) obj.get("sendfreq");

	   StringBuffer unifysql = new StringBuffer("update radio_unify_task_tab t set t.is_delete=0 ");
	   StringBuffer subsql = new StringBuffer(" update radio_sub_task_tab set is_delete=0 ");
	   if(task_type.equalsIgnoreCase("指标任务")){
		   unifysql.append(",t.quality_sleeptime='"+sleep_time_quality+"'");
	   } 
	   if(task_type.equalsIgnoreCase("录音任务")){
		   unifysql.append(",t.stream_sleeptime='"+sleep_time_record+"'");
		   if(bps!=null&&!bps.equalsIgnoreCase("")){
			   unifysql.append(",bps='"+bps+"'");
			   subsql.append(",bps='"+bps+"'");
		   }
//		   if(station_name!=null){
//			   subsql.append(",station_name='"+station_name+"'");
//		   }
		   if(language!=null){
			   subsql.append(",language='"+language+"'");
		   }
	   }
	   if(task_type.equalsIgnoreCase("频偏任务")){
		   unifysql.append(",t.offset_sleeptime='"+sleep_time_offset+"'");
	   }
	   if(task_type.equalsIgnoreCase("指标录音任务")){
		   unifysql.append(",t.quality_sleeptime='"+sleep_time_quality+"'");
		   unifysql.append(",t.stream_sleeptime='"+sleep_time_record+"'");
		   if(bps!=null&&!bps.equalsIgnoreCase("")){
			   unifysql.append(",bps='"+bps+"'");
			   subsql.append(",bps='"+bps+"'");
		   }
//		   if(station_name!=null){
//			   subsql.append(",station_name='"+station_name+"'");
//		   }
		   if(language!=null){
			   subsql.append(",language='"+language+"'");
		   }
	   }
	   if(task_type.equalsIgnoreCase("频谱任务")){
		   if(sband!="" && !sband.equals("")){
			   subsql.append(",band='"+sband+"'");
		   }
		   if(ssleep_time!="" && !ssleep_time.equals("")){
			   unifysql.append(",t.spectrum_sleeptime='"+ssleep_time+"'");
		   }
		   if(sstepfreq!="" && !sstepfreq.equals("")){
			   subsql.append(",stepfreq='"+sstepfreq+"'");
		   }
		   if(sstartfreq!="" && !sstartfreq.equals("")){
			   subsql.append(",startfreq='"+sstartfreq+"'");
		   }
		   if(sendfreq!="" && !sendfreq.equals("")){
			   subsql.append(",endfreq='"+sendfreq+"'");
		   }
	   }
	   if(record_length!=null&&!record_length.equalsIgnoreCase("")){
		   unifysql.append(",t.recordlength='"+record_length+"'");
	   }
	   if(freq!=null&&!freq.equalsIgnoreCase("")){
		   subsql.append(",freq='"+freq+"'");
	   } 
	   if(band!=null&&!band.equalsIgnoreCase("")){
		   subsql.append(",band='"+band+"'");
	   }
	   if(equ_code!=null){
		   unifysql.append(",t.equ_code='"+equ_code+"'");
		   subsql.append(",equ_code='"+equ_code+"'");
	   }
	   if(head_code!=null){
		   unifysql.append(",t.head_code='"+head_code+"'");
	   }
	   if(valid_starttime!=null&&!valid_starttime.equals("")){
		   unifysql.append(",valid_startdatetime='"+valid_starttime+"'");
	   }
	   if(valid_endtime!=null&&!valid_endtime.equals("")){
		   unifysql.append(",valid_enddatetime='"+valid_endtime+"'");
	   }
	   unifysql.append(" where  t.task_id='"+task_id+"'");
	   subsql.append(" where  task_id='"+task_id+"' ");
	   sql[0]=unifysql.toString();
	   sql[1]=subsql.toString();
	   try {
		DbComponent.exeBatch(sql);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		LogTool.fatal(e);
		return new EXEException("","修改任务异常："+e.getMessage(),"");
	}
	   return res;
   }
   
   /**
 * @throws GDSetException 
 * @throws DbException 
	 * ************************************************
	* @Title: queryAllTaskPlanList
	* @Description: TODO(查询任务计划列表 包括任务详细)
	* @param @param task
	* @param @return    设定文件
	* @author  张文
	* @return String    返回类型
	* @throws
	
	************************************************
	 */
	    public Object queryAllTaskPlanList(Task obj) throws DbException, GDSetException {
	    	ASObject objRes = new ASObject();
	         String code = obj.getHead_code();//前端code
	         String equCode = obj.getEqu_code();//equ code
	         String task_type = obj.getTask_type();//任务类型 
	         String start =obj.getStart();//开始时间
	         String end =obj.getEnd();//结束时间
	         String is_send = obj.getIs_send();//下发状态
	         String send_way=obj.getIs_temporary();//生成方式。
	          ArrayList<Task> list = new ArrayList();
	          GDSet gdSet = null;
		      GDSet gdSet2 = null;
		      String val = getVal(start);
		      String sarray[]=start.split(" ");   
		      String earray[]=end.split(" ");
	       String tasklistsql="";
	       String totalsql="";
             //0代表全部，1：指标任务； 2：录音任务； 3：频偏任务； 4频谱任务
	        
	        	 tasklistsql+="	 select task.task_id,sub.sub_task_id ,decode(task.task_type,1,'指标任务',2,'录音任务',3,'频偏任务',4,'频谱任务',5,'指标录音任务') task_type,cyc.time_id cyc_time_id,sin.time_id sin_time_id,task.equ_code spe_equ_task,task.head_code," +
	        	 		"task.valid_startdatetime,task.valid_enddatetime,task.check_level,decode(task.is_temporary,1,'临时任务',2,'日常任务') is_temporary," +
	        	 		"task.check_fm_modulation,task.check_am_modulation,task.check_fm_modulation_max,task.create_user,task.authentic_user,task.send_user," +
	        	 		"task.check_offset,task.check_bandwidth,decode(task.unit,1,'分钟',2,'秒钟',3,'小时') unit,task.samples_number,decode(task.is_send,0,'未下达',1,'已下达',2,'下达失败') is_send,task.recordlength," +
	        	 		"task.runplan_id,task.is_active,task.priority,task.quality_sleeptime,task.stream_sleeptime,task.offset_sleeptime," +
	        	 		"task.spectrum_sleeptime,sub.band,sub.bps,sub.startfreq,sub.endfreq,sub.stepfreq,sub.from_runplan,sub.freq,sub.equ_code," +
	        	 		"cyc.dayofweek,cyc.starttime cs,cyc.endtime cend,cyc.reportmode cm,cyc.reportinterval cr ,cyc.expiredays ce," +
	        	 		"sin.reportmode sm,sin.reportinterval sr ,sin.expiredays se,sin.startdatetime ss,sin.enddatetime send,task.note,task.batch_no" +
	        	 		"  ,sub.station_name,sub.language,task.record_type,runplan.start_time||'-'||runplan.end_time as play_time " + 		
			            " from  radio_unify_task_tab task,zres_runplan_tab runplan,"+
		                " radio_sub_task_tab sub, radio_task_time_cycle_tab cyc, radio_task_time_single_tab sin "+
			            " where task.task_id=sub.task_id and task.is_delete=0 and sub.sub_task_id=cyc.sub_task_id(+)"+
			            " and sub.sub_task_id=sin.sub_task_id(+)  and task.runplan_id = runplan.runplan_id(+) ";
	        	 
	        	 totalsql+=" select count(*) count from  radio_unify_task_tab task,zres_runplan_tab runplan,"+
		           "  radio_sub_task_tab sub, radio_task_time_cycle_tab cyc, radio_task_time_single_tab sin "+
		           " where task.task_id=sub.task_id and task.is_delete=0 and sub.sub_task_id=cyc.sub_task_id(+)"+
		           " and sub.sub_task_id=sin.sub_task_id(+) and task.runplan_id = runplan.runplan_id(+)  ";
	        	 if(!task_type.equals("0")){
	        		 tasklistsql+=" and task.task_type = "+ task_type;
	        		 totalsql+=" and task.task_type = "+ task_type;
	        	 }
	        
	        	 if(!code.equals(""))
	        	 {   
	        		 if(code.endsWith(",")){
	        			 code += "分AB";
	        		 }
	        		 String[] codeArr = code.split(",");//不分AB，分AB
	        		 if(!codeArr[1].equals("") && !codeArr[1].equals("分AB")){
		        		 tasklistsql+=" and task.head_code ='"+codeArr[1]+"'";
		        		 totalsql+=" and task.head_code ='"+codeArr[1]+"'";
	        		 } else{
	        			 tasklistsql+=" and ( (task.head_code='"+ codeArr[0]+"' and task.is_send<>1) or (task.head_code like '"+ codeArr[0]+"%'  ) )";
	        			 totalsql+=" and ( (task.head_code='"+ codeArr[0]+"' and task.is_send<>1) or (task.head_code like '"+ codeArr[0]+"%'  ) )";
	        		 }
//	        		 tasklistsql+=" and (('"+ code+"' like task.head_code || '%'  and task.is_send<>1) OR (task.head_code='"+ code+"' and task.is_send=1)) ";
//	        		 totalsql+=" and (('"+ code+"' like task.head_code || '%'  and task.is_send<>1) OR (task.head_code='"+ code+"' and task.is_send=1)) ";
	        	 }
		        	if(!equCode.equals("")){
		        		tasklistsql+=" and sub.equ_code='"+equCode+"'";
		        		totalsql+=" and sub.equ_code='"+equCode+"'";
		        	}
	        	if(!start.equals("")) {
		        		 tasklistsql+=" and valid_startdatetime<= to_date('"+ start+"','yyyy-mm-dd hh24:mi:ss') and task.valid_enddatetime>= to_date('"+ end+"','yyyy-mm-dd hh24:mi:ss') " +
		        		 		"and (( sin.startdatetime >=to_date('"+start+"', 'yyyy-mm-dd hh24:mi:ss') and sin.enddatetime <= to_date('"+end+"', 'yyyy-mm-dd hh24:mi:ss')) " +
		        		 		"or ( to_date(cyc.starttime, 'hh24:mi:ss') >=to_date('"+sarray[1]+"', 'hh24:mi:ss') " +
		        		 		"and  to_date(cyc.endtime, 'hh24:mi:ss')<= to_date('"+earray[1]+"', 'hh24:mi:ss') and ( cyc.dayofweek ='All' or cyc.dayofweek='"+val+"'))) ";
		        		    totalsql+=" and valid_startdatetime<= to_date('"+ start+"','yyyy-mm-dd hh24:mi:ss') and task.valid_enddatetime>= to_date('"+ end+"','yyyy-mm-dd hh24:mi:ss') " +
		        		    	"and (( sin.startdatetime >=to_date('"+start+"', 'yyyy-mm-dd hh24:mi:ss')and sin.enddatetime <= to_date('"+end+"', 'yyyy-mm-dd hh24:mi:ss')) " +
		        		    	"or ( to_date(cyc.starttime, 'hh24:mi:ss') >=to_date('"+sarray[1]+"', 'hh24:mi:ss') " +
		        		    	"and  to_date(cyc.endtime, 'hh24:mi:ss')<= to_date('"+earray[1]+"', 'hh24:mi:ss') and ( cyc.dayofweek ='All' or cyc.dayofweek='"+val+"'))) ";
			      }  
	        	
	        	 /**
	        	  * {label:"全部", data:"0"}, 
					{label:"未下发未审核", data:"1"}, 
					{label:"已审核未下发", data:"2"},
					{label:"已下发", data:"3"} ]);
	        	  */
	        	 if(!is_send.equals("0"))
	        	 {
	        		 if(is_send.equals("1"))
	        		 {
	        		 tasklistsql+=" and task.is_send= 0 and task.authentic_status=0 ";
	        		 totalsql+=" and task.is_send= 0 and task.authentic_status=0 ";
	        		 }
	        		 else if(is_send.equals("2"))
	        		 {
	        			 tasklistsql+=" and task.is_send= 0 and task.authentic_status=1 ";
	        			 totalsql+=" and task.is_send= 0 and task.authentic_status=1 ";
	        		 }
	        		 else if(is_send.equals("3"))
	        		 {
	        			 tasklistsql+=" and task.is_send= 2 ";
	        			 totalsql+=" and task.is_send= 2 ";
	        		 }
	        		 else if(is_send.equals("4"))
	        		 {
	        			 tasklistsql+=" and task.is_send= 3 ";
	        			 totalsql+=" and task.is_send= 3 ";
	        		 }
	        		 else if(is_send.equals("5"))
	        		 {
	        			 tasklistsql+=" and task.is_send= 1 ";
	        			 totalsql+=" and task.is_send= 1 ";
	        		 }
	        	 }
	        	 if(!send_way.equals("0"))
	        	 {
	        		 tasklistsql+=" and task.IS_TEMPORARY= "+ send_way+" ";
	        		 totalsql+=" and task.IS_TEMPORARY= "+ send_way+" ";
	        	 }
	        	 tasklistsql+=" order by task.task_id desc, sub.sub_task_id desc ";
	        	 String temp_sub_take_id="";
	        	 String temp_cyc_time_id="";
	        	 String temp_sin_time_id="";
	      
	         Task tasktemp=new Task();
	         
	      
	       
	         try {
	        	
	        	
	        			Integer startRow = (Integer)obj.getStartRow();
	        			Integer endRow = (Integer)obj.getEndRow();
	        		
				gdSet = DbComponent.Query(StringTool.pageSql(tasklistsql.toString(),startRow,endRow));
				gdSet2= DbComponent.Query(totalsql);
				ArrayList<Object> headlist = Common.headendListDistinct;
				HashMap<String,String> hhm = new HashMap<String,String>();
				for(int x=0; x<headlist.size(); x++){
					ResHeadendBean hbean = (ResHeadendBean)headlist.get(x);
					hhm.put(hbean.getCode_noab(),hbean.getShortname_noab());
				}
				   for (int i = 0; i < gdSet.getRowCount(); i++) {
					   Task task=new Task();
					    
					      ArrayList cycList =new ArrayList();
				          ArrayList sinList=new ArrayList();
				          ArrayList subList =new ArrayList();
					   if(i==0)
					   {
							  temp_sub_take_id=gdSet.getString(i, "sub_task_id")+",";
				        	  temp_cyc_time_id=gdSet.getString(i, "cyc_time_id")+",";
				        	  temp_sin_time_id=gdSet.getString(i, "sin_time_id")+",";  
				        	  String is_temporary = gdSet.getString(i, "is_temporary");
				        	  String record_type = gdSet.getString(i, "record_type");
				        	  is_temporary = is_temporary+(record_type.equals("")?"":"["+record_type+"]");
				        	  tasktemp.setIs_temporary(is_temporary);
				        	  tasktemp.setTask_id(gdSet.getString(i, "task_id"));
				        	  tasktemp.setEqu_code(gdSet.getString(i, "spe_equ_task"));
				        	  tasktemp.setBatch_no(gdSet.getString(i, "batch_no"));
				        	  tasktemp.setHead_code(gdSet.getString(i, "head_code"));
				        	  String shortname = Common.getHeadendByCode(gdSet.getString(i, "head_code"),"NOAB").getShortname_noab();
				        	  if(shortname.equals("")){
				        		  shortname = Common.getHeadendByCode(gdSet.getString(i, "head_code"),"").getShortname();
				        	  }
				        	  tasktemp.setShortname(shortname+"["+tasktemp.getHead_code()+"]");
				        	  tasktemp.setTask_type(gdSet.getString(i, "task_type"));
				        	  tasktemp.setValid_startdatetime(gdSet.getString(i, "valid_startdatetime"));
				        	  tasktemp.setValid_enddatetime(gdSet.getString(i, "valid_enddatetime"));
				        	  tasktemp.setCheck_level(gdSet.getString(i, "check_level"));
				        	  tasktemp.setCheck_fm_modulation(gdSet.getString(i,"check_fm_modulation"));
				        	  tasktemp.setCheck_am_modulation(gdSet.getString(i, "check_am_modulation"));
				        	  tasktemp.setCheck_bandwidth(gdSet.getString(i, "check_bandwidth"));
				        	  tasktemp.setCheck_offset(gdSet.getString(i, "check_offset"));
				        	  tasktemp.setUnit(gdSet.getString(i, "unit"));
				        	  tasktemp.setSamples_number(gdSet.getString(i, "samples_number"));
				        	  tasktemp.setIs_send(gdSet.getString(i, "is_send"));
				        	  tasktemp.setRecordlength(gdSet.getString(i, "recordlength"));
				        	  tasktemp.setRunplan_id(gdSet.getString(i, "runplan_id"));
				        	  tasktemp.setPriority(gdSet.getString(i, "priority"));
				        	  tasktemp.setQuality_sleeptime(gdSet.getString(i, "quality_sleeptime"));
				        	  tasktemp.setStream_sleeptime(gdSet.getString(i, "stream_sleeptime"));
				        	  tasktemp.setOffset_sleeptime(gdSet.getString(i, "offset_sleeptime"));
				        	  tasktemp.setSpectrum_sleeptime(gdSet.getString(i, "spectrum_sleeptime"));
				        	  tasktemp.setCreate_user(gdSet.getString(i, "create_user"));
				        	  tasktemp.setAuthentic_user(gdSet.getString(i, "authentic_user"));
				        	  tasktemp.setSend_user(gdSet.getString(i, "send_user"));
				        	  tasktemp.setFreq(gdSet.getString(i,"freq"));
				        	  tasktemp.setValidDate("自"+gdSet.getString(i, "valid_startdatetime")+"到"+gdSet.getString(i, "valid_enddatetime"));
				        	  String play_time = gdSet.getString(i, "play_time");
				        	  play_time = play_time.equals("-")?"":play_time;
				        	  tasktemp.setPlay_time(play_time);
				        	  tasktemp.setStation_name(gdSet.getString(i, "station_name"));
				        	  tasktemp.setLanguage(gdSet.getString(i, "language"));
				               
				               
				               
				               Subtask sub=new Subtask();
				               sub.setBand(gdSet.getString(i, "band"));
				               sub.setBps(gdSet.getString(i, "bps"));
				               sub.setEndfreq(gdSet.getString(i, "endfreq"));
				               sub.setEqu_code(gdSet.getString(i, "equ_code"));
				               sub.setFreq(gdSet.getString(i,"freq"));
				               sub.setFrom_runplan(gdSet.getString(i, "from_runplan"));
				               sub.setStartfreq(gdSet.getString(i, "startfreq"));
				               sub.setStepfreq(gdSet.getString(i, "stepfreq"));
				               sub.setSub_task_id(gdSet.getString(i, "sub_task_id"));
				               sub.setTask_id(gdSet.getString(i, "task_id"));
				               
				            
				           
				               
				               CycleSubTask cyc=new CycleSubTask();
				               String cyc_time_id=gdSet.getString(i, "cyc_time_id");
				               cyc.setDayofweek(gdSet.getString(i, "dayofweek"));
				               cyc.setEndtime(gdSet.getString(i, "cend"));
				               cyc.setStarttime(gdSet.getString(i, "cs"));
				               cyc.setExpiredays(gdSet.getString(i, "ce"));
				               cyc.setReportinterval(gdSet.getString(i, "cr"));
				               cyc.setReportmode(gdSet.getString(i, "cm"));
				               cyc.setSub_task_id(gdSet.getString(i, "sub_task_id"));
				               cyc.setTime_id(cyc_time_id);
				               cycList.add(cyc);
				               
				               SingleSubTask sin=new SingleSubTask();
				               String sin_time_id=gdSet.getString(i, "sin_time_id");
				               sin.setEnddatetime(gdSet.getString(i, "send"));
				               sin.setExpiredays(gdSet.getString(i, "se"));
				               sin.setReportinterval(gdSet.getString(i, "sr"));
				               sin.setReportmode(gdSet.getString(i, "sm"));
				               sin.setStartdatetime(gdSet.getString(i, "ss"));
				               sin.setSub_task_id(gdSet.getString(i, "sub_task_id"));
				               sin.setTime_id(sin_time_id);
				               sinList.add(sin);
				               
				               sub.setCyctask(cycList);
				               sub.setSintask(sinList);
				               subList.add(sub);
				               tasktemp.setSubtask(subList);
				               if(gdSet.getRowCount()==1)
				               {
				            	   list.add(tasktemp);   
				               }
				               
					   }else
					   {
						   if(!tasktemp.getTask_id().equals(gdSet.getString(i, "task_id")))
						   {
							   list.add(tasktemp);   
							   tasktemp=new Task();
							   
							   
							      temp_sub_take_id=gdSet.getString(i, "sub_task_id")+",";
					        	  temp_cyc_time_id=gdSet.getString(i, "cyc_time_id")+",";
					        	  temp_sin_time_id=gdSet.getString(i, "sin_time_id")+",";  
					        	  String is_temporary = gdSet.getString(i, "is_temporary");
					        	  String record_type = gdSet.getString(i, "record_type");
					        	  is_temporary = is_temporary+(record_type.equals("")?"":"["+record_type+"]");
					        	  tasktemp.setIs_temporary(is_temporary);
					        	  tasktemp.setTask_id(gdSet.getString(i, "task_id"));
					        	  tasktemp.setEqu_code(gdSet.getString(i, "equ_code"));
					        	  tasktemp.setHead_code(gdSet.getString(i, "head_code"));
					        	  String shortname = Common.getHeadendByCode(gdSet.getString(i, "head_code"),"NOAB").getShortname_noab();
					        	  if(shortname.equals("")){
					        		  shortname = Common.getHeadendByCode(gdSet.getString(i, "head_code"),"").getShortname();
					        	  }
					        	  tasktemp.setShortname(shortname+"["+tasktemp.getHead_code()+"]");
					        	  tasktemp.setTask_type(gdSet.getString(i, "task_type"));
					        	  tasktemp.setBatch_no(gdSet.getString(i, "batch_no"));
					        	  tasktemp.setValid_startdatetime(gdSet.getString(i, "valid_startdatetime"));
					        	  tasktemp.setValid_enddatetime(gdSet.getString(i, "valid_enddatetime"));
					        	  tasktemp.setCheck_level(gdSet.getString(i, "check_level"));
					        	  tasktemp.setCheck_fm_modulation(gdSet.getString(i,"check_fm_modulation"));
					        	  tasktemp.setCheck_am_modulation(gdSet.getString(i, "check_am_modulation"));
					        	  tasktemp.setCheck_bandwidth(gdSet.getString(i, "check_bandwidth"));
					        	  tasktemp.setCheck_offset(gdSet.getString(i, "check_offset"));
					        	  tasktemp.setUnit(gdSet.getString(i, "unit"));
					        	  tasktemp.setSamples_number(gdSet.getString(i, "samples_number"));
					        	  tasktemp.setIs_send(gdSet.getString(i, "is_send"));
					        	  tasktemp.setRecordlength(gdSet.getString(i, "recordlength"));
					        	  tasktemp.setRunplan_id(gdSet.getString(i, "runplan_id"));
					        	  tasktemp.setPriority(gdSet.getString(i, "priority"));
					        	  tasktemp.setQuality_sleeptime(gdSet.getString(i, "quality_sleeptime"));
					        	  tasktemp.setStream_sleeptime(gdSet.getString(i, "stream_sleeptime"));
					        	  tasktemp.setOffset_sleeptime(gdSet.getString(i, "offset_sleeptime"));
					        	  tasktemp.setSpectrum_sleeptime(gdSet.getString(i, "spectrum_sleeptime"));
					        	  tasktemp.setCreate_user(gdSet.getString(i, "create_user"));
					        	  tasktemp.setAuthentic_user(gdSet.getString(i, "authentic_user"));
					        	  tasktemp.setSend_user(gdSet.getString(i, "send_user"));
					        	  tasktemp.setFreq(gdSet.getString(i,"freq"));
					        	  tasktemp.setValidDate("自"+gdSet.getString(i, "valid_startdatetime")+"到"+gdSet.getString(i, "valid_enddatetime"));
					        	  String play_time = gdSet.getString(i, "play_time");
					        	  play_time = play_time.equals("-")?"":play_time;
					        	  tasktemp.setPlay_time(play_time);
					        	  tasktemp.setStation_name(gdSet.getString(i, "station_name"));
					        	  tasktemp.setLanguage(gdSet.getString(i, "language"));
					               
					               
					               
					               Subtask sub=new Subtask();
					               sub.setBand(gdSet.getString(i, "band"));
					               sub.setBps(gdSet.getString(i, "bps"));
					               sub.setEndfreq(gdSet.getString(i, "endfreq"));
					               sub.setEqu_code(gdSet.getString(i, "equ_code"));
					               sub.setFreq(gdSet.getString(i,"freq"));
					               sub.setFrom_runplan(gdSet.getString(i, "from_runplan"));
					               sub.setStartfreq(gdSet.getString(i, "startfreq"));
					               sub.setStepfreq(gdSet.getString(i, "stepfreq"));
					               sub.setSub_task_id(gdSet.getString(i, "sub_task_id"));
					               sub.setTask_id(gdSet.getString(i, "task_id"));
					               
					           
					               
					               CycleSubTask cyc=new CycleSubTask();
					               String cyc_time_id=gdSet.getString(i, "cyc_time_id");
					               cyc.setDayofweek(gdSet.getString(i, "dayofweek"));
					               cyc.setEndtime(gdSet.getString(i, "cend"));
					               cyc.setStarttime(gdSet.getString(i, "cs"));
					               cyc.setExpiredays(gdSet.getString(i, "ce"));
					               cyc.setReportinterval(gdSet.getString(i, "cr"));
					               cyc.setReportmode(gdSet.getString(i, "cm"));
					               cyc.setSub_task_id(gdSet.getString(i, "sub_task_id"));
					               cyc.setTime_id(cyc_time_id);
					               cycList.add(cyc);
					               
					               SingleSubTask sin=new SingleSubTask();
					               String sin_time_id=gdSet.getString(i, "sin_time_id");
					               sin.setEnddatetime(gdSet.getString(i, "send"));
					               sin.setExpiredays(gdSet.getString(i, "se"));
					               sin.setReportinterval(gdSet.getString(i, "sr"));
					               sin.setReportmode(gdSet.getString(i, "sm"));
					               sin.setStartdatetime(gdSet.getString(i, "ss"));
					               sin.setSub_task_id(gdSet.getString(i, "sub_task_id"));
					               sin.setTime_id(sin_time_id);
					               sinList.add(sin);
					               
					               sub.setCyctask(cycList);
					               sub.setSintask(sinList);
					               subList.add(sub);
					               tasktemp.setSubtask(subList);
			               
						   }else
						   {							   
							   
							   String cyc_time_id=gdSet.getString(i, "cyc_time_id"); 
					        	  if(temp_cyc_time_id.indexOf(cyc_time_id+",")==-1)
					        	  {
					        	        
						               CycleSubTask cyc=new CycleSubTask();
						               cyc.setDayofweek(gdSet.getString(i, "dayofweek"));
						               cyc.setEndtime(gdSet.getString(i, "cend"));
						               cyc.setStarttime(gdSet.getString(i, "cs"));
						               cyc.setExpiredays(gdSet.getString(i, "ce"));
						               cyc.setReportinterval(gdSet.getString(i, "cr"));
						               cyc.setReportmode(gdSet.getString(i, "cm"));
						               cyc.setSub_task_id(gdSet.getString(i, "sub_task_id"));
						               cyc.setTime_id(cyc_time_id);
						               tasktemp.getSubtask().get(0).getCyctask().add(cyc);
						               temp_cyc_time_id+=cyc_time_id+",";

					        	  }
							       String sin_time_id=gdSet.getString(i, "sin_time_id");
							       
							       if(temp_sin_time_id.indexOf(sin_time_id+",")==-1)
							       {
							           
						               SingleSubTask sin=new SingleSubTask();
						      
						               sin.setEnddatetime(gdSet.getString(i, "send"));
						               sin.setExpiredays(gdSet.getString(i, "se"));
						               sin.setReportinterval(gdSet.getString(i, "sr"));
						               sin.setReportmode(gdSet.getString(i, "sm"));
						               sin.setStartdatetime(gdSet.getString(i, "ss"));
						               sin.setSub_task_id(gdSet.getString(i, "sub_task_id"));
						               sin.setTime_id(sin_time_id);
						               tasktemp.getSubtask().get(0).getSintask().add(sin);
						               temp_sin_time_id+=sin_time_id+",";
							       }

						   }
						   

					   }
					  if(i== gdSet.getRowCount()-1&&i!=0)
					  {
						  list.add(tasktemp);  
					  }
			         }
				   
				   
			} catch (DbException e) {
				// TODO Auto-generated catch block
				LogTool.fatal(e);
				return new EXEException("", "查询任务信息失败！"+ e.getMessage(),null);
			} catch (GDSetException e) {
				// TODO Auto-generated catch block
				LogTool.fatal(e);
				return new EXEException("", "查询任务信息失败！"+ e.getMessage(),null);
			}
           
			objRes.put("resultList", list);
			try {
				objRes.put("resultTotal", gdSet2.getString(0, "count"));
			} catch (GDSetException e) {
				// TODO Auto-generated catch block
				LogTool.fatal(e);
				return new EXEException("", "查询任务信息失败！"+ e.getMessage(),null);
			}
	         return objRes;
	    }
	    
       private static String getVal(String start) throws DbException, GDSetException{
			
			String sql = " select to_char(to_date('"+start+"', 'yyyy-mm-dd hh24:mi:ss'),'day') day from dual";
			
			String val="";
			
			GDSet set = DbComponent.Query(sql);
			
			String day = set.getString(0, "day");
			
			if(day.equals("星期一")){
				
				val="1";
			}else if(day.equals("星期二")){
				
				val="2";
			}else if(day.equals("星期三")){
				
				val="3";
			}else if(day.equals("星期四")){
				
				val="4";
			}else if(day.equals("星期五")){
				
				val="5";
			}else if(day.equals("星期六")){
				
				val="6";
			}
			
			return val;
			
		}
}
