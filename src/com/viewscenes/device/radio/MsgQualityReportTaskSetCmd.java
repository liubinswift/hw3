package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import java.util.*;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.radio.MsgQualityStreamTaskSetCmd.AlarmParam;
import com.viewscenes.device.util.InnerMsgType;

/**
 * <p>
 * Title:
 * </p>
 * 
 * 指标收测任务设置下发接口
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
 * @version 8.0
 */
public class MsgQualityReportTaskSetCmd extends BaseMessageCommand {

	private Collection singleTasks;

	private Collection circleTasks;

	private Collection channels;

	ArrayList list = new ArrayList();

	public MsgQualityReportTaskSetCmd() {

		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setList(ArrayList li) {

		this.list = li;

	}

	/**
	 * 任务设置
	 */
	public static class TaskChannel {

		ArrayList qualityTaskSet;

		ArrayList qualityTask;

		ArrayList channel;

		ArrayList singleReportTime;

		ArrayList cycleReportTime;

		ArrayList qualityParam;
		ArrayList alarmParam;

		public TaskChannel(ArrayList qualityTaskSet, ArrayList qualityParam,
				ArrayList qualityTask, ArrayList channel,
				ArrayList singleReportTime, ArrayList cycleReportTime,ArrayList alarmParam) {

			this.qualityTaskSet = qualityTaskSet;
			this.qualityParam = qualityParam;
			this.qualityTask = qualityTask;
			this.channel = channel;
			this.singleReportTime = singleReportTime;
			this.cycleReportTime = cycleReportTime;
			this.alarmParam = alarmParam;
		}

		public void setQualityTaskSet(Collection tasks) {

			this.qualityTaskSet = (ArrayList) tasks;

		}

		public ArrayList getQualityTaskSet() {

			return (ArrayList) this.qualityTaskSet;
		}

		public void setQualityTask(Collection tasks) {

			this.qualityTask = (ArrayList) tasks;

		}

		public ArrayList getQualityTask() {

			return (ArrayList) this.qualityTask;
		}

		public void setSingleTasks(Collection tasks) {

			this.singleReportTime = (ArrayList) tasks;

		}

		public ArrayList getSingleTasks() {

			return (ArrayList) this.singleReportTime;
		}

		public void setCircleTasks(Collection tasks) {

			this.cycleReportTime = (ArrayList) tasks;

		}

		public ArrayList getCircleTasks() {

			return (ArrayList) this.cycleReportTime;
		}

		public void setChannels(Collection channels) {

			this.channel = (ArrayList) channels;

		}

		public ArrayList getChannels() {

			return (ArrayList) this.channel;
		}

		public void setQualityParam(Collection params) {

			this.qualityParam = (ArrayList) params;
		}

		public ArrayList getQualityParam() {
			return (ArrayList) this.qualityParam;
		}

		public void setAlarmParam(Collection params) {

			this.alarmParam = (ArrayList) params;
		}

		public ArrayList getAlarmParam() {
			return (ArrayList) this.alarmParam;
		}
		
	}

	/**
	 * 
	 * 包含StreamTaskSet循环任务所需参数的内部类
	 * 
	 * @version 8.0
	 * 
	 */
	public static class CircleTask {

		private String dayofWeek, startTime, endTime, reportMode,
				reportInterval, reportTime, expireDays, sampleNumber;// ,
																		// checkInterval;

		/**
		 * 
		 * 用StreamTaskSet所需参数构造对象（同一类型的长参数表，注意顺序）
		 * 
		 * @param startTime
		 *            起始时间
		 * 
		 * @param endTime
		 *            结束时间
		 * 
		 * @param dayofWeek
		 *            每周的第几天，全部为"All"
		 * 
		 * @param reportMode
		 *            上报模式
		 * 
		 * @param reportInterval
		 *            按时间间隔上报结果
		 * 
		 * @param reportTime
		 *            表示任务完成后再等待一段时间后再上报
		 * 
		 * 注：v5、v6、v7使用
		 * 
		 */

		public CircleTask(String startTime, String endTime, String dayofWeek,
				String reportMode, String reportInterval, String reportTime) {

			// assert startTime != null && endTime != null && expireDays != null

			// && dayofWeek != null;

			this.startTime = startTime;

			this.endTime = endTime;

			this.dayofWeek = dayofWeek;

			this.reportMode = reportMode;

			this.reportInterval = reportInterval;

			this.reportTime = reportTime;

		}

		/**
		 * 
		 * 用StreamTaskSet所需参数构造对象（同一类型的长参数表，注意顺序）
		 * 
		 * @param startTime
		 *            起始时间
		 * 
		 * @param endTime
		 *            结束时间
		 * 
		 * @param dayofWeek
		 *            每周的第几天，全部为"All"
		 * 
		 * @param reportMode
		 *            上报模式
		 * 
		 * @param reportInterval
		 *            按时间间隔上报结果
		 * 
		 * @param reportTime
		 *            表示任务完成后再等待一段时间后再上报
		 * 
		 * @param expireDays
		 *            过期时间。单位：天
		 * 
		 * 注：v8版使用
		 * 
		 */

		public CircleTask(String startTime, String endTime, String dayofWeek,
				String reportMode, String reportInterval, String reportTime,
				String expireDays) {

			// assert startTime != null && endTime != null && expireDays != null

			// && dayofWeek != null;

			this.startTime = startTime;

			this.endTime = endTime;

			this.dayofWeek = dayofWeek;

			this.reportMode = reportMode;

			this.reportInterval = reportInterval;

			this.reportTime = reportTime;

			this.expireDays = expireDays;

		}

		/**
		 * 
		 * 此方法是为广播和边境创建的，规则参照参数最大化，是广播和边境的通用接口。
		 * 
		 */

		public CircleTask(String startTime, String endTime, String dayofWeek,
				String reportMode, String sampleNumber, String reportInterval,
				String reportTime, String expireDays, String Radio_or_Video) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.dayofWeek = dayofWeek;

			this.reportMode = reportMode;

			this.sampleNumber = sampleNumber;
			this.reportInterval = reportInterval;
			this.reportTime = reportTime;

			this.expireDays = expireDays;

		}

		/**
		 * 
		 * 用StreamTaskSet所需参数构造对象（同一类型的长参数表，注意顺序）
		 * 
		 * @param startTime
		 *            起始时间
		 * 
		 * @param endTime
		 *            结束时间
		 * 
		 * @param dayofWeek
		 *            每周的第几天，全部为"All"
		 * 
		 * @param reportMode
		 *            上报模式:1表示任务完成后上报,2不主动上报结果
		 * 
		 * @param sampleNumber
		 *            收测时间段内采样个数
		 * 
		 * @param checkInterval
		 *            每个频点收测间隔
		 * 
		 * @param expireDays
		 *            过期时间。单位：天
		 * 
		 * 注：边境v1版使用
		 * 
		 * @param Radio_or_Video
		 *            判断是边境电视或广播（此参数暂时无用，可传入null值）
		 * 
		 */

		public CircleTask(String startTime, String endTime, String dayofWeek,
				String reportMode, String sampleNumber, String checkInterval,
				String expireDays, String Radio_or_Video) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.dayofWeek = dayofWeek;

			this.reportMode = reportMode;

			this.sampleNumber = sampleNumber;

			// this.checkInterval = checkInterval;

			this.expireDays = expireDays;

		}

	}

	/**
	 * 
	 * 包含StreamTaskSet单次任务所需参数的内部类
	 * 
	 * @version 1.0
	 * 
	 */

	public static class SingleTask {

		private String startTime, endTime, reportMode, reportInterval,
				reportTime, expireDays, sampleNumber;// , checkInterval;

		/**
		 * 
		 * 用StreamTaskSet所需参数构造对象（同一类型的长参数表，注意顺序）
		 * 
		 * @param startTime
		 *            起始时间
		 * 
		 * @param endTime
		 *            结束时间
		 * 
		 * @param reportMode
		 *            上报模式
		 * 
		 * @param reportInterval
		 *            按时间间隔上报结果
		 * 
		 * @param reportTime
		 *            表示任务完成后再等待一段时间后再上报
		 * 
		 * @param expireDays
		 *            过期时间。单位：天
		 * 
		 * 注：v8版使用
		 * 
		 */

		public SingleTask(String startTime, String endTime, String reportMode,
				String reportInterval, String reportTime, String expireDays) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.reportMode = reportMode;

			this.reportInterval = reportInterval;

			this.reportTime = reportTime;

			this.expireDays = expireDays;

		}

		/**
		 * 
		 * 此方法是为广播和边境创建的，规则参照参数最大化，是广播和边境的通用接口。
		 * 
		 */

		public SingleTask(String startTime, String endTime, String reportMode,
				String sampleNumber, String reportInterval, String reportTime,
				String expireDays, String Radio_or_Video) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.reportMode = reportMode;

			this.sampleNumber = sampleNumber;
			this.reportInterval = reportInterval;
			this.reportTime = reportTime;

			this.expireDays = expireDays;

		}

		/**
		 * 
		 * 用StreamTaskSet所需参数构造对象（同一类型的长参数表，注意顺序）
		 * 
		 * @param startTime
		 *            起始时间
		 * 
		 * @param endTime
		 *            结束时间
		 * 
		 * @param reportMode
		 *            上报模式:1表示任务完成后上报,2不主动上报结果
		 * 
		 * @param sampleNumber
		 *            收测时间段内采样个数
		 * 
		 * @param checkInterval
		 *            每个频点收测间隔
		 * 
		 * @param expireDays
		 *            过期时间。单位：天
		 * 
		 * 注：边境v1版使用
		 * 
		 * @param Radio_or_Video
		 *            判断是边境电视或广播（此参数暂时无用，可传入null值）
		 * 
		 */

		public SingleTask(String startTime, String endTime, String reportMode,
				String sampleNumber, String checkInterval, String expireDays,
				String Radio_or_Video) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.reportMode = reportMode;

			this.sampleNumber = sampleNumber;

			// this.checkInterval = checkInterval;

			this.expireDays = expireDays;

		}

		/**
		 * 
		 * 用StreamTaskSet所需参数构造对象（同一类型的长参数表，注意顺序）
		 * 
		 * @param startTime
		 *            起始时间
		 * 
		 * @param endTime
		 *            结束时间
		 * 
		 * @param reportMode
		 *            上报模式
		 * 
		 * @param reportInterval
		 *            按时间间隔上报结果
		 * 
		 * @param reportTime
		 *            表示任务完成后再等待一段时间后再上报
		 * 
		 * 注：v5、v6、v7使用
		 * 
		 */

		public SingleTask(String startTime, String endTime, String reportMode,
				String reportInterval, String reportTime) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.reportMode = reportMode;

			this.reportInterval = reportInterval;

			this.reportTime = reportTime;

		}

	}

	/**
	 * 
	 * 指标类型内部类
	 * 
	 * @author zgl
	 * 
	 */
	public static class QualityParam {

		private String type, Desc, sampleNumber;

		/**
		 * v6、v7、v8、边境v1版使用
		 * 
		 * @param type
		 *            String
		 * @param desc
		 *            String
		 */
		public QualityParam(String type, String desc) {

			this.type = type;
			this.Desc = desc;
		}

		/**
		 * v5版使用
		 * 
		 * @param type
		 *            String
		 * @param desc
		 *            String
		 * @param sampleNumber
		 *            String
		 */
		public QualityParam(String type, String desc, String sampleNumber) {

			this.type = type;
			this.Desc = desc;
			this.sampleNumber = sampleNumber;
		}

	}

	/**
	 * 指标是否报警参数
	 * @author thinkpad
	 *
	 */
	public static class AlarmParam {
		private String type,desc;
		
		public AlarmParam(String type,String desc){
			this.type = type;
			this.desc = desc;
		}
	}
	
	/**
	 * 
	 * 频道内部类
	 * 
	 * @author zgl
	 * 
	 */
	public static class Channel {

		private String freq, band, attenuation;

		/**
		 * 
		 * @param band
		 *            波段
		 * 
		 * @param freq
		 *            频率，单位为KHz。必须有效
		 * 
		 * @param attenuation
		 * 
		 * 注：v5、v6、v7、v8版
		 */

		public Channel(String freq, String band) {

			this.freq = freq;

			this.band = band;

		}

		/**
		 * 
		 * @param band
		 *            波段
		 * 
		 * @param freq
		 *            频率，单位为KHz。必须有效
		 * 
		 * @param attenuation
		 * 
		 * 注：边境广播v1版
		 */

		public Channel(String freq, String band, String attenuation) {

			this.freq = freq;

			this.band = band;

			this.attenuation = attenuation;

		}

		/**
		 * 
		 * @param band
		 *            波段
		 * 
		 * @param freq
		 *            频率，单位为KHz。必须有效
		 * 
		 * @param attenuation
		 * 
		 * 注：边境电视v1版
		 */

		public Channel(String freq) {

			this.freq = freq;

		}

	}

	/**
	 * QualityTaskSet标签内部类
	 */

	public static class QualityTaskSet {

		private String equCode, taskID, action, validStartDateTime,
				validEndDateTime, sampleNumber, unit;

		/**
		 * 注：v6、v7、v8版
		 * 
		 * @param equCode
		 *            String
		 * @param taskID
		 *            String
		 * @param action
		 *            String
		 * @param validStartDateTime
		 *            String
		 * @param validEndDateTime
		 *            String
		 * @param sampleNumber
		 *            String
		 * @param unit
		 *            String
		 */

		public QualityTaskSet(String equCode, String taskID, String action,
				String validStartDateTime, String validEndDateTime,
				String sampleNumber, String unit) {

			this.equCode = equCode;

			this.taskID = taskID;

			this.action = action;

			this.validStartDateTime = validStartDateTime;

			this.validEndDateTime = validEndDateTime;

			this.sampleNumber = sampleNumber;

			this.unit = unit;

		}

		/**
		 * 注：v5版、边境v1版使用
		 * 
		 * @param equCode
		 *            String
		 * @param taskID
		 *            String
		 * @param action
		 *            String
		 * @param validStartDateTime
		 *            String
		 * @param validEndDateTime
		 *            String
		 */

		public QualityTaskSet(String equCode, String taskID, String action,
				String validStartDateTime, String validEndDateTime) {

			this.equCode = equCode;

			this.taskID = taskID;

			this.action = action;

			this.validStartDateTime = validStartDateTime;

			this.validEndDateTime = validEndDateTime;

		}
	}

	/**
	 * 包含QualityTask所需参数的内部类
	 * 
	 * @version 1.0
	 * 
	 */

	public static class QualityTask {

		private String sleepTime;

		/**
		 * v5、v6、v7、v8版使用
		 * 
		 * @param sleepTime
		 *            String
		 */
		public QualityTask(String sleepTime) {

			this.sleepTime = sleepTime;
		}

		/**
		 * 边境v1版使用
		 * 
		 * @param sleepTime
		 *            String
		 */
		public QualityTask() {
		}
	}

	/**
	 * 拼凑xml
	 */

	/**
	 * v5、v6、v7、v8
	 * 
	 * @param tc
	 *            Channel
	 * @return MessageElement
	 */
	private MessageElement channelToElement(
			MsgQualityReportTaskSetCmd.Channel tc) {

		Map attrs = new LinkedHashMap(2);

		attrs.put("Freq", tc.freq);
		attrs.put("Band", tc.band);

		return new MessageElement("Channel", attrs, null);

	}

	/**
	 * 边境广播v1
	 * 
	 * @param tc
	 *            Channel
	 * @return MessageElement
	 */
	private MessageElement channelToElementFrontierRadio(
			MsgQualityReportTaskSetCmd.Channel tc) {

		Map attrs = new LinkedHashMap(3);

		attrs.put("Freq", tc.freq);
		attrs.put("Band", tc.band);
		attrs.put("Attenuation", tc.attenuation);

		return new MessageElement("Channel", attrs, null);

	}

	/**
	 * 边境电视v1
	 * 
	 * @param tc
	 *            Channel
	 * @return MessageElement
	 */
	private Map channelToElementFrontierVideo(
			MsgQualityReportTaskSetCmd.Channel tc) {

		Map attrs = new LinkedHashMap(3);

		attrs.put("Freq", tc.freq);
		attrs.put("Band", tc.band);
		attrs.put("Attenuation", tc.attenuation);

		return attrs;

	}

	/**
	 * v8
	 * 
	 * @param st
	 *            SingleTask
	 * @return MessageElement
	 */
	private MessageElement singleTaskToElement(
			MsgQualityReportTaskSetCmd.SingleTask st) {

		Map attrs = new LinkedHashMap(5);

		attrs.put("StartDateTime", st.startTime);

		attrs.put("EndDateTime", st.endTime);

		attrs.put("ReportMode", st.reportMode);

		if (st.reportMode.equalsIgnoreCase("0")) {
			attrs.put("ReportInterval", st.reportInterval);
		}

		if (st.reportMode.equalsIgnoreCase("1")) {
			attrs.put("ReportTime", st.reportTime);
		}

		attrs.put("ExpireDays", st.expireDays);

		return new MessageElement("SingleReportTime", attrs, null);

	}

	/**
	 * v5、v6、v7
	 * 
	 * @param st
	 *            SingleTask
	 * @return MessageElement
	 */
	private MessageElement singleTaskToElementV5V6V7(
			MsgQualityReportTaskSetCmd.SingleTask st) {

		Map attrs = new LinkedHashMap(4);

		attrs.put("StartDateTime", st.startTime);

		attrs.put("EndDateTime", st.endTime);

		attrs.put("ReportMode", st.reportMode);

		if (st.reportMode.equalsIgnoreCase("0")) {
			attrs.put("ReportInterval", st.reportInterval);
		}

		if (st.reportMode.equalsIgnoreCase("1")) {
			attrs.put("ReportTime", st.reportTime);
		}

		return new MessageElement("SingleReportTime", attrs, null);

	}

	/**
	 * 边境v1
	 * 
	 * @param st
	 *            SingleTask
	 * @return MessageElement
	 */
	private MessageElement singleTaskToElementFrontier(
			MsgQualityReportTaskSetCmd.SingleTask st) {

		Map attrs = new LinkedHashMap(7);

		attrs.put("StartDateTime", st.startTime);

		attrs.put("EndDateTime", st.endTime);

		attrs.put("ReportMode", st.reportMode);

		attrs.put("SampleNumber", st.sampleNumber);

		// attrs.put("CheckInterval", st.checkInterval);

		attrs.put("ExpireDays", st.expireDays);

		return new MessageElement("SingleReportTime", attrs, null);

	}

	/**
	 * v8
	 * 
	 * @param ct
	 *            CircleTask
	 * @return MessageElement
	 */
	private MessageElement circleTaskToElement(
			MsgQualityReportTaskSetCmd.CircleTask ct) {

		Map attrs = new LinkedHashMap(6);

		attrs.put("DayOfWeek", ct.dayofWeek);

		attrs.put("StartTime", ct.startTime);

		attrs.put("EndTime", ct.endTime);

		attrs.put("ReportMode", ct.reportMode);

		if (ct.reportMode.equalsIgnoreCase("0")) {
			attrs.put("ReportInterval", ct.reportInterval);
		}

		if (ct.reportMode.equalsIgnoreCase("1")) {
			attrs.put("ReportTime", ct.reportTime);
		}

		attrs.put("ExpireDays", ct.expireDays);

		return new MessageElement("CycleReportTime", attrs, null);

	}

	/**
	 * v5、v6、v7
	 * 
	 * @param ct
	 *            CircleTask
	 * @return MessageElement
	 */
	private MessageElement circleTaskToElementV5V6V7(
			MsgQualityReportTaskSetCmd.CircleTask ct) {

		Map attrs = new LinkedHashMap(5);

		attrs.put("DayOfWeek", ct.dayofWeek);

		attrs.put("StartTime", ct.startTime);

		attrs.put("EndTime", ct.endTime);

		attrs.put("ReportMode", ct.reportMode);

		if (ct.reportMode.equalsIgnoreCase("0")) {
			attrs.put("ReportInterval", ct.reportInterval);
		}

		if (ct.reportMode.equalsIgnoreCase("1")) {
			attrs.put("ReportTime", ct.reportTime);
		}

		return new MessageElement("CycleReportTime", attrs, null);

	}

	/**
	 * v1
	 * 
	 * @param ct
	 *            CircleTask
	 * @return MessageElement
	 */
	private MessageElement circleTaskToElementFrontier(
			MsgQualityReportTaskSetCmd.CircleTask ct) {

		Map attrs = new LinkedHashMap(8);

		attrs.put("DayOfWeek", ct.dayofWeek);

		attrs.put("StartTime", ct.startTime);

		attrs.put("EndTime", ct.endTime);

		attrs.put("ReportMode", ct.reportMode);

		attrs.put("SampleNumber", ct.sampleNumber);

		// attrs.put("CheckInterval", ct.checkInterval);

		attrs.put("ExpireDays", ct.expireDays);

		return new MessageElement("CycleReportTime", attrs, null);

	}

	/**
	 * 边境v1、v6、v7、v8
	 * 
	 * @param qp
	 *            QualityParam
	 * @return Map
	 */
	private Map qualityParamToElement(MsgQualityReportTaskSetCmd.QualityParam qp) {

		Map attrs = new LinkedHashMap(2);

		attrs.put("Type", qp.type);
		attrs.put("Desc", qp.Desc);

		return attrs;
	}

	/**
	 * 是否报警参数
	 * 
	 * @param qp
	 *            
	 * @return Map
	 */
	private Map alarmParamToElement(MsgQualityReportTaskSetCmd.AlarmParam ap) {

		Map attrs = new LinkedHashMap(2);

		attrs.put("Type", ap.type);
		attrs.put("Desc", ap.desc);

		return attrs;
	}
	
	/**
	 * v5
	 * 
	 * @param qp
	 *            QualityParam
	 * @return Map
	 */
	private Map qualityParamToElementV5(
			MsgQualityReportTaskSetCmd.QualityParam qp) {

		Map attrs = new LinkedHashMap(3);

		attrs.put("Type", qp.type);
		attrs.put("Desc", qp.Desc);
		attrs.put("SampleNumber", qp.sampleNumber);

		return attrs;
	}

	/**
	 * v6、v7、v8
	 * 
	 * @param qt
	 *            QualityTaskSet
	 * @return Map
	 */
	private Map qualityTaskSetToElement(
			MsgQualityReportTaskSetCmd.QualityTaskSet qt) {

		Map attrs = new LinkedHashMap(7);
		attrs.put("EquCode", qt.equCode);
		attrs.put("TaskID", qt.taskID);
		attrs.put("Action", qt.action);
		attrs.put("ValidStartDateTime", qt.validStartDateTime);
		attrs.put("ValidEndDateTime", qt.validEndDateTime);
		attrs.put("SampleNumber", qt.sampleNumber);
		attrs.put("Unit", qt.unit);

		return attrs;
	}

	/**
	 * 边境v1、v5
	 * 
	 * @param qt
	 *            QualityTaskSet
	 * @return Map
	 */
	private Map qualityTaskSetToElementFrontierV5Frontier(
			MsgQualityReportTaskSetCmd.QualityTaskSet qt) {

		Map attrs = new LinkedHashMap(5);
		attrs.put("EquCode", qt.equCode);
		attrs.put("TaskID", qt.taskID);
		attrs.put("Action", qt.action);
		attrs.put("ValidStartDateTime", qt.validStartDateTime);
		attrs.put("ValidEndDateTime", qt.validEndDateTime);

		return attrs;
	}

	/**
	 * v5、v6、v7、v8
	 * 
	 * @param qt
	 *            QualityTask
	 * @return Map
	 */
	private Map qualityTaskToElement(MsgQualityReportTaskSetCmd.QualityTask qt) {

		Map attrs = new LinkedHashMap(1);

		attrs.put("SleepTime", qt.sleepTime);

		return attrs;
	}

	/**
	 * 边境v1
	 * 
	 * @param qt
	 *            QualityTask
	 * @return Map
	 */
	private Map qualityTaskToElementFrontier(
			MsgQualityReportTaskSetCmd.QualityTask qt) {
		Map attrs = new LinkedHashMap(1);
		return attrs;
	}

	/**
	 * 
	 * v8广播接口的builder
	 * 
	 */

	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {

		public BuilderV8Radio() {

		}

		protected MessageElement buildBody() {

			Collection children = new ArrayList();
			Map attrs = new LinkedHashMap();

			for (int i = 0; i < list.size(); i++) {

				TaskChannel tc = (TaskChannel) list.get(i);

				ArrayList vt = tc.getQualityTaskSet();

				ArrayList qm = tc.getQualityParam();

				ArrayList st = tc.getQualityTask();

				ArrayList ch = tc.getChannels();

				ArrayList cy = tc.getCircleTasks();

				ArrayList sss = tc.getSingleTasks();

				Collection c1 = new ArrayList();

				ArrayList alarmParam = tc.getAlarmParam();
				
				Map map = new LinkedHashMap(1);
				Map map2 = new LinkedHashMap(2);
				Map map3 = new LinkedHashMap(1);

				QualityTaskSet v = null;
				QualityTask s = null;
				QualityParam p = null;
				AlarmParam alarmP = null;

				for (int j = 0; j < vt.size(); j++) {
					v = (QualityTaskSet) vt.get(j);
				}
				attrs = qualityTaskSetToElement(v);

				for (int j = 0; j < st.size(); j++) {
					s = (QualityTask) st.get(j);
				}
				if (s != null) {
					map = qualityTaskToElement(s);
				}
				for (int j = 0; j < ch.size(); j++) {
					Channel c = (Channel) ch.get(j);
					c1.add(channelToElement(c));
				}

				for (int k = 0; k < cy.size(); k++) {
					CircleTask cr = (CircleTask) cy.get(k);
					c1.add(circleTaskToElement(cr));
				}

				for (int k = 0; k < sss.size(); k++) {
					SingleTask cr = (SingleTask) sss.get(k);
					c1.add(singleTaskToElement(cr));
				}

				for (int j = 0; j < qm.size(); j++) {
					p = (QualityParam) qm.get(j);
					map2 = qualityParamToElement(p);
					children
							.add(new MessageElement("QualityIndex", map2, null));
				}

				for (int a = 0; a < alarmParam.size(); a++){
					alarmP = (AlarmParam) alarmParam.get(a);
					map3 = alarmParamToElement(alarmP);
					children.add(new MessageElement("QualityAlarmParam", map3, null));
				}
				
				children.add(new MessageElement("QualityTask", map, c1));

			}

			return new MessageElement("QualityTaskSet", attrs, children);
		}
	}

}
