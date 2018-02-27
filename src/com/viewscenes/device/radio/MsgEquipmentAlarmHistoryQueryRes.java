package com.viewscenes.device.radio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import com.viewscenes.device.framework.BaseMessageResponse;
import com.viewscenes.device.framework.IMessageParser;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.util.InnerMsgType;

/**
 * 
 * <p>
 * Title:设备历史报警查询返回类。
 * </p>
 * 
 * <p>
 * Description: 支持v8版本广播版本。
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
public class MsgEquipmentAlarmHistoryQueryRes extends BaseMessageResponse {
	private Collection equipmentAlarms;

	public MsgEquipmentAlarmHistoryQueryRes() {
		setParsers(new IMessageParser[] { new ParserV8Radio() });
	}

	public Collection getEquipmentAlarms() {
		return this.equipmentAlarms;
	}

	protected class ParserV8Radio extends
			MsgEquipmentAlarmHistoryQueryRes.ParserV8BaseRadio {
		protected ParserV8Radio() {
		}

		protected Map makeAttrs(Map oriAttrs) {
			oriAttrs.put("devicecode", oriAttrs.get("equcode"));
			return oriAttrs;
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
			if (eas == null) {
				eas = new Vector();
			} else {
				equipmentAlarms = new ArrayList(eas.size());
				MessageElement ea;
				Map apattrs;
				for (Iterator easit = eas.iterator(); easit.hasNext(); equipmentAlarms
						.add(new EquipmentAlarm(makeAttrs(ea.getAttributes()),
								apattrs))) {
					ea = (MessageElement) easit.next();
					Collection aps = ea.getChildren();
					apattrs = null;
					if (aps != null && !aps.isEmpty()) {
						apattrs = new LinkedHashMap(aps.size());
						Map ap;
						for (Iterator apsit = aps.iterator(); apsit.hasNext(); apattrs
								.put(ap.get("name"), ap.get("value")))
							ap = ((MessageElement) apsit.next())
									.getAttributes();

					}
				}

			}
		}

		protected Map makeAttrs(Map oriAttrs) {
			return oriAttrs;
		}
	}

	public static class EquipmentAlarm {
		private String deviceCode;
		private String alarmID;
		private String mode;
		private String desc;
		private String type;
		private String reason;
		private String checkTime;
		private Map ap;

		private EquipmentAlarm(Map attrs, Map ap) {
			this.deviceCode = ((String) attrs.get("devicecode"));
			this.alarmID = ((String) attrs.get("alarmid"));
			this.mode = ((String) attrs.get("mode"));
			this.desc = ((String) attrs.get("desc"));
			this.type = ((String) attrs.get("type"));
			this.reason = ((String) attrs.get("reason"));
			this.checkTime = ((String) attrs.get("checkdatetime"));
			this.ap = ap;
		}

		public String getDeviceCode() {
			return this.deviceCode;
		}

		public String getAlarmID() {
			return this.alarmID;
		}

		public String getCheckTime() {
			return this.checkTime;
		}

		public String getMode() {
			return this.mode;
		}

		public String getDesc() {
			return this.desc;
		}

		public String getType() {
			return this.type;
		}

		public String getReason() {
			return this.reason;
		}

		public Map getAlarmParams() {
			return this.ap;
		}

	}
}
