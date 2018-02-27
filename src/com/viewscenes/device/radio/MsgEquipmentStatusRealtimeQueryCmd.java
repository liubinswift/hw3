package com.viewscenes.device.radio;

import java.util.LinkedHashMap;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

/**
 * 
 * <p>
 * Titles:�豸״̬ʵʱ��ѯ
 * </p>
 * 
 * <p>
 * Description:֧�ֹ㲥v4---v8�汾�ͱ߾��汾
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
 * @author ����
 * @version 1.0
 */

public class MsgEquipmentStatusRealtimeQueryCmd extends BaseMessageCommand {
	private String _reportInterval;
	private String _sampleInterval;
	private String _expireTime;
	/**
	 * �߾����е����ԡ� ʵʱ�豸״̬��ѯ�������� Start:��ʼʵʱ��ѯ�� Stop:ֹͣ��ǰʵʱ��ѯ��
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
