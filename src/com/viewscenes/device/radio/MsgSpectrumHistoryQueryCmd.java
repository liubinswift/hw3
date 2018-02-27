package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;

/**
 * 
 * 频谱历史查询
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
public class MsgSpectrumHistoryQueryCmd extends BaseMessageCommand {

	private String equCode;

	private String taskId;

	private String type;

	private String startDateTime;

	private String endDateTime;

	private String sampleNumber;

	private String unit;

	public MsgSpectrumHistoryQueryCmd() {

		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });

	}

	// V8、V7、V6
	public void setParamV8V7V6(String equcode, String taskId,
			String startDateTime, String endDateTime, String sampleNumber,
			String unit) {

		this.equCode = equcode;

		this.taskId = taskId;

		this.startDateTime = startDateTime;

		this.endDateTime = endDateTime;

		this.sampleNumber = sampleNumber;

		this.unit = unit;

	}

	// V5、边境广播电视V1
	public void setParamV5FrontierRadioVideoV1(String equcode, String taskId,
			String startDateTime, String endDateTime, String sampleNumber) {

		this.equCode = equcode;

		this.taskId = taskId;

		this.startDateTime = startDateTime;

		this.endDateTime = endDateTime;

		this.sampleNumber = sampleNumber;

	}

	// V4、边境广播电视V1
	public void setParamV4(String equcode, String taskId, String startDateTime,
			String endDateTime) {

		this.equCode = equcode;

		this.taskId = taskId;

		this.startDateTime = startDateTime;

		this.endDateTime = endDateTime;

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

			Map attrs = new LinkedHashMap(6);

			attrs.put("EquCode", equCode);

			attrs.put("TaskID", taskId);

			attrs.put("StartDateTime", startDateTime);

			attrs.put("EndDateTime", endDateTime);

			attrs.put("SampleNumber", sampleNumber);

			attrs.put("Unit", unit);

			return new MessageElement("SpectrumHistoryQuery", attrs, null);

		}

	}


}
