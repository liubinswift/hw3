package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import java.util.*;

import com.viewscenes.device.framework.BaseMessageResponse;
import com.viewscenes.device.framework.IMessageParser;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.util.InnerMsgType;

/**
 * 
 * <p>
 * Title : 指标报警历史查询解析类
 * </p>
 * 
 * <p>
 * Description: 兼容v5、v6、v7、v8版本。
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
 * @author 赵广磊
 * @version 1.0
 */

public class MsgQualityAlarmHistoryQueryRes extends BaseMessageResponse {

	
	private Result result;

	
	public static class Result{
		
		private Collection qas;
		private Collection qualityAlarmsScans;
		
		public Result(){
			this(new ArrayList(),new ArrayList());
		}
		
		public Result(Collection qas,Collection qualityAlarmsScans){
			this.qas = qas;
			this.qualityAlarmsScans = qualityAlarmsScans;
		}		
		public Collection getQualityAlarms() {
			return qas;
		}
		public Collection getQualityAlarmsScans() {
			return qualityAlarmsScans;
		}
	}
	
	
	
	public Result getResult() {
		return result;
	}


	public MsgQualityAlarmHistoryQueryRes() {

		setParsers(new IMessageParser[] {

		new ParserV8Radio() });
	}


	public static class QualityAlarm {

		private String alarmID;
		private String band;
		private String freq;
		private String mode;
		private String checkTime;
		private String type;
		private String desc;
		private String reason;
		private String equCode;
		private Map alarmParams;

		// static final boolean $assertionsDisabled; /* synthetic field */

		public String getAlarmID() {
			return alarmID;
		}

		public String getBand() {
			return band;
		}

		public String getFreq() {
			return freq;
		}

		public String getMode() {
			return mode;
		}

		public String getCheckTime() {
			return checkTime;
		}

		public String getType() {
			return type;
		}

		public String getDesc() {
			return desc;
		}

		public String getReason() {
			return reason;
		}

		public String getEquCode() {
			return equCode;
		}

		public Map getAlarmParams() {
			return alarmParams;
		}

		private QualityAlarm(Map attrs, Map aps) {
			if (attrs == null) {
				throw new AssertionError();
			} else {
				alarmParams = aps;
				equCode = (String) attrs.get("equcode");
				alarmID = (String) attrs.get("alarmid");
				mode = (String) attrs.get("mode");
				band = (String) attrs.get("band");
				freq = (String) attrs.get("freq");
				type = (String) attrs.get("type");
				desc = (String) attrs.get("desc");
				reason = (String) attrs.get("reason");
				checkTime = (String) attrs.get("checktime");
				return;
			}
		}
	}

	/**
	 * 
	 * V8版具体实现
	 * 
	 */
	protected class ParserV8Radio extends
			MsgQualityAlarmHistoryQueryRes.ParserV8BaseRadio {

		protected ParserV8Radio() {

		}

		protected Map makeQualityAlarmAttrs(Map oriAttrs) {
			oriAttrs.put("checktime", oriAttrs.get("checkdatetime"));
			return oriAttrs;
		}

		public InnerMsgType getType() {

			return InnerMsgType.instance("radio8");
		}
	}


	/**
	 * 
	 * V8基础版具体实现
	 * 
	 */
	protected class ParserV8BaseRadio extends
			BaseMessageResponse.ParserV8BaseRadio {

		protected ParserV8BaseRadio() {

		}

		protected void doParse() {

			MessageElement ear = getBody();
			if (ear == null) {
				return;
			}
			Collection qualityAlarmsScans = null;
			Collection qas = null;
			Collection qu = ear.getChildren();
			if (qu == null) {
				qualityAlarmsScans = new Vector();
			} else {
				qualityAlarmsScans = new ArrayList(qu.size());
			}

			Collection qualityAlarmReport = ear.getChildren();
			if (qualityAlarmReport == null) {
				return;
			}
			
			qas = new ArrayList(qualityAlarmReport.size());
			for (Iterator it = qualityAlarmReport.iterator(); it.hasNext();) {
				MessageElement qa = (MessageElement) it.next();
				Collection aps = qa.getChildren();
				if (aps != null) {
					Map alarmParams = new LinkedHashMap(aps.size());
					Map m;
					for (Iterator apsIt = aps.iterator(); apsIt.hasNext(); alarmParams
							.put(m.get("name"), m.get("value"))) {
						m = ((MessageElement) apsIt.next()).getAttributes();
					}
					qas.add(new QualityAlarm(makeQualityAlarmAttrs(qa
							.getAttributes()), alarmParams));
				}
			}
			
			result = new Result(qas,qualityAlarmsScans);
		}

		protected Map makeQualityAlarmAttrs(Map oriAttrs) {
			return oriAttrs;
		}

	}
	
}
