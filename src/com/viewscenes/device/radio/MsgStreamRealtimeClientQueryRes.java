package com.viewscenes.device.radio;

import java.util.*;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageResponse;
import com.viewscenes.device.framework.IMessageParser;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.radio.MsgStreamRealtimeClientQueryRes.ClientInfo;
import com.viewscenes.device.util.InnerMsgType;

/**
 * 
 * <p>
 * Title:客户端连接查询返回。
 * </p>
 * 
 * <p>
 * Description:此接口为新增接口，v8版本
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
public class MsgStreamRealtimeClientQueryRes extends BaseMessageResponse {

	public MsgStreamRealtimeClientQueryRes() {

		setParsers(new IMessageParser[] { new ParserV8Radio() });

	}

	Collection streamRealtime = new ArrayList();

	public Collection getStreamRealtime() {
		return this.streamRealtime;
	}

	public class StreamRealtime {
		private String equCode, url, freq, bps, Type;
		private Collection clientInfo;

		public StreamRealtime(String equCode, String type, String url,
				String freq, String bps, Collection clientInfo) {
			this.equCode = equCode;
			this.url = url;
			this.freq = freq;
			this.bps = bps;
			this.Type = type;
			this.clientInfo = clientInfo;
		}

		public String getEquCode() {
			return this.equCode;
		}

		public String getURL() {
			return this.url;
		}

		public String getFreq() {
			return this.freq;
		}

		public String getBps() {
			return this.bps;
		}

		public String getType() {
			return this.Type;
		}

		public Collection getClientInfo() {
			return this.clientInfo;
		}

	}

	public class ClientInfo {
		private String ip;

		public ClientInfo(String t) {
			this.ip = t;
		}

		public String getIP() {
			return this.ip;
		}

	}

	/**
	 * 
	 * V8版具体实现
	 * 
	 */
	protected class ParserV8Radio extends
			MsgStreamRealtimeClientQueryRes.ParserV8BaseRadio {

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

		protected void doParse() {

			MessageElement tqr = getBody();

			Collection clients = tqr.getChildren();
			if (clients == null) {

				return;
			}

			for (Iterator clientsit = clients.iterator(); clientsit.hasNext();) {

				MessageElement client = (MessageElement) clientsit.next();
				Map indexMap = client.getAttributes();
				Collection info = client.getChildren();
				String EquCode = (String) indexMap.get("equcode");
				String Type = (String) indexMap.get("type");
				String URL = (String) indexMap.get("url");
				String Freq = (String) indexMap.get("freq");
				String Bps = (String) indexMap.get("bps");

				Collection cs = new ArrayList();
				for (Iterator it = info.iterator(); it.hasNext();) {
					MessageElement me = (MessageElement) it.next();
					Map map = me.getAttributes();
					String ip = (String) map.get("ip");
					cs.add(new ClientInfo(ip));

				}
				StreamRealtime qi = new StreamRealtime(EquCode, Type, URL,
						Freq, Bps, cs);
				streamRealtime.add(qi);

			}
		}

	}

}
