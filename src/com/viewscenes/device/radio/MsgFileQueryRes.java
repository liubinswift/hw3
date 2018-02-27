package com.viewscenes.device.radio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageResponse;
import com.viewscenes.device.framework.IMessageParser;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.util.InnerMsgType;

/**
 * 
 * <p>
 * Title:文件查询返回类。
 * </p>
 * 
 * <p>
 * Description:支持v8版本.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company: Viewscenes
 * </p>
 * 
 * @author 刘斌
 * @version 1.0
 */
public class MsgFileQueryRes extends BaseMessageResponse {
	private Collection resultInfos;

	public MsgFileQueryRes() {
		setParsers(new IMessageParser[] { new ParserV8Radio() });
	}

	public Collection getResultInfos() {
		return this.resultInfos;
	}

	protected class ParserV8Radio extends MsgFileQueryRes.ParserV8BaseRadio {
		protected ParserV8Radio() {
		}

		protected Map makeAttrs(Map oriAttrs) {
			oriAttrs.put("fileurl", oriAttrs.get("filename"));
			oriAttrs.put("frequency", oriAttrs.get("freq"));
			return oriAttrs;
		}

		public InnerMsgType getType() {
			return InnerMsgType.instance("radio8");
		}
	}

	protected class ParserV8BaseRadio extends
			BaseMessageResponse.ParserV8BaseRadio {
		protected ParserV8BaseRadio() {
		}

		protected void doParse() {
			MessageElement ear = getBody();
			if (ear == null)
				return;
			Collection eas = ear.getChildren();
			if (eas == null)
				return;

			resultInfos = new ArrayList(eas.size());
			MessageElement ri;
			for (Iterator easit = eas.iterator(); easit.hasNext(); resultInfos
					.add(new ResultInfo(makeAttrs(ri.getAttributes()))))
				ri = (MessageElement) easit.next();

		}

		protected Map makeAttrs(Map oriAttrs) {
			return oriAttrs;
		}
	}

	public static class ResultInfo {
		private String resultType;
		private String startDateTime;
		private String endDateTime;
		private String fileURL;
		private String taskID;
		private String band;
		private String freq;
		private String size;

		public ResultInfo(){};
		
		protected ResultInfo(Map attrs) {
			this.resultType = ((String) attrs.get("resulttype"));
			this.startDateTime = ((String) attrs.get("startdatetime"));
			this.endDateTime = ((String) attrs.get("enddatetime"));
			this.fileURL = ((String) attrs.get("fileurl"));
			this.taskID = ((String) attrs.get("taskid"));
			this.band = ((String) attrs.get("band"));
			this.freq = ((String) attrs.get("frequency"));
			this.size = ((String) attrs.get("size"));
		}

		public String getSize() {
			return this.size;
		}

		public String getFileURL() {
			return this.fileURL;
		}

		public String getTaskID() {
			return this.taskID;
		}

		public String getBand() {
			return this.band;
		}

		public String getFreq() {
			return this.freq;
		}

		public String getResultType() {
			return this.resultType;
		}

		public String getStartDateTime() {
			return this.startDateTime;
		}

		public String getEndDateTime() {
			return this.endDateTime;
		}

	}
}
