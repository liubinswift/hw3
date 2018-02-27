package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;

/**
 * <p>
 * Title:
 * </p>
 * 
 * 指标实时下发接口
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company: Viewscenes
 * </p>
 * 
 * @author 赵广磊
 * @version 8.0
 */

public class MsgQualityRealtimeQueryCmd extends BaseMessageCommand {

	private ArrayList types = new ArrayList();

	private String equCode;

	private String freq;

	private String band;

	private String reportInterval;

	private String type;

	private String expireTime;

	private String action;

	public MsgQualityRealtimeQueryCmd() {

		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	/**
	 * 设置报警参数对应的频道
	 * 
	 * @param equCode
	 *            逻辑设备标识。不写表示对所有逻辑设备
	 * @param band
	 *            波段，0：短波，1：中波，2：调频。不写表示对所有波段的所有频道查询
	 * @param freq
	 *            频率，单位为KHz。不写表示对所有频道的报警信息进行查询 V8、V7、V6、V5、边境广播V1
	 */

	public void setParams(String equcode, String freq, String band,
			String reportInterval, String expireTime, String action) {

		// assert equCode != null && freq != null && band != null &&
		// sampleLength != null;

		this.equCode = equcode;

		this.freq = freq;

		this.band = band;

		this.reportInterval = reportInterval;

		this.expireTime = expireTime;

		this.action = action;

	}

	/**
	 * 边境电视V1
	 * 
	 * @param equcode
	 *            String
	 * @param freq
	 *            String
	 * @param reportInterval
	 *            String
	 * @param expireTime
	 *            String
	 * @param action
	 *            String
	 */
	public void setParams(String equcode, String freq, String reportInterval,
			String expireTime, String action) {

		// assert equCode != null && freq != null && band != null &&
		// sampleLength != null;

		this.equCode = equcode;

		this.freq = freq;

		this.reportInterval = reportInterval;

		this.expireTime = expireTime;

		this.action = action;

	}

	/**
	 * 
	 * 设置报警参数列表
	 * 
	 * @param types
	 *            QueryTypes对象的Collection
	 * 
	 * @preconditions types != null
	 * 
	 * @see QueryTypes
	 * 
	 */

	public void setQueryTypes(ArrayList types) {

		// assert types != null;

		this.types = types;

	}

	public ArrayList getQueryTypes() {
		return (ArrayList) this.types;
	}

	/**
	 * 
	 * 包含QualityRealtimeQuery所需参数的内部类
	 * 
	 * @version 1.0
	 * 
	 */

	public static class QueryTypes {

		private String type;

		private String desc;

		private String sampleNumber;

		/**
		 * 
		 * 用QualityRealtimeQuery所需参数构造对象
		 * 
		 * @param type
		 *            质量指标类型
		 * 
		 * @param desc
		 *            质量指标类型描述
		 * 
		 * @preconditions 参数都不为null
		 * 
		 */

		public QueryTypes(String type, String desc, String sampleNumber) {

			// assert type != null && desc != null;

			this.type = type;

			this.desc = desc;

			this.sampleNumber = sampleNumber;

		}

	}

	private Map qualityParamToElement(MsgQualityRealtimeQueryCmd.QueryTypes qp) {

		Map attrs = new LinkedHashMap(3);

		attrs.put("Type", qp.type);

		attrs.put("Desc", qp.desc);

		attrs.put("SampleNumber", qp.sampleNumber);

		return attrs;
	}

	/**
	 * V8版，具体实现 注：与v7版比较，在v8版中新增type=8，desc=“带宽”
	 * Type="1:电平,2:调制度,3:调幅度,5:最大调制度,6:频偏,8:带宽"
	 */
	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {

		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {

			Map attrs = new LinkedHashMap(6);

			attrs.put("EquCode", equCode);

			attrs.put("Freq", freq);

			attrs.put("Band", band);

			attrs.put("ReportInterval", reportInterval);

			attrs.put("ExpireTime", expireTime);

			attrs.put("Action", action);

			// types
			List list = new ArrayList();
			Map map2 = new LinkedHashMap(6);
			QueryTypes p = null;

			for (int j = 0; j < types.size(); j++) {
				p = (QueryTypes) types.get(j);
				map2 = qualityParamToElement(p);
				list.add(new MessageElement("QualityIndex", map2, null));
			}
			return new MessageElement("QualityRealtimeQuery", attrs, list);

		}
	}

}
