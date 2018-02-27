package com.viewscenes.device.radio;

import java.util.LinkedHashMap;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

/**
 * 
 * <p>
 * Title:设备日志查询类。
 * </p>
 * 
 * <p>
 * Description: 支持v8版本。
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
public class MsgEquipmentLogHistoryQueryCmd extends BaseMessageCommand {
	private String startDateTime;
	private String endDateTime;
	/**
	 * 边境需要这个参数。 1:对外来访问的记录 2:向外连接类日志记录 3:硬件设备状态日志 5:系统自身开关机记录 6:系统软件事件记录
	 * 为空表示查询所有设备日志
	 */

	private String logType;

	public MsgEquipmentLogHistoryQueryCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setParams(String startDateTime, String endDateTime,
			String logType) {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.logType = logType;
	}

	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {
		protected BuilderV8Radio() {

		}

		protected MessageElement buildBody() {
			Map attrs = new LinkedHashMap(2);
			attrs.put("StartDateTime",
					MsgEquipmentLogHistoryQueryCmd.this.startDateTime);
			attrs.put("EndDateTime",
					MsgEquipmentLogHistoryQueryCmd.this.endDateTime);
			return new MessageElement("EquipmentLogHistoryQuery", attrs, null);
		}
	}

}
