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
 * Title : 频谱历史查询解析类
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
 * @author 谭长伟
 * @version 1.0
 */

public class MsgSpectrumHistoryQueryRes extends BaseMessageResponse {

	private Result result;
	
	public static class Result{
		private String replyID;
		private String taskID;
		private String equCode;

		private Collection spectrumScans;
		
		
		public Result(String taskid,String replyid,String equcode,Collection spectrumScans){
			this.taskID = taskid;
			this.equCode = equcode;
			this.spectrumScans = spectrumScans;
		}
		
		public String getReplyID() {

			return replyID;
		}

		public String getEquCode() {

			return equCode;
		}

		public String getTaskID() {

			return taskID;
		}

		public Collection getSpectrumScan() {

			return spectrumScans;
		}

	}

	
	
	public Result getResult() {
		return result;
	}

	public MsgSpectrumHistoryQueryRes() {

		setParsers(new IMessageParser[] { new ParserV8Radio() });

	}

	

	public static class ScanResult {

		private String band;
		private String freq;
		private String level;

		// static final boolean $assertionsDisabled; /* synthetic field */

		public String getBand() {

			return band;
		}

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

				if (attrs.containsKey("band")) {

					band = (String) attrs.get("band");
				}
				freq = (String) attrs.get("freq");
				level = (String) attrs.get("level");
				return;
			}
		}
	}

	public static class SpectrumTask {

		private String equcode;
		private String taskid;
		private Collection SpectrumScan;

		// static final boolean $assertionsDisabled; /* synthetic field */

		public String getEqucode() {

			return equcode;
		}

		public String getTaskid() {

			return taskid;
		}

		public Collection getSpectrumScan() {

			return SpectrumScan;
		}

		private SpectrumTask(String equcode, String taskid,
				Collection SpectrumScan) {

			if (equcode == null || taskid == null || SpectrumScan == null) {

				throw new AssertionError();
			} else {

				this.equcode = equcode;
				this.taskid = taskid;
				this.SpectrumScan = SpectrumScan;
				return;
			}
		}
	}

	public static class SpectrumScan {

		private String scanTime;
		private String spectrumID;
		private Collection scanResults;

		// static final boolean $assertionsDisabled; /* synthetic field */

		public String getSpectrumID() {

			return spectrumID;
		}

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

		private SpectrumScan(String scanTime, String spectrumID,
				Collection scanResults) {

			if (scanTime == null || spectrumID == null || scanResults == null) {

				throw new AssertionError();
			} else {

				this.spectrumID = spectrumID;
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
			MsgSpectrumHistoryQueryRes.ParserV8BaseRadio {

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

			if (qr == null) {
				return;
			}
			// replyID = (String)getAttributes().get("ReplyID");

			// System.out.println((String)getAttributes().get("replyid"));
			
			
			String equCode = qr.getAttributeValue("equcode");
			String taskID = qr.getAttributeValue("taskid");
			Collection qualitys = qr.getChildren();
			Collection spectrumScans = null;
			
			if (qualitys == null) {
				spectrumScans = new Vector();
			} else {
				spectrumScans = new ArrayList(qualitys.size());
				for (Iterator it = qualitys.iterator(); it.hasNext();) {
					MessageElement quality = (MessageElement) it.next();
					Collection qchildren = quality.getChildren();
					if (qchildren != null) {
						Collection srs = new ArrayList(qchildren.size());
						MessageElement sr;
						for (Iterator ittt = qchildren.iterator(); ittt
								.hasNext(); srs.add(new ScanResult(sr
								.getAttributes()))) {
							sr = (MessageElement) ittt.next();
						}
						spectrumScans.add(new SpectrumScan(quality
								.getAttributeValue("scandatetime"), srs));
					}
				}
			}
			
			result = new Result(taskID,"",equCode,spectrumScans);
		}
	}
}
