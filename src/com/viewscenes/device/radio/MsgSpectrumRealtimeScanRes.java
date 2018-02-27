package com.viewscenes.device.radio;

import com.viewscenes.device.*;

import java.util.*;

import com.viewscenes.device.framework.BaseMessageResponse;
import com.viewscenes.device.framework.IMessageParser;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.radio.MsgQualityRealtimeQueryRes.Result;
import com.viewscenes.device.util.InnerMsgType;

/**
 * 
 * <p>
 * Title : 频谱实时查询解析类
 * </p>
 * 
 * <p>
 * Description: 支持v8版本。
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

public class MsgSpectrumRealtimeScanRes extends BaseMessageResponse {


	private Result result;
	
	public static class Result{
		private String band;
		private String equCode;
		private Collection spectrumScans;
		private String headTime = ""; // xml头部时间
		
		public Result(String equcode,String band,String headtime,Collection spectrumScans){
			this.equCode = equcode;
			this.band = band;
			this.headTime = headtime;
			this.spectrumScans = spectrumScans;
		}
		
		public String getheadTime() {
			return this.headTime;
		}

		public String getEquCode() {

			return equCode;
		}

		public String getBand() {

			return band;
		}

		public Collection getSpectrumScan() {

			return spectrumScans;
		}
		
	}
	
	
	public Result getResult() {
		return result;
	}

	public MsgSpectrumRealtimeScanRes() {

		setParsers(new IMessageParser[] { new ParserV8Radio() });
	}

	

	public static class ScanResult {

		private String freq;
		private String level;

		// static final boolean $assertionsDisabled; /* synthetic field */

		public String getFreq() {
			return freq;
		}

		public String getLevel() {
			return level;
		}

		private ScanResult(Map attrs) {
			if (attrs == null) {
				throw new AssertionError();
			} else {
				freq = (String) attrs.get("freq");
				level = (String) attrs.get("level");
				return;
			}
		}

	}

	public static class SpectrumScan {

		private String scanTime;
		private Collection scanResults;

		// static final boolean $assertionsDisabled; /* synthetic field */

		public String getScanTime() {
			return scanTime;
		}

		public Collection getScanResults() {

			return scanResults;
		}

		private SpectrumScan(String scanTime, Collection scanResults) {
			if (scanTime == null || scanResults == null) {
				throw new AssertionError();
			} else {
				this.scanTime = scanTime;
				this.scanResults = scanResults;
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
			MsgSpectrumRealtimeScanRes.ParserV8BaseRadio {

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
			MessageElement qr = getBody();

			String headTime = null;
			Map mm = getAttributes();
			headTime = mm.get("DateTime") == null ? "" : (String) mm
					.get("DateTime");
			headTime = headTime.trim();
			result = new Result("","",headTime,new ArrayList());
			
			if (qr == null) {
				return;
			}

			String equCode = qr.getAttributeValue("equcode");

			String band = null;
			if (qr.getAttributeValue("band") != null) {

				band = qr.getAttributeValue("band");
			}
			Collection qualitys = qr.getChildren();
			Collection spectrumScans = null;
			if (qualitys == null) {
				return;
			}
			spectrumScans = new ArrayList(qualitys.size());

			for (Iterator it = qualitys.iterator(); it.hasNext();) {
				MessageElement quality = (MessageElement) it.next();
				Collection qchildren = quality.getChildren();
				if (qchildren != null) {

					Collection srs = new ArrayList(qchildren.size());
					MessageElement sr;

					for (Iterator ittt = qchildren.iterator(); ittt.hasNext(); srs
							.add(new ScanResult(sr.getAttributes()))) {
						sr = (MessageElement) ittt.next();
					}
					spectrumScans.add(new SpectrumScan(quality
							.getAttributeValue("scandatetime"), srs));
				}
			}
			
			result = new Result(equCode,band,headTime,spectrumScans);
		}
	}

}
