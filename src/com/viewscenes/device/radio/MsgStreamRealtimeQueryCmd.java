package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;

/**
 * 
 * 录音录像实时查询
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
 * @version 8.0
 */

public class MsgStreamRealtimeQueryCmd extends BaseMessageCommand {

	private Collection channels;

	public MsgStreamRealtimeQueryCmd() {

		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	/**
	 * 设置查询的设备列表
	 * 
	 * @param equs
	 *            RealtimeStream对象的Collection
	 * @preconditions equs != null
	 * @see RealtimeStream
	 */

	public void setEqus(Collection equs) {

		this.channels = equs;

	}

	/**
	 * 
	 * RealtimeStream内部类
	 * 
	 * <p>
	 * Copyright: Copyright (c) 2009
	 * </p>
	 * 
	 * <p>
	 * Company: Viewscenes
	 * </p>
	 * 
	 * @author zgl
	 * @version 1.0
	 */
	public static class RealtimeStream {

		private String equcode, lastUrl, band, freq, bps, encode, action;
//				width, height, fps, angle, zoom, channel;
//		private boolean v7RadioFlag = true;

		/**
		 * V7版本包括广播和摄像头
		 * 
		 * @param index
		 *            String
		 * @param equcode
		 *            String
		 * @param lastUrl
		 *            String
		 * @param band
		 *            String
		 * @param freq
		 *            String
		 * @param bps
		 *            String
		 * @param encode
		 *            String
		 * @param action
		 *            String
		 */
//		public RealtimeStream(String equcode, String lastUrl, String band,
//				String freq, String bps, String encode, String action) {
//
//			this.equcode = equcode;
//
//			this.freq = freq;
//
//			this.bps = bps;
//
//			this.encode = encode;
//
//			this.band = band;
//
//			this.lastUrl = lastUrl;
//
//			this.action = action;
//
//		}

		/**
		 * V8广播V1
		 * 
		 * @param index
		 *            String
		 * @param equcode
		 *            String
		 * @param lastUrl
		 *            String
		 * @param band
		 *            String
		 * @param freq
		 *            String
		 * @param bps
		 *            String
		 * @param encode
		 *            String
		 * @param action
		 *            String
		 */
		public RealtimeStream(String equcode, String lastUrl, String band,
				String freq, String bps, String encode, String action) {

			this.equcode = equcode;

			this.freq = freq;

			this.bps = (bps==null || bps.equals(""))?"32000":bps;

			this.encode = encode;

			this.band = band;

			this.lastUrl = lastUrl;

			this.action = action;

//			this.v7RadioFlag = radioFlag;
		}

		public String getAction() {
			return action;
		}

		/**
		 * V8视频，V7摄像头实时
		 * 
		 * @param equcode
		 *            String
		 * @param lastUrl
		 *            String
		 * @param angle
		 *            String
		 * @param zoom
		 *            String
		 * @param channel
		 *            String
		 * @param bps
		 *            String
		 * @param encode
		 *            String
		 * @param action
		 *            String
		 */
//		public RealtimeStream(String equcode, String lastUrl, String angle,
//				String zoom, String channel, String bps, String encode,
//				String action, boolean radioFlag) {
//
//			this.equcode = equcode;
//
//			this.lastUrl = lastUrl;
//
//			this.angle = angle;
//
//			this.zoom = zoom;
//
//			this.channel = channel;
//
//			this.bps = bps;
//
//			this.encode = encode;
//
//			this.action = action;
//
//			this.v7RadioFlag = radioFlag;
//		}

		/**
		 * V4
		 * 
		 * @param index
		 *            String
		 * @param equcode
		 *            String
		 * @param lastUrl
		 *            String
		 * @param band
		 *            String
		 * @param freq
		 *            String
		 * @param bps
		 *            String
		 * @param encode
		 *            String
		 * @param V4
		 *            String 传入空值
		 * @param V44
		 *            String 传入空值
		 * @param V444
		 *            String 传入空值
		 */
//		public RealtimeStream(String equcode, String lastUrl, String band,
//				String freq, String bps, String encode, String V4, String V44,
//				String V444, String V4444) {
//
//			this.equcode = equcode;
//
//			this.freq = freq;
//
//			this.bps = bps;
//
//			this.encode = encode;
//
//			this.band = band;
//
//			this.lastUrl = lastUrl;
//
//		}

		/**
		 * 边境视频V1
		 * 
		 * @param equcode
		 *            String
		 * @param freq
		 *            String
		 * @param bps
		 *            String
		 * @param encode
		 *            String
		 * @param width
		 *            String
		 * @param height
		 *            String
		 * @param fps
		 *            String
		 * @param lastUrl
		 *            String
		 * @param frontier
		 *            String
		 */
//		public RealtimeStream(String equcode, String freq, String bps,
//				String encode, String width, String height, String fps,
//				String lastUrl, String action) {
//
//			this.equcode = equcode;
//
//			this.freq = freq;
//
//			this.bps = bps;
//
//			this.encode = encode;
//
//			this.width = width;
//
//			this.height = height;
//
//			this.fps = fps;
//
//			this.lastUrl = lastUrl;
//
//			this.action = action;
//
//		}

	}


	/**
	 * V8版，具体实现
	 * 
	 */
	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {

		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {

			List list = new ArrayList(channels.size());

			int index = 0;

			for (Iterator it = channels.iterator(); it.hasNext();) {

				RealtimeStream cs = (RealtimeStream) it.next();

				Map map = new LinkedHashMap(8);

				map.put("Index", String.valueOf(index++));

				map.put("EquCode", cs.equcode);

				map.put("LastURL", cs.lastUrl);

				map.put("Band", cs.band);

				map.put("Freq", cs.freq);

				map.put("Bps", cs.bps);

				map.put("Encode", cs.encode);

				map.put("Action", cs.action);

				list.add(new MessageElement("RealtimeStream", map, null));

			}

			return new MessageElement("StreamRealtimeQuery", null, list);

		}
	}


}
