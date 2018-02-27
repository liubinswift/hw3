package com.viewscenes.device.radio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageResponse;
import com.viewscenes.device.framework.IMessageParser;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.util.InnerMsgType;
import com.viewscenes.device.util.Zip;
import com.viewscenes.util.business.SiteRunplanUtil;

public class MsgFileRetrieveRes extends BaseMessageResponse {
	private Collection _values;

	public MsgFileRetrieveRes() {
		setParsers(new IMessageParser[] { new ParserV8Radio() });
	}

	public Collection getValues() {
		return this._values;
	}

	/**
	 * 
	 * V8�����ʵ��
	 * 
	 */
	protected class ParserV8Radio extends MsgFileRetrieveRes.ParserV8BaseRadio {

		protected ParserV8Radio() {

		}

		public InnerMsgType getType() {

			return InnerMsgType.instance("radio8");
		}
	}

	/**
	 * �����½ӿ�������Ӧ�ķ��ض�����
	 * 
	 * @author thinkpad
	 * 
	 */
	public static class FileRetrieve {

		private String fileUrl;
		private String level;
		private String fmModulation = "";
		private String amModulation = "";
		private String offset;

		// ������Щ���Կ�ͨ���ļ���(fileUrl)�Ƶõ�
		private String fileName;
		private String headCode;
		private String taskId;
		private String startDatetime;
		private String endDatetime;
		private String freq;
		private String equCode;
		private String band;
		private String language = "";
		private String stationName = "";	//����̨����
		private String recType = "";	//¼�����ͣ�Ч������������ʱ
		private String reveiceType;	//���ջ�����

		public String getReveiceType() {
			return reveiceType;
		}

		public void setReveiceType(String reveiceType) {
			this.reveiceType = reveiceType;
		}

		public FileRetrieve() {
		};

		protected FileRetrieve(Map attrs) {
			this.fileUrl = ((String) attrs.get("fileurl"));
			this.level = ((String) attrs.get("level"));
			this.fmModulation = ((String) attrs.get("fm-modulation")) == null ? ""
					: ((String) attrs.get("fm-modulation"));
			this.amModulation = ((String) attrs.get("am-modulation")) == null ? ""
					: ((String) attrs.get("am-modulation"));
			this.offset = ((String) attrs.get("offset"));
			 
			//¼���ļ�����������վ�����_�����_¼����ʼ����ʱ��_¼����������ʱ��_Ƶ��_���ջ�����_����_����̨_¼������_���������.mp3
			//OAF07A_43136_20121225114102_20121225114203_97400_R1_����_2022_Ч��_NI-1000.mp3
			this.setFileUrl(this.getFileUrl().replaceAll("\\\\", "/"));
			
			
			this.setFileName(this.getFileUrl().substring(this.getFileUrl().lastIndexOf("/")+1));
			String[] names = this.getFileName().split("_");
			
			String ext = ".mp3";
			switch (names.length){
				case 10:
					ext = fileUrl.endsWith(".mp3")?".mp3":".zip";
					this.setReveiceType(names[9].substring(0,names[9].indexOf(ext)));
				case 9:
					//¼�����ͣ�0��Ч��¼����1����¼����2ʵʱ¼����3����ʱ¼��
					String rep = names[8];
					this.setRecType(rep);
				case 8:
					this.setStationName(names[7]);
				case 7:
					this.setLanguage(names[6]);
				case 6:
					this.setHeadCode(names[0]);
					this.setTaskId(names[1]);
					this.setStartDatetime(names[2]);
					this.setEndDatetime(names[3]);
					this.setFreq(names[4]);
					this.setEquCode(names[5]);
					this.setLanguage(names[6]);
					this.setStationName(names[7]);
					this.setBand(SiteRunplanUtil.getBandFromFreq(this.getFreq()));
				break;
				
			}
			if (ext.equals(".zip"))
				Zip.unZip2Local(fileUrl);
		}

		public String getFileUrl() {
			return fileUrl;
		}

		public void setFileUrl(String fileUrl) {
			this.fileUrl = fileUrl;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public String getFmModulation() {
			return fmModulation;
		}

		public void setFmModulation(String fmModulation) {
			this.fmModulation = fmModulation;
		}

		public String getAmModulation() {
			return amModulation;
		}

		public void setAmModulation(String amModulation) {
			this.amModulation = amModulation;
		}

		public String getOffset() {
			return offset;
		}

		public void setOffset(String offset) {
			this.offset = offset;
		}

		public String getTaskId() {
			return taskId;
		}

		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}

		public String getStartDatetime() {
			return startDatetime;
		}

		public void setStartDatetime(String startDatetime) {
			this.startDatetime = startDatetime;
		}

		public String getEndDatetime() {
			return endDatetime;
		}

		public void setEndDatetime(String endDatetime) {
			this.endDatetime = endDatetime;
		}

		public String getFreq() {
			return freq;
		}

		public void setFreq(String freq) {
			this.freq = freq;
		}

		public String getEquCode() {
			return equCode;
		}

		public void setEquCode(String equCode) {
			this.equCode = equCode;
		}

		public String getBand() {
			return band;
		}

		public void setBand(String band) {
			this.band = band;
		}

		public String getHeadCode() {
			return headCode;
		}

		public void setHeadCode(String headCode) {
			this.headCode = headCode;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public String getRecType() {
			return recType;
		}

		public void setRecType(String recType) {
			this.recType = recType;
		}

		public String getStationName() {
			return stationName;
		}

		public void setStationName(String stationName) {
			this.stationName = stationName;
		}

		
	}

	/**
	 * 
	 * V8���������ʵ��
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
			Collection ps = rcr.getChildren();
			if (ps == null)
				return;

			_values = new ArrayList(ps.size());
			for (Iterator easit = ps.iterator(); easit.hasNext();) {
				MessageElement ea = (MessageElement) easit.next();
				MsgFileRetrieveRes.this._values.add(new FileRetrieve(ea
						.getAttributes()));
			}
		}
	}

}
