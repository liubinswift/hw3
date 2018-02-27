package com.viewscenes.device.radio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageResponse;
import com.viewscenes.device.framework.IMessageParser;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.util.InnerMsgType;

/**
 * 
 * <p>
 * Title:设备日志历史查询返回
 * </p>
 * 
 * <p>
 * Description:支持广播v8版本广播版本。
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
 * @author 谭长伟
 * @version 1.0
 */
public class MsgEquipmentLogHistoryQueryRes extends BaseMessageResponse {
	private Collection equipmentLogs;

	public MsgEquipmentLogHistoryQueryRes() {
		setParsers(new IMessageParser[] { new ParserV8Radio() });
	}

	public Collection getEquipmentLogs() {
		return this.equipmentLogs;
	}

	/**
	 * 
	 * V8版具体实现
	 * 
	 */
	protected class ParserV8Radio extends
			MsgEquipmentLogHistoryQueryRes.ParserV8BaseRadio {

		protected ParserV8Radio() {

		}

		public InnerMsgType getType() {

			return InnerMsgType.instance("radio8");
		}
	}

	/**
	 * 
	 * V8基础版具体实现
	 * 
	 */
	protected class ParserV8BaseRadio extends
			BaseMessageResponse.ParserV8BaseRadio {
		protected ParserV8BaseRadio() {
		}

		protected void doParse() {
			MessageElement ear = getBody();
			if (ear == null)
				return;
			Collection eas = ear.getChildren();
			if (eas == null)
				return;
			equipmentLogs = new ArrayList(eas.size());
			for (Iterator easit = eas.iterator(); easit.hasNext(); equipmentLogs
					.add(new EquipmentLog(((MessageElement) easit.next())
							.getAttributes())))
				;
		}

	}

	public static class EquipmentLog {
		private String dateTime;
		private String name;
		private String desc;

		private EquipmentLog(Map attrs) {

			this.dateTime = ((String) attrs.get("datetime")).toLowerCase();
			this.name = ((String) attrs.get("name"));
			this.desc = ((String) attrs.get("desc"));
		}

		public String getDateTime() {
			return this.dateTime;
		}

		public String getName() {
			return this.name;
		}

		public String getDesc() {
			return this.desc;
		}

	}
}
