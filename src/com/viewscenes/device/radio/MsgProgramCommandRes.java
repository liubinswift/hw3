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
 * Title:设备站点命令返回类。功能如：版本查询，设备重启，校时，查询程序状态。
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
public class MsgProgramCommandRes extends BaseMessageResponse {
	private Collection programInfos;

	public MsgProgramCommandRes() {
		setParsers(new IMessageParser[] { new ParserV8Radio() });
	}

	public Collection getProgramInfos() {
		return this.programInfos;
	}

	/**
	 * 
	 * V8版具体实现
	 * 
	 */
	protected class ParserV8Radio extends
			MsgProgramCommandRes.ParserV8BaseRadio {

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
			try {
				MessageElement ear = getBody();
				if (ear == null)
					return;
				Collection eas = ear.getChildren();
				if (eas == null)
					return;
				programInfos = new ArrayList(eas.size());
				Iterator easit = eas.iterator();
				while (easit.hasNext()) {
					MessageElement me = (MessageElement) easit.next();
					if (me.getName().toLowerCase().equals(
							("ProgramInfo").toLowerCase())) {
						programInfos.add(new ProgramInfo(me.getAttributes()));
					} else if (me.getName().toLowerCase().equals(
							("AliveInfo").toLowerCase())) {
						programInfos.add(new AliveInfo(me.getAttributes()));
					}
				}
			} catch (Exception e) {

			}
		}

	}

	public static class ProgramInfo {
		private String company;
		private String name;
		private String version;

		private ProgramInfo(Map attrs) {
			if ((attrs == null))
				throw new AssertionError();
			this.company = ((String) attrs.get("company"));
			this.name = ((String) attrs.get("name"));
			this.version = ((String) attrs.get("version"));
		}

		public String getCompany() {
			return this.company;
		}

		public String getName() {
			return this.name;
		}

		public String getVersion() {
			return this.version;
		}
	}

	public static class AliveInfo {
		private String value;

		private AliveInfo(Map attrs) {
			if ((attrs == null))
				throw new AssertionError();
			this.value = ((String) attrs.get("value"));
		}

		public String getValue() {
			return this.value;
		}

	}

}
