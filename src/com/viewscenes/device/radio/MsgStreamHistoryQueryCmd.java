package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;

/**
 * 
 * ¼��¼����ʷ��ѯ
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company: Viewscenes
 * </p>
 * 
 * @author �Թ���
 * @version 8.0
 * 
 *  2012-09-15 by tcw
 * �ýӿ��ں���������ɾ��,û���������
 */

public class MsgStreamHistoryQueryCmd extends BaseMessageCommand {

//	private String equCode;
//
//	private String taskId;
//
//	private String freq;
//
//	private String band;
//
//	private String startDateTime;
//
//	private String endDateTime;
//
//	public MsgStreamHistoryQueryCmd() {
//
//		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
//	}
//
//	/**
//	 * v8��v7��v6��v5��v4���߾�¼��v1
//	 * 
//	 * @param equcode
//	 *            String
//	 * @param taskId
//	 *            String
//	 * @param freq
//	 *            String
//	 * @param band
//	 *            String
//	 * @param startDateTime
//	 *            String
//	 * @param endDateTime
//	 *            String
//	 */
//
//	public void setParams(String equCode, String taskId, String freq,
//			String band, String startDateTime, String endDateTime) {
//
//		this.equCode = equCode;
//
//		this.taskId = taskId;
//
//		this.freq = freq;
//
//		this.band = band;
//
//		this.startDateTime = startDateTime;
//
//		this.endDateTime = endDateTime;
//
//	}
//
//	/**
//	 * �߾�¼��V1
//	 * 
//	 * @param equCode
//	 *            String
//	 * @param taskId
//	 *            String
//	 * @param freq
//	 *            String
//	 * @param startDateTime
//	 *            String
//	 * @param endDateTime
//	 *            String
//	 */
//	public void setFrontierVideoParams(String equCode, String taskId,
//			String freq, String startDateTime, String endDateTime) {
//
//		this.equCode = equCode;
//
//		this.taskId = taskId;
//
//		this.freq = freq;
//
//		this.startDateTime = startDateTime;
//
//		this.endDateTime = endDateTime;
//
//	}
//
//	/**
//	 * V8�棬����ʵ��
//	 * 
//	 */
//	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {
//
//		protected BuilderV8Radio() {
//		}
//
//		protected MessageElement buildBody() {
//
//			Map map = new LinkedHashMap(6);
//
//			map.put("EquCode", equCode);
//
//			map.put("TaskID", taskId);
//
//			map.put("Band", band);
//
//			map.put("Freq", freq);
//
//			map.put("StartDateTime", startDateTime);
//
//			map.put("EndDateTime", endDateTime);
//
//			return new MessageElement("StreamHistoryQuery", map, null);
//
//		}
//	}


}
