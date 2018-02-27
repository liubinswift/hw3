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
 * Title : 指标历史查询解析类
 * </p>
 * 
 * <p>
 * Description: 兼容v5、v6、v7、v8、边境广播电视V1版本。
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

public class MsgQualityHistoryQueryRes extends BaseMessageResponse {

	
	private Result result;
	
	
	public static class Result{
		
		private String equCode;
		private String taskID;
		private Collection qs;
		private Collection qualityScans;
		
		public Result(String taskId,String equcode,Collection qs ,Collection qualityScans){
			this.equCode =equcode;
			this.taskID =taskId;
			this.qs =qs;
			this.qualityScans = qualityScans;
		}
		
		public Collection getQualityScans() {

			return qualityScans;
		}

		public Collection getQuality() {

			return qs;
		}

		public String getEquCode() {

			return equCode;
		}

		public String getTaskID() {

			return taskID;
		}
	}
	
	
	
	public Result getResult() {
		return result;
	}

	public MsgQualityHistoryQueryRes() {

		setParsers(new IMessageParser[] { new ParserV8Radio() });
	}

	

	public static class Quality {

		private String freq;
		private String band;
		private String checkTime;
		private Collection qualityIndexs;

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

		public Collection getQualityIndexs() {

			return qualityIndexs;
		}

		private Quality(Map attrs, Collection qis) {

			if (attrs == null) {

				throw new AssertionError();
			} else {

				qualityIndexs = qis;
				freq = (String) attrs.get("freq");

				if (attrs.containsKey("band")) {
					band = attrs.containsKey("band") ? (String) attrs
							.get("band") : "";
				}

				checkTime = (String) attrs.get("checkdatetime");
				return;
			}
		}
	}

	public static class QualityIndex {

		private String type;
		private String desc;
		private String value;
		private String minvalue;
		private String maxvalue;

		// static final boolean $assertionsDisabled; /* synthetic field */

		public String getType() {
			return type;
		}

		public String getDesc() {
			return desc;
		}

		public String getValue() {
			return value;
		}

		public String getMinvalue() {
			return minvalue;
		}

		public String getMaxvalue() {
			return maxvalue;
		}

		private QualityIndex(Map attrs) {
			if (attrs == null) {
				throw new AssertionError();
			} else {

				if (attrs.containsKey("desc")) {

					desc = attrs.containsKey("desc") ? (String) attrs
							.get("desc") : "";
				}

				type = (String) attrs.get("type");
				value = attrs.containsKey("value") ? (String) attrs
						.get("value") : "";
				minvalue = attrs.containsKey("minvalue") ? (String) attrs
						.get("minvalue") : "";
				maxvalue = attrs.containsKey("maxvalue") ? (String) attrs
						.get("maxvalue") : "";
				return;
			}
		}
	}

	// /**
	// *
	// * 边境电视V1版具体实现
	// *
	// */
	// protected class ParserV1FrontierVideo extends
	// MsgQualityHistoryQueryRes.ParserV1FrontierRadio {
	//
	// protected ParserV1FrontierVideo() {
	//
	// }
	//
	// protected Map makeQualityAttrs(Map oriAttrs) {
	// oriAttrs.put("checktime", oriAttrs.get("checkdatetime"));
	// return oriAttrs;
	// }
	//
	// public InnerMsgType getType() {
	//
	// return InnerMsgType.instance("tv1");
	// }
	// }
	//
	// /**
	// *
	// * 边境广播V1版具体实现
	// *
	// */
	// protected class ParserV1FrontierRadio extends
	// BaseMessageResponse.ParserV1FrontierRadio {
	//
	// protected ParserV1FrontierRadio() {
	//
	// }
	//
	// protected void doParse() {
	//
	// MessageElement qualityReport = getBody();
	//
	// if (qualityReport == null) {
	//
	// return;
	// }
	//
	// Collection d = qualityReport.getChildren();
	//
	// if(d == null) {
	// qualityScans = new Vector();
	// return;
	// } else {
	// qualityScans = new ArrayList(d.size());
	// }
	// qs = new ArrayList();
	// for(Iterator tit = d.iterator();tit.hasNext();) {
	//
	// MessageElement x = (MessageElement)tit.next();
	// equCode = x.getAttributeValue("equcode");
	// taskID = x.getAttributeValue("taskid");
	// Collection qualitys = x.getChildren();
	//
	// if (qualitys == null) {
	// return;
	// }
	// for (Iterator it = qualitys.iterator(); it.hasNext();) {
	//
	// MessageElement qulity = (MessageElement)it.next();
	// Collection children = qulity.getChildren();
	//
	// if (children != null) {
	//
	// Collection qualityIndexs = new ArrayList(children.size());
	// MessageElement child;
	//
	// for (Iterator ittt = children.iterator(); ittt.hasNext();
	// qualityIndexs.add(new QualityIndex(child.getAttributes()))) {
	// child = (MessageElement)ittt.next();
	// }
	// qs.add(new Quality(makeQualityAlarmAttrs(qulity.getAttributes()),
	// qualityIndexs));
	// }
	// }
	// }
	// }
	//
	// protected Map makeQualityAlarmAttrs(Map oriAttrs) {
	// return oriAttrs;
	// }
	// }

