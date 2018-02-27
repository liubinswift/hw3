package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;

/**
 * 
 * 频偏历史查询
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

public class MsgOffsetHistoryQueryCmd extends BaseMessageCommand {

	private String equCode;

	private String taskId;

	private String freq;

	private String band;

	private String startDateTime;

	private String endDateTime;

	private String unit;

	private String sampleNumber;

	private String levelSampleNum;

	public MsgOffsetHistoryQueryCmd() {

		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });

	}

	// V8、V7、V6
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

			Map attrs = new LinkedHashMap(8);

			attrs.put("EquCode", equCode);

			attrs.put("TaskID", taskId);

			attrs.put("Band", band);

			attrs.put("Freq", freq);

			attrs.put("StartDateTime", startDateTime);

			attrs.put("EndDateTime", endDateTime);

			attrs.put("SampleNumber", sampleNumber);

			attrs.put("Unit", unit);

			return new MessageElement("OffsetHistoryQuery", attrs, null);

		}

	}

}
