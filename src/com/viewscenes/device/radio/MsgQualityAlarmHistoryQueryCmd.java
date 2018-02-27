package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;

/**
 * 指标报警历史查询
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
public class MsgQualityAlarmHistoryQueryCmd extends BaseMessageCommand {

	private String equCode;

	private String freq;

	private String band;

	private String startDateTime;

	private String endDateTime;

	public MsgQualityAlarmHistoryQueryCmd() {

		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	/**
	 * 
	 * @param equcode
	 *            String
	 * @param freq
	 *            String
	 * @param band
	 *            String
	 * @param startDateTime
	 *            String
	 * @param endDateTime
	 *            String
	 */
	public void setParams(String equcode, String freq, String band,
			String startDateTime, String endDateTime) {

		this.equCode = equcode;

		this.freq = freq;

		this.band = band;

		this.startDateTime = startDateTime;

		this.endDateTime = endDateTime;

	}

	/**
	 * V8版，具体实现
	 */
	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {

		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {

			Map attrs = new LinkedHashMap(5);

			attrs.put("EquCode", equCode);

			attrs.put("Freq", freq);

			attrs.put("Band", band);

			attrs.put("StartDateTime", startDateTime);

			attrs.put("EndDateTime", endDateTime);

			return new MessageElement("QualityAlarmHistoryQuery", attrs, null);

		}
	}


}
