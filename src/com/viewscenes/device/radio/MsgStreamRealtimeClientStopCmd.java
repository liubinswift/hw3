package com.viewscenes.device.radio;

import java.util.*;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.radio.MsgStreamRealtimeClientStopCmd.ClientInfo;

/**
 * 
 * <p>
 * Title:客户端连接中断。
 * </p>
 * 
 * <p>
 * Description:新增接口。 支持最新的V8版本，还有边境接口。
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
public class MsgStreamRealtimeClientStopCmd extends BaseMessageCommand {

	public MsgStreamRealtimeClientStopCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	Collection ClientInfos = new ArrayList();

	public void setClientInfos(Collection list) {
		this.ClientInfos = list;
	}

	protected class BuilderV8Radio

	extends BaseMessageCommand.BuilderV8Radio {

		public BuilderV8Radio() {

		}

		protected MessageElement buildBody() {

			List list = new ArrayList();
			Iterator it = ClientInfos.iterator();
			while (it.hasNext()) {
				ClientInfo cl = (ClientInfo) it.next();
				Map attrs = new LinkedHashMap(2);
				attrs.put("EquCode", cl.getEquCode());
				attrs.put("ClientIP", cl.getClientIP());
				list.add(new MessageElement("ClientInfo", attrs, null));
			}
			return new MessageElement("StreamRealtimeClientStop", null, list);
		}

	}

	public static class ClientInfo {
		private String equCode;
		private String clientIP;
		private String type;

		public ClientInfo(String equCode, String clientIP, String type) {
			this.equCode = equCode;
			this.clientIP = clientIP;
			this.type = type;
		}

		public String getEquCode() {
			return this.equCode;
		}

		public String getClientIP() {
			return this.clientIP;
		}

		public String getType() {
			return this.type;
		}

	}

}
