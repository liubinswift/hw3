package com.viewscenes.device.radio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.viewscenes.device.framework.BaseMessageResponse;
import com.viewscenes.device.framework.IMessageParser;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.util.InnerMsgType;

/**
 * 
 * <p>
 * Title:设备实时状态查询
 * </p>
 * 
 * <p>
 * Description: 支持广播v8版本广播
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
public class MsgEquipmentStatusRealtimeQueryRes extends BaseMessageResponse {
	private Collection equStatus;
	private MessageElement body;

	private String headTime = ""; // xml头部时间

	public MsgEquipmentStatusRealtimeQueryRes() {
		setParsers(new IMessageParser[] {

		new ParserV8Radio() });
	}

	public Collection getEquStatus() {
		return this.equStatus;
	}

	// 写此方法的目的是返回的就是没有头的xml不需要进行解析组合然后再到页面解析.
	public MessageElement returnBody() {
		return body;
	}

	public String getheadTime() {
		return this.headTime;
	}

	/**
	 * 
	 * V8版具体实现
	 * 
	 */
	protected class ParserV8Radio extends
			MsgEquipmentStatusRealtimeQueryRes.ParserV8BaseRadio {

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
			Map mm = getAttributes();
			headTime = mm.get("DateTime") == null ? "" : (String) mm
					.get("DateTime");
			headTime = headTime.trim();

			MessageElement qr = getBody();
			body = qr;
			if (qr == null)
				return;

			Collection erss = qr.getChildren();
			if (erss == null) {
				equStatus = new Vector();
			} else {
				equStatus = new ArrayList(erss.size());
				for (Iterator ersit = erss.iterator(); ersit.hasNext();) {
					MessageElement ers = (MessageElement) ersit.next();
					Collection es = ers.getChildren();
					if (es != null) {
						Collection equipments = new ArrayList(es.size());
						MessageElement quality;
						Map params;
						for (Iterator it = es.iterator(); it.hasNext(); equipments
								.add(new Equipment(makeAttrs(quality
										.getAttributes()), params))) {
							quality = (MessageElement) it.next();
							Collection qchildren = quality.getChildren();
							params = null;
							if (qchildren != null) {
								params = new HashMap(qchildren.size());
								Map attrs;
								for (Iterator ittt = qchildren.iterator(); ittt
										.hasNext(); params.put(attrs
										.get("parammetertype"), attrs
										.get("value")))
									attrs = ((MessageElement) ittt.next())
											.getAttributes();

							}
						}

						equStatus.add(new EquStatus(ers.getAttributes(),
								equipments));
					}
				}

			}
		}

		protected Map makeAttrs(Map oriAttrs) {
			oriAttrs.put("equipmenttype", oriAttrs.get("type"));
			oriAttrs.put("devicecode", oriAttrs.get("equcode"));
			return oriAttrs;
		}
	}


	public static final class Equipment {
		private String equipmentType;
		private String deviceCode;
		private String equStatus;
		private String desc;
		private Map ps;

		private Equipment(Map attrs, Map ps) {

			this.equipmentType = ((String) attrs.get("equipmenttype"));
			this.deviceCode = ((String) attrs.get("devicecode"));
			this.equStatus = ((String) attrs.get("equstatus"));
			this.desc = ((String) attrs.get("desc"));
			this.ps = ps;
		}

		public String getEquipmentType() {
			return this.equipmentType;
		}

		public String getDeviceCode() {
			return this.deviceCode;
		}

		public String getEquStatus() {
			return this.equStatus;
		}

		public String getDesc() {
			return this.desc;
		}

		public Map getParameters() {
			return this.ps;
		}

	}

	public static final class EquStatus {
		private String checkDateTime;
		private Collection equipments;

		private EquStatus(Map attrs, Collection equs) {
			this.checkDateTime = ((String) attrs.get("checkdatetime"));
			this.equipments = equs;
		}

		public String getCheckDateTime() {
			return this.checkDateTime;
		}

		public Collection getEquipments() {
			return this.equipments;
		}

	}

}
