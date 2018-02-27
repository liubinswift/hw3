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
 * Title : 指标实时查询解析类
 * </p>
 * 
 * <p>
 * Description: 支持v8版本广播。
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

public class MsgQualityRealtimeQueryRes extends BaseMessageResponse {

	
	private Result result;
	
	public static  class Result{
		private Collection qs;
		private String headTime = ""; // xml头部时间
		
		
		public Result(String headtime,Collection qs){
			this.headTime = headtime;
			this.qs = qs;
		}
		
		public Collection getQuality() {
			return qs;
		}

		public String getheadTime() {
			return this.headTime;
		}
		
	}
	
	
	public Result getResult() {
		return result;
	}

	public MsgQualityRealtimeQueryRes() {

		setParsers(new IMessageParser[] { new ParserV8Radio() });
	}

	

	public static class QualityIndex {

		private String type;
		private String desc;
		private String value;
		private String minvalue;
		private String maxvalue;
		private String xvalue;
		private String yvalue;

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

		public String getXvalue() {
			return xvalue;
		}

		public String getYvalue() {
			return yvalue;
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
				value = (String) attrs.get("value");
				minvalue = attrs.containsKey("min-value") ? (String) attrs
						.get("min-value") : "";
				maxvalue = attrs.containsKey("max-value") ? (String) attrs
						.get("max-value") : "";
				xvalue = attrs.containsKey("x-value") ? (String) attrs
						.get("x-value") : "";
				yvalue = attrs.containsKey("y-value") ? (String) attrs
						.get("y-value") : "";
				return;
			}
		}
	}

	public static class Quality {

		private String equCode;
		private String freq;
		private String band;
		private String checkTime;
		private Collection qualityIndexs;

		// static final boolean $assertionsDisabled; /* synthetic field */

		public String getEquCode() {
			return equCode;
		}

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
				equCode = (String) attrs.get("equcode");
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
			MsgQualityRealtimeQueryRes.ParserV8BaseRadio {

		protected ParserV8Radio() {

		}

		protected Map makeQualityAlarmAttrs(Map oriAttrs) {
			oriAttrs.put("equcode", equCode);
			oriAttrs.put("checktime", oriAttrs.get("checkdatetime"));
			return oriAttrs;
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

		protected String equCode;

		protected void doParse() {

			MessageElement qualityReport = getBody();
			String headTime = null;
			Map mm = getAttributes();
			headTime = mm.get("DateTime") == null ? "" : (String) mm
					.get("DateTime");
			headTime = headTime.trim();
			result = new Result(headTime,new ArrayList());
			
			
			if (qualityReport == null) {
				return;
			}
			

			equCode = qualityReport.getAttributeValue("equcode");
			Collection qualitys = qualityReport.getChildren();

			Collection qs =null;
			if (qualitys == null) {

				qualitys = new Vector();
			} else {

				qs = new ArrayList(qualitys.size());

				for (Iterator it = qualitys.iterator(); it.hasNext();) {

					MessageElement qulity = (MessageElement) it.next();
					Collection children = qulity.getChildren();

					if (children != null) {

						Collection qualityIndexs = new ArrayList(children
								.size());
						MessageElement child;

						for (Iterator ittt = children.iterator(); ittt
								.hasNext(); qualityIndexs.add(new QualityIndex(
								child.getAttributes()))) {

							child = (MessageElement) ittt.next();
						}
						qs.add(new Quality(makeQualityAttrs(qulity
								.getAttributes()), qualityIndexs));
					}
				}
			}
			
			result = new Result(headTime,qs);
		}

		protected Map makeQualityAttrs(Map oriAttrs) {
			return oriAttrs;
		}

	}

}
