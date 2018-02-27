package com.viewscenes.device.radio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

/**
 * 
 * <p>
 * Title:设备初始化参数设置。
 * </p>
 * 
 * <p>
 * Description:支持广播V4--v8版本和边境版本。
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
public class MsgEquipmentInitParamSetCmd extends BaseMessageCommand {
	private String systemCode;
	private Collection systemCenter;
	private Collection systemLoginfo;
	private Map systemParams;
	private Collection otherParams;

	public MsgEquipmentInitParamSetCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setEquipmentInitParams(Collection params) {

		this.otherParams = params;
	}

	public void setSystemEquInitParams(String equCode, Collection centerParams,
			Collection loginfoParams, Map systemParams) {
		this.systemCenter = centerParams;
		this.systemCode = equCode;
		this.systemLoginfo = loginfoParams;
		this.systemParams = systemParams;
	}


	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {
		public BuilderV8Radio() {

		}

		protected MessageElement buildBody() {
			Collection initParams = new ArrayList();
			if (systemCode != null)
				initParams.add(makeSystem());
			if (otherParams != null) {
				for (Iterator it = otherParams.iterator(); it.hasNext(); initParams
						.add(makeOther((EquipmentInitParam) it.next())))
					;
			}
			return new MessageElement("EquipmentInitParamSet", null, initParams);
		}

		protected MessageElement makeSystem() {
			Map attrs = new LinkedHashMap(2);
			attrs.put("Type", "System");
			Collection ps = new ArrayList();
			if (systemCenter != null) {
				for (Iterator it = systemCenter.iterator(); it.hasNext(); ps
						.add(makeSystemCenter((Center) it.next())))
					;
			}
			if (systemLoginfo != null) {
				for (Iterator it = systemLoginfo.iterator(); it.hasNext(); ps
						.add(makeSystemLoginfo((Loginfo) it.next())))
					;
			}
			if (systemParams != null) {
				for (Iterator it = systemParams.entrySet().iterator(); it
						.hasNext(); ps
						.add(makeSystemParams((java.util.Map.Entry) it.next())))
					;
			}
			return new MessageElement("Equipment", attrs, ps);
		}

		protected MessageElement makeOther(EquipmentInitParam eip) {
			Map attrs = new LinkedHashMap(2);
			attrs.put("Type", eip.equName);
			attrs.put("EquCode", eip.equCode);
			Collection ps = new ArrayList();
			Collection pm = eip.params;
			if (pm != null && pm.size() > 0) {
				Map pa;
				for (Iterator pmit = pm.iterator(); pmit.hasNext(); ps
						.add(new MessageElement("Param", pa, null))) {
					pa = new LinkedHashMap(1);
					pa.put("Value", pmit.next());
				}

			}
			return new MessageElement("Equipment", attrs, ps);
		}

		protected MessageElement makeSystemCenter(Center center) {
			Map attrs = new LinkedHashMap(2);
			attrs.put("SrcCode", center.srcCode);
			attrs.put("Type", center.type);
			Collection ps = new ArrayList();
			Map pm = center.params;
			if (pm != null && pm.size() > 0) {
				for (Iterator pmit = pm.entrySet().iterator(); pmit.hasNext(); ps
						.add(makeSystemParams((java.util.Map.Entry) pmit.next())))
					;
			}
			return new MessageElement("Center", attrs, ps);
		}

		protected MessageElement makeSystemLoginfo(Loginfo log) {
			Map attrs = new LinkedHashMap(1);
			attrs.put("Type", log.type);
			Collection ps = new ArrayList();
			Map pm = log.params;
			if (pm != null && pm.size() > 0) {
				for (Iterator pmit = pm.entrySet().iterator(); pmit.hasNext(); ps
						.add(makeSystemParams((java.util.Map.Entry) pmit.next())))
					;
			}
			return new MessageElement("LogInfo", attrs, ps);
		}

		protected MessageElement makeSystemParams(java.util.Map.Entry me) {
			Map pa = new LinkedHashMap(2);
			pa.put("Name", me.getKey());
			pa.put("Value", me.getValue());
			return new MessageElement("Param", pa, null);
		}

	}


	public static class EquipmentInitParam {
		private String equName;
		private String equCode;
		private Collection params;

		public EquipmentInitParam(String equName, String equCode,
				Collection params) {
			this.equName = equName;
			this.equCode = equCode;
			this.params = params;
		}
	}

	public static final class Loginfo {
		private String type;
		private Map params;

		public Loginfo(String type, Map params) {
			this.type = type;
			this.params = params;
		}
	}

	public static final class Center {

		private String srcCode;
		private String type;
		private Map params;

		public Center(String srcCode, String type, Map params) {
			this.srcCode = srcCode;
			this.type = type;
			this.params = params;
		}
	}
}
