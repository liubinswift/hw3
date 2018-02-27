package com.viewscenes.device.radio;

import java.util.LinkedHashMap;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

/**
 * 
 * <p>
 * Title:�豸��־��ѯ�ࡣ
 * </p>
 * 
 * <p>
 * Description: ֧��v8�汾��
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
public class MsgEquipmentLogHistoryQueryCmd extends BaseMessageCommand {
	private String startDateTime;
	private String endDateTime;
	/**
	 * �߾���Ҫ��������� 1:���������ʵļ�¼ 2:������������־��¼ 3:Ӳ���豸״̬��־ 5:ϵͳ�����ػ���¼ 6:ϵͳ����¼���¼
	 * Ϊ�ձ�ʾ��ѯ�����豸��־
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
