package com.viewscenes.device.radio;

import java.util.LinkedHashMap;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

/**
 * 
 * <p>
 * Title:设备报警历史查询
 * </p>
 * 
 * <p>
 * Description: 支持广播v8版本
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
 * @author 刘斌
 * @version 1.0
 */
public class MsgEquipmentAlarmHistoryQueryCmd extends BaseMessageCommand {
	private String startTime;
	private String endTime;
	// 边境需要此类型。
	private String alarmType;

	public MsgEquipmentAlarmHistoryQueryCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setEquipmentAlarmQueryParams(String startTime, String endTime,
			String alarmType) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.alarmType = alarmType;
	}

	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {
		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {
			Map attrs = new LinkedHashMap(2);
			attrs.put("StartDateTime",
					MsgEquipmentAlarmHistoryQueryCmd.this.startTime);
			attrs.put("EndDateTime",
					MsgEquipmentAlarmHistoryQueryCmd.this.endTime);
			return new MessageElement("EquipmentAlarmHistoryQuery", attrs, null);
		}
	}

}
