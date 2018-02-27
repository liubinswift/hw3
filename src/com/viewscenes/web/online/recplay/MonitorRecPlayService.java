package com.viewscenes.web.online.recplay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jmask.web.controller.EXEException;

import com.viewscenes.bean.RadioMarkZstViewBean;
import com.viewscenes.bean.device.StreamHistoryQueryResult;
import com.viewscenes.bean.runplan.RunplanBean;
import com.viewscenes.bean.task.Task;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.device.api.StreamAPI;
import com.viewscenes.device.exception.DeviceFilterException;
import com.viewscenes.device.exception.DeviceNotExistException;
import com.viewscenes.device.exception.DeviceProcessException;
import com.viewscenes.device.exception.DeviceReportException;
import com.viewscenes.device.exception.DeviceTimedOutException;
import com.viewscenes.device.radio.MsgStreamHistoryQueryRes;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.Security;
import com.viewscenes.web.online.onlineListen.MarkService;
import com.viewscenes.web.online.onlineListen.OnlineService;

import flex.messaging.io.amf.ASObject;

/**
 * 监测录音播放
 *  2012-09-15 by tcw
 * 该接口在海外监测中已删除,没有相关需求
 * @author thinkpad
 * 
 */
public class MonitorRecPlayService {

	/**
	 * 录音历史收测数据查询,但没有入库
	 * <p>
	 * class/function:com.viewscenes.web.online.recplay
	 * <p>
	 * explain: 2012-09-15 by tcw
	 * 该接口在海外监测中已删除,没有相关需求
	 * <p>
	 * author-date:谭长伟 2012-8-6
	 * @param:
	 * @return:
	 */
//	public Object msgStreamHistoryQueryCmd(ASObject obj) {
//		ASObject resultObj = new ASObject();
//		ArrayList<StreamHistoryQueryResult> list = new ArrayList<StreamHistoryQueryResult>();
//		String userId = (String) obj.get("userId");
//		String headCode = (String) obj.get("headCode");
//		String equCode = (String) obj.get("equCode");
//		String freq = (String) obj.get("freq");
//		String band = (String) obj.get("band");
//		String taskid = (String) obj.get("taskId");
//		String startDatetime = (String) obj.get("startDatetime");
//		String endDatetime = (String) obj.get("endDatetime");
//		String priority = (String) obj.get("priority");
//		try {
//			
//			Security security = new Security();
//    		long msgPrio = 0;
//    		if (userId != null) {
//    		       try {
//    		           msgPrio = security.getMessagePriority(userId, 1, 1, 0);
//    		           priority = new Long(msgPrio).toString();
//    		       } catch (Exception ex1) {
//    		       }
//    		}
//			
//			MsgStreamHistoryQueryRes.Result result = StreamAPI
//					.msgStreamHistoryQueryCmd(headCode, equCode, freq, band,
//							taskid, startDatetime, endDatetime, priority);
//
//			if (result != null){
//				Collection col = result.getTaskRecords();
//				String _equCode = result.getEquCode();
//				String _taskId = result.getTaskID();
//	
//	//			GDSet set = null;
//				Task task = null;
//				RunplanBean runplanBean = null;
//				try {
//					if (!_taskId.equals("")){
//						task = getTaskById(_taskId);
//						if (task != null){
//							String runplan_id = task.getRunplan_id();
//							//该录音从运行图生成,否则为临时任务
//							if (!runplan_id.equals("")){
//								runplanBean = getRunplanById(runplan_id);
//							}
//						}
//					}
//				} catch (DbException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					return new EXEException("", "[站点:" + headCode + "]监测录音播放失败|"
//							+ e.getMessage(), "");
//				} catch (GDSetException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					return new EXEException("", "[站点:" + headCode + "]监测录音播放失败|"
//							+ e.getMessage(), "");
//				}
//	
//				Iterator it = col.iterator();
//				while (it.hasNext()) {
//					MsgStreamHistoryQueryRes.TaskRecord taskRecord = (MsgStreamHistoryQueryRes.TaskRecord) it
//							.next();
//	
//						String _band = taskRecord.getBand();
//						String _freq = taskRecord.getFreq();
//						ArrayList<MsgStreamHistoryQueryRes.Record> recordList = (ArrayList<MsgStreamHistoryQueryRes.Record>) taskRecord
//								.getRecords();
//						for (int j = 0; j < recordList.size(); j++) {
//							StreamHistoryQueryResult resultBean = new StreamHistoryQueryResult();
//	
//							MsgStreamHistoryQueryRes.Record record = recordList
//									.get(j);
//							String _endDatetime = record.getEndDateTime();
//							// String _expireDays = record.getExpireDays();
//							String _fileName = record.getFileName();
//							String _recordId = record.getRecordID();
//							String _size = record.getSize();
//							String _startDatetime = record.getStartDateTime();
//							String _url = record.getURL();
//	
//							String _level = record.getLevel();
//							String _fmModulatio = record.getFmModulation();
//							String _amModulation = record.getAmModulation();
//							String _offset = record.getOffset();
//	
//							resultBean.setTaskId(_taskId);
//							resultBean.setEquCode(_equCode);
//							resultBean.setBand(_band);
//							resultBean.setFreq(_freq);
//							resultBean.setEndDateTime(_endDatetime);
//							resultBean.setFileName(_fileName);
//							resultBean.setRecordId(_recordId);
//							resultBean.setSize(_size);
//							resultBean.setStartDateTime(_startDatetime);
//							resultBean.setUrl(_url);
//	
//							resultBean.setLevel(_level);
//							resultBean.setFmModulatio(_fmModulatio);
//							resultBean.setAmModulation(_amModulation);
//							resultBean.setOffset(_offset);
//	
//							if (task != null)
//								resultBean.setTask(task);
//							if (runplanBean != null)
//								resultBean.setRunplanBean(runplanBean);
//							list.add(resultBean);
//						}
//				}
//			}
//
//		} catch (DeviceNotExistException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return new EXEException("", "[站点:" + headCode + "]监测录音播放失败|"
//					+ e.getMessage(), "");
//		} catch (DeviceFilterException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return new EXEException("", "[站点:" + headCode + "]监测录音播放失败|"
//					+ e.getMessage(), "");
//		} catch (DeviceProcessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return new EXEException("", "[站点:" + headCode + "]监测录音播放失败|"
//					+ e.getMessage(), "");
//		} catch (DeviceTimedOutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return new EXEException("", "[站点:" + headCode + "]监测录音播放失败|"
//					+ e.getMessage(), "");
//		} catch (DeviceReportException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return new EXEException("", "[站点:" + headCode + "]监测录音播放失败|"
//					+ e.getMessage(), "");
//		}
//		if (list ==null)
//			list = new ArrayList();
//		resultObj.put("resultList", list);
//		resultObj.put("resultTotal", list.size());
//		return resultObj;
//	}

