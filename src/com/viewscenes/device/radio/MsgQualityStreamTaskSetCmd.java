package com.viewscenes.device.radio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

/**
 * 
 * ��ָ���¼������ӿ�
 * 
 * ���ݺ�����������
 * ¼����ΪЧ��¼��������¼��
 * ������ͬʱ��ָ�������¼������
 * ������������¼���е�ָ������������������Ч��¼������
 * ���Կ�������½ӿڣ���ԭָ�������¼������ļ���
 * ��ָ������������<QualityAlarmParam Type=��0�� Desc=��Alarm/NoAlarm��/>�ڵ�
 * ͨ���ýڵ��Ƿ񱨾�������Ч��¼����������¼������
 * @author thinkpad ̷��ΰ
 *
 */
public class MsgQualityStreamTaskSetCmd extends BaseMessageCommand{
	
	ArrayList streamList = new ArrayList();
	public MsgQualityStreamTaskSetCmd() {

		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });

	}

	public void setStreamList(ArrayList li) {

		this.streamList = li;

	}

	public static class StreamTaskChannel {

		ArrayList streamTaskSet;

		ArrayList streamTask;

		ArrayList channel;

		ArrayList singleReportTime;

		ArrayList cycleReportTime;

		public StreamTaskChannel(ArrayList streamTaskSet, ArrayList streamTask,
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
	 * ����ѭ����������������ڲ���
	 * 
	 */
	public static class StreamCircleTask {

		private String dayofWeek, startTime, endTime, reportMode, reportTime,
				expireDays, recordLength;

		/**
		 * V8 ��StreamTaskSet��������������ͬһ���͵ĳ�������ע��˳��
		 * 
		 * @param startTime
		 *            ��ʼʱ��
		 * @param endTime
		 *            ����ʱ��
		 * @param expireDays
		 *            ����ʱ�䡣��λ����
		 * @param dayofWeek
		 *            ÿ�ܵĵڼ��죬ȫ��Ϊ"All"
		 */

		public StreamCircleTask(String startTime, String endTime, String dayofWeek,
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
		 * �߾��㲥V1 ��StreamTaskSet��������������ͬһ���͵ĳ�������ע��˳��
		 * 
		 * @param startTime
		 *            ��ʼʱ��
		 * @param endTime
		 *            ����ʱ��
		 * @param expireDays
		 *            ����ʱ�䡣��λ����
		 * @param dayofWeek
		 *            ÿ�ܵĵڼ��죬ȫ��Ϊ"All"
		 */

		public StreamCircleTask(String startTime, String endTime, String dayofWeek,
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
	 * ����StreamTaskSet������������������ڲ���
	 * 
	 * @version 1.0
	 * 
	 */

	public static class StreamSingleTask {

		private String startTime, endTime, reportMode, reportTime, expireDays,
				recordLength;

		/**
		 * V8 ��StreamTaskSet��������������ͬһ���͵ĳ�������ע��˳��
		 * 
		 * @param startTime
		 *            ��ʼʱ��
		 * @param endTime
		 *            ����ʱ��
		 * @param expireDays
		 *            ����ʱ�䡣��λ����
		 */

		public StreamSingleTask(String startTime, String endTime, String reportMode,
				String reportTime, String expireDays, String recordLength) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.reportMode = reportMode;

			this.reportTime = reportTime;

			this.expireDays = expireDays;

			this.recordLength = recordLength;

		}

		/**
		 * V7��V6��V5
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
		public StreamSingleTask(String startTime, String endTime, String expireDays,
				String recordLength) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.expireDays = expireDays;

			this.recordLength = recordLength;

		}

		/**
		 * �߾��㲥V1 ��StreamTaskSet��������������ͬһ���͵ĳ�������ע��˳��
		 * 
		 * @param startTime
		 *            ��ʼʱ��
		 * @param endTime
		 *            ����ʱ��
		 * @param expireDays
		 *            ����ʱ�䡣��λ����
		 */

		public StreamSingleTask(String startTime, String endTime, String reportMode,
				String expireDays, String recordLength) {

			this.startTime = startTime;

			this.endTime = endTime;

			this.reportMode = reportMode;

			this.expireDays = expireDays;

			this.recordLength = recordLength;

		}

	}

	public static class StreamChannel {

		private String freq, band,stationName,language;

		/**
		 * V8��V7��V6��V5���߾��㲥V1
		 * 
		 * @param band
		 *            ����
		 * @param freq
		 *            Ƶ�ʣ���λΪKHz��������Ч
		 */
		public StreamChannel(String freq, String band,String stationName,String language) {

			this.freq = freq;

			this.band = band;
			
			this.stationName = stationName;
			this.language = language;

		}

		/**
		 * �߾�����V1
		 * 
		 * @param freq
		 *            String
		 */
		public StreamChannel(String freq) {

			this.freq = freq;

		}

	}

	public static class StreamTaskSet {

		private String equCode, taskID, action, validStartDateTime,
				validEndDateTime, bps, encode, width, height, fps;

		/**
		 * V8��V7��V6��V5���߾��㲥V1
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
		 * �߾�����V1
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
	 * ����StreamTask����������ڲ���
	 * 
	 */
	public static class StreamTask {

		private String sleepTime;

		/**
		 * �߾��㲥������
		 */
		public StreamTask() {
		}

		/**
		 * V8��V7��V6��V5
		 * 
		 * @param sleepTime
		 *            String
		 */
		public StreamTask(String sleepTime) {

			this.sleepTime = sleepTime;
		}

	}

	private MessageElement channelToElement(MsgQualityStreamTaskSetCmd.StreamChannel tc) {

		Map attrs = new LinkedHashMap(2);

		attrs.put("Freq", tc.freq);
		attrs.put("Band", tc.band);
		attrs.put("StationName", tc.stationName);
		attrs.put("LanguageName", tc.language);

		return new MessageElement("Channel", attrs, null);

	}

	private MessageElement channelToElementFrontierVideoV1(
			MsgQualityStreamTaskSetCmd.StreamChannel tc) {

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
			MsgQualityStreamTaskSetCmd.StreamSingleTask st) {

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
			MsgQualityStreamTaskSetCmd.StreamSingleTask st) {

		Map attrs = new LinkedHashMap(4);

		attrs.put("StartDateTime", st.startTime);

		attrs.put("EndDateTime", st.endTime);

		attrs.put("RecordLength", st.recordLength);

		attrs.put("ExpireDays", st.expireDays);

		return new MessageElement("SingleReportTime", attrs, null);

	}

	/**
	 * �߾��㲥����V1
	 * 
	 * @param st
	 *            SingleTask
	 * @return MessageElement
	 */
	private MessageElement singleTaskToElementFrontier(
			MsgQualityStreamTaskSetCmd.StreamSingleTask st) {

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
			MsgQualityStreamTaskSetCmd.StreamCircleTask ct) {

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
	 * �߾��㲥����V1
	 * 
	 * @param ct
	 *            CircleTask
	 * @return MessageElement
	 */
	private MessageElement circleTaskToElementFrontier(
			MsgQualityStreamTaskSetCmd.StreamCircleTask ct) {

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
			MsgQualityStreamTaskSetCmd.StreamCircleTask ct) {

		Map attrs = new LinkedHashMap(6);

		attrs.put("DayOfWeek", ct.dayofWeek);

		attrs.put("StartTime", ct.startTime);

		attrs.put("EndTime", ct.endTime);

		attrs.put("RecordLength", ct.recordLength);

		attrs.put("ExpireDays", ct.expireDays);

		return new MessageElement("CycleReportTime", attrs, null);

	}

	private Map streamTaskSetToElement(MsgQualityStreamTaskSetCmd.StreamTaskSet qt) {

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
			MsgQualityStreamTaskSetCmd.StreamTaskSet qt) {

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

	private Map streamTaskToElement(MsgQualityStreamTaskSetCmd.StreamTask qt) {
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
	 * V8����ʵ��
	 * 
	 */
//	protected class BuilderV8Radio extends
//			com.viewscenes.device.framework.BaseMessageCommand.BuilderV8Radio {
//
//		public BuilderV8Radio() {
//		}
//
//		protected MessageElement buildBody() {
//
//			Collection children = new ArrayList();
//			Map attrs = new LinkedHashMap();
//
//			for (int i = 0; i < streamList.size(); i++) {
//
//				StreamTaskChannel tc = (StreamTaskChannel) streamList.get(i);
//
//				ArrayList vt = tc.getStreamTaskSet();
//
//				ArrayList st = tc.getStreamTask();
//
//				ArrayList ch = tc.getChannels();
//
//				ArrayList cy = tc.getCircleTasks();
//
//				ArrayList sss = tc.getSingleTasks();
//
//				Collection c1 = new ArrayList();
//
//				Map map = new LinkedHashMap(1);
//
//				StreamTaskSet v = null;
//
//				StreamTask s = null;
//
//				for (int j = 0; j < vt.size(); j++) {
//					v = (StreamTaskSet) vt.get(j);
//				}
//				attrs = streamTaskSetToElement(v);
//
//				for (int j = 0; j < st.size(); j++) {
//					s = (StreamTask) st.get(j);
//				}
//				if (s != null) {
//					map = streamTaskToElement(s);
//				}
//				for (int j = 0; j < ch.size(); j++) {
//					StreamChannel c = (StreamChannel) ch.get(j);
//					c1.add(channelToElement(c));
//				}
//
//				for (int k = 0; k < cy.size(); k++) {
//					StreamCircleTask cr = (StreamCircleTask) cy.get(k);
//					c1.add(circleTaskToElementV8(cr));
//				}
//
//				for (int k = 0; k < sss.size(); k++) {
//					StreamSingleTask cr = (StreamSingleTask) sss.get(k);
//					c1.add(singleTaskToElementV8(cr));
//				}
//
//				children.add(new MessageElement("StreamTask", map, c1));
//
//			}
//
//			return new MessageElement("StreamTaskSet", attrs, children);
//		}
//	}
	
	/*********************************************ָ������*******************************************************/
	
	private Collection singleTasks;

	private Collection circleTasks;

	private Collection channels;

	ArrayList qualityList = new ArrayList();
//
//	public MsgQualityReportTaskSetCmd() {
//
//		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
//	}
//
	public void setQualityList(ArrayList li) {

		this.qualityList = li;

	}

	/**
	 * ��������
	 */
	public static class QualityTaskChannel {

		ArrayList qualityTaskSet;

		ArrayList qualityTask;

		ArrayList channel;

		ArrayList singleReportTime;

		ArrayList cycleReportTime;

		ArrayList qualityParam;
		
		ArrayList alarmParam;

		public QualityTaskChannel(ArrayList qualityTaskSet, ArrayList qualityParam,
				ArrayList qualityTask, ArrayList channel,
				ArrayList singleReportTime, ArrayList cycleReportTime,ArrayList alarmParam) {

			this.qualityTaskSet = qualityTaskSet;
			this.qualityParam = qualityParam;
			this.qualityTask = qualityTask;
			this.channel = channel;
			this.singleReportTime = singleReportTime;
			this.cycleReportTime = cycleReportTime;
			this.alarmParam = alarmParam;	//ָ���Ƿ�������
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
	 * ����StreamTaskSetѭ����������������ڲ���
	 * 
	 * @version 8.0
	 * 
	 */
	public static class QualityCircleTask {

		private String dayofWeek, startTime, endTime, reportMode,
				reportInterval, reportTime, expireDays, sampleNumber;// ,
																		// checkInterval;

		/**
		 * 
		 * ��StreamTaskSet��������������ͬһ���͵ĳ�������ע��˳��
		 * 
		 * @param startTime
		 *            ��ʼʱ��
		 * 
		 * @param endTime
		 *            ����ʱ��
		 * 
		 * @param dayofWeek
		 *            ÿ�ܵĵڼ��죬ȫ��Ϊ"All"
		 * 
		 * @param reportMode
		 *            �ϱ�ģʽ
		 * 
		 * @param reportInterval
		 *            ��ʱ�����ϱ����
		 * 
		 * @param reportTime
		 *            ��ʾ������ɺ��ٵȴ�һ��ʱ������ϱ�
		 * 
		 * ע��v5��v6��v7ʹ��
		 * 
		 */

		public QualityCircleTask(String startTime, String endTime, String dayofWeek,
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
		 * ��StreamTaskSet��������������ͬһ���͵ĳ�������ע��˳��
		 * 
		 * @param startTime
		 *            ��ʼʱ��
		 * 
		 * @param endTime
		 *            ����ʱ��
		 * 
		 * @param dayofWeek
		 *            ÿ�ܵĵڼ��죬ȫ��Ϊ"All"
		 * 
		 * @param reportMode
		 *            �ϱ�ģʽ
		 * 
		 * @param reportInterval
		 *            ��ʱ�����ϱ����
		 * 
		 * @param reportTime
		 *            ��ʾ������ɺ��ٵȴ�һ��ʱ������ϱ�
		 * 
		 * @param expireDays
		 *            ����ʱ�䡣��λ����
		 * 
		 * ע��v8��ʹ��
		 * 
		 */

		public QualityCircleTask(String startTime, String endTime, String dayofWeek,
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
		 * �˷�����Ϊ�㲥�ͱ߾������ģ�������ղ�����󻯣��ǹ㲥�ͱ߾���ͨ�ýӿڡ�
		 * 
		 */

		public QualityCircleTask(String startTime, String endTime, String dayofWeek,
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
		 * ��StreamTaskSet��������������ͬһ���͵ĳ�������ע��˳��
		 * 
		 * @param startTime
		 *            ��ʼʱ��
		 * 
		 * @param endTime
		 *            ����ʱ��
		 * 
		 * @param dayofWeek
		 *            ÿ�ܵĵڼ��죬ȫ��Ϊ"All"
		 * 
		 * @param reportMode
		 *            �ϱ�ģʽ:1��ʾ������ɺ��ϱ�,2�������ϱ����
		 * 
		 * @param sampleNumber
		 *            �ղ�ʱ����ڲ�������
		 * 
		 * @param checkInterval
		 *            ÿ��Ƶ���ղ���
		 * 
		 * @param expireDays
		 *            ����ʱ�䡣��λ����
		 * 
		 * ע���߾�v1��ʹ��
		 * 
		 * @param Radio_or_Video
		 *            �ж��Ǳ߾����ӻ�㲥���˲�����ʱ���ã��ɴ���nullֵ��
		 * 
		 */

		public QualityCircleTask(String startTime, String endTime, String dayofWeek,
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
	 * ����StreamTaskSet������������������ڲ���
	 * 
	 * @version 1.0
	 * 
	 */

	public static class QualitySingleTask {

		private String startTime, endTime, reportMode, reportInterval,
				reportTime, expireDays, sampleNumber;// , checkInterval;

		/**
		 * 
		 * ��StreamTaskSet��������������ͬһ���͵ĳ�������ע��˳��
		 * 
		 * @param startTime
		 *            ��ʼʱ��
		 * 
		 * @param endTime
		 *            ����ʱ��
		 * 
		 * @param reportMode
		 *            �ϱ�ģʽ
		 * 
		 * @param reportInterval
		 *            ��ʱ�����ϱ����
		 * 
		 * @param reportTime
		 *            ��ʾ������ɺ��ٵȴ�һ��ʱ������ϱ�
		 * 
		 * @param expireDays
		 *            ����ʱ�䡣��λ����
		 * 
		 * ע��v8��ʹ��
		 * 
		 */

		public QualitySingleTask(String startTime, String endTime, String reportMode,
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
		 * �˷�����Ϊ�㲥�ͱ߾������ģ�������ղ�����󻯣��ǹ㲥�ͱ߾���ͨ�ýӿڡ�
		 * 
		 */

		public QualitySingleTask(String startTime, String endTime, String reportMode,
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
		 * ��StreamTaskSet��������������ͬһ���͵ĳ�������ע��˳��
		 * 
		 * @param startTime
		 *            ��ʼʱ��
		 * 
		 * @param endTime
		 *            ����ʱ��
		 * 
		 * @param reportMode
		 *            �ϱ�ģʽ:1��ʾ������ɺ��ϱ�,2�������ϱ����
		 * 
		 * @param sampleNumber
		 *            �ղ�ʱ����ڲ�������
		 * 
		 * @param checkInterval
		 *            ÿ��Ƶ���ղ���
		 * 
		 * @param expireDays
		 *            ����ʱ�䡣��λ����
		 * 
		 * ע���߾�v1��ʹ��
		 * 
		 * @param Radio_or_Video
		 *            �ж��Ǳ߾����ӻ�㲥���˲�����ʱ���ã��ɴ���nullֵ��
		 * 
		 */

		public QualitySingleTask(String startTime, String endTime, String reportMode,
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
		 * ��StreamTaskSet��������������ͬһ���͵ĳ�������ע��˳��
		 * 
		 * @param startTime
		 *            ��ʼʱ��
		 * 
		 * @param endTime
		 *            ����ʱ��
		 * 
		 * @param reportMode
		 *            �ϱ�ģʽ
		 * 
		 * @param reportInterval
		 *            ��ʱ�����ϱ����
		 * 
		 * @param reportTime
		 *            ��ʾ������ɺ��ٵȴ�һ��ʱ������ϱ�
		 * 
		 * ע��v5��v6��v7ʹ��
		 * 
		 */

		public QualitySingleTask(String startTime, String endTime, String reportMode,
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
	 * ָ�������ڲ���
	 * 
	 * @author zgl
	 * 
	 */
	public static class QualityParam {

		private String type, Desc, sampleNumber;

		/**
		 * v6��v7��v8���߾�v1��ʹ��
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
		 * v5��ʹ��
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
	 * ָ���Ƿ񱨾�����
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
	 * Ƶ���ڲ���
	 * 
	 * @author zgl
	 * 
	 */
	public static class QualityChannel {

		private String freq, band, attenuation;

		/**
		 * 
		 * @param band
		 *            ����
		 * 
		 * @param freq
		 *            Ƶ�ʣ���λΪKHz��������Ч
		 * 
		 * @param attenuation
		 * 
		 * ע��v5��v6��v7��v8��
		 */

		public QualityChannel(String freq, String band) {

			this.freq = freq;

			this.band = band;

		}

		/**
		 * 
		 * @param band
		 *            ����
		 * 
		 * @param freq
		 *            Ƶ�ʣ���λΪKHz��������Ч
		 * 
		 * @param attenuation
		 * 
		 * ע���߾��㲥v1��
		 */

		public QualityChannel(String freq, String band, String attenuation) {

			this.freq = freq;

			this.band = band;

			this.attenuation = attenuation;

		}

		/**
		 * 
		 * @param band
		 *            ����
		 * 
		 * @param freq
		 *            Ƶ�ʣ���λΪKHz��������Ч
		 * 
		 * @param attenuation
		 * 
		 * ע���߾�����v1��
		 */

		public QualityChannel(String freq) {

			this.freq = freq;

		}

	}

	/**
	 * QualityTaskSet��ǩ�ڲ���
	 */

	public static class QualityTaskSet {

		private String equCode, taskID, action, validStartDateTime,
				validEndDateTime, sampleNumber, unit;

		/**
		 * ע��v6��v7��v8��
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
		 * ע��v5�桢�߾�v1��ʹ��
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
	 * ����QualityTask����������ڲ���
	 * 
	 * @version 1.0
	 * 
	 */

	public static class QualityTask {

		private String sleepTime;

		/**
		 * v5��v6��v7��v8��ʹ��
		 * 
		 * @param sleepTime
		 *            String
		 */
		public QualityTask(String sleepTime) {

			this.sleepTime = sleepTime;
		}

		/**
		 * �߾�v1��ʹ��
		 * 
		 * @param sleepTime
		 *            String
		 */
		public QualityTask() {
		}
	}

	/**
	 * ƴ��xml
	 */

	/**
	 * v5��v6��v7��v8
	 * 
	 * @param tc
	 *            Channel
	 * @return MessageElement
	 */
	private MessageElement channelToElement(
			MsgQualityStreamTaskSetCmd.QualityChannel tc) {

		Map attrs = new LinkedHashMap(2);

		attrs.put("Freq", tc.freq);
		attrs.put("Band", tc.band);

		return new MessageElement("Channel", attrs, null);

	}

	/**
	 * �߾��㲥v1
	 * 
	 * @param tc
	 *            Channel
	 * @return MessageElement
	 */
	private MessageElement channelToElementFrontierRadio(
			MsgQualityStreamTaskSetCmd.QualityChannel tc) {

		Map attrs = new LinkedHashMap(3);

		attrs.put("Freq", tc.freq);
		attrs.put("Band", tc.band);
		attrs.put("Attenuation", tc.attenuation);

		return new MessageElement("Channel", attrs, null);

	}

	/**
	 * �߾�����v1
	 * 
	 * @param tc
	 *            Channel
	 * @return MessageElement
	 */
	private Map channelToElementFrontierVideo(
			MsgQualityStreamTaskSetCmd.QualityChannel tc) {

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
			MsgQualityStreamTaskSetCmd.QualitySingleTask st) {

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
	 * v5��v6��v7
	 * 
	 * @param st
	 *            SingleTask
	 * @return MessageElement
	 */
	private MessageElement singleTaskToElementV5V6V7(
			MsgQualityStreamTaskSetCmd.QualitySingleTask st) {

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
	 * �߾�v1
	 * 
	 * @param st
	 *            SingleTask
	 * @return MessageElement
	 */
	private MessageElement singleTaskToElementFrontier(
			MsgQualityStreamTaskSetCmd.QualitySingleTask st) {

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
			MsgQualityStreamTaskSetCmd.QualityCircleTask ct) {

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
	 * v5��v6��v7
	 * 
	 * @param ct
	 *            CircleTask
	 * @return MessageElement
	 */
	private MessageElement circleTaskToElementV5V6V7(
			MsgQualityStreamTaskSetCmd.QualityCircleTask ct) {

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
			MsgQualityStreamTaskSetCmd.QualityCircleTask ct) {

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
	 * �߾�v1��v6��v7��v8
	 * 
	 * @param qp
	 *            QualityParam
	 * @return Map
	 */
	private Map qualityParamToElement(MsgQualityStreamTaskSetCmd.QualityParam qp) {

		Map attrs = new LinkedHashMap(2);

		attrs.put("Type", qp.type);
		attrs.put("Desc", qp.Desc);

		return attrs;
	}

	
	/**
	 * �Ƿ񱨾�����
	 * 
	 * @param qp
	 *            
	 * @return Map
	 */
	private Map alarmParamToElement(MsgQualityStreamTaskSetCmd.AlarmParam ap) {

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
			MsgQualityStreamTaskSetCmd.QualityParam qp) {

		Map attrs = new LinkedHashMap(3);

		attrs.put("Type", qp.type);
		attrs.put("Desc", qp.Desc);
		attrs.put("SampleNumber", qp.sampleNumber);

		return attrs;
	}

	/**
	 * v6��v7��v8
	 * 
	 * @param qt
	 *            QualityTaskSet
	 * @return Map
	 */
	private Map qualityTaskSetToElement(
			MsgQualityStreamTaskSetCmd.QualityTaskSet qt) {

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
	 * �߾�v1��v5
	 * 
	 * @param qt
	 *            QualityTaskSet
	 * @return Map
	 */
	private Map qualityTaskSetToElementFrontierV5Frontier(
			MsgQualityStreamTaskSetCmd.QualityTaskSet qt) {

		Map attrs = new LinkedHashMap(5);
		attrs.put("EquCode", qt.equCode);
		attrs.put("TaskID", qt.taskID);
		attrs.put("Action", qt.action);
		attrs.put("ValidStartDateTime", qt.validStartDateTime);
		attrs.put("ValidEndDateTime", qt.validEndDateTime);

		return attrs;
	}

	/**
	 * v5��v6��v7��v8
	 * 
	 * @param qt
	 *            QualityTask
	 * @return Map
	 */
	private Map qualityTaskToElement(MsgQualityStreamTaskSetCmd.QualityTask qt) {

		Map attrs = new LinkedHashMap(1);

		attrs.put("SleepTime", qt.sleepTime);

		return attrs;
	}

	/**
	 * �߾�v1
	 * 
	 * @param qt
	 *            QualityTask
	 * @return Map
	 */
	private Map qualityTaskToElementFrontier(
			MsgQualityStreamTaskSetCmd.QualityTask qt) {
		Map attrs = new LinkedHashMap(1);
		return attrs;
	}

	/**
	 * 
	 * v8�㲥�ӿڵ�builder
	 * 
	 */

	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {

		public BuilderV8Radio() {

		}

		protected MessageElement buildBody() {
			
			Collection list = new ArrayList();
/*************************************¼��XML**************************************************/
			
			Collection streamChildren = new ArrayList();
			Map streamAttrs = new LinkedHashMap();

			for (int i = 0; i < streamList.size(); i++) {

				StreamTaskChannel tc = (StreamTaskChannel) streamList.get(i);

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
				streamAttrs = streamTaskSetToElement(v);

				for (int j = 0; j < st.size(); j++) {
					s = (StreamTask) st.get(j);
				}
				if (s != null) {
					map = streamTaskToElement(s);
				}
				for (int j = 0; j < ch.size(); j++) {
					StreamChannel c = (StreamChannel) ch.get(j);
					c1.add(channelToElement(c));
				}

				for (int k = 0; k < cy.size(); k++) {
					StreamCircleTask cr = (StreamCircleTask) cy.get(k);
					c1.add(circleTaskToElementV8(cr));
				}

				for (int k = 0; k < sss.size(); k++) {
					StreamSingleTask cr = (StreamSingleTask) sss.get(k);
					c1.add(singleTaskToElementV8(cr));
				}

				streamChildren.add(new MessageElement("StreamTask", map, c1));

			}
			list.add(new MessageElement("StreamTaskSet", streamAttrs, streamChildren));

			
			/*************************************ָ��XML**************************************************/
			
			Collection qualityChildren = new ArrayList();
			Map qualityAttrs = new LinkedHashMap();

			for (int i = 0; i < qualityList.size(); i++) {

				QualityTaskChannel tc = (QualityTaskChannel) qualityList.get(i);

				ArrayList vt = tc.getQualityTaskSet();

				ArrayList qm = tc.getQualityParam();

				ArrayList st = tc.getQualityTask();

				ArrayList ch = tc.getChannels();

				ArrayList cy = tc.getCircleTasks();

				ArrayList sss = tc.getSingleTasks();
				
				ArrayList alarmParam = tc.getAlarmParam();

				Collection c1 = new ArrayList();

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
				qualityAttrs = qualityTaskSetToElement(v);

				for (int j = 0; j < st.size(); j++) {
					s = (QualityTask) st.get(j);
				}
				if (s != null) {
					map = qualityTaskToElement(s);
				}
				for (int j = 0; j < ch.size(); j++) {
					QualityChannel c = (QualityChannel) ch.get(j);
					c1.add(channelToElement(c));
				}

				for (int k = 0; k < cy.size(); k++) {
					QualityCircleTask cr = (QualityCircleTask) cy.get(k);
					c1.add(circleTaskToElement(cr));
				}

				for (int k = 0; k < sss.size(); k++) {
					QualitySingleTask cr = (QualitySingleTask) sss.get(k);
					c1.add(singleTaskToElement(cr));
				}

				for (int j = 0; j < qm.size(); j++) {
					p = (QualityParam) qm.get(j);
					map2 = qualityParamToElement(p);
					qualityChildren.add(new MessageElement("QualityIndex", map2, null));
				}

				for (int a = 0; a < alarmParam.size(); a++){
					alarmP = (AlarmParam) alarmParam.get(a);
					map3 = alarmParamToElement(alarmP);
					qualityChildren.add(new MessageElement("QualityAlarmParam", map3, null));
				}
				
				qualityChildren.add(new MessageElement("QualityTask", map, c1));

			}

			list.add(new MessageElement("QualityTaskSet", qualityAttrs, qualityChildren));
			
			
			
			return new MessageElement("QualityStreamTaskSet", null, list);
		}
	}
}
