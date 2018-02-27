package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageResponse;
import com.viewscenes.device.framework.IMessageParser;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.util.*;
import java.util.*;

/**
 * 
 * <p>
 * Title : 音频历史查询解析类
 * </p>
 * 
 * <p>
 * Description: v8版本广播。
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company: Viewscenes
 * </p>
 * 
 * @author 谭长伟
 * @version 1.0
 * 
 * 
 * 2012-09-15 by tcw
 * 该接口在海外监测中已删除,没有相关需求
 */

public class MsgStreamHistoryQueryRes extends BaseMessageResponse {

//	private Result result;
//
//	
//	public static class Result{
//		private Collection taskRecords;
//		private String equCode;
//		private String taskID;
////		private Collection RecordTask;
//		
//		public Result(String taskid,String equcode,Collection taskRecords){
//			this.equCode = equcode;
//			this.taskID = taskid;
//			this.taskRecords = taskRecords;
//		}
//		
//		public Collection getTaskRecords() {
//
//			return taskRecords;
//		}
//
//		public String getEquCode() {
//
//			return equCode;
//		}
//
//		public String getTaskID() {
//
//			return taskID;
//		}
//		
//	}
//	
//	
//	
//	public Result getResult() {
//		return result;
//	}
//
//	public MsgStreamHistoryQueryRes() {
//
//		setParsers(new IMessageParser[] { new ParserV8Radio() });
//	}
//
//	
//
//	public static class RecordTask {
//		private String equcode;
//		private String taskid;
//		private Collection taskRecords;
//
//		public String getEqucode() {
//			return equcode;
//		}
//
//		public String getTaskid() {
//			return taskid;
//		}
//
//		public Collection getTaskrecords() {
//			return taskRecords;
//		}
//
//		private RecordTask(String equcode, String taskid, Collection TaskRecords) {
//			if (equcode == null || taskid == null || TaskRecords == null) {
//				throw new AssertionError();
//			} else {
//				this.equcode = equcode;
//				this.taskid = taskid;
//				this.taskRecords = TaskRecords;
//				return;
//			}
//		}
//	}
//
//	public static class TaskRecord {
//
//		private String band, freq;
//
//		private Collection records;
//
//		private TaskRecord(Map attr, Collection records) {
//
//			if (attr == null || records == null) {
//
//				throw new AssertionError();
//			} else {
//
//				if (attr.containsKey("freq")) {
//
//					this.freq = (String) attr.get("freq");
//				}
//
//				this.band = (String) attr.get("band");
//				this.records = records;
//				return;
//			}
//		}
//
//		public String getBand() {
//
//			return band;
//		}
//
//		public String getFreq() {
//
//			return freq;
//		}
//
//		public Collection getRecords() {
//
//			return records;
//		}
//	}
//
//	public static class Record {
//
//		private String recordID;
//		private String startDateTime;
//		private String endDateTime;
//		private String size;
//		private String url;
////		private String expireDays;
//		private String fileName;
//		
//		/**
//		 *接口新增加的返回结果值
//		 *expireDays 在接口文档中没有该项，现注释掉
//		 *2012-08-10 
//		 */
//		private String level;	//电平
//		private String fmModulation;//调制度
//		private String amModulation;//调幅度
//		private String offset;		//频偏
//		
//
//		// static final boolean $assertionsDisabled; /* synthetic field */
//
//		public String getRecordID() {
//
//			return recordID;
//		}
//
//		public String getStartDateTime() {
//
//			return startDateTime;
//		}
//
//		public String getEndDateTime() {
//
//			return endDateTime;
//		}
//
//		public String getSize() {
//			return size;
//		}
//
//		public String getURL() {
//			return url;
//		}
//
////		public String getExpireDays() {
////			return expireDays;
////		}
//
//		public String getFileName() {
//			return fileName;
//		}
//		
//		
//		
//		
//
//		public String getLevel() {
//			return level;
//		}
//
//		public String getFmModulation() {
//			return fmModulation;
//		}
//
//		public String getAmModulation() {
//			return amModulation;
//		}
//
//		public String getOffset() {
//			return offset;
//		}
//
//		private Record(Map attr) {
//
//			if (attr == null) {
//
//				throw new AssertionError();
//			} else {
//
//				recordID = (String) attr.get("recordid");
//				startDateTime = (String) attr.get("startdatetime");
//				endDateTime = (String) attr.get("enddatetime");
//				url = (String) attr.get("url");
//				size = attr.containsKey("size") ? (String) attr.get("size")
//						: "";
////				expireDays = attr.containsKey("expiredays") ? (String) attr
////						.get("expiredays") : "";
//				fileName = attr.containsKey("filename") ? (String) attr
//						.get("filename") : "";
//						
//				level = attr.containsKey("level") ? (String) attr
//						.get("level") : "";
//				
//				fmModulation = attr.containsKey("fmModulation") ? (String) attr
//						.get("fmModulation") : "";
//				
//				amModulation = attr.containsKey("amModulation") ? (String) attr
//						.get("amModulation") : "";
//				
//				offset = attr.containsKey("offset") ? (String) attr
//						.get("offset") : "";
//				return;
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * V8版具体实现
//	 * 
//	 */
//	protected class ParserV8Radio extends
//			MsgStreamHistoryQueryRes.ParserV8BaseRadio {
//
//		protected ParserV8Radio() {
//
//		}
//
//		public InnerMsgType getType() {
//
//			return InnerMsgType.instance("radio8");
//		}
//	}
//
//	/**
//	 * 
//	 * V8基础版具体实现
//	 * 
//	 */
//	protected class ParserV8BaseRadio extends
//			BaseMessageResponse.ParserV8BaseRadio {
//
//		protected ParserV8BaseRadio() {
//
//		}
//
//		protected void doParse() {
//
//			MessageElement report = getBody();
//
//			if (report == null) {
//
//				return;
//			}
//
//			Collection children = report.getChildren();
//
//			if (children == null) {
//
//				return;
//			}
//
//			String equCode = report.getAttributeValue("equcode");
//
//			String taskID = report.getAttributeValue("taskid");
//
//			Collection taskRecords = new ArrayList(children.size());
//
//			for (Iterator it = children.iterator(); it.hasNext();) {
//
//				MessageElement me = (MessageElement) it.next();
//				Collection records = me.getChildren();
//
//				if (records != null) {
//
//					Collection c = new ArrayList(records.size());
//
//					for (Iterator each = records.iterator(); each.hasNext();) {
//
//						c.add(new Record(((MessageElement) each.next())
//								.getAttributes()));
//					}
//
//					taskRecords.add(new TaskRecord(me.getAttributes(), c));
//				}
//
//			}
//
//			result = new Result(taskID,equCode,taskRecords);
//		}
//
//	}

}
