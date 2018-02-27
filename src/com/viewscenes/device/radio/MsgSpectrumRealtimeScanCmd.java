package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;

/**
 * 
 * 频谱实时查询
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

public class MsgSpectrumRealtimeScanCmd extends BaseMessageCommand {

	private String equCode;

	private String band;

	private String reportInterval;

	private String type;

	private String freq;

	private String expireTime;

	private String action;

	private String startFreq;

	private String endFreq;

	private String stepFreq;

	private String freqNumber;

	private String taskType;

	public MsgSpectrumRealtimeScanCmd() {

		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });

	}

	// V8、边境广播V1
	public void setParamV8FrontierRadioV1(String equcode, String band,
			String startFreq, String endFreq, String stepFreq,
			String reportInterval, String expireTime, String freqNumber,
			String action, String freq, String type) {

		this.equCode = equcode;

		this.band = band;

		this.startFreq = startFreq;

		this.endFreq = endFreq;

		this.stepFreq = stepFreq;

		this.reportInterval = reportInterval;

		this.expireTime = expireTime;

		this.freqNumber = freqNumber;

		this.action = action;

		this.freq = freq;

		this.type = type;
	}


	/**
	 * 
	 * V8版本，具体实现
	 * 
	 * <p>
	 * Copyright: Copyright (c) 2009
	 * </p>
	 * 
	 * <p>
	 * Company: Viewscenes
	 * </p>
	 * 
	 * @author zgl
	 * @version 1.0
	 */
	protected class BuilderV8Radio extends
			com.viewscenes.device.framework.BaseMessageCommand.BuilderV8Radio {

		public BuilderV8Radio() {
		}

		protected MessageElement buildBody() {

			Map attrs = new LinkedHashMap(11);

			attrs.put("EquCode", equCode);

			attrs.put("Type", type);

			attrs.put("Freq", freq);

			attrs.put("Band", band);

			attrs.put("StartFreq", startFreq);

			attrs.put("EndFreq", endFreq);

			attrs.put("StepFreq", stepFreq);

			attrs.put("ReportInterval", reportInterval);

			attrs.put("ExpireTime", expireTime);

			attrs.put("FreqNumber", freqNumber);

			attrs.put("Action", action);

			return new MessageElement("SpectrumRealtimeQuery", attrs, null);

		}

	}


}
