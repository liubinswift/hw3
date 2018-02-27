package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;

/**
 * 
 * 频谱任务设置
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

public class MsgSpectrumTaskSetCmd extends BaseMessageCommand {

	ArrayList list = new ArrayList();

	public MsgSpectrumTaskSetCmd() {

		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });

	}

	public void setList(ArrayList li) {

		this.list = li;

	}

	public static class TaskChannel {

		ArrayList spectrumTaskSet;

		ArrayList spectrumTask;

		ArrayList singleReportTime;

		ArrayList cycleReportTime;

		public TaskChannel(ArrayList spectrumTaskSet, ArrayList spectrumTask,
				ArrayList singleReportTime, ArrayList cycleReportTime) {

			this.spectrumTaskSet = spectrumTaskSet;
			this.spectrumTask = spectrumTask;
			this.singleReportTime = singleReportTime;
			this.cycleReportTime = cycleReportTime;
		}

		public void setSpectrumTaskSet(Collection tasks) {

			this.spectrumTaskSet = (ArrayList) tasks;

		}

		public ArrayList getSpectrumTaskSet() {
			return (ArrayList) this.spectrumTaskSet;
		}

		public void setSpectrumTask(Collection tasks) {

			this.spectrumTask = (ArrayList) tasks;

		}

		public ArrayList getSpectrumTask() {
			return (ArrayList) this.spectrumTask;
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

	}

	/**
	 * 
	 * 包含循环任务所需参数的内部类
	 * 
	 */
	public static class CircleTask {

		private String dayofWeek, startTime, endTime, reportMode,
				reportInterval, reportTime, expireDays, sampleNumber;

		/**
		 * V8 用StreamTaskSet所需参数构造对象（同一类型的长参数表，注意顺序）
		 * 
		 * @param startTime
		 *            起始时间
		 * @param endTime
		 *            结束时间
		 * @param expireDays
		 *            过期时间。单位：天
		 * @param dayofWeek
		 *            每周的第几天，全部为"All"
		 */
		public CircleTask(String startTime, String endTime, String dayofWeek,
				String reportMode, String reportInterval, String reportTime,
				String expireDays) {

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

		/**
		 * 边境广播电视V1,修改后也可用于广播v8使用。
		 * 
		 * @param startTime
		 *            String
		 * @param endTime
		 *            String
		 * @param dayofWeek
		 *            String
		 * @param reportMode
		 *            String
		 * @param expireDays
		 *            String
		 * @param sampleNumber
		 *            String
		 * @param reportInterval
		 *            String 输入空值
		 * @param reportTime
		 *            String 输入空值
		 */
		public CircleTask(String startTime, String endTime, String dayofWeek,
				String reportMode, String expireDays, String sampleNumber,
				String reportInterval, String reportTime) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.dayofWeek = dayofWeek;

			this.reportMode = reportMode;

			this.expireDays = expireDays;

			this.sampleNumber = sampleNumber;
			this.reportInterval = reportInterval;
			this.reportTime = reportTime;

		}

	}

	/**
	 * 
	 * 包含StreamTaskSet单次任务所需参数的内部类
	 * 
	 */
	public static class SingleTask {

		private String startTime, endTime, reportMode, reportInterval,
				reportTime, expireDays, sampleNumber;

		/**
		 * V8 用StreamTaskSet所需参数构造对象（同一类型的长参数表，注意顺序）
		 * 
		 * @param startTime
		 *            起始时间
		 * @param endTime
		 *            结束时间
		 * @param expireDays
		 *            过期时间。单位：天
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
		 */
		public SingleTask(String startTime, String endTime, String reportMode,
				String reportInterval, String reportTime) {

			// assert startTime != null && endTime != null && expireDays !=
			// null;

			this.startTime = startTime;

			this.endTime = endTime;

			this.reportMode = reportMode;

			this.reportInterval = reportInterval;

			this.reportTime = reportTime;

		}

		/**
		 * 边境广播电视V1,也可用于v8
		 * 
		 * @param startTime
		 *            String
		 * @param endTime
		 *            String
		 * @param reportMode
		 *            String
		 * @param expireDays
		 *            String
		 * @param sampleNumber
		 *            String
		 * @param reportInterval
		 *            String
		 * @param reportTime
		 *            String
		 */
		public SingleTask(String startTime, String endTime, String reportMode,
				String expireDays, String sampleNumber, String reportInterval,
				String reportTime) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.reportMode = reportMode;

			this.reportInterval = reportInterval;

			this.reportTime = reportTime;

			this.expireDays = expireDays;

			this.sampleNumber = sampleNumber;

		}

	}

	public static class SpectrumTaskSet {

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
		public SpectrumTaskSet(String equCode, String taskID, String action,
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
		 * @param sampleNumber
		 *            String
		 */
		public SpectrumTaskSet(String equCode, String taskID, String action,
				String validStartDateTime, String validEndDateTime,
				String sampleNumber) {

			this.equCode = equCode;

			this.taskID = taskID;

			this.action = action;

			this.validStartDateTime = validStartDateTime;

			this.validEndDateTime = validEndDateTime;

			this.sampleNumber = sampleNumber;

		}

		/**
		 * 边境广播电视V1
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
		 */
		public SpectrumTaskSet(String equCode, String taskID, String action,
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
	 * 包含SpectrumTask所需参数的内部类
	 * 
	 */
	public static class SpectrumTask {

		private String sleepTime, band, taskType, startFreq, endFreq, stepFreq;

		/**
		 * V8、V7、V6、V5
		 * 
		 * @param band
		 *            String
		 * @param taskType
		 *            String
		 * @param startFreq
		 *            String
		 * @param endFreq
		 *            String
		 * @param stepFreq
		 *            String
		 * @param sleepTime
		 *            String
		 */
		public SpectrumTask(String band, String taskType, String startFreq,
				String endFreq, String stepFreq, String sleepTime) {

			this.band = band;
			this.taskType = taskType;
			this.startFreq = startFreq;
			this.endFreq = endFreq;
			this.stepFreq = stepFreq;
			this.sleepTime = sleepTime;

		}

		/**
		 * 边境广播V1
		 * 
		 * @param band
		 *            String
		 * @param startFreq
		 *            String
		 * @param endFreq
		 *            String
		 * @param stepFreq
		 *            String
		 */
		public SpectrumTask(String band, String startFreq, String endFreq,
				String stepFreq) {

			this.band = band;
			this.startFreq = startFreq;
			this.endFreq = endFreq;
			this.stepFreq = stepFreq;

		}

		/**
		 * 边境电视V1
		 * 
		 * @param taskType
		 *            String
		 * @param startFreq
		 *            String
		 * @param endFreq
		 *            String
		 * @param stepFreq
		 *            String
		 * @param FrontierVideo
		 *            String 输入空值
		 */
		public SpectrumTask(String taskType, String startFreq, String endFreq,
				String stepFreq, String FrontierVideo) {

			this.taskType = taskType;
			this.startFreq = startFreq;
			this.endFreq = endFreq;
			this.stepFreq = stepFreq;

		}

	}

	/**
	 * V8
	 * 
	 * @param st
	 *            SingleTask
	 * @return MessageElement
	 */
	private MessageElement singleTaskToElementV8(
			MsgSpectrumTaskSetCmd.SingleTask st) {

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
			MsgSpectrumTaskSetCmd.SingleTask st) {

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

		return new MessageElement("SingleReportTime", attrs, null);

	}

	/**
	 * 边境广播电视V1
	 * 
	 * @param st
	 *            SingleTask
	 * @return MessageElement
	 */
	private MessageElement singleTaskToElementFrontierV1(
			MsgSpectrumTaskSetCmd.SingleTask st) {

		Map attrs = new LinkedHashMap(4);

		attrs.put("StartDateTime", st.startTime);

		attrs.put("EndDateTime", st.endTime);

		attrs.put("SampleNumber", st.sampleNumber);

		attrs.put("ReportMode", st.reportMode);

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
			MsgSpectrumTaskSetCmd.CircleTask ct) {

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
			MsgSpectrumTaskSetCmd.CircleTask ct) {

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

		return new MessageElement("CycleReportTime", attrs, null);

	}

	/**
	 * 边境广播电视
	 * 
	 * @param ct
	 *            CircleTask
	 * @return MessageElement
	 */
	private MessageElement circleTaskToElementFrontierV1(
			MsgSpectrumTaskSetCmd.CircleTask ct) {

		Map attrs = new LinkedHashMap(5);

		attrs.put("DayOfWeek", ct.dayofWeek);

		attrs.put("StartTime", ct.startTime);

		attrs.put("EndTime", ct.endTime);

		attrs.put("SampleNumber", ct.sampleNumber);

		attrs.put("ReportMode", ct.reportMode);

		attrs.put("ExpireDays", ct.expireDays);

		return new MessageElement("CycleReportTime", attrs, null);

	}

	/**
	 * V8、V7、V6
	 * 
	 * @param qt
	 *            SpectrumTaskSet
	 * @return Map
	 */
	private Map spectrumTaskSetToElementV8V7V6(
			MsgSpectrumTaskSetCmd.SpectrumTaskSet qt) {

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
	 * @param qt
	 *            SpectrumTaskSet
	 * @return Map
	 */
	private Map spectrumTaskSetToElementV5(
			MsgSpectrumTaskSetCmd.SpectrumTaskSet qt) {

		Map attrs = new LinkedHashMap(6);
		attrs.put("EquCode", qt.equCode);
		attrs.put("TaskID", qt.taskID);
		attrs.put("Action", qt.action);
		attrs.put("ValidStartDateTime", qt.validStartDateTime);
		attrs.put("ValidEndDateTime", qt.validEndDateTime);
		attrs.put("SampleNumber", qt.sampleNumber);

		return attrs;
	}

	/**
	 * V5
	 * 
	 * @param qt
	 *            SpectrumTaskSet
	 * @return Map
	 */
	private Map spectrumTaskSetToElementFrontierV1(
			MsgSpectrumTaskSetCmd.SpectrumTaskSet qt) {

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
	 *            SpectrumTask
	 * @return Map
	 */
	private Map spectrumTaskToElementV8V7V6V5(
			MsgSpectrumTaskSetCmd.SpectrumTask qt) {

		Map attrs = new LinkedHashMap(6);
		attrs.put("Band", qt.band);
		attrs.put("TaskType", qt.taskType);
		attrs.put("StartFreq", qt.startFreq);
		attrs.put("EndFreq", qt.endFreq);
		attrs.put("StepFreq", qt.stepFreq);
		attrs.put("SleepTime", qt.sleepTime);

		return attrs;
	}

	/**
	 * 边境广播V1
	 * 
	 * @param qt
	 *            SpectrumTask
	 * @return Map
	 */
	private Map spectrumTaskToElementFrontierRadioV1(
			MsgSpectrumTaskSetCmd.SpectrumTask qt) {

		Map attrs = new LinkedHashMap(4);
		attrs.put("Band", qt.band);
		attrs.put("StartFreq", qt.startFreq);
		attrs.put("EndFreq", qt.endFreq);
		attrs.put("StepFreq", qt.stepFreq);

		return attrs;
	}

	/**
	 * 边境电视V1
	 * 
	 * @param qt
	 *            SpectrumTask
	 * @return Map
	 */
	private Map spectrumTaskToElementFrontierVideoV1(
			MsgSpectrumTaskSetCmd.SpectrumTask qt) {

		Map attrs = new LinkedHashMap(4);
		attrs.put("TaskType", qt.taskType);
		attrs.put("StartFreq", qt.startFreq);
		attrs.put("EndFreq", qt.endFreq);
		attrs.put("StepFreq", qt.stepFreq);

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

				ArrayList vt = tc.getSpectrumTaskSet();

				ArrayList st = tc.getSpectrumTask();

				ArrayList cy = tc.getCircleTasks();

				ArrayList sss = tc.getSingleTasks();

				Collection c1 = new ArrayList();

				Map map = new LinkedHashMap(6);

				SpectrumTaskSet v = null;

				SpectrumTask s = null;

				for (int j = 0; j < vt.size(); j++) {
					v = (SpectrumTaskSet) vt.get(j);
				}
				attrs = spectrumTaskSetToElementV8V7V6(v);

				for (int j = 0; j < st.size(); j++) {
					s = (SpectrumTask) st.get(j);
				}
				if (s != null) {
					map = spectrumTaskToElementV8V7V6V5(s);
				}
				for (int k = 0; k < cy.size(); k++) {
					CircleTask cr = (CircleTask) cy.get(k);
					c1.add(circleTaskToElementV8(cr));
				}

				for (int k = 0; k < sss.size(); k++) {
					SingleTask cr = (SingleTask) sss.get(k);
					c1.add(singleTaskToElementV8(cr));
				}

				children.add(new MessageElement("SpectrumTask", map, c1));

			}

			return new MessageElement("SpectrumTaskSet", attrs, children);
		}
	}

}
