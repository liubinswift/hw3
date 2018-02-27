package com.viewscenes.device.radio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

/**
 * 
 * <p>
 * Title: 接收机控制类
 * </p>
 * 
 * <p>
 * Description:本版支持v8版本
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
 * @author:刘斌
 * @version 1.0
 */

public class MsgReceiverControlCmd extends BaseMessageCommand {
	private String _equCode;
	private String _action;
	private String _expireTime;
	private Collection _params;

	/**
	 * 设置对应的MessageBuilder。
	 */
	public MsgReceiverControlCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setAttrs(String equCode, String action, String expireTime,
			Collection params) {
		this._equCode = equCode;
		this._action = action;
		this._expireTime = expireTime;
		this._params = params;
	}

	/*
	 * V8广播的Builder
	 */
	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {
		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {
			Map attrs = new LinkedHashMap(3);
			attrs.put("EquCode", MsgReceiverControlCmd.this._equCode);
			attrs.put("Action", MsgReceiverControlCmd.this._action);
			attrs.put("ExpireTime", MsgReceiverControlCmd.this._expireTime);
			if (MsgReceiverControlCmd.this._params == null) {
				return new MessageElement("ReceiverControl", attrs, null);
			}
			Collection children = new ArrayList(
					MsgReceiverControlCmd.this._params.size());
			for (Iterator pit = MsgReceiverControlCmd.this._params.iterator(); pit
					.hasNext();) {
				Map values = new LinkedHashMap(1);
				values.put("Value", pit.next());
				children.add(new MessageElement("Param", values, null));
			}
			return new MessageElement("ReceiverControl", attrs, children);
		}
	}

}
