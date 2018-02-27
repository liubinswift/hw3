package com.viewscenes.device.radio;

import java.util.*;

import com.viewscenes.device.framework.BaseMessageResponse;
import com.viewscenes.device.framework.IMessageParser;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.util.InnerMsgType;

/**
 * 
 * <p>
 * Title: ����ִ��״̬����
 * </p>
 * 
 * <p>
 * Description:֧�ֹ㲥��v8�汾
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
 * @author ����
 * @version 1.0
 */
public class MsgTaskStatusQueryRes extends BaseMessageResponse {
	private Collection ts;

	public MsgTaskStatusQueryRes() {
		setParsers(new IMessageParser[] { new ParserV8Radio() });

	}

	public Collection getTaskStatus() {
		return ts;
	}

	/**
	 * 
	 * V8�����ʵ��
	 * 
	 */
	protected class ParserV8Radio extends
			MsgTaskStatusQueryRes.ParserV8BaseRadio {

		protected ParserV8Radio() {

		}

		public InnerMsgType getType() {

			return InnerMsgType.instance("radio8");
		}
	}

	/**
	 * 
	 * V8���������ʵ��
	 * 
	 */
	protected class ParserV8BaseRadio extends
			BaseMessageResponse.ParserV8BaseRadio {

		protected void doParse() {
			MessageElement ear = getBody();
			if (ear == null)
				return;
			Collection eas = ear.getChildren();
			if (eas == null)
				return;
			ts = new ArrayList(eas.size());
			for (Iterator easit = eas.iterator(); easit.hasNext(); ts
					.add(new TaskStatus(((MessageElement) easit.next())
							.getAttributes())))
				;
		}

		protected ParserV8BaseRadio() {
		}
	}

	public static class TaskStatus {

		private String taskID;
		private String status;
		private String desc;

		public String getTaskID() {
			return taskID;
		}

		public String getStatus() {
			return status;
		}

		public String getDesc() {
			return desc;
		}

		private TaskStatus(Map attrs) {
			if (attrs == null) {
				throw new AssertionError();
			} else {
				taskID = (String) attrs.get("taskid");
				status = (String) attrs.get("status");
				desc = (String) attrs.get("desc");
				return;
			}
		}

	}

}
