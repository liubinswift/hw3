package com.viewscenes.device.radio;

import java.util.LinkedHashMap;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

public class MsgTaskDeleteCmd extends BaseMessageCommand {
	private String band;
	private String freq;
	private String taskid;
	private String StartDateTime;
	private String EndDateTime;
	private String SrcCode;
	private String Date;
	private String TaskType;

	public MsgTaskDeleteCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setAttrs(String band, String freq, String taskid,
			String StartDateTime, String EndDateTime, String SrcCode,
			String Date, String TaskType) {

		this.band = band;
		this.freq = freq;
		this.taskid = taskid;
		this.StartDateTime = StartDateTime;
		this.EndDateTime = EndDateTime;
		this.SrcCode = SrcCode;
		this.Date = Date;
		this.TaskType = TaskType;
	}

	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {
		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {
			Map attrs = new LinkedHashMap(6);
			attrs.put("Band", MsgTaskDeleteCmd.this.band);
			attrs.put("Freq", MsgTaskDeleteCmd.this.freq);
			attrs.put("TaskID", MsgTaskDeleteCmd.this.taskid);
			attrs.put("StartDateTime", MsgTaskDeleteCmd.this.StartDateTime);
			attrs.put("EndDateTime", MsgTaskDeleteCmd.this.EndDateTime);
			attrs.put("SrcCode", MsgTaskDeleteCmd.this.SrcCode);
			attrs.put("Date", MsgTaskDeleteCmd.this.Date);
			attrs.put("TaskType", MsgTaskDeleteCmd.this.TaskType);
			return new MessageElement("TaskDelete", attrs, null);
		}
	}


}
