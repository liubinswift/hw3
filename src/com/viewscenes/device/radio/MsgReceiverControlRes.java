package com.viewscenes.device.radio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.viewscenes.device.framework.BaseMessageResponse;
import com.viewscenes.device.framework.IMessageParser;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.util.InnerMsgType;

/**
 * 
 * <p>
 * Title: 接收机控制返回解析类
 * </p>
 * 
 * <p>
 * Description:支持v8版本。
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
 * @author:刘斌
 * @version 1.0
 */
public class MsgReceiverControlRes extends BaseMessageResponse {
	

	private Result result;
	
	public static class Result{
		private String _url;
		private Collection _values;
		
		public Result(String url,Collection values){
			this._url = url;
			this._values = values;
		}
		
		public String getURL() {
			return this._url;
		}

		public Collection getValues() {
			return this._values;
		}
	}
	
	
	
	public Result getResult() {
		return result;
	}

	public MsgReceiverControlRes() {
		setParsers(new IMessageParser[] { new ParserV8Radio() });
	}

	

	/**
	 * 
	 * V8版具体实现
	 * 
	 */
	protected class ParserV8Radio extends
			MsgReceiverControlRes.ParserV8BaseRadio {

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
			MessageElement rcr = getBody();
			if (rcr == null)
				return;
			String _url = "";
			Collection _values = null;
			_url = rcr.getAttributeValue("url");
			Collection ps = rcr.getChildren();
			if (ps == null)
				return;
			_values = new ArrayList(ps.size());
			MessageElement ea;
			for (Iterator easit = ps.iterator(); easit.hasNext(); _values
					.add(ea.getAttributeValue("value")))
				ea = (MessageElement) easit.next();

			result = new Result(_url,_values);
		}

	}
}
