package com.viewscenes.device.radio;

import java.util.*;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

/**
 * 
 * <p>
 * Title:客户端连接查询。
 * </p>
 * 
 * <p>
 * Description:新增接口。 支持最新的V8版本
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
public class MsgStreamRealtimeClientQueryCmd extends BaseMessageCommand {

	// 前端监测点设备代码
	private String equCode;
	// 查询的类型
	private String type;

	public MsgStreamRealtimeClientQueryCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setEequCode(String equcode) {
		this.equCode = equcode;
	}

	/**
	 * 边境需要这个参数。Radio:查询音频连接TV:查询视频连接为空查询音视频连接
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
