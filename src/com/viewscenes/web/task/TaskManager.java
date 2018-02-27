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
	* @Description: TODO(��ѯ�����б� ����������ϸ)
	* @param @param task
	* @param @return    �趨�ļ�
	* @author  ����
	* @return String    ��������
	* @throws
	
	************************************************
	 */
	    public Object queryAllTaskList(Task obj) {
	    	ASObject objRes = new ASObject();
	         String code = obj.getHead_code();//ǰ��code
	         String equCode = obj.getEqu_code();//equ code
	         String task_type = obj.getTask_type();//�������� 
	         String valid_end_datetime =obj.getValid_enddatetime();//��Ч��ʼʱ��
	         String valid_start_datetime = obj.getValid_startdatetime();//��Ч����ʱ��
	         String is_send = obj.getIs_send();//�·�״̬
	         String send_way=obj.getIs_temporary();//���ɷ�ʽ��
	           ArrayList<Task> list = new ArrayList();
	           GDSet gdSet = null;
		         GDSet gdSet2 = null;

	       String tasklistsql="";
	       String totalsql="select count(*) count from radio_unify_task_tab task ,zres_runplan_tab runplan," +
	       		"radio_sub_task_tab sub, radio_task_time_cycle_tab cyc, radio_task_time_single_tab sin "+
           " where task.task_id=sub.task_id and task.is_delete=0 and sub.sub_task_id=cyc.sub_task_id(+)"+
           " and sub.sub_task_id=sin.sub_task_id(+)  and task.runplan_id = runplan.runplan_id(+) ";
               //0����ȫ����1��ָ������ 2��¼������ 3��Ƶƫ���� 4Ƶ������
	        
//	       tasklistsql+="	 select distinct task.task_id,sub.sub_task_id ,decode(task.task_type,1,'ָ������',2,'¼������',3,'Ƶƫ����',4,'Ƶ������',5,'ָ��¼������') task_type,cyc.time_id cyc_time_id,sin.time_id sin_time_id,decode(head.type_id,102,substr(head.shortname,0,length(head.shortname)-1)) as shortname,task.equ_code spe_equ_task,task.head_code," +
//	 		"task.valid_startdatetime,task.valid_enddatetime,task.check_level,decode(task.is_temporary,1,'��ʱ����',2,'�ճ�����') is_temporary," +
//	 		"task.check_fm_modulation,task.check_am_modulation,task.check_fm_modulation_max,task.create_user,task.authentic_user,task.send_user," +
//	 		"task.check_offset,task.check_bandwidth,decode(task.unit,1,'����',2,'����',3,'Сʱ') unit,task.samples_number,decode(task.is_send,0,'δ�´�',1,'���´�',2,'�´�ʧ��') is_send,task.recordlength," +
//	 		"task.runplan_id,task.is_active,task.priority,task.quality_sleeptime,task.stream_sleeptime,task.offset_sleeptime," +
//	 		"task.spectrum_sleeptime,sub.band,sub.bps,sub.startfreq,sub.endfreq,sub.stepfreq,sub.from_runplan,sub.freq,sub.equ_code," +
//	 		"cyc.dayofweek,cyc.starttime cs,cyc.endtime cend,cyc.reportmode cm,cyc.reportinterval cr ,cyc.expiredays ce,task.check_alarm," +
//	 		"sin.reportmode sm,sin.reportinterval sr ,sin.expiredays se,sin.startdatetime ss,sin.enddatetime send,task.note,task.batch_no" + 		
//  " from  radio_unify_task_tab task,"+
//  " res_headend_tab head, radio_sub_task_tab sub, radio_task_time_cycle_tab cyc, radio_task_time_single_tab sin "+
//  " where head.code  like task.head_code||'%' and task.task_id=sub.task_id and task.is_delete=0 and sub.sub_task_id=cyc.sub_task_id(+)"+
//  " and sub.sub_task_id=sin.sub_task_id(+)  ";
	       
	        	 tasklistsql+="	 select task.task_id,sub.sub_task_id ,decode(task.task_type,1,'ָ������',2,'¼������',3,'Ƶƫ����',4,'Ƶ������',5,'ָ��¼������') task_type,cyc.time_id cyc_time_id,sin.time_id sin_time_id,task.equ_code spe_equ_task,task.head_code," +
	        	 		"task.valid_startdatetime,task.valid_enddatetime,task.check_level,decode(task.is_temporary,1,'��ʱ����',2,'�ճ�����') is_temporary," +
	        	 		"task.check_fm_modulation,task.check_am_modulation,task.check_fm_modulation_max,task.create_user,task.authentic_user,task.send_user," +
	        	 		"task.check_offset,task.check_bandwidth,decode(task.unit,2,'����',1,'����',3,'Сʱ') unit,task.samples_number,decode(task.is_send,0,'δ�´�',1,'���´�',2,'�´�ʧ��') is_send,task.recordlength," +
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
	        			 code += "��AB";
	        		 }
	        		 String[] codeArr = code.split(",");//����AB����AB
	        		 if(!codeArr[1].equals("") && !codeArr[1].equals("��AB")){
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
	        	  * {label:"ȫ��", data:"0"}, 
					{label:"δ�·�δ���", data:"1"}, 
					{label:"�����δ�·�", data:"2"},
					{label:"���·�", data:"3"} ]);
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
				        	  tasktemp.setValidDate("��"+gdSet.getString(i, "valid_startdatetime")+"��"+gdSet.getString(i, "valid_enddatetime"));
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
					        	  tasktemp.setValidDate("��"+gdSet.getString(i, "valid_startdatetime")+"��"+gdSet.getString(i, "valid_enddatetime"));
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
				return new EXEException("", "��ѯ������Ϣʧ�ܣ�"+ e.getMessage(),null);
			} catch (GDSetException e) {
				// TODO Auto-generated catch block
				LogTool.fatal(e);
				return new EXEException("", "��ѯ������Ϣʧ�ܣ�"+ e.getMessage(),null);
			}
             
			
		
	         return objRes;
	    }
	  /**
	   * ************************************************
	  
	  * @Title: AddSyn_Task
	  
	  * @Description: TODO(��������)
	  
	  * @param @param msg
	  * @param @return
	  * @param @throws DbException
	  * @param @throws GDSetException
	  * @param @throws SQLException    �趨�ļ�
	  
	  * @author  ����
	  
	  * @return String    ��������
	  
	  * @throws
	  
	  ************************************************
	   */
	public Object AddSyn_Task(ArrayList<Task> totallist) {

		int count = 0;// ��¼�ܹ����ɵ�����������
		for (int x = 0; x < totallist.size(); x++) {
			Task task = totallist.get(x);
			// ����¼����Ƶƫ��ָ��ĸ�ʽ���ݺ�̨���� ���磺1,1,1
			String task_types = task.getTask_type();
			if (task_types.equals("4"))// ���Ƶ������
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
			else if (task_types.equals("5"))// ���¼��ָ������
			{
				ArrayList<Subtask> list = task.getSubtask();

				for (int i = 0; i < list.size(); i++) {
					Subtask sub = (Subtask) list.get(i);
					try {
						//�����������ȼ���Ϊ�����ߵ�
						if(task.getRecord_type().equals("����")){
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
			else// ����ۺ�����
			{
				String[] types = task_types.split(",");
				String stream_task = types[0];
				String offset_task = types[1];
				String quality_task = types[2];

				// ѭ������ǰ̨���ݹ��ĵ�����Ƶ������һ��Ƶ�ʶ�Ӧһ������
				ArrayList<Subtask> list = task.getSubtask();

				for (int i = 0; i < list.size(); i++) {
					Subtask sub = (Subtask) list.get(i);
					if (stream_task.equals("1"))// ����¼������
					{
                       task.setRecord_type("��ʱ");
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

					if (offset_task.equals("1"))// ����Ƶƫ����
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
					if (quality_task.equals("1"))// ����ָ������
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
        	  
      	
			
	        	 //ѭ������ǰ̨���ݹ��ĵ�����Ƶ������һ��Ƶ�ʶ�Ӧһ������
          
                    
			                    int k=0;
			                    sql = "select radio_task_seq.nextval as task_id from dual";
			                   
									gdSet = DbComponent.Query(sql);
							
			                    String task_id = null;
							
									task_id = gdSet.getString(0, "task_id");
								
			                
			                	 String runplan_id=sub.getFrom_runplan();

			                	 //�ж��Ƿ�����������ͼ��
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
	        
	        * @Title: DelSyn_TaskList ������ɾ�������б������������ɾ��
	        
	        * @Description: TODO(ɾ�������ܣ�û���·���ֱ��ɾ�����ݿ⣬�·�����Ҫɾ���豸�ϵ�����)
	        
	        * @param @param msg
	        * @param @return
	        * @param @throws DbException
	        * @param @throws GDSetException    �趨�ļ�
	        
	        * @author  ����
	        
	        * @return String    ��������
	        
	        * @throws
	        
	        ************************************************
	         */
 public Object DelSyn_TaskList(ArrayList taskList) {


        String not_online_task[]=new String[taskList.size()];
        System.out.println(taskList.size());

        String sql = "";
        boolean isDel = false;
        int j=0;
        int count1=0;  //��¼ɾ������ɹ��ļ���
        int count2=0;  //��¼ɾ������ʧ�ܵļ���
        String message="";//��¼����ɾ��ʧ����Ϣ;
        for (int i = 0; i < taskList.size(); i++) {
       	 
     
        	
        	Task task=(Task) taskList.get(i);
            String task_id =task.getTask_id() ;
          
            String task_type =task.getTask_type();
            String is_send = task.getIs_send();
            String code=task.getHead_code();
            String shortName=task.getShortname();
            
             if(is_send.equals("���´�"))//ɾ�����ݿ�Ҳɾ���豸�ϵ�����
             {
            	  //�·�����ɾ���ӿ�
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
		                     //ɾ���豸�ϵ�����
		                 	
		
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
									 * �����¼��������Ҫɾ����Ӧʱ�䵥Ԫ���еĵ�Ԫʱ���ܷ�������
									 */
									
									if(taskTypeWithEnglish.equals("QualityStreamTask"))
									{
									  	this.updateUnitToAdc(task);
									}
								} catch (DeviceNotExistException e) {
									// TODO Auto-generated catch block
									LogTool.fatal(e);
							         count2++;
		                             message+="վ��:"+shortName+task_type+" ����ɾ��ʧ�ܣ�\r";
		                             continue;
								} catch (DeviceFilterException e) {
									// TODO Auto-generated catch block
									LogTool.fatal(e);
							         count2++;
		                             message+="վ��:"+shortName+task_type+" ����ɾ��ʧ�ܣ�\r";
		                             continue;
								} catch (DeviceProcessException e) {
									// TODO Auto-generated catch block
									LogTool.fatal(e);
							         count2++;
		                             message+="վ��:"+shortName+task_type+" ����ɾ��ʧ�ܣ�\r";
		                             continue;
								} catch (DeviceTimedOutException e) {
									// TODO Auto-generated catch block
									LogTool.fatal(e);
							         count2++;
		                             message+="վ��:"+shortName+task_type+" ����ɾ��ʧ�ܣ�\r";
		                             continue;
								} catch (DeviceReportException e) {
									LogTool.fatal(e);
									if(e.getMessage().indexOf("���񲻴���")<0){
								         count2++;
			                             message+="վ��:"+shortName+task_type+" ����ɾ��ʧ�ܣ�\r";
			                             continue;
									}
								} catch (Exception e) {
									LogTool.fatal(e);
							         count2++;
		                             message+="վ��:"+shortName+task_type+" ����ɾ��ʧ�ܣ�\r";
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
		                     message+="վ��:"+shortName+"վ�㲻����,"+task_type+" ����ɾ��ʧ�ܣ�\r";
		                  
		                 } 
                 }
             }else //ֻɾ�����ݿ� �� 
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
  * ɾ��0���ӵļ�¼
  * @detail  
  * @method   
  * @return  void  
  * @author  zhaoyahui
  * @version 2013-3-28 ����05:52:26
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
 
 * @Description: TODO(����ɾ������ģ�鹦�ܣ�û���·���ֱ��ɾ�����ݿ⣬�·�����Ҫɾ���豸�ϵ�����)
 
 * @param @param msg
 * @param @return
 * @param @throws DbException
 * @param @throws GDSetException    �趨�ļ�
 
 * @author  ����
 
 * @return String    ��������
 
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
		int count1=0;  //��¼ɾ������ɹ��ļ���
		int count2=0;  //��¼ɾ������ʧ�ܵļ���
		String message="";//��¼����ɾ��ʧ����Ϣ;
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
		 //ɾ���豸�ϵ�����
		
		 
            if(count==5)
            {
            		
            	try {
						isDel = TaskAPI.msgTaskDeleteCmd(code, "",
							freq, band, startDate,
							endDate, date,"" ,
							 priority,"");
						isDelStreamQuality = isDel;
				} catch (Exception e) {
					if(e.getMessage() != null && e.getMessage().indexOf("���񲻴���")>-1){
				         message+="վ��:"+shortName+" ����ɾ���豸��ʧ�ܣ�"+e.getMessage()+"\r";
					} else{
						deleByTasktype=" and 1=2";
				         message+="վ��:"+shortName+" ����ɾ��ʧ�ܣ�"+e.getMessage()+"\r";
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
				         if(e.getMessage() != null && e.getMessage().indexOf("���񲻴���")>-1){
	    			         message+="վ��:"+shortName+check_qualityStream_task+" ��������ɾ���豸��ʧ�ܣ�"+e.getMessage()+"\r";
				         }  else{
				        	 message+="վ��:"+shortName+check_qualityStream_task+" ��������ɾ��ʧ�ܣ�"+e.getMessage()+"\r";
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
				         if(e.getMessage() != null && e.getMessage().indexOf("���񲻴���")>-1){
	    			         message+="վ��:"+shortName+check_quality_task+" ��������ɾ���豸��ʧ�ܣ�"+e.getMessage()+"\r";
				         }  else{
				        	 message+="վ��:"+shortName+check_quality_task+" ��������ɾ��ʧ�ܣ�"+e.getMessage()+"\r";
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
				         if(e.getMessage() != null && e.getMessage().indexOf("���񲻴���")>-1){
	    			         message+="վ��:"+shortName+check_stream_task+" ��������ɾ���豸��ʧ�ܣ�"+e.getMessage()+"\r";
				         }  else{
				        	 message+="վ��:"+shortName+check_stream_task+" ��������ɾ��ʧ�ܣ�"+e.getMessage()+"\r";
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
				         if(e.getMessage() != null && e.getMessage().indexOf("���񲻴���")>-1){
	    			         message+="վ��:"+shortName+check_offset_task+" ��������ɾ���豸��ʧ�ܣ�"+e.getMessage()+"\r";
				         }  else{
				        	 message+="վ��:"+shortName+check_offset_task+" ��������ɾ��ʧ�ܣ�"+e.getMessage()+"\r";
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
				         if(e.getMessage() != null && e.getMessage().indexOf("���񲻴���")>-1){
	    			         message+="վ��:"+shortName+check_spec_task+" ��������ɾ���豸��ʧ�ܣ�"+e.getMessage()+"\r";
				         }  else{
				        	 message+="վ��:"+shortName+check_spec_task+" ��������ɾ��ʧ�ܣ�"+e.getMessage()+"\r";
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
	                                    	   if(task.getTask_type().equals("5") && task.getIs_send().equals("1"))//���´��
	                                    	   {
	                                    		 /**
	                           					 	* ���ɾ���ɹ���Ҫɾ��¼������ʱ�䵥Ԫ�ķ�������
	                           					 */
	                                    	     this.updateUnitToAdc(task);  
	                                    	   }
	                                       }
		                           		}//��ѯ¼���������
	                                   	gd1 = DbComponent.exeUpdate(sql.toString());
									} catch (DbException e) {
										// TODO Auto-generated catch block
										LogTool.fatal(e);
										  message+="վ��:"+shortName+check_spec_task+" ����ɾ��ʧ�ܣ�\r";
									} catch (GDSetException e) {
										// TODO Auto-generated catch block
										LogTool.fatal(e);
										 message+="վ��:"+shortName+check_spec_task+" ����ɾ��ʧ�ܣ�\r";
									}
		                           if(gd1){
		                           	count1++;
		                           }
		                       
//		      }
		 
		  }//ѭ������վ�����for

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
 
 * @Description: TODO(�������)
 
 * @param @param str
 * @param @return    �趨�ļ�
 
 * @author  ����
 
 * @return String    ��������
 
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
			return new EXEException("", "���������Ϣʧ�ܣ�"+ e.getMessage(),null);
		}
		return true;
 }
 /**
  * �·�ȫ������(��ǰ��ѯ�����鵽������)
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
		 returnlist.add("ѡ���·����������Ѿ��´�����������δ��˵�����������ѡ���·�����");
		 return returnlist;
	 }
	 Object resObj =  this.sendSyn_Task(taskList);
	 return resObj;
 }
 /**
  * ************************************************
 
 * @Title: checkTaskType
 
 * @Description: TODO(������һ�仰�����������������)
 
 * @param @param str
 * @param @return    �趨�ļ�
 
 * @author  ����
 
 * @return String    ��������
 
 * @throws
 
 ************************************************
  */
 public Object sendSyn_Task(ArrayList taskList) {
     int count1=0;  //��¼ɾ������ɹ��ļ���
     int count2=0;  //��¼ɾ������ʧ�ܵļ���
     String message="";//��¼����ɾ��ʧ����Ϣ;

     ArrayList taskListZL = new ArrayList();
     //��ѭ��һ�ΰ�ָ��¼����������������ó��������·�
     for(int i=taskList.size()-1;i>=0;i--)
	 {
		 Task task=(Task) taskList.get(i);
		 if(task.getRecord_type().equals("����")){
			 /**
			  * ���ﲻ��Ҫָ�����ܻ���
			  */
//			 if(task.getEqu_code().equals("")){
//				 return new EXEException("",  task.getHead_code()+"_"+task.getFreq()+"����¼���������ָ��һ�����ջ�",null);
//			 }
			 taskListZL.add(task);
			 taskList.remove(i);
		 }
	     
	 }
     //�·�ָ��¼���������������Ŀ�ı�֤��������ռ�ý��ջ���Ч��������ж�
     for(int i=0;i<taskListZL.size();i++)
	 {
		 Task task=(Task) taskListZL.get(i);
		 ArrayList tempList = this.sendSingleTask(task);
		 count1 = count1 +(Integer)tempList.get(0);
		 count2 = count2 +(Integer)tempList.get(1);
		 message = message +(String)tempList.get(2);
         
	 }
     //�·���������
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
	 int count1=0;  //��¼ɾ������ɹ��ļ���
     int count2=0;  //��¼ɾ������ʧ�ܵļ���
     String message="";//��¼����ɾ��ʧ����Ϣ;
	 String task_type=task.getTask_type();
     String task_id=task.getTask_id();
     String send_user=task.getSend_user();
     String equCode=task.getEqu_code();//ֵΪ����������R1������R2���� ����
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

			    String note = "["+ TimeMethod.get_nowdate()+"]�����´�ɹ�";
		        updateSendToEquipmentState("radio_unify_task_tab",1,note,TimeMethod.get_nowdate(),task_id,send_user,task.getHead_code());
			 
      	 }  catch (Exception e) {
				  message += task.getShortname()+task_type+"�·�ʧ��,Ƶ�ʣ�"+task.getFreq()+e.getMessage()+"\r";
				  count2++;

				   updateSendToEquipmentState("radio_unify_task_tab",2,"["+ TimeMethod.get_nowdate()+"]�����·�ʧ��",TimeMethod.get_nowdate(),
						   task_id,send_user,headcode);
					
				LogTool.fatal(e);
			}
       }
      else if(task_typewithenglish.equals("QualityStreamTask")){
      	 try {
      		
      			
      		 if(task.getRecord_type().equals("����")){
      			 
      			 task.setCheck_alarm("0");//����
      		 } else if(task.getRecord_type().equals("Ч��")){
      			 task.setCheck_alarm("1");//Ч��
      		 }else
      		 {
      			 task.setCheck_alarm("3");//��ʱ���� 
      		 }
      		
      	

	 				sendOK=sendQualtiyStreamTask(task,"Set");
	 				ArrayList subTaskList = task.getSubtask(); 
	 				//ÿ������ֻ����һ��subtask
	 				Subtask sub=(Subtask) subTaskList.get(0);
	 				/*��
	 				 * ����Ҫ����ʱ�䵥Ԫ��
	 				 */
	 				//boolean updateUnit=this.updateUnit(task,task.getHead_code(),sub.getEqu_code());
	 				count1++;
      	
      		 
			    String note = "["+ TimeMethod.get_nowdate()+"]�����´�ɹ�";
		        updateSendToEquipmentState("radio_unify_task_tab",1,note,TimeMethod.get_nowdate(),task_id,send_user,task.getHead_code());

			}  catch (Exception e) {
				// TODO Auto-generated catch block
				  message += task.getShortname()+task_type+"�·�ʧ��,Ƶ�ʣ�"+task.getFreq()+e.getMessage()+"\r";
				  count2++;
				   updateSendToEquipmentState("radio_unify_task_tab",2,"["+ TimeMethod.get_nowdate()+"]�����·�ʧ��",TimeMethod.get_nowdate(),
						   task_id,send_user,headcode);
					
				LogTool.fatal(e);
			}
       }
        else if(task_typewithenglish.equals("QualityTask"))
       {
      	 try {
      	
				sendOK=sendQualityTask(task,"Set");
			     String note = "["+ TimeMethod.get_nowdate()+"]�����´�ɹ�";
		         updateSendToEquipmentState("radio_unify_task_tab",1,note,TimeMethod.get_nowdate(),task_id,send_user,task.getHead_code());
				count1++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				  message+= task.getShortname()+task_type+"�·�ʧ��,Ƶ�ʣ�"+task.getFreq()+e.getMessage()+"\r";
				  count2++;
				  updateSendToEquipmentState("radio_unify_task_tab",2,"["+ TimeMethod.get_nowdate()+"]�����·�ʧ��",TimeMethod.get_nowdate(),
						  task_id,send_user,headcode);
					
				LogTool.fatal(e);
			}
       }
       else if(task_typewithenglish.equals("OffsetTask"))
       {
      	 
      	 try{
      	 sendOK=sendOffsetTask(task,"Set");
      		count1++;
      	
			     String note = "["+ TimeMethod.get_nowdate()+"]�����´�ɹ�";
		         updateSendToEquipmentState("radio_unify_task_tab",1,note,TimeMethod.get_nowdate(),task_id,send_user,task.getHead_code());
           }  catch (Exception e) {
				// TODO Auto-generated catch block
				  message+= task.getShortname()+task_type+"�·�ʧ��,Ƶ�ʣ�"+task.getFreq()+e.getMessage()+"\r";
				  count2++;
				  updateSendToEquipmentState("radio_unify_task_tab",2,"["+ TimeMethod.get_nowdate()+"]�����·�ʧ��",TimeMethod.get_nowdate(),
						  task_id,send_user,headcode);
					
				LogTool.fatal(e);
			}
       }
       else
       {
      	 try{
      	 sendOK=sendSpectrumTask(task,"Set");
   		 count1++;
   	
		     String note = "["+ TimeMethod.get_nowdate()+"]�����´�ɹ�";
	         updateSendToEquipmentState("radio_unify_task_tab",1,note,TimeMethod.get_nowdate(),task_id,send_user,task.getHead_code());
         }  catch (Exception e) {
			// TODO Auto-generated catch block
			  message = task.getShortname()+task_type+"�·�ʧ��,Ƶ�ʣ�"+task.getFreq()+e.getMessage()+"\r";
			  count2++;
			  updateSendToEquipmentState("radio_unify_task_tab",2,"["+ TimeMethod.get_nowdate()+"]�����·�ʧ��",TimeMethod.get_nowdate(),
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
  * ������������·����״̬
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

* @Description: TODO(�����������27����)

* @param @param task
* @param @return    �趨�ļ�

* @author  ����

* @return boolean    ��������

* @throws

************************************************
 */
 public boolean checkUnit30(Task task) throws DbException, GDSetException
 {
	 boolean  checkboo=false;
	 String valid_startdatetime=task.getValid_startdatetime();
	 String valid_enddatetime=task.getValid_enddatetime();
//     String headcode=task.getHead_code().substring(0,task.getHead_code().length()-1);//ȡcode����AB
     String headcode=task.getHead_code();//ȡcode����AB
     String head_type_id = task.getHead_type_id();
 	 ArrayList subTaskList = task.getSubtask(); 
 	boolean flagA=true;//վ���A���Ƿ�����·�
 	boolean flagBR1=true;//վ���B�Ľ��ջ�R1�Ƿ�����·���
 	boolean flagBR2=true;//վ���B�Ľ��ջ�R2�Ƿ�����·���
	  //ÿ������ֻ����һ��subtask
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
//	        				" and t.record_type='Ч��' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//	        		" group by t.head_code,t.equ_code union ";   
        		   sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
	        		" where t.head_code like '"+headcode+"%' " + 
	        		"  and ( (t.record_type='Ч��' and t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+") " +
	        				"or (t.record_type='����') ) and t.unittime in" +this.getSqlQueryTimeList(flag)+
	        		" group by t.head_code,t.equ_code union ";   
        		   flag=new ArrayList();
        	   }
           }
//	        sqlxg+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
//    		" where t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+" and t.head_code like '"+headcode+"%' " +
//    				" and t.record_type='Ч��' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//    		" group by t.head_code,t.equ_code ";
//	        sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
//       		" where  t.head_code like '"+headcode+"' " +
//       				" and t.record_type='����' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//       		" group by t.head_code,t.equ_code ";
	        sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
    		" where t.head_code like '"+headcode+"%' " + 
    		"  and ( (t.record_type='Ч��' and t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+") " +
    				"or (t.record_type='����') ) and t.unittime in" +this.getSqlQueryTimeList(flag)+
    		" group by t.head_code,t.equ_code union " +
    		"  select head.code head_code ,'R1' equ_code " +
    		"  from res_headend_tab head where head.code = '"+headcode+"A'"+" and head.is_online=0" +
    		"  union select head.code head_code ,'R2' equ_code" +
    		"  from res_headend_tab head  where head.code ='"+headcode+"B'"+" and head.is_online=0";
  
       
	        
//	        GDSet setxg = DbComponent.Query(sqlxg); 
	        GDSet setzl = DbComponent.Query(sqlzl); 
	      //�ȸ��������ݾӵĽ��ջ��ж�һ��
	        if(head_type_id.equals("101")){
	        	if(setzl.getRowCount()==2){//�ɼ���һ�����ջ�
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
	        	//731��R1������ָ�꣬����������ջ�����������ͼ������
		        if(setzl.getRowCount()==2)//��ѯ��������˵�������Ѿ������������������·���
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
//	      //�ٸ���Ч���ݾӵĽ��ջ��ж�һ��
//	        if(head_type_id.equals("101")){
//	        	if(setxg.getRowCount()==2){//�ɼ���һ�����ջ�
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
//		        if(setxg.getRowCount()==3)//��ѯ��������˵�������Ѿ������������������·���
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
//		        				" and t.record_type='Ч��' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//		        		" group by t.head_code,t.equ_code union ";   
//	        		   sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
//		        		" where t.head_code like '"+headcode+"' " +
//		        				"  and t.record_type='����' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//		        		" group by t.head_code,t.equ_code union ";   
	        		   sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
		        		" where t.head_code like '"+headcode+"%' " + 
		        		"  and ( (t.record_type='Ч��' and t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+") " +
		        				"or (t.record_type='����') ) and t.unittime in" +this.getSqlQueryTimeList(flag)+
		        		" group by t.head_code,t.equ_code union ";   
	        		   flag=new ArrayList();
	        	   }
	           }
//	           sqlxg+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
//	        		" where t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+" and t.head_code like '"+headcode+"%' " +
//	        				" and t.record_type='Ч��' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//	        		" group by t.head_code,t.equ_code ";
//	           sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
//		       		" where  t.head_code like '"+headcode+"' " +
//		       				" and t.record_type='����' and t.unittime in" +this.getSqlQueryTimeList(flag)+
//		       		" group by t.head_code,t.equ_code ";
	           sqlzl+="   select t.head_code,t.equ_code from res_task_unit_tab t " +
       		" where t.head_code like '"+headcode+"%' " + 
       		"  and ( (t.record_type='Ч��' and t.count_minutes+"+count_minutes+">"+this.CountMinutesLimit+") " +
       				"or (t.record_type='����') ) and t.unittime in" +this.getSqlQueryTimeList(flag)+
       	    		" group by t.head_code,t.equ_code union " +
    		"  select head.code head_code ,'R1' equ_code " +
    		"  from res_headend_tab head where head.code = '"+headcode+"A'"+ " and head.is_online=0 " +
    		"  union select head.code head_code ,'R2' equ_code" +
    		"  from res_headend_tab head  where head.code ='"+headcode+"B'"+"  and head.is_online=0 ";
	        
//	        GDSet setxg = DbComponent.Query(sqlxg); 
	        GDSet setzl = DbComponent.Query(sqlzl); 
	       
	        //�ȸ��������ݾӵĽ��ջ��ж�һ��
	        if(head_type_id.equals("101")){
	        	if(setzl.getRowCount()==2){//�ɼ���һ�����ջ�
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
	        	//731��R1������ָ�꣬����������ջ�����������ͼ������
		        if(setzl.getRowCount()==2)//��ѯ��������˵�������Ѿ������������������·���
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
	        
//	        //�ȸ��������ݾӵĽ��ջ��ж�һ��
//	        if(head_type_id.equals("101")){
//	        	if(setxg.getRowCount()==2){//�ɼ���һ�����ջ�
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
//		        if(setxg.getRowCount()==3)//��ѯ��������˵�������Ѿ������������������·���
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
 
 * @Description: TODO(����ʱ��ȡ������)
 
 * @param @param time 00:01:02
 * @param @return    �趨�ļ�
 
 * @author  ����
 
 * @return float    ��������
 
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
 
 * @Description: TODO(����list�е�ʱ����ϳ�sql���Բ�ѯ�ķ�ʽ)
 
 * @param @param time 00:01:02
 * @param @return    �趨�ļ�
 
 * @author  ����
 
 * @return float    ��������
 
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

 * @Description: TODO(������������27���ӵ�Ԫ����Ҫ���ڼ�С��Ԫ������)

 * @param @param task,head_code,equ_code
 * @param @return    �趨�ļ�

 * @author  ����

 * @return boolean    ��������

 * @throws

 ************************************************
  */
 public boolean updateUnitToAdc(Task task) throws GDSetException, DbException
 {
 	 String valid_startdatetime=task.getValid_startdatetime();
 	 String valid_enddatetime=task.getValid_enddatetime();
      String head_code=task.getHead_code();
  	ArrayList subTaskList = task.getSubtask(); 
  
 	  //ÿ������ֻ����һ��subtask
 	  Subtask sub=(Subtask) subTaskList.get(0);
 	String equ_code=sub.getEqu_code();
 	ArrayList cycTaskList = sub.getCyctask(); 

 	ArrayList sinTAskList = sub.getSintask(); 
 	
 	if(task.getHead_type_id().equals("102")){//ң��վ
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
		        
 		       
 		        //�������еĵ�Ԫ
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
		        
 		   
 		        //�ȸ������еĵ�Ԫ
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

* @Description: TODO(������������27���ӵ�Ԫ��Ҫ���������͸��µ�Ԫ)

* @param @param task,head_code,equ_code
* @param @return    �趨�ļ�

* @author  ����

* @return boolean    ��������

* @throws

************************************************
 */
public boolean updateUnit(Task task,String head_code,String equ_code) throws GDSetException, DbException
{
	 String valid_startdatetime=task.getValid_startdatetime();
	 String valid_enddatetime=task.getValid_enddatetime();
     String headcode=task.getHead_code();
 	ArrayList subTaskList = task.getSubtask(); 
 
	  //ÿ������ֻ����һ��subtask
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
		        //�ȸ������еĵ�Ԫ
		        if(updateArr.length>0)
			        DbComponent.exeBatch(updateArr); 
		        //����û�еĵ�Ԫ
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
		        //�ȸ������еĵ�Ԫ
		        if(updateArr.length>0)
			        DbComponent.exeBatch(updateArr); 
		        //����û�еĵ�Ԫ
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
   
    //ÿ������ֻ����һ��subtask
    Subtask sub=(Subtask) subTaskList.get(0);
  
	ArrayList cycTaskList = sub.getCyctask(); 
	ArrayList sinTAskList =sub.getSintask(); 

	String equCode=sub.getEqu_code();//ң��վ(102)ֵΪ����������NI_R1������713_R1������713_R2��  �ɼ���(101)ֵΪ��������R1������R2��
    String headcode = task.getHead_code();
    String headtype = task.getHead_type_id();//101�ɼ��� 102ң��վ
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
		          msgPrio = security.getMessagePriorityByUserName(task.getSend_user(), task.getIs_temporary().equals("��ʱ����")?2:1, 4, Integer.parseInt(task.getPriority()));
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
    
     //ÿ������ֻ����һ��subtask
     Subtask sub=(Subtask) subTaskList.get(0);
   
 	ArrayList cycTaskList = sub.getCyctask(); 
 	ArrayList sinTAskList = sub.getSintask(); 
 
 	String equCode=sub.getEqu_code();//ֵΪ��������R1������R2��
    String headcode = task.getHead_code();
    String headtype = task.getHead_type_id();//101�ɼ��� 102ң��վ
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
		          msgPrio = security.getMessagePriorityByUserName(task.getSend_user(), task.getIs_temporary().equals("��ʱ����")?2:1, 4, Integer.parseInt(task.getPriority()));
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
	    
	     //ÿ������ֻ����һ��subtask
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
			          msgPrio = security.getMessagePriorityByUserName(task.getSend_user(), task.getIs_temporary().equals("��ʱ����")?2:1, 4, Integer.parseInt(task.getPriority()));
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
    
     //ÿ������ֻ����һ��subtask
     Subtask sub=(Subtask) subTaskList.get(0);
  
 	ArrayList cycTaskList = sub.getCyctask(); 
 
 	ArrayList sinTAskList = sub.getSintask(); 

    String equCode=sub.getEqu_code();//��R1������R2��
    String headcode = task.getHead_code();
    String headtype = task.getHead_type_id();//101�ɼ��� 102ң��վ
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
		          msgPrio = security.getMessagePriorityByUserName(task.getSend_user(), task.getIs_temporary().equals("��ʱ����")?2:1, 4, Integer.parseInt(task.getPriority()));
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
    
     //ÿ������ֻ����һ��subtask
     Subtask sub=(Subtask) subTaskList.get(0);
  
 	ArrayList cycTaskList =sub.getCyctask(); 
 	ArrayList sinTAskList = sub.getSintask(); 

 	String equCode=sub.getEqu_code();//ң��վ(102)ֵΪ����������NI_R1������713_R1������713_R2��  �ɼ���(101)ֵΪ��������R1������R2��
    String headcode = task.getHead_code();
    String headtype = task.getHead_type_id();//101�ɼ��� 102ң��վ
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
		          msgPrio = security.getMessagePriorityByUserName(task.getSend_user(), task.getIs_temporary().equals("��ʱ����")?2:1, 4, Integer.parseInt(task.getPriority()));
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
 
 * @Description: TODO(�鿴����ִ��״̬��)
 
 * @param @param ASObject
 * @param @return    �趨�ļ�
 
 * @author  ����
 
 * @return String    ��������
 
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


         //	����״̬��ѯ����
                      String status_reply = "";
          // �������ؽ��
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
 	if(str.equals("����"))
 	{
 		return "2";
 	}else if(str.equals("����"))
 	{
 		return "1";
 	}else if(str.equals("Сʱ"))
 	{
 		return "3";
 	}else
 	{
 		return str;
 	}
 }
public String checkTaskType(String str)
        {
        	if(str.equals("ָ������"))
        	{
        		return "QualityTask";
        	}else if(str.equals("¼������"))
        	{
        		//����û�е�����¼������ֱ���·�¼��ָ������
        		return "QualityStreamTask";
        		//return "StreamTask";
        	}else if(str.equals("Ƶƫ����"))
        	{
        		return "OffsetTask";
        	}else if(str.equals("ָ��¼������"))
        	{
        		return "QualityStreamTask";
        	}else
        	{
        		return "SpectrumTask";
        	}
        }

/**
 * �����޸������ִ���豸
 * �����
 */
public Object updateSelectTask(String  sql){
	
	
	


	
	try {
	     String[] sqlsStrings=sql.split(";");
		DbComponent.exeBatch(sqlsStrings);

		
	} catch (DbException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
		return new EXEException("", "������������ִ���豸ʧ��,ԭ��:"+e.getMessage(), "");
	} 
	
	return "������������ִ���豸�ɹ�";
	
}
    /**
     * �޸�������Ϣ
     * @author ������
     * @date 2012/09/15    
     * @param obj
     * @return
     */
   public Object updateTask(ASObject obj){
	   String res ="�޸�����ɹ�!";
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
	   if(task_type.equalsIgnoreCase("ָ������")){
		   unifysql.append(",t.quality_sleeptime='"+sleep_time_quality+"'");
	   } 
	   if(task_type.equalsIgnoreCase("¼������")){
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
	   if(task_type.equalsIgnoreCase("Ƶƫ����")){
		   unifysql.append(",t.offset_sleeptime='"+sleep_time_offset+"'");
	   }
	   if(task_type.equalsIgnoreCase("ָ��¼������")){
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
	   if(task_type.equalsIgnoreCase("Ƶ������")){
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
		return new EXEException("","�޸������쳣��"+e.getMessage(),"");
	}
	   return res;
   }
   
   /**
 * @throws GDSetException 
 * @throws DbException 
	 * ************************************************
	* @Title: queryAllTaskPlanList
	* @Description: TODO(��ѯ����ƻ��б� ����������ϸ)
	* @param @param task
	* @param @return    �趨�ļ�
	* @author  ����
	* @return String    ��������
	* @throws
	
	************************************************
	 */
	    public Object queryAllTaskPlanList(Task obj) throws DbException, GDSetException {
	    	ASObject objRes = new ASObject();
	         String code = obj.getHead_code();//ǰ��code
	         String equCode = obj.getEqu_code();//equ code
	         String task_type = obj.getTask_type();//�������� 
	         String start =obj.getStart();//��ʼʱ��
	         String end =obj.getEnd();//����ʱ��
	         String is_send = obj.getIs_send();//�·�״̬
	         String send_way=obj.getIs_temporary();//���ɷ�ʽ��
	          ArrayList<Task> list = new ArrayList();
	          GDSet gdSet = null;
		      GDSet gdSet2 = null;
		      String val = getVal(start);
		      String sarray[]=start.split(" ");   
		      String earray[]=end.split(" ");
	       String tasklistsql="";
	       String totalsql="";
             //0����ȫ����1��ָ������ 2��¼������ 3��Ƶƫ���� 4Ƶ������
	        
	        	 tasklistsql+="	 select task.task_id,sub.sub_task_id ,decode(task.task_type,1,'ָ������',2,'¼������',3,'Ƶƫ����',4,'Ƶ������',5,'ָ��¼������') task_type,cyc.time_id cyc_time_id,sin.time_id sin_time_id,task.equ_code spe_equ_task,task.head_code," +
	        	 		"task.valid_startdatetime,task.valid_enddatetime,task.check_level,decode(task.is_temporary,1,'��ʱ����',2,'�ճ�����') is_temporary," +
	        	 		"task.check_fm_modulation,task.check_am_modulation,task.check_fm_modulation_max,task.create_user,task.authentic_user,task.send_user," +
	        	 		"task.check_offset,task.check_bandwidth,decode(task.unit,1,'����',2,'����',3,'Сʱ') unit,task.samples_number,decode(task.is_send,0,'δ�´�',1,'���´�',2,'�´�ʧ��') is_send,task.recordlength," +
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
	        			 code += "��AB";
	        		 }
	        		 String[] codeArr = code.split(",");//����AB����AB
	        		 if(!codeArr[1].equals("") && !codeArr[1].equals("��AB")){
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
	        	  * {label:"ȫ��", data:"0"}, 
					{label:"δ�·�δ���", data:"1"}, 
					{label:"�����δ�·�", data:"2"},
					{label:"���·�", data:"3"} ]);
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
				        	  tasktemp.setValidDate("��"+gdSet.getString(i, "valid_startdatetime")+"��"+gdSet.getString(i, "valid_enddatetime"));
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
					        	  tasktemp.setValidDate("��"+gdSet.getString(i, "valid_startdatetime")+"��"+gdSet.getString(i, "valid_enddatetime"));
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
				return new EXEException("", "��ѯ������Ϣʧ�ܣ�"+ e.getMessage(),null);
			} catch (GDSetException e) {
				// TODO Auto-generated catch block
				LogTool.fatal(e);
				return new EXEException("", "��ѯ������Ϣʧ�ܣ�"+ e.getMessage(),null);
			}
           
			objRes.put("resultList", list);
			try {
				objRes.put("resultTotal", gdSet2.getString(0, "count"));
			} catch (GDSetException e) {
				// TODO Auto-generated catch block
				LogTool.fatal(e);
				return new EXEException("", "��ѯ������Ϣʧ�ܣ�"+ e.getMessage(),null);
			}
	         return objRes;
	    }
	    
       private static String getVal(String start) throws DbException, GDSetException{
			
			String sql = " select to_char(to_date('"+start+"', 'yyyy-mm-dd hh24:mi:ss'),'day') day from dual";
			
			String val="";
			
			GDSet set = DbComponent.Query(sql);
			
			String day = set.getString(0, "day");
			
			if(day.equals("����һ")){
				
				val="1";
			}else if(day.equals("���ڶ�")){
				
				val="2";
			}else if(day.equals("������")){
				
				val="3";
			}else if(day.equals("������")){
				
				val="4";
			}else if(day.equals("������")){
				
				val="5";
			}else if(day.equals("������")){
				
				val="6";
			}
			
			return val;
			
		}
}