	/**
	 * 
	 * V8版具体实现
	 * 
	 */
	protected class ParserV8Radio extends
			MsgQualityHistoryQueryRes.ParserV8BaseRadio {

		protected ParserV8Radio() {

		}

		protected Map makeQualityAttrs(Map oriAttrs) {
			oriAttrs.put("checktime", oriAttrs.get("checkdatetime"));
			return oriAttrs;
		}

		public InnerMsgType getType() {
			return InnerMsgType.instance("radio8");
		}
	}

	// /**
	// *
	// * V7版具体实现
	// *
	// */
	// protected class ParserV7Radio extends
	// MsgQualityHistoryQueryRes.ParserV4Radio {
	//
	// protected ParserV7Radio() {
	//
	// }
	//
	// protected Map makeQualityAttrs(Map oriAttrs) {
	// oriAttrs.put("checktime", oriAttrs.get("checkdatetime"));
	// return oriAttrs;
	// }
	//
	// public InnerMsgType getType() {
	// return InnerMsgType.instance("radio7");
	// }
	// }
	//
	// /**
	// *
	// * V6版具体实现
	// *
	// */
	// protected class ParserV6Radio extends
	// MsgQualityHistoryQueryRes.ParserV4Radio {
	//
	// protected ParserV6Radio() {
	//
	// }
	//
	// protected Map makeQualityAttrs(Map oriAttrs) {
	// oriAttrs.put("checktime", oriAttrs.get("checkdatetime"));
	// return oriAttrs;
	// }
	//
	// public InnerMsgType getType() {
	// return InnerMsgType.instance("radio6");
	// }
	// }
	//
	// /**
	// *
	// * V5版具体实现
	// *
	// */
	// protected class ParserV5Radio extends
	// MsgQualityHistoryQueryRes.ParserV4Radio {
	//
	// protected ParserV5Radio() {
	//
	// }
	//
	// protected Map makeQualityAttrs(Map oriAttrs) {
	// oriAttrs.put("checktime", oriAttrs.get("checkdatetime"));
	// return oriAttrs;
	// }
	//
	// public InnerMsgType getType() {
	// return InnerMsgType.instance("radio5");
	// }
	// }

	/**
	 * 
	 * V4版具体实现
	 * 
	 */
	protected class ParserV8BaseRadio extends
			BaseMessageResponse.ParserV8BaseRadio {

		protected ParserV8BaseRadio() {

		}

		protected void doParse() {

			MessageElement qualityReport = getBody();

			if (qualityReport == null) {
				return;
			}
			
			Collection qualityScans = null;
			
			Collection qu = qualityReport.getChildren();
			if (qu == null) {
				qualityScans = new Vector();
			} else {
				qualityScans = new ArrayList(qu.size());
			}

			
			String equCode = qualityReport.getAttributeValue("equcode");
			String taskID = qualityReport.getAttributeValue("taskid");
			Collection qualitys = qualityReport.getChildren();

			if (qualitys == null) {
				return;
			}
			Collection qs = null;
			qs = new ArrayList(qualitys.size());

			for (Iterator it = qualitys.iterator(); it.hasNext();) {

				MessageElement qulity = (MessageElement) it.next();
				Collection children = qulity.getChildren();

				if (children != null) {

					Collection qualityIndexs = new ArrayList(children.size());
					MessageElement child;

					for (Iterator ittt = children.iterator(); ittt.hasNext(); qualityIndexs
							.add(new QualityIndex(child.getAttributes()))) {
						child = (MessageElement) ittt.next();
					}
					qs.add(new Quality(
							makeQualityAttrs(qulity.getAttributes()),
							qualityIndexs));
				}
			}
			
			result = new Result(taskID,equCode,qs,qualityScans);
		}

		protected Map makeQualityAttrs(Map oriAttrs) {
			return oriAttrs;
		}

	}
}
