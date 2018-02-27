package com.viewscenes.device.framework;

import java.util.Map;
import com.viewscenes.device.util.InnerDevice;
import com.viewscenes.device.exception.DeviceReportException;
import com.viewscenes.device.util.InnerMsgType;

public abstract class BaseMessageResponse implements IMessageResponse {
	public IMessage msg;
	private InnerDevice device;
	private IMessageParser[] parsers;

	protected final void setParsers(IMessageParser[] parsers) {
		if ((parsers == null))
			throw new AssertionError();
		this.parsers = parsers;
	}

	private IMessageParser getMessageParser() {
		for (int i = 0; i < this.parsers.length; ++i) {

			if (this.parsers[i].getType().equals(this.device.getType())) {
				return this.parsers[i];
			}
		}
		return null;
	}

	public synchronized void setMessage(IMessage msg)
			throws DeviceReportException {
		if ((msg == null))
			throw new AssertionError();
		this.msg = msg;
		UpMessage um = (UpMessage) msg;
		if (um.getBody() != null && um.getBody().getName() != null
				&& um.getBody().getName().equals("autoreportfile")) {
			throw new DeviceReportException("",
					"回收数据量过大，后台自动回收到数据库中，请到数据查询中查询上报的数据!");
		}
		Map retAttrs = um.getReturnEle().getAttributes();
		String code = (String) retAttrs.get("Value");
		if (!(code.equals("0"))) {
			throw new DeviceReportException(code, (String) retAttrs.get("Desc"));
		}
		if (this.parsers == null) {
			return;
		}
		String srccode = (String) um.getHeader().getAttributes().get("SrcCode");
		String type = (String) um.getHeader().getAttributes().get("Type");
		if (InnerDevice.deviceType(srccode).equals("1")) {
			this.device = InnerDevice.instance(srccode
					+ type.substring(0, type.length() - 2).toLowerCase());
		} else {
			this.device = InnerDevice.instance(srccode);
		}
		getMessageParser().parseMessage(msg);
	}


	protected class ParserV8Radio extends BaseMessageResponse.BaseParser {
		protected ParserV8Radio() {
		}

		public InnerMsgType getType() {
			return InnerMsgType.instance("radio8");
		}
	}

	protected class ParserV8BaseRadio extends BaseMessageResponse.BaseParser {
		protected ParserV8BaseRadio() {
		}

		public InnerMsgType getType() {
			return InnerMsgType.instance("radio8");
		}
	}

	protected abstract class BaseParser implements IMessageParser {
		private Map attrs;
		private MessageElement body;

		public void parseMessage(IMessage msg) {
			if ((msg == null))
				throw new AssertionError();
			UpMessage um = (UpMessage) msg;
			this.attrs = um.getHeader().getAttributes();
			this.body = um.getBody();
			doParse();
		}

		protected final Map getAttributes() {
			return this.attrs;
		}

		protected final MessageElement getBody() {
			return this.body;
		}

		protected void doParse() {
		}
	}
}
