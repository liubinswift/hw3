package com.viewscenes.device.radio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

public class MsgFileQueryCmd extends BaseMessageCommand {
	private Collection freqs;
	private String resultType;
	private String startDate;
	private String endDate;
	private String taskID;

	public MsgFileQueryCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setParams(String resultType, String startDate, String endDate,
			String taskID) {
		this.resultType = resultType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.taskID = taskID;
	}

	public void setFrequencys(Collection freqs) {
		this.freqs = freqs;
	}

	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {

		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {
			Map attrs = new LinkedHashMap(4);
			attrs.put("Type", MsgFileQueryCmd.this.resultType);
			attrs.put("StartDateTime", MsgFileQueryCmd.this.startDate);
			attrs.put("EndDateTime", MsgFileQueryCmd.this.endDate);
			attrs.put("TaskID", MsgFileQueryCmd.this.taskID);
			List list = new ArrayList(MsgFileQueryCmd.this.freqs.size());
			for (Iterator it = MsgFileQueryCmd.this.freqs.iterator(); it
					.hasNext();) {
				MsgFileQueryCmd.Frequency cs = (MsgFileQueryCmd.Frequency) it
						.next();
				Map map = new LinkedHashMap(2);
				map.put("Band", cs.band);
				map.put("Value", cs.value);
				list.add(new MessageElement("Frequency", map, null));
			}
			return new MessageElement("FileQuery", attrs, list);
		}
	}
	public static class Frequency
	  {
	    private String band;
	    private String value;

	    public Frequency(String band, String value)
	    {
	      this.band = band;
	      this.value = value;
	    }
	  }
}