	/**
	 * 监测录音播放打分
	 * <p>
	 * class/function:com.viewscenes.web.online.recplay
	 * <p>
	 * explain:
	 * <p>
	 * author-date:谭长伟 2012-8-6
	 * 
	 * @param:
	 * @return:
	 */
//	public Object setMark(RadioMarkZstViewBean radioMarkZstViewBean) {
//		return MarkService.setMark(radioMarkZstViewBean);
//	}
	
	/**
	 * 查询站点做过录音任务的频率
	 * <p>
	 * class/function:com.viewscenes.web.online.recplay
	 * <p>
	 * explain:
	 * <p>
	 * author-date:谭长伟 2012-8-6
	 * 
	 * @param:
	 * @return:
	 */
	public Object getRecTaskFreqList() {
		ArrayList<String> list = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct sub.freq ");
		sql
				.append(" from radio_unify_task_tab t, radio_sub_task_tab sub, res_headend_tab hd ");
		sql.append(" where t.task_type = 2 ");
		sql.append(" and t.is_delete = 0 ");
		sql.append(" and sub.is_delete = 0 ");
		sql.append(" and hd.is_delete = 0 ");
		sql.append(" and t.task_id = sub.task_id ");
		sql.append(" and t.head_code = hd.code ");
		sql.append(" and sub.freq != 0 ");
		GDSet set = null;
		try {
			set = DbComponent.Query(sql.toString());
			for (int i = 0; i < set.getRowCount(); i++) {
				String freq = set.getString(i, "freq");
				list.add(freq);
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

	

	/**
	 * 根据TASKID查询任务表
	 * <p>class/function:com.viewscenes.web.online.recplay
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-8-13
	 * @param:
	 * @return:
	 */
	public static Task getTaskById(String taskId) throws DbException, GDSetException {
		GDSet set = null;
		String task_sql = " select t.*,hd.shortname from radio_unify_task_tab t,res_headend_tab hd ";
		task_sql += " where sysdate between t.valid_startdatetime and t.valid_enddatetime ";
		task_sql += " and t.head_code = hd.code ";
		task_sql += " and t.is_delete = 0 ";
		task_sql += " and t.task_id = " + taskId + " ";

		set = DbComponent.Query(task_sql);
		if (set.getRowCount() > 0) {
			String runplan_id = set.getString(0, "runplan_id");
			String task_id = set.getString(0, "task_id");
			String equ_code = set.getString(0, "equ_code");
			String head_code = set.getString(0, "head_code");
			String shortname = set.getString(0, "shortname");
//			String task_freq = set.getString(0, "freq");

			Task task = new Task();

			task.setRunplan_id(runplan_id);
			task.setTask_id(task_id);
			task.setEqu_code(equ_code);
			task.setHead_code(head_code);
			task.setShortname(shortname);
//			task.setFreq(task_freq);

			return task;
		}
		
		return null;
	}

	
	/**
	 * 根据运行图ID，查询运行图
	 * <p>class/function:com.viewscenes.web.online.recplay
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-8-13
	 * @param:
	 * @return:
	 * @throws DbException 
	 * @throws GDSetException 
	 */
	public static RunplanBean getRunplanById(String runplan_id) throws GDSetException, DbException{
		return getRunplanByType("runplanId",runplan_id);
	}
	
	/**
	 * 根据任务ID，查询运行图
	 * <p>class/function:com.viewscenes.web.online.recplay
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-8-13
	 * @param:
	 * @return:
	 * @throws DbException 
	 * @throws GDSetException 
	 */
	public static RunplanBean getRunplanByTaskId(String taskId) throws GDSetException, DbException{
		return getRunplanByType("taskId",taskId);
		
	}
	
	
	private static RunplanBean getRunplanByType(String type,String id) throws GDSetException, DbException {
		
		GDSet runplanSet = null;
		String runplan_sql = "  select t.*,lan.language_name from zres_runplan_tab t,zdic_language_tab lan,radio_unify_task_tab task ";
		runplan_sql += " where t.language_id = lan.language_id(+) ";
		runplan_sql += " and t.runplan_id = task.runplan_id ";
		
		if (type.equals("runplanId")  && !id.equals(""))
			runplan_sql += " and t.runplan_id = " + id + "	";
		else if (type.equals("taskId") && !id.equals(""))
			runplan_sql += " and task.task_id =  " + id + "	";
		
		runplanSet = DbComponent.Query(runplan_sql);
		if (runplanSet.getRowCount() > 0) {
			RunplanBean runplanBean = new RunplanBean();
			String _runplan_id = runplanSet.getString(0, "runplan_id");
			String station_id = runplanSet.getString(0, "station_id");
			String station_name = runplanSet.getString(0, "station_name");
			String language_name = runplanSet.getString(0, "language_name");
			String language_id = runplanSet.getString(0, "language_id");
			String satellite_channel = runplanSet.getString(0, "satellite_channel");

			runplanBean.setRunplan_id(_runplan_id);
			runplanBean.setStation_id(station_id);
			runplanBean.setStation_name(station_name);
			runplanBean.setLanguage_id(language_id);
			runplanBean.setLanguage_name(language_name);
			runplanBean.setStart_time(runplanSet.getString(0, "start_time"));
			runplanBean.setEnd_time(runplanSet.getString(0, "end_time"));
			runplanBean.setSatellite_channel(satellite_channel);
			

			return runplanBean;
		}
		return null;
	}
}
