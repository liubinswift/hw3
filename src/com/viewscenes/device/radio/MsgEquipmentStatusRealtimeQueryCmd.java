package com.viewscenes.device.radio;

import java.util.LinkedHashMap;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

/**
 * 
 * <p>
 * Titles:设备状态实时查询
 * </p>
 * 
 * <p>
 * Description:支持广播v4---v8版本和边境版本
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

public class MsgEquipmentStatusRealtimeQueryCmd extends BaseMessageCommand {
	private String _reportInterval;
	private String _sampleInterval;
	private String _expireTime;
	/**
	 * 边境才有的属性。 实时设备状态查询操作命令 Start:开始实时查询； Stop:停止当前实时查询；
	 */
	private String _action;

	public MsgEquipmentStatusRealtimeQueryCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setAttrs(String reportInterval, String sampleInterval,
			String expireTime, String _action) {
		this._reportInterval = reportInterval;
		this._sampleInterval = sampleInterval;
		this._expireTime = expireTime;
		this._action = _action;
	}

	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {
		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {
			Map attrs = new LinkedHashMap(3);
			attrs.put("ReportInterval",
					MsgEquipmentStatusRealtimeQueryCmd.this._reportInterval);
			attrs.put("SampleInterval",
					MsgEquipmentStatusRealtimeQueryCmd.this._sampleInterval);
			attrs.put("ExpireTime",
					MsgEquipmentStatusRealtimeQueryCmd.this._expireTime);
			return new MessageElement("EquipmentStatusRealtimeQuery", attrs,
					null);
		}
	}

}
