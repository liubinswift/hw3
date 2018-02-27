package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import java.util.*;

import com.viewscenes.device.framework.BaseMessageResponse;
import com.viewscenes.device.framework.IMessageParser;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.util.InnerMsgType;

/**
 * 
 * <p>
 * Title : 频偏历史查询解析类
 * </p>
 * 
 * <p>
 * Description: 兼容v5、v6、v7、v8版本。
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
 * @author 赵广磊
 * @version 1.0
 */

public class MsgOffsetHistoryQueryRes extends BaseMessageResponse {

	private Result result;
	
	public class Result{
		
		String equCode;
		String taskID;
		Collection qs;

		public Result(String taskId,String equcode,Collection qs){
			this.taskID = taskId;
			this.equCode = equcode;
			this.qs = qs;
		}
		
		public String getEquCode() {
			return equCode;
		}
		public String getTaskID() {
			return taskID;
		}
		public Collection getOffsets() {
			return qs;
		}
		
	}
	
	
	
	
	public Result getResult() {
		return result;
	}

	public MsgOffsetHistoryQueryRes() {

		setParsers(new IMessageParser[] { new ParserV8Radio() });
	}


	public static class Offset {

		private String freq;
		private String band;
		private String checkTime;
		private String offset;

		// static final boolean $assertionsDisabled; /* synthetic field */

		public String getFreq() {
			return freq;
		}

		public String getBand() {
			return band;
		}

		public String getCheckTime() {
			return checkTime;
		}

		public String getOffset() {
			return offset;
		}

		private Offset(Map attrs) {

			if (attrs == null) {
				throw new AssertionError();
			} else {

				freq = (String) attrs.get("freq");
				band = (String) attrs.get("band");
				checkTime = (String) attrs.get("checkdatetime");
				offset = (String) attrs.get("offset");
				return;

			}
		}
	}

	/**
	 * 
	 * V8版具体实现
	 * 
	 */
	protected class ParserV8Radio extends
			MsgOffsetHistoryQueryRes.ParserV8BaseRadio {

		protected ParserV8Radio() {

		}

		public InnerMsgType getType() {

			return InnerMsgType.instance("radio8");
		}
	}

	/**
	 * 
	 * V8基础版具体实现
	 * 
	 */
	protected class ParserV8BaseRadio extends
			BaseMessageResponse.ParserV8BaseRadio {

		protected ParserV8BaseRadio() {

		}

		protected void doParse() {

			MessageElement qualityReport = getBody();

			if (qualityReport == null)
				return;

			String equCode = qualityReport.getAttributeValue("equcode");
			String taskID = qualityReport.getAttributeValue("taskid");
			Collection qualitys = qualityReport.getChildren();

			Collection qs = null;
			
			if (qualitys == null) {

				qs = new Vector();
			} else {

				qs = new ArrayList(qualitys.size());
				MessageElement qulity;

				for (Iterator it = qualitys.iterator(); it.hasNext(); qs
						.add(new Offset(qulity.getAttributes()))) {
					qulity = (MessageElement) it.next();
				}
			}
			
			result = new Result(taskID,equCode,qs);
		}
	}
}
