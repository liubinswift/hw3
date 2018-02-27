package com.viewscenes.device.radio;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;

public class StreamRecoveryReportCmd extends BaseMessageCommand {

	private String headCode;
	private String startDatetime;
	private String endDatetime;

	public StreamRecoveryReportCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setParams(String headCode, String startDatetime, String endDatetime) {
		this.headCode = headCode;
		this.startDatetime = startDatetime;
		this.endDatetime = endDatetime;
	}

	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {

		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {
			Map attrs = new LinkedHashMap(4);
			attrs.put("StartDateTime", StreamRecoveryReportCmd.this.startDatetime);
			attrs.put("EndDateTime", StreamRecoveryReportCmd.this.endDatetime);
			return new MessageElement("StreamRecoveryReport", attrs, null);
		}
	}

}
