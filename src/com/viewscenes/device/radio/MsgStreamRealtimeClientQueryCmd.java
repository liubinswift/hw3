package com.viewscenes.device.radio;

import java.util.*;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

/**
 * 
 * <p>
 * Title:�ͻ������Ӳ�ѯ��
 * </p>
 * 
 * <p>
 * Description:�����ӿڡ� ֧�����µ�V8�汾
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
public class MsgStreamRealtimeClientQueryCmd extends BaseMessageCommand {

	// ǰ�˼����豸����
	private String equCode;
	// ��ѯ������
	private String type;

	public MsgStreamRealtimeClientQueryCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setEequCode(String equcode) {
		this.equCode = equcode;
	}

	/**
	 * �߾���Ҫ���������Radio:��ѯ��Ƶ����TV:��ѯ��Ƶ����Ϊ�ղ�ѯ����Ƶ����
	 * 
	 * @param type
	 *            String
	 */
	public void setType(String type) {
		this.type = type;
	}

	protected class BuilderV8Radio

	extends BaseMessageCommand.BuilderV8Radio {

		public BuilderV8Radio() {

		}

		protected MessageElement buildBody() {

			Map attrs = new LinkedHashMap(1);
			attrs.put("EquCode", equCode);
			return new MessageElement("StreamRealtimeClientQuery", attrs, null);

		}

	}

}
