package com.viewscenes.bean;

import org.jdom.Element;

import com.viewscenes.bean.runplan.RunplanBean;

/**
 * 查询数据库录音文件返回对象
 * 
 * @author thinkpad
 * 
 */
public class RadioStreamResultBean {

	private String result_id;
	private String band;
	private String task_id;
	private String start_datetime;
	private String end_datetime;
	private String frequency;
	private String filename;
	private String filesize;
	private String head_id;
	private String url;
	private String report_type;
	private String is_stored;
	private String is_delete;
	private String store_datetime;
	private String mark_file_name;
	private String runplan_id;
	private String equ_code;
	private String  is_temporary;//区别临时和日常
	private String fmModulation;
	private String amModulation;
	private String offset;
	private String levelValue;
	private String stationName;	//用户下任务时手动添加的发射台名称
	private String language;	//用户下任务时手动添加的语言
	
	private String shortname;	//后增加的，这个数据库中没有该字段，只是为了方便页面传值
	
	
	public String getIs_temporary() {
		return is_temporary;
	}

	public void setIs_temporary(String isTemporary) {
		is_temporary = isTemporary;
	}

	private RunplanBean  runplanBean = new RunplanBean();			//录音相关联的运行图
	private RadioMarkZstViewBean radioMarkZstViewBean = new RadioMarkZstViewBean();//录音相关联的打分
	
	public RadioStreamResultBean() {
		
	}
//	public RadioStreamResultBean(Element attrs) {
////		this.recordID = attrs.getAttributeValue("RecordID");
//		this.start_datetime = attrs.getAttributeValue("StartDateTime");
//		this.end_datetime = attrs.getAttributeValue("EndDateTime");
//		this.url = attrs.getAttributeValue("URL");
//		this.filename = attrs.getAttributeValue("FileName");
//		this.filesize = attrs.getAttributeValue("size");
////		this.expireDays = attrs.getAttributeValue("expiredays");
//		this.levelValue = attrs.getAttributeValue("Level");
//		this.fmModulation = (attrs.getAttributeValue("FM-Modulation")==null?"":attrs.getAttributeValue("FM-Modulation"));
//		this.amModulation = (attrs.getAttributeValue("AM-Modulation")==null?"":attrs.getAttributeValue("AM-Modulation"));
//		this.offset = attrs.getAttributeValue("Offset");
//	}
	
	public String getResult_id() {
		return result_id;
	}

	public void setResult_id(String resultId) {
		result_id = resultId;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String taskId) {
		task_id = taskId;
	}

	public String getStart_datetime() {
		return start_datetime;
	}

	public void setStart_datetime(String startDatetime) {
		start_datetime = startDatetime;
	}

	public String getEnd_datetime() {
		return end_datetime;
	}

	public void setEnd_datetime(String endDatetime) {
		end_datetime = endDatetime;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getHead_id() {
		return head_id;
	}

	public void setHead_id(String headId) {
		head_id = headId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String reportType) {
		report_type = reportType;
	}

	public String getIs_stored() {
		return is_stored;
	}

	public void setIs_stored(String isStored) {
		is_stored = isStored;
	}

	public String getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(String isDelete) {
		is_delete = isDelete;
	}

	public String getStore_datetime() {
		return store_datetime;
	}

	public void setStore_datetime(String storeDatetime) {
		store_datetime = storeDatetime;
	}

	public String getMark_file_name() {
		return mark_file_name;
	}

	public void setMark_file_name(String markFileName) {
		mark_file_name = markFileName;
	}

	public String getRunplan_id() {
		return runplan_id;
	}

	public void setRunplan_id(String runplanId) {
		runplan_id = runplanId;
	}

	public String getEqu_code() {
		return equ_code;
	}

	public void setEqu_code(String equCode) {
		equ_code = equCode;
	}

	public RunplanBean getRunplanBean() {
		return runplanBean;
	}

	public void setRunplanBean(RunplanBean runplanBean) {
		this.runplanBean = runplanBean;
	}

	public RadioMarkZstViewBean getRadioMarkZstViewBean() {
		return radioMarkZstViewBean;
	}

	public void setRadioMarkZstViewBean(RadioMarkZstViewBean radioMarkZstViewBean) {
		this.radioMarkZstViewBean = radioMarkZstViewBean;
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

	public String getLevelValue() {
		return levelValue;
	}

	public void setLevelValue(String levelValue) {
		this.levelValue = levelValue;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	
}
