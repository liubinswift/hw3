package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;

/**
 * 
 * 录音录像历史查询
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
 * 
 *  2012-09-15 by tcw
 * 该接口在海外监测中已删除,没有相关需求
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
//	 * v8、v7、v6、v5、v4、边境录音v1
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
//	 * 边境录像V1
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
//	 * V8版，具体实现
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
