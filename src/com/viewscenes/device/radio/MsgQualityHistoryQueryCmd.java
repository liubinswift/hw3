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
 * ָ����ʷ�·��ӿ�
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

public class MsgQualityHistoryQueryCmd extends BaseMessageCommand {

	private ArrayList types;

	private String equCode;

	private String taskId;

	private String freq;

	private String band;

	private String startDateTime;

	private String type;

	private String endDateTime;

	private String unit;

	private String sampleNumber;

	public MsgQualityHistoryQueryCmd() {

		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });

	}

	/**
	 * 
	 * ���ñ���������Ӧ��Ƶ��
	 * 
	 * @param equCode
	 *            �߼��豸��ʶ����д��ʾ�������߼��豸
	 * 
	 * @param band
	 *            ���Σ�0���̲���1���в���2����Ƶ����д��ʾ�����в��ε�����Ƶ����ѯ
	 * 
	 * @param freq
	 *            Ƶ�ʣ���λΪKHz����д��ʾ������Ƶ���ı�����Ϣ���в�ѯ
	 * 
	 */

	// V8��V7��V6
	public void setParamV8V7V6(String equcode, String taskId, String freq,
			String band, String startDateTime, String endDateTime,
			String sampleNumber, String unit) {

		this.equCode = equcode;

		this.taskId = taskId;

		this.freq = freq;

		this.band = band;

		this.startDateTime = startDateTime;

		this.endDateTime = endDateTime;

		this.sampleNumber = sampleNumber;

		this.unit = unit;

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

		public QueryTypes(String type, String desc) {

			// assert type != null && desc != null;

			this.type = type;

			this.desc = desc;

		}

	}

	private Map qualityParamToElement(MsgQualityHistoryQueryCmd.QueryTypes qp) {

		Map attrs = new LinkedHashMap(3);

		attrs.put("Type", qp.type);

		attrs.put("Desc", qp.desc);

		return attrs;
	}

	/**
	 * V8�棬����ʵ�� ע����v7��Ƚϣ���v8��������type=8��desc=������
	 */
	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {

		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {

			Map attrs = new LinkedHashMap(8);

			attrs.put("EquCode", equCode);

			attrs.put("TaskID", taskId);

			attrs.put("Freq", freq);

			attrs.put("Band", band);

			attrs.put("StartDateTime", startDateTime);

			attrs.put("EndDateTime", endDateTime);

			attrs.put("SampleNumber", sampleNumber);

			attrs.put("Unit", unit);

			// types
			List list = new ArrayList();
			Map map2 = new LinkedHashMap(3);
			QueryTypes p = null;

			for (int j = 0; j < types.size(); j++) {
				p = (QueryTypes) types.get(j);
				map2 = qualityParamToElement(p);
				list.add(new MessageElement("QualityIndex", map2, null));
			}

			return new MessageElement("QualityHistoryQuery", attrs, list);

		}
	}

}
