package com.viewscenes.device.framework;

import java.util.LinkedHashMap;
import java.util.Map;
import com.viewscenes.device.util.InnerDevice;
import com.viewscenes.device.exception.DeviceNotExistException;
import com.viewscenes.device.util.MsgUtil;
import com.viewscenes.device.util.InnerMsgType;

public abstract class BaseMessageCommand implements IMessageCommand {
	private IMessage msg;
	private InnerDevice device;
	private IMessageBuilder[] builders;
	private IMessageBuilder builder;
	private String user;
	private String password;
	private String priority;

	protected final void setBuilders(IMessageBuilder[] builders) {
		this.builders = builders;
	}

	private IMessageBuilder getMessageBuilder() {
		for (int i = 0; i < this.builders.length; ++i) {
			if (this.builders[i].getType().equals(this.device.getType())) {
				return this.builders[i];
			}
		}
		return null;
	}

	public void setDestCode(String destCode) throws DeviceNotExistException {
		this.device = InnerDevice.instance(destCode);
		if (this.device == null) {
			throw new DeviceNotExistException(destCode + "出现问题!");
		}
		this.builder = getMessageBuilder();
		if (this.builder == null)
			throw new DeviceNotExistException(destCode + ":"
					+ this.device.getType().getType() + ":消息类型与设备类型不匹配");
	}

	/**
	 * 边境因为有三个类型的（common,radio,video），这里需要把类型确定下来。 author:刘斌
	 */
	public void setDestType(String destType) {

	}

	public void setUserInfo(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public synchronized IMessage getMessage() {
		if (this.msg == null) {
			if (this.builder == null) {
				System.out.println("可能没有设置dstCode");
			}
			this.msg = this.builder.buildMessage();
		}
		return this.msg;
	}

	public int getTimedout() {
		return MsgUtil.getMsgTimedOut(getMsgName());
	}

	protected String getMsgName() {
		return "default";
	}

	protected abstract class BuilderV8Radio extends
			BaseMessageCommand.BaseBuilder8 {
		protected BuilderV8Radio() {
		}

		public InnerMsgType getType() {
			return InnerMsgType.instance("radio8");
		}
	}


	protected abstract class BaseBuilder8 extends
			BaseMessageCommand.BaseBuilder {
		protected BaseBuilder8() {

		}

		protected Map buildAttr() {
			Map msgAttr = new LinkedHashMap(4);
			msgAttr.put("Version", getType().getVersion());
			msgAttr.put("Type", getType().getType() + "Down");
			msgAttr.put("DstCode", BaseMessageCommand.this.device.getCode());
			msgAttr.put("Priority",
					(BaseMessageCommand.this.priority == null) ? "1"
							: BaseMessageCommand.this.priority);
			return msgAttr;
		}
	}


	// -------------zgl---2009-11-05---end----------------------
	protected abstract class BaseBuilder implements IMessageBuilder {
		public IMessage buildMessage() {
			return new DownMessage(buildAttr(), buildBody());
		}

		protected Map buildAttr() {
			Map msgAttr = new LinkedHashMap(6);
			msgAttr.put("Version", getType().getVersion());
			msgAttr.put("Type", getType().getType() + "Down");
			msgAttr.put("DstCode", BaseMessageCommand.this.device.getCode());
			// msgAttr.put("UserName", (BaseMessageCommand.this.user == null) ?
			// "qc" : BaseMessageCommand.this.user);
			// msgAttr.put("PassWord", (BaseMessageCommand.this.password ==
			// null) ? "bw" : BaseMessageCommand.this.password);
			msgAttr.put("Priority",
					(BaseMessageCommand.this.priority == null) ? "1"
							: BaseMessageCommand.this.priority);
			return msgAttr;
		}

		protected abstract MessageElement buildBody();
	}
}
