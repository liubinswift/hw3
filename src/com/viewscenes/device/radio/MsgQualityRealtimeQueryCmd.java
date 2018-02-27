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
 * ָ��ʵʱ�·��ӿ�
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company: Viewscenes
 * </p>
 * 
 * @author �Թ���
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
	 * ���ñ���������Ӧ��Ƶ��
	 * 
	 * @param equCode
	 *            �߼��豸��ʶ����д��ʾ�������߼��豸
	 * @param band
	 *            ���Σ�0���̲���1���в���2����Ƶ����д��ʾ�����в��ε�����Ƶ����ѯ
	 * @param freq
	 *            Ƶ�ʣ���λΪKHz����д��ʾ������Ƶ���ı�����Ϣ���в�ѯ V8��V7��V6��V5���߾��㲥V1
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
	 * �߾�����V1
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
	 * ���ñ��������б�
	 * 
	 * @param types
	 *            QueryTypes�����Collection
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
	 * ����QualityRealtimeQuery����������ڲ���
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
		 * ��QualityRealtimeQuery��������������
		 * 
		 * @param type
		 *            ����ָ������
		 * 
		 * @param desc
		 *            ����ָ����������
		 * 
		 * @preconditions ��������Ϊnull
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
	 * V8�棬����ʵ�� ע����v7��Ƚϣ���v8��������type=8��desc=������
	 * Type="1:��ƽ,2:���ƶ�,3:������,5:�����ƶ�,6:Ƶƫ,8:����"
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
