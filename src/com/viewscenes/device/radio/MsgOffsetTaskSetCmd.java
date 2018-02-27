package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;

/**
 * 
 * 频偏任务设置
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

public class MsgOffsetTaskSetCmd extends BaseMessageCommand {

	private Collection singleTasks;

	private Collection circleTasks;

	private Collection channels;

	private String levelSampleNum;

	ArrayList list = new ArrayList();

	public MsgOffsetTaskSetCmd() {

		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });

	}

	public void setList(ArrayList li) {

		this.list = li;

	}

	public static class TaskChannel {

		// String Code;

		ArrayList offsetTaskSet;

		ArrayList offsetTask;

		ArrayList channel;

		ArrayList singleReportTime;

		ArrayList cycleReportTime;

		ArrayList qualityIndex;

		/**
		 * V8、V7、V6,V5
		 * 
		 * @author 王福祥 修改
		 * @date 2011/03/11
		 * @param offsetTaskSet
		 *            ArrayList
		 * @param offsetTask
		 *            ArrayList
		 * @param channel
		 *            ArrayList
		 * @param singleReportTime
		 *            ArrayList
		 * @param cycleReportTime
		 *            ArrayList
		 */
		public TaskChannel(ArrayList offsetTaskSet, ArrayList offsetTask,
				ArrayList qualityIndex, ArrayList channel,
				ArrayList singleReportTime, ArrayList cycleReportTime) {

			this.offsetTaskSet = offsetTaskSet;
			this.offsetTask = offsetTask;
			this.channel = channel;
			this.singleReportTime = singleReportTime;
			this.cycleReportTime = cycleReportTime;
			this.qualityIndex = qualityIndex;
		}

		public void setOffsetTaskSet(Collection tasks) {

			this.offsetTaskSet = (ArrayList) tasks;

		}

		public ArrayList getOffsetTaskSet() {
			return (ArrayList) this.offsetTaskSet;
		}

		public void setOffsetTask(Collection tasks) {

			this.offsetTask = (ArrayList) tasks;

		}

		public ArrayList getOffsetTask() {
			return (ArrayList) this.offsetTask;
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

		public void setQualityIndex(Collection qis) {
			this.qualityIndex = (ArrayList) qis;
		}

		public ArrayList getQualityIndex() {
			return (ArrayList) this.qualityIndex;
		}

	}

	public void setLevelSampleNum(String levelSampleNum) {

		this.levelSampleNum = levelSampleNum;
	}

	public String getLevelSampleNum() {

		return this.levelSampleNum;
	}

	/**
	 * 
	 * 包含循环任务所需参数的内部类
	 * 
	 */
	public static class CircleTask {

		private String dayofWeek, startTime, endTime, reportMode,
				reportInterval, reportTime, expireDays;

		/**
		 * V8
		 * 
		 * @param startTime
		 *            String
		 * @param endTime
		 *            String
		 * @param dayofWeek
		 *            String
		 * @param reportMode
		 *            String
		 * @param reportInterval
		 *            String
		 * @param reportTime
		 *            String
		 * @param expireDays
		 *            String
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
		 * V7、V6、V5
		 * 
		 * @param startTime
		 *            String
		 * @param endTime
		 *            String
		 * @param dayofWeek
		 *            String
		 * @param reportMode
		 *            String
		 * @param reportInterval
		 *            String
		 * @param reportTime
		 *            String
		 */
		public CircleTask(String startTime, String endTime, String dayofWeek,
				String reportMode, String reportInterval, String reportTime) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.dayofWeek = dayofWeek;

			this.reportMode = reportMode;

			this.reportInterval = reportInterval;

			this.reportTime = reportTime;

		}

	}

	/**
	 * 
	 * 包含单次任务所需参数的内部类
	 * 
	 */

	public static class SingleTask {

		private String startTime, endTime, reportMode, reportInterval,
				reportTime, expireDays;

		/**
		 * V8
		 * 
		 * @param startTime
		 *            String
		 * @param endTime
		 *            String
		 * @param reportMode
		 *            String
		 * @param reportInterval
		 *            String
		 * @param reportTime
		 *            String
		 * @param expireDays
		 *            String
		 */
		public SingleTask(String startTime, String endTime, String reportMode,
				String reportInterval, String reportTime, String expireDays) {

			// assert startTime != null && endTime != null && expireDays !=
			// null;

			this.startTime = startTime;

			this.endTime = endTime;

			this.reportMode = reportMode;

			this.reportInterval = reportInterval;

			this.reportTime = reportTime;

			this.expireDays = expireDays;

		}

		/**
		 * V7、V6、V5
		 * 
		 * @param startTime
		 *            String
		 * @param endTime
		 *            String
		 * @param reportMode
		 *            String
		 * @param reportInterval
		 *            String
		 * @param reportTime
		 *            String
		 * @param expireDays
		 *            String
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
	 * 频道的内部类
	 * 
	 */
	public static class Channel {

		private String freq, band;

		/**
		 * V8、V7、V6、V5
		 * 
		 * @param freq
		 *            String
		 * @param band
		 *            String
		 */
		public Channel(String freq, String band) {

			this.freq = freq;

			this.band = band;

		}

	}

	public static class OffsetTaskSet {

		private String equCode, taskID, action, validStartDateTime,
				validEndDateTime, sampleNumber, unit;

		/**
		 * V8、V7、V6
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
		public OffsetTaskSet(String equCode, String taskID, String action,
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
		 * V5
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
		public OffsetTaskSet(String equCode, String taskID, String action,
				String validStartDateTime, String validEndDateTime) {

			this.equCode = equCode;

			this.taskID = taskID;

			this.action = action;

			this.validStartDateTime = validStartDateTime;

			this.validEndDateTime = validEndDateTime;

		}

	}

	/**
	 * 
	 * 包含所需参数的内部类
	 * 
	 */

	public static class OffsetTask {

		private String sleepTime;

		/**
		 * V8、V7、V6、V5
		 * 
		 * @param sleepTime
		 *            String
		 */
		public OffsetTask(String sleepTime) {

			this.sleepTime = sleepTime;
		}

	}

	/**
	 * V5版本
	 * 
	 * @author 王福祥
	 * @date 2011/03/11修改 v5版本增加了<QualityIndex Type="6" Desc="Offset"
	 *       SampleNumber="10"/>节点
	 */
	public static class QualityIndex {

		private String type, desc, sampleNumber;

		public QualityIndex(String type, String desc, String sampleNumber) {

			this.type = type;
			this.desc = desc;
			this.sampleNumber = sampleNumber;

		}
	}

	/**
	 * V8、V7、V6、V5
	 * 
	 * @param tc
	 *            Channel
	 * @return MessageElement
	 */
	private MessageElement channelToElement(MsgOffsetTaskSetCmd.Channel tc) {

		Map attrs = new LinkedHashMap(2);

		attrs.put("Freq", tc.freq);
		attrs.put("Band", tc.band);

		return new MessageElement("Channel", attrs, null);

	}

	/**
	 * V8
	 * 
	 * @param st
	 *            SingleTask
	 * @return MessageElement
	 */
	private MessageElement singleTaskToElementV8(
			MsgOffsetTaskSetCmd.SingleTask st) {

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
	 * V7、V6、V5
	 * 
	 * @param st
	 *            SingleTask
	 * @return MessageElement
	 */
	private MessageElement singleTaskToElementV7V6V5(
			MsgOffsetTaskSetCmd.SingleTask st) {

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
	 * V8
	 * 
	 * @param ct
	 *            CircleTask
	 * @return MessageElement
	 */
	private MessageElement circleTaskToElementV8(
			MsgOffsetTaskSetCmd.CircleTask ct) {

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
	 * V7、V6、V5
	 * 
	 * @param ct
	 *            CircleTask
	 * @return MessageElement
	 */
	private MessageElement circleTaskToElementV7V6V5(
			MsgOffsetTaskSetCmd.CircleTask ct) {

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
	 * V8、V7、V6
	 * 
	 * @param qt
	 *            OffsetTaskSet
	 * @return Map
	 */
	private Map offsetTaskSetToElementV8V7V6(
			MsgOffsetTaskSetCmd.OffsetTaskSet qt) {

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
	 * V5
	 * 
	 * @author 王福祥
	 * @date 2011/03/11 V5版本有<QualityIndex Type="6" Desc="Offset"
	 *       SampleNumber="10"/>节点
	 * @param qt
	 *            OffsetTaskSet
	 * @return Map
	 */
	private Map qualityIndexToElementV5(MsgOffsetTaskSetCmd.QualityIndex qi) {

		Map attrs = new LinkedHashMap(3);
		attrs.put("Type", qi.type);
		attrs.put("Desc", qi.desc);
		attrs.put("SampleNumber", qi.sampleNumber);

		return attrs;
	}

	/**
	 * V5
	 * 
	 * @param qt
	 *            OffsetTaskSet
	 * @return Map
	 */
	private Map offsetTaskSetToElementV5(MsgOffsetTaskSetCmd.OffsetTaskSet qt) {

		Map attrs = new LinkedHashMap(5);
		attrs.put("EquCode", qt.equCode);
		attrs.put("TaskID", qt.taskID);
		attrs.put("Action", qt.action);
		attrs.put("ValidStartDateTime", qt.validStartDateTime);
		attrs.put("ValidEndDateTime", qt.validEndDateTime);

		return attrs;
	}

	/**
	 * V8、V7、V6、V5
	 * 
	 * @param qt
	 *            OffsetTask
	 * @return Map
	 */
	private Map offsetTaskToElement(MsgOffsetTaskSetCmd.OffsetTask qt) {
		Map attrs = new LinkedHashMap(1);
		attrs.put("SleepTime", qt.sleepTime);

		return attrs;
	}

	/**
	 * 
	 * V8具体实现
	 * 
	 */
	protected class BuilderV8Radio extends
			com.viewscenes.device.framework.BaseMessageCommand.BuilderV8Radio {

		public BuilderV8Radio() {
		}

		protected MessageElement buildBody() {
			Collection children = new ArrayList();
			Map attrs = new LinkedHashMap();

			for (int i = 0; i < list.size(); i++) {

				TaskChannel tc = (TaskChannel) list.get(i);

				ArrayList vt = tc.getOffsetTaskSet();

				ArrayList st = tc.getOffsetTask();

				ArrayList ch = tc.getChannels();

				ArrayList cy = tc.getCircleTasks();

				ArrayList sss = tc.getSingleTasks();

				Collection c1 = new ArrayList();

				Map map = new LinkedHashMap(1);

				OffsetTaskSet v = null;
				OffsetTask s = null;

				for (int j = 0; j < vt.size(); j++) {
					v = (OffsetTaskSet) vt.get(j);
				}
				attrs = offsetTaskSetToElementV8V7V6(v);

				for (int j = 0; j < st.size(); j++) {
					s = (OffsetTask) st.get(j);
				}
				if (s != null) {
					map = offsetTaskToElement(s);
				}
				for (int j = 0; j < ch.size(); j++) {
					Channel c = (Channel) ch.get(j);
					c1.add(channelToElement(c));
				}

				for (int k = 0; k < cy.size(); k++) {
					CircleTask cr = (CircleTask) cy.get(k);
					c1.add(circleTaskToElementV8(cr));
				}

				for (int k = 0; k < sss.size(); k++) {
					SingleTask cr = (SingleTask) sss.get(k);
					c1.add(singleTaskToElementV8(cr));
				}

				children.add(new MessageElement("QualityTask", map, c1));// 按文档改动，字符串OffsetTask改为字符串QualityTask

			}

			return new MessageElement("OffsetTaskSet", attrs, children);
		}
	}


}
