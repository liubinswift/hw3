package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;

/**
 * 
 * 视音频任务设置
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

public class MsgStreamTaskSetCmd extends BaseMessageCommand {

	ArrayList list = new ArrayList();
	private String type;

	public MsgStreamTaskSetCmd() {

		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });

	}

	public void setList(ArrayList li) {

		this.list = li;

	}

	public static class TaskChannel {

		ArrayList streamTaskSet;

		ArrayList streamTask;

		ArrayList channel;

		ArrayList singleReportTime;

		ArrayList cycleReportTime;

		public TaskChannel(ArrayList streamTaskSet, ArrayList streamTask,
				ArrayList channel, ArrayList singleReportTime,
				ArrayList cycleReportTime) {

			this.streamTaskSet = streamTaskSet;
			this.streamTask = streamTask;
			this.channel = channel;
			this.singleReportTime = singleReportTime;
			this.cycleReportTime = cycleReportTime;
		}

		public void setStreamTaskSet(Collection tasks) {

			// assert tasks != null;

			this.streamTaskSet = (ArrayList) tasks;

		}

		public ArrayList getStreamTaskSet() {
			return (ArrayList) this.streamTaskSet;
		}

		public void setStreamTask(Collection tasks) {

			// assert tasks != null;

			this.streamTask = (ArrayList) tasks;

		}

		public ArrayList getStreamTask() {
			return (ArrayList) this.streamTask;
		}

		public void setSingleTasks(Collection tasks) {

			// assert tasks != null;

			this.singleReportTime = (ArrayList) tasks;

		}

		public ArrayList getSingleTasks() {
			return (ArrayList) this.singleReportTime;
		}

		public void setCircleTasks(Collection tasks) {

			// assert tasks != null;

			this.cycleReportTime = (ArrayList) tasks;

		}

		public ArrayList getCircleTasks() {
			return (ArrayList) this.cycleReportTime;
		}

		public void setChannels(Collection channels) {

			// assert tasks != null;

			this.channel = (ArrayList) channels;

		}

		public ArrayList getChannels() {
			return (ArrayList) this.channel;
		}

	}

	/**
	 * 
	 * 包含循环任务所需参数的内部类
	 * 
	 */
	public static class CircleTask {

		private String dayofWeek, startTime, endTime, reportMode, reportTime,
				expireDays, recordLength;

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
				String reportMode, String reportTime, String expireDays,
				String recordLength) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.dayofWeek = dayofWeek;

			this.reportMode = reportMode;

			this.reportTime = reportTime;

			this.expireDays = expireDays;

			this.recordLength = recordLength;

		}

		

		/**
		 * 边境广播V1 用StreamTaskSet所需参数构造对象（同一类型的长参数表，注意顺序）
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
				String reportMode, String expireDays, String recordLength) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.dayofWeek = dayofWeek;

			this.reportMode = reportMode;

			this.expireDays = expireDays;

			this.recordLength = recordLength;

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

		private String startTime, endTime, reportMode, reportTime, expireDays,
				recordLength;

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
				String reportTime, String expireDays, String recordLength) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.reportMode = reportMode;

			this.reportTime = reportTime;

			this.expireDays = expireDays;

			this.recordLength = recordLength;

		}

		/**
		 * V7、V6、V5
		 * 
		 * @param startTime
		 *            String
		 * @param endTime
		 *            String
		 * @param expireDays
		 *            String
		 * @param recordLength
		 *            String
		 */
		public SingleTask(String startTime, String endTime, String expireDays,
				String recordLength) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.expireDays = expireDays;

			this.recordLength = recordLength;

		}

		/**
		 * 边境广播V1 用StreamTaskSet所需参数构造对象（同一类型的长参数表，注意顺序）
		 * 
		 * @param startTime
		 *            起始时间
		 * @param endTime
		 *            结束时间
		 * @param expireDays
		 *            过期时间。单位：天
		 */

		public SingleTask(String startTime, String endTime, String reportMode,
				String expireDays, String recordLength) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.reportMode = reportMode;

			this.expireDays = expireDays;

			this.recordLength = recordLength;

		}

	}

	public static class Channel {

		private String freq, band,stationName,language;

		/**
		 * V8、V7、V6、V5、边境广播V1
		 * 
		 * @param band
		 *            波段
		 * @param freq
		 *            频率，单位为KHz。必须有效
		 */
		public Channel(String freq, String band,String stationName,String language) {

			this.freq = freq;

			this.band = band;
			
			this.stationName = stationName;
			this.language = language;

		}

		/**
		 * 边境电视V1
		 * 
		 * @param freq
		 *            String
		 */
		public Channel(String freq) {

			this.freq = freq;

		}

	}

	public static class StreamTaskSet {

		private String equCode, taskID, action, validStartDateTime,
				validEndDateTime, bps, encode, width, height, fps;

		/**
		 * V8、V7、V6、V5、边境广播V1
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
		 * @param bps
		 *            String
		 * @param encode
		 *            String
		 */
		public StreamTaskSet(String equCode, String taskID, String action,
				String validStartDateTime, String validEndDateTime, String bps,
				String encode) {

			this.equCode = equCode;

			this.taskID = taskID;

			this.action = action;

			this.validStartDateTime = validStartDateTime;

			this.validEndDateTime = validEndDateTime;

			this.bps = bps;

			this.encode = encode;

		}

		/**
		 * 边境电视V1
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
		 * @param bps
		 *            String
		 * @param encode
		 *            String
		 * @param width
		 *            String
		 * @param height
		 *            String
		 */
		public StreamTaskSet(String equCode, String taskID, String action,
				String validStartDateTime, String validEndDateTime, String bps,
				String encode, String width, String height, String fps) {

			this.equCode = equCode;

			this.taskID = taskID;

			this.action = action;

			this.validStartDateTime = validStartDateTime;

			this.validEndDateTime = validEndDateTime;

			this.bps = bps;

			this.encode = encode;

			this.width = width;

			this.height = height;

			this.fps = fps;

		}

	}

	/**
	 * 
	 * 包含StreamTask所需参数的内部类
	 * 
	 */
	public static class StreamTask {

		private String sleepTime;

		/**
		 * 边境广播、电视
		 */
		public StreamTask() {
		}

		/**
		 * V8、V7、V6、V5
		 * 
		 * @param sleepTime
		 *            String
		 */
		public StreamTask(String sleepTime) {

			this.sleepTime = sleepTime;
		}

	}

	private MessageElement channelToElement(MsgStreamTaskSetCmd.Channel tc) {

		Map attrs = new LinkedHashMap(2);

		attrs.put("Freq", tc.freq);
		attrs.put("Band", tc.band);
		attrs.put("StationName", tc.stationName);
		attrs.put("LanguageName", tc.language);

		return new MessageElement("Channel", attrs, null);

	}

	private MessageElement channelToElementFrontierVideoV1(
			MsgStreamTaskSetCmd.Channel tc) {

		Map attrs = new LinkedHashMap(1);

		attrs.put("Freq", tc.freq);

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
			MsgStreamTaskSetCmd.SingleTask st) {

		Map attrs = new LinkedHashMap(6);

		attrs.put("StartDateTime", st.startTime);

		attrs.put("EndDateTime", st.endTime);

		attrs.put("RecordLength", st.recordLength);

		attrs.put("ExpireDays", st.expireDays);

		attrs.put("ReportMode", st.reportMode);

		if (st.reportMode.equalsIgnoreCase("1")) {
			attrs.put("ReportTime", st.reportTime);
		}

		return new MessageElement("SingleReportTime", attrs, null);

	}

	/**
	 * V7V6V5
	 * 
	 * @param st
	 *            SingleTask
	 * @return MessageElement
	 */
	private MessageElement singleTaskToElementV7V6V5(
			MsgStreamTaskSetCmd.SingleTask st) {

		Map attrs = new LinkedHashMap(4);

		attrs.put("StartDateTime", st.startTime);

		attrs.put("EndDateTime", st.endTime);

		attrs.put("RecordLength", st.recordLength);

		attrs.put("ExpireDays", st.expireDays);

		return new MessageElement("SingleReportTime", attrs, null);

	}

	/**
	 * 边境广播电视V1
	 * 
	 * @param st
	 *            SingleTask
	 * @return MessageElement
	 */
	private MessageElement singleTaskToElementFrontier(
			MsgStreamTaskSetCmd.SingleTask st) {

		Map attrs = new LinkedHashMap(6);

		attrs.put("StartDateTime", st.startTime);

		attrs.put("EndDateTime", st.endTime);

		attrs.put("RecordLength", st.recordLength);

		attrs.put("ExpireDays", st.expireDays);

		attrs.put("ReportMode", st.reportMode);

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
			MsgStreamTaskSetCmd.CircleTask ct) {

		Map attrs = new LinkedHashMap(6);

		attrs.put("DayOfWeek", ct.dayofWeek);

		attrs.put("StartTime", ct.startTime);

		attrs.put("EndTime", ct.endTime);

		attrs.put("RecordLength", ct.recordLength);

		attrs.put("ExpireDays", ct.expireDays);

		attrs.put("ReportMode", ct.reportMode);

		if (ct.reportMode.equalsIgnoreCase("1")) {
			attrs.put("ReportTime", ct.reportTime);
		}

		return new MessageElement("CycleReportTime", attrs, null);

	}

	/**
	 * 边境广播电视V1
	 * 
	 * @param ct
	 *            CircleTask
	 * @return MessageElement
	 */
	private MessageElement circleTaskToElementFrontier(
			MsgStreamTaskSetCmd.CircleTask ct) {

		Map attrs = new LinkedHashMap(6);

		attrs.put("DayOfWeek", ct.dayofWeek);

		attrs.put("StartTime", ct.startTime);

		attrs.put("EndTime", ct.endTime);

		attrs.put("RecordLength", ct.recordLength);

		attrs.put("ExpireDays", ct.expireDays);

		attrs.put("ReportMode", ct.reportMode);

		return new MessageElement("CycleReportTime", attrs, null);

	}

	/**
	 * V7V6V5
	 * 
	 * @param ct
	 *            CircleTask
	 * @return MessageElement
	 */
	private MessageElement circleTaskToElementV7V6V5(
			MsgStreamTaskSetCmd.CircleTask ct) {

		Map attrs = new LinkedHashMap(6);

		attrs.put("DayOfWeek", ct.dayofWeek);

		attrs.put("StartTime", ct.startTime);

		attrs.put("EndTime", ct.endTime);

		attrs.put("RecordLength", ct.recordLength);

		attrs.put("ExpireDays", ct.expireDays);

		return new MessageElement("CycleReportTime", attrs, null);

	}

	private Map streamTaskSetToElement(MsgStreamTaskSetCmd.StreamTaskSet qt) {

		Map attrs = new LinkedHashMap(7);

		attrs.put("TaskID", qt.taskID);
		attrs.put("Action", qt.action);
		attrs.put("EquCode", qt.equCode);
		attrs.put("Bps", qt.bps);
		attrs.put("Encode", qt.encode);
		attrs.put("ValidStartDateTime", qt.validStartDateTime);
		attrs.put("ValidEndDateTime", qt.validEndDateTime);

		return attrs;
	}

	private Map streamTaskSetToElementFrontVideo(
			MsgStreamTaskSetCmd.StreamTaskSet qt) {

		Map attrs = new LinkedHashMap(10);

		attrs.put("TaskID", qt.taskID);
		attrs.put("Action", qt.action);
		attrs.put("EquCode", qt.equCode);
		attrs.put("Width", qt.width);
		attrs.put("Height", qt.height);
		attrs.put("Fps", qt.fps);
		attrs.put("Bps", qt.bps);
		attrs.put("Encode", qt.encode);
		attrs.put("ValidStartDateTime", qt.validStartDateTime);
		attrs.put("ValidEndDateTime", qt.validEndDateTime);

		return attrs;
	}

	private Map streamTaskToElement(MsgStreamTaskSetCmd.StreamTask qt) {
		Map attrs = new LinkedHashMap(1);
		attrs.put("SleepTime", qt.sleepTime);

		return attrs;
	}

	private Map streamTaskToElementFrontier(MsgStreamTaskSetCmd.StreamTask qt) {
		Map attrs = new LinkedHashMap(1);
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

				ArrayList vt = tc.getStreamTaskSet();

				ArrayList st = tc.getStreamTask();

				ArrayList ch = tc.getChannels();

				ArrayList cy = tc.getCircleTasks();

				ArrayList sss = tc.getSingleTasks();

				Collection c1 = new ArrayList();

				Map map = new LinkedHashMap(1);

				StreamTaskSet v = null;

				StreamTask s = null;

				for (int j = 0; j < vt.size(); j++) {
					v = (StreamTaskSet) vt.get(j);
				}
				attrs = streamTaskSetToElement(v);

				for (int j = 0; j < st.size(); j++) {
					s = (StreamTask) st.get(j);
				}
				if (s != null) {
					map = streamTaskToElement(s);
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

				children.add(new MessageElement("StreamTask", map, c1));

			}

			return new MessageElement("StreamTaskSet", attrs, children);
		}
	}


}
